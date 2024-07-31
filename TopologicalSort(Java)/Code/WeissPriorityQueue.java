
import java.util.Iterator;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.HashMap;

/**
 * Class for priority queue.
 * @author Patrick Doyne
 * 
 * @param <T> is the object the queue can hold.
 */
public class WeissPriorityQueue<T> extends WeissAbstractCollection<T>
{
	/**
	 * Default size of this queue.	
	 */
	private static final int DEFAULT_CAPACITY = 100;
	/**
	 * Size of queue.
	 */
	private int currentSize;   
	/**
	 * Array to hold objects of any type.
	 */		
	private T [ ] array; 
	/**
	 * Comparator for custom compare.
	 */
	private Comparator<? super T> cmp;
	/**
	 * Helps with priority of queue.
	 */
	private HashMap<T, Integer> indexMap; 
	/**
	 * Gets the index of x.	
	 * @param x item in query.
	 * @return Index of x.
	 */
	public int getIndex(T x) {

		if(this.indexMap.get(x)!=null) {
			return this.indexMap.get(x);
		}
		return -1; 
	}
	/**
	 * Updates the current queue with compare.
	 * @param x item to adjust queue for.
	 * @return true if update works.
	 */
	public boolean update(T x) {

		if(this.indexMap.get(x)==null)return false;
		this.removeAny(x);
		this.add(x);

		return true; 
	}


	/**
	 * Construct an empty PriorityQueue.
	 */
	@SuppressWarnings("unchecked")
	public WeissPriorityQueue( )
	{
		currentSize = 0;
		cmp = null;
		array = (T[]) new Object[ DEFAULT_CAPACITY + 1 ];
		this.indexMap = new HashMap<T, Integer>();
	}

	/**
	 * Construct an empty PriorityQueue with a specified comparator.
	 * @param c is the comparator passed into the constructor for this queue.
	 */
	@SuppressWarnings("unchecked")
	public WeissPriorityQueue( Comparator<? super T> c )
	{
		currentSize = 0;
		cmp = c;
		array = (T[]) new Object[ DEFAULT_CAPACITY + 1 ];
		this.indexMap = new HashMap<T, Integer>();
	}


	/**
	 * Construct a PriorityQueue from another Collection.
	 * @param coll is a collection being passed in to ad to this queue.
	 */
	@SuppressWarnings("unchecked")
	public WeissPriorityQueue( WeissCollection<? extends T> coll )
	{
		cmp = null;
		currentSize = coll.size( );
		array = (T[]) new Object[ ( currentSize + 2 ) * 11 / 10 ];
		this.indexMap = new HashMap<T, Integer>();

		int i = 1;
		for( T item : coll )
			array[ i++ ] = item;
		buildHeap( );
	}

	/**
	 * Compares 2 objects.
	 * @param lhs is the first object.
	 * @param rhs is the second object.
	 * @return int showing which item is greater.
	 */
	@SuppressWarnings("unchecked")
	private int compare( T lhs, T rhs )
	{
		if( cmp == null ) {
			if(lhs instanceof GraphNode && rhs instanceof GraphNode) {
				GraphNode one = (GraphNode) lhs;
				GraphNode two = (GraphNode) rhs;
				int ans =0;
				if(one.getCost()<two.getCost()) {
					ans = -1;
				}
				else if(one.getCost()>two.getCost()) {
					ans = 1;
				}
				else {
					if(one.getId()<two.getId()) {
						ans =-1;
					}
					else if (one.getId()>two.getId()) {
						ans=1;
					}
					else ans = 0;			
				}
				return ans;
			}
			else{
				return ((Comparable)lhs).compareTo( rhs );
			}
		}
		else
			return cmp.compare( lhs, rhs );	
	}

	/**
	 * Adds an item to this PriorityQueue.
	 * @param x any object.
	 * @return true.
	 */
	public boolean add( T x )
	{
		if( currentSize + 1 == array.length )
			doubleArray( );

		// Percolate up
		int hole = ++currentSize;

		array[ 0 ] = x;

		for( ; compare( x, array[ hole / 2 ] ) < 0; hole /= 2 ) {
			array[ hole ] = array[ hole / 2 ];
			this.indexMap.put(array[hole/2],hole);
		}
		//index as hole and x as value this is likely for hash map
		array[ hole ] = x;
		this.indexMap.put(x, hole);

		return true;
	}

	/**
	 * Returns the number of items in this PriorityQueue.
	 * @return the number of items in this PriorityQueue.
	 */
	public int size( )
	{
		return currentSize;
	}

	/**
	 * Make this PriorityQueue empty.
	 */
	public void clear( )
	{
		currentSize = 0;
		this.indexMap.clear();
	}

	/**
	 * Returns an iterator over the elements in this PriorityQueue.
	 * The iterator does not view the elements in any particular order.
	 * @return an iterator over the elements in this PriorityQueue.
	 */
	public Iterator<T> iterator( )
	{
		return new Iterator<T>( )
		{
			int current = 0;

			public boolean hasNext( )
			{
				return current != size( );
			}

			@SuppressWarnings("unchecked")
			public T next( )
			{
				if( hasNext( ) )
					return array[ ++current ];
				else
					throw new NoSuchElementException( );
			}

			public void remove( )
			{
				throw new UnsupportedOperationException( );
			}
		};
	}

	/**
	 * Returns the smallest item in the priority queue.
	 * @return the smallest item.
	 * @throws NoSuchElementException if empty.
	 */
	public T element( )
	{
		if( isEmpty( ) )
			throw new NoSuchElementException( );
		return array[ 1 ];
	}

	/**
	 * Removes the smallest item in the priority queue.
	 * @return the smallest item.
	 * @throws NoSuchElementException if empty.
	 */
	public T remove( )
	{
		T minItem = element( );
		array[ 1 ] = array[ currentSize-- ];
		this.indexMap.remove(minItem);
		percolateDown( 1 );


		return minItem;
	}
	/**
	 * Remove and x from this queue.
	 * @param x item to be removed.
	 * @return The item removed.
	 */
	public T removeAny (T x)
	{
		int itemIndex = this.indexMap.get(x);
		array[ itemIndex ] = array[ currentSize-- ];
		this.indexMap.remove(x);
		percolateDown( itemIndex );


		return x;
	}


	/**
	 * Establish heap order property from an arbitrary
	 * arrangement of items. Runs in linear time.
	 */
	private void buildHeap( )
	{
		for( int i = currentSize / 2; i > 0; i-- )
			percolateDown( i );
	}


	/**
	 * Internal method to percolate down in the heap.
	 * @param hole the index at which the percolate begins.
	 */
	private void percolateDown( int hole )
	{
		int child;
		T tmp = array[ hole ];

		for( ; hole * 2 <= currentSize; hole = child )
		{
			child = hole * 2;
			if( child != currentSize &&
					compare( array[ child + 1 ], array[ child ] ) < 0 )
				child++;
			if( compare( array[ child ], tmp ) < 0 ) {
				array[ hole ] = array[ child ];
				this.indexMap.put(array[hole], child);
			}
			else
				break;
		}
		array[ hole ] = tmp;
		this.indexMap.put(tmp, hole);
	}

	/**
	 * Internal method to extend array.
	 */
	@SuppressWarnings("unchecked")
	private void doubleArray( )
	{
		T [ ] newArray;

		newArray = (T []) new Object[ array.length * 2 ];
		for( int i = 0; i < array.length; i++ )
			newArray[ i ] = array[ i ];
		array = newArray;
	}
}

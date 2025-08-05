import java.util.*;

//Fixed size generic queue
public final class BoundedQueue <E> {

	private List<E> rep;
	private int maxSize;

	//Rep-Invariant: maxSize > 0 && rep != null && each element in rep != null

	//Creator
	//Effects: throws IllegalArguementException if size <= 0 else creates a new bounded queue of length size
	public BoundedQueue(int size) throws IllegalArgumentException{
		if(size <= 0) {
			throw new IllegalArgumentException("size must be greater than 0. NO OBJECT WAS CREATED");
		}	
		rep = new ArrayList<E>();
		maxSize = size;
	}

	//Observer
	//Effects: returns true if rep has no elements else returns false;
	public boolean isEmpty() {
		return (rep.isEmpty());
	}

	//Observer
	//Effects: returns true if rep has no space left else returns false;
	public boolean isFull() {
		return (rep.size() == maxSize);

	}

	//Observer
	//Effects: returns number of elements in this BoundedQueue
	public int getCount() {
		return rep.size();
	}

	//Observer
	//Effects: returns maximum number of elements this BoundedQueue can hold
	public int getSize() {
		return maxSize;
	}

	//Mutator
	//Effects: if e null throw NPE else if queue is already full throw ISE else add e to the queue
	public void put(E e) throws NullPointerException, IllegalStateException{
		if (e == null) throw new NullPointerException("Null elements are not permitted within BoundedQueue");
		if (isFull()) throw new IllegalStateException("BoundedQueue is full! No space left!");
		rep.add(e);  
	}
   
	//Mutator
	//Effects: if the queue is empty throw ISA else remove and return the first element from this BoundedQueue
	public E get() throws IllegalStateException{
		if (isEmpty()) throw new IllegalStateException("BoundedQueue is empty! Nothing to get!");
		E result= rep.removeFirst();
		return result;
	}

	//Mutator
	//Effects: If list is null throw NPE else if the BoundedQueue is full || if have no space to add the full list-> throw ISE else add all elements from list to this BoundedQueue
	public void putAll(List<? extends E> list) throws NullPointerException, IllegalStateException{
		if(list == null) throw new NullPointerException("List is not permitted to be null!");
		if(isFull() || rep.size()+list.size() > maxSize) throw new IllegalStateException("BoundedQueue is full! No space left!");
		rep.addAll(list);	
	}

	//Mutator
	//Effects: If this BoundedQueue is empty throw ISE else copy over all elements to a new list and return this list 
	public List<E> getAll() {
		if (isEmpty()) throw new IllegalStateException("BoundedQueue is empty! Nothing to get!");
		List<E> result = new ArrayList<>();;
		while (!isEmpty()) {
			result.add(this.get());
		}
		return result;
	}

	public static void main(String args[]) {
		BoundedQueue<Number> queue = new BoundedQueue<>(10);
		
		List <Double> list2 = new ArrayList<>();
		list2.add(2.2);
		list2.add(1.0);

		for (int i = 0; !queue.isFull(); i++) {
			queue.put(i);
			System.out.println("put: " + i);
		}
		while (!queue.isEmpty()) {
			System.out.println("get: " + queue.get());
		}
		queue.putAll(list2);
		System.out.println("getAll: " + queue.getAll());

	}
}
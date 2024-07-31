import java.util.Iterator;
/**
 * Represents a doubly linked list.
 * @author Patrick T Doyne
 *
 * @param <T> is the element that this list stores.
 */
public class ThreeTenDLList<T> implements Iterable<T> { 
	/**
	 * Points to head of list.
	 */
	private Node<T> head; 
	/**
	 * Points to tail of list.
	 */
	private Node<T> tail; 
	/**
	 * Size of the list.
	 */
	private int sizeCount;

	/**
	 * Constructor.
	 */
	public ThreeTenDLList(){

		this.sizeCount =0;

	}

	/**
	 * Shows the size of this list.
	 * @return the size of this list.
	 */
	public int size(){
		return this.sizeCount;	
	}

	/**
	 * Get the data of the first node in this list.
	 * @return the data from the first node in this list.
	 */
	public T getFirst() {
		if(this.sizeCount==0) return null;
		return head.getData();	
	}

	/**
	 * Add a new node to the front of this list.
	 * @param value is the value inside the node being added to the front of the list.
	 */
	public void addFirst(T value) { 

		Node<T> tmp = new Node<T>(value);

		if(this.sizeCount==0) {		

			this.head = tmp;
			this.tail = tmp;		
			this.sizeCount++;
			return;

		}
		if(this.sizeCount==1) {
			this.head = tmp;
			this.tail.setPrev(this.head);
			this.head.setNext(this.tail);
			this.sizeCount++;
			return;
		}
		else {
			this.head.setPrev(tmp);
			tmp.setNext(this.head);
			this.head=tmp;				
			this.sizeCount++;
		}

	}

	/**
	 * Remove the node at the front of this list.
	 * 
	 * @return value of the node being removed.
	 */
	public T removeFirst(){ //FINISHED

		if(this.sizeCount==0)return null;
		T val = this.head.getData();
		if(this.sizeCount==1) {
			this.tail=new Node<T>(null);
			this.head=new Node<T>(null);
			this.sizeCount--;

			return val;	
		}
		this.head = this.head.getNext();
		this.sizeCount--;
		return val;	
	}

	/**
	 * Get the value of the last node in this list.
	 * @return the value of the node.
	 */
	public T getLast() {
		if(this.sizeCount==0)return null;
		return this.tail.getData();

	}


	/**
	 * Add a new node to the end of this list.
	 * @param value is the value inside the new node being added to the end of the list.
	 */
	public void addLast(T value) {		

		Node<T> tmp = new Node<T>(value);

		if(this.sizeCount==0) {		
			this.tail=tmp;
			this.head=tmp;
			this.sizeCount++;		
			return;
		}
		if(this.sizeCount==1) {
			this.tail=tmp;
			this.tail.setPrev(this.head);
			this.head.setNext(this.tail);
			this.sizeCount++;
			return;
		}
		else {
			this.tail.setNext(tmp);
			tmp.setPrev(this.tail);
			this.tail=tmp;
			this.sizeCount++;

		}
	}


	/**
	 * Removes the last node from this list.
	 * @return the value from the node being removed.
	 */
	public T removeLast(){

		if(this.sizeCount==0)return null;

		T val = this.tail.getData();

		if(this.sizeCount==1) {
			this.tail=new Node<T>(null);
			this.head=new Node<T>(null);
			this.sizeCount--;
			return val;	
		}
		this.tail = this.tail.getPrev();
		this.sizeCount--;
		return val;	
	}

	/**
	 * Remove a node with a specified value from this list.
	 * @param value is the value to be removed.
	 * @return value of the node just removed.
	 */
	public T remove(T value){//WRONG SO FAR
	
		T target = head.getData();
		if(target.equals(value)) {return this.removeFirst();}
		target=tail.getData();
		if(target.equals(value))return this.removeLast();
		
		Node<T> x = head;
		Iterator<T> temp = this.iterator();
		int count = 0;
		
		while(temp.hasNext()) {
			count++;
			if(count==this.sizeCount) {
				return null;
			}
				
			if(x.getNext().getData().equals(value)) 
			{
				target=x.getNext().getData();
				x.setNext(x.getNext().getNext());
				x.getNext().setPrev(x);	
				this.sizeCount--;
				return target;
			}
			else {
				x=x.getNext();	
				temp.next();
			}		
		}
		return null;	
	}

	/**
	 * Return list of this lists value starting a given node.
	 * @param start is the starting point.
	 * @return A string of the values after the starting index.
	 */
	public String listToString(int start) { 
		if(this.sizeCount==0 || start <0)return "";
		int count = 0;
		Iterator<T> it = this.iterator();
		StringBuilder s = new StringBuilder();
		while(it.hasNext()) {
			if(count++<start) continue;
			else {
				s.append(it.next());
			}
		}

		return s.toString().trim();	

	}
	/**
	 * List of the values in this list from back to front.
	 * @return a list of the values in this list from back to front.
	 */
	public String listToStringBackward() {  
		if(this.sizeCount==0)return "";

		StringBuilder s = new StringBuilder();
		Iterator<T> list = this.backwardIterator();
		while(list.hasNext()) {
			s.append(list.next()+" ");			
		}
		return s.toString().trim();	

	}
	/**
	 * Iterates through all items T.
	 */
	@Override
	public Iterator<T> iterator() {

		Iterator<T> items = new Iterator<T>() {

			private int count=0;
			private Node<T> temp = head;
			/**
			 * True if there is another value in the list.
			 */
			@Override
			public boolean hasNext() {
				if(count>=sizeCount) return false;
				return  temp!=null;

			}
			/**
			 * moves pointers to the next item in this list.
			 */
			@Override
			public T next() {

				if(temp==null) 
					throw new NullPointerException("This list has reached its end!");
				T tmp = temp.getData();
				temp=temp.getNext();
				count++;
				return tmp;
			}

		};

		return items;
	}

	/**
	* Iterates through items T from back to front.
	* @return the new iterator.
	*/
	public Iterator<T> backwardIterator() {

		Iterator<T> items = new Iterator<T>() {

			private int count=0;
			private Node<T> temp = tail;
			/**
			 * True if there is another value in the list.
			 */
			@Override
			public boolean hasNext() {
				if(count>=sizeCount) return false;
				return  temp!=null;

			}
			/**
			 * moves pointers to the previous item in this list.
			 */
			@Override
			public T next() {

				if(temp==null) 
					throw new NullPointerException("This list has reached its end!");
				T tmp = temp.getData();
				temp=temp.getPrev();
				count++;
				return tmp;
			}

		};
	
		return items;


	}

	/**
	 * Full string representation of the values of all items in this list.
	 * @return string representation of the values of all items in this list.
	 */
	public String listToString() {  
		StringBuilder s = new StringBuilder();
		Iterator<T> list = this.iterator();
		while(list.hasNext()) {
			s.append(list.next()+" ");			
		}
		return s.toString().trim();
	}

	/**
	 * This is to test different cases of functionality within this object.
	 * @param args are the arguments passed in from the console.
	 */
	public static void main(String[] args) {

		//addFirst		
		ThreeTenDLList<Integer> list = new ThreeTenDLList<>();
		list.addFirst(100);

		list.addFirst(200);

		list.addFirst(300);

		list.addFirst(400);	



		if (list.getFirst()==400 && list.getLast()==100 &&
				list.listToString().equals("400 300 200 100")) {
			System.out.println("Yay1");
		}

		list.addLast(500);	
		if (list.listToString().equals("400 300 200 100 500")) {
			System.out.println("Yay2");
		}

		
		ThreeTenDLList<String> states = new ThreeTenDLList<>();
		states.addLast("VA");
		states.addLast("MD");
		states.addLast("NJ");
		states.addLast("WV");
		states.addLast("WA");
				
		//removeFirst, removeLast
		String name1 = states.removeFirst();
		String name2 = states.removeLast();
				

		if (name1.equals("VA") && name2.equals("WA") && 
			states.listToString().equals("MD NJ WV") && 
			states.listToStringBackward().equals("WV NJ MD")){
			System.out.println("Yay3");
		}
		

		//remove
		ThreeTenDLList<Integer> nums = new ThreeTenDLList<>();
		nums.addLast(10);
		nums.addLast(20);
		nums.addLast(10);
		nums.addLast(30);
		nums.addLast(10);
		nums.addLast(40);
		if (nums.remove(10)==10 && nums.listToString().equals("20 10 30 10 40")
			&& nums.remove(10)==10 && nums.listToString().equals("20 30 10 40")
			&& nums.remove(50)==null && nums.remove(40)==40 
			&& nums.listToString().equals("20 30 10")
			&& nums.listToStringBackward().equals("10 30 20")){
			System.out.println("Yay4");		
		}
				
				
		//iterator
		int total = 0;
		for (Integer num: nums){
			total += num;
		}
		if (total == 60){
			System.out.println("Yay5");		
		}
		
		
		Iterator<String> iter = states.iterator();
		if (iter.hasNext() && iter.next().equals("MD") &&
			iter.next().equals("NJ") && iter.next().equals("WV") 
			&& !iter.hasNext()){
			System.out.println("Yay6");		
		}
		
		/**
		 * Random class to test special case of remove.
		 */
		class SomeType{
			private String value;
			/**
			 * Constructor.
			 * @param value is the value being passed in.
			 */
			public SomeType(String value) { this.value = value; }
			/**
			 * Checks to see if first Char of two objects are equal.
			 * @param o is the object being checked for equality.
			 * @return true if the objects are equal.
			 */
			public boolean equals(Object o) {
				if (!(o instanceof SomeType)) return false;
						
				//both null
				if (((SomeType)o).value == null && this.value==null) return true;
						
				//both empty string
				if (((SomeType)o).value.length() == 0 && this.value.length()==0) return true;
						
				//compare only the leading chars
				return ((SomeType)o).value.charAt(0) == this.value.charAt(0);
			}
			/**
			 * Returns a string represntation of the item.
			 * @return a string represntation of the item.
			 */
			public String toString(){ return value;}
		}	
				
		SomeType item1 = new SomeType("Apple");
		SomeType item2 = new SomeType("Alligator");
		SomeType item3 = new SomeType("Bee");
		SomeType item4 = new SomeType("Alder");
				
				
		ThreeTenDLList<SomeType> items = new ThreeTenDLList<>();
		items.addLast(item1);
		items.addLast(item2);
		items.addLast(item3);

				
		SomeType deleted = items.remove(item4);
		if (deleted.toString().equals("Apple")){
			System.out.println("Yay7");				
		}
				
	}
}
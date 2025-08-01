
import java.util.Iterator;
/**
 * This is a class that represents a stack which uses a DL list for its element.
 * 
 * @author Patrick T Doyne
 *
 * @param <T> is the item being stored in this stack.
 */
public class ThreeTenStack<T> {  
	/**
	 * DL list of elements for this stack.
	 */
	private ThreeTenDLList<T> elements;
	/**
	 * constructor.
	 */
	public ThreeTenStack() {
		this.elements = new ThreeTenDLList<T>();
	}
	/**
	 * Pushes an item to the top of the stack.
	 * @param item is what we are adding to the stack.
	 */
	public void push(T item) {	
		this.elements.addFirst(item);
	}
	/**
	 * Removes the element at the top of the stack.
	 * @return the value being popped off of the stack.
	 */
	public T pop() {
		if(this.isEmpty())return null;
		return this.elements.removeFirst();
	}
	/**
	 * Shows what the top element of the stack is.
	 * @return the element at the top of the stack.
	 */
	public T peek() {
		if(this.isEmpty())return null;
		return 	elements.getFirst();
	}
	/**
	 * Returns a String representation of the stack bottom to top.
	 * @return a String representation of the stack bottom to top.
	 */
	public String toString() {

		return this.elements.listToStringBackward();

	}

	/**
	 * Checks to see if the stack has any elements in it.	
	 * @return true if there is/are element(s) in the stack.
	 */
	public boolean isEmpty() {
		return this.elements.size()==0;
	}
	/**
	 * Reverse the order of the stack.
	 * @return the reversed stack.
	 */
	public ThreeTenStack<T> reverseStack(){

		ThreeTenStack<T> temp = new ThreeTenStack<T>();
		int size = this.elements.size();
		for(int i = 0 ; i < size; i++) {
			T item = this.pop();
			temp.push(item);
			this.elements.addLast(item);
		}

		return temp;	

	}

	/**
	 * This is to test different cases of functionality within this object.
	 * @param args are the arguments passed in from the console.
	 */
	public static void main(String[] args) {
		ThreeTenStack<String> s = new ThreeTenStack<>();
		s.push("student");
		s.push("help");

		if (!s.isEmpty() && s.peek().equals("help") && s.pop().equals("help")
				&& s.peek().equals("student")) {
			System.out.println("Yay1");
		}

		s.push("support");
		s.push("and");
		s.push("advocacy");
		s.push("center");
		if (s.toString().equals("student support and advocacy center")
				&& !s.isEmpty()) {
			System.out.println("Yay2");
		}

		ThreeTenStack<String> back = s.reverseStack();
		//uncomment to check details


		s.pop();
		s.pop();
		s.pop();



		if (s.toString().equals("student support") && s.pop().equals("support")
				&& s.pop().equals("student") && s.isEmpty() && s.pop() == null
				&& back.toString().equals("center advocacy and support student")) {
			System.out.println("Yay3");
		}

	}
}

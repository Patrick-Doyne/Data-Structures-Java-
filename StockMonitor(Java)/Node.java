
/**
 * A generic class representing a node in the doubly linked list implementation 
 * ThreeTenDLList. 
 * @param <T> the type of element in the linked list
 *
 * @author Y. Zhong
 */

class Node<T> {
	/**
	 * The node's data value. 
	 */
	private T data;
	
	/**
	 * The node's link to the next element in the linked list. 
	 */	
	private Node<T> next;
	
	/**
	 * The node's link to the previous element in the linked list. 
	 */
	private Node<T> prev;
	
	/**
	 * constructor.
	 * @param data value to initialize data attribute
	 */
	public Node(T data) {
		this.data = data;
		this.next = null;
		this.prev = null;
	}
	
	/**
	 * getter for the data attribute.
	 * @return data attribute of this node
	 */
	public T getData() {
		return data;
	}
	
	/**
	 * setter for the data  attribute.
	 * @param data value to set data attribute
	 */
	public void setData(T data) {
		this.data = data;
	}
	
	
	/**
	 * getter for the next attribute.
	 * @return next attribute of this node
	 */
	public Node<T> getNext() {
		return this.next;
	}
	
	/**
	 * setter for the data attribute.
	 * @param next reference to set next attribute
	 */
	public void setNext(Node<T> next) {
		this.next = next;
	}
	
	/**
	 * getter for the prev attribute.
	 * @return prev attribute of this node
	 */
	public Node<T> getPrev() {
		return this.prev;
	}
	
	/**
	 * setter for the data attribute.
	 * @param prev reference to set prev attribute
	 */
	public void setPrev(Node<T> prev) {
		this.prev = prev;
	}
	
	/**
	 * returns a string description of the node's data attribute.
	 * @return string based on data of this node
	 */	
	@Override
	public String toString() { 
		return data.toString(); 
	}

}



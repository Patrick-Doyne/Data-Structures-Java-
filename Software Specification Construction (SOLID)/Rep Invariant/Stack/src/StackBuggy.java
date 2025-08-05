

public class StackBuggy {

   private Object[] elements;
   private int size = 0;

   // Rep-invariant: 
   // int i >= 0 && i < size | elements[i] must be an Object != null
   
   boolean repOk() {

      if(size < 0 || this == null || elements.length < size) return false; // invalid size or object itself was never initialized
      for(int i = 0; i < size; i++){
         if(elements[i] == null) return false; // Stack "contains" a null object 
         if(!(elements[i] instanceof Object)) return false; // i is not an object
      }
      return true;
	
   }
   
   // AF(this) = A stack representation that stores initialized objects in this.elements for i = 0 .. size-1 iff the stack is not empty, else we have an empty stack. No restrictions on the stack size itself.
   
   public String toString() {
	  if(size == 0) return "ThisStack [Elements: [], Size = 0]";
      String ret = "ThisStack [" +
                "Elements = [" + elements[0];
      for(int i = 1; i <size;i++) {
    	  ret += ", " + elements[i];
      }
              return ret +  "], Size = " + size +
                ']';
   }



   // Effects: creates an empty stack
   public StackBuggy() {
     this.elements = new Object[0];
   }

   // Effects: Pushes e onto the top of this stack
   //		   i.e., this becomes this + [e]
   //          throws IllegalArgumentException if e is null
   public void push (Object e) {	   
	/* BUG THAT WAS ADDED IS TO TAKE OUT THE NULL CHECK PREVENTING NULL VALUES FROM BEING ADDED */  
	/* if (e == null) throw new IllegalArgumentException(); */
	   
     ensureCapacity();
     elements[size++] = e;
     
   }
   
   // Effects: Removes the object at the top of this stack and returns it as the value of this function
   //	       i.e., this becomes this - [e]  
   //          throws EmptyStackException if this stack is empty 
   public Object pop () {
	 /* BUG ADDED TO ALLOW FOR INCORRECT SIZING */
     /*if (size <= 0) throw new EmptyStackException();*/
     Object result = elements[--size];
     elements[size] = null;
     return result;
   }

   private void ensureCapacity() {
      if (elements.length == size) {
         Object oldElements[] = elements;
         elements = new Object[2*size + 1];
         System.arraycopy(oldElements, 0, elements, 0, size);
      }
   }

}
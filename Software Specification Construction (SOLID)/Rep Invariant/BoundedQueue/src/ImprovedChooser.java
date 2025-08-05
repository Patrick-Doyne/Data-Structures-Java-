import java.util.*;
import java.util.concurrent.*;
public final class ImprovedChooser<T>{

	  private final ArrayList<T> choiceArray;
	  
	  // Rep-invariant: 
	  // choiceArray != null
	  // int i >= 0 && i < size | choiceArray.get(i) must be anything != null
	   
	   private boolean repOk() {
		   if( choiceArray == null) return false; //object itself was never initialized
		   int size = choiceArray.size();
	       for(int i = 0; i < size; i++){
	         if(choiceArray.get(i) == null) return false; //  contains a null  
	       }
	       return true;	
	   }
	   //Creator
	   //Effects: Creates a Chooser<T> object with an ArrayList<T> when passed in a Collection<T>
	   public ImprovedChooser (Collection<T>  choices) {
	       choiceArray = new ArrayList<T>(choices);
	   }
	    
	   //Observer
	   //Effects: Returns null if rep-invar was violated else creates hard copy, chooses a random element from within hard copy, then returns. (from this specific instance of the object [THREAD SAFE])
	   public T choose() {
		   if(!repOk()) return null;
	       Random rnd = ThreadLocalRandom.current();
	       ArrayList<T> tmp = new ArrayList<T>(choiceArray);
	       return tmp.get(rnd.nextInt(tmp.size()));
	   }
	   
	   //Mutator
	   //Effects: Prints an error message when rep is violated or choice is null then returns with no changes, else adds the passed in T to choiceArray
	   public void addChoice(T choice) {
		   if(!repOk() || choice == null) {
			   System.out.println("Rep invariant violated at the start of method: addChoice in Chooser class. NO CHANGES MADE");
			   return;
		   }
		   choiceArray.add(choice);	   
	   }
	   
	   
}

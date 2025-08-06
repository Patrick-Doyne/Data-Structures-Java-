import java.util.*;

public class IntSet {  
   private List<Integer> els;
   
   public IntSet(List<Integer> els) {
	   this.els = els;
   }
   
   public String toString() { return els.toString(); }

   
   /******** Two different ways to implement equals() ********/

   /* equalsOption1: */
   // order is taken into consideration
   // this should be problematic. Why?
   
   /* Although List default equals method checks ordering, size, and value
    * we are actually using list as a wrapper for other classes that extend the interface
    * Particularly any class the extends List can be wrapped and stored in this class
    * under els and at the end of the day what we are trying tro do is disguise the
    *  use of a list as the use of a set therefor the ordering of a "List" makes 
    *  no sense inm the context of this class since sets do not care about
    *  ordering of elements only that they do not contain duplicates. 
    * 
    */
   @Override public boolean equals(Object obj) { 
	   if(!(obj instanceof IntSet)) return false;
		IntSet tmp = (IntSet)obj;
		//if(tmp.els.size() != this.els.size()) return false;// Shouldn't need this since we have the deepEquals
		return Objects.deepEquals(tmp, obj);
   }
   
  
   /* equalsOption2: */
   // order is not taken into consideration
   // this is the correct behavior. Why?
   
   /*
    * This is correct because this is how we can use composition and still
    * maintain the ability toi treat this class as a set: i.e. order does not matter
    * We must hide details about the implementation from the clients
    */
   
   @Override public boolean equals(Object obj) {     
	   if(!(obj instanceof IntSet)) return false;
	 		IntSet tmp = (IntSet)obj;
	 		if(tmp.els.size() != this.els.size()) return false;

	 		for(Integer i:els) {
	 			if(this.els.contains(tmp.els.get(i) != 0)) return false;
	 		}
	 		return true;
   }
   
   

	/******** Four different ways to implement hashCode() ********/

   /* hashOption1: */
   // do not override "int hashCode()"
   // this should be problematic. Why?
   
   /* This is problematic because it violates the hashCode contract since
    * equals and it are tightly coupled we must always override both if we override equals
    */
   
   
   /* hashOption2: */
   // Is this problematic? Explain.
 
   /*
    * This is problematic because all though this does not violate
    * any contracts, it is terrible for performance when sifting through buckets
    * so we end up degrading to O(n) instead of O(1) because we have a single bucket only
    */
   
   @Override public int hashCode() { 
      return 0; 
   }
   
   
   /* hashOption3: */
   // this should be problematic. Why?
   /*
    * This is problematic because else.hashcode() return the hashcode for the object
    * but not for the objects within the list. This violates the hash contract because
    * two lists containing the same elements should have an equal hash code but in this
    * case they will be different since it is only generated from the external List object
    * Compostiton can also lead to consistanct issues since this class could contain
    * anything in els as long as it extends list
    */
   @Override public int hashCode() { 
      return Objects.hashCode(Objects.requireNonNull(this.els, "List cannot be null");
   }
   
   
   /* hashOption4: */
   // order is not taken into consideration
   // this is the correct behavior. Why?
   
   /*
    * Again since we are representing an IntSet from client POV they expect
    * that there will be no ordering enforced since its a set.
    */
   @Override public int hashCode() { 
      int hash = 0;
      for (Integer i:els) {
    	  hash += (i == null ? 0 : els.get(i).hashCode());
      }
      return hash;
   }
   
}
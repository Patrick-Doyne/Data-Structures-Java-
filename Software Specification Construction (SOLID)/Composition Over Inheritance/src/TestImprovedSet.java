import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class TestImprovedSet {
    @Test
    public void testIntSetEquals() {
        IntSet set = new IntSet();
        IntSet2 set2 = new IntSet2();

        // Here we are adding into our new IntSets to make them equal.
        set.add(1);
        set2.add(1);

        // Now we want to add more onto the new IntSet2 since it has doubles!
        set2.addDouble(1.0);

        // From this we can check and see that set one is equal to set2 BUT set2 is not equal to set.
        boolean oneEqualsTwo = set.equals(set2);
        boolean twoEqualsOne = set2.equals(set);

        // Shows that the transitive property doesn't hold true.
        assertTrue(oneEqualsTwo);
        assertFalse(twoEqualsOne);
    }

    @Test
    public void testImprovedIntSetEquals() {
    	StringBuilder me = new StringBuilder("Patrick");
    	StringBuilder engie = new StringBuilder("Engie");

    	StringBuilder dad = new StringBuilder("Dad");
    	StringBuilder lara = new StringBuilder("Lara");

    	Cat c1 = new Cat(me);
    	Cat c2 = new Cat(engie);
    	
    	Dog d1 = new Dog(dad);
    	Dog d2 = new Dog(lara);

        ImprovedSet<Cat> set = new DoubleSet<Cat>(c1.getType());
        DoubleSet<Dog> set2 = new DoubleSet<Dog>(d1.getType());

        // Here we are adding into our new IntSets to make them equal.
        set.add(c1);
       // set2.add(1);

        // Now we want to add more onto the new IntSet2 since it has doubles!
       // set2.addDouble(2.0);

        // From this we can see that both of the sets are able to correctly check if they are equal or not.
        boolean oneEqualsTwo = set.equals(set2);
       // boolean twoEqualsOne = set2.equals(set);

        // Shows that the transitive property holds true
        assertTrue(oneEqualsTwo);
      //  assertTrue(twoEqualsOne);
    }

//    @Test
//    public void testImprovedIntSetClone() throws CloneNotSupportedException {
//        ImprovedIntSet set;
//        ImprovedIntSet2 set2 = new ImprovedIntSet2();
//
//        // Add into our set2
//        set2.add(1);
//        set2.addDouble(1.0);
//
//        // Now we want to clone the information over to our original set.
//        set = set2.clone();
//
//        // The transitive property should still hold true.
//        boolean oneEqualsTwo = set.equals(set2);
//        boolean twoEqualsOne = set2.equals(set);
//
//        assertTrue(oneEqualsTwo);
//        assertTrue(twoEqualsOne);
//    }
}
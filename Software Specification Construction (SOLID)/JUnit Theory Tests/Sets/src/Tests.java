// Introduction to Software Testing
// Authors: Paul Ammann & Jeff Offutt
// Chapter 3; page ??
// Valid JUnit theory for sets.

import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.junit.Assume.*;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;

import java.util.*;

@RunWith (Theories.class)
public class Tests
{
	@DataPoints
	public static Object[] allEqual = {	null, new IntSet(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 10, 15))), 
			new IntSet(new ArrayList<Integer>(Arrays.asList(3, 2, 1, 15, 10))),
			new IntSet(new ArrayList<Integer>(Arrays.asList(15, 2, 1, 3, 10)))
	};


	@Theory
	public void testEquals(Object a, Object b)  
	{
		assumeTrue (a != null);            
		assumeTrue (b != null);            

		assertTrue(a.equals(b) && b.equals(a));
	}

	@Theory
	public void testHashCode(Object a, Object b)  
	{
		assumeTrue (a != null);            
		assumeTrue (b != null);            
		assumeTrue(a.equals(b) && b.equals(a)); 

		assertTrue(a.hashCode() == b.hashCode());
	}
}

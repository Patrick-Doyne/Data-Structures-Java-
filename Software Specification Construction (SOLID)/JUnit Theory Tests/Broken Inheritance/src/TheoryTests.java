import org.junit.*;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.junit.Assume.*;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;

import java.awt.Color;


@RunWith (Theories.class)
public class TheoryTests
{
	/* 1-a */
	@DataPoints public static final Point[] points = {
			null, new Point(3, 7), new ColorPoint(3, 7, Color.red), new ColorPoint(3, 7, Color.blue)
	};

	/* 1-b */
	// 4x4 = 16 (cartesian product)
	// 7 fail pre-cond
	// 9 make it to the post-condition 


	/* 1-c */
	// If we were to favor composition over inhertiance then 
	// the theory tests class will break. We will recieve a compiler 
	// error because we can no longer create an array of Points 
	// that contains ColorPoints since there is no longer a relationship

	@Theory public void testSymmetry (Object a, Object b) {

		//Pre-condition
		assumeTrue(a!=null);
		assumeTrue(b!=null);
		//Post-condition
		try {
			assertTrue(a.equals(b) && b.equals(a));
		}
		catch(AssertionError e) {
			System.out.println("Symmetry violated: Point 1: " + a + " \n\t\tPoint 2: " + b);
		}
	}

	/* 2-b */
	//4x4x4 = 64 (cartesian product)
	//27 make it past null checks and only 7 of those 
	//make it to assert (note if equal contract was not
	//violated only 3 make it past the assert



	/* 2-c */
	//If we were to favor composition over inhertiance then 
	//the theory tests class will break. We will recieve a compiler 
	//error because we can no longer create an array of Points 
	//that contains ColorPoints since there is no longer a relationship
	//Also note this will break equality between CP and P
	@Theory public void testTransitivity(Object a, Object b, Object c) {

		//Pre-condition
		assumeTrue(a!=null);
		assumeTrue(b!=null);
		assumeTrue(c!=null);

		assumeTrue(a.equals(b));
		assumeTrue(b.equals(c));

		//Post-condition
		try {
			assertTrue(a.equals(c));
			//System.out.println("Symmetry Maintained: Point 1: " + a + " \n\t\tPoint 2: " + b + " \n\tPoint 3: " + c);

		}
		catch(AssertionError e) {
			System.out.println("Symmetry violated: Point 1: " + a + " \n\t\tPoint 2: " + b + " \n\tPoint 2: " + c);
		}

	}
	/* 3 */
	@Theory public void testEqualAndHash(Object a, Object b) {
		assumeTrue(a!= null);
		assumeTrue(b!=null);
		assumeTrue(a.equals(b));

		//Post-condition
		try {
			assertTrue(a.hashCode() == b.hashCode());
			//System.out.println("HashCode Contract Maintained: Point 1: " + a + " \n\t\tPoint 2: " + b );

		}
		catch(AssertionError e) {
			System.out.println("HashCode Contract Violated: Point 1: " + a + " \n\t\tPoint 2: " + b );
		}


	}

}

/* 4 */

/*
 * 
 *public final class BrokenHash {
 *	private final int x;
 *	private final int y;
 *	public BrokenHash(int x, int y) {
 *		this.x = x;
 *		this.y = y;
 *	}
 *	@Override public boolean equals(Object a) {
 *		if(!(a instanceof BrokenHash)) return false;
 *		BrokenHash tmp = (BrokenHash)a;
 *		return this.x == tmp.x && this.y == tmp.y;
 *	}
 *	
 *	 FIX FOR CONTRACT
 *
 *	  @Override public int hashCode(){
 *	  	return this.x * 31 + this.y ;
 *	  }
 *
 *}
 *	
 * 
 */

/* 5 */
// Comparable would need to have theory tests for anti-symmetry, transitivity, 
// and tests for the equals and hashcode contracts as well (reflexivity is not directly apart
// of the CompareTo contract.

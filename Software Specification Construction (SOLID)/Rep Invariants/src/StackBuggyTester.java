//import static org.junit.Assert.*;
//import org.junit.jupiter.api.Test;

import org.junit.*;
import static org.junit.Assert.*;


public class StackBuggyTester {
			
		//These test violate the rep invariant and should return false to the assert statements
		@Test 
		public void push_NULL_rep_not_Ok_test() {
			StackBuggy stack = new StackBuggy();
			stack.push(1);
			stack.push(null);
			assertFalse(stack.repOk());
		}
		
		@Test 
		public void pop_empty_rep_not_Ok_test() {
			StackBuggy stack = new StackBuggy();
			try {
				stack.pop();
			}
			catch(Exception e) {
				System.out.println("Buggy exception caught size was still updated...");
			}
			assertFalse(stack.repOk());
		}
		
		
}

//import static org.junit.Assert.*;
//import org.junit.jupiter.api.Test;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;


public class StackTester {
		public Stack stack = new Stack();
		
		@After /* NOTE THIS RUNS EVEN AFTER THE EXCEPTION IS CAUGHT AND CONFIRMS STATE AFTER EXCEPTIONS */
		public void name() { 
			assertTrue(stack.repOk());
			stack = new Stack();
		}
		
		@Test (expected = EmptyStackException.class)
		public void pop_repOk_test() {
			stack.pop();
		}
		
		@Test (expected = IllegalArgumentException.class)
		public void push_NULL_repOk_test() {
			stack.push(1);
			stack.push(null);
		}
		
		
		@Test
		public void empty_stack_repOk_test() {
			stack = new Stack();
		}
		
		@Test
		public void push_repOk_test() {
			stack.push(1);
		}
		
		@Test
		public void push_pop_repOk_test() {
			stack.push(1);
			stack.pop();
		}
		
		@Test
		public void different_objects_repOk_test() {
			stack.push(1);
			stack.push('a');
			stack.push("TEST");
			stack.push(3.14);
			stack.push(new Stack());
		}
		
		//After exceptions occur we want to make sure that if we return to a safe state that rep invar still holds
		@Test
		public void exceptions_repOk_test() {
			try {
				stack.pop();
			}
			catch(EmptyStackException e) {
				System.out.println("EmptyStackException caught properly");
			}
			stack.push(1);
			stack.push('a');
			try {
				stack.push(null);
			}
			catch(IllegalArgumentException e) {
				System.out.println("IllegalArgumentException caught properly");
			}
			stack.push("TEST");
			stack.push(3.14);
		}
		

		@Test
		public void empty_toString_test() {
			Stack stack = new Stack(); 
			assertEquals("ThisStack [Elements: [], Size = 0]", stack.toString());
		}
		
		@Test
		public void Strings_toString_test() {
			Stack stack = new Stack(); 
			stack.push("NYC");
			stack.push("DC");
			stack.push("LA");
			assertEquals("ThisStack [Elements = [NYC, DC, LA], Size = 3]", stack.toString());
		}
		
		//Ensure that the toString method does not print all of the empty indexes of elements
		@Test
		public void elements_toString_test() {
			Stack stack = new Stack(); 
			stack.push("NYC");
			stack.push("DC");
			stack.push("LA");
			stack.push("LA"); //causes a call to increase capacity of elements by 2x +1 
			assertEquals("ThisStack [Elements = [NYC, DC, LA, LA], Size = 4]", stack.toString());
		}
		
		@Test
		public void different_objects_toString_test() {
			stack.push(1);
			stack.push('a');
			stack.push("TEST");
			stack.push(3.14);
			assertEquals("ThisStack [Elements = [1, a, TEST, 3.14], Size = 4]", stack.toString());
		}
		
		@Test
		public void exceptions_toString_test() {
			try {
				stack.pop();
			}
			catch(EmptyStackException e) {
				System.out.println("EmptyStackException caught properly");
			}
			assertEquals("ThisStack [Elements: [], Size = 0]", stack.toString());
			stack.push(1);
			stack.push('a');
			try {
				stack.push(null);
			}
			catch(IllegalArgumentException e) {
				System.out.println("IllegalArgumentException caught properly");
			}
			stack.push("TEST");
			stack.push(3.14);
			assertEquals("ThisStack [Elements = [1, a, TEST, 3.14], Size = 4]", stack.toString());
		}
		//...
		//...
}

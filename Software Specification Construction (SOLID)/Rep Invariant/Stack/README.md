# *Stack Suite — Software Specification Construction*  


## *Program Structure & Design*:

  - A Java-based suite demonstrating proper and improper usage of representation invariants (rep invariants) in stack data structures.
  - Consists of a correct implementation (`Stack.java`) and a buggy variant (`StackBuggy.java`), both supported by structured test classes.
  - Designed to clarify rep invariant enforcement and fault isolation via targeted test cases.
  - Developed for CS 310: Software Specification Construction at George Mason University under Prof. Zhong.

## *Tech Stack*:

  - Language: Java (OpenJDK 11+)  
  - IDE: Eclipse (local development and execution)  
  - Testing Framework: JUnit 4.x  
  - Version Control: Project managed locally (no remote repository)  
  - Input/Output: Manual driver classes and standard console output  

## *Features Implemented*:

  - Stack operations: push, pop, peek, and isEmpty for bounded integer stacks  
  - Defensive programming: explicit rep invariant checks in constructors and mutators  
  - Fault injection: `StackBuggy` class deliberately violates key invariants  
  - Comprehensive testing: JUnit-based test cases and custom driver programs  
  - Driver verification: `StackTester.java` and `StackBuggyTester.java` include standard execution traces  

## *Key Concepts*:

  - Representation Invariants and Abstraction Barriers  
  - Class Invariants and Modular Correctness  
  - Unit Testing and Defensive Constructors  
  - Regression Testing via Behavioral Comparison  
  - Software Fault Isolation and Bug Exposure  

## *Files*:

  - `Stack.java` – correct bounded stack with enforced rep invariants  
  - `StackBuggy.java` – faulty stack with deliberate invariant violations  
  - `StackTester.java` – interactive console test driver for `Stack`  
  - `StackBuggyTester.java` – interactive test driver for `StackBuggy`  
  - `AllTests.java` – aggregates all JUnit test cases for automated testing  

## *Functions & Classes*:

  **Stack.java**  
  - `Stack(int size)` — constructs a bounded stack and validates size rep invariant  
  - `void push(int item)` — adds item if stack is not full  
  - `int pop()` — removes and returns top item  
  - `int peek()` — returns top item without removing  
  - `boolean isEmpty()` — returns true if stack is empty  
  - `boolean repOk()` — validates internal state according to class invariants  
  - `String toString()` — returns string representation of stack contents  

  **StackBuggy.java**  
  - Same public interface as `Stack.java`  
  - Internal logic breaks key rep invariant assumptions (e.g., skips bounds checks)  

  **StackTester.java** / **StackBuggyTester.java**  
  - Provide CLI-based testing and standard test traces  
  - Compare expected vs. actual outputs for typical operations  

  **AllTests.java**  
  - Imports JUnit test cases and groups them for batch execution  

## *Licensing & Attribution*:

  - All classes authored by me  
  - May be shared privately with recruiters or interviewers upon request  
  - No external libraries or frameworks used beyond JUnit  

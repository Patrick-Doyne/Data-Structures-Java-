# *Set Wraps List Suite — Composition, Equality, and Cloning in Java*

## *Program Structure & Design*:

  - A Java-based suite exploring equality contracts and deep-copy semantics in custom set implementations.  
  - Composed of two independent modules: `IntSet` (with `IntSet2`) and `ImprovedSet` (with `DoubleSet`), each supported by targeted test classes.  
  - Designed to expose subtle violations of `equals()` transitivity and demonstrate robust cloning via deep-copy protocols.  
  - Emphasizes the importance of honoring the client’s point of view: although the internal structure may use lists, externally we must behave like a set—ensuring no duplicates, consistent equality, and predictable behavior.

## *Tech Stack*:

  - Language: Java (OpenJDK 11+)  
  - IDE: Eclipse (local development and execution)  
  - Testing Framework: JUnit 4.x  
  - Version Control: Project managed locally (no remote repository)  
  - Input/Output: Manual driver classes and standard console output  

## *Features Implemented*:

  - Set operations: add, contains, equals, and clone for integer and generic sets  
  - Fault injection: `IntSet2` introduces double values to break equality transitivity  
  - Deep-copy enforcement: `ImprovedSet` and `DoubleSet` use `DeepCopy` interface to ensure clone integrity  
  - Domain modeling: `Cat` and `Dog` classes illustrate structural equality and cloning behavior  
  - Comprehensive testing: JUnit-based test cases validate equality, hashCode, and clone correctness  

## *Key Concepts*:

  - Equality Contracts: Reflexivity, Symmetry, Transitivity  
  - Deep Copy vs. Shallow Copy in Object-Oriented Design  
  - Defensive Programming and Clone Safety  
  - Property-Based Testing and Behavioral Invariants  
  - Fault Isolation via Controlled Violations  
  - Client Perspective vs. Internal Representation  

## *Files*:

  - `IntSet.java` – basic integer set with naive equality logic  
  - `IntSet2.java` – extended set allowing doubles, breaks equality transitivity  
  - `TestIntSet.java` – JUnit tests for `IntSet` and `IntSet2` behavior  
  - `ImprovedSet.java` – generic set with deep-copy enforcement  
  - `DoubleSet.java` – subclass of `ImprovedSet` with type-specific constraints  
  - `Cat.java` / `Dog.java` – domain classes implementing `DeepCopy`  
  - `TestImprovedSet.java` – JUnit tests for cloning and equality in generic sets  

## *Functions & Classes*:

  **IntSet.java**  
  - `void add(int x)` — adds integer to set  
  - `boolean contains(int x)` — checks membership  
  - `boolean equals(Object o)` — compares set contents  
  - `int hashCode()` — returns hash based on contents  

  **IntSet2.java**  
  - Same interface as `IntSet`, but allows doubles  
  - Violates transitivity when mixed with `IntSet`  

  **ImprovedSet.java**  
  - `void add(E x)` — adds deep-copyable element  
  - `boolean contains(E x)` — checks membership  
  - `ImprovedSet<E> clone()` — returns deep copy of set  
  - `boolean equals(Object o)` — compares deep-copied contents  

  **DoubleSet.java**  
  - Subclass enforcing type-specific constraints  
  - Inherits all methods from `ImprovedSet`  

  **Cat.java** / **Dog.java**  
  - Implement `DeepCopy` interface  
  - Provide `clone()` and `equals()` methods for structural comparison  

  **TestIntSet.java** / **TestImprovedSet.java**  
  - Validate equality, hashCode, and clone behavior  
  - Include edge cases and contract violations  

## *Licensing & Attribution*:

  - All classes authored by me  
  - May be shared privately with recruiters or interviewers upon request  
  - No external libraries or frameworks used beyond JUnit

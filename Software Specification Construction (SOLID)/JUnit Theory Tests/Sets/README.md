# *IntSet Suite — Software Specification Construction*

## *Program Structure & Design*:

  - A Java-based suite demonstrating valid and problematic implementations of `equals` and `hashCode` methods in an integer set wrapper (`IntSet`).  
  - Consists of a single `IntSet.java` with multiple `equals` and `hashCode` options, a theory-based test class (`Tests.java`), and a suite runner (`AllTests.java`).  
  - Designed to illustrate the abstraction of a list as a mathematical set and to expose subtle contract violations via targeted property-based tests.  
  - Inspired by Ammann & Offutt’s Software Testing (Chapter 3); developed for educational purposes in software specification construction.

## *Tech Stack*:

  - Language: Java (OpenJDK 11+)  
  - IDE: Any Java-compatible IDE (Eclipse, IntelliJ, VS Code)  
  - Testing Framework: JUnit 4.x (Theories and Suite runners)  
  - Version Control: Project managed locally (no remote repository)  
  - Input/Output: Console I/O via standard output and JUnit reports

## *Features Implemented*:

  - IntSet abstraction over `List<Integer>` with two `equals` behaviors (ordered vs. unordered).  
  - Four `hashCode` strategies: default, constant zero, list-based, and set-consistent computation.  
  - Property-based tests using JUnit Theories for equality (`testEquals`) and hash code consistency (`testHashCode`).  
  - Data points include `null` and multiple `IntSet` instances containing the same elements in different orders.  
  - Aggregated test execution via a JUnit Suite runner (`AllTests.java`).

## *Key Concepts*:

  - Collection abstraction vs. implementation details  
  - equals contract: reflexivity, symmetry, transitivity  
  - hashCode contract and performance considerations  
  - Property-based testing for combinatorial input coverage  
  - Fault isolation and regression testing

## *Files*:

  - `IntSet.java` – wrapper class implementing multiple `equals` and `hashCode` options  
  - `Tests.java` – JUnit Theories test class for equality and hashCode contracts  
  - `AllTests.java` – JUnit Suite runner aggregating `Tests.java`

## *Functions & Classes*:

  **IntSet.java**  
  - `IntSet(List<Integer> els)` — constructs the set with provided elements  
  - `boolean equals(Object obj)` — two variants: list-based deepEquals and unordered set comparison  
  - `int hashCode()` — four options: default, constant, list-hash, set-consistent sum of element hashes  
  - `String toString()` — returns string representation of elements

  **Tests.java**  
  - `@DataPoints public static Object[] allEqual` — includes `null` and three `IntSet` instances with the same elements in different orders  
  - `@Theory public void testEquals(Object a, Object b)` — assumes non-null; asserts bidirectional equality  
  - `@Theory public void testHashCode(Object a, Object b)` — assumes non-null and equality; asserts hash code consistency

  **AllTests.java**  
  - `@RunWith(Suite.class) @SuiteClasses({Tests.class})` — aggregates `Tests.java`  
  - `public static void main(String[] args)` — invokes `junit.textui.TestRunner.run(suite())`  
  - `public static junit.framework.Test suite()` — adapts for JUnit3 runners and Ant

## *Licensing & Attribution*:

  - All classes and tests authored by me  
  - May be shared privately with recruiters or interviewers upon request  
  - No external libraries or frameworks used beyond JUnit 4.x  

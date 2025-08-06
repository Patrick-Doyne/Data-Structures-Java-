# *Equality and HashCode Contract Suite — Software Specification Construction*

## *Program Structure & Design*:

  - A Java-based suite demonstrating correct and incorrect implementations of Java’s `equals` and `hashCode` contracts in value classes.  
  - Consists of a base class (`Point.java`), a subclass with extra state (`ColorPoint.java`), a broken-hash example (`BrokenHash.java`), and property-based tests (`TheoryTests.java`), all aggregated by a JUnit suite runner (`AllTests.java`).  
  - Designed to illustrate object equality principles, Liskov-substitution pitfalls, and contract enforcement via targeted test cases.  
  - Developed as an educational demonstration of value-class design and contract verification.

## *Tech Stack*:

  - Language: Java (OpenJDK 11+)  
  - IDE: Any Java-compatible IDE (Eclipse, IntelliJ, VS Code)  
  - Testing Framework: JUnit 4.x (Theories and Suite runners)  
  - Version Control: Project managed locally (no remote repository)  
  - Input/Output: Console I/O via standard output

## *Features Implemented*:

  - Value-class equality in `Point.java`  
  - Subclass equality refinement in `ColorPoint.java`  
  - HashCode contract violation and fix in `BrokenHash.java`  
  - Symmetry, transitivity, and equals/hashCode consistency tests via JUnit Theories  
  - Aggregated test execution with a Suite runner (`AllTests.java`)

## *Key Concepts*:

  - equals contract: reflexivity, symmetry, transitivity  
  - hashCode contract and consistency with equals  
  - Liskov Substitution Principle and inheritance pitfalls  
  - Property-based testing for combinatorial coverage  
  - Defensive programming for object invariants

## *Files*:

  - `Point.java` – immutable 2D point with overridden `equals` and `toString`  
  - `ColorPoint.java` – extends Point with a `Color` field; refines equality and `toString`  
  - `BrokenHash.java` – demonstrates a missing `hashCode`, then provides a correct implementation (`x * 31 + y`)  
  - `TheoryTests.java` – JUnit Theories testing symmetry, transitivity, and equals/hashCode consistency  
  - `AllTests.java` – JUnit Suite runner aggregating TheoryTests; includes a `main()` for console execution

## *Functions & Classes*:

  **Point.java**  
  - `Point(int x, int y)` — initializes coordinates  
  - `boolean equals(Object o)` — compares `x` and `y` for equality  
  - `String toString()` — returns `"x y"` representation

  **ColorPoint.java**  
  - `ColorPoint(int x, int y, Color color)` — initializes base and color  
  - `boolean equals(Object o)` — ensures type match, calls `super.equals`, compares `color`  
  - `String toString()` — appends `color` to base representation

  **BrokenHash.java**  
  - `BrokenHash(int x, int y)` — initializes coordinates  
  - `boolean equals(Object a)` — compares `x` and `y` for equality  
  - `int hashCode()` — fixed implementation: `x * 31 + y`

  **TheoryTests.java**  
  - `@DataPoints public static final Point[] points` — `null`, `new Point(3,7)`, `new ColorPoint(3,7,Color.red)`, `new ColorPoint(3,7,Color.blue)`  
  - `@Theory testSymmetry(Object a, Object b)` — assumes non-null; asserts `a.equals(b) == b.equals(a)`  
  - `@Theory testTransitivity(Object a, Object b, Object c)` — assumes non-null and `a.equals(b)`, `b.equals(c)`; asserts `a.equals(c)`  
  - `@Theory testEqualAndHash(Object a, Object b)` — assumes non-null and `a.equals(b)`; asserts `a.hashCode() == b.hashCode()`

  **AllTests.java**  
  - `@RunWith(Suite.class) @SuiteClasses({TheoryTests.class})` — aggregates TheoryTests  
  - `public static void main(String[] args)` — invokes `junit.textui.TestRunner.run(suite())`  
  - `public static junit.framework.Test suite()` — adapts for JUnit3 runners and Ant

## *Licensing & Attribution*:

  - All classes and tests authored by me  
  - May be shared privately with recruiters or interviewers upon request  
  - No external libraries or frameworks used beyond JUnit 4.x  

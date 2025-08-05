
# Queue Selection Suite — Software Design and Execution Flow  
=============================================================================

## *Program Structure & Design*:

  - A modular Java suite exploring **progressive refinements to selection and queue-handling logic**.
  - Each class represents a **versioned iteration** of the same conceptual foundation — not components in a unified system.
  
    - `Chooser.java` — an early, structurally weak implementation loosely inspired by Liskov principles, with poor abstraction and rigid coupling.
    - `ImprovedChooser.java` — refactors the design by introducing **generics**, strategy injection, and cleaner separation of concerns.
    - `BoundedQueue.java` — decouples selection from storage and demonstrates how **bounded capacity and flexible queue management** strengthen runtime control.

  - Core emphasis placed on:
    - **Generics** — promoting type safety and extensibility without sacrificing readability.
    - **List interface over ArrayList** — supporting polymorphism, better testing practices, and collection abstraction.
    - **Strategic separation** — chooser logic, data handling, and orchestration live independently by design.
    - **Versioned evolution** — highlighting lessons learned across iterations rather than unified orchestration.

  - This design shows how poor architectural choices (rigid coupling, ignored abstraction barriers) can be addressed through **interface-based polymorphism, defensive programming**, and clearer separation of control/data logic.


## *Tech Stack*:
  - Language: Java (OpenJDK 11+)  
  - IDE: Eclipse (local dev & test runs)  
  - Input/Output: Console-driven demo via `Main.java`  
  - No external libraries or frameworks used  

## *Features Implemented*:
  - Queue operations: enqueue, dequeue, isFull, isEmpty, and size tracking  
  - Selection logic: strategy-based item selection from arbitrary collections  
  - Extensibility: interfaces allow swapping selection heuristics at runtime  
  - Demonstration: `Main.java` provides CLI or hardcoded execution scenarios  

## *Key Concepts*:
  - Data Abstraction and Modular Design  
  - Strategy Pattern and Runtime Configuration  
  - Bounded Queue Capacity and Defensive Programming  
  - Separation of Concerns across queue, chooser, and driver logic  

## *Files*:
  - `BoundedQueue.java` – queue implementation with fixed-capacity control  
  - `ImprovedChooser.java` – strategy-aware chooser with custom selection logic  
  - `Chooser.java` – abstract base logic for choosing elements  
  - `Main.java` – CLI entry point and driver orchestrator  

## *Functions & Classes*:
  **BoundedQueue.java**  
  - `enqueue(T item)` — inserts item if queue not full  
  - `dequeue()` — removes and returns front item  
  - `isFull()`, `isEmpty()` — capacity checks  
  - `size()` — reports item count  

  **ImprovedChooser.java**  
  - `chooseFrom(List<T>)` — returns best item based on strategy  
  - `setStrategy(Strategy)` — injects custom selection logic  
  - `reset()` — clears internal state  

  **Chooser.java**  
  - `choose(List<T>)` — generic selection logic  
  - `compare(T a, T b)` — comparator utility  

  **Main.java**  
  - `main(String[] args)` — initializes system and runs demo  

## *Licensing & Attribution*:
  - All components authored by me  
  - Developed as academic and recruiter-facing material  
  - Freely shareable upon request; local execution only  


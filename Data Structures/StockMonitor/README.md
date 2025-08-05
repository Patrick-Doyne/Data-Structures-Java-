## *Program Structure & Design*:

  - A Java-based stock span monitor built from scratch (no Java Collections API)  
  - Reads daily integer prices, computes each day’s span, and tracks the max span per price  
  - Developed for CS 310: Data Structures at George Mason University (Spring 2023)

## *Tech Stack*:

  - Language: Java (OpenJDK 11+)  
  - IDE: Eclipse (local compilation and testing)  
  - Input Format: CSV file parsing via basic Java I/O  
  - Testing: Manual testing via `testMain()` and debug tracing  
  - Code Quality: Checkstyle for formatting consistency, SpotBugs for basic static analysis  
  - Version Control: N/A (project managed locally)  

## *Features Implemented*:

  - File I/O: load integer prices from `stocks.csv` into a `ThreeTenDLList<Integer>`  
  - Span computation: use a `ThreeTenStack<StackItem>` for day–price records and a `ThreeTenDLList<Integer>` for spans  
  - Max-span tracking: maintain a `ThreeTenHashMap<Integer,Integer>` mapping price → max observed span  
  - Debug mode: print day, price, span, stack contents, span list, and remaining prices step-by-step  
  - Reporting: query an individual price’s max span and produce a `<price>:<maxSpan>` summary  
  - Robustness: handle empty inputs, nulls, and I/O exceptions

## *Key Concepts*:

  - Stock Span Algorithm (O(n) via LIFO stack)  
  - Generic, null-safe data structures with explicit invariants  
  - Doubly-linked list traversal (forward/backward)  
  - Hash map separate-chaining using custom DLList buckets  
  - Stack abstraction on top of linked list with reverse support

## *Files*:

  - `StockMonitor.java` – orchestrates file parsing, span logic, debug output, and summary  
  - `Node.java` – generic doubly-linked node (scaffold by Prof. Zhong)  
  - `ThreeTenDLList.java` – custom doubly-linked list with iterators  
  - `ThreeTenHashMap.java` – generic map with bucket chaining  
  - `ThreeTenStack.java` – LIFO stack backed by `ThreeTenDLList`  
  - `stocks.csv` (optional) – sample input of one integer price per line

## *Functions & Classes*:

  **StockMonitor.java**
  
  - `fileToPriceList(String filename) throws IOException`  
    - Reads each line or comma-separated integer and returns a `ThreeTenDLList<Integer>`.  
  - `int stepProcess(int day, int price)`  
    - Pops all `StackItem`s with price ≤ current, pushes `(day,price)`, appends span to `spanList`, updates `priceSpanMap`, and returns the span.  
  - `int reportMaxSpan(int price)`  
    - Looks up and returns the max span for `price` from `priceSpanMap`.  
  - `String spanRecordToString()`  
    - Iterates first-seen prices and builds a space-separated `<price>:<maxSpan>` string.  
  - `void runProgram(String filename, boolean debug) throws IOException`  
    - Loads prices, initializes data structures, processes each day (with optional debug pauses), and prints final results.  
  - `void testMain()`  
    - Runs built-in tests on each data structure and key methods using hard-coded sample data.  

  *Inner Class: StackItem (private static):*
  
  - `StackItem(int day, int price)` — constructs a day–price pair  
  - `int getDay()` — returns stored day  
  - `int getPrice()` — returns stored price  
  - `String toString()` — returns `"<day,price>"`

  **Node.java** 
  
  - `Node(T data)` — initializes node with `data`, null `next`/`prev`  
  - `T getData()` / `void setData(T data)` — access/update payload  
  - `Node<T> getNext()` / `void setNext(Node<T> next)` — access/update forward link  
  - `Node<T> getPrev()` / `void setPrev(Node<T> prev)` — access/update backward link  
  - `String toString()` — delegates to `data.toString()`

  **ThreeTenDLList.java**
  
  - `ThreeTenDLList()` — constructs empty list  
  - `int size()` — returns element count  
  - `T getFirst()` / `T getLast()` — peek head/tail or return null  
  - `void addFirst(T value)` / `void addLast(T value)` — insert at head/tail  
  - `T removeFirst()` / `T removeLast()` — remove and return head/tail or null  
  - `T remove(T value)` — remove first occurrence or return null  
  - `String listToString(int start)` — forward elements from index `start`  
  - `String listToStringBackward()` — backward elements from tail to head  
  - `Iterator<T> iterator()` / `Iterator<T> backwardIterator()` — forward/backward traversal  
  - `String listToString()` — forward list string

  **ThreeTenHashMap.java** 
  
  - `ThreeTenHashMap()` — initialize default bucket array (11 buckets)  
  - `int size()` — count of key–value pairs  
  - `void put(K key, V value)` — insert or update mapping  
  - `V get(K key)` — retrieve value or null if absent  
  - `V delete(K key)` — remove mapping and return value or null  
  - `String toString()` — show bucket contents
      
  *Private Helpers:*

  - `int getHash(K key)` — compute non-negative bucket index  
  - `int capacity()` — current bucket count 
    
  *Inner Class: Pair (private static)*  
  
  - `Pair(K key, V value)` — constructs key–value pair  
  - `K getKey()` / `V getValue()` — accessors  
  - `void setKey(K key)` / `void setValue(V value)` — mutators  
  - `String toString()` — returns `"<key,value>"`  
  - `boolean equals(Object o)` — true if keys match  
  - `int hashCode()` — key’s hash code

  **ThreeTenStack.java**  
  
  - `ThreeTenStack()` — constructs empty stack  
  - `void push(T item)` — add item on top  
  - `T pop()` — remove and return top or null  
  - `T peek()` — view top without removal or null  
  - `boolean isEmpty()` — true if no elements  
  - `String toString()` — list bottom→top  
  - `ThreeTenStack<T> reverseStack()` — return new stack with reversed order

## *Licensing & Attribution:*

  - All code (except `Node.java`) authored by the student for CS 310 under Prof. Zhong  
  - May be shared privately with recruiters or interviewers upon request  

# Graph Simulator - Modular Java Framework with GUI and Topological Sort

## *Program Structure & Design*:

- A Java-based modular graph simulator built from scratch, showcasing lifecycle-driven algorithms, adjacency-matrix graphs, indexed heaps, and live GUI visualization  
- Implements a clear start→cleanup→setup→do→finish pattern so each algorithm step drives both data updates and colorized rendering  
- Developed as a graduate-level software engineering portfolio project at George Mason University (Fall 2025)  

## *Tech Stack*:

- Language: Java (OpenJDK 11+)  
- Graph Library Compatibility: JUNG for factory-based node/edge creation  
- GUI: Swing/AWT for interactive simulation panels  
- Collections: Custom `WeissCollection` hierarchy (no use of built-in Java Collections API)  
- Testing: Manual validation via sample graphs and debug mode in `SimGUI`  
- Version Control: N/A (project managed locally)  

## *Features Implemented*:

- Unified base class `GraphComp` for ID management, ordering, and color state  
- `GraphNode` and `GraphEdge` with active/inactive highlighting and JUNG-compatible factories  
- `ThreeTenGraph`: full `DirectedGraph<GraphNode,GraphEdge>` API over an adjacency-matrix backbone  
- `WeissPriorityQueue`: indexed min-heap supporting efficient decrease-key operations  
- `WeissCollection` & `WeissAbstractCollection`: generic collection API with default bulk behaviors  
- `SimGUI`: controller binding algorithm ticks to repaint cycles, plus speed control and highlight utilities  
- Rich `toString()` and traversal outputs for logging, debugging, and recruiter-friendly snapshots  

## *Algorithms*:

- **Topological Sort (`TopologicalSort.java`)**  
  - Implements `ThreeTenAlg` lifecycle (start → cleanup → setupNextStep → doNextStep → finish)  
  - Stepwise removal of zero in-degree nodes via an indexed min-heap, live GUI highlighting, and rank assignment  

- **Depth-First Traversal (`ThreeTenGraph.java`)**  
  - Recursive DFT with component scanning (`startRound`), producing a global visitation order and optional active-state coloring  

## *Key Concepts*:

- Lifecycle pattern (start → step → finish) for algorithm extensibility and GUI integration  
- Adjacency-matrix vs. adjacency-list trade-offs for dense graph scenarios  
- Indexed heap for dynamic cost ordering and O(log n) key updates  
- Color cues to reinforce real-time algorithm progress  
- Factory pattern to decouple graph model construction from visualization backend  

## *Files*:

- `GraphComp.java`  
- `GraphNode.java`  
- `GraphEdge.java`  
- `ThreeTenGraph.java`  
- `ThreeTenAlg.java`  
- `TopologicalSort.java`  
- `WeissCollection.java`  
- `WeissAbstractCollection.java`  
- `WeissPriorityQueue.java`  
- `SimGUI.java`  

## *Functions & Classes*:

**GraphComp.java**  
- `getId()` — returns unique ID  
- `getColor()` / `setColor(Color)` — access/update visual state  
- `equals(Object)` / `compareTo(GraphComp)` / `hashCode()` — ID-based equality and ordering

**GraphNode.java**  
- `GraphNode(int)` — initializes node  
- `setActive()` / `unsetActive()` / `isActive()` — toggles highlighting  
- `setCost(int)` / `getCost()` — in-degree tracking  
- `setRank(int)` / `getRank()` — sort order assignment  
- `compareTo(GraphComp)` / `toString()` — heap compatibility and rendering  
- `getFactory()` — JUNG node factory

**GraphEdge.java**  
- `GraphEdge(int)` — constructs edge  
- `toString()` — renders edge ID  
- `getFactory()` — JUNG edge factory

**ThreeTenGraph.java**  
- Graph construction: `addVertex()`, `removeVertex()`, `addEdge()`, `removeEdge()`  
- Querying: `containsVertex()`, `containsEdge()`, `getVertices()`, `getEdges()`  
- Edge counts: `getEdgeCount()`, `getVertexCount()`, `getEdgeCount(EdgeType)`  
- Neighborhood logic: `getInEdges()`, `getOutEdges()`, `getPredecessors()`, `getSuccessors()`, `isNeighbor()`  
- Traversals: `depthFirstTraversal()`, `startRound()`, `makeListForDFT()`  
- Graph modeling: `getSource()`, `getDest()`, `getOpposite()`, `getEndpoints()`  
- Interface implementations: `getFactory()`, `getDefaultEdgeType()`, `toString()`

**ThreeTenAlg.java**  
- Lifecycle: `start()`, `cleanUpLastStep()`, `setupNextStep()`, `doNextStep()`, `finish()`  
- Optional: `simplify()`  
- Integration: `step()` (default runner), `reset(Graph)`  
- Validation: `isStarted()`, `graphEdgeType()`

**TopologicalSort.java**  
- Control: `start()`, `reset()`, `finish()`  
- Stepping: `setupNextStep()`, `doNextStep()`, `cleanUpLastStep()`  
- Core logic: `selectNext()`, `updateSuccessorCost()`, `highlightNext()`  
- Preprocessing: `simplify()`  
- Edge specification: `graphEdgeType()`

**WeissCollection.java & WeissAbstractCollection.java**  
- Core API: `add()`, `remove()`, `contains()`, `iterator()`, `size()`, `clear()`  
- Bulk operations: `addAll()`, `removeAll()`, `retainAll()`, `toArray()`, `equals()`, `hashCode()`, `toString()`

**WeissPriorityQueue.java**  
- Construction: `WeissPriorityQueue()`, with optional comparator  
- Heap ops: `add()`, `remove()`, `poll()`, `peek()`, `update()`, `clear()`, `size()`  
- Helpers: `compare()`, `buildHeap()`, `percolateDown()`, `doubleArray()`  
- Lookup: `getIndex()`, `get()`, `contains()`, `iterator()`, `getArray()`

**SimGUI.java**  
- Initialization: `SimGUI()`, `makeMenu()`, `makeGraphPanel()`, `makeAlgPanels()`, `makeBottomButtons()`  
- Simulation control: `simplify()`, `step()`, `resetAlg()`, `play()`  
- Utilities: `highlightNode()`, `highlightEdge()`, `getSidePanel()`, `getTopPanel()`  
- Entry point: `main(String[] args)`

## *Licensing & Attribution*:

Core simulator classes (`GraphComp.java`, `GraphNode.java`, `GraphEdge.java`, `ThreeTenAlg.java`, `SimGUI.java`) were authored by Katherine (Raven) Russell. The graph model (`ThreeTenGraph.java`) and topological sort (`TopologicalSort.java`) are by Patrick. Collection and heap structures (`WeissCollection.java`, `WeissAbstractCollection.java`, `WeissPriorityQueue.java`) draw inspiration from Mark Allen Weiss’s textbook. Portions of visualization leverage the JUNG framework and Swing/AWT. All code is available for private review by recruiters or academic collaborators upon request.

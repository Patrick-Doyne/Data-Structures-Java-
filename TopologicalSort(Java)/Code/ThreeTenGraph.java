import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.DirectedGraph;

import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.graph.util.EdgeType;

import org.apache.commons.collections15.Factory;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Class that represents a graph.
 * @author Patrick Doyne.
 *
 */
class ThreeTenGraph implements Graph<GraphNode,GraphEdge>, DirectedGraph<GraphNode,GraphEdge> {

	/**
	 * Max number of Nodes.
	 */
	private static final int MAX_NUMBER_OF_NODES = 200;

	/**
	 * Array that holds nodes.
	 */
	private GraphNode[] vertexList = null;
	/**
	 * 2D array that holds edges connected to nodes.
	 */
	private GraphEdge[][] matrix = null;

	/**
	 * Constructor.
	 */
	public ThreeTenGraph() {
		this.vertexList= new GraphNode[MAX_NUMBER_OF_NODES];
		this.matrix= new GraphEdge[MAX_NUMBER_OF_NODES][MAX_NUMBER_OF_NODES];
	}



	/**
	 * Returns a view of all edges in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees 
	 * about the ordering of the edges within the set.
	 * @return a Collection view of all edges in this graph.
	 */
	public Collection<GraphEdge> getEdges() {
		//O(n^2) where n is the max number of nodes in the graph
		LinkedList<GraphEdge> listOfEdges = new LinkedList<GraphEdge>();
		for(int i = 0 ; i < this.matrix.length; i++) {
			for(int j = 0 ; j < this.matrix[i].length; j++) {
				if(matrix[i][j]!=null && matrix[i][j].getId()>-998) {
					listOfEdges.add(matrix[i][j]);
				}
			}
		}
		return listOfEdges; //default return, remove or change as needed

	}

	/**
	 * Returns a view of all vertices in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees 
	 * about the ordering of the vertices within the set.
	 * @return a Collection view of all vertices in this graph.
	 */
	public Collection<GraphNode> getVertices() {
		LinkedList<GraphNode> listOfVerticies = new LinkedList<GraphNode>();

		for(int i = 0 ; i < this.vertexList.length ; i++) {
			if(this.vertexList[i]!=null && this.vertexList[i].getId()>-998) {
				listOfVerticies.add(this.vertexList[i]);
			}
		}

		return listOfVerticies; //default return, remove or change as needed


	}

	/**
	 * Returns the number of edges in this graph.
	 * @return the number of edges in this graph.
	 */
	public int getEdgeCount() {
		int count =0;
		for(int i = 0 ; i < this.matrix.length; i++) {
			for(int j = 0 ; j < this.matrix[i].length; j++) {
				if(matrix[i][j]!=null && matrix[i][j].getId()>-998) {
					count++;
				}
			}
		}
		return count; //default return, remove or change as needed

	}

	/**
	 * Returns the number of vertices in this graph.
	 * @return the number of vertices in this graph.
	 */
	public int getVertexCount() {
		//O(n) where n is the max number of vertices in the graph
		int count=0;
		for(int i = 0 ; i < this.vertexList.length ; i++) {
			if(this.vertexList[i]!=null && this.vertexList[i].getId()>-998) {
				count++;
			}
		}
		return count; //default return, remove or change as needed
	}


	/**
	 * Returns true if this graph's vertex collection contains vertex.
	 * Equivalent to getVertices().contains(vertex).
	 * @param vertex the vertex whose presence is being queried.
	 * @return true iff this graph contains a vertex vertex.
	 */
	public boolean containsVertex(GraphNode vertex) {
		if(vertex==null)return false;
		if(Arrays.asList(this.vertexList).contains(vertex))return true;
		return false; //default return, remove or change as needed

	}


	/**
	 * Returns a Collection view of the incoming edges incident to vertex
	 * in this graph.
	 * @param vertex    the vertex whose incoming edges are to be returned.
	 * @return  a Collection view of the incoming edges incident to vertex in this graph.
	 */
	public Collection<GraphEdge> getInEdges(GraphNode vertex) {

		LinkedList<GraphEdge> inEdges = new LinkedList<GraphEdge>();
		if(vertex==null)return inEdges;

		//get all the edges coming in
		for(int i =0;i<this.matrix.length;i++) {
			if(this.matrix[i][vertex.id]!=null && this.matrix[i][vertex.id].getId()>-998) {
				inEdges.add(this.matrix[i][vertex.id]);
			}
		}
		return inEdges; //default return, remove or change as needed


	}

	/**
	 * Returns a Collection view of the outgoing edges incident to vertex
	 * in this graph.
	 * @param vertex    the vertex whose outgoing edges are to be returned.
	 * @return  a Collection view of the outgoing edges incident to vertex in this graph.
	 */
	public Collection<GraphEdge> getOutEdges(GraphNode vertex) {
		LinkedList<GraphEdge> outEdges = new LinkedList<GraphEdge>();
		if(vertex==null)return outEdges;

		//get all the edges coming out
		for(int i =0;i<this.matrix[vertex.getId()].length;i++) {
			if(this.matrix[vertex.getId()][i]!=null && this.matrix[vertex.getId()][i].getId()>-998) {
				outEdges.add(this.matrix[vertex.id][i]);
			}
		}
		return outEdges; //default return, remove or change as needed
	}

	/**
	 * Returns the number of incoming edges incident to vertex.
	 * Equivalent to getInEdges(vertex).size().
	 * @param vertex    the vertex whose indegree is to be calculated.
	 * @return  the number of incoming edges incident to vertex.
	 */
	public int inDegree(GraphNode vertex) {
		if(vertex==null) {
			throw new IllegalArgumentException("Vertex cannot be null!");
		}
		int in = this.getInEdges(vertex).size();
		return in; //default return, remove or change as needed
	}

	/**
	 * Returns the number of outgoing edges incident to vertex.
	 * Equivalent to getOutEdges(vertex).size().
	 * @param vertex    the vertex whose outdegree is to be calculated.
	 * @return  the number of outgoing edges incident to vertex.
	 */
	public int outDegree(GraphNode vertex) {
		if(vertex==null) {
			throw new IllegalArgumentException("Vertex cannot be null!");
		}
		int out = this.getOutEdges(vertex).size();
		return out; //default return, remove or change as needed
	}


	/**
	 * Returns a Collection view of the predecessors of vertex 
	 * in this graph.  A predecessor of vertex is defined as a vertex v 
	 * which is connected to 
	 * vertex by an edge e, where e is an outgoing edge of 
	 * v and an incoming edge of vertex.
	 * @param vertex    the vertex whose predecessors are to be returned.
	 * @return  a Collection view of the predecessors of vertex in this graph.
	 */
	public Collection<GraphNode> getPredecessors(GraphNode vertex) {
		LinkedList<GraphNode> predecessors = new LinkedList<GraphNode>();
		if(vertex==null)return predecessors;
		for(int i =0;i<this.matrix.length;i++) {
			if(this.matrix[i][vertex.id]!=null && this.matrix[i][vertex.id].getId()>-998) {
				predecessors.add(this.vertexList[i]);
			}
		}
		return predecessors; //default return, remove or change as needed

	}

	/**
	 * Returns a Collection view of the successors of vertex 
	 * in this graph.  A successor of vertex is defined as a vertex v 
	 * which is connected to 
	 * vertex by an edge e, where e is an incoming edge of 
	 * v and an outgoing edge of vertex.
	 * @param vertex    the vertex whose predecessors are to be returned.
	 * @return  a Collection view of the successors of vertex in this graph.
	 */
	public Collection<GraphNode> getSuccessors(GraphNode vertex) {
		LinkedList<GraphNode> successors = new LinkedList<GraphNode>();
		if(vertex==null)return successors;
		for(int i =0;i<this.matrix[vertex.getId()].length;i++) {
			if(this.matrix[vertex.getId()][i]!=null&&this.matrix[vertex.getId()][i].getId()>-998) {
				successors.add(this.vertexList[i]);
			}
		}
		return successors; //default return, remove or change as needed
	}

	/**
	 * Returns true if v1 is a predecessor of v2 in this graph.
	 * Equivalent to v1.getPredecessors().contains(v2).
	 * @param v1 the first vertex to be queried.
	 * @param v2 the second vertex to be queried.
	 * @return true if v1 is a predecessor of v2, and false otherwise.
	 */
	public boolean isPredecessor(GraphNode v1, GraphNode v2) {
		if(v1==null||v2==null)return false;
		if(this.matrix[v1.getId()][v2.getId()]!=null&&this.matrix[v1.getId()][v2.getId()].getId()>-998)return true;
		return false; //default return, remove or change as needed
	}

	/**
	 * Returns true if v1 is a successor of v2 in this graph.
	 * Equivalent to v1.getSuccessors().contains(v2).
	 * @param v1 the first vertex to be queried.
	 * @param v2 the second vertex to be queried.
	 * @return true if v1 is a successor of v2, and false otherwise.
	 */
	public boolean isSuccessor(GraphNode v1, GraphNode v2) {
		if(v1==null||v2==null)return false;
		if(this.matrix[v2.getId()][v1.getId()]!=null && this.matrix[v2.getId()][v1.getId()].getId()>-998)return true;
		return false; //default return, remove or change as needed
	}

	/**
	 * Returns the collection of vertices which are connected to vertex
	 * via any edges in this graph.
	 * If vertex is connected to itself with a self-loop, then 
	 * it will be included in the collection returned.
	 * 
	 * @param vertex the vertex whose neighbors are to be returned.
	 * @return  the collection of vertices which are connected to vertex, or null if vertex is not present.
	 */
	public Collection<GraphNode> getNeighbors(GraphNode vertex) {
		LinkedList<GraphNode> neighbors = new LinkedList<GraphNode>();
		if(vertex==null||!this.containsVertex(vertex))return null;
		for(int i =0;i<this.matrix.length;i++) {
			if(this.matrix[i][vertex.getId()]!=null && !neighbors.contains(this.vertexList[i]) &&this.matrix[i][vertex.getId()].getId()>-998) {
				neighbors.add(this.vertexList[i]);
			}
			if(this.matrix[vertex.getId()][i]!=null && !neighbors.contains(this.vertexList[i]) &&this.matrix[vertex.getId()][i].getId()>-998) {
				neighbors.add(this.vertexList[i]);
			}
		}
		return neighbors; //default return, remove or change as needed		

	}


	/**
	 * Returns the number of vertices that are adjacent to vertex
	 * (that is, the number of vertices that are incident to edges in vertex's
	 * incident edge set).
	 * 
	 * <p>Equivalent to getNeighbors(vertex).size().
	 * @param vertex the vertex whose neighbor count is to be returned.
	 * @return the number of neighboring vertices.
	 */
	public int getNeighborCount(GraphNode vertex) {
		if(vertex==null) {
			throw new IllegalArgumentException("Vertex cannot be null!");
		}	
		return this.getNeighbors(vertex).size(); //default return, remove or change as needed
	}


	/**
	 * If directed_edge is a directed edge in this graph, returns the source; 
	 * otherwise returns null. 
	 * The source of a directed edge d is defined to be the vertex for which  
	 * d is an outgoing edge.
	 * directed_edge is guaranteed to be a directed edge if 
	 * its EdgeType is DIRECTED. 
	 * @param directedEdge is the edge in query.
	 * @return  the source of directedEdge if it is a directed edge in this graph, or null otherwise.
	 */
	public GraphNode getSource(GraphEdge directedEdge) {
		//O(n^2) where n is the max number of vertices in the graph
		if(this.getEdgeType(directedEdge)!= EdgeType.DIRECTED)return null;
		if(directedEdge==null)	return null;
		for(int i = 0 ; i < this.matrix.length;i++) {
			for(int j =0 ; j < this.matrix[i].length;j++) {
				if(this.matrix[i][j]!=null &&this.matrix[i][j].getId()>-998 && this.matrix[i][j].equals(directedEdge)) {
					return this.vertexList[i];
				}
			}
		}
		return null; //default return, remove or change as needed   	
	}

	/**
	 * If directed_edge is a directed edge in this graph, returns the destination; 
	 * otherwise returns null. 
	 * The destination of a directed edge d is defined to be the vertex 
	 * incident to d for which  
	 * d is an incoming edge.
	 * directed_edge is guaranteed to be a directed edge if 
	 * its EdgeType is DIRECTED. 
	 * @param directedEdge is the edge in query.
	 * @return  the destination of directed_edge if it is a directed edge in this graph, or null otherwise.
	 */
	public GraphNode getDest(GraphEdge directedEdge) {
		if(this.getEdgeType(directedEdge)!= EdgeType.DIRECTED)return null;
		if(directedEdge==null)	return null;

		for(int i = 0 ; i < this.matrix.length;i++) {
			for(int j =0 ; j < this.matrix[i].length;j++) {
				if(this.matrix[i][j]!=null&&this.matrix[i][j].getId()>-998 &&this.matrix[i][j].equals(directedEdge)) {
					return this.vertexList[j];
				}
			}
		}
		return null; //default return, remove or change as needed   	

	}


	/**
	 * Returns an edge that connects v1 to v2.
	 * @param v1 is first node in query.
	 * @param v2 is second node in query.
	 * @return  an edge that connects v1 to v2, or null if no such edge exists (or either vertex is not present).
	 * @see Hypergraph#findEdgeSet(Object, Object) .
	 */
	public GraphEdge findEdge(GraphNode v1, GraphNode v2) {
		//O(1) 
		if(v1==null||v2==null)return null;
		if(!this.containsVertex(v1) || !this.containsVertex(v2))return null;
		if(this.matrix[v1.getId()][v2.getId()]!=null && this.matrix[v1.getId()][v2.getId()].getId()>-998) {
			return this.matrix[v1.getId()][v2.getId()];
		}
		return null; //default return, remove or change as needed   	

	}

	/**
	 * Returns true if vertex and edge 
	 * are incident to each other.
	 * Equivalent to getIncidentEdges(vertex).contains(edge) and to
	 * getIncidentVertices(edge).contains(vertex).
	 * @param vertex is the vertex in query.
	 * @param edge is the edge in query.
	 * @return true if vertex and edge are incident to each other.
	 */
	public boolean isIncident(GraphNode vertex, GraphEdge edge) {
		//O(n) where n is the max number of vertices in the graph
		if(vertex==null|edge==null)return false;
		if(!this.containsVertex(vertex))return false;
		for(int i =0;i<this.matrix.length;i++) {
			if((this.matrix[i][vertex.getId()]!=null && this.matrix[i][vertex.getId()].getId()>-998) || (this.matrix[vertex.getId()][i]!=null &&this.matrix[vertex.getId()][i].getId()>-998)) {
				return true;
			}
		}
		return false; //default return, remove or change as needed

	}



	/**
	 * Adds edge e to this graph such that it connects.
	 * @param e the edge to be added.
	 * @param v1 the first vertex to be connected.
	 * @param v2 the second vertex to be connected.
	 * @return true if the add is successful, false otherwise.
	 * @see Hypergraph#addEdge(Object, Collection)
	 * @see #addEdge(Object, Object, Object, EdgeType)
	 */
	public boolean addEdge(GraphEdge e, GraphNode v1, GraphNode v2) {
		//O(n^2) where n is the max number of vertices in the graph
		if(e==null||v1==null||v2==null)return false;
		if(!this.containsVertex(v2) || !this.containsVertex(v1)) {
			throw new IllegalArgumentException("Must enter 2 verticies in this graph.");
		}
		if(this.matrix[v1.getId()][v2.getId()]!=null )return false;
		for(int i = 0 ; i < this.matrix.length;i++) {
			if(Arrays.asList(matrix[i]).contains(e)) {
				return false;
			}
		}
		if(v1.getId()==v2.getId())return false;

		this.matrix[v1.getId()][v2.getId()]= e;

		//default return, remove or change as needed
		return true;
	}

	/**
	 * Adds vertex to this graph.
	 * Fails if vertex is null or already in the graph.
	 * 
	 * @param vertex    the vertex to add.
	 * @return true if the add is successful, and false otherwise.
	 * @throws IllegalArgumentException if vertex is null.
	 */
	public boolean addVertex(GraphNode vertex) {
		//O(1) 
		if(vertex==null) {
			throw new IllegalArgumentException("Vertex cannot be null!");
		}
		if(this.containsVertex(vertex)) return false;

		this.vertexList[vertex.id] = vertex;
		//this.vertexList[vertex.id].setActive();

		return true; //default return, remove or change as needed
	}

	/**
	 * Removes edge from this graph.
	 * Fails if edge is null, or is otherwise not an element of this graph.
	 * 
	 * @param edge the edge to remove.
	 * @return true if the removal is successful, false otherwise.
	 */
	public boolean removeEdge(GraphEdge edge) {
		//O(n^2) where n is the max number of vertices in the graph
		if(edge==null)return false;

		for(int i = 0 ; i < this.matrix.length; i ++) {
			for(int j = 0; j < this.matrix[i].length; j++) {
				if(this.matrix[i][j]!=null&&this.matrix[i][j].equals(edge)) {
					this.matrix[i][j] = new GraphEdge(-999);
					return true;
				}
			}
		}
		return false; //default return, remove or change as needed


	}

	/**
	 * Removes vertex from this graph.
	 * As a side effect, removes any edges e incident to vertex if the 
	 * removal of vertex would cause e to be incident to an illegal
	 * number of vertices. Thus, for example, incident hyperedges are not removed, but 
	 * incident edges--which must be connected to a vertex at both endpoints--are removed.
	 * @param vertex the vertex to remove.
	 * @return true if the removal is successful, false otherwise.
	 */
	public boolean removeVertex(GraphNode vertex) {

		if(vertex==null||!this.containsVertex(vertex)) {
			return false;
		}
		LinkedList<GraphNode> in = null;
		LinkedList<GraphNode> out = null;
		boolean vertexRemoved=false;
		for(int i = 0 ; i < this.vertexList.length;i++) {
			if(this.vertexList[i]!=null&&this.vertexList[i].equals(vertex)) {
				in = (LinkedList<GraphNode>) this.getPredecessors(this.vertexList[i]);
				out = (LinkedList<GraphNode>) this.getSuccessors(this.vertexList[i]);
				this.vertexList[i]= new GraphNode(-999);
				vertexRemoved=true;
				if(this.getPredecessorCount(vertex)+this.getSuccessorCount(vertex)==0) {
					return true;
				}
				break;
			}
		}
		if(vertexRemoved) {
			int inSize = in.size();
			int outSize = out.size();
			for(int i = 0 ;  i <inSize; i++) {
				if(in.peek()!=null&&in.peek().getId()>-998) {
					this.matrix[in.pop().getId()][vertex.getId()] = new GraphEdge(-999);
				}
			}
			for(int i = 0 ; i < outSize;i++) {
				if(out.peek()!=null && out.peek().getId()>-998) {
					this.matrix[vertex.getId()][out.pop().getId()] = new GraphEdge(-999);
				}
			}
			return true;
		}
		return false; //default return, remove or change as needed

	}



	/**
	 *  Returns a string of the depth first traversal of the graph. 
	 *  We may need to perform depth first traversal for multiple rounds until all nodes
	 *  are visited.  Always pick the lowest ID vertex that has not been visited to start
	 *  a round and all nodes reachable from that starting node should be traversed in 
	 *  this round before we move on to the next round. 
	 *  
	 *  @return a string representation of the depth first traversal, or an empty string if the graph is empty.
	 */


	public String depthFirstTraversal() {
		LinkedList<GraphNode> stack = new LinkedList<GraphNode>();
		GraphNode [] visited = new GraphNode [this.vertexList.length];
		int size = 0;
		StringBuilder ans = new StringBuilder();	

		ans = startRound(ans,stack,visited,size);

		return ans.toString().trim(); //default return, remove or change as needed


	}
	/**
	 * Makes the list for this round of the traversal.
	 * @param s is the traversal to this point.
	 * @param lastID is the id of the last node.
	 * @param stack is a list of the nodes.
	 * @param visited array of visited nodes.
	 * @param size is the size of the stack.
	 * @return the full string of the traversal for this round.
	 */
	public StringBuilder makeListForDFT(StringBuilder s, int lastID, LinkedList<GraphNode> stack, GraphNode[] visited, int size) {
		int min = 999;
		GraphNode next=null;
		boolean nextFound=false;
		//finds next vertex
		for(int i = 0 ; i < this.matrix.length; i++) {
			if(this.matrix[lastID][i]!=null && this.vertexList[i].getId()<min && !Arrays.asList(visited).contains(this.vertexList[i])&&this.vertexList[i].getId()>-998) {
				min=this.vertexList[i].getId();
				next = this.vertexList[i];
				nextFound=true;
			}
		}
		//if found add and look for next
		if(nextFound) {
			visited[size] = next;
			size++;
			s.append(min+" ");
			stack.addFirst(next);
			s = makeListForDFT(s,min,stack,visited,size);
		}
		//if not found go back a vertex and look for another until we cant find more.
		else if(stack.size()!=0) {
			GraphNode backTrack = stack.pop();
			s = makeListForDFT(s,backTrack.getId(),stack,visited,size);
		}
		//otherwise start next round
		else if(size!=this.getVertexCount()) {
			s= startRound(s,stack,visited,size);
		}


		return s;
	}
	/**
	 * Starts the next round of the traversal.
	 * @param s is the traversal to this point.
	 * @param stack is a list of the nodes.
	 * @param visited array of visited nodes.
	 * @param size is the size of the stack.
	 * @return collective traversal so far.
	 */
	public StringBuilder startRound (StringBuilder s, LinkedList<GraphNode> stack, GraphNode[] visited, int size) {
		GraphNode start =  null;
		int min =999;

		for (int i =0 ; i < this.vertexList.length; i++) {
			if(this.vertexList[i]!=null &&this.vertexList[i].getId()>-998&& this.vertexList[i].getId() < min && !Arrays.asList(visited).contains(this.vertexList[i])) {
				min = this.vertexList[i].getId();
				start= this.vertexList[i];
			}
		}

		if(start!=null) {
			visited[size] = start;
			size++;
			stack.addFirst(start);
			s.append(start.getId()+" ");

			s = makeListForDFT(s, stack.peek().getId(), stack, visited, size);
		}

		return s;
	}


	/**
	 * Returns the number of edges incident to vertex.  
	 * @param vertex the vertex whose degree is to be returned.
	 * @return the degree of this node.
	 * @see Hypergraph#getNeighborCount(Object)
	 */
	public int degree(GraphNode vertex) {
		return inDegree(vertex) + outDegree(vertex);
	}

	/**
	 * Returns true if v1 and v2 share an incident edge.
	 * Equivalent to getNeighbors(v1).contains(v2).
	 * 
	 * @param v1 the first vertex to test.
	 * @param v2 the second vertex to test.
	 * @return true if v1 and v2 share an incident edge.
	 */
	public boolean isNeighbor(GraphNode v1, GraphNode v2) {
		return (findEdge(v1, v2) != null || findEdge(v2, v1)!=null);
	}

	/**
	 * Returns the endpoints of edge as a Pair.
	 * @param edge the edge whose endpoints are to be returned.
	 * @return the endpoints (incident vertices) of edge.
	 */
	public Pair<GraphNode> getEndpoints(GraphEdge edge) {

		if (edge==null) return null;

		GraphNode v1 = getSource(edge);
		if (v1==null)
			return null;

		GraphNode v2 = getDest(edge);
		if (v2==null)
			return null;

		return new Pair<>(v1, v2);
	}


	/**
	 * Returns the collection of edges in this graph which are connected to vertex.
	 * 
	 * @param vertex the vertex whose incident edges are to be returned.
	 * @return  the collection of edges which are connected to vertex, or null if vertex is not present.
	 */
	public Collection<GraphEdge> getIncidentEdges(GraphNode vertex) {
		LinkedList<GraphEdge> ret = new LinkedList<>();
		ret.addAll(getInEdges(vertex));
		ret.addAll(getOutEdges(vertex));
		return ret;
	}

	/**
	 * Returns the collection of vertices in this graph which are connected to edge.
	 * Note that for some graph types there are guarantees about the size of this collection
	 * (i.e., some graphs contain edges that have exactly two endpoints, which may or may 
	 * not be distinct).  Implementations for those graph types may provide alternate methods 
	 * that provide more convenient access to the vertices.
	 * 
	 * @param edge the edge whose incident vertices are to be returned.
	 * @return  the collection of vertices which are connected to edge, or null if edge is not present.
	 */
	public Collection<GraphNode> getIncidentVertices(GraphEdge edge) {
		Pair<GraphNode> p = getEndpoints(edge);
		LinkedList<GraphNode> ret = new LinkedList<>();
		ret.add(p.getFirst());
		ret.add(p.getSecond());
		return ret;
	}


	/**
	 * Returns true if this graph's edge collection contains edge.
	 * Equivalent to getEdges().contains(edge).
	 * @param edge the edge whose presence is being queried.
	 * @return true iff this graph contains an edge edge.
	 */
	public boolean containsEdge(GraphEdge edge) {
		return (getEndpoints(edge) != null);
	}

	/**
	 * Returns the collection of edges in this graph which are of type edge_type.
	 * @param edgeType the type of edges to be returned.
	 * @return the collection of edges which are of type edge_type, or null if the graph does not accept edges of this type.
	 * @see EdgeType
	 */
	public Collection<GraphEdge> getEdges(EdgeType edgeType) {
		if(edgeType == EdgeType.DIRECTED) {
			return getEdges();
		}
		return null;
	}

	/**
	 * Returns the number of edges of type edge_type in this graph.
	 * @param edgeType the type of edge for which the count is to be returned.
	 * @return the number of edges of type edge_type in this graph.
	 */
	public int getEdgeCount(EdgeType edgeType) {
		if(edgeType == EdgeType.DIRECTED) {
			return getEdgeCount();
		}
		return 0;
	}

	/**
	 * Returns the number of predecessors that vertex has in this graph.
	 * Equivalent to vertex.getPredecessors().size().
	 * @param vertex the vertex whose predecessor count is to be returned.
	 * @return  the number of predecessors that vertex has in this graph.
	 */
	public int getPredecessorCount(GraphNode vertex) {
		return inDegree(vertex);
	}

	/**
	 * Returns the number of successors that vertex has in this graph.
	 * Equivalent to vertex.getSuccessors().size().
	 * @param vertex the vertex whose successor count is to be returned.
	 * @return  the number of successors that vertex has in this graph.
	 */
	public int getSuccessorCount(GraphNode vertex) {
		return outDegree(vertex);
	}

	/**
	 * Returns the vertex at the other end of edge from vertex.
	 * (That is, returns the vertex incident to edge which is not vertex.)
	 * @param vertex the vertex to be queried.
	 * @param edge the edge to be queried.
	 * @return the vertex at the other end of edge from vertex.
	 */
	public GraphNode getOpposite(GraphNode vertex, GraphEdge edge) {
		if (getSource(edge).equals(vertex)){
			return getDest(edge);
		}
		else if (getDest(edge).equals(vertex)){
			return getSource(edge);
		}
		else
			return null;			
	}

	/**
	 * Returns all edges that connects v1 to v2.
	 * @param v1 the first vertex to be connected.
	 * @param v2 the second vertex to be connected.
	 * @return  a collection containing all edges that connect v1 to v2, or null if either vertex is not present.
	 * @see Hypergraph#findEdge(Object, Object)
	 */
	public Collection<GraphEdge> findEdgeSet(GraphNode v1, GraphNode v2) {
		GraphEdge edge = findEdge(v1, v2);
		if(edge == null) {
			return null;
		}

		LinkedList<GraphEdge> ret = new LinkedList<>();
		ret.add(edge);
		return ret;

	}

	/**
	 * Returns true if vertex is the source of edge.
	 * Equivalent to getSource(edge).equals(vertex).
	 * @param vertex the vertex to be queried.
	 * @param edge the edge to be queried.
	 * @return true iff vertex is the source of edge.
	 */
	public boolean isSource(GraphNode vertex, GraphEdge edge) {
		return getSource(edge).equals(vertex);
	}

	/**
	 * Returns true if vertex is the destination of edge.
	 * Equivalent to getDest(edge).equals(vertex).
	 * @param vertex the vertex to be queried.
	 * @param edge the edge to be queried.
	 * @return true iff vertex is the destination of edge.
	 */
	public boolean isDest(GraphNode vertex, GraphEdge edge) {
		return getDest(edge).equals(vertex);
	}

	/**
	 * Adds edge e to this graph such that it connects 
	 * vertex v1 to v2.
	 * @param e the edge to be added.
	 * @param v1 the first vertex to be connected.
	 * @param v2 the second vertex to be connected.
	 * @param edgeType the type to be assigned to the edge.
	 * @return true if the add is successful, false otherwise.
	 * @see Hypergraph#addEdge(Object, Collection)
	 * @see #addEdge(Object, Object, Object)
	 */
	public boolean addEdge(GraphEdge e, GraphNode v1, GraphNode v2, EdgeType edgeType) {
		//NOTE: Only directed edges allowed

		if(edgeType == EdgeType.UNDIRECTED) {
			throw new IllegalArgumentException();
		}

		return addEdge(e, v1, v2);
	}

	/**
	 * Adds edge to this graph.
	 * @param edge is the edge in query.
	 * @param vertices are the vertices in query.
	 * @return true if the add is successful, and false otherwise.
	 * @throws IllegalArgumentException if edge or vertices is null, or if a different vertex set in this graph is already connected by edge, or if vertices are not a legal vertex set for edge .
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(GraphEdge edge, Collection<? extends GraphNode> vertices) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}

		GraphNode[] vs = (GraphNode[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1]);
	}

	/**
	 * Adds edge to this graph with type edge_type.
	 * @param edge is the edge in query.
	 * @param vertices is the vertices in query.
	 * @param edgeType is the type of edge edge is.
	 * @return true if the add is successful, and false otherwise.
	 * @throws IllegalArgumentException if edge or vertices is null, or if a different vertex set in this graph is already connected by edge,  or if vertices are not a legal vertex set for edge.
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(GraphEdge edge, Collection<? extends GraphNode> vertices, EdgeType edgeType) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}

		GraphNode[] vs = (GraphNode[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1], edgeType);
	}

	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE EXCEPT FOR FIXING JAVADOC
	//********************************************************************************

	/**
	 * Returns a {@code Factory} that creates an instance of this graph type.
	 * @param <V> Graph Node.
	 * @param <E> Graph Edge.
	 * @return a {@code Factory} that creates an instance of this graph type.
	 */

	public static <V,E> Factory<Graph<GraphNode,GraphEdge>> getFactory() { 
		return new Factory<Graph<GraphNode,GraphEdge>> () {
			@SuppressWarnings("unchecked")
			public Graph<GraphNode,GraphEdge> create() {
				return (Graph<GraphNode,GraphEdge>) new ThreeTenGraph();
			}
		};
	}



	/**
	 * Returns the edge type of edge in this graph.
	 * @param edge is the edge in query.
	 * @return the EdgeType of edge, or null if edge has no defined type.
	 */
	public EdgeType getEdgeType(GraphEdge edge) {
		return EdgeType.DIRECTED;
	}

	/**
	 * Returns the default edge type for this graph.
	 * 
	 * @return the default edge type for this graph.
	 */
	public EdgeType getDefaultEdgeType() {
		return EdgeType.DIRECTED;
	}

	/**
	 * Returns the number of vertices that are incident to edge.
	 * @param edge the edge whose incident vertex count is to be returned.
	 * @return the number of vertices that are incident to edge.
	 */
	public int getIncidentCount(GraphEdge edge) {
		return 2;
	}

	/**
	 *  {@inheritDoc}
	 */
	public String toString() {
		return depthFirstTraversal();
	}


}


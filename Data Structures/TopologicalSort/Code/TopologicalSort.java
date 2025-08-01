//TODO: Implement the required methods and add JavaDoc for them.
//Remember: Do NOT add any additional instance or class variables (local variables are ok)
//and do NOT alter any provided methods or change any method signatures!

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

import java.awt.Color;

import javax.swing.JPanel;

import java.util.Collection;
import java.util.NoSuchElementException;

import java.util.LinkedList;

/**
 * Simulates a topological sort.
 * @author Patrick Doyne.
 *
 */
class TopologicalSort implements ThreeTenAlg {
	/**
	 *  The graph the algorithm will run on.
	 */
	Graph<GraphNode, GraphEdge> graph;
	
	/**
	 *  The priority queue of nodes for the algorithm.
	 */
	WeissPriorityQueue<GraphNode> pqueue;
	
	/**
	 *  The sorted list of nodes for the algorithm.
	 */
	LinkedList<GraphNode> queue;
	
	/**
	 *  Whether or not the algorithm has been started.
	 */
	private boolean started = false;

	/**
	 * The max rank that has been assigned in the current sorting.
	 */
	private int maxRank;
	
	/**
	 *  The color when a node has "no color".
	 */
	public static final Color COLOR_NONE_NODE = Color.WHITE;
	
	/**
	 *  The color when an edge has "no color".
	 */
	public static final Color COLOR_NONE_EDGE = Color.BLACK;
		
	/**
	 *  The color when a node is inactive.
	 */
	public static final Color COLOR_INACTIVE_NODE = Color.LIGHT_GRAY;

	/**
	 *  The color when an edge is inactive.
	 */
	public static final Color COLOR_INACTIVE_EDGE = Color.LIGHT_GRAY;
	
	/**
	 *  The color when a node is highlighted.
	 */
	public static final Color COLOR_HIGHLIGHT = new Color(255,204,51);
	
	/**
	 *  The color when a node is in warning.
	 */
	public static final Color COLOR_WARNING = new Color(255,51,51);

			
	/**
	 *  {@inheritDoc}
	 */
	public EdgeType graphEdgeType() {
		return EdgeType.DIRECTED;
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public void reset(Graph<GraphNode, GraphEdge> graph) {
		this.graph = graph;
		started = false;
		queue = null;
		pqueue = null;		
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public boolean isStarted() {
		return started;
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public void start() {
		started = true;
		
		//create an empty list
		queue = new LinkedList<>();
		
		//create an empty priority queue
		pqueue = new WeissPriorityQueue<>();
		
		//no nodes sorted yet
		maxRank = -1;
			
		for(GraphNode v : graph.getVertices()) {
			
			//clear rank;
			v.setRank(-1);
			
			//Set the cost of each node to be its degree
			v.setCost(graph.inDegree(v));
			
			//Set each node to be active
			//This enables the display of cost for the node
			v.setActive();
		
			//add node into priority queue
			pqueue.add(v);
		}
		
		//highlight the node with best priority 
		highlightNext();
			
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public void finish() {
	
		// Sorting completed. Set all edges back to "no color".
		for (GraphEdge e: graph.getEdges()){
			e.setColor(COLOR_NONE_EDGE);
		}

		//Set all sorted nodes back to "no special color".
		for(GraphNode v : graph.getVertices()) {			
			if (v.color.equals(COLOR_INACTIVE_NODE))
				v.setColor(COLOR_NONE_NODE);
		}
				
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public void cleanUpLastStep() {
		// Unused. Required by the interface.		
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public boolean setupNextStep() {
	
		// no more nodes, done with simulation. 
		if (pqueue.size() == 0){
			return false;
		}
				
		//Return true to indicate more steps to continue.
		return true;
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public void doNextStep() {
	
		//find and process next node
		GraphNode minNode = selectNext();
				
		//update successor info as needed
		updateSuccessorCost(minNode);
		
		//highlight next node with best priority 
		highlightNext();

	
	}
	/**
	 * Selects next node for sorting.
	 * @return node to be sorted.
	 */
	public GraphNode selectNext(){
		//removes the node from the hash map and the pqueue
		GraphNode next = this.pqueue.remove();
		int cost = next.getCost();

		// 2. Check the cost of this node. 
		//    - if the cost / number of active incoming edges is zero: 
		if(cost==0) {
		 //         1) add it to the end of the sorted list (queue), 
			this.queue.add(next);
		 //	       2) set its rank to indicate the sorted order, 
			//unsure 
			next.setRank(3);
		 //         3) set it to be inactive and change its color to be COLOR_INACTIVE_NODE.
			next.unsetActive();
			next.setColor(COLOR_INACTIVE_NODE);
		}
		
		//     - if the cost / number of active incoming edges is not zero, 
		//       it means this node cannot be topologically sorted.  
		//       Just change its color to be COLOR__WARNING.
		else if(cost!=0) {
			next.setColor(COLOR_WARNING);
		}
		// 3. Return the min node.  If priority queue is empty, return null.
		if(this.pqueue.size()==0) return null;
		
		return next;
			
	}
	/**
	 * Updates the cost for successors of minNode.
	 * @param minNode is the node to be updated
	 */
	public void updateSuccessorCost(GraphNode minNode){
		if(minNode==null) return;
		if(minNode.getCost()==0) {
			LinkedList<GraphNode> successors = (LinkedList<GraphNode>) this.graph.getSuccessors(minNode);
			for(int i = 0; i <= successors.size(); i++) {
				if(successors.peek()!=null){
					GraphNode tmp = successors.pop();
					tmp.setCost(tmp.getCost()-1);
				}
			}
		
			LinkedList<GraphEdge> outEdges = (LinkedList<GraphEdge>) this.graph.getOutEdges(minNode);
			for(int i = 0; i <= outEdges.size(); i++) {
				if(outEdges.peek()!=null){
					GraphEdge tmp = outEdges.pop();
					tmp.setColor(COLOR_INACTIVE_EDGE);
				}
			}
		}
	}	
	/**
	 * Highlights the next node yellow.
	 */
	public void highlightNext(){
		if(this.pqueue.size()==0)return;
		GraphNode next = this.pqueue.remove();
		this.pqueue.add(next);
		next = this.pqueue.element();
		next.setColor(COLOR_HIGHLIGHT);	
	}
	/**
	 * Removes all edges from lower Id nodes to higher Id nodes.
	 * @return true if simplification worked.
	 */
	public boolean simplify(){
		if(this.started)return false;
		
		LinkedList<GraphNode> allNodes = (LinkedList<GraphNode>) this.graph.getVertices();
		int sizeN = allNodes.size();
		
		for(int i = 0 ; i < sizeN; i++) {
			GraphNode tmp = allNodes.pop();
			LinkedList<GraphEdge> allOutEdges = (LinkedList<GraphEdge>) this.graph.getOutEdges(tmp);
			int sizeE = allOutEdges.size();
			
			for(int j = 0 ; j < sizeE;j++) {
				GraphEdge tmpE = allOutEdges.pop();
				if(this.graph.getDest(tmpE).getId()>tmp.getId()) {
					this.graph.removeEdge(tmpE);
				}
			}

		}
		return true;
	}
	
}
package graph;

import java.util.Collection;
import java.util.HashMap;

public class Node<V, L> {
	private V item = null;
	private boolean isVisited = false;
	private HashMap<Integer, AbstractEdge<V, L>> edges = null;
	
	public Node(V item) {
		this.item = item;
		this.edges = new HashMap<>();
	}
	
	/**
	 * Get the item
	 * @return the item V
	 */
	public V getItem() {
		return this.item;
	}
	
	/**
	 * Get is visited
	 * @return if the node has been visited
	 */
	public boolean getIsVisited() {
		return this.isVisited;
	}
	
	/**
	 * Set is visited
	 */
	public void setIsVisited(boolean value) {
		this.isVisited = value;
	}
	
	/**
	 * Add a new edge to the node hashmap
	 * @param edgeToBeAdded
	 * @return true if the edge has been added, otherwise false
	 */
	public boolean addEdge(AbstractEdge<V, L> edgeToBeAdded) throws GraphException {
		if (edgeToBeAdded == null)
			throw new GraphException("addEdge: the edge can't be null");

		if (containsEdgeWith(edgeToBeAdded.getEnd()))
			return false;
		
		edges.put(edgeToBeAdded.getEnd().hashCode(), edgeToBeAdded);
		return true;
	}
	
	/**
	 * Check if the edge is already in the node hashmap
	 * @param endNode
	 * @return true if the edge is contained in the hashmap, otherwise false
	 */
	public boolean containsEdgeWith(V endNode) throws GraphException {
		if (endNode == null)
			throw new GraphException("containsEdgeWith: the node can't be null");

		return edges.containsKey(endNode.hashCode());
	}

	/**
	 * Remove a defined edge
	 * @param b
	 * @return
	 */
	public boolean removeEdgeWith(V b) throws GraphException {
		if (b == null)
			throw new GraphException("removeEdgeWith: the value can't be null");

		AbstractEdge<V, L> removedEdge = edges.remove(b.hashCode());
		return removedEdge != null;
	}
	
	/**
	 * Remove all edges of the node
	 * @return true
	 */
	public boolean removeAllEdges() {
		edges.clear();
		return true;
	}
	
	/**
	 * Count how many edges the node has
	 * @return the number of edges
	 */
	public Integer edgesSize() {
		return edges.size();
	}
	
	/**
	 * Return the edge
	 * @return AbstractEdge<V, L>
	 */
	public AbstractEdge<V, L> getEdgeWith(V b) throws GraphException {
		if (b == null)
			throw new GraphException("getEdgeWith: the value can't be null");

		return edges.get(b.hashCode());
	}
	
	/**
	 * Return the list of edges
	 * @return collection of edges
	 */
	public Collection<AbstractEdge<V, L>> getEdges() {
		return edges.values();
	}
}

package graph;

import java.util.Collection;
import java.util.HashMap;

public class Vertex<V, L> {
	private V item = null;
	private boolean isVisited = false;
	private HashMap<Integer, AbstractEdge<V, L>> edges = null;
	
	public Vertex(V item) {
		this.item = item;
		this.edges = new HashMap<Integer, AbstractEdge<V, L>>();
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
	 * @return if the vertex has been visited
	 */
	public boolean getIsVisisted() {
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
	public boolean addEdge(AbstractEdge<V, L> edgeToBeAdded) {
		if (edgeToBeAdded == null || containsEdge(edgeToBeAdded.getEnd()))
			return false;
		
		edges.put(edgeToBeAdded.getEnd().hashCode(), edgeToBeAdded);
		return true;
	}
	
	/**
	 * Check if the edge is already in the node hashmap
	 * @param endVertex
	 * @return true if the edge is contained in the hashmap, 
	 * otherwise false also if the endVertex is null
	 */
	public boolean containsEdge(V endVertex) {
		if (endVertex == null) return false;
		
		return edges.containsKey(endVertex.hashCode());
	}

	/**
	 * Remove a defined edge
	 * @param b
	 * @return
	 */
	public boolean removeEdge(V b) {
		if (b == null) return false;
		
		AbstractEdge<V, L> removedEdge = edges.remove(b.hashCode());
		return removedEdge != null;
	}
	
	/**
	 * Remove all edges of the vertex
	 * @return true
	 */
	public boolean removeAllEdges() {
		edges.clear();
		return true;
	}
	
	/**
	 * Count how many edges the vertex has
	 * @return the number of edges
	 */
	public Integer edgesSize() {
		return edges.size();
	}
	
	/**
	 * Return the edge
	 * @return AbstractEdge<V, L>
	 */
	public AbstractEdge<V, L> getEdge(V b) {
		if (b == null) return null;
		
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

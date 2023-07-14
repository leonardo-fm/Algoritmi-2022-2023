package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

public class Graph<V, L> implements AbstractGraph<V, L> {
	private HashMap<Integer, Vertex<V, L>> graph = null; 
	private boolean directed = false;
	private boolean labelled = false;
	
	public Graph(boolean directed, boolean labelled) {
		this.directed = directed; 
		this.labelled = labelled;
		this.graph = new HashMap<Integer, Vertex<V, L>>();
	}

	/**
	 * @return true if the graph is directed otherwise false
	 */
	@Override
	public boolean isDirected() {
		return directed;
	}

	/**
	 * @return true if the graph is labelled otherwise false
	 */
	@Override
	public boolean isLabelled() {
		return labelled;
	}

	/**
	 * Add a new generic vertex
	 * @param a
	 * @return true if the vertex has been added, otherwise false
	 */
	@Override
	public boolean addNode(V a) {
		if (a == null || containsNode(a)) return false;
		
		graph.put(a.hashCode(), new Vertex<V, L>(a));
		return true;
	}
	
	/**
	 * Add a new edge to a vertex
	 * @param a
	 * @param b
	 * @param l
	 * @return true if the edge has been added, otherwise false
	 */
	@Override
	public boolean addEdge(V a, V b, L l) {
		if (a == null || b == null || containsEdge(a, b)
				|| (labelled && l == null) || (!labelled && l != null))
			return false;
		
		Vertex<V, L> currentVertexA = graph.get(a.hashCode());
		if (currentVertexA == null) return false;
		AbstractEdge<V, L> newEdge = new Edge<V, L>(currentVertexA.getItem(), b, l);
		currentVertexA.addEdge(newEdge);
		
		if (!isDirected()) {
			Vertex<V, L> currentVertexB = graph.get(b.hashCode());
			if (currentVertexB == null) return false;
			AbstractEdge<V, L> newEdgeB = new Edge<V, L>(currentVertexB.getItem(), a, l);
			currentVertexB.addEdge(newEdgeB);
		}
		
		return true;
	}

	/**
	 * Check if the graph has a vertex a
	 * @param a
	 * @return true if the vertex is already in, otherwise false
	 */
	@Override
	public boolean containsNode(V a) {
		if (a == null) return false;
		
		return graph.containsKey(a.hashCode());
	}

	/**
	 * Check if the vertex has an edge a - b
	 * @param a
	 * @param b
	 * @return true if the edge is already in, otherwise false
	 */
	@Override
	public boolean containsEdge(V a, V b) {
		if (a == null || b == null) return false;
		
		Vertex<V, L> currentVertex = graph.get(a.hashCode());
		if (currentVertex == null) return false;
		
		return currentVertex.containsEdge(b);
	}

	/**
	 * Remove a vertex a and all the edges connected to it
	 * @param a
	 * @return true if success otherwise false
	 */
	@Override
	public boolean removeNode(V a) {
		if (a == null) return false;
		
		for (Entry<Integer, Vertex<V, L>> vertex : graph.entrySet()) {
			Vertex<V, L> currentVertex = vertex.getValue();
			if (currentVertex.getItem().equals(a)) {
				currentVertex.removeAllEdges();
			} else {
				currentVertex.removeEdge(a);				
			}
		}
		
		return graph.remove(a.hashCode()) != null;
	}

	/**
	 * Remove an edge b
	 * @param a
	 * @param b
	 * @return true if success otherwise false
	 */
	@Override
	public boolean removeEdge(V a, V b) {
		if (a == null || b == null) return false;
		
		boolean response = true;
		
		Vertex<V, L> vertexA = graph.get(a.hashCode());
		if (vertexA == null) return false;
		response = vertexA.removeEdge(b);
		
		if (!isDirected()) {
			Vertex<V, L> vertexB = graph.get(b.hashCode());
			if (vertexB == null) return false;
			response = vertexB.removeEdge(a);
		}
		
		return response;
	}

	/**
	 * Return the amount of vertex in the graph
	 * @return number of vertexes
	 */
	@Override
	public int numNodes() {
		return graph.size();
	}

	/**
	 * Return the amount of edges in all the vertexes
	 * @return number of all edges
	 */
	@Override
	public int numEdges() {
		int totalNumberOfEdges = 0;
		for (Entry<Integer, Vertex<V, L>> vertex : graph.entrySet()) {
			totalNumberOfEdges += vertex.getValue().edgesSize();
		}
		
		return totalNumberOfEdges;
	}

	/**
	 * Return the list of vertexes
	 * @return collection of vertex
	 */
	@Override
	public Collection<V> getNodes() {
		Collection<V> result = new ArrayList<>();
		for (Entry<Integer, Vertex<V, L>> vertex : graph.entrySet()) {
			result.add(vertex.getValue().getItem());
		}
		
		return result;
	}

	/**
	 * Return all edges of all vertexes
	 * @return a collection of AbstractEdge<V, L>
	 */
	@Override
	public Collection<? extends AbstractEdge<V,L>> getEdges() {
		Collection<AbstractEdge<V,L>> result = new ArrayList<>();
		for (Entry<Integer, Vertex<V, L>> vertex : graph.entrySet()) {
			result.addAll(vertex.getValue().getEdges());
		}
		
		return result;
	}
	
	/**
	 * Return the list of adjacent vertexes
	 * @param a
	 * @return a collection of adjacent vertexes 
	 */
	@Override
	public Collection<V> getNeighbours(V a) {
		Collection<V> result = new ArrayList<>();
		if (a != null) {
			Vertex<V, L> currentVertex = graph.get(a.hashCode());
			if (currentVertex == null) return result;
			Collection<AbstractEdge<V, L>> edges = currentVertex.getEdges();
			for (AbstractEdge<V, L> edge : edges) {
				result.add(edge.getEnd());
			}			
		}
		
		return result;
	}

	/**
	 * Return the label for the edge a - b
	 * @param a
	 * @param b
	 * @return the label L
	 */
	@Override
	public L getLabel(V a, V b) {
		if (!isLabelled()) return null;
		
		Vertex<V, L> currentVertex = graph.get(a.hashCode());
		if (currentVertex == null) return null;
		
		AbstractEdge<V, L> currentEdge = currentVertex.getEdge(b);
		if (currentEdge == null) return null;
		
		return currentEdge.getLabel();
	}
}

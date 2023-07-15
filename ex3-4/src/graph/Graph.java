package graph;

import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Graph<V, L> implements AbstractGraph<V, L> {
	private boolean directed = false;
	private boolean labelled = false;
	private HashMap<Integer, Vertex<V, L>> vertexes = null; 
	
	public Graph(boolean directed, boolean labelled) {
		this.directed = directed; 
		this.labelled = labelled;
		this.vertexes = new HashMap<Integer, Vertex<V, L>>();
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
		
		vertexes.put(a.hashCode(), new Vertex<>(a));
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
		
		Vertex<V, L> currentVertexA = vertexes.get(a.hashCode());
		if (currentVertexA == null) return false;
		AbstractEdge<V, L> newEdge = new Edge<>(currentVertexA.getItem(), b, l);
		currentVertexA.addEdge(newEdge);
		
		if (!isDirected()) {
			Vertex<V, L> currentVertexB = vertexes.get(b.hashCode());
			if (currentVertexB == null) return false;
			AbstractEdge<V, L> newEdgeB = new Edge<>(currentVertexB.getItem(), a, l);
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
		
		return vertexes.containsKey(a.hashCode());
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
		
		Vertex<V, L> currentVertex = vertexes.get(a.hashCode());
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
		
		for (Entry<Integer, Vertex<V, L>> vertex : vertexes.entrySet()) {
			Vertex<V, L> currentVertex = vertex.getValue();
			if (currentVertex.getItem().equals(a)) {
				currentVertex.removeAllEdges();
			} else {
				currentVertex.removeEdge(a);				
			}
		}
		
		return vertexes.remove(a.hashCode()) != null;
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
		
		Vertex<V, L> vertexA = vertexes.get(a.hashCode());
		if (vertexA == null) return false;
		response = vertexA.removeEdge(b);
		
		if (!isDirected()) {
			Vertex<V, L> vertexB = vertexes.get(b.hashCode());
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
		return vertexes.size();
	}

	/**
	 * Return the amount of edges in all the vertexes
	 * @return number of all edges
	 */
	@Override
	public int numEdges() {
		int totalNumberOfEdges = 0;
		for (Entry<Integer, Vertex<V, L>> vertex : vertexes.entrySet()) {
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
		Collection<V> result = new ArrayList<V>();
		for (Entry<Integer, Vertex<V, L>> vertex : vertexes.entrySet()) {
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
		for (Entry<Integer, Vertex<V, L>> vertex : vertexes.entrySet()) {
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
			Vertex<V, L> currentVertex = vertexes.get(a.hashCode());
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
		
		Vertex<V, L> currentVertex = vertexes.get(a.hashCode());
		if (currentVertex == null) return null;
		
		AbstractEdge<V, L> currentEdge = currentVertex.getEdge(b);
		if (currentEdge == null) return null;
		
		return currentEdge.getLabel();
	}
	
	/**
	 * Return the vertex if exist given the value
	 * @param a
	 * @return the vertex Vertex<V, L>
	 */
	public Vertex<V, L> getVertexByValue(V a) {
		if (a == null) return null;
		
		return vertexes.get(a.hashCode());
	}
}

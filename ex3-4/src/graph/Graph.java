package graph;

import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Graph<V, L> implements AbstractGraph<V, L> {
	private boolean directed = false;
	private boolean labelled = false;
	private HashMap<Integer, Node<V, L>> nodes = null;
	
	public Graph(boolean directed, boolean labelled) {
		this.directed = directed; 
		this.labelled = labelled;
		this.nodes = new HashMap<>();
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
	 * Add a new generic node
	 * @param a
	 * @return true if the node has been added, otherwise false
	 */
	@Override
	public boolean addNode(V a) throws GraphException {
		if (a == null)
			throw new GraphException("addNode: the value can't be null");
		if (containsNode(a)) return false;
		
		nodes.put(a.hashCode(), new Node<>(a));
		return true;
	}
	
	/**
	 * Add a new edge to a node
	 * @param a
	 * @param b
	 * @param l
	 * @return true if the edge has been added, otherwise false
	 */
	@Override
	public boolean addEdge(V a, V b, L l) throws GraphException {
		if (a == null || b == null)
			throw new GraphException("addEdge: the values a and b can't be null");
		if ((labelled && l == null) || (!labelled && l != null))
			throw new GraphException("addEdge: the label value is against the direct initialization");

		if (containsEdge(a, b))
			return false;
		
		Node<V, L> currentNodeA = nodes.get(a.hashCode());
		if (currentNodeA == null) return false;
		AbstractEdge<V, L> newEdge = new Edge<>(currentNodeA.getItem(), b, l);
		currentNodeA.addEdge(newEdge);
		
		if (!isDirected()) {
			Node<V, L> currentNodeB = nodes.get(b.hashCode());
			if (currentNodeB == null) return false;
			AbstractEdge<V, L> newEdgeB = new Edge<>(currentNodeB.getItem(), a, l);
			currentNodeB.addEdge(newEdgeB);
		}
		
		return true;
	}

	/**
	 * Check if the graph has a node a
	 * @param a
	 * @return true if the node is already in, otherwise false
	 */
	@Override
	public boolean containsNode(V a) throws GraphException {
		if (a == null)
			throw new GraphException("containsNode: the value can't be null");

		return nodes.containsKey(a.hashCode());
	}

	/**
	 * Check if the node has an edge a - b
	 * @param a
	 * @param b
	 * @return true if the edge is already in, otherwise false
	 */
	@Override
	public boolean containsEdge(V a, V b) throws GraphException {
		if (a == null || b == null)
			throw new GraphException("containsEdge: the values a and b can't be null");

		Node<V, L> currentNode = nodes.get(a.hashCode());
		if (currentNode == null) return false;
		
		return currentNode.containsEdgeWith(b);
	}

	/**
	 * Remove a node a and all the edges connected to it
	 * @param a
	 * @return true if success otherwise false
	 */
	@Override
	public boolean removeNode(V a) throws GraphException {
		if (a == null)
			throw new GraphException("removeNode: the value can't be null");

		for (Entry<Integer, Node<V, L>> node : nodes.entrySet()) {
			Node<V, L> currentNode = node.getValue();
			if (currentNode.getItem().equals(a)) {
				currentNode.removeAllEdges();
			} else {
				currentNode.removeEdgeWith(a);
			}
		}
		
		return nodes.remove(a.hashCode()) != null;
	}

	/**
	 * Remove an edge b
	 * @param a
	 * @param b
	 * @return true if success otherwise false
	 */
	@Override
	public boolean removeEdge(V a, V b) throws GraphException {
		if (a == null || b == null)
			throw new GraphException("removeEdge: the values a and b can't be null");

		boolean response = true;
		
		Node<V, L> nodeA = nodes.get(a.hashCode());
		if (nodeA == null) return false;
		response = nodeA.removeEdgeWith(b);
		
		if (!isDirected()) {
			Node<V, L> nodeB = nodes.get(b.hashCode());
			if (nodeB == null) return false;
			response = nodeB.removeEdgeWith(a);
		}
		
		return response;
	}

	/**
	 * Return the amount of node in the graph
	 * @return number of nodes
	 */
	@Override
	public int numNodes() {
		return nodes.size();
	}

	/**
	 * Return the amount of edges in all the nodes
	 * @return number of all edges
	 */
	@Override
	public int numEdges() {
		int totalNumberOfEdges = 0;
		for (Entry<Integer, Node<V, L>> node : nodes.entrySet()) {
			totalNumberOfEdges += node.getValue().edgesSize();
		}
		
		return totalNumberOfEdges;
	}

	/**
	 * Return the list of nodes
	 * @return collection of node
	 */
	@Override
	public Collection<V> getNodes() {
		Collection<V> result = new ArrayList<V>();
		for (Entry<Integer, Node<V, L>> node : nodes.entrySet()) {
			result.add(node.getValue().getItem());
		}
		
		return result;
	}

	/**
	 * Return all edges of all nodes
	 * @return a collection of AbstractEdge<V, L>
	 */
	@Override
	public Collection<? extends AbstractEdge<V,L>> getEdges() {
		Collection<AbstractEdge<V,L>> result = new ArrayList<>();
		for (Entry<Integer, Node<V, L>> node : nodes.entrySet()) {
			result.addAll(node.getValue().getEdges());
		}
		
		return result;
	}
	
	/**
	 * Return the list of adjacent nodes
	 * @param a
	 * @return a collection of adjacent nodes 
	 */
	@Override
	public Collection<V> getNeighbours(V a) throws GraphException {
		if (a == null)
			throw new GraphException("getNeighbours: the value can't be null");

		Collection<V> result = new ArrayList<>();
		Node<V, L> currentNode = nodes.get(a.hashCode());
		if (currentNode == null) return result;
		Collection<AbstractEdge<V, L>> edges = currentNode.getEdges();
		for (AbstractEdge<V, L> edge : edges) {
			result.add(edge.getEnd());
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
	public L getLabel(V a, V b) throws GraphException {
		if (a == null || b == null)
			throw new GraphException("getLabel: the values a and b can't be null");

		if (!isLabelled()) return null;
		
		Node<V, L> currentNode = nodes.get(a.hashCode());
		if (currentNode == null) return null;
		
		AbstractEdge<V, L> currentEdge = currentNode.getEdgeWith(b);
		if (currentEdge == null) return null;
		
		return currentEdge.getLabel();
	}
	
	/**
	 * Return the node if exist given the value
	 * @param a
	 * @return the node Node<V, L>
	 */
	public Node<V, L> getNodeByValue(V a) throws GraphException {
		if (a == null)
			throw new GraphException("getNodeByValue: the value can't be null");

		return nodes.get(a.hashCode());
	}
}

package graph;

public class Edge<V, L> implements AbstractEdge<V, L> {

	V start = null;
	V end = null;
	L label = null;
	
	/**
	 * The label can be null
	 * @param start
	 * @param end
	 * @param label
	 */
	public Edge(V start, V end, L label) {
		this.start = start;
		this.end = end;
		this.label = label;
	}

	/**
	 * @return the start node
	 */
	@Override
	public V getStart() {
		return start;
	}

	/**
	 * @return the end node
	 */
	@Override
	public V getEnd() {
		return end;
	}

	/**
	 * @return the label of the edge
	 */
	@Override
	public L getLabel() {
		return label;
	}
}

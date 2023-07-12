package graph;

public interface AbstractEdge<V,L> {
	// il nodo di partenza dell'arco
	public V getStart(); 

	// il nodo di arrivo dell'arco
	public V getEnd(); 

	// l'etichetta dell'arco
	public L getLabel(); 
};
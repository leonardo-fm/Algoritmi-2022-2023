package graph;

import java.util.Collection;

public interface AbstractGraph<V, L> {
  // dice se il grafo è diretto o meno -- O(1)
  public boolean isDirected(); 
  
  // dice se il grafo è etichettato o meno -- O(1)
  public boolean isLabelled(); 
  
  // aggiunge un nodo -- O(1)
  public boolean addNode(V a) throws GraphException;
  
  // aggiunge un arco dati estremi ed etichetta -- O(1)
  public boolean addEdge(V a, V b, L l) throws GraphException;
  
  // controlla se un nodo è nel grafo -- O(1)
  public boolean containsNode(V a) throws GraphException;
  
  // controlla se un arco è nel grafo -- O(1) (*)
  public boolean containsEdge(V a, V b) throws GraphException;
  
  // rimuove un nodo dal grafo -- O(N)
  public boolean removeNode(V a) throws GraphException;
  
  // rimuove un arco dal grafo -- O(1) (*)
  public boolean removeEdge(V a, V b) throws GraphException;
  
  // numero di nodi -- O(1)
  public int numNodes(); 
  
  // numero di archi -- O(N)
  public int numEdges(); 
  
  // recupero dei nodi del grafo -- O(N)
  public Collection<V> getNodes(); 
  
  // recupero degli archi del grafo -- O(N)
  public Collection<? extends AbstractEdge<V,L>> getEdges(); 
  
  // recupero dei nodi adiacenti ad un dato nodo -- O(1) (*)
  public Collection<V> getNeighbours(V a) throws GraphException;
  
  // recupero dell'etichetta di un arco -- O(1) (*)
  public L getLabel(V a, V b) throws GraphException;
};
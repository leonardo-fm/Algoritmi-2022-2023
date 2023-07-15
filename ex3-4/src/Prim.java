import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import graph.AbstractEdge;
import graph.Graph;
import graph.Vertex;
import priorityqueue.PriorityQueue;
import priorityqueue.PriorityQueueException;

public class Prim {
	public static void main(String[] args) throws IOException, PriorityQueueException {
		Graph<String, Double> graph = new Graph<>(false, true);
		loadGraph(graph, args[1]);
		Collection<? extends AbstractEdge<String, Double>> mst = minimumSpanningForest(graph);
		// scrivi su standard output solo la descrizione della foresta calcolata come CSV con formato analogo a quello in input
		// su standard error si possono scrivere ulteriori informazioni, come il numero di nodi e archi nella foresta calcolata,
		// o il peso totale della foresta
	}
  	  
  	public static <V, L extends Number> Collection<? extends AbstractEdge<V, L>> minimumSpanningForest(Graph<V, L> graph) {
		PriorityQueue<AbstractEdge<V, L>> pq = new PriorityQueue<AbstractEdge<V, L>>(new EdgeDoubleComparator());
		int numberOfEdges = graph.numNodes() - 1;
		int edgesCount = 0;
		Double totalWeight = 0.0;
		Collection<AbstractEdge<V, L>> response = new ArrayList<>(numberOfEdges);
		if (graph.numNodes() == 0) return response;
		graph.getNodes();
		V firstNodeValue = graph.getNodes().iterator().next();
		addEdgesToPriorityQueue(graph, graph.getVertexByValue(firstNodeValue), pq);
		while (!pq.empty() && edgesCount != numberOfEdges) {
			AbstractEdge<V, L> currentEdge = pq.top();
			pq.pop();
			
  			if (graph.getVertexByValue(currentEdge.getEnd()).getIsVisisted()) continue;
  			response.add(currentEdge);
  			edgesCount++;
  			totalWeight += (Double)currentEdge.getLabel();
  			
  			addEdgesToPriorityQueue(graph, graph.getVertexByValue(currentEdge.getEnd()), pq);
		}
  		if (response.size() != numberOfEdges) return null;
		return response;
  	}
  	
  	private static void loadGraph(Graph<String, Double> graph, String filePath) throws IOException {
  		System.out.println("Loading data from file...");
		int nOfItems = 0;
  		Path inputFilePath = Paths.get(filePath);
		try(BufferedReader fileInputReader = Files.newBufferedReader(inputFilePath, StandardCharsets.UTF_8)) {
			String line = null;
			while((line = fileInputReader.readLine()) != null) {      
			    String[] lineElements = line.split(",");
			    graph.addNode(lineElements[0]);
			    graph.addNode(lineElements[1]);
			    graph.addEdge(lineElements[0], lineElements[1], Double.parseDouble(lineElements[2]));
			}
			nOfItems++;
		}
  	    
  	    System.out.println("Data loaded (" + nOfItems + ")");
  	}

  	private static <V, L extends Number> void addEdgesToPriorityQueue(Graph<V, L> graph, Vertex<V, L> vertex, PriorityQueue<AbstractEdge<V, L>> pq) {
  		vertex.setIsVisited(true);
  		Collection<AbstractEdge<V, L>> adjacent = vertex.getEdges();
  		for (AbstractEdge<V, L> edge : adjacent) {
  			if (graph.getVertexByValue(edge.getEnd()).getIsVisisted()) continue;
  			pq.push(edge);
  		}
  	}
}

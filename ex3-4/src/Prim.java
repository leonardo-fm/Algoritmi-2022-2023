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
	public static void main(String[] args) throws IOException {
		Graph<String, Double> graph = new Graph<>(false, true);
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		System.out.println("THE DATA PHAT IS PROVIDED BY CODE NOT BU THE USER!");
		loadGraph(graph, "./data/italian_dist_graph.csv");
		//loadGraph(graph, args[1]);
		Collection<? extends AbstractEdge<String, Double>> mst = minimumSpanningForest(graph);
		/*
		for (AbstractEdge<String, Double> edge : mst) {
			System.out.println(edge.getStart() + "," + edge.getEnd() + "," + edge.getLabel());
		}*/
	}
  	  
  	public static <V, L extends Number> Collection<? extends AbstractEdge<V, L>> minimumSpanningForest(Graph<V, L> graph) {
		PriorityQueue<AbstractEdge<V, L>> pq = new PriorityQueue<>(new EdgeDoubleComparator<>());
		int edgesCount = 0;
		int vertexCount = 1;
		double totalWeight = 0.0;
		Collection<AbstractEdge<V, L>> response = new ArrayList<>(graph.numNodes() - 1);
		if (graph.numNodes() == 0) return response;
		graph.getNodes();
		V firstNodeValue = graph.getNodes().iterator().next();
		addEdgesToPriorityQueue(graph, graph.getVertexByValue(firstNodeValue), pq);
		while (!pq.empty()) {
			AbstractEdge<V, L> currentEdge = pq.top();
			pq.pop();
			
  			if (graph.getVertexByValue(currentEdge.getEnd()).getIsVisisted()) continue;
  			response.add(currentEdge);
  			edgesCount++;
			vertexCount++;
  			totalWeight += Double.parseDouble(currentEdge.getLabel().toString());
  			
  			addEdgesToPriorityQueue(graph, graph.getVertexByValue(currentEdge.getEnd()), pq);
		}

		System.err.println("Total number of vertexes: " + vertexCount);
		System.err.println("Total number of edges: " + edgesCount);
		System.err.println("Total weight of the mst: " + totalWeight / 1000);

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
				nOfItems++;
			}
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

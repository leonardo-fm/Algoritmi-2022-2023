package graph;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * It specifies a test suite for the Graph library
 * To improve readability, Java methods' naming conventions are not fully
 * respected...
 * @author ferrero-merlino
 */
public class GraphTests {

	private Graph<String, Integer> graphDL;
	private Graph<String, Integer> graphD;
	private Graph<String, Integer> graphL;
	private Graph<String, Integer> graph;
	private Vertex<String, Integer> v1;
	private Vertex<String, Integer> v2;
	private Vertex<String, Integer> v3;
	
	@Before
	public void createPriorityQueue() throws GraphException {
		graphDL = new Graph<String, Integer>(true, true);
		graphD = new Graph<String, Integer>(true, false);
		graphL = new Graph<String, Integer>(false, true);
		graph = new Graph<String, Integer>(false, false);

		v1 = new Vertex<>("Vertex 1", 0);
		v2 = new Vertex<>("Vertex 2", 1);
		v3 = new Vertex<>("Vertex 3", 2);
	}
	
	// ----------- direct tests -----------
	@Test
	public void testIsDirect() {
		assertTrue(graphD.isDirected());
	}
	
	@Test
	public void testIsNotDirect() {
		assertFalse(graph.isDirected());
	}
	
	@Test
	public void testIsDirectEdges() {
		graphD.addNode(v1.getValue());
		graphD.addNode(v2.getValue());
		graphD.addEdge(v1.getValue(), v2.getValue(), null);
		assertEquals(1, graphD.numEdges());
	}
	
	@Test
	public void testIsNotDirectEdges() {
		graph = new Graph<String, Integer>(false, false);
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		assertEquals(2, graph.numEdges());
	}
	
	// ----------- labelled tests -----------
	@Test
	public void testIsLabelled() {
		assertTrue(graphL.isLabelled());
	}
	
	@Test
	public void testIsNotLabelled() {
		assertFalse(graph.isLabelled());
	}
	
	// ----------- add vertex tests -----------
	@Test
	public void testAddOneVertex() {
		graph.addNode(v1.getValue());
		assertEquals(1, graph.numNodes());
	}
	
	@Test
	public void testAddTwoVertexes() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		assertEquals(2, graph.numNodes());
	}
	
	@Test
	public void testAddTwoEqVertexes() {
		graph.addNode(v1.getValue());
		graph.addNode(v1.getValue());
		assertEquals(1, graph.numNodes());
	}
	
	// ----------- add edge tests -----------
	@Test
	public void testAddOneEdge() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		assertEquals(2, graph.numEdges());
	}
	
	@Test
	public void testAddTwoEdges() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addNode(v3.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		graph.addEdge(v2.getValue(), v3.getValue(), null);
		assertEquals(4, graph.numEdges());
	}
	
	@Test
	public void testAddTwoEqEdges() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		assertEquals(2, graph.numEdges());
	}
	
	@Test
	public void testAddOneEdgeWithLabel() {
		graphL.addNode(v1.getValue());
		graphL.addNode(v2.getValue());
		graphL.addEdge(v1.getValue(), v2.getValue(), 8);
		assertEquals(2, graphL.numEdges());
	}
	
	@Test
	public void testAddOneEdgeDirect() {
		graphD.addNode(v1.getValue());
		graphD.addNode(v2.getValue());
		graphD.addEdge(v1.getValue(), v2.getValue(), null);
		assertEquals(1, graphD.numEdges());
	}
	
	@Test
	public void testAddTwoEdgesDirect() {
		graphD.addNode(v1.getValue());
		graphD.addNode(v2.getValue());
		graphD.addNode(v3.getValue());
		graphD.addEdge(v1.getValue(), v2.getValue(), null);
		graphD.addEdge(v2.getValue(), v3.getValue(), null);
		assertEquals(2, graphD.numEdges());
	}
	
	@Test
	public void testAddTwoEqEdgesDirect() {
		graphD.addNode(v1.getValue());
		graphD.addNode(v2.getValue());
		graphD.addEdge(v1.getValue(), v2.getValue(), null);
		graphD.addEdge(v1.getValue(), v2.getValue(), null);
		assertEquals(1, graphD.numEdges());
	}
	
	@Test
	public void testAddOneEdgeWithLabelDirect() {
		graphDL.addNode(v1.getValue());
		graphDL.addNode(v2.getValue());
		graphDL.addEdge(v1.getValue(), v2.getValue(), 8);
		assertEquals(1, graphDL.numEdges());
	}
	
	// ----------- contain vertex tests -----------
	@Test
	public void testAddCheckVertex() {
		graph.addNode(v1.getValue());
		assertTrue(graph.containsNode(v1.getValue()));
	}
	
	@Test
	public void testAddTwoCheckVertexes() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		assertTrue(graph.containsNode(v2.getValue()));
	}
	
	@Test
	public void testAddCheckNoVertex() {
		assertFalse(graph.containsNode(v1.getValue()));
	}
	
	// ----------- contain edge tests -----------
	@Test
	public void testAddCheckEdge() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		assertTrue(graph.containsEdge(v1.getValue(), v2.getValue()));
	}
	
	@Test
	public void testAddTwoCheckEdges() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addNode(v3.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		graph.addEdge(v1.getValue(), v3.getValue(), null);
		assertTrue(graph.containsEdge(v1.getValue(), v3.getValue()));
	}
	
	@Test
	public void testAddCheckNoEdge() {
		assertFalse(graph.containsEdge(v1.getValue(), v2.getValue()));	
	}
	
	@Test
	public void testAddCheckEdgeOtherDirection() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		assertTrue(graph.containsEdge(v2.getValue(), v1.getValue()));
	}
	
	@Test
	public void testDeleteCheckEdgeOtherDirection() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		graph.removeEdge(v1.getValue(), v2.getValue());
		assertFalse(graph.containsEdge(v2.getValue(), v1.getValue()));
	}
	
	// ----------- remove vertex tests -----------
	@Test
	public void testRemoveVertex() {
		graph.addNode(v1.getValue());
		assertTrue(graph.removeNode(v1.getValue()));
	}
	
	@Test
	public void testRemoveNoVertex() {
		assertFalse(graph.removeNode(v1.getValue()));
	}
	
	@Test
	public void testRemoveAllVertexes() {
		boolean response = true;
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addNode(v3.getValue());
		response = response && graph.removeNode(v1.getValue());
		response = response && graph.removeNode(v2.getValue());
		response = response && graph.removeNode(v3.getValue());
		assertTrue(response);
	}
	
	// ----------- remove edge tests -----------
	@Test
	public void testRemoveEdge() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		assertTrue(graph.removeEdge(v1.getValue(), v2.getValue()));
	}
	
	@Test
	public void testRemoveOtherEdge() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		assertTrue(graph.removeEdge(v2.getValue(), v1.getValue()));
	}
	
	@Test
	public void testRemoveNoEdge() {
		assertFalse(graph.removeEdge(v1.getValue(), v2.getValue()));
	}
	
	@Test
	public void testRemoveAllEdge() {
		boolean response = true;
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addNode(v3.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		graph.addEdge(v1.getValue(), v3.getValue(), null);
		graph.addEdge(v2.getValue(), v3.getValue(), null);
		response = response && graph.removeEdge(v1.getValue(), v2.getValue());
		response = response && graph.removeEdge(v1.getValue(), v3.getValue());
		response = response && graph.removeEdge(v2.getValue(), v3.getValue());
		assertTrue(response);
	}
	
	// ----------- vertex size tests -----------
	@Test
	public void testEmptyGraph() {
		assertEquals(0, graph.numNodes());
	}
	
	@Test
	public void testOneVertex() {
		graph.addNode(v1.getValue());
		assertEquals(1, graph.numNodes());
	}
	
	@Test
	public void testTwoVertex() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		assertEquals(2, graph.numNodes());
	}
	
	@Test
	public void testTwoEqVertex() {
		graph.addNode(v1.getValue());
		graph.addNode(v1.getValue());
		assertEquals(1, graph.numNodes());
	}
	
	// ----------- edge size tests -----------
	@Test
	public void testEmptyEdgeGraph() {
		assertEquals(0, graph.numEdges());
	}
	
	@Test
	public void testOneEdge() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		assertEquals(2, graph.numEdges());
	}
	
	@Test
	public void testTwoEdges() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addNode(v3.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		graph.addEdge(v1.getValue(), v3.getValue(), null);
		assertEquals(4, graph.numEdges());
	}
	
	@Test
	public void testTwoEqEdges() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		assertEquals(2, graph.numEdges());
	}
	
	@Test
	public void testOneEdgeDirect() {
		graphD.addNode(v1.getValue());
		graphD.addNode(v2.getValue());
		graphD.addEdge(v1.getValue(), v2.getValue(), null);
		assertEquals(1, graphD.numEdges());
	}
	
	@Test
	public void testTwoEdgesDirect() {
		graphD.addNode(v1.getValue());
		graphD.addNode(v2.getValue());
		graphD.addNode(v3.getValue());
		graphD.addEdge(v1.getValue(), v2.getValue(), null);
		graphD.addEdge(v1.getValue(), v3.getValue(), null);
		assertEquals(2, graphD.numEdges());
	}
	
	@Test
	public void testTwoEqEdgesDirect() {
		graphD.addNode(v1.getValue());
		graphD.addNode(v2.getValue());
		graphD.addEdge(v1.getValue(), v2.getValue(), null);
		graphD.addEdge(v1.getValue(), v2.getValue(), null);
		assertEquals(1, graphD.numEdges());
	}
	
	// ----------- get vertexes tests -----------
	@Test
	public void testEmptyGraphGetVertex() {
		String[] expected = {};
		assertArrayEquals(expected, graph.getNodes().toArray());
	}
	
	@Test
	public void testOneGetVertex() {
		String[] expected = { v1.getValue() };
		graph.addNode(v1.getValue());
		assertArrayEquals(expected, graph.getNodes().toArray());
	}

	@Test
	public void testThreeGetVertex() {
		String[] expected = { v1.getValue(), v3.getValue(), v2.getValue() };
		graph.addNode(v1.getValue());
		graph.addNode(v3.getValue());
		graph.addNode(v2.getValue());
		assertArrayEquals(expected, graph.getNodes().toArray());
	}
	
	@Test
	public void testTwoGetVertex() {
		String[] expected = { v3.getValue(), v2.getValue() };
		graph.addNode(v1.getValue());
		graph.addNode(v3.getValue());
		graph.addNode(v2.getValue());
		graph.removeNode(v1.getValue());
		assertArrayEquals(expected, graph.getNodes().toArray());
	}
	
	// ----------- get edges tests -----------
	@Test
	public void testEmptyGraphGetEdges() {
		ArrayList<Edge<String, Integer>> expected = new ArrayList<Edge<String,Integer>>();
		assertArrayEquals(expected.toArray(), graph.getEdges().toArray());
	}
	
	@Test
	public void testOneGetEdge() {
		ArrayList<Edge<String, Integer>> expected = new ArrayList<Edge<String,Integer>>();
		expected.add(new Edge<String, Integer>(v1.getValue(), v2.getValue(), null));
		expected.add(new Edge<String, Integer>(v2.getValue(), v1.getValue(), null));
		
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);

		ArrayList<Edge<String, Integer>> response = (ArrayList<Edge<String, Integer>>) graph.getEdges();
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i).start, response.get(i).start);
			assertEquals(expected.get(i).end, response.get(i).end);
			assertEquals(expected.get(i).label, response.get(i).label);
		}
	}

	@Test
	public void testTwoGetEdges() {
		ArrayList<Edge<String, Integer>> expected = new ArrayList<Edge<String,Integer>>();
		expected.add(new Edge<String, Integer>(v1.getValue(), v2.getValue(), null));
		expected.add(new Edge<String, Integer>(v3.getValue(), v2.getValue(), null));
		expected.add(new Edge<String, Integer>(v2.getValue(), v1.getValue(), null));
		expected.add(new Edge<String, Integer>(v2.getValue(), v3.getValue(), null));
		
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addNode(v3.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		graph.addEdge(v2.getValue(), v3.getValue(), null);

		ArrayList<Edge<String, Integer>> response = (ArrayList<Edge<String, Integer>>) graph.getEdges();
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i).start, response.get(i).start);
			assertEquals(expected.get(i).end, response.get(i).end);
			assertEquals(expected.get(i).label, response.get(i).label);
		}
	}
	
	@Test
	public void testOneGetEdgesDeletionEdge() {
		ArrayList<Edge<String, Integer>> expected = new ArrayList<Edge<String,Integer>>();
		expected.add(new Edge<String, Integer>(v1.getValue(), v2.getValue(), null));
		expected.add(new Edge<String, Integer>(v2.getValue(), v1.getValue(), null));
		
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addNode(v3.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		graph.addEdge(v2.getValue(), v3.getValue(), null);
		graph.removeEdge(v2.getValue(), v3.getValue());
		
		ArrayList<Edge<String, Integer>> response = (ArrayList<Edge<String, Integer>>) graph.getEdges();
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i).start, response.get(i).start);
			assertEquals(expected.get(i).end, response.get(i).end);
			assertEquals(expected.get(i).label, response.get(i).label);
		}
	}
	
	@Test
	public void testOneGetEdgesDeletionVertex() {
		ArrayList<Edge<String, Integer>> expected = new ArrayList<Edge<String,Integer>>();
		expected.add(new Edge<String, Integer>(v1.getValue(), v2.getValue(), null));
		expected.add(new Edge<String, Integer>(v2.getValue(), v1.getValue(), null));
		
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addNode(v3.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		graph.addEdge(v2.getValue(), v3.getValue(), null);
		graph.removeNode(v3.getValue());

		ArrayList<Edge<String, Integer>> response = (ArrayList<Edge<String, Integer>>) graph.getEdges();
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i).start, response.get(i).start);
			assertEquals(expected.get(i).end, response.get(i).end);
			assertEquals(expected.get(i).label, response.get(i).label);
		}
	}
	
	// ----------- get neighbours vertexes tests -----------
	@Test
	public void testEmptyGraphGetNeighboursVertex() {
		String[] expected = {};
		assertArrayEquals(expected, graph.getNeighbours(v1.getValue()).toArray());
	}
	
	@Test
	public void testOneGetNeighboursVertex() {
		String[] expected = {};
		graph.addNode(v1.getValue());
		assertArrayEquals(expected, graph.getNeighbours(v1.getValue()).toArray());
	}

	@Test
	public void testThreeGetNeighboursVertex() {
		String[] expected = { v3.getValue(), v2.getValue() };
		graph.addNode(v1.getValue());
		graph.addNode(v3.getValue());
		graph.addNode(v2.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		graph.addEdge(v1.getValue(), v3.getValue(), null);
		assertArrayEquals(expected, graph.getNeighbours(v1.getValue()).toArray());
	}
	
	@Test
	public void testTwoGetNeighboursVertex() {
		String[] expected = { v3.getValue() };
		graph.addNode(v1.getValue());
		graph.addNode(v3.getValue());
		graph.addNode(v2.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		graph.addEdge(v1.getValue(), v3.getValue(), null);
		graph.removeNode(v2.getValue());
		assertArrayEquals(expected, graph.getNeighbours(v1.getValue()).toArray());
	}
	
	// ----------- add edge label -----------
	@Test
	public void testNoIsLabelledInsert() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		assertTrue(graph.addEdge(v1.getValue(), v2.getValue(), null));
	}
	
	@Test
	public void testNoIsLabelledInsertValue() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		assertFalse(graph.addEdge(v1.getValue(), v2.getValue(), 8));
	}
	
	@Test
	public void testIsLabelledInsert() {
		graphL.addNode(v1.getValue());
		graphL.addNode(v2.getValue());
		assertFalse(graphL.addEdge(v1.getValue(), v2.getValue(), null));
	}
		
	@Test
	public void testIsLabelledInsertValue() {
		graphL.addNode(v1.getValue());
		graphL.addNode(v2.getValue());
		assertTrue(graphL.addEdge(v1.getValue(), v2.getValue(), 8));
	}
	
	@Test
	public void testNoDirection() {
		Integer expected = 8;
		graphL.addNode(v1.getValue());
		graphL.addNode(v2.getValue());
		graphL.addEdge(v1.getValue(), v2.getValue(), 8);
		assertEquals(expected, graphL.getLabel(v1.getValue(), v2.getValue()));
	}
	
	@Test
	public void testNoLabelledNoDirection() {
		graph.addNode(v1.getValue());
		graph.addNode(v2.getValue());
		graph.addEdge(v1.getValue(), v2.getValue(), null);
		assertEquals(null, graphL.getLabel(v1.getValue(), v2.getValue()));
	}
	
	@Test
	public void testNoEdge() {
		assertEquals(null, graphL.getLabel(v1.getValue(), v2.getValue()));
	}
	
	// ----------- get index tests -----------
	@Test
	public void testGetIndexFromVertexNotInTheGraph() {
		assertEquals(-1, graph.indexOf(v1.getValue()));
	}
	
	@Test
	public void testGetIndex() {
		graph.addNode(v1.getValue());
		assertEquals(0, graph.indexOf(v1.getValue()));
	}
}
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

		v1 = new Vertex<>("Vertex 1");
		v2 = new Vertex<>("Vertex 2");
		v3 = new Vertex<>("Vertex 3");
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
		graphD.addNode(v1.getItem());
		graphD.addNode(v2.getItem());
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(1, graphD.numEdges());
	}
	
	@Test
	public void testIsNotDirectEdges() {
		graph = new Graph<String, Integer>(false, false);
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
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
		graph.addNode(v1.getItem());
		assertEquals(1, graph.numNodes());
	}
	
	@Test
	public void testAddTwoVertexes() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		assertEquals(2, graph.numNodes());
	}
	
	@Test
	public void testAddTwoEqVertexes() {
		graph.addNode(v1.getItem());
		graph.addNode(v1.getItem());
		assertEquals(1, graph.numNodes());
	}
	
	// ----------- add edge tests -----------
	@Test
	public void testAddOneEdge() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(2, graph.numEdges());
	}
	
	@Test
	public void testAddTwoEdges() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addNode(v3.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.addEdge(v2.getItem(), v3.getItem(), null);
		assertEquals(4, graph.numEdges());
	}
	
	@Test
	public void testAddTwoEqEdges() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(2, graph.numEdges());
	}
	
	@Test
	public void testAddOneEdgeWithLabel() {
		graphL.addNode(v1.getItem());
		graphL.addNode(v2.getItem());
		graphL.addEdge(v1.getItem(), v2.getItem(), 8);
		assertEquals(2, graphL.numEdges());
	}
	
	@Test
	public void testAddOneEdgeDirect() {
		graphD.addNode(v1.getItem());
		graphD.addNode(v2.getItem());
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(1, graphD.numEdges());
	}
	
	@Test
	public void testAddTwoEdgesDirect() {
		graphD.addNode(v1.getItem());
		graphD.addNode(v2.getItem());
		graphD.addNode(v3.getItem());
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
		graphD.addEdge(v2.getItem(), v3.getItem(), null);
		assertEquals(2, graphD.numEdges());
	}
	
	@Test
	public void testAddTwoEqEdgesDirect() {
		graphD.addNode(v1.getItem());
		graphD.addNode(v2.getItem());
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(1, graphD.numEdges());
	}
	
	@Test
	public void testAddOneEdgeWithLabelDirect() {
		graphDL.addNode(v1.getItem());
		graphDL.addNode(v2.getItem());
		graphDL.addEdge(v1.getItem(), v2.getItem(), 8);
		assertEquals(1, graphDL.numEdges());
	}
	
	// ----------- contain vertex tests -----------
	@Test
	public void testAddCheckVertex() {
		graph.addNode(v1.getItem());
		assertTrue(graph.containsNode(v1.getItem()));
	}
	
	@Test
	public void testAddTwoCheckVertexes() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		assertTrue(graph.containsNode(v2.getItem()));
	}
	
	@Test
	public void testAddCheckNoVertex() {
		assertFalse(graph.containsNode(v1.getItem()));
	}
	
	// ----------- contain edge tests -----------
	@Test
	public void testAddCheckEdge() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertTrue(graph.containsEdge(v1.getItem(), v2.getItem()));
	}
	
	@Test
	public void testAddTwoCheckEdges() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addNode(v3.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.addEdge(v1.getItem(), v3.getItem(), null);
		assertTrue(graph.containsEdge(v1.getItem(), v3.getItem()));
	}
	
	@Test
	public void testAddCheckNoEdge() {
		assertFalse(graph.containsEdge(v1.getItem(), v2.getItem()));	
	}
	
	@Test
	public void testAddCheckEdgeOtherDirection() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertTrue(graph.containsEdge(v2.getItem(), v1.getItem()));
	}
	
	@Test
	public void testDeleteCheckEdgeOtherDirection() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.removeEdge(v1.getItem(), v2.getItem());
		assertFalse(graph.containsEdge(v2.getItem(), v1.getItem()));
	}
	
	// ----------- remove vertex tests -----------
	@Test
	public void testRemoveVertex() {
		graph.addNode(v1.getItem());
		assertTrue(graph.removeNode(v1.getItem()));
	}
	
	@Test
	public void testRemoveNoVertex() {
		assertFalse(graph.removeNode(v1.getItem()));
	}
	
	@Test
	public void testRemoveAllVertexes() {
		boolean response = true;
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addNode(v3.getItem());
		response = response && graph.removeNode(v1.getItem());
		response = response && graph.removeNode(v2.getItem());
		response = response && graph.removeNode(v3.getItem());
		assertTrue(response);
	}
	
	// ----------- remove edge tests -----------
	@Test
	public void testRemoveEdge() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertTrue(graph.removeEdge(v1.getItem(), v2.getItem()));
	}
	
	@Test
	public void testRemoveOtherEdge() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertTrue(graph.removeEdge(v2.getItem(), v1.getItem()));
	}
	
	@Test
	public void testRemoveNoEdge() {
		assertFalse(graph.removeEdge(v1.getItem(), v2.getItem()));
	}
	
	@Test
	public void testRemoveAllEdge() {
		boolean response = true;
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addNode(v3.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.addEdge(v1.getItem(), v3.getItem(), null);
		graph.addEdge(v2.getItem(), v3.getItem(), null);
		response = response && graph.removeEdge(v1.getItem(), v2.getItem());
		response = response && graph.removeEdge(v1.getItem(), v3.getItem());
		response = response && graph.removeEdge(v2.getItem(), v3.getItem());
		assertTrue(response);
	}
	
	// ----------- vertex size tests -----------
	@Test
	public void testEmptyGraph() {
		assertEquals(0, graph.numNodes());
	}
	
	@Test
	public void testOneVertex() {
		graph.addNode(v1.getItem());
		assertEquals(1, graph.numNodes());
	}
	
	@Test
	public void testTwoVertex() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		assertEquals(2, graph.numNodes());
	}
	
	@Test
	public void testTwoEqVertex() {
		graph.addNode(v1.getItem());
		graph.addNode(v1.getItem());
		assertEquals(1, graph.numNodes());
	}
	
	// ----------- edge size tests -----------
	@Test
	public void testEmptyEdgeGraph() {
		assertEquals(0, graph.numEdges());
	}
	
	@Test
	public void testOneEdge() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(2, graph.numEdges());
	}
	
	@Test
	public void testTwoEdges() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addNode(v3.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.addEdge(v1.getItem(), v3.getItem(), null);
		assertEquals(4, graph.numEdges());
	}
	
	@Test
	public void testTwoEqEdges() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(2, graph.numEdges());
	}
	
	@Test
	public void testOneEdgeDirect() {
		graphD.addNode(v1.getItem());
		graphD.addNode(v2.getItem());
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(1, graphD.numEdges());
	}
	
	@Test
	public void testTwoEdgesDirect() {
		graphD.addNode(v1.getItem());
		graphD.addNode(v2.getItem());
		graphD.addNode(v3.getItem());
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
		graphD.addEdge(v1.getItem(), v3.getItem(), null);
		assertEquals(2, graphD.numEdges());
	}
	
	@Test
	public void testTwoEqEdgesDirect() {
		graphD.addNode(v1.getItem());
		graphD.addNode(v2.getItem());
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
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
		String[] expected = { v1.getItem() };
		graph.addNode(v1.getItem());
		assertArrayEquals(expected, graph.getNodes().toArray());
	}

	@Test
	public void testThreeGetVertex() {
		String[] expected = { v1.getItem(), v3.getItem(), v2.getItem() };
		graph.addNode(v1.getItem());
		graph.addNode(v3.getItem());
		graph.addNode(v2.getItem());
		assertArrayEquals(expected, graph.getNodes().toArray());
	}
	
	@Test
	public void testTwoGetVertex() {
		String[] expected = { v3.getItem(), v2.getItem() };
		graph.addNode(v1.getItem());
		graph.addNode(v3.getItem());
		graph.addNode(v2.getItem());
		graph.removeNode(v1.getItem());
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
		expected.add(new Edge<String, Integer>(v1.getItem(), v2.getItem(), null));
		expected.add(new Edge<String, Integer>(v2.getItem(), v1.getItem(), null));
		
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);

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
		expected.add(new Edge<String, Integer>(v1.getItem(), v2.getItem(), null));
		expected.add(new Edge<String, Integer>(v3.getItem(), v2.getItem(), null));
		expected.add(new Edge<String, Integer>(v2.getItem(), v1.getItem(), null));
		expected.add(new Edge<String, Integer>(v2.getItem(), v3.getItem(), null));
		
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addNode(v3.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.addEdge(v2.getItem(), v3.getItem(), null);

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
		expected.add(new Edge<String, Integer>(v1.getItem(), v2.getItem(), null));
		expected.add(new Edge<String, Integer>(v2.getItem(), v1.getItem(), null));
		
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addNode(v3.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.addEdge(v2.getItem(), v3.getItem(), null);
		graph.removeEdge(v2.getItem(), v3.getItem());
		
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
		expected.add(new Edge<String, Integer>(v1.getItem(), v2.getItem(), null));
		expected.add(new Edge<String, Integer>(v2.getItem(), v1.getItem(), null));
		
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addNode(v3.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.addEdge(v2.getItem(), v3.getItem(), null);
		graph.removeNode(v3.getItem());

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
		assertArrayEquals(expected, graph.getNeighbours(v1.getItem()).toArray());
	}
	
	@Test
	public void testOneGetNeighboursVertex() {
		String[] expected = {};
		graph.addNode(v1.getItem());
		assertArrayEquals(expected, graph.getNeighbours(v1.getItem()).toArray());
	}

	@Test
	public void testThreeGetNeighboursVertex() {
		String[] expected = { v3.getItem(), v2.getItem() };
		graph.addNode(v1.getItem());
		graph.addNode(v3.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.addEdge(v1.getItem(), v3.getItem(), null);
		assertArrayEquals(expected, graph.getNeighbours(v1.getItem()).toArray());
	}
	
	@Test
	public void testTwoGetNeighboursVertex() {
		String[] expected = { v3.getItem() };
		graph.addNode(v1.getItem());
		graph.addNode(v3.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.addEdge(v1.getItem(), v3.getItem(), null);
		graph.removeNode(v2.getItem());
		assertArrayEquals(expected, graph.getNeighbours(v1.getItem()).toArray());
	}
	
	// ----------- add edge label -----------
	@Test
	public void testNoIsLabelledInsert() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		assertTrue(graph.addEdge(v1.getItem(), v2.getItem(), null));
	}
	
	@Test
	public void testNoIsLabelledInsertValue() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		assertFalse(graph.addEdge(v1.getItem(), v2.getItem(), 8));
	}
	
	@Test
	public void testIsLabelledInsert() {
		graphL.addNode(v1.getItem());
		graphL.addNode(v2.getItem());
		assertFalse(graphL.addEdge(v1.getItem(), v2.getItem(), null));
	}
		
	@Test
	public void testIsLabelledInsertValue() {
		graphL.addNode(v1.getItem());
		graphL.addNode(v2.getItem());
		assertTrue(graphL.addEdge(v1.getItem(), v2.getItem(), 8));
	}
	
	@Test
	public void testNoDirection() {
		Integer expected = 8;
		graphL.addNode(v1.getItem());
		graphL.addNode(v2.getItem());
		graphL.addEdge(v1.getItem(), v2.getItem(), 8);
		assertEquals(expected, graphL.getLabel(v1.getItem(), v2.getItem()));
	}
	
	@Test
	public void testNoLabelledNoDirection() {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(null, graphL.getLabel(v1.getItem(), v2.getItem()));
	}
	
	@Test
	public void testNoEdge() {
		assertEquals(null, graphL.getLabel(v1.getItem(), v2.getItem()));
	}
}





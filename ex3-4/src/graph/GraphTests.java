package graph;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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
	private Node<String, Integer> v1;
	private Node<String, Integer> v2;
	private Node<String, Integer> v3;
	
	@Before
	public void createPriorityQueue() {
		graphDL = new Graph<String, Integer>(true, true);
		graphD = new Graph<String, Integer>(true, false);
		graphL = new Graph<String, Integer>(false, true);
		graph = new Graph<String, Integer>(false, false);

		v1 = new Node<>("Vertex 1");
		v2 = new Node<>("Vertex 2");
		v3 = new Node<>("Vertex 3");
	}

	// ----------- direct tests -----------
	@Test
	public void testIsDirect() throws Exception {
		assertTrue(graphD.isDirected());
	}

	@Test
	public void testIsNotDirect() throws Exception {
		assertFalse(graph.isDirected());
	}

	@Test
	public void testIsDirectEdges() throws Exception {
		graphD.addNode(v1.getItem());
		graphD.addNode(v2.getItem());
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(1, graphD.numEdges());
	}

	@Test
	public void testIsNotDirectEdges() throws Exception {
		graph = new Graph<String, Integer>(false, false);
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(2, graph.numEdges());
	}

	// ----------- labelled tests -----------
	@Test
	public void testIsLabelled() throws Exception {
		assertTrue(graphL.isLabelled());
	}

	@Test
	public void testIsNotLabelled() throws Exception {
		assertFalse(graph.isLabelled());
	}

	// ----------- add vertex tests -----------
	@Test
	public void testAddOneVertex() throws Exception {
		graph.addNode(v1.getItem());
		assertEquals(1, graph.numNodes());
	}

	@Test
	public void testAddTwoVertexes() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		assertEquals(2, graph.numNodes());
	}

	@Test
	public void testAddTwoEqVertexes() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v1.getItem());
		assertEquals(1, graph.numNodes());
	}

	// ----------- add edge tests -----------
	@Test
	public void testAddOneEdge() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(2, graph.numEdges());
	}

	@Test
	public void testAddTwoEdges() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addNode(v3.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.addEdge(v2.getItem(), v3.getItem(), null);
		assertEquals(4, graph.numEdges());
	}

	@Test
	public void testAddTwoEqEdges() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(2, graph.numEdges());
	}

	@Test
	public void testAddOneEdgeWithLabel() throws Exception {
		graphL.addNode(v1.getItem());
		graphL.addNode(v2.getItem());
		graphL.addEdge(v1.getItem(), v2.getItem(), 8);
		assertEquals(2, graphL.numEdges());
	}

	@Test
	public void testAddOneEdgeDirect() throws Exception {
		graphD.addNode(v1.getItem());
		graphD.addNode(v2.getItem());
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(1, graphD.numEdges());
	}

	@Test
	public void testAddTwoEdgesDirect() throws Exception {
		graphD.addNode(v1.getItem());
		graphD.addNode(v2.getItem());
		graphD.addNode(v3.getItem());
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
		graphD.addEdge(v2.getItem(), v3.getItem(), null);
		assertEquals(2, graphD.numEdges());
	}

	@Test
	public void testAddTwoEqEdgesDirect() throws Exception {
		graphD.addNode(v1.getItem());
		graphD.addNode(v2.getItem());
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(1, graphD.numEdges());
	}

	@Test
	public void testAddOneEdgeWithLabelDirect() throws Exception {
		graphDL.addNode(v1.getItem());
		graphDL.addNode(v2.getItem());
		graphDL.addEdge(v1.getItem(), v2.getItem(), 8);
		assertEquals(1, graphDL.numEdges());
	}

	// ----------- contain vertex tests -----------
	@Test
	public void testAddCheckVertex() throws Exception {
		graph.addNode(v1.getItem());
		assertTrue(graph.containsNode(v1.getItem()));
	}

	@Test
	public void testAddTwoCheckVertexes() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		assertTrue(graph.containsNode(v2.getItem()));
	}

	@Test
	public void testAddCheckNoVertex() throws Exception {
		assertFalse(graph.containsNode(v1.getItem()));
	}

	// ----------- contain edge tests -----------
	@Test
	public void testAddCheckEdge() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertTrue(graph.containsEdge(v1.getItem(), v2.getItem()));
	}

	@Test
	public void testAddTwoCheckEdges() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addNode(v3.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.addEdge(v1.getItem(), v3.getItem(), null);
		assertTrue(graph.containsEdge(v1.getItem(), v3.getItem()));
	}

	@Test
	public void testAddCheckNoEdge() throws Exception {
		assertFalse(graph.containsEdge(v1.getItem(), v2.getItem()));
	}

	@Test
	public void testAddCheckEdgeOtherDirection() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertTrue(graph.containsEdge(v2.getItem(), v1.getItem()));
	}

	@Test
	public void testDeleteCheckEdgeOtherDirection() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.removeEdge(v1.getItem(), v2.getItem());
		assertFalse(graph.containsEdge(v2.getItem(), v1.getItem()));
	}

	// ----------- remove vertex tests -----------
	@Test
	public void testRemoveVertex() throws Exception {
		graph.addNode(v1.getItem());
		assertTrue(graph.removeNode(v1.getItem()));
	}

	@Test
	public void testRemoveNoVertex() throws Exception {
		assertFalse(graph.removeNode(v1.getItem()));
	}

	@Test
	public void testRemoveAllVertexes() throws Exception {
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
	public void testRemoveEdge() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertTrue(graph.removeEdge(v1.getItem(), v2.getItem()));
	}

	@Test
	public void testRemoveOtherEdge() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertTrue(graph.removeEdge(v2.getItem(), v1.getItem()));
	}

	@Test
	public void testRemoveNoEdge() throws Exception {
		assertFalse(graph.removeEdge(v1.getItem(), v2.getItem()));
	}

	@Test
	public void testRemoveAllEdge() throws Exception {
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
	public void testEmptyGraph() throws Exception {
		assertEquals(0, graph.numNodes());
	}

	@Test
	public void testOneVertex() throws Exception {
		graph.addNode(v1.getItem());
		assertEquals(1, graph.numNodes());
	}

	@Test
	public void testTwoVertex() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		assertEquals(2, graph.numNodes());
	}

	@Test
	public void testTwoEqVertex() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v1.getItem());
		assertEquals(1, graph.numNodes());
	}

	// ----------- edge size tests -----------
	@Test
	public void testEmptyEdgeGraph() throws Exception {
		assertEquals(0, graph.numEdges());
	}

	@Test
	public void testOneEdge() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(2, graph.numEdges());
	}

	@Test
	public void testTwoEdges() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addNode(v3.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.addEdge(v1.getItem(), v3.getItem(), null);
		assertEquals(4, graph.numEdges());
	}

	@Test
	public void testTwoEqEdges() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(2, graph.numEdges());
	}

	@Test
	public void testOneEdgeDirect() throws Exception {
		graphD.addNode(v1.getItem());
		graphD.addNode(v2.getItem());
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(1, graphD.numEdges());
	}

	@Test
	public void testTwoEdgesDirect() throws Exception {
		graphD.addNode(v1.getItem());
		graphD.addNode(v2.getItem());
		graphD.addNode(v3.getItem());
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
		graphD.addEdge(v1.getItem(), v3.getItem(), null);
		assertEquals(2, graphD.numEdges());
	}

	@Test
	public void testTwoEqEdgesDirect() throws Exception {
		graphD.addNode(v1.getItem());
		graphD.addNode(v2.getItem());
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
		graphD.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(1, graphD.numEdges());
	}

	// ----------- get vertexes tests -----------
	@Test
	public void testEmptyGraphGetVertex() throws Exception {
		String[] expected = {};
		assertArrayEquals(expected, graph.getNodes().toArray());
	}

	@Test
	public void testOneGetVertex() throws Exception {
		String[] expected = { v1.getItem() };
		graph.addNode(v1.getItem());
		assertArrayEquals(expected, graph.getNodes().toArray());
	}

	@Test
	public void testThreeGetVertex() throws Exception {
		String[] expected = { v1.getItem(), v3.getItem(), v2.getItem() };
		graph.addNode(v1.getItem());
		graph.addNode(v3.getItem());
		graph.addNode(v2.getItem());
		assertArrayEquals(expected, graph.getNodes().toArray());
	}

	@Test
	public void testTwoGetVertex() throws Exception {
		String[] expected = { v3.getItem(), v2.getItem() };
		graph.addNode(v1.getItem());
		graph.addNode(v3.getItem());
		graph.addNode(v2.getItem());
		graph.removeNode(v1.getItem());
		assertArrayEquals(expected, graph.getNodes().toArray());
	}

	// ----------- get edges tests -----------
	@Test
	public void testEmptyGraphGetEdges() throws Exception {
		ArrayList<Edge<String, Integer>> expected = new ArrayList<Edge<String,Integer>>();
		assertArrayEquals(expected.toArray(), graph.getEdges().toArray());
	}

	@Test
	public void testOneGetEdge() throws Exception {
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
	public void testTwoGetEdges() throws Exception {
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
	public void testOneGetEdgesDeletionEdge() throws Exception {
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
	public void testOneGetEdgesDeletionVertex() throws Exception {
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
	public void testEmptyGraphGetNeighboursVertex() throws Exception {
		String[] expected = {};
		assertArrayEquals(expected, graph.getNeighbours(v1.getItem()).toArray());
	}

	@Test
	public void testOneGetNeighboursVertex() throws Exception {
		String[] expected = {};
		graph.addNode(v1.getItem());
		assertArrayEquals(expected, graph.getNeighbours(v1.getItem()).toArray());
	}

	@Test
	public void testThreeGetNeighboursVertex() throws Exception {
		String[] expected = { v3.getItem(), v2.getItem() };
		graph.addNode(v1.getItem());
		graph.addNode(v3.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		graph.addEdge(v1.getItem(), v3.getItem(), null);
		assertArrayEquals(expected, graph.getNeighbours(v1.getItem()).toArray());
	}

	@Test
	public void testTwoGetNeighboursVertex() throws Exception {
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
	public void testNoIsLabelledInsert() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		assertTrue(graph.addEdge(v1.getItem(), v2.getItem(), null));
	}

	@Test
	public void testNoIsLabelledInsertValue() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		try {
			graph.addEdge(v1.getItem(), v2.getItem(), 8);
			assertTrue(false);
		} catch (GraphException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testIsLabelledInsertNull() throws Exception {
		graphL.addNode(v1.getItem());
		graphL.addNode(v2.getItem());
		try {
			graphL.addEdge(v1.getItem(), v2.getItem(), null);
			assertTrue(false);
		} catch (GraphException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testIsLabelledInsertValue() throws Exception {
		graphL.addNode(v1.getItem());
		graphL.addNode(v2.getItem());
		assertTrue(graphL.addEdge(v1.getItem(), v2.getItem(), 8));
	}

	@Test
	public void testNoDirection() throws Exception {
		Integer expected = 8;
		graphL.addNode(v1.getItem());
		graphL.addNode(v2.getItem());
		graphL.addEdge(v1.getItem(), v2.getItem(), 8);
		assertEquals(expected, graphL.getLabel(v1.getItem(), v2.getItem()));
	}

	@Test
	public void testNoLabelledNoDirection() throws Exception {
		graph.addNode(v1.getItem());
		graph.addNode(v2.getItem());
		graph.addEdge(v1.getItem(), v2.getItem(), null);
		assertEquals(null, graphL.getLabel(v1.getItem(), v2.getItem()));
	}

	@Test
	public void testNoEdge() throws Exception {
		assertEquals(null, graphL.getLabel(v1.getItem(), v2.getItem()));
	}

	// ----------- get isVisited tests -----------
	@Test
	public void testIsVisited() throws Exception {
		assertFalse(v1.getIsVisited());
	}

	@Test
	public void testIsVisitedTrue() throws Exception {
		v1.setIsVisited(true);
		assertTrue(v1.getIsVisited());
	}
}
package priorityqueue;

import java.util.Comparator;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * It specifies a test suite for the Priority Queue library
 * To improve readability, Java methods' naming conventions are not fully
 * respected...
 * @author ferrero-merlino
 */
public class PriorityQueueTests {
  
  class IntegerComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer i1, Integer i2) {
      return i1.compareTo(i2);
    }
  }
  
  
  private Integer i1, i2, i3, i4;
  private PriorityQueue<Integer> priorityQueue;
  
  @Before
  public void createPriorityQueue() throws PriorityQueueException {
    i1 = 1;
    i2 = 3;
    i3 = 5;
    i4 = -1;
    priorityQueue = new PriorityQueue<>(new IntegerComparator());
  }
  
  @Test
  public void testIsEmptyZeroEl(){
    assertTrue(priorityQueue.empty());
  }
  
  @Test
  public void testIsEmptyOneEl() throws Exception {
    priorityQueue.push(i1);
    assertFalse(priorityQueue.empty());
  }
  
  @Test
  //It directly accesses the PriorityQueue instance variable priorityQueue.array
  public void testAddOneEl() throws Exception {
    priorityQueue.push(i1);
    assertEquals(i1, priorityQueue.array.get(0));
  }
  
  @Test
  //It directly accesses the PriorityQueue instance variable priorityQueue.array
  public void testAddThreeElAddedInOrder() throws Exception {
    Integer[] expectedArray = { i4, i1, i2, i3 };
    priorityQueue.push(i1);
    priorityQueue.push(i2);
    priorityQueue.push(i3);
    priorityQueue.push(i4);
    assertArrayEquals(expectedArray, priorityQueue.array.toArray());
  }

  @Test
  //It directly accesses the PriorityQueue instance variable priorityQueue.array
  public void testAddThreeElAddedInReverseOrder() throws Exception {
    Integer[] expectedArray = { i4, i2, i1, i3 };
    priorityQueue.push(i3);
    priorityQueue.push(i2);
    priorityQueue.push(i4);
    priorityQueue.push(i1);
    assertArrayEquals(expectedArray, priorityQueue.array.toArray());
  }

  @Test
  //It directly accesses the PriorityQueue instance variable priorityQueue.array
  public void testAddThreeElAllEqual() throws Exception {
    Integer[] expectedArray = { i2, i2, i2 };
    priorityQueue.push(i2);
    priorityQueue.push(i2);
    priorityQueue.push(i2);
    assertArrayEquals(expectedArray, priorityQueue.array.toArray());
  }

  @Test
  public void testContainsNoEl() throws Exception {
    assertFalse(priorityQueue.contains(i1));
  }

  @Test
  public void testContainsEl() throws Exception {
    priorityQueue.push(i1);
    assertTrue(priorityQueue.contains(i1));
  }

  @Test
  public void testContainsElRemoved() throws Exception {
    priorityQueue.push(i1);
    assertFalse(priorityQueue.contains(i2));
  }

  @Test
  public void testTopEl() throws Exception {
    priorityQueue.push(i1);
    assertEquals(i1, priorityQueue.top());
  }

  @Test
  public void testTopTwoEl() throws Exception {
    priorityQueue.push(i1);
    priorityQueue.push(i4);
    assertEquals(i4, priorityQueue.top());
  }

  @Test
  public void testTopNoEl() throws Exception {
    assertEquals(null, priorityQueue.top());
  }
  
  @Test
  public void testPopEl() throws Exception {
    priorityQueue.push(i1);
    priorityQueue.pop();
    assertTrue(priorityQueue.empty());
  }

  @Test
  public void testPopTwoEl() throws Exception {
    priorityQueue.push(i1);
    priorityQueue.push(i2);
    priorityQueue.pop();
    priorityQueue.pop();
    assertTrue(priorityQueue.empty());
  }

  @Test
  public void testPopElFirst() throws Exception {
    Integer[] expectedArray = { i1, i3, i2 };
    priorityQueue.push(i1);
    priorityQueue.push(i2);
    priorityQueue.push(i3);
    priorityQueue.push(i4);
    priorityQueue.pop();
    assertArrayEquals(expectedArray, priorityQueue.array.toArray());
  }

  @Test
  public void testRemoveElFirst() throws Exception {
    Integer[] expectedArray = { i1, i3, i2 };
    priorityQueue.push(i1);
    priorityQueue.push(i2);
    priorityQueue.push(i3);
    priorityQueue.push(i4);
    priorityQueue.remove(i4);
    assertArrayEquals(expectedArray, priorityQueue.array.toArray());
  }

  @Test
  public void testRemoveNoEl() throws Exception {
    Integer[] expectedArray = { i1 };
    priorityQueue.push(i1);
    priorityQueue.remove(i3);
    assertArrayEquals(expectedArray, priorityQueue.array.toArray());
  }
  
  @Test
  public void testRemoveNoElBool() throws Exception {
    assertFalse(priorityQueue.remove(i1));
  }
  
  @Test
  public void testRemoveElBool() throws Exception {
    priorityQueue.push(i1);
    assertTrue(priorityQueue.remove(i1));
  }
}


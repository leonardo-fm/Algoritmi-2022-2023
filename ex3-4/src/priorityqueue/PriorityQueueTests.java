package priorityqueue;

import java.util.Comparator;
import java.util.Random;

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

  private int randomValues[] = {52, 8, -9, 35, 15, 68, 6, 18, 78, 88, 45, 14, 56, 156, 15, 165};
  
  @Before
  public void createPriorityQueue() {
    i1 = 1;
    i2 = 3;
    i3 = 5;
    i4 = -1;
    priorityQueue = new PriorityQueue<Integer>(new IntegerComparator());
  }
  
  @Test
  public void testIsEmptyZeroEl() {
    assertTrue(priorityQueue.empty());
  }
  
  @Test
  public void testIsEmptyOneEl() throws Exception {
    priorityQueue.push(i1);
    assertFalse(priorityQueue.empty());
  }
  
  @Test
  public void testAddOneEl() throws Exception {
    priorityQueue.push(i1);
    assertEquals(i1, priorityQueue.array.get(0));
  }
  
  @Test
  public void testAddThreeElAddedInOrder() throws Exception {
    Integer[] expectedArray = { i4, i1, i3, i2 };
    priorityQueue.push(i1);
    priorityQueue.push(i2);
    priorityQueue.push(i3);
    priorityQueue.push(i4);
    assertArrayEquals(expectedArray, priorityQueue.array.toArray());
  }

  @Test
  public void testAddThreeElAddedInReverseOrder() throws Exception {
    Integer[] expectedArray = { i4, i1, i2, i3 };
    priorityQueue.push(i3);
    priorityQueue.push(i2);
    priorityQueue.push(i4);
    priorityQueue.push(i1);
    assertArrayEquals(expectedArray, priorityQueue.array.toArray());
  }

  @Test
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
    Integer[] expectedArray = { i1, i2, i3 };
    priorityQueue.push(i1);
    priorityQueue.push(i2);
    priorityQueue.push(i3);
    priorityQueue.push(i4);
    priorityQueue.pop();
    assertArrayEquals(expectedArray, priorityQueue.array.toArray());
  }

  @Test
  public void testRemoveElFirst() throws Exception {
    Integer[] expectedArray = { i1, i2, i3 };
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

  @Test
  public void testAddThreeElAllEqualRemoveOne() throws Exception {
    priorityQueue.push(i2);
    priorityQueue.push(i2);
    priorityQueue.push(i2);
    priorityQueue.remove(i2);
    assertTrue(priorityQueue.contains(i2));
  }

  //{52, 8, -9, 35, 15, 68, 6, 18, 78, 88, 45, 14, 56, 156, 15, 165}
  @Test
  public void testAddLotOfNumber() throws Exception {
    for (int i = 0; i < randomValues.length; i++) {
      priorityQueue.push(randomValues[i]);
    }
    assertEquals(-9, (int)priorityQueue.top());
  }

  @Test
  public void testAddLotOfNumberAndLowerAfter() throws Exception {
    for (int i = 0; i < randomValues.length; i++) {
      priorityQueue.push(randomValues[i]);
    }
    priorityQueue.push(-100);
    assertEquals(-100, (int)priorityQueue.top());
  }

  @Test
  public void testAddLotOfNumberAndPop() throws Exception {
    for (int i = 0; i < randomValues.length; i++) {
      priorityQueue.push(randomValues[i]);
    }
    priorityQueue.pop();
    priorityQueue.pop();
    assertEquals(8, (int)priorityQueue.top());
  }

  @Test
  public void testAddLotOfNumberAndSameLower() throws Exception {
    for (int i = 0; i < randomValues.length; i++) {
      priorityQueue.push(randomValues[i]);
    }
    priorityQueue.push(-9);
    assertEquals(-9, (int)priorityQueue.top());
  }

  @Test
  public void testAddLotOfNumberAndSameLowerAndPop() throws Exception {
    for (int i = 0; i < randomValues.length; i++) {
      priorityQueue.push(randomValues[i]);
    }
    priorityQueue.push(-9);
    priorityQueue.pop();
    assertEquals(-9, (int)priorityQueue.top());
  }

  @Test
  public void testAddRandomNumbers10() throws Exception {
    int minNumber = Integer.MAX_VALUE;
    Random random = new Random();
    for (int i = 0; i < 10; i++) {
      int currentNumber = random.nextInt();
      if (currentNumber < minNumber) minNumber = currentNumber;
      priorityQueue.push(currentNumber);
    }
    assertEquals(minNumber, (int)priorityQueue.top());
  }

  @Test
  public void testAddRandomNumbers100() throws Exception {
    int minNumber = Integer.MAX_VALUE;
    Random random = new Random();
    for (int i = 0; i < 100; i++) {
      int currentNumber = random.nextInt();
      if (currentNumber < minNumber) minNumber = currentNumber;
      priorityQueue.push(currentNumber);
    }
    assertEquals(minNumber, (int)priorityQueue.top());
  }

  @Test
  public void testAddRandomNumbers1000() throws Exception {
    int minNumber = Integer.MAX_VALUE;
    Random random = new Random();
    for (int i = 0; i < 1000; i++) {
      int currentNumber = random.nextInt();
      if (currentNumber < minNumber) minNumber = currentNumber;
      priorityQueue.push(currentNumber);
    }
    assertEquals(minNumber, (int)priorityQueue.top());
  }

  @Test
  public void testAddRandomNumbers10000() throws Exception {
    int minNumber = Integer.MAX_VALUE;
    Random random = new Random();
    for (int i = 0; i < 10000; i++) {
      int currentNumber = random.nextInt();
      if (currentNumber < minNumber) minNumber = currentNumber;
      priorityQueue.push(currentNumber);
    }
    assertEquals(minNumber, (int)priorityQueue.top());
  }

  @Test
  public void testAddRandomNumbers10000AndRemoveRandom100() throws Exception {
    int numberToBeDeleted[] = new int[100];
    int minNumber = Integer.MAX_VALUE;
    Random random = new Random();
    int j = 0;
    for (int i = 0; i < 10000; i++) {
      int currentNumber = random.nextInt();
      if (currentNumber < minNumber) minNumber = currentNumber;
      else if (j < numberToBeDeleted.length) numberToBeDeleted[j++] = currentNumber;
      priorityQueue.push(currentNumber);
    }
    for (int i = 0; i < numberToBeDeleted.length; i++) {
      priorityQueue.remove(numberToBeDeleted[i]);
    }
    assertEquals(minNumber, (int)priorityQueue.top());
  }

  @Test
  public void testAddRandomNumbers500000AndRemoveRandom10000() throws Exception {
    int numberToBeDeleted[] = new int[10000];
    int minNumber = Integer.MAX_VALUE;
    Random random = new Random();
    int j = 0;
    for (int i = 0; i < 500000; i++) {
      int currentNumber = random.nextInt();
      if (currentNumber < minNumber) minNumber = currentNumber;
      else if (j < numberToBeDeleted.length) numberToBeDeleted[j++] = currentNumber;
      priorityQueue.push(currentNumber);
    }
    for (int i = 0; i < numberToBeDeleted.length; i++) {
      priorityQueue.remove(numberToBeDeleted[i]);
    }
    assertEquals(minNumber, (int)priorityQueue.top());
  }
}
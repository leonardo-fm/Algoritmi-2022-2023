package priorityqueue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * It represents a priority queue. Elements in the queue are always ordered according
 * to a criterion specified by a comparator at creation time.
 * @author ferrero-merlino
 * @param <T>: type of the priority queue elements
 */
public class PriorityQueue<T> implements AbstractQueue {
  ArrayList<T> array = null;
  Comparator<? super T> comparator = null;
  HashMap<Integer, T> elementsMap = new HashMap<Integer, T>();
  
  /**
   * It creates an empty priority queue.
   * It accepts as input a comparator implementing the 
   * precedence relation between the queue elements. 
   * @param comparator: a comparator implementing the precedence relation between the queue elements.
   * @throws PriorityQueueException if the parameter is null.
   */
  public PriorityQueue(Comparator<? super T> comparator) throws OrderedArrayException{
    if(comparator == null) throw new PriorityQueueException("PriorityQueue constructor: comparator parameter cannot be null");
    this.array = new ArrayList<>(); 
    this.comparator = comparator;
  }
 
  /**
  * @return true iff this priority queue is empty
  */
  public boolean isEmpty() {
    return (this.array).isEmpty();
  }

  /**
  * @return true iff the element is in the queue
  * @throws priorityqueue.PriorityQueueException iff the parameter is null
  */
  public boolean contains(T e) {
    if(e == null) {
      throw new PriorityQueueException("push: element parameter cannot be null");
    }
    return getFromHashMap(e) != null;
  }
 
  /**
  * @return true iff the item has been push
  * @throws priorityqueue.PriorityQueueException iff the parameter is null
  */
  public boolean push(T e) throws PriorityQueueException {
    if(e == null) {
      throw new PriorityQueueException("push: element parameter cannot be null");
    }
    
    boolean added = (this.array).add(e);
    if (added == false) return false;
    added = addToHashMap(e);
    if (added == false) {
      remove(e);
      return false;
    }
    int eIndex = (this.array).size() - 1;
    bubbleUp(e, eIndex);
    return added;
  }

  /**
  * @return: the first element, if the queue is empty return null.
  */
  public T top(){
    if ((this.array).size > 0) {
      return (this.array).get(0);
    }
    return null;
  }

  /**
  * @throws priorityqueue.PriorityQueueException iff the queue size is 0
  */
  public void pop(){
    if ((this.array).size == 0) {
      throw new PriorityQueueException("pop: no elements in the queue");
    } else if ((this.array).size == 1) {
      removeFromHashMap((this.array).get(0));
      (this.array).remove(0);
      return;  
    }
    
    int lastItemIndex = (this.array).size() - 1;
    swap(0, lastItemIndex);
    removeFromHashMap((this.array).get(lastItemIndex));
    (this.array).remove(lastItemIndex);
    sink(top(), 0);
  }

  /**
  * @return true iff the element has been removed
  * @throws priorityqueue.PriorityQueueException iff the parameter is null
  */
  public boolean remove(T e) {
    if(e == null) {
      throw new PriorityQueueException("push: element parameter cannot be null");
    }

    if ((this.array).size == 0 || !contains(e)) {
      return false;
    }
    
    int eIndex = (this.array).indexOf(e);
    swap(eIndex, (this.array).size() - 1);
    removeFromHashMap(e);
    (this.array).remove((this.array).size() - 1);

    if (eIndex == 0 || (this.arra).compare((this.array).get(eIndex), (this.array).get((eIndex - 1) / 2) >= 0) {
      sink((this.array).get(eIndex), eIndex);
    } else {
      bubbleUp((this.array).get(eIndex), eIndex);
    }

    return true;
  }

  // Given two indexs swap the elements
  private void swap(int dest, int source) {
    T tmp = (this.array).get(dest);
    (this.array).set(dest, (this.array).get(source));
    (this.array).set(source, tmp);
  }

  // bubbleup an element to the queue
  private void bubbleUp(T e, int eIndex) {
    if (eIndex == 0) {
      return;
    }
    int fatherIndex = (eIndex - 1) / 2;
    if ((this.comparator).compare(e, (this.array).get(fatherIndex)) > 0) {
      swap(eIndex, fatherIndex);
      bubbleUp(e, fatherIndex);
    }
  }

  // Sink an element to the queue
  private void sink(T e, int eIndex) {
    int leftElementIndex = (eIndex * 2) + 1;
    int rightElementIndex = leftElementIndex + 1;    
    if (leftElementIndex >= (this.array).size()) {
      return;
    }
    boolean eGreaterL =  (this.comparator).compare(e, (this.array).get(leftElementIndex)) > 0; 
    boolean eGreaterR = (this.comparator).compare(e, (this.array).get(rightElementIndex)) > 0;
    if (eGreaterL && eGreaterR) {
      if ((this.comparator).compare((this.array).get(leftElementIndex), (this.array).get(rightElementIndex)) >= 0) {
        // L is greater or equal then R
        swap(eIndex, leftElementIndex);
        sink(e, leftElementIndex);
      } else {
        // R is greater of L 
        swap(eIndex, rightElementIndex);
        sink(e, rightElementIndex);
      }
    } else if (eGreaterL) {
      swap(eIndex, leftElementIndex);
      sink(e, leftElementIndex);
    } else if (eGreaterR) {
      swap(eIndex, rightElementIndex);
      sink(e, rightElementIndex);
    }
  }

  // Add an element to the hash map
  private boolean addToHashMap(T e) {
    T el = elementsMap.put(hashCode(e), e);
    return el != null;
  }

  // remove an element from the hash map
  private boolean removeFromHashMap(T e) {
    T el = elementsMap.remove(hashCode(e));
    return el != null;
  }

  // Return an element iff present in the hash map, otherwise null
  private T getFromHashMap(T e) {
    return elementsMap.get(hashCode(e));
  }
}

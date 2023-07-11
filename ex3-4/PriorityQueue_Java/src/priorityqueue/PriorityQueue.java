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
public class PriorityQueue<T> implements AbstractQueue<T> {
  ArrayList<T> array = null;
  Comparator<? super T> comparator = null;
  HashMap<Integer, Record<T>> elementsMap = new HashMap<Integer, Record<T>>();
  
  /**
   * It creates an empty priority queue.
   * It accepts as input a comparator implementing the 
   * precedence relation between the queue elements. 
   * @param comparator: a comparator implementing the precedence relation between the queue elements.
   * @throws PriorityQueueException if the parameter is null.
   */
  public PriorityQueue(Comparator<? super T> comparator) throws PriorityQueueException {
    if (comparator == null) throw new PriorityQueueException("PriorityQueue constructor: comparator parameter cannot be null");
    this.array = new ArrayList<T>(); 
    this.comparator = comparator;
    this.elementsMap = new HashMap<Integer, Record<T>>();
  }
 
  /**
   * Return a boolean that indicate if the queue is empty
   * @return true iff this priority queue is empty
   */
  public boolean empty() {
    return (this.array).isEmpty();
  }
 
  /**
   * Push a new element into the queue
   * @return true iff the item has been push false otherwhise
   */
  public boolean push(T e) {
    if (e == null) {
      return false;
    }

    boolean added = (this.array).add(e);
    if (added == false) return false;
    int eIndex = (this.array).size() - 1;
    addToHashMap(e, eIndex);

    bubbleUp(e, eIndex);
    return added;
  }

  /**
   * Return a boolean that indicate if en element is present in the queue
   * @return true iff the element is in the queue false otherwise
   */
  public boolean contains(T e) {
    if (e == null) {
      return false;
    }

    return getFromHashMap(e) != null;
  }

  /**
   * return the first element in the queue
   * @return: the first element, iff the queue is empty return null.
   */
  public T top(){
    if (!empty()) {
      return (this.array).get(0);
    }
    return null;
  }

  /**
   * Remove the top element in the queue
   */
  public void pop(){
    if (empty()) {
      return;
    }
    
    int lastItemIndex = (this.array).size() - 1;
    swap(0, lastItemIndex);
    removeFromHashMap((this.array).get(lastItemIndex), lastItemIndex);
    (this.array).remove(lastItemIndex);
    sink(top(), 0);
  }

  /**
   * Remove an element in the queue iff present
   * @return true iff the element has been removed
   */
  public boolean remove(T e) {
    if (e == null) {
      return false;
    }

    if ((this.array).size() == 0 || !contains(e)) {
      return false;
    }
    
    int eIndex = getIndexHashMap(e);
    swap(eIndex, (this.array).size() - 1);
    removeFromHashMap(e, eIndex);
    (this.array).remove((this.array).size() - 1);
    if (empty()) {
      return true;
    }

    if (eIndex == 0 || (this.comparator).compare((this.array).get(eIndex), (this.array).get((eIndex - 1) / 2)) >= 0) {
      sink((this.array).get(eIndex), eIndex);
    } else {
      bubbleUp((this.array).get(eIndex), eIndex);
    }

    return true;
  }

  /**
   * Given two indexs swap the elements
   * @param destinationIndex
   * @param sourceIndex
   */
  private void swap(int destinationIndex, int sourceIndex) {
    T tmp = (this.array).get(destinationIndex);
    (this.array).set(destinationIndex, (this.array).get(sourceIndex));
    (this.array).set(sourceIndex, tmp);
    changeIndexHashMap((this.array).get(destinationIndex), sourceIndex, destinationIndex);
    changeIndexHashMap((this.array).get(sourceIndex), destinationIndex, sourceIndex);
  }

  /**
   * Bubbleup an element to the queue
   * @param e
   * @param eIndex
   */
  private void bubbleUp(T e, int eIndex) {
    if (eIndex == 0 || (this.array).isEmpty()) {
      return;
    }

    int fatherIndex = (eIndex - 1) / 2;
    if ((this.comparator).compare((this.array).get(fatherIndex), e) > 0) {
      swap(eIndex, fatherIndex);
      bubbleUp(e, fatherIndex);
    }
  }

  /**
   * Sink an element to the queue
   * @param e
   * @param eIndex
   */
  private void sink(T e, int eIndex) {
    int leftElementIndex = (eIndex * 2) + 1;
    int rightElementIndex = leftElementIndex + 1;    
    if (leftElementIndex >= ((this.array).size() - 1)) {
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

  /**
   * Add an element to the hash map 
   * if element already present add ammount of that item
   * @param e
   * @param index index of the element e in the array
   * @return true iff added false otherwise
   */
  private boolean addToHashMap(T e, int index) {
    Record<T> record = getFromHashMap(e);
    if (record != null) {
      record.setAmmount(record.getAmmount() + 1);
      record.addIndex(index);
    } else {
      record = new Record<T>(e, index);
    }
    elementsMap.put(record.getKey(), record);
    return true;
  }

  /**
   * Remove an element from the hash map
   * if element.ammount > 1 remove 1 to the total ammount
   * @param e
   * @param index index of the element e in the array
   * @return true iff removed false otherwise
   */
  private boolean removeFromHashMap(T e, int index) {
    Record<T> record = getFromHashMap(e);
    if (record.getAmmount() > 1) {
      record.setAmmount(record.getAmmount() - 1);
      record.removeIndex(index);
      elementsMap.put(record.getKey(), record);
      return true;
    } else {
      elementsMap.remove(e.hashCode());
      return record != null;
    }
  }

  /**
   * Return an element from the hash map
   * @param e
   * @return iff present element T otherwhise null
   */
  private Record<T> getFromHashMap(T e) {
    return elementsMap.get(e.hashCode());
  }

  /**
   * Change the index of an element with a new one
   * @param e
   * @param oldIndex
   * @param newIndex
   */
  private void changeIndexHashMap(T e, int oldIndex, int newIndex) {
    Record<T> record = elementsMap.get(e.hashCode());
    record.removeIndex(oldIndex);
    record.addIndex(newIndex);
  }

  /**
   * Return the first index of an element
   * @param e
   */
  private Integer getIndexHashMap(T e) {
    Record<T> record = elementsMap.get(e.hashCode());
    return (record.getIndexs()).first();
  }
}

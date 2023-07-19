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
  HashMap<Integer, PQItem<T>> elementsMap = null;

  /**
   * It creates an empty priority queue.
   * It accepts as input a comparator implementing the 
   * precedence relation between the queue elements. 
   * @param comparator: a comparator implementing the precedence relation between the queue elements.
   */
  public PriorityQueue(Comparator<? super T> comparator) {
    this.array = new ArrayList<>();
    this.comparator = comparator;
    this.elementsMap = new HashMap<>();
  }

  /**
   * Return the last index of the array
   * @return the last index of the array
   */
  public Integer getLastIndex() {
    if (empty()) return 0;
    return (this.array).size() - 1;
  }

  /**
   * Return a boolean that indicate if the queue is empty
   * @return true iff this priority queue is empty
   */
  @Override
  public boolean empty() {
    return (this.array).isEmpty();
  }
 
  /**
   * Push a new element into the queue
   * @return true iff the item has been push false otherwise
   */
  @Override
  public boolean push(T e) {
    if (e == null) return false;

    boolean added = (this.array).add(e);
    if (added) {
      int eIndex = getLastIndex();
      addToHashMap(e, eIndex);
      bubbleUp(e, eIndex);
    }
    return added;
  }

  /**
   * Return a boolean that indicate if en element is present in the queue
   * @return true iff the element is in the queue false otherwise
   */
  @Override
  public boolean contains(T e) {
    if (e == null) return false;

    return getFromHashMap(e) != null;
  }

  /**
   * return the first element in the queue
   * @return: the first element, iff the queue is empty return null.
   */
  @Override
  public T top() {
    if (empty()) return null;

    return (this.array).get(0);
  }

  /**
   * Remove the top element in the queue
   */
  @Override
  public void pop()  {
    if (empty()) return;

    int lastItemIndex = getLastIndex();
    swap(0, lastItemIndex);
    removeFromHashMap((this.array).get(lastItemIndex), lastItemIndex);
    (this.array).remove(lastItemIndex);
    if (lastItemIndex != 0)
      sink(top(), 0);
  }

  /**
   * Remove an element in the queue iff present
   * @return true iff the element has been removed
   */
  @Override
  public boolean remove(T e) {
    if (e == null || !contains(e)) return false;

    int lastItemIndex = getLastIndex();
    int eIndex = getIndexHashMap(e);
    swap(eIndex, lastItemIndex);
    removeFromHashMap(e, eIndex);
    (this.array).remove(lastItemIndex);
    if (lastItemIndex != 0) {
      T currentItem = (this.array).get(eIndex);
      if (eIndex == 0 || (this.comparator).compare(currentItem, (this.array).get((eIndex - 1) / 2)) >= 0) {
        sink(currentItem, eIndex);
      } else {
        bubbleUp(currentItem, eIndex);
      }
    }
    return true;
  }

  /**
   * Given two indexs swap the elements
   * @param destinationIndex
   * @param sourceIndex
   */
  private void swap(int destinationIndex, int sourceIndex) {
    int lastItemIndex = getLastIndex();
    if (destinationIndex < 0 || destinationIndex > lastItemIndex
            || sourceIndex < 0 || sourceIndex > lastItemIndex) return;

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
    if (e == null || eIndex < 0 || eIndex > getLastIndex()
            || eIndex == 0 || (this.array).isEmpty()) return;

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
    if (e == null || eIndex < 0 || eIndex > getLastIndex()) return;

    int leftElementIndex = (eIndex * 2) + 1;
    int rightElementIndex = leftElementIndex + 1;
    if (rightElementIndex > getLastIndex()) return;

    T leftElement = (this.array).get(leftElementIndex);
    T rightElement = (this.array).get(rightElementIndex);

    boolean lLessR = (this.comparator).compare(leftElement, rightElement) < 0;
    if (lLessR) {
      boolean eGreaterL = (this.comparator).compare(e, leftElement) > 0;
      if (eGreaterL) {
        swap(eIndex, leftElementIndex);
        sink(e, leftElementIndex);
      } else {
        boolean eGreaterR = (this.comparator).compare(e, rightElement) > 0;
        if (eGreaterR) {
          swap(eIndex, rightElementIndex);
          sink(e, rightElementIndex);
        }
      }
    } else {
      boolean eGreaterR = (this.comparator).compare(e, rightElement) > 0;
      if (eGreaterR) {
        swap(eIndex, rightElementIndex);
        sink(e, rightElementIndex);
      } else {
        boolean eGreaterL = (this.comparator).compare(e, leftElement) > 0;
        if (eGreaterL) {
          swap(eIndex, leftElementIndex);
          sink(e, leftElementIndex);
        }
      }
    }
  }

  /**
   * Add an element to the hash map 
   * if element already present add amount of that item
   * @param e
   * @param index index of the element e in the array
   * @return true iff added false otherwise
   */
  private boolean addToHashMap(T e, int index) {
    if (e == null || index < 0 || index > getLastIndex()) return false;

    PQItem<T> PQItem = getFromHashMap(e);
    if (PQItem != null) {
      PQItem.setAmount(PQItem.getAmount() + 1);
      PQItem.addIndex(index);
    } else {
      PQItem = new PQItem<>(e, index);
    }
    elementsMap.put(PQItem.getKey(), PQItem);
    return true;
  }

  /**
   * Remove an element from the hash map
   * if amount of element > 1 remove 1 to the total amount
   * @param e
   * @param index index of the element e in the array
   * @return true iff removed false otherwise
   */
  private boolean removeFromHashMap(T e, int index) {
    if (e == null || !contains(e) || index < 0 || index > getLastIndex()) return false;

    PQItem<T> PQItem = getFromHashMap(e);
    if (PQItem.getAmount() > 1) {
      PQItem.setAmount(PQItem.getAmount() - 1);
      PQItem.removeIndex(index);
      elementsMap.put(PQItem.getKey(), PQItem);
      return true;
    } else {
      elementsMap.remove(e.hashCode());
      return PQItem != null;
    }
  }

  /**
   * Return an element from the hash map
   * @param e
   * @return iff present element T otherwise null
   */
  private PQItem<T> getFromHashMap(T e) {
    if (e == null) return null;

    return elementsMap.get(e.hashCode());
  }

  /**
   * Change the index of an element with a new one
   * @param e
   * @param oldIndex
   * @param newIndex
   */
  private void changeIndexHashMap(T e, int oldIndex, int newIndex) {
    int lastIndex = getLastIndex();
    if (e == null || !contains(e)
          || oldIndex < 0 || oldIndex > lastIndex
            || newIndex < 0 || newIndex > lastIndex) return;

    PQItem<T> PQItem = elementsMap.get(e.hashCode());
    PQItem.removeIndex(oldIndex);
    PQItem.addIndex(newIndex);
  }

  /**
   * Return the first index of an element
   * @param e
   * @return the first index of an item otherwise -1
   */
  private Integer getIndexHashMap(T e) {
    if (e == null || !contains(e)) return -1;

    PQItem<T> PQItem = elementsMap.get(e.hashCode());
    return (PQItem.getIndexes()).first();
  }
}
package priorityqueue;

import java.util.TreeSet;

/**
 * This class is used inside the Priority Queue library for the hash map
 */
public class Record<T> {
    /**
     * The value of the Generic<T> given by the user
     */
    private T value;

    /**
     * The amount of instance with the same value in the hash map
     */
    private Integer amount;

    /**
     * The index/es of the element/s in the queue
     */
    private TreeSet<Integer> indexes;

    /**
     * The key of the item for the hash map
     */
    private Integer key;

    /**
     * Return a class with the given value, an amount of 1 and
     * a key generate using value.hashCode() function
     * @param value: the value given by the user
     */
    public Record(T value, Integer index) {
        this.value = value;
        this.amount = 1;
        this.indexes = new TreeSet<>();
        (this.indexes).add(index);
        this.key = value.hashCode();
    }

    /**
     * @return the amount
     */
    public Integer getAmount() {
        return this.amount;
    }
    
    /**
     * @param newAmount: the new amount to set
     */
    public void setAmount(Integer newAmount) {
        this.amount = newAmount;
    }

    /**
     * @return the index/es
     */
    public TreeSet<Integer> getIndexes() {
        return this.indexes;
    }

    /**
     * @return the key for the hash map
     */
    public Integer getKey() {
        return this.key;
    }

    /**
     * @param newIndex: the new index to add
     */
    public void addIndex(Integer newIndex) {
        (this.indexes).add(newIndex);
    }

    /**
     * @param indexToBeRemoved: the new to be removed
     */
    public void removeIndex(Integer indexToBeRemoved) {
        (this.indexes).remove(indexToBeRemoved);
    }
}

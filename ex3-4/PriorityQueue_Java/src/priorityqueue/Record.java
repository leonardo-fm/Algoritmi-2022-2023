package priorityqueue;

import java.util.TreeSet;

/**
 * This calss is used inside the Priority Queue library for the hash map
 */
public class Record<T> {
    /**
     * The value of the Generic<T> given by the user
     */
    private T value;

    /**
     * The ammount of istances whit the same value in the hash map
     */
    private Integer ammount;

    /**
     * The index/s of the element/s in the queue
     */
    private TreeSet<Integer> indexs;

    /**
     * The key of the item for the hash map
     */
    private Integer key;

    /**
     * Return a class with the given value, an ammount of 1 and 
     * a key generate using value.hashCode() function
     * @param value: the value given by the user
     */
    public Record(T value, Integer index) {
        this.value = value;
        this.ammount = 1;
        this.indexs = new TreeSet<Integer>();
        (this.indexs).add(index);
        this.key = value.hashCode();
    }

    /**
     * @retrun the value
     */
    public T getValue() {
        return this.value;
    }

    /**
     * @return the ammount
     */
    public Integer getAmmount() {
        return this.ammount;
    }
    
    /**
     * @param newAmmount: the new ammount to set
     */
    public void setAmmount(Integer newAmmount) {
        this.ammount = newAmmount;
    }

    /**
     * @return the index/s
     */
    public TreeSet<Integer> getIndexs() {
        return this.indexs;
    }

    /**
     * @return the key for the hash map
     */
    public Integer getKey() {
        return this.key;
    }

    /**
     * @param newIndexs: the new index to add
     */
    public void addIndex(Integer newIndex) {
        (this.indexs).add(newIndex);
    }

    /**
     * @param indexsToBeRemoved: the new to be removed
     */
    public void removeIndex(Integer indexsToBeRemoved) {
        (this.indexs).remove(indexsToBeRemoved);
    }
}

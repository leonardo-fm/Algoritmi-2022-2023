package priorityqueue;

public class Record<T> {
    private T value;
    private Integer ammount;
    private Integer key;

    public Record(T value) {
        this.value = value;
        this.ammount = 1;
        this.key = value.hashCode();
    }

    public T getValue() {
        return this.value;
    }

    public Integer getAmmount() {
        return this.ammount;
    }

    public void setAmmount(Integer newAmmount) {
        this.ammount = newAmmount;
    }

    public Integer getKey() {
        return this.key;
    }
}

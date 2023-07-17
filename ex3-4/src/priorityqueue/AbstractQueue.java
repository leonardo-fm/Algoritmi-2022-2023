package priorityqueue;

/**
 * Interface use to implement the priority queue
 */
public interface AbstractQueue<T> {
    // controlla se la coda è vuota -- O(1)
    public boolean empty(); 
    
    // aggiunge un elemento alla coda -- O(logN)
    public boolean push(T e) throws PriorityQueueException;
    
    // controlla se un elemento è in coda -- O(1)
    public boolean contains(T e) throws PriorityQueueException;
    
    // accede all'elemento in cima alla coda -- O(1)
    public T top(); 
    
    // rimuove l'elemento in cima alla coda -- O(logN)
    public void pop() throws PriorityQueueException;
    
    // rimuove un elemento se presente in coda -- O(logN)
    public boolean remove(T e) throws PriorityQueueException;
  };
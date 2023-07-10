package priorityqueue;

public interface AbstractQueue<T> {
    // controlla se la coda è vuota -- O(1)
    public boolean empty(); 
    
    // aggiunge un elemento alla coda -- O(logN)
    public boolean push(T e); 
    
    // controlla se un elemento è in coda -- O(1)
    public boolean contains(T e); 
    
    // accede all'elemento in cima alla coda -- O(1)
    public T top(); 
    
    // rimuove l'elemento in cima alla coda -- O(logN)
    public void pop(); 
    
    // rimuove un elemento se presente in coda -- O(logN)
    public boolean remove(T e); 
  };
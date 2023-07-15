import java.util.Comparator;
import graph.AbstractEdge;

public class EdgeDoubleComparator<V, L> implements Comparator<AbstractEdge<V, L>> {
  	public int compare(AbstractEdge<V, L> e1, AbstractEdge<V, L> e2) {
		return ((Double) e1.getLabel()).compareTo((Double) e2.getLabel());
	}	
}


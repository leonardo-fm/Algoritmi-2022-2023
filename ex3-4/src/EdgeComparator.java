import java.util.Comparator;
import graph.AbstractEdge;

public class EdgeComparator implements Comparator<AbstractEdge<String, Double>> {
  	private static int compare(AbstractEdge<String, Double> e1, AbstractEdge<String, Double> e2) {
		return e1.getLabel().compareTo(e2.getLabel());
	}	
}


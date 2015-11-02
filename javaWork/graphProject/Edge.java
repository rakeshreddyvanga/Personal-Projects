package graphProject;

public class Edge implements Comparable<Edge>{
 private Node from;
 private Node to;
 private int cost;
 
 
 public Edge(Node from, Node to, int cost) {
	 this.from = from;
	 this.to = to;
	 this.cost = cost;
 }
 
 public Node getFrom() {
	return from;
}

public Node getTo() {
	return to;
}
 public int getCost() {
	 return cost;
 }

/**
 * used for ordering edges based on it's weight
 */
@Override
public int compareTo(Edge edge) {
	return this.cost - edge.cost;
}
}

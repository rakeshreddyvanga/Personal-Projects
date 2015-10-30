package graphProject;

public class Edge implements Comparable<Edge>{
 private Node from;
 public Node getFrom() {
	return from;
}

public Node getTo() {
	return to;
}


private Node to;
 private int cost;
 
 public Edge(Node from, Node to, int cost) {
	 this.from = from;
	 this.to = to;
	 this.cost = cost;
 }
 
 public int getCost() {
	 return cost;
 }


@Override
public int compareTo(Edge edge) {
	if(this.cost == edge.cost)
		return 0;
	return (this.cost > edge.cost) ? 1 : -1;
}
}

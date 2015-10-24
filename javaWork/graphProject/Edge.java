package graphProject;

public class Edge {
 private Node from;
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
}

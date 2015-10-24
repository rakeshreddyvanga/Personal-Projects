package graphProject;

public interface IGraph {
	
	public Edge addEdge(Node from, Node to, int cost);
	public Node addNode(String label);
	public int computePath(Node from, Node to);
}

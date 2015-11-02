package graphProject;

import java.util.List;

public interface IGraph {
	
	public Edge addEdge(Node from, Node to, int cost);
	public Node addNode(String label);
	public int computePath(Node from, Node to);
	public List<Edge> getEdges();
	public Node getNode(String node);
	public List<Node> getAllNodes();
}

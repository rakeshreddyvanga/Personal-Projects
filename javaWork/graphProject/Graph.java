package graphProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph implements IGraph {

	private String graphName;
	private HashMap<String, Node> nodes;
	private List<Edge> edges;
	private int costInterval;
	
	public Graph(String graphName, int costInterval){
		this.graphName = graphName;
		this.nodes = new HashMap<>();
		this.edges = new ArrayList<>();
		this.costInterval = costInterval;
	}
	
	@Override
	public Edge addEdge(Node from, Node to, int cost) {
		Edge edge = null;
		if(cost <= costInterval) {
			edge = new Edge(from, to, cost);
			from.addOutEdge(edge);
			to.addInEdge(edge);
			edges.add(edge);
		}		
		return edge;
	}
	
	public Node addNode(String label){
		Node node = null;
		if(!nodes.containsKey(label)) {
			node = new Node(label);
			nodes.put(label, node);
		}
		else
			node = nodes.get(label);
		
		return node;
	}
	@Override
	public int computePath(Node from, Node to) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getGraphName() {
		return graphName;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public int getCostInterval() {
		return costInterval;
	}

}

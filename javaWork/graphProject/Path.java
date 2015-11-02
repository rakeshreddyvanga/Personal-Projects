package graphProject;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Path {

	private int cost;
	private Node source;
	private Node to;
	private List<Edge> edges;
	HashMap<Node,Edge> edgeTo;
	
	public Path(Node source, Node to){
		this.source = source;
		this.to = to;
		edgeTo = new HashMap<>();
		cost=0;
	}
	
	public void putEdge(Node node, Edge edge){
		edgeTo.put(node,edge);
	}

	public String toString(){
		Stack<Edge> revPath = getRoute();
		StringBuilder path = new StringBuilder();
		while(!revPath.isEmpty()){
			Edge edge = revPath.pop();
			path.append(edge.getFrom().getLabel());
		}
		path.append(to.getLabel());
		return path.toString();
	}
	
	public Stack<Edge> getRoute() {
		if(to.getMinCost() == Integer.MAX_VALUE)
			return null; // TODO: Throw exception
		Stack<Edge> revPath = new Stack<Edge>();
		for(Edge edge = edgeTo.get(to); edge != null;edge=edgeTo.get(edge.getFrom())){
			revPath.push(edge);
			cost+=edge.getCost();
		}
		return revPath;
	}
}

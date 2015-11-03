package graphProject;

import java.util.HashMap;
import java.util.Stack;

import graphProject.exceptions.ExceptionEnum;
import graphProject.exceptions.GraphException;

public class Path {

	private int cost;
	private Node source;
	private Node to;
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

	public String getPath() throws GraphException {
		Stack<Edge> revPath = getRoute();
		StringBuilder path = new StringBuilder();
		path.append("<path cost="+cost+">");
		while(!revPath.isEmpty()){
			Edge edge = revPath.pop();
			path.append(edge.toString());
		}
		path.append("</path>");
		return path.toString();
	}
	
	public Stack<Edge> getRoute() throws GraphException {
		if(to.getMinCost() == Integer.MAX_VALUE)
			throw new GraphException(ExceptionEnum.PathNotFoundException,source.getLabel()+" to "+ to.getLabel());
		Stack<Edge> revPath = new Stack<Edge>();
		for(Edge edge = edgeTo.get(to); edge != null;edge=edgeTo.get(edge.getFrom())){
			revPath.push(edge);
			cost+=edge.getCost();
		}
		return revPath;
	}
}

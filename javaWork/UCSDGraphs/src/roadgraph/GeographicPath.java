package roadgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import geography.GeographicPoint;

public class GeographicPath {
	private GeographicNode start;
	private GeographicNode goal;
	private HashMap<GeographicNode, GeographicEdge> edgeTo;
	
	public GeographicPath(GeographicNode start, GeographicNode goal){
		this.start = start;
		this.goal = goal;
		this.edgeTo = new HashMap<>();
	}
	
	/**
	 * Adds a vertex, edge combination to edgeTo map only if the vertex is not already present in the map.
	 * This is ensure that we only save the shortest path to a vertex. So, if we encounter a vertex again from
	 * different edge we don't save it.
	 * @param vertex - A GeographicPoint in the graph.
	 * @param edge - The edge to reach this point/vertex.
	 */
	public void addToPath(GeographicNode vertex, GeographicEdge edge){
		//if(!edgeTo.containsKey(vertex)) 
			edgeTo.put(vertex, edge);
	}
	
	/**
	 * This method returns points in the graph which are used to reach the goal
	 * @return - List of Geographic Points ordered from start to goal
	 */
	public List<GeographicPoint> getPath() {
		Stack<GeographicEdge> reversePath = getRoute();
		List<GeographicPoint> path = new ArrayList<>();
		path.add(start.getLocation()); //add start to path
		while(!reversePath.isEmpty()){
			GeographicEdge edge = reversePath.pop();
			path.add(edge.getTo().getLocation());
		}
		return path;
	}
	
	/**
	 * Helper method for constructing path from start to goal
	 * @return - Stack of edges in the path between start and goal in reverse order.
	 */
	private Stack<GeographicEdge> getRoute() {
		Stack<GeographicEdge> reversePath = new Stack<GeographicEdge>();
		for(GeographicEdge edge= edgeTo.get(goal); edge != null; edge = edgeTo.get(edge.getFrom())){
			reversePath.push(edge);
		}
		return reversePath;
		
	}
	
}

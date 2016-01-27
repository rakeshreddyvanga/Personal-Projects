/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 2
	private int numEdges;
	//adjacency list representation of graph, a point and list of out-going edges from that point
	private HashMap<GeographicPoint, GeographicNode> adjList;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 2
		numEdges = 0;
		adjList = new HashMap<>();
		
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 2
		return adjList.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 2
		return adjList.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 2
		return numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 2
		
		//The node was already in the graph, or the parameter is null.
		if(location == null || adjList.containsKey(location))
			return false;
		//add the new vertex to the graph with empty outgoing edges
		adjList.put(location, new GeographicNode(location));
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 2
		
		//validation checks
		if(from == null || to == null || length < 0L || !adjList.containsKey(from) || !adjList.containsKey(to)) 
			throw new IllegalArgumentException("Please check the input parameters.");
		
		numEdges++; //increment no of edges
		//Create GeographicEdge object and add it to from's node edges set
		GeographicEdge newEdge = new GeographicEdge(adjList.get(from),adjList.get(to),roadName,roadType,length);
		adjList.get(from).addEdge(newEdge);		
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param src The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 2
		if(start == null || goal == null || !adjList.containsKey(start) || !adjList.containsKey(goal))
			return null; // return null when start or goal is not defined or not present in the graph
		GeographicNode srcNode = adjList.get(start);
		GeographicNode destNode = adjList.get(goal);
		GeographicPath path = new GeographicPath(srcNode,destNode); //path object saves minimum path edge to reach a particular point
		HashSet<GeographicNode> visitedPoints = new HashSet<>();
		Queue<GeographicNode> nextPoints = new LinkedList<>(); 
		nextPoints.offer(srcNode); // add first point into queue
		visitedPoints.add(srcNode);
		while(!nextPoints.isEmpty()){
			GeographicNode next = nextPoints.poll();
			if(next.equals(destNode)) return path.getPath(); //return list of points built from path when goal is reached
			for (GeographicEdge edge : next.getEdges()) {
				GeographicNode to = edge.getTo();
				if (visitedPoints.add(to)) { //traverse only if the point is not visited and also add the new point to set
					nextPoints.offer(to); // add the next point to the queue
					path.addToPath(to, edge); // update the path to newly found node
					nodeSearched.accept(to.getLocation()); // For visual search
				}
			}
		}
		
		System.out.println("No path found.");
		return null; // this is encountered when no path exists
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		if(start == null || goal == null || !adjList.containsKey(start) || !adjList.containsKey(goal))
			return null; // return null when start or goal is not defined or not present in the graph
		
		int count=0; //for week 3 quiz
		//initialization
		initDijkstraNodes();
		
		//Get Nodes from points
		GeographicNode src = adjList.get(start);
		GeographicNode end = adjList.get(goal);
		
		//path object to track the list of nodes
		GeographicPath path = new GeographicPath(src,end);
		
		//create set to store visited nodes
		Set<GeographicNode> visitedNodes = new HashSet<>();
		
		//create priority queue and add start node
		PriorityQueue<GeographicNode> que = new PriorityQueue<>();
		src.setDistance(0); // set src distance to zero
		que.offer(src); // update queue
		
		while(!que.isEmpty()){
			GeographicNode node = que.poll();
			if (!visitedNodes.contains(node)) {
				count++;
				//System.out.println(node.toString());
				if(node.equals(end)) {
					System.out.println("For Dijkstra "+count);
					return path.getPath();
				}
				for (GeographicEdge edge : node.getEdges()) { //get all edges from the current node
					relaxEdges(edge, path, que);
					nodeSearched.accept(edge.getTo().getLocation()); //hook for visualization
				}
				visitedNodes.add(node); //once node is done processing all it's edge add it to visited set.
			}
		}
		
		System.out.println("Count for Dijkstra: "+count);
		return path.getPath();
	}

	/**
	 * Check if a minimum edge is found for edge.ToNode(), if so update the distance for that node
	 * along with adding it to priority queue and also update it's path.
	 * @param edge - to set minimum distance for edge.ToNode()
	 * @param path - if a minimum path edge is available to a node, then update the path
	 * @param que - if the minimum node is available, add that node to priority queue.
	 */
	private void relaxEdges(GeographicEdge edge, GeographicPath path, PriorityQueue<GeographicNode> que) {
		GeographicNode from = edge.getFrom();
		GeographicNode to = edge.getTo();
		if(to.getPredicatedDistance() > from.getPredicatedDistance() + edge.getLength()){
			to.setDistance(from.getDistance() + edge.getLength());
			que.offer(to);
			path.addToPath(to, edge);
		}
		
	}

	/**
	 * Initializes all node's distance to positive infinity in the graph.
	 * This method is called by dijkstra method and Astar method.
	 * So initializes predicted distance and distance to infinity.
	 */
	private void initDijkstraNodes() {
		for(GeographicNode node : adjList.values()){
			node.setDistance(Double.POSITIVE_INFINITY);
			node.setActualDistance(0);
		}
		
	}
	
	/**
	 * Initializes all node's distance to positive infinity in the graph.
	 * This method is called by dijkstra method and Astar method.
	 * So initializes predicted distance and distance to infinity.
	 */
	private void initAStarNodes(GeographicPoint goal) {
		for(GeographicNode node : adjList.values()){
			node.setDistance(Double.POSITIVE_INFINITY);
			node.setActualDistance(node.getLocation().distance(goal));
		}
		
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		if(start == null || goal == null || !adjList.containsKey(start) || !adjList.containsKey(goal))
			return null; // return null when start or goal is not defined or not present in the graph
		
		int count=0; //for week 3 quiz
		//initialization
		initAStarNodes(goal); 
		
		//Get Nodes from points
		GeographicNode src = adjList.get(start);
		GeographicNode end = adjList.get(goal);
		
		//path object to track the list of nodes
		GeographicPath path = new GeographicPath(src,end);
		
		//create set to store visited nodes
		Set<GeographicNode> visitedNodes = new HashSet<>();
		
		//create priority queue and add start node
		PriorityQueue<GeographicNode> que = new PriorityQueue<>();
		src.setDistance(0); // set src distance to zero
		src.setActualDistance(0);
		que.offer(src); // update queue
		
		while(!que.isEmpty()){
			GeographicNode node = que.poll();
			if (!visitedNodes.contains(node)) {
				count++;
				//System.out.println(node.toString());
				if(node.equals(end)) {
					System.out.println("For A-star Search: "+count);
					return path.getPath();
					}
				for (GeographicEdge edge : node.getEdges()) { //get all edges from the current node
					relaxEdges(edge, path, que); 
					nodeSearched.accept(edge.getTo().getLocation()); //hook for visualization
				}
				visitedNodes.add(node); //once this current node is done processing all it's edge add it to visited set.
			}
		}
		
		System.out.println("For A-star Search: "+count);
		return path.getPath();
	}

	
	
	public static void main(String[] args)
	{
		/*
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
		
		GeographicPoint start = new GeographicPoint(1, 1);
		GeographicPoint end = new GeographicPoint(8, -1);
		List<GeographicPoint> route = theMap.aStarSearch(start, end);
		for(GeographicPoint point: route)
			System.out.println(point.toString());
			
		
		List<GeographicPoint> route = theMap.bfs(start, end);
		for(GeographicPoint point: route){
			System.out.println(point.toString());
		}
		
			*/	
		// You can use this method for testing.  
		
		//Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		for(GeographicPoint point: route){
			System.out.println(point.toString());
		}
		System.out.println("A-Star");
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		for(GeographicPoint point: route2){
			System.out.println(point.toString());
		}
		
		
		
	}
	
}

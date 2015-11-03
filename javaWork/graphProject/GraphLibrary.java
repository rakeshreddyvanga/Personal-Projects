package graphProject;

import java.util.List;
import java.util.HashMap;

import graphProject.data_structures.MinHeap;
import graphProject.data_structures.exceptions.HeapException;
import graphProject.exceptions.ExceptionEnum;
import graphProject.exceptions.GraphException;

public class GraphLibrary implements IGraphLibrary {

	private HashMap<String, IGraph> graphDB;
	
	public GraphLibrary(){
		graphDB = new HashMap<>();
	}

	@Override
	public boolean createGraph(String graphName, int costInterval) {
		IGraph graph = null;
		if(graphDB.containsKey(graphName))
			graph = graphDB.get(graphName);
		else {
			graph = new Graph(graphName,costInterval);
			graphDB.put(graphName, graph);
		}
			
		return true;
	}

	@Override
	public boolean addEdge(String graphName, String from, String to, int cost) throws GraphException {
		if(graphName.trim().isEmpty() || !graphDB.containsKey(graphName))
			throw new GraphException(ExceptionEnum.GraphNotFoundException,graphName);
		IGraph graph = graphDB.get(graphName);
		graph.addEdge(graph.addNode(from), graph.addNode(to), cost);		
		return true;
	}

	@Override
	public String addGraph(String fromGraph, String toGraph) {
		System.out.println("Add graph called with "+fromGraph+" : "+ toGraph);
		return null;
	}

	@Override
	public String computePath(String graph, String from, String to) throws GraphException {
		if(graph.trim().isEmpty() || !graphDB.containsKey(graph))
			throw new GraphException(ExceptionEnum.GraphNotFoundException,graph);
		//get graph object and edges
		IGraph graphObj = graphDB.get(graph);
		List<Node> nodes = graphObj.getAllNodes();
		
		//get nodes
		Node fromNode = graphObj.getNode(from);
		Node toNode = graphObj.getNode(to);
		if(fromNode == null)
			throw new GraphException(ExceptionEnum.NodeNotFoundException,from);
		if(toNode == null)
			throw new GraphException(ExceptionEnum.NodeNotFoundException,to);
		Path path = new Path(fromNode, toNode);
		//catch heap exceptions
		try{
		MinHeap<Node> minQueue = new MinHeap<>(nodes.size());
		//initialize edges for Dijkstras algorithm
		initializeEdges(nodes);
		
		//Start Dijkstras
		fromNode.setMinCost(0);
		minQueue.offer(fromNode);
		while (!minQueue.isEmpty()) {
			Node node = minQueue.poll();
			for (Edge edge : node.getOutEdges()) {
				relaxEdges(edge,minQueue,path);
			}
		}
		}catch(HeapException heapException){
			throw new GraphException(ExceptionEnum.DataStructureException,heapException.getMessage());
		}
		//As the algorithm sets minimum cost for all the nodes, just return to nodes cost
		return path.getPath();
	}

	private void relaxEdges(Edge edge, MinHeap<Node> minQueue, Path path) throws HeapException {
		Node from = edge.getFrom();
		Node to = edge.getTo();
		if(to.getMinCost() > from.getMinCost()+edge.getCost()){
			to.setMinCost(from.getMinCost()+edge.getCost());
			path.putEdge(to, edge);// save node and edge that is used to get there
			if(minQueue.contains(to)) minQueue.buildHeap(); //TODO :Optimize to perform in O(logn) instead of O(n)
			else 
				minQueue.offer(to);
		}
		
	}

	/**
	 * Set each node's minCost value to Integer.MAX_VALUE;
	 * @param nodes
	 */
	private void initializeEdges(List<Node> nodes) {
		for (Node node : nodes) {
			node.setMinCost(Integer.MAX_VALUE);
		}
		
	}

}

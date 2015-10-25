package graphProject;

import java.util.HashMap;

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
	public boolean addEdge(String graphName, String from, String to, int cost) {
		if(graphName.trim().isEmpty() || !graphDB.containsKey(graphName))
			return false;
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
	public int computePath(String graph, String from, String to) {
		System.out.println("compute path called with "+from+" : "+ to+ " : "+graph);
		return 0;
	}

}

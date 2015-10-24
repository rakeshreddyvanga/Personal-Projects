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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String addGraph(String fromGraph, String toGraph) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int computePath(String graph, String from, String to) {
		// TODO Auto-generated method stub
		return 0;
	}

}

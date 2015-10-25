package graphProject;

import java.util.List;

public class Path {

	private int cost;
	private Node from;
	private Node to;
	private List<Edge> edges;
	
	public int getCost(){
		for(Edge edge: edges){
			cost += edge.getCost();
		}
		
		return cost;
	}
}

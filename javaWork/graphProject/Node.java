package graphProject;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node>{
 private String label;
 private List<Edge> outEdges;
 private List<Edge> inEdges;
 private int minCost;
 
 public Node(String name){
	 this.label = name;
	 setOutEdges(new ArrayList<Edge>());
	 setInEdges(new ArrayList<Edge>());
	 minCost = Integer.MAX_VALUE;
 }

public List<Edge> getOutEdges() {
	return outEdges;
}

public void setOutEdges(List<Edge> outEdges) {
	this.outEdges = outEdges;
}

public List<Edge> getInEdges() {
	return inEdges;
}

public void setInEdges(List<Edge> inEdges) {
	this.inEdges = inEdges;
}

public void addInEdge(Edge inEdge){
	inEdges.add(inEdge);
}

public void addOutEdge(Edge outEdge){
	outEdges.add(outEdge);
}

public int getMinCost() {
	return minCost;
}

public String getLabel() {
	return label;
}

@Override
public int compareTo(Node node) {
	return this.minCost - node.getMinCost();
}

public void setMinCost(int minCost) {
	this.minCost = minCost;	
}

 
 
 
}

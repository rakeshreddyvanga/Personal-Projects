package graphProject;

import java.util.ArrayList;
import java.util.List;

public class Node {
 private String label;
 private List<Edge> outEdges;
 private List<Edge> inEdges;
 
 public Node(String name){
	 this.label = name;
	 setOutEdges(new ArrayList<Edge>());
	 setInEdges(new ArrayList<Edge>());
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

public String getLabel() {
	return label;
}

 
 
 
}

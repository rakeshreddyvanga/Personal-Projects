package roadgraph;

import java.util.HashSet;
import java.util.Set;

import geography.GeographicPoint;

public class GeographicNode implements Comparable<GeographicNode> {
	
	private GeographicPoint location;
	// the predicted distance of this node from start
	private double distance;
	// the actual distance of this node from start
	private double actualDistance;
	private Set<GeographicEdge> edges;

	public GeographicNode(GeographicPoint location) {
		this.location = location;
		edges = new HashSet<>();
		distance = Double.POSITIVE_INFINITY;
		actualDistance = Double.POSITIVE_INFINITY;
	}

	public GeographicPoint getLocation() {
		return location;
	}

	public double getDistance() {
		return distance;
	}

	public double getPredicatedDistance() {
		if(distance == Double.POSITIVE_INFINITY)
			return distance;
		
		return actualDistance + distance;
	}

	//adds edges to this nodes out going edges list
	public void addEdge(GeographicEdge newEdge) {
		edges.add(newEdge);
		
	}
	/**
	 * Resets actual distance and predicted distance to
	 * positive infinity
	 */
	public void resetDistances(){
		this.distance = Double.POSITIVE_INFINITY;
		this.actualDistance = Double.POSITIVE_INFINITY;
	}
	
	public void setDistance(double distance){
		this.distance = distance;
	}
	
	public void setActualDistance(double actualDistance){
		this.actualDistance = actualDistance;
	}
	
	public Set<GeographicEdge> getEdges(){
		return edges;
	}
	
	 public String toString()
	    {
	    	return "Lat: " + getLocation().getX() + ", Lon: " + getLocation().getY();
	    }
	 
	 @Override
	 public boolean equals(Object o) {
		 if(!(o instanceof GeographicNode))
			 return false;
		 GeographicNode other = (GeographicNode)o;
		 return other.getLocation().equals(this.getLocation());
	 }
	 
	 @Override
	 public int hashCode(){
		 return this.getLocation().hashCode();
	 }

	@Override
	public int compareTo(GeographicNode other) {
		if(other.getPredicatedDistance() == this.getPredicatedDistance())
			return 0;
		else if(other.getPredicatedDistance() < this.getPredicatedDistance())
			return 1;
		else
			return -1;
	}

}

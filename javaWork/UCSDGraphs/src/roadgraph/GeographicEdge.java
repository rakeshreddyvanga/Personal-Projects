package roadgraph;


public class GeographicEdge {
	private GeographicNode from;
	private GeographicNode to;
	private String roadName;
	private String roadType;
	private double length;
	
	
	public GeographicEdge(GeographicNode from, GeographicNode to, String roadName,
			String roadType, double length){
		this.from = from;
		this.to = to;
		this.roadName = roadName;
		this.roadType = roadType;
		this.length = length;
	}


	public GeographicNode getFrom() {
		return from;
	}


	public GeographicNode getTo() {
		return to;
	}


	public String getRoadName() {
		return roadName;
	}


	public String getRoadType() {
		return roadType;
	}


	public double getLength() {
		return length;
	}
	
	
	/** Two road segments are equal if they have the same start and end points
	 *  and they have the same road name.
	 */
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof GeographicEdge))
			return false;
		GeographicEdge other = (GeographicEdge)o;
		boolean ptsEqual = false;
		if(this.to.equals(other.getTo()) && this.from.equals(other.getFrom()))
			ptsEqual = true;
		
		if(this.to.equals(other.getFrom()) && this.from.equals(other.getTo()))
			ptsEqual = true;
		
		return this.roadName == other.getRoadName() && ptsEqual && this.length == other.getLength();
	}
	
	@Override
	public int hashCode(){
		return to.hashCode() + from.hashCode();
	}
	
	public GeographicNode otherVertex(GeographicNode point){
		if(point.equals(to))
			return from;
		else if(point.equals(from))
			return to;
		else // not valid point so return null
			return null;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("From: ");sb.append(from.toString());
		sb.append("\n\t Road Name: ");sb.append(getRoadName());
		sb.append("\n\t Road Type: ");sb.append(getRoadType());
		sb.append("\n\t Length: ");sb.append(getLength());
		sb.append("\nTo: ");sb.append(to.toString());
		return sb.toString();
	}
	
}

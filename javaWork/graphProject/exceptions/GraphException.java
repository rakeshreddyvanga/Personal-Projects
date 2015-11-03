package graphProject.exceptions;

public class GraphException extends Exception {

	private ExceptionEnum type;
	private String value;
	/**
	 * serial UID
	 */
	private static final long serialVersionUID = 3237019244361513113L;
	
	public GraphException() { super(); }

	public GraphException(ExceptionEnum type, String value) {  
		super(exceptionMsg(type,value));
		this.type = type;
		this.value = value;
		}

	public GraphException(ExceptionEnum type, String value, Throwable cause) { 
		super(exceptionMsg(type,value), cause); 
		this.type = type;
		this.value = value;
		}

	@Override
	public String toString() {
		return super.toString();
	}

	private static String exceptionMsg(ExceptionEnum exceptionType, String value){
		switch(exceptionType) {
		case NodeNotFoundException:
			return "Node "+value+" is not present in the graph.";
		case GraphNotFoundException:
			return "Graph '"+value+"' is invalid.";
		case InvalidXMLException:
			return "Please check the following error with XML: "+value;
		case PathNotFoundException:
			return "There is no path from: "+value;
		case DataStructureException:
			return value;			
		default:
			return value;
		}
	}
	
	@Override
	public String getMessage() {
		return String.format("<error msg=%s />", super.getMessage());
	}

	

}

package graphProject.exceptions;

public class GraphException extends Exception {

	private String type;
	private String value;
	/**
	 * serial UID
	 */
	private static final long serialVersionUID = 3237019244361513113L;
	
	public GraphException() { super(); }

	public GraphException(String type, String value) { 
		super(); 
		this.type = type;
		this.value = value;
		}

	public GraphException(String message, int productId, Throwable cause) { super(message, cause); this.productId = productId; }

	@Override
	public String toString() {
		return super.toString();
	}

	private String exceptionMsg(){
		
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + " for productId :" + productId;
	}

	public int getProductId() {
		return productId;
	}

	

}

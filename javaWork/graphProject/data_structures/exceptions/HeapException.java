package graphProject.data_structures.exceptions;

public class HeapException extends Exception {

	/**
	 * default generated serial UID
	 */
	private static final long serialVersionUID = -9197539367687062137L;
	
	public HeapException() { super(); }

	public HeapException(String message) {  
		super(message);
		}

	public HeapException(String message, Throwable cause) { 
		super(message, cause); 
		}

	@Override
	public String toString() {
		return super.toString();
	}

	
	@Override
	public String getMessage() {
		return super.getMessage();
	}


}

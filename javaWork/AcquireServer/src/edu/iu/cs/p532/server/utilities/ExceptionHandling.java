package edu.iu.cs.p532.server.utilities;


public class ExceptionHandling extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ExceptionHandling(String exceptionMessage) {
		super(exceptionMessage);
		this.message = exceptionMessage;
		
	}
}

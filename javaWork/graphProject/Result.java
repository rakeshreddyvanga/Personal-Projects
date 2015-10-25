package graphProject;

public class Result {
	private boolean errorOccured;
	private StringBuilder errorMessage;
	private Path path;
	
	public Result(){
		errorOccured= false;
		errorMessage = new StringBuilder();
	}
	public void setErrorFlag(boolean errorOccured) {
		this.errorOccured = errorOccured;
		
	}
	
	public boolean errorOccured(){
		return errorOccured;
	}
	
	public void setMessage(String errorMessage){
		this.errorMessage.append(errorMessage);
	}
	
	public String getErrorMessage(){
		return errorMessage.toString();
	}
}

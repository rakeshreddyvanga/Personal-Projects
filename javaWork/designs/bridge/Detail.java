package designs.patterns.bridge;

public class Detail {
	
	String key;
	String value;
	
	public Detail(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	public String toString(){
		return String.format("%s: %s", key,value);
	}

}

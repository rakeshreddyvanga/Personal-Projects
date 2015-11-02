package designs.patterns.singleton;

public class Singleton {
	
	private static Singleton instance;
	private static int count = 0;
	
	private Singleton(){
		
	}
	
	public Singleton getInstance(){
		if(instance == null){
			synchronized(this.getClass()){
				if(instance == null){
					instance = new Singleton();
				}				
			}
		}
		
		return instance;
	}
	
	public Singleton getFiveInstances() {
		if (instance == null || count < 5) {
			synchronized (this.getClass()) {
				if (instance == null || count < 5) {
					instance = new Singleton();
					count++;
				}
			}
		}
		return instance;
	}

}

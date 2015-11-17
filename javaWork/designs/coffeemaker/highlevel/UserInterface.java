package designs.coffeemaker.highlevel;

import designs.coffeemaker.M4CoffeeMaker.M4ContainerVessel;
import designs.coffeemaker.M4CoffeeMaker.M4HotWaterSource;

public abstract class UserInterface {
	private M4HotWaterSource hws;
	private M4ContainerVessel cv;
	protected boolean isComplete;
	
	public UserInterface(){
		isComplete = true;
	}
	
	public void init(M4HotWaterSource hws, M4ContainerVessel cv) {
		this.hws = hws;
		this.cv = cv;
	}
	
	public void complete(){
		isComplete = true;
		completeCycle();
	}
	
	public abstract void completeCycle();
	public abstract void done();

	protected void startBrewing(){
		if(hws.isReady() && cv.isReady()){
			hws.start();
			cv.start();
		}
	}
}

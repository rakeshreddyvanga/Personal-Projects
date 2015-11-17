 package designs.coffeemaker.highlevel;

import designs.coffeemaker.M4CoffeeMaker.M4HotWaterSource;
import designs.coffeemaker.M4CoffeeMaker.M4UserInterface;

public abstract class ContainerVessel {
	private M4UserInterface ui;
	private M4HotWaterSource hws;
	protected boolean isComplete;
	protected boolean isBrewing;
	
	public ContainerVessel(){
		isComplete=false;
		isBrewing=false;
	}
	
	public void init(M4UserInterface ui, M4HotWaterSource hws) {
		this.ui = ui;
		this.hws = hws;
	}
	
	public void start() {
		isBrewing=true;
		isComplete=false;
	}
	
	public void done() {
		isBrewing = false;
	}
	
	protected void containerAvailable(){
		hws.Resume();
	}
	
	protected void containerUnAvailable(){
		hws.Pause();
	}
	
	protected void declareComplete(){
		ui.complete();
		isComplete = true;
	}
	public abstract boolean isReady();
}

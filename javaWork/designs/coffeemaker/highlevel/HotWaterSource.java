package designs.coffeemaker.highlevel;

import designs.coffeemaker.M4CoffeeMaker.M4ContainerVessel;
import designs.coffeemaker.M4CoffeeMaker.M4UserInterface;

public abstract class HotWaterSource {
	private M4UserInterface ui;
	private M4ContainerVessel cv;
	protected boolean isBrewing;
	
	public void init(M4UserInterface ui, M4ContainerVessel cv) {
		this.ui = ui;
		this.cv = cv;
	}
	
	public HotWaterSource() {
		isBrewing = false;
	}
	
	public void start(){
		isBrewing = true;
		startBrewing();
	}
	
	public void done(){
		isBrewing = false;
	}
	
	public void declareDone(){
		ui.done();
		cv.done();
		isBrewing = false;
	}
	
	
	public abstract boolean isReady();
	public abstract void startBrewing();
	public abstract void Resume();
	public abstract void Pause();
}

package designs.coffeemaker.tests;

import designs.coffeemaker.M4CoffeeMaker.M4ContainerVessel;
import designs.coffeemaker.M4CoffeeMaker.M4HotWaterSource;
import designs.coffeemaker.M4CoffeeMaker.M4UserInterface;
import designs.coffeemaker.M4CoffeeMakerAPI.CoffeeMakerImpl;
import designs.coffeemaker.M4CoffeeMakerAPI.ICoffeeMakerAPI;
import junit.framework.TestCase;
import junit.textui.TestRunner;

public class TestCoffeeMaker extends TestCase {

	public static void main(String[] args) {
		TestRunner.main(new String[] {"TestCoffeeMaker"});
	}
	
	public TestCoffeeMaker (String name) {
		super(name);
	}
	
	private M4UserInterface ui;
	private M4HotWaterSource hws;
	private M4ContainerVessel cv;
	private CoffeeMakerImpl api;
	
	public void setup(){
		api = new CoffeeMakerImpl();
		ui = new M4UserInterface(api);
		hws = new M4HotWaterSource(api);
		cv = new M4ContainerVessel(api);
		ui.init(hws, cv);
		hws.init(ui, cv);
		cv.init(ui, hws);
	}
	
	/**
	 * Used to run one set of coffee making actions
	 */
	private void poll(){
		ui.poll();
		hws.poll();
		cv.poll();
	}
	
	/**
	 * Proper start of the coffee maker.
	 */
	private void normalUse(){
		poll();
		api.setBoilerEmpty(false);
		api.setButtonPressed(true);
		poll();
	}
	
	/**
	 * When part of the pot is filled
	 */
	private void normalFill(){
		normalUse();
		api.setPotNotEmpty(true);
		poll();
	}
	
	/**
	 * After a while the water is completed in the boiler
	 * which means one round of coffee is done.
	 */
	private void normalBrew(){
		normalFill();
		api.setBoilerEmpty(true);
		poll();
	}
	
	/*****Test Cases *************/
	
	//below three cases: when button is pressed without water in boiler and pot not on the plate
	public void initialCondition() {
		poll();
		assert(api.isBoilerOn() == false);
		assert(api.isLightOn() == false);
		assert(api.isPlateOn() == false);
		assert(api.isValveClosed() == true);
	}
	
	public void startNoPot(){
		poll();
		api.setButtonPressed(true);
		api.setPotPresent(false);
		poll();
		assert(api.isBoilerOn() == false);
		assert(api.isLightOn() == false);
		assert(api.isPlateOn() == false);
		assert(api.isValveClosed() == true);
	}
	
	public void startNoWater(){
		poll();
		api.setButtonPressed(true);
		api.setBoilerEmpty(true);
		poll();
		assert(api.isBoilerOn() == false);
		assert(api.isLightOn() == false);
		assert(api.isPlateOn() == false);
		assert(api.isValveClosed() == true);
	}
	
	//initial good start: all will remain same except that boiler will be on
	// warmer plate will be turned on only when it senses the pot is not empty.
	public void goodStart() {
		normalUse();
		assert(api.isBoilerOn() == true);
		assert(api.isLightOn() == false);
		assert(api.isPlateOn() == false);
		assert(api.isValveClosed() == true);
	}
	
	//test started and pot is not empty to test whether the plate is on or not
	public void startPotNotEmpty(){
		normalUse();
		api.setPotNotEmpty(true);
		poll();
		assert(api.isBoilerOn() == true);
		assert(api.isLightOn() == false);
		assert(api.isPlateOn() == true);
		assert(api.isValveClosed() == true);
	}
	
	public void potRemovedAndReplacedWhileEmpty(){
		normalUse();
		api.setPotPresent(false);
		poll();
		assert(api.isBoilerOn() == false);
		assert(api.isLightOn() == false);
		assert(api.isPlateOn() == false);
		assert(api.isValveClosed() == false);
		api.setPotPresent(true);
		poll();
		assert(api.isBoilerOn() == true);
		assert(api.isLightOn() == false);
		assert(api.isPlateOn() == false);
		assert(api.isValveClosed() == true);

	}
	
	public void potRemovedWhileNotEmptyAndReplacedEmpty(){
		normalFill();
		api.setPotPresent(false);
		poll();
		assert(api.isBoilerOn() == false);
		assert(api.isLightOn() == false);
		assert(api.isPlateOn() == false);
		assert(api.isValveClosed() == false);
		api.setPotPresent(true);
		api.setPotNotEmpty(false);
		poll();
		assert(api.isBoilerOn() == true);
		assert(api.isLightOn() == false);
		assert(api.isPlateOn() == false);
		assert(api.isValveClosed() == true);
	}
}

package designs.coffeemaker.M4CoffeeMaker;

import designs.coffeemaker.M4CoffeeMakerAPI.*;
import designs.coffeemaker.highlevel.IPolling;
import designs.coffeemaker.highlevel.UserInterface;

public class M4UserInterface extends UserInterface implements IPolling {
	private ICoffeeMakerAPI api;
	
	public M4UserInterface(ICoffeeMakerAPI api) {
		this.api = api;
	}

	/**
	 *  
	 */
	@Override
	public void poll() {
		int buttonStatus = api.getBrewButtonStatus();
		if(buttonStatus == ICoffeeMakerAPI.BREW_BUTTON_PUSHED){
			startBrewing();
		}
	}

	/**
	 * Is used to turn off the light, to indicate the coffee is
	 * completed even from pot and time to start new coffee.
	 */
	@Override
	public void completeCycle() {
		api.setIndicatorState(ICoffeeMakerAPI.INDICATOR_OFF);
		
	}
	/**
	 * Indicates that the brewing is completed by turning on the light on
	 * coffee maker.
	 */
	@Override
	public void done() {
		api.setIndicatorState(ICoffeeMakerAPI.INDICATOR_ON);		
	}

}

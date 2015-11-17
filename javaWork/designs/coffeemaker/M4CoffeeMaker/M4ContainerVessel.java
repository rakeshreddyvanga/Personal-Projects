package designs.coffeemaker.M4CoffeeMaker;

import designs.coffeemaker.M4CoffeeMakerAPI.ICoffeeMakerAPI;
import designs.coffeemaker.highlevel.*;

public class M4ContainerVessel extends ContainerVessel implements IPolling {

	private ICoffeeMakerAPI api;
	private int lastPotStatus;

	public M4ContainerVessel(ICoffeeMakerAPI api) {
		this.api = api;
		lastPotStatus = ICoffeeMakerAPI.POT_EMPTY;
	}

	@Override
	public void poll() {
		int potStatus = api.getBoilerStatus();
		if(potStatus != lastPotStatus) {
			if(isBrewing) { // currently brewing
				handleBrewingEvent(potStatus);
			}
			else if(!isComplete) { //Not brewing but coffee is present in the pot
				handleInCompleteEvent(potStatus);
			}
			lastPotStatus = potStatus;
		}
	}

	private void handleInCompleteEvent(int potStatus) {
		switch(potStatus) {
		case ICoffeeMakerAPI.POT_NOT_EMPTY:
			api.setWarmerState(ICoffeeMakerAPI.WARMER_ON);
			break;
		case ICoffeeMakerAPI.WARMER_EMPTY:
			api.setWarmerState(ICoffeeMakerAPI.WARMER_OFF);
			break;
		case ICoffeeMakerAPI.POT_EMPTY:
			api.setWarmerState(ICoffeeMakerAPI.WARMER_OFF);
			declareComplete();
			break;		
		}
		
	}

	private void handleBrewingEvent(int potStatus) {
		switch(potStatus) {
		case ICoffeeMakerAPI.POT_NOT_EMPTY:
			containerAvailable();
			api.setWarmerState(ICoffeeMakerAPI.WARMER_ON);
			break;
		case ICoffeeMakerAPI.WARMER_EMPTY:
			containerUnAvailable();
			api.setWarmerState(ICoffeeMakerAPI.WARMER_OFF);
			break;
		case ICoffeeMakerAPI.POT_EMPTY:
			containerAvailable();
			api.setWarmerState(ICoffeeMakerAPI.WARMER_OFF);
			break;
		}		
	}

	@Override
	public boolean isReady() {
		int plateStatus = api.getWarmerPlateStatus();
		return plateStatus == ICoffeeMakerAPI.POT_EMPTY;
	}




}

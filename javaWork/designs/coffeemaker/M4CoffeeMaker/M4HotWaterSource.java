package designs.coffeemaker.M4CoffeeMaker;

import designs.coffeemaker.M4CoffeeMakerAPI.ICoffeeMakerAPI;
import designs.coffeemaker.highlevel.*;

public class M4HotWaterSource extends HotWaterSource implements IPolling {
	ICoffeeMakerAPI api;
	
	public M4HotWaterSource(ICoffeeMakerAPI api) {
		this.api = api;
	}


	@Override
	public void poll() {
		int boilerStatus = api.getBoilerStatus();
		if(boilerStatus == ICoffeeMakerAPI.BOILER_EMPTY){
			if(isBrewing) {
				api.setBoilerState(ICoffeeMakerAPI.BOILER_OFF);
				api.setReliefValveState(ICoffeeMakerAPI.VALVE_OPEN);
				declareDone();
			}
		}
	}


	@Override
	public boolean isReady() {
		int boilerStatus = api.getBoilerStatus();
		return boilerStatus == ICoffeeMakerAPI.BOILER_NOT_EMPTY;
	}


	@Override
	public void startBrewing() {
		api.setBoilerState(ICoffeeMakerAPI.BOILER_ON);
		api.setReliefValveState(ICoffeeMakerAPI.VALVE_CLOSED);		
	}


	@Override
	public void Resume() {
		api.setReliefValveState(ICoffeeMakerAPI.VALVE_CLOSED);
		api.setBoilerState(ICoffeeMakerAPI.BOILER_ON);
	}


	@Override
	public void Pause() {
		api.setReliefValveState(ICoffeeMakerAPI.VALVE_OPEN);
		api.setBoilerState(ICoffeeMakerAPI.BOILER_OFF);
		
	}


}

package designs.coffeemaker.M4CoffeeMakerAPI;

/**
 * 
 * We will have a simulation implementation of coffee maker API
 * So, this implementation will always have initial state and
 * is useful for testing purposes. This will allow you to update state of the maker
 * save the state of the maker as you are testing with it.
 */
public class CoffeeMakerImpl implements ICoffeeMakerAPI {
	
	private boolean buttonPressed;
	private boolean lightOn;
	private boolean boilerOn;
	private boolean valveClosed;
	private boolean plateOn;
	private boolean boilerEmpty;
	private boolean potNotEmpty;
	private boolean potPresent;
	
	//Initializes coffee maker to the start state 
	public CoffeeMakerImpl() {
		buttonPressed = false;
		lightOn = false;
		boilerOn = false;
		valveClosed = true;
		plateOn = false;
		boilerEmpty = true;
		potPresent = true;
		potNotEmpty = false;
	}

	@Override
	public int getWarmerPlateStatus() {
		if(!potPresent) return WARMER_EMPTY;
		else if (potNotEmpty) return POT_NOT_EMPTY;
		else return POT_EMPTY;
	}

	@Override
	public int getBoilerStatus() {
		return boilerEmpty ? BOILER_EMPTY : BOILER_NOT_EMPTY;
	}

	@Override
	public int getBrewButtonStatus() {
		if(buttonPressed){
			buttonPressed = false;
			return BREW_BUTTON_PUSHED;
		}
		else
			return BREW_BUTTON_NOT_PUSHED;
	}

	@Override
	public void setBoilerState(int boilerStatus) {
		boilerOn = boilerStatus == BOILER_ON;
	}

	@Override
	public void setWarmerState(int warmerState) {
		plateOn = warmerState == WARMER_ON;
	}

	@Override
	public void setIndicatorState(int indicatorState) {
		lightOn = indicatorState == INDICATOR_ON;
	}

	@Override
	public void setReliefValveState(int reliefValveState) {
		valveClosed = reliefValveState == VALVE_CLOSED;
	}

	public boolean isButtonPressed() {
		return buttonPressed;
	}

	public void setButtonPressed(boolean buttonPressed) {
		this.buttonPressed = buttonPressed;
	}

	public boolean isLightOn() {
		return lightOn;
	}

	public void setLightOn(boolean lightOn) {
		this.lightOn = lightOn;
	}

	public boolean isBoilerOn() {
		return boilerOn;
	}

	public void setBoilerOn(boolean boilerOn) {
		this.boilerOn = boilerOn;
	}

	public boolean isValveClosed() {
		return valveClosed;
	}

	public void setValveClosed(boolean valveClosed) {
		this.valveClosed = valveClosed;
	}

	public boolean isPlateOn() {
		return plateOn;
	}

	public void setPlateOn(boolean plateOn) {
		this.plateOn = plateOn;
	}

	public boolean isBoilerEmpty() {
		return boilerEmpty;
	}

	public void setBoilerEmpty(boolean boilerEmpty) {
		this.boilerEmpty = boilerEmpty;
	}

	public boolean isPotNotEmpty() {
		return potNotEmpty;
	}

	public void setPotNotEmpty(boolean potNotEmpty) {
		this.potNotEmpty = potNotEmpty;
	}

	public boolean isPotPresent() {
		return potPresent;
	}

	public void setPotPresent(boolean potPresent) {
		this.potPresent = potPresent;
	}

}

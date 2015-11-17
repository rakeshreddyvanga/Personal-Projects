package designs.coffeemaker.M4CoffeeMakerAPI;

public interface ICoffeeMakerAPI {
		/**
		* This function returns the status of the warmer-plate
		* sensor. This sensor detects the presence of the pot
		* and whether it has coffee in it.
		*/
		public int getWarmerPlateStatus();
		public static final int WARMER_EMPTY = 0;
		public static final int POT_EMPTY = 1;
		public static final int POT_NOT_EMPTY = 2;
		/**
		* This function returns the status of the boiler switch.
		* The boiler switch is a float switch that detects if
		* there is more than 1/2 cup of water in the boiler.
		*/
		public int getBoilerStatus();
		public static final int BOILER_EMPTY = 0;
		public static final int BOILER_NOT_EMPTY = 1;
		/**
		* This function returns the status of the brew button.
		* The brew button is a momentary switch that remembers
		* its state. Each call to this function returns the
		* remembered state and then resets that state to
		* BREW_BUTTON_NOT_PUSHED.
		*
		* Thus, even if this function is polled at a very slow
		* rate, it will still detect when the brew button is
		* pushed.
		*/
		public int getBrewButtonStatus();
		public static final int BREW_BUTTON_PUSHED = 0;
		public static final int BREW_BUTTON_NOT_PUSHED = 1;
		
		/**
		* This function turns the heating element in the boiler
		* on or off.
		*/
		public void setBoilerState(int boilerStatus);
		public static final int BOILER_ON = 0;
		public static final int BOILER_OFF = 1;
		
		
		/**
		* This function turns the heating element in the warmer
		* plate on or off.
		*/
		public void setWarmerState(int warmerState);
		public static final int WARMER_ON = 0;
		public static final int WARMER_OFF = 1;
		
		/**
		* This function turns the indicator light on or off.
		* The indicator light should be turned on at the end
		* of the brewing cycle. It should be turned off when
		* the user presses the brew button.
		*/
		public void setIndicatorState(int indicatorState);
		public static final int INDICATOR_ON = 0;
		public static final int INDICATOR_OFF = 1;
		/**
		* This function opens and closes the pressure-relief
		* valve. When this valve is closed, steam pressure in
		* the boiler will force hot water to spray out over
		* the coffee filter. When the valve is open, the steam
		* in the boiler escapes into the environment, and the
		* water in the boiler will not spray out over the filter.
		*/
		public void setReliefValveState(int reliefValveState);
		public static final int VALVE_OPEN = 0;
		public static final int VALVE_CLOSED = 1;
}

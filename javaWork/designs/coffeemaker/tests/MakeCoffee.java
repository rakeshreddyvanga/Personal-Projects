package designs.coffeemaker.tests;

import designs.coffeemaker.M4CoffeeMaker.M4ContainerVessel;
import designs.coffeemaker.M4CoffeeMaker.M4HotWaterSource;
import designs.coffeemaker.M4CoffeeMaker.M4UserInterface;
import designs.coffeemaker.M4CoffeeMakerAPI.*;

public class MakeCoffee {

	public static void main(String[] args) {
		//Instantiate API 
		ICoffeeMakerAPI api = new CoffeeMakerImpl();
		
		//Instantiate the components of M4 CoffeeMaker
		M4UserInterface ui = new M4UserInterface(api);
		M4HotWaterSource hws = new M4HotWaterSource(api);
		M4ContainerVessel cv = new M4ContainerVessel(api);
		
		//Send the reference of these components to each other
		ui.init(hws, cv);
		hws.init(ui, cv);
		cv.init(ui, hws);
		
		//Continuously poll these components, assuming we are having constant electricity
		while(true) { // condition of while loop can be changed to other factors, like availability of electricity.
			ui.poll();
			hws.poll();
			cv.poll();
		}

	}

}

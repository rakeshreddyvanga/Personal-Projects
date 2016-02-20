package designs.elevator.tests;

import java.util.ArrayList;
import java.util.List;

import designs.elevator.logic.Elevator;
import designs.elevator.logic.ElevatorControlSystem;

public class ElevatorControlSystemTest {
	  public static final int ELEVATOR_ID_ZERO = 0;
	  public static final int ELEVATOR_ID_ONE = 1;
	  public static final int TENTH_FLOOR = 10;
	  public static final int FIRST_FLOOR = 1;
	  public static final int SEVENTH_FLOOR = 8;
	  private ElevatorControlSystem elevatorControlSystem;
	  private List<Elevator> elevators;
	  
	  public void initialize(){
	    try {
	      elevatorControlSystem = new ElevatorControlSystem(2, 20);
	    } catch (InvalidNumber invalidNumber) {
	      invalidNumber.printStackTrace();
	    }
	  }

	  public void testRequestingOneElevator(){
	    elevatorControlSystem.pickUp(TENTH_FLOOR);
	    for(int idx=0;idx<TENTH_FLOOR;idx++){
	      elevatorControlSystem.step();
	    }
	    elevators = elevatorControlSystem.getElevators();
	    assertEquals(TENTH_FLOOR, elevators.get(ELEVATOR_ID_ZERO).currentFloor());
	  }
	  
	  public void testElevatorTwoNotMoving(){
	    elevatorControlSystem.pickUp(TENTH_FLOOR);
	    for(int idx=0;idx<TENTH_FLOOR;idx++){
	      elevatorControlSystem.step();
	    }
	    elevators = elevatorControlSystem.getElevators();
	    assertEquals(FIRST_FLOOR, elevators.get(ELEVATOR_ID_ONE).currentFloor());
	  }

	  @Test
	  public void testCallingTwoElevators(){
	    elevatorControlSystem.pickUp(TENTH_FLOOR);
	    elevatorControlSystem.pickUp(SEVENTH_FLOOR);
	    for(int idx=0;idx<TENTH_FLOOR;idx++){
	      elevatorControlSystem.step();
	    }
	    elevators = elevatorControlSystem.getElevators();
	    assertEquals(TENTH_FLOOR, elevators.get(ELEVATOR_ID_ZERO).currentFloor());
	    assertEquals(SEVENTH_FLOOR, elevators.get(ELEVATOR_ID_ONE).currentFloor());
	  }

	  @Test
	  public void testSendingElevatorToDestination(){
	    elevatorControlSystem.destination(ELEVATOR_ID_ZERO, TENTH_FLOOR);
	    for(int idx=0;idx<TENTH_FLOOR;idx++){
	      elevatorControlSystem.step();
	    }
	    elevators = elevatorControlSystem.getElevators();
	    assertEquals(TENTH_FLOOR, elevators.get(ELEVATOR_ID_ZERO).currentFloor());
	  }

	  @Test
	  public void testSendingElevatorToMultipleDestinations(){
	    elevatorControlSystem.destination(ELEVATOR_ID_ZERO, TENTH_FLOOR);
	    elevatorControlSystem.destination(ELEVATOR_ID_ZERO, SEVENTH_FLOOR);
	    for(int idx=0;idx<TENTH_FLOOR;idx++){
	      elevatorControlSystem.step();
	    }
	    elevators = elevatorControlSystem.getElevators();
	    assertEquals(TENTH_FLOOR, elevators.get(ELEVATOR_ID_ZERO).currentFloor());
	    for(int idx=0;idx<TENTH_FLOOR-SEVENTH_FLOOR;idx++){
	      elevatorControlSystem.step();
	    }
	    elevators = elevatorControlSystem.getElevators();
	    assertEquals(SEVENTH_FLOOR, elevators.get(ELEVATOR_ID_ZERO).currentFloor());
	  }

	}

package designs.elevator.tests;

import designs.elevator.logic.Elevator;

public class ElevatorTest {
	  public static final int TENTH_FLOOR = 10;
	  public static final int SECOND_FLOOR = 2;
	  public static final int BASEMENT_TWO = -2;

	  private Elevator elevator;

	  
	  public void initializeElevator(){
	    elevator = new Elevator(0);
	  }

	  
	  public void testAddingDestination(){
	    elevator.addDestination(TENTH_FLOOR);
	    assertEquals(TENTH_FLOOR, elevator.getNextDestination());
	  }

	  
	  public void checkCurrentFloor(){
	    // Move to floor 2
	    elevator.moveUp();
	    elevator.moveUp();
	    assertEquals(SECOND_FLOOR, elevator.currentFloor());
	  }

	  
	  public void checkMoveDown(){
	    elevator.moveDown();
	    elevator.moveDown();
	    assertEquals(BASEMENT_TWO, elevator.currentFloor());
	  }

	  
	  public void checkDirectionUp(){
	    elevator.addNewDestinatoin(SECOND_FLOOR);
	    assertEquals(ElevatorDirection.ELEVATOR_UP, elevator.direction());
	  }

	  
	  public void checkDirectionDown(){
	    elevator.addNewDestinatoin(BASEMENT_TWO);
	    assertEquals(ElevatorDirection.ELEVATOR_DOWN, elevator.direction());
	  }

	  
	  public void checkDirectionHold(){
	    assertEquals(ElevatorDirection.ELEVATOR_HOLD, elevator.direction());
	  }

	  
	  public void checkStatusEmpty(){
	    assertEquals(ElevatorStatus.ELEVATOR_EMPTY, elevator.status());
	  }

	  
	  public void checkStatusOccupied(){
	    elevator.addNewDestinatoin(TENTH_FLOOR);
	    assertEquals(ElevatorStatus.ELEVATOR_OCCUPIED, elevator.status());
	  }
	}
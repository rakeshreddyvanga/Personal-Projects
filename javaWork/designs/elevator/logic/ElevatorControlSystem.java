package designs.elevator.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import designs.elevator.interfaces.IElevatorControlSystem;

public class ElevatorControlSystem implements IElevatorControlSystem {
	private int totalFloors;
	private Queue<Integer> pickUps;
	private List<Elevator> elevators;
	
	public ElevatorControlSystem(int noOfElevators, int totalFloors) {
		pickUps = new LinkedList<>();
		this.totalFloors = totalFloors;
		initElevators(noOfElevators);
	}

	public int getTotalFloors() {
		return totalFloors;
	}

	public Queue<Integer> getPickUps() {
		return pickUps;
	}

	public List<Elevator> getElevators() {
		return elevators;
	}

	private void initElevators(int noOfElevators) {
		elevators = new ArrayList<>();
		for (int i = 0; i < noOfElevators; i++) {
			elevators.add(new Elevator(1));
		}		
	}

	/**
	 * TODO:
	 * Improvements: 1. Each elevator will cater only one floor at a time, so it should stop for the floors requested in its direction
	 * 				 2. When a elevator is in hold position for a long time, alert the system.
	 * 				 3. Put a elevator to hold in emergency or maintenance
	 */
	@Override
	public void step() {
		for (Elevator elevator : elevators) {
			switch(elevator.getStatus()) {
			case Empty:
				if(!pickUps.isEmpty())
					elevator.addDestination(pickUps.poll());
				break;
			case Occupied:
				switch(elevator.getDirection()) {
				case Elevator_Up:
					elevator.moveUp();
					break;
				case Elevator_Down:
					elevator.moveDown();
					break;
				case Elevator_Hold:
					//Destination reached so pop the destination for that element
					elevator.popDestination();
					break;
				}
				break;
			}
		}
	}

	@Override
	public void pickUp(int floorNo) {
		pickUps.offer(floorNo);

	}

	@Override
	public void destination(int elevatorId, int toFloor) {
		elevators.get(elevatorId).addDestination(toFloor);

	}

}

package designs.elevator.logic;

import java.util.LinkedList;
import java.util.Queue;

import javax.print.attribute.standard.Destination;

import designs.elevator.enums.Direction;
import designs.elevator.enums.Status;
import designs.elevator.interfaces.IElevator;

public class Elevator implements IElevator {

	private int currFloor;
	private Queue<Integer> nextDestination;
	
	public Elevator(int currFloorNo){
		this.currFloor = currFloorNo;
		nextDestination = new LinkedList<Integer>();
	}
	@Override
	public void moveUp() {
		currFloor++;

	}

	@Override
	public void moveDown() {
		currFloor--;
	}

	public int getCurrFloor() {
		return currFloor;
	}
	public int getNextDestination() {
		return nextDestination.peek();
	}
	
	public void popDestination(){
		nextDestination.poll();
	}
	
	@Override
	public Direction getDirection() {
		if(nextDestination.size() < 1 || currFloor == nextDestination.peek())
			return Direction.Elevator_Hold;
		if(currFloor < nextDestination.peek())
			return Direction.Elevator_Up;
		else
			return Direction.Elevator_Down;		
	}

	@Override
	public Status getStatus() {
		return nextDestination.size() < 1 ? Status.Empty : Status.Occupied;
	}

	@Override
	public void addDestination(int floorNo) {
		nextDestination.offer(floorNo);

	}

}

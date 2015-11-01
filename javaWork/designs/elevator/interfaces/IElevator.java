package designs.elevator.interfaces;

import designs.elevator.enums.*;

public interface IElevator {
	public void moveUp();
	public void moveDown();
	public Direction getDirection();
	public Status getStatus();
	public void addDestination(int floorNo);

}

package designs.elevator.interfaces;

public interface IElevatorControlSystem {
	final int Total_Floors = 16;
	public void step();
	public void destination(int elevatorId, int toFloor);
	public void pickUp(int floorNo);
	
}

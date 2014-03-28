package iu.edu.cs.p532.pair13.acquire.admin;

public class TurnDetail{
	int turnID;
	State beginState;
	State placeState;
	State buyState;
	State doneState;
	public TurnDetail(int turnID, State beginState, State placeState,
			State buyState, State doneState) {
		this.turnID = turnID;
		this.beginState = beginState;
		this.placeState = placeState;
		this.buyState = buyState;
		this.doneState = doneState;
	}
}
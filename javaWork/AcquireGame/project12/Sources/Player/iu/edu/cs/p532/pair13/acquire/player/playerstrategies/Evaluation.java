package iu.edu.cs.p532.pair13.acquire.player.playerstrategies;

import iu.edu.cs.p532.pair13.acquire.player.components.PState;
import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;





public class Evaluation {
	private PState eState;
	private double eValue;
	//private IEvaluate EvalPlan;
	

	public Evaluation(PState state,int PlayerID,IEvaluate EvalPlan) throws GameException{
		this.eState = state;
		this.eValue = EvalPlan.evaluateState(state,PlayerID);

	}
	public Evaluation(double eValue) throws GameException{
		this.eState = null;
		this.eValue = eValue;

	}
	public void setState(PState eState){
		this.eState = eState;
	}
	public double getValue(){
		return this.eValue;
	}
	public PState getState(){
		return this.eState;
	}
}

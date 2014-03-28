package iu.edu.cs.p532.pair13.acquire.player.playerstrategies;

import iu.edu.cs.p532.pair13.acquire.player.components.*;
import iu.edu.cs.p532.pair13.acquire.player.constants.GameParameters;
import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;

//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;






public class EvaluationStrategy implements iStrategy, GameParameters{
	private IEvaluate ev;
	public static final int maxHotelSize = 41;
	public PState chooseState(PState inState) throws GameException {
		PState state = new PState(inState);
		if (state.getPlayers().get(0).getName().equals("eval1")){
			ev = new evalN();
		}
		else if(state.getPlayers().get(0).getName().equals("eval2")){
			ev = new eval2();
		}
		else 
			return null;

		return minimax(state,2,1,ev).getState();

	}
	public Evaluation minimax(PState inState,int depth,int me,IEvaluate evalPlan) throws GameException{
		PState state = new PState(inState);
		int totalPlayers = state.getPlayers().size();
		Evaluation evn;
		//System.out.println("minimax");
		if (depth == 0 || checkGameTermination(state)){
			if (me == 1)
				evn = new Evaluation(state,0,evalPlan);
			else
				evn = new Evaluation(state,totalPlayers - me + 1,evalPlan);
			return evn;
		}
		if(me == 1){
			Evaluation bestEvn = new Evaluation(0);
			GameTree gtree = new GameTree(state);
			gtree.generate();
			for(GameTree gt : gtree.subtrees){	
				//System.out.println(depth);
				evn = minimax(gt.Data,depth-1,2,evalPlan);
				bestEvn = evn.getValue() >= bestEvn.getValue() ? evn : bestEvn;
				if(bestEvn.getValue() == evn.getValue())
					bestEvn.setState(gt.Data);
			}
			return bestEvn;
		}
		else{
			Evaluation bestEvn = new Evaluation(1);
			GameTree gtree = new GameTree(state);
			gtree.generate();
			me = me == totalPlayers ? 1 : me+1;
			for(GameTree gt : gtree.subtrees){	
				//System.out.println(depth);
				evn = minimax(gt.Data,depth-1,me,evalPlan);
				bestEvn = evn.getValue() <= bestEvn.getValue() ? evn : bestEvn;
				if(bestEvn.getValue() == evn.getValue())
					bestEvn.setState(gt.Data);
			}
			return bestEvn;
		}
	}

	public boolean checkGameTermination(PState state) throws GameException {
		int count = 0;
		for(Hotel h : state.getBoard().getHotels()){
			if(h.getSize()>= maxHotelSize)
				return true;
			if (h.getSize()>= GameParameters.safeHotelSize)
				count++;
		}
		if (count>0 && count == state.getBoard().getHotels().size())
			return true;

		/*if(state.getPlayers().get(0).getTiles().size() == 0)
			return true;

		for(Player p : state.getPlayers()){
			int safeCount = 0;
			for(Tile t : p.getTiles()){
				if(state.getBoard().InspectTile(t).equals("safe"))
					safeCount++;
			}
			if(safeCount == p.getTiles().size())
				return true;
		}
		for(Player p : state.getPlayers()){
			int foundCount = 0;
			for(Tile t : p.getTiles()){
				if(state.getBoard().InspectTile(t).equals("founding") && 
						state.getBoard().getHotels().size() == numberOfHotels)
					foundCount++;
			}
			if(foundCount == p.getTiles().size())
				return true;
		}*/

		return false;
	}
	/*class CustomEStateComparator implements Comparator<Evaluation> {
		public int compare(Evaluation e1, Evaluation e2) {

			if (e1.getValue()>e2.getValue()) 
				return -1;
			else if (e1.getValue()<e2.getValue()) 
				return 1;
			else 
				return 0;

		}
	}*/
}
package iu.edu.cs.p532.pair13.acquire.player.playerstrategies;

import iu.edu.cs.p532.pair13.acquire.player.components.*;
import iu.edu.cs.p532.pair13.acquire.player.constants.GameParameters;
import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;


import java.util.Collections;
import java.util.Comparator;





public class evalN implements IEvaluate,GameParameters{

	public static final int maxHotelSize = 41;
	public static final int totalShares = 25;

	public double evaluateState(PState state,int PlayerID) throws GameException{
		double val = 0;

		//rating based on player's buying power
		Player cPlayer = state.getPlayers().get(PlayerID);

		if(state.getBoard().getHotels().size()>0){
			//System.out.println(cPlayer.getName());
			Collections.sort(state.getBoard().getHotels(), new CustomHotelComparator());
			if(cPlayer.getCash() >= (3*state.getBoard().getHotels().get(0).getStockCost()))
				val=val+100;
			else if(cPlayer.getCash() >= (2*state.getBoard().getHotels().get(0).getStockCost()))
				val=val+95;
			else if(cPlayer.getCash() >= state.getBoard().getHotels().get(0).getStockCost())
				val=val+85;
			else if(cPlayer.getCash() >=
					(3*state.getBoard().getHotels().get(state.getBoard().getHotels().size() - 1).getStockCost()))
				val=val+70;
			else if(cPlayer.getCash() >=
					(2*state.getBoard().getHotels().get(state.getBoard().getHotels().size() - 1).getStockCost()))
				val=val+50;
			else if(cPlayer.getCash() >=
					state.getBoard().getHotels().get(state.getBoard().getHotels().size() - 1).getStockCost())
				val=val+25;
			
		}
		//rating based on player's chance of getting merger bonus
			val += addValueByBonus(state,PlayerID);
		val = val / 500;
		val = val > 1 ? 1 : val;
		//System.out.println(val);
		return val;
	}
	class CustomHotelComparator implements Comparator<Hotel> {
		public int compare(Hotel h1, Hotel h2) {

			if (h1.getStockCost()>h2.getStockCost()) 
				return -1;
			else if (h1.getStockCost()<h2.getStockCost()) 
				return 1;
			else 
				return 0;

		}
	}
	public  double addValueByBonus(PState state, int PlayerID) throws GameException{
		double val = 0;
		Player p = state.getPlayers().get(PlayerID);
		Board board = state.getBoard();
		for (Share s : p.getShares()){
			int remShares = state.getRemShareCountByLabel(s.getLabel());
			int pCount = s.getCount();
			val  = val + pCount* board.getHotelByName(s.getLabel()).getStockCost()/100;
			if(pCount >= 13){
				val = val + board.getHotelByName(s.getLabel()).getBonus1()/100;
			}
			else if(pCount >= 4 && remShares + pCount == totalShares ){
				val = val + board.getHotelByName(s.getLabel()).getBonus1()/120;
			}
			else if(remShares + pCount == totalShares ){
				val = val + board.getHotelByName(s.getLabel()).getBonus1()/150;
			}
			else if(pCount >= 10 &&   2*pCount > totalShares - remShares ){
				val = val + board.getHotelByName(s.getLabel()).getBonus1()/150;
			}
			else if( 2*pCount > totalShares - remShares ){
				val = val + board.getHotelByName(s.getLabel()).getBonus1()/200;
			}
		}
		return val;
	}
}
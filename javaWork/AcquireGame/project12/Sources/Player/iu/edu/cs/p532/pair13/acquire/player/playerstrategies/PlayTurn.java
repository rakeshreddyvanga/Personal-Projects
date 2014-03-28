package iu.edu.cs.p532.pair13.acquire.player.playerstrategies;

import iu.edu.cs.p532.pair13.acquire.player.components.Board;
import iu.edu.cs.p532.pair13.acquire.player.components.Share;
import iu.edu.cs.p532.pair13.acquire.player.components.Tile;
import iu.edu.cs.p532.pair13.acquire.player.components.Turn;
import iu.edu.cs.p532.pair13.acquire.player.constants.GameParameters;
import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;

import java.util.ArrayList;


public class PlayTurn implements GameParameters{
	
	Turn turn;
	PlayerStrategy pStrategy;
	
	//using Strategy design pattern for choosing strategy
	public PlayTurn(Turn turn, PlayerStrategy pStrategy) {
		this.turn = turn;
		this.pStrategy = pStrategy;
	}
	public ArrayList<String> BuyShares() {
		ArrayList<String> ret = new ArrayList<String>();
		Share s;
		for(int i =1; i<=3; i++){
			while(turn.getShares().size()>0){
				s = pStrategy.ChooseShare(turn.getShares());
				if(turn.getPlayer().getCash()>=
						turn.getBoard().getHotelByName(s.getLabel()).getStockCost()){
					turn.getPlayer().setCash(turn.getPlayer().getCash() - 
							turn.getBoard().getHotelByName(s.getLabel()).getStockCost());
					ret.add(s.getLabel());
					s.setCount(s.getCount()-1);
					if(turn.getPlayer().hasShareByLabel(s.getLabel()))
						turn.getPlayer().getShareByLabel(s.getLabel()).
						setCount(turn.getPlayer().getShareByLabel(s.getLabel()).getCount() + 1);
					else
						turn.getPlayer().getShares().add(new Share(s.getLabel(),1));
					if(s.getCount()==0)
						turn.getShares().remove(s);
					break;
				}
				else
					turn.getShares().remove(s);

			}
		}
		return ret;
	}
	public Tile ChooseTile() throws GameException {
		String conseq;
		Tile placeTile;
		do{
			if(turn.getPlayer().getTiles().size()>0)
				placeTile = pStrategy.ChooseTile(turn.getPlayer().getTiles());
			else
				return null;
			turn.getPlayer().getTiles().remove(placeTile);
			conseq = turn.getBoard().InspectTile(placeTile);
		}while(conseq.equals("occupied") || conseq.equals("safe") 
				|| (conseq.equals("founding") && turn.getBoard().getHotels().size() == numberOfHotels));
		
		if(conseq.equals("founding"))
			placeTile.setStatus(pStrategy.ChooseFoundngHotel(turn.getxHotels()));
		if(Board.isHotel(conseq))
			placeTile.setStatus(conseq);

		return placeTile;
	}
}
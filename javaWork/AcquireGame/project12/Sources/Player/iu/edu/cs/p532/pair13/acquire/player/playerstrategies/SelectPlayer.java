package iu.edu.cs.p532.pair13.acquire.player.playerstrategies;


import iu.edu.cs.p532.pair13.acquire.player.components.Board;
import iu.edu.cs.p532.pair13.acquire.player.components.PState;
import iu.edu.cs.p532.pair13.acquire.player.components.Tile;
import iu.edu.cs.p532.pair13.acquire.player.components.Turn;
import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;
import iu.edu.cs.p532.pair13.acquire.player.parsers.TurnParser;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

public  class SelectPlayer{

	public static String takeTurn(String takeTurn,String player) throws iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException, GameException{

		PlayTurn p;
		String action,place = "",hotel = "";
		Turn t = TurnParser.parseTurnRequest(new BufferedReader(new StringReader("<root>" + takeTurn + "</root>")));
		if(player.equals("ordered"))
			p = new PlayTurn(t,new OrderedPlayer());
		else if(player.equals("random"))
			p = new PlayTurn(t,new RandomPlayer());
		else if(player.equals("lalpha"))
			p = new PlayTurn(t,new LargestAlpha());
		else if(player.equals("santi"))
			p = new PlayTurn(t,new SmallestAnti());
		else
			throw new GameException("invalid player");

		Tile tile = p.ChooseTile(); 
		ArrayList<String> shares = p.BuyShares();

		if(tile!=null){
			if(Board.isHotel(tile.getStatus()))
				hotel = "\" hotel=\""+tile.getStatus();
			place = "<place row=\"" + tile.getRow() + "\" column=\"" + tile.getColumn()+hotel + "\" >  </place>";
		}

		action="<action win=\"yes\" ";
		for (int i=1;i<shares.size()+1;i++){
			action = action + " hotel" + i + "=\"" + shares.get(i-1) + "\"";
		}
		action=action + " >" + place + "</action>";
		return action;
	}
	public static String takeTurn2(String takeTurn,String name) throws iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException, GameException{

		iStrategy strat;
		
		Turn t = TurnParser.parseTurnRequest(new BufferedReader(new StringReader("<root>" + takeTurn + "</root>")));
		Player p = t.getPlayer();
		p.setName(name);
		PState ps = t.toPState();
		if(ps.getPlayers().get(0).getName().equals("eval1") || ps.getPlayers().get(0).getName().equals("eval2"))
			strat = new EvaluationStrategy();
		else if(ps.getPlayers().get(0).getName().equals("rand1") || ps.getPlayers().get(0).getName().equals("rand2") || ps.getPlayers().get(0).getName().equals("rand3"))
			strat = new RandomStrategy();
		else
			throw new GameException("invalid player");

		return strat.chooseState(ps).ActionString();
		
		
		
	}

}
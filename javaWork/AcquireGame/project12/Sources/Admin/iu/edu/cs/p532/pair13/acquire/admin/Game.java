package iu.edu.cs.p532.pair13.acquire.admin;
import iu.edu.cs.p532.pair13.acquire.components.Hotel;
import iu.edu.cs.p532.pair13.acquire.components.Player;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;
import iu.edu.cs.p532.pair13.acquire.parsers.ActionParser;
import iu.edu.cs.p532.pair13.acquire.parsers.Parser;
//import iu.edu.cs.p532.pair13.acquire.player.playerstrategies.SelectPlayer;


import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;



public class Game implements GamePlay,GameParameters{

	public ArrayList<TurnDetail> turnDetails = new ArrayList<TurnDetail>();
	public static final int maxHotelSize = 41;

	public GameResult runGame(String[] players, GameServerTester connection) throws GameException, GameException{
		int turnCount = 0;
		State currState;
		State placeState;
		State buyState;
		State doneState;
		currState = beginGame(players);
		Action action = new Action(null,null,true);
		while(!checkGameTermination(currState,action)){
			//String actionStr = takeTurn(currState);
			GameTree gt = new GameTree(currState);
			gt.generate();
			int turnCheck = 0;
			//String actionStr = SelectPlayer.takeTurn2(currState.getTakeTurnElement(),currState.getPlayers().get(0).getName());
			String actionStr = connection.TakeTurn(currState.getTakeTurnElement());
			//System.out.println(actionStr);
			if(actionStr.contains("<error")){
				System.out.println(actionStr);
				break;
			}
			action = ActionParser.parseInputRequest(actionStr);
			if (action.getPlaceTile() != null)
				placeState = placePlayerTile(currState,action.getPlaceTile());
			else
				placeState = currState;
			if(action.getBuyShares().size() > 0)
				buyState = buyStock(placeState,action.getBuyShares());
			else
				buyState = placeState;
			doneState = endTurn(buyState);
			for(GameTree t : gt.subtrees){
				if(buyState.equals(t.Data)){
					turnCheck = 1;
					break;
				}
			}
			if(turnCheck == 0){
				GameResult gameResult = new GameResult();
				gameResult.addResult(doneState.getPlayers().get(doneState.getPlayers().size() - 1).getName(), -1);
				return gameResult;
			}
			turnDetails.add(new TurnDetail(turnCount++,currState,placeState,buyState,doneState));
			currState = doneState;
			//doneState.getBoard().printBoard();
		}
		State endState = endGame(currState);
		GameResult gameResult = new GameResult();
		for (Player p : endState.getPlayers())
			gameResult.addResult(p.getName(), p.getCash());
		return gameResult;

	}
	/*public State runGame(String[] players) throws GameException, GameException{
		int turnCount = 0;
		State currState;
		State placeState;
		State buyState;
		State doneState;
		currState = beginGame(players);
		Action action = new Action(null,null,true);
		while(!checkGameTermination(currState,action)){
			//String actionStr = takeTurn(currState);
			System.out.println(currState.getPlayers().get(0).getName());
			String actionStr = SelectPlayer.takeTurn2(currState.getTakeTurnElement(),
					currState.getPlayers().get(0).getName());
			action = ActionParser.parseInputRequest(actionStr);
			if (action.getPlaceTile() != null)
				placeState = placePlayerTile(currState,action.getPlaceTile());
			else
				placeState = currState;
			if(action.getBuyShares().size() > 0)
				buyState = buyStock(placeState,action.getBuyShares());
			else
				buyState = placeState;
			doneState = endTurn(buyState);
			turnDetails.add(new TurnDetail(turnCount++,currState,placeState,buyState,doneState));
			currState = doneState;
			//doneState.getBoard().printBoard();
		}
		State endState = endGame(currState);
		return endState;
	}*/
	public State beginGame(String[] Players) throws GameException {
		String setup = "<setup ";
		for(int i = 0; i<Players.length;i++){
			setup = setup + "player"+(i+1)+"=\""+Players[i]+"\" ";
		}
		setup = setup + " />";
		//System.out.println(setup);
		return Parser.parseInputRequest(new BufferedReader(new StringReader("<root>" + setup + "</root>")));
	}
	@Override
	public State placePlayerTile(State state, iu.edu.cs.p532.pair13.acquire.components.Tile tile) throws GameException {

		if(tile!=null){
			String s = state.constructStateElement();
			if (!BoardManager.isHotel(tile.getStatus()))
				s = "<place row=\""+tile.getRow()+"\" column=\""+tile.getColumn()+"\" >" +s;
			else
				s = "<place row=\""+tile.getRow()+"\" column=\""+tile.getColumn()+"\" hotel=\""+tile.getStatus()+"\" >"+s;

			s = s + "</place>";


			return Parser.parseInputRequest(new BufferedReader(new StringReader(s)));
		}
		else
			return state;
	}


	public State buyStock(State state, ArrayList<String> hotelNames) throws GameException {
		if(hotelNames.size()!=0)
			return StateManager.buyShare(state, hotelNames);
		else
			return state;

	}
	public State endGame(State state) throws GameException {
		for(Hotel hotel : state.getBoard().getHotels()){
			PlaceTileManager.GiveAwayBonus(state, hotel);
			PlaceTileManager.sellStocks(state, hotel);
		}
		Collections.sort(state.getPlayers(), new CustomComparator());
		return state;
	}
	/*public String takeTurn(State state) throws iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException, GameException{
		String takeTurn = state.getTakeTurnElement();
		return SelectPlayer.takeTurn(takeTurn, state.getPlayers().get(0).getName());

	}*/



	@Override
	public State endTurn(State state) throws GameException {
		return StateManager.FinishTurn(state);
	}



	@Override
	public boolean checkGameTermination(State state,Action action) {
		int count = 0;
		for(Hotel h : state.getBoard().getHotels()){
			if(h.getSize()>= maxHotelSize && action.isWin())
				return true;
			if (h.getSize()>= GameParameters.safeHotelSize)
				count++;
		}
		if (count>0 && count == state.getBoard().getHotels().size() && action.isWin())
			return true;

		if(state.getPlayers().get(0).getTiles().size() == 0)
			return true;

		return false;
	}
	class CustomComparator implements Comparator<Player> {
		public int compare(Player p1, Player p2) {

			if (p1.getCash()>p2.getCash()) 
				return -1;
			else if (p1.getCash()<p2.getCash()) 
				return 1;
			else 
				return 0;

		}
	}

}

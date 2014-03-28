package iu.edu.cs.p532.pair13.acquire.admin;
import iu.edu.cs.p532.pair13.acquire.components.Board;
import iu.edu.cs.p532.pair13.acquire.components.Hotel;
import iu.edu.cs.p532.pair13.acquire.components.Player;
import iu.edu.cs.p532.pair13.acquire.components.Share;
import iu.edu.cs.p532.pair13.acquire.components.Tile;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;

import java.util.ArrayList;
import java.util.Random;

public class StateManager implements GameParameters{

	private static final int TotalShares = 25;

	public static State GameSetup(ArrayList<Player> players) throws GameException{
		State state = new State(players);
		Board board = state.getBoard();
		Random randomGenerator = new Random();
		for (Player p: players){
			for(int i = 0; i <6;i++){
				ArrayList<Tile> TilePool = CreateTilePool(state);
				int randomInt = randomGenerator.nextInt(TilePool.size());
				p.AddTile(TilePool.get(randomInt));
				board.setAllocatedRowCol(rows.inverse().get(TilePool.get(randomInt).getRow()), TilePool.get(randomInt).getColumn());
			}
		}
		return state;
	}
	public static ArrayList<Tile> CreateTilePool (State state) throws GameException{
		ArrayList<Tile> TilePool = new ArrayList<Tile>();
		Board board = state.getBoard();
		for (int i=1;i<=ROWS;i++){
			for (int j=1; j<=COLS;j++){
				if(board.getStatusOfRowCol(i,j).equals("free") && board.getAllocatedRowCol(i,j) == 0
						&& !board.InspectTile(board.getTileByRowCol(i,j)).equals("safe"))
					TilePool.add(board.getTileByRowCol(i,j));
			}
		}
		return TilePool;
	}

	public static State CreateState(Board board,ArrayList<Player> players) 
			throws GameException{
		State state= new State (board,players);
		for(Player p : state.getPlayers()){
			for(Tile t: p.getTiles()){
				board.AddPlayerTile(t);
			}
		}
		chkState(state);
		return state;
	}

	public static State buyShare(State buyState, ArrayList<String> labels) throws GameException{
		State state = new State(buyState);
		Board board = state.getBoard();
		Hotel hotel = null;
		for(String s : labels){
			if (!Board.isHotel(s))
				throw new GameException("Invalid hotel name : " + s);
		}
		if(labels.size() > 3)
			throw new GameException("Player cannot buy more than 3 shares at a time");

		for (int i = 0; i < labels.size();i++){
			if(board.hasHotelByName(labels.get(i))){
				hotel = board.getHotelByName(labels.get(i));
				Player cPlayer = state.getPlayers().get(0);
				if(cPlayer.getCash() >= hotel.getStockCost()){
					if (calculateRemainingShares(state, labels.get(i))>0){
						addSharesToCurrentPlayer(state,labels.get(i));
						cPlayer.setCash(cPlayer.getCash() - hotel.getStockCost());

					}
					else
						throw new GameException(labels.get(i)+" does not have enough shares left");
				}
				else
					throw new GameException(cPlayer.getName()+" does not have enough cash to buy these shares");
			}
			else 
				throw new GameException(labels.get(i)+" does not have a chain on the board");

		}
		return state;
	}
	public static boolean chkState(State state) throws GameException{

		for(int i=0;i<ListOfHotels.length;i++){
			if (calculateRemainingShares(state,ListOfHotels[i])<0)
				throw new GameException(ListOfHotels[i]+" has more than "+TotalShares
						+" shares in total held by all players");
		}
		return true;

	}
	public static State FinishTurn(State finishState) throws GameException{
		State state = new State(finishState);
		Player cPlayer = state.getPlayers().get(0);
		Board board = state.getBoard();
		Random randomGenerator = new Random();
		ArrayList<Tile> TilePool = CreateTilePool(state);
		while(cPlayer.getTiles().size()<6 && TilePool.size()>0){
			int randomInt = randomGenerator.nextInt(TilePool.size());
			cPlayer.AddTile(TilePool.get(randomInt));
			board.setAllocatedRowCol(rows.inverse().get(TilePool.get(randomInt).getRow()), TilePool.get(randomInt).getColumn());
		}
		cPlayer = state.removeFirstPlayer();
		state.addPlayer(cPlayer);
		replaceSafeTilesOfPlayers(state);
		return state;
	}
	public static State replaceSafeTilesOfPlayers(State state) throws GameException{

		for(Player p : state.getPlayers()){
			for(int i = 0; i< p.getTiles().size();i++){
				if(state.getBoard().InspectTile(p.getTiles().get(i)).equals("safe")){
					p.getTiles().remove(i);
					Random randomGenerator = new Random();
					ArrayList<Tile> TilePool = CreateTilePool(state);
					if(TilePool.size()>0){
						int randomInt = randomGenerator.nextInt(TilePool.size());
						p.AddTile(TilePool.get(randomInt));
						state.getBoard().setAllocatedRowCol(rows.inverse().get(TilePool.get(randomInt).getRow()), TilePool.get(randomInt).getColumn());
					}
				}
			}
		}
		return state;
	}
	public static int calculateRemainingShares(State state, String label){
		int ret = TotalShares;
		for (Player p : state.getPlayers()){
			for (Share s : p.getShares()){
				if (s.getLabel().equals(label)){
					ret = ret - s.getCount();
				}
			}
		}
		return ret;
	}
	public static void addSharesToCurrentPlayer(State state, String label){
		Share s;
		if(calculateRemainingShares(state,label) > 0){
			Player p = state.getPlayers().get(0);
			if((s = p.getShareByLabel(label))!=null)
				s.setCount(s.getCount()+1);
			else
				p.AddShare(new Share(label,1));			
		}
	}
}
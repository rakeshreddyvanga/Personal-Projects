package iu.edu.cs.p532.pair13.acquire.admin;






import iu.edu.cs.p532.pair13.acquire.components.*;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;

import java.util.ArrayList;


public class GameTree implements GameParameters{

	public State Data;
	public ArrayList<GameTree> subtrees;

	public GameTree(State rootData) {
		Data = rootData;
		this.subtrees = new ArrayList<GameTree>();
	}
	public GameTree(State rootData, ArrayList<GameTree> subtrees) {
		Data = rootData;
		this.subtrees = subtrees;
	}
	public void generate() throws GameException{

		ArrayList<State> placeStates =  generatePlaceStates(this.Data);
		ArrayList<State> buyStates =  generateBuyStates(placeStates);
		//ArrayList<State> doneStates =  generateDoneStates(buyStates);

		for(State s : buyStates){
			this.subtrees.add(new GameTree(s));
		}
	}

	public ArrayList<State> generatePlaceStates(State state) throws GameException{
		Game game = new Game();
		Board board = state.getBoard();
		ArrayList<State> placeStates = new ArrayList<State>();
		for (Tile t : state.getPlayers().get(0).getTiles()){
			//System.out.println("alltiles");
			//t.printTile();
			String conseq = board.InspectTile(t);
			if(conseq.equals("singleton") || conseq.equals("growing"))
				placeStates.add(game.placePlayerTile(this.Data,t));
			else if(conseq.equals("founding") && board.getHotels().size() < numberOfHotels){
				ArrayList<String> remHotels = board.getRemainingHotels();
				for(String s : remHotels){
					placeStates.add(game.placePlayerTile(this.Data,
							new Tile(t.getRow(),t.getColumn(),s,t.getAllocated())));
				}
			}
			else if(Board.isHotel(conseq)){
				placeStates.add(game.placePlayerTile(this.Data,
						new Tile(t.getRow(),t.getColumn(),conseq,t.getAllocated())));
			}
		}
		return placeStates;
	}
	public ArrayList<State> generateBuyStates(ArrayList<State> placeState) throws GameException{
		ArrayList<State> shareCombinationList = new ArrayList<State>();
		for(State state: placeState)
		{
			shareCombinationList.add(state); //adding the state for purchase of zeros shares

			for(Hotel hotel : state.getBoard().getHotels()) //adding the state for purchase of one shares
			{
				if(hotel.getStockCost() <= state.getPlayers().get(0).getCash() && 
						StateManager.calculateRemainingShares(state, hotel.getName()) > 0)
				{
					ArrayList<String> hotelsToBuy = new ArrayList<String>();
					hotelsToBuy.add(hotel.getName());
					shareCombinationList.add(StateManager.buyShare(state, hotelsToBuy));
				}
			}

			for(Hotel firstHotel : state.getBoard().getHotels()) //adding the state for purchase of two shares
			{

				for(Hotel secondHotel : state.getBoard().getHotels())
				{
					int hotelFlag = 0;

					if(firstHotel.getStockCost() + secondHotel.getStockCost() <= state.getPlayers().get(0).getCash() &&
							StateManager.calculateRemainingShares(state, firstHotel.getName()) > 0 &&
							StateManager.calculateRemainingShares(state, secondHotel.getName()) > 0){
						if(firstHotel.getName().equals(secondHotel.getName()))
						{
							if(StateManager.calculateRemainingShares(state, secondHotel.getName()) < 2)
							{
								hotelFlag = 1;
							}
						}
						if(hotelFlag == 0)
						{
							ArrayList<String> hotelsToBuy = new ArrayList<String>();
							hotelsToBuy.add(firstHotel.getName());
							hotelsToBuy.add(secondHotel.getName());
							shareCombinationList.add(StateManager.buyShare(state, hotelsToBuy));
						}
					}
				}
			}

			for(Hotel firstHotel : state.getBoard().getHotels()) //adding the state for purchase of three shares
			{

				for(Hotel secondHotel : state.getBoard().getHotels())
				{
					for(Hotel thirdHotel : state.getBoard().getHotels()){
						int hotelFlag = 0;
						if(firstHotel.getStockCost() + thirdHotel.getStockCost() + secondHotel.getStockCost()
								<= state.getPlayers().get(0).getCash() &&
								StateManager.calculateRemainingShares(state, firstHotel.getName()) > 0 &&
								StateManager.calculateRemainingShares(state, secondHotel.getName()) > 0 &&
								StateManager.calculateRemainingShares(state, thirdHotel.getName()) > 0) {
							if(firstHotel.getName().equals(secondHotel.getName()))
							{
								if(StateManager.calculateRemainingShares(state, secondHotel.getName()) < 2)
								{
									hotelFlag = 1;
								}
							}
							if(firstHotel.getName().equals(thirdHotel.getName()))
							{
								if(StateManager.calculateRemainingShares(state, thirdHotel.getName()) < 2)
								{
									hotelFlag = 1;
								}
							}
							if(thirdHotel.getName().equals(secondHotel.getName()))
							{
								if(StateManager.calculateRemainingShares(state, secondHotel.getName()) < 2)
								{
									hotelFlag = 1;
								}
							}
							if(firstHotel.getName().equals(secondHotel.getName()) && 
									secondHotel.getName().equals(thirdHotel.getName()))
							{
								if(StateManager.calculateRemainingShares(state, secondHotel.getName()) < 3)
								{
									hotelFlag = 1;
								}
							}
							if(hotelFlag == 0)
							{
								ArrayList<String> hotelsToBuy = new ArrayList<String>();
								hotelsToBuy.add(firstHotel.getName());
								hotelsToBuy.add(secondHotel.getName());
								hotelsToBuy.add(thirdHotel.getName());
								shareCombinationList.add(StateManager.buyShare(state, hotelsToBuy));
							}
						}
					}
				}
			}


		}		
		return shareCombinationList;
	}
	public ArrayList<State> generateDoneStates(ArrayList<State> states) throws GameException{
		ArrayList<State> doneStates = new ArrayList<State>();
		for(State state : states){
			Player cPlayer = state.getPlayers().get(0);
			ArrayList<Tile> TilePool = StateManager.CreateTilePool(state);
			if(cPlayer.getTiles().size()<6 && TilePool.size()>0){
				for(Tile t : TilePool){
					State dState = new State(state);
					cPlayer = dState.removeFirstPlayer();
					cPlayer.AddTile(dState.getBoard().getTileByRowCol(rows.inverse().get(t.getRow()), t.getColumn()));
					dState.getBoard().setAllocatedRowCol
					(rows.inverse().get(t.getRow()), t.getColumn());					
					dState.addPlayer(cPlayer);
					doneStates.add(dState);
				}
			}
		}
		return doneStates;

	}
}
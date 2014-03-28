package iu.edu.cs.p532.pair13.acquire.player.playerstrategies;

import iu.edu.cs.p532.pair13.acquire.player.components.Board;
import iu.edu.cs.p532.pair13.acquire.player.components.Hotel;
import iu.edu.cs.p532.pair13.acquire.player.components.PState;
import iu.edu.cs.p532.pair13.acquire.player.components.Tile;
import iu.edu.cs.p532.pair13.acquire.player.constants.GameParameters;
import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;

import java.util.ArrayList;


public class GameTree implements GameParameters{

	public PState Data;
	public ArrayList<GameTree> subtrees;

	public GameTree(PState rootData) {
		Data = rootData;
		this.subtrees = new ArrayList<GameTree>();
	}
	public GameTree(PState rootData, ArrayList<GameTree> subtrees) {
		Data = rootData;
		this.subtrees = subtrees;
	}
	public void generate() throws GameException{
		//Game game = new Game();
		ArrayList<PState> placeStates =  generatePlaceStates(this.Data);
		ArrayList<PState> buyStates =  generateBuyStates(placeStates);
		ArrayList<PState> doneStates =  generateDoneState(buyStates);

		for(PState s : doneStates){
			this.subtrees.add(new GameTree(s));
		}
		/* for(Tree t : gameTree.subtrees){
			if(!game.checkGameTermination(t.Data)){
				GameTree gt = new GameTree(t.Data);
				gt.generate();
			}
			else 
				break;
		}*/
		//System.out.println("tree gen done");

	}

	public ArrayList<PState> generatePlaceStates(PState state) throws GameException{
		//Game game = new Game();
		Board board = state.getBoard();
		ArrayList<PState> placeStates = new ArrayList<PState>();
		if(state.getPlayers().get(0).getTiles().size() == 0)
			placeStates.add(new PState(state));
		else
			for (Tile t : state.getPlayers().get(0).getTiles()){
				String conseq = board.InspectTile(t);
				if(conseq.equals("singleton") || conseq.equals("growing"))
					placeStates.add(PlaceTileManager.PlaceTile(this.Data,t.getRow(),t.getColumn()));
				else if(conseq.equals("founding") && board.getHotels().size() < numberOfHotels){
					ArrayList<String> remHotels = board.getRemainingHotels();
					for(String s : remHotels){
						placeStates.add(PlaceTileManager.PlaceTile(this.Data,
								t.getRow(),t.getColumn(),s));
					}
				}
				else if(Board.isHotel(conseq)){
					placeStates.add(PlaceTileManager.PlaceTile(this.Data,
							t.getRow(),t.getColumn(),conseq));
				}
			}
		return placeStates;
	}
	public ArrayList<PState> generateBuyStates(ArrayList<PState> placeState) throws GameException{
		ArrayList<PState> shareCombinationList = new ArrayList<PState>();
		for(PState state: placeState)
		{
			shareCombinationList.add(state); //adding the state for purchase of zeros shares

			for(Hotel hotel : state.getBoard().getHotels()) //adding the state for purchase of one shares
			{
				if(hotel.getStockCost() <= state.getPlayers().get(0).getCash() && 
						PStateManager.calculateRemainingShares(state, hotel.getName()) > 0)
				{
					ArrayList<String> hotelsToBuy = new ArrayList<String>();
					hotelsToBuy.add(hotel.getName());
					shareCombinationList.add(PStateManager.buyShare(state, hotelsToBuy));
				}
			}

			for(Hotel firstHotel : state.getBoard().getHotels()) //adding the state for purchase of two shares
			{

				for(Hotel secondHotel : state.getBoard().getHotels())
				{
					int hotelFlag = 0;

					if(firstHotel.getStockCost() + secondHotel.getStockCost() <= state.getPlayers().get(0).getCash() &&
							PStateManager.calculateRemainingShares(state, firstHotel.getName()) > 0 &&
							PStateManager.calculateRemainingShares(state, secondHotel.getName()) > 0){
						if(firstHotel.getName().equals(secondHotel.getName()))
						{
							if(PStateManager.calculateRemainingShares(state, secondHotel.getName()) < 2)
							{
								hotelFlag = 1;
							}
						}
						if(hotelFlag == 0)
						{
							ArrayList<String> hotelsToBuy = new ArrayList<String>();
							hotelsToBuy.add(firstHotel.getName());
							hotelsToBuy.add(secondHotel.getName());
							shareCombinationList.add(PStateManager.buyShare(state, hotelsToBuy));
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
								PStateManager.calculateRemainingShares(state, firstHotel.getName()) > 0 &&
								PStateManager.calculateRemainingShares(state, secondHotel.getName()) > 0 &&
								PStateManager.calculateRemainingShares(state, thirdHotel.getName()) > 0) {
							if(firstHotel.getName().equals(secondHotel.getName()))
							{
								if(PStateManager.calculateRemainingShares(state, secondHotel.getName()) < 2)
								{
									hotelFlag = 1;
								}
							}
							if(firstHotel.getName().equals(thirdHotel.getName()))
							{
								if(PStateManager.calculateRemainingShares(state, thirdHotel.getName()) < 2)
								{
									hotelFlag = 1;
								}
							}
							if(thirdHotel.getName().equals(secondHotel.getName()))
							{
								if(PStateManager.calculateRemainingShares(state, secondHotel.getName()) < 2)
								{
									hotelFlag = 1;
								}
							}
							if(firstHotel.getName().equals(secondHotel.getName()) && 
									secondHotel.getName().equals(thirdHotel.getName()))
							{
								if(PStateManager.calculateRemainingShares(state, secondHotel.getName()) < 3)
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
								shareCombinationList.add(PStateManager.buyShare(state, hotelsToBuy));
							}
						}
					}
				}
			}
		}		
		return shareCombinationList;
	}
	public ArrayList<PState> generateDoneStates(ArrayList<PState> states) throws GameException{
		ArrayList<PState> doneStates = new ArrayList<PState>();
		for(PState state : states){
			Player cPlayer = state.getPlayers().get(0);
			ArrayList<Tile> TilePool = PStateManager.CreateTilePool(state);
			if(cPlayer.getTiles().size()<6 && TilePool.size()>0){
				for(Tile t : TilePool){
					PState dState = new PState(state);
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
	public ArrayList<PState> generateDoneState(ArrayList<PState> states) throws GameException{
		ArrayList<PState> doneStates = new ArrayList<PState>();
		for(PState state : states){
			PState dState = new PState(state);
			Player cPlayer = dState.removeFirstPlayer();				
			dState.addPlayer(cPlayer);
			doneStates.add(dState);
		}
		return doneStates;
	}
}
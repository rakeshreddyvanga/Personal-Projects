package iu.edu.cs.p532.pair13.acquire.admin;
import iu.edu.cs.p532.pair13.acquire.components.Board;
import iu.edu.cs.p532.pair13.acquire.components.Hotel;
import iu.edu.cs.p532.pair13.acquire.components.Player;
import iu.edu.cs.p532.pair13.acquire.components.Share;
import iu.edu.cs.p532.pair13.acquire.components.Tile;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;

import java.util.ArrayList;


public class PlaceTileManager implements GameParameters {

	public static State PlaceTile(State placeState, String row, int col)throws GameException{
		int rowIndex = rows.inverse().get(row);
		int colIndex = col;
		int freeCount = 0, hotelCount = 0;
		Tile[] Adj;
		State state = new State(placeState);
		Board board = state.getBoard();
		String label = null;
		if (!board.getStatusOfRowCol(rowIndex,colIndex).equals("free")){		
			throw new GameException("Tile :" +col+row+" is already occupied");
		}
		Player cPlayer = state.getPlayers().get(0);
		cPlayer.hasTileByRowCol(row, col);
		Adj = getAdjacentTiles(board,rowIndex,colIndex);


		ArrayList<String> procHotels = new ArrayList<String>();
		int procCount = 0;
		for(int i=0;i<4;i++){
			if(Adj[i].getStatus().equals("free"))
				freeCount++;
			else if(Adj[i].getStatus().equals("singleton"));
			else{
				for(String s : procHotels){
					if(s!=null && s.equals(Adj[i].getStatus())){
						procCount++;
					}					
				}
				if (procCount > 0){
					procCount = 0;
				}
				else{
					hotelCount++;
					procHotels.add(Adj[i].getStatus());
				}
			}
		}
		//Singleton scenario
		if(freeCount == 4){
			board.setStatusOfRowCol(rowIndex,colIndex,"singleton");
			state.setBoard(board);
			cPlayer.removeTileByRowCol(row, col);
			return state;
		}
		//founding scenario
		if(hotelCount == 0)
			throw new GameException("Hotel name is needed for founding");

		//Growing scenario
		if(hotelCount == 1){
			for(int i=0;i<4;i++){
				if(!Adj[i].getStatus().equals("singleton") && !Adj[i].getStatus().equals("free")){
					label = Adj[i].getStatus();
					break;
				}
			}
			Hotel hotel = board.getHotelByName(label);
			for(int i=0;i<4;i++){
				if(Adj[i].getStatus().equals("singleton")){
					hotel.AddTile(new Tile(Adj[i].getRow(),Adj[i].getColumn(),label,1));
					board.AddTile(new Tile(Adj[i].getRow(),Adj[i].getColumn(),label,1));
				}
			}
			board.AddTile(new Tile(row,col,label,1));
			hotel.AddTile(new Tile(row,col,label,1));
			hotel.setSize(hotel.getTiles().size());
			state.setBoard(board);
			cPlayer.removeTileByRowCol(row, col);
			return state;
		}
		//Merging scenario
		throw new GameException("Acquirer hotel name is needed for merging");
	}



	public static State PlaceTile(State placeState, String row, int col, String label) throws GameException{
		int rowIndex = rows.inverse().get(row);
		int colIndex = col;
		int freeCount = 0, hotelCount = 0;
		State state = new State(placeState);
		String Acq=null,Adj1=null,Adj2 = null,Adj3 = null;
		Tile[] Adj = new Tile[4];
		Board board = state.getBoard();
		Player cPlayer = state.getPlayers().get(0);
		cPlayer.hasTileByRowCol(row, col);
		if(!BoardManager.isHotel(label))
			throw new GameException("Invalid Hotel Name:" +label);
		if (!board.getStatusOfRowCol(rowIndex,colIndex).equals("free"))	
			throw new GameException("Tile :" +col+row+" is already occupied");



		Adj = getAdjacentTiles(board,rowIndex,colIndex);
		ArrayList<String> procHotels = new ArrayList<String>();
		int procCount = 0;
		for(int i=0;i<4;i++){
			if(Adj[i].getStatus().equals("free"))
				freeCount++;
			else if(Adj[i].getStatus().equals("singleton"));
			else{
				for(String s : procHotels){
					if(s!=null && s.equals(Adj[i].getStatus())){
						procCount++;
					}					
				}
				if (procCount > 0){
					procCount = 0;
				}
				else{
					hotelCount++;
					procHotels.add(Adj[i].getStatus());
				}
			}
		}
		//Singleton scenario
		if(freeCount == 4){
			throw new GameException("Founding is not possible in this tile");
		}
		//Founding scenario
		if(hotelCount == 0){
			if(board.hasHotelByName(label))
				throw new GameException(""+label+" hotel chain already exists on board");
			Hotel hotel = new Hotel(label);
			board.AddTile(new Tile(row,col,label,1));
			hotel.AddTile(new Tile(row,col,label,1));
			for(int i=0;i<4;i++){
				if(Adj[i].getStatus().equals("singleton")){
					hotel.AddTile(new Tile(Adj[i].getRow(),Adj[i].getColumn(),label,1));
					board.AddTile(new Tile(Adj[i].getRow(),Adj[i].getColumn(),label,1));
				}
			}
			board.AddHotel(hotel);
			state.setBoard(board);
			//giving one free share to the player who founds the hotel
			StateManager.addSharesToCurrentPlayer(state, label);
			cPlayer.removeTileByRowCol(row, col);
			return state;		
		}
		if(hotelCount == 1){
			throw new GameException("Merging is not possible in this tile");
		}
		if(hotelCount == 2){
			for(int i=0;i<4;i++){
				if(BoardManager.isHotel(Adj[i].getStatus())){
					if(Adj1 == null){
						Adj1 = Adj[i].getStatus();
						continue;
					}
					if(!Adj[i].getStatus().equals(Adj1)){
						Adj2 = Adj[i].getStatus();
						break;
					}
				}
			}
			if(Adj1.equals(label)){
				Acq = Adj1;
				Adj1 =Adj2;
			}
			else if(Adj2.equals(label)){
				Acq = Adj2;
			}
			else
				throw new GameException("Merging is not possible as "
						+Acq+" is not adjacent to the given tile.");

			if(board.getHotelByName(Adj1).getSize() >= GameParameters.safeHotelSize)
				throw new GameException(""+Acq+" cannot acquire "+Adj1+". "+Adj1+" is safe.");

			if(board.getHotelByName(Acq).getSize() < board.getHotelByName(Adj1).getSize())
				throw new GameException(""+Acq+" cannot acquire "
						+Adj1+". "+Adj1+" is larger than "+Acq);

			Hotel hotel1 = board.getHotelByName(label);
			for(int i=0;i<4;i++){
				if(Adj[i].getStatus().equals("singleton")){
					hotel1.AddTile(new Tile(Adj[i].getRow(),Adj[i].getColumn(),label,1));
					board.AddTile(new Tile(Adj[i].getRow(),Adj[i].getColumn(),label,1));
				}

			}
			board.AddTile(new Tile(row,col,label,1));
			hotel1.AddTile(new Tile(row,col,label,1));
			Hotel hotel2 = board.getHotelByName(Adj1);
			for(Tile t : hotel2.getTiles()){
				if(t!=null){
					t.setStatus(label);
					hotel1.AddTile(t);
				}
			}
			for (int i=1;i<10;i++){
				for (int j=1; j<13;j++){
					if(board.getStatusOfRowCol(i,j).equals(Adj1))
						board.setStatusOfRowCol(i,j,Acq);
				}
			}
			GiveAwayBonus(state,hotel2);
			sellStocks(state,hotel2);
			board.RemoveHotel(hotel2);
			hotel1.setSize(hotel1.getTiles().size());
			state.setBoard(board);
			cPlayer.removeTileByRowCol(row, col);
			return state;	

		}
		if(hotelCount == 3){
			for(int i=0;i<4;i++){
				if(BoardManager.isHotel(Adj[i].getStatus())){
					if(Adj1 == null){
						Adj1 = Adj[i].getStatus();
						continue;
					}
					if(Adj2 == null){
						if(!Adj[i].getStatus().equals(Adj1)){
							Adj2 = Adj[i].getStatus();
						}
						continue;
					}
					if(!Adj[i].getStatus().equals(Adj1) && !Adj[i].getStatus().equals(Adj2)){
						Adj3 = Adj[i].getStatus();
						break;
					}
				}

			}
			if(Adj1.equals(label)){
				Acq = Adj1;
				Adj1 =Adj2;
				Adj2 = Adj3;
			}
			else if(Adj2.equals(label)){
				Acq = Adj2;
				Adj2 = Adj3;
			}
			else if(Adj3.equals(label)){
				Acq = Adj3;
			}
			else{
				throw new GameException("Merging is not possible as "+Acq+" is not adjacent to the given tile.");
			}
			if(board.getHotelByName(Adj1).getSize() >= GameParameters.safeHotelSize){
				throw new GameException(""+Acq+" cannot acquire "+Adj1+". "+Adj1+" is safe.\" />");
			}
			if(board.getHotelByName(Adj2).getSize() >= GameParameters.safeHotelSize){
				throw new GameException(""+Acq+" cannot acquire "+Adj2+". "+Adj2+" is safe.\" />");
			}
			if(board.getHotelByName(Acq).getSize() < board.getHotelByName(Adj1).getSize()){
				throw new GameException(""+Acq+" cannot acquire "+Adj1+". "+Adj1+" is larger than "+Acq);
			}
			if(board.getHotelByName(Acq).getSize() < board.getHotelByName(Adj2).getSize()){
				throw new GameException(""+Acq+" cannot acquire "+Adj2+". "+Adj2+" is larger than "+Acq);
			}
			Hotel hotel1 = board.getHotelByName(label);
			for(int i=0;i<4;i++){
				if(Adj[i].getStatus().equals("singleton")){
					hotel1.AddTile(new Tile(Adj[i].getRow(),Adj[i].getColumn(),label,1));
					hotel1.AddTile(new Tile(Adj[i].getRow(),Adj[i].getColumn(),label,1));
				}

			}

			board.AddTile(new Tile(row,col,label,1));
			hotel1.AddTile(new Tile(row,col,label,1));
			Hotel hotel2 = board.getHotelByName(Adj1);
			Hotel hotel3 = board.getHotelByName(Adj2);
			for(Tile t : hotel2.getTiles()){
				if(t!=null){
					t.setStatus(label);
					hotel1.AddTile(t);
				}
			}
			for(Tile t : hotel3.getTiles()){
				if(t!=null){
					t.setStatus(label);
					hotel1.AddTile(t);
				}
			}
			for (int i=1;i<10;i++){
				for (int j=1; j<13;j++){
					if(board.getStatusOfRowCol(i,j).equals(Adj1) ||
							board.getStatusOfRowCol(i,j).equals(Adj2))
						board.setStatusOfRowCol(i,j,Acq);
				}
			}
			GiveAwayBonus(state,hotel2);
			GiveAwayBonus(state,hotel3);
			sellStocks(state,hotel2);
			sellStocks(state,hotel3);
			board.RemoveHotel(hotel2);
			board.RemoveHotel(hotel3);
			hotel1.setSize(hotel1.getTiles().size());
			state.setBoard(board);
			cPlayer.removeTileByRowCol(row, col);
			return state;	


		}
		if(hotelCount == 4){
			if(Adj[0].getStatus().equals(label)){
				Acq = Adj[0].getStatus();
				Adj1 = Adj[1].getStatus();
				Adj2 = Adj[2].getStatus();
				Adj3 = Adj[3].getStatus();
			}
			else if(Adj[1].getStatus().equals(label)){
				Adj1 = Adj[0].getStatus();
				Acq = Adj[1].getStatus();
				Adj2 = Adj[2].getStatus();
				Adj3 = Adj[3].getStatus();
			}
			else if(Adj[2].getStatus().equals(label)){
				Adj1 = Adj[0].getStatus();
				Adj2 = Adj[1].getStatus();
				Acq = Adj[2].getStatus();
				Adj3 = Adj[3].getStatus();
			}
			else if(Adj[3].getStatus().equals(label)){
				Adj1 = Adj[0].getStatus();
				Adj2 = Adj[1].getStatus();
				Adj3 = Adj[2].getStatus();
				Acq = Adj[3].getStatus();
			}
			else{
				throw new GameException("Merging is not possible as "
						+Acq+" is not adjacent to the given tile.\" />");
			}
			if(board.getHotelByName(Adj1).getSize() >= GameParameters.safeHotelSize){
				throw new GameException(""+Acq+" cannot acquire "+Adj1+". "+Adj1+" is safe.");
			}
			if(board.getHotelByName(Adj2).getSize() >= GameParameters.safeHotelSize){
				throw new GameException(""+Acq+" cannot acquire "+Adj2+". "+Adj2+" is safe.");
			}
			if(board.getHotelByName(Adj3).getSize() >= GameParameters.safeHotelSize){
				throw new GameException(""+Acq+" cannot acquire "+Adj3+". "+Adj3+" is safe.");
			}
			if(board.getHotelByName(Acq).getSize() < board.getHotelByName(Adj1).getSize()){
				throw new GameException(""+Acq+" cannot acquire "+Adj1+". "+Adj1+" is larger than "+Acq);
			}
			if(board.getHotelByName(Acq).getSize() < board.getHotelByName(Adj2).getSize()){
				throw new GameException(""+Acq+" cannot acquire "+Adj2+". "+Adj2+" is larger than "+Acq);
			}
			if(board.getHotelByName(Acq).getSize() < board.getHotelByName(Adj3).getSize()){
				throw new GameException(""+Acq+" cannot acquire "+Adj3+". "+Adj3+" is larger than "+Acq);
			}
			Hotel hotel1 = board.getHotelByName(label);
			for(int i=0;i<4;i++){
				if(Adj[i].getStatus().equals("singleton")){
					hotel1.AddTile(new Tile(Adj[i].getRow(),Adj[i].getColumn(),label,1));
					board.AddTile(new Tile(Adj[i].getRow(),Adj[i].getColumn(),label,1));

				}

			}
			board.AddTile(new Tile(row,col,label,1));
			hotel1.AddTile(new Tile(row,col,label,1));
			Hotel hotel2 = board.getHotelByName(Adj1);
			Hotel hotel3 = board.getHotelByName(Adj2);
			Hotel hotel4 = board.getHotelByName(Adj3);
			for(Tile t : hotel2.getTiles()){
				if(t!=null){
					t.setStatus(label);
					hotel1.AddTile(t);
				}
			}
			for(Tile t : hotel3.getTiles()){
				if(t!=null){
					t.setStatus(label);
					hotel1.AddTile(t);
				}
			}
			for(Tile t : hotel4.getTiles()){
				if(t!=null){
					t.setStatus(label);
					hotel1.AddTile(t);
				}
			}
			for (int i=1;i<10;i++){
				for (int j=1; j<13;j++){
					if(board.getStatusOfRowCol(i,j).equals(Adj1) ||
							board.getStatusOfRowCol(i,j).equals(Adj2)||
							board.getStatusOfRowCol(i,j).equals(Adj3))
						board.setStatusOfRowCol(i,j,Acq);
				}
			}
			GiveAwayBonus(state,hotel2);
			GiveAwayBonus(state,hotel3);
			GiveAwayBonus(state,hotel4);
			sellStocks(state,hotel2);
			sellStocks(state,hotel3);
			sellStocks(state,hotel4);
			board.RemoveHotel(hotel2);
			board.RemoveHotel(hotel3);
			board.RemoveHotel(hotel4);
			hotel1.setSize(hotel1.getTiles().size());
			state.setBoard(board);
			cPlayer.removeTileByRowCol(row, col);
			return state;	
		}
		throw new GameException("Impossible Scenario\" />");
	}

	public static void GiveAwayBonus(State state, Hotel hotel) throws GameException{
		ArrayList<Player> MajHolder = new ArrayList<Player>(),MinHolder = new ArrayList<Player>();
		int MajCount = 0,MinCount = 0;

		for (Player p : state.getPlayers()){
			for (Share s : p.getShares()){
				if (s.getLabel().equals(hotel.getName())){
					if(s.getCount()!=0 && s.getCount()>=MajCount){
						MajCount = s.getCount();
					}
				}
			}
		}
		for (Player p : state.getPlayers()){
			for (Share s : p.getShares()){
				if (s.getLabel().equals(hotel.getName())){
					if(s.getCount() == MajCount){
						MajHolder.add(p);
					}
				}
			}
		}
				
		if(MajHolder.size()>1){
			for (Player p : MajHolder){
				p.setCash(p.getCash() + ((hotel.getBonus1()+hotel.getBonus2())/MajHolder.size()));
			}
		}
		else if(MajHolder.size()>0){
			for (Player p : MajHolder){
				p.setCash(p.getCash() + (hotel.getBonus1()/MajHolder.size()));
			}

			for (Player p : state.getPlayers()){
				for (Share s : p.getShares()){
					if (s.getLabel().equals(hotel.getName())){
						if(s.getCount()!=0 && s.getCount()<MajCount && s.getCount() >= MinCount){
							MinCount = s.getCount();
						}
					}
				}
			}
			for (Player p : state.getPlayers()){
				for (Share s : p.getShares()){
					if (s.getLabel().equals(hotel.getName())){
						if(s.getCount()==MinCount){
							MinHolder.add(p);
						}
					}
				}
			}
			if(MinHolder.size()>0){

				for (Player p : MinHolder){
					p.setCash(p.getCash() + (hotel.getBonus2()/MinHolder.size()));
				}

			}
			else{
				for (Player p : MajHolder){
					p.setCash(p.getCash() + (hotel.getBonus2()/MajHolder.size()));
				}
			}

		}
	}
	public static void sellStocks(State state, Hotel hotel) throws GameException{

		for (Player p : state.getPlayers())
			for (Share s : p.getShares())
				if (s.getLabel().equals(hotel.getName()))
					p.setCash(p.getCash() + (hotel.getStockCost() * s.getCount()));
		
		for (int i=0;i<state.getPlayers().size();i++)
			if(state.getPlayers().get(i).hasShareByLabel(hotel.getName()))
				state.getPlayers().get(i).getShares().remove(state.getPlayers().get(i).getShareByLabel(hotel.getName()));
	}
	public static Tile[] getAdjacentTiles(Board board,int rowIndex,int colIndex){
		Tile[] Adj = new Tile[4];
		Adj[0] = board.getTileByRowCol(rowIndex-1,colIndex);
		Adj[1] = board.getTileByRowCol(rowIndex,colIndex-1);
		Adj[2] = board.getTileByRowCol(rowIndex+1,colIndex);
		Adj[3] = board.getTileByRowCol(rowIndex,colIndex+1);
		return Adj;
	}
}
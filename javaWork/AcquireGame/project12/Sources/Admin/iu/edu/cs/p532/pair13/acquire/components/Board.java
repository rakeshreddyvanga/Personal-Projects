package iu.edu.cs.p532.pair13.acquire.components;
import iu.edu.cs.p532.pair13.acquire.admin.GameParameters;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;


import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Board implements GameParameters {

	private ArrayList<Hotel> hotels;
	private Tile[][] tileArray = new Tile[ROWS + 2][COLS + 2];

	public Board() throws GameException 
	{
		this.hotels = new ArrayList<Hotel>();
		for (int i=0;i < ROWS + 2;i++){
			for (int j=0; j < COLS + 2;j++){
				tileArray[i][j] = new Tile(i,j,"free",0);
			}
		}
	}
	public Board(Board b){
		this.hotels = new ArrayList<Hotel>();
		for(Hotel h : b.getHotels())
			this.hotels.add(new Hotel(h));
		for (int i=0;i < ROWS + 2;i++){
			for (int j=0; j < COLS + 2;j++){
				tileArray[i][j] = new Tile(b.getTileByRowCol(i,j));
			}
		}
	}
	public Board(ArrayList<Hotel> hotels, Tile[][] tileArray) {
		super();
		this.hotels = hotels;
		this.tileArray = tileArray;
	}
	public ArrayList<Hotel> getHotels() {
		return hotels;
	}
	public Tile[][] getTileArray() {
		return tileArray;
	}
	public void setTileArray(Tile[][] tileArray) {
		this.tileArray = tileArray;
	}
	
	public void setHotels(ArrayList<Hotel> hotels) {
		this.hotels = hotels;
	}
	public String getStatusOfRowCol(int Row,int Col){
		return tileArray[Row][Col].getStatus();
	}
	
	public void setStatusOfRowCol(int Row,int Col,String status) throws GameException{
		tileArray[Row][Col].setStatus(status);
	}
	public void setAllocatedRowCol(int Row,int Col) throws GameException{
		tileArray[Row][Col].setAllocated(1);
	}
	public int getAllocatedRowCol(int Row,int Col){
		return tileArray[Row][Col].getAllocated();
	}
	public Tile getTileByRowCol(int Row,int Col){
		return tileArray[Row][Col];
	}
	public Hotel getHotelByName(String name){
		for(Hotel h : hotels)
			if(h.getName().equals(name))
				return h;
		return null;
	}
	public boolean hasHotelByName(String name){
		for(Hotel h : hotels)
			if(h.getName().equals(name))
				return true;
		return false;
	}
	public void AddTile(Tile tile)throws GameException{
		
		if(tileArray[rows.inverse().get(tile.getRow())][tile.getColumn()].getStatus().equals("free")){
			tileArray[rows.inverse().get(tile.getRow())][tile.getColumn()].setStatus(tile.getStatus());
			tileArray[rows.inverse().get(tile.getRow())][tile.getColumn()].setAllocated(tile.getAllocated());
		}
		else if(tileArray[rows.inverse().get(tile.getRow())][tile.getColumn()].getStatus().equals("singleton") &&
				!tile.getStatus().equals("singleton")){
			tileArray[rows.inverse().get(tile.getRow())][tile.getColumn()].setStatus(tile.getStatus());
			tileArray[rows.inverse().get(tile.getRow())][tile.getColumn()].setAllocated(tile.getAllocated());
		}
		else
			throw new GameException("Tile "+tile.getRow()+tile.getColumn()+" is already occupied");		
	}
	public boolean equals(Board board)
	{
		int hotelCompare = 0;
		for(Hotel firstHotel : board.getHotels())
			for(Hotel secondHotel : this.hotels)
				if(firstHotel.equals(secondHotel))
					hotelCompare++;
		if(hotelCompare == this.hotels.size() && hotelCompare == board.getHotels().size())
		{
			for (int i=1;i<=ROWS;i++){
				for (int j=1; j<=COLS;j++){
					if(!tileArray[i][j].equals(board.getTileByRowCol(i,j))){
						//System.out.println("failed in board comp1");
						return false;
					}
						
				}
			}
			return true;

		}
		else{
			//System.out.println("failed in board comp");
			return false;
		}
	}
	public void AddPlayerTile(Tile tile) throws GameException{

		if(tileArray[rows.inverse().get(tile.getRow())][tile.getColumn()].getStatus().equals("free")){
			if(tileArray[rows.inverse().get(tile.getRow())][tile.getColumn()].getAllocated() == 0)
				tileArray[rows.inverse().get(tile.getRow())][tile.getColumn()].setAllocated(tile.getAllocated());
			else
				throw new GameException("Tile "+tile.getRow()+tile.getColumn()+" is already allocated to a player");
		}
		/*else if(tileArray[tile.getRow()-64][tile.getColumn()].getStatus().equals("singleton") &&
				!tile.getStatus().equals("singleton")){
			if(tileArray[tile.getRow()-64][tile.getColumn()].getAllocated() == 0)
				tileArray[tile.getRow()-64][tile.getColumn()].setAllocated(tile.getAllocated());
			else
				throw new GameException("Tile "+tile.getRow()+tile.getColumn()+" is already allocated to a player");

		}*/
		else
			throw new GameException("Tile "+tile.getRow()+tile.getColumn()+" is already occupied");		
	}
	public void AddHotel(Hotel hotel)throws GameException{
		if(getHotelByName(hotel.getName()) == null){
			hotel.setSize(hotel.getTiles().size());
			hotels.add(hotel);
		}
		else{
			throw new GameException("Hotel "+hotel.getName()+" already exists on board");
		}
	}
	public ArrayList<String> getRemainingHotels(){
		ArrayList<String> remHotels = new ArrayList<String>();
		for(String s : ListOfHotels)
			if(!hasHotelByName(s))
				remHotels.add(s);
		return remHotels;

	}
	public void RemoveHotel(Hotel hotel)throws GameException{
		if(getHotelByName(hotel.getName()) == null){
			throw new GameException("Hotel "+hotel.getName()+" does not exist on board");
		}
		else{
			hotels.remove(hotel);
		}
	}
	public String InspectTile(Tile tile) throws GameException{
		int rowIndex = rows.inverse().get(tile.getRow());
		int colIndex = tile.getColumn();
		int freeCount = 0, hotelCount = 0;
		String Adj1=null,Adj2 = null,Adj3 = null;
		String[] Adj = new String[4];
		//return "occupied" if tile is already occupied
		if (!this.tileArray[rowIndex][colIndex].getStatus().equals("free")){	
			return "occupied";
		}
		Adj = getAdjacentTiles(rowIndex,colIndex);
		ArrayList<String> procHotels = new ArrayList<String>();
		int procCount = 0;
		for(int i=0;i<4;i++){
			if(Adj[i].equals("free"))
				freeCount++;
			else if(Adj[i].equals("singleton"));
			else{
				for(String s : procHotels){
					if(s!=null && s.equals(Adj[i])){
						procCount++;
					}					
				}
				if (procCount > 0){
					procCount = 0;
				}
				else{
					hotelCount++;
					procHotels.add(Adj[i]);
				}
			}
		}
		//return singleton in singleton case
		if(freeCount == 4){
			return "singleton";
		}
		//return founding in founding case
		if(hotelCount == 0){
			return "founding";
		}
		//return growing in growing case
		if(hotelCount == 1){
			for(int i=0;i<4;i++){
				if(isHotel(Adj[i]))
					return "growing";
			}
		}
		if(hotelCount == 2){
			for(int i=0;i<4;i++){
				if(isHotel(Adj[i])){
					if(Adj1 == null){
						Adj1 = Adj[i];
						continue;
					}
					if(!Adj[i].equals(Adj1)){
						Adj2 = Adj[i];
						break;
					}
				}
			}
			Adj[0] = Adj1;
			Adj[1] = Adj2;
			return sortAdjacentTiles(Adj,2);
		}
		if(hotelCount == 3){
			for(int i=0;i<4;i++){
				if(isHotel(Adj[i])){
					if(Adj1 == null){
						Adj1 = Adj[i];
						continue;
					}
					if(Adj2 == null){
						if(!Adj[i].equals(Adj1)){
							Adj2 = Adj[i];
						}
						continue;
					}
					if(!Adj[i].equals(Adj1) && !Adj[i].equals(Adj2)){
						Adj3 = Adj[i];
						break;
					}
				}
			}
			Adj[0] = Adj1;
			Adj[1] = Adj2;
			Adj[2] = Adj3;
			return sortAdjacentTiles(Adj,3);
		}
		if(hotelCount == 4)
			return sortAdjacentTiles(Adj,4);
		
		throw new GameException("Impossible Situation in Inspect Tile");
	}
	public static boolean isHotel (String str) throws GameException{
		if(str != null){
			for(String s : ListOfHotels){
				if(s.equals(str))
					return true;
			}
		}
		return false;
	}
	private String[] getAdjacentTiles(int rowIndex,int colIndex){
		String[] Adj = new String[4];
		Adj[0] = this.getTileByRowCol(rowIndex-1,colIndex).getStatus();
		Adj[1] = this.getTileByRowCol(rowIndex,colIndex-1).getStatus();
		Adj[2] = this.getTileByRowCol(rowIndex+1,colIndex).getStatus();
		Adj[3] = this.getTileByRowCol(rowIndex,colIndex+1).getStatus();
		return Adj;
	}
	private String sortAdjacentTiles(String[] Adj,int n){
		String temp;
		int safeCount =0;
		for(int i = 0;i<n;i++){
			if(getHotelByName(Adj[i]).getSize()>= safeHotelSize)
				safeCount++;
		}
		if(safeCount>=2)
			return "safe";
		for(int i = 0;i<n;i++){
			for(int j = i+1;j<n;j++){
				if (getHotelByName(Adj[j]).getSize() > getHotelByName(Adj[i]).getSize()){
					temp = Adj[j];
					Adj[j] = Adj[i];
					Adj[i] = temp;
				}
				if (getHotelByName(Adj[j]).getSize() == getHotelByName(Adj[i]).getSize()){
					if(Adj[j].compareTo(Adj[i])<0){
						temp = Adj[j];
						Adj[j] = Adj[i];
						Adj[i] = temp;
					}
				}
			}
		}

		return Adj[0];
	}
	public void printBoard(){
		for (int i=1;i<=ROWS;i++){
			for (int j=1; j<=COLS;j++){
				if(tileArray[i][j].getStatus().equals("free")|| tileArray[i][j].getStatus().equals("TOWER"))
					System.out.print(tileArray[i][j].getStatus()+ " " +tileArray[i][j].getAllocated() + "\t\t\t");
				else
					System.out.print(tileArray[i][j].getStatus()+ " " +tileArray[i][j].getAllocated() + "\t\t");
			}
			System.out.println();
		}
	}
	public String constructBoardElement(){
		String newline = System.getProperty("line.separator");
		String ret = "<board>";
		for (int i=1;i<=ROWS;i++){
			for (int j=1; j<=COLS;j++){
				if (!tileArray[i][j].getStatus().equals("free"))
					ret = ret  + newline + "<tile column=\""+tileArray[i][j].getColumn()+"\" row=\""+tileArray[i][j].getRow()+"\" />";
			}
		}
		for(Hotel h : hotels){
			ret = ret + newline + "<hotel name=\""+hotelNameConv.get(h.getName())+"\">";
			for(Tile ht : h.getTiles()){
				ret = ret  + newline + "<tile column=\""+ht.getColumn()+"\" row=\""+ht.getRow()+"\" />";
			}
			ret = ret + newline + "</hotel>";
		}
		ret = ret + newline + "</board>";
		return ret;
	}
	public Element constructBoardElement(Document parentDocument,Element boardTag){
		for (int i=1;i<=ROWS;i++){ //Adding the placed tiles on the board
			for (int j=1; j<=COLS;j++){
				if (!tileArray[i][j].getStatus().equals("free")){
					Element boardTileTag = parentDocument.createElement("tile");
					boardTag.appendChild(boardTileTag);
					boardTileTag.setAttribute("column",String.valueOf(tileArray[i][j].getColumn()));
					boardTileTag.setAttribute("row", String.valueOf(tileArray[i][j].getRow()));
					}
			}
		}
		//Adding hotels and its respective tile list
		
				for(Hotel h : hotels){
					Element boardHotelTag = parentDocument.createElement("hotel");
					boardTag.appendChild(boardHotelTag);
					boardHotelTag.setAttribute("name", hotelNameConv.get(h.getName()));
					for(Tile ht : h.getTiles()){
						Element hotelTileTag = parentDocument.createElement("tile");
						boardHotelTag.appendChild(hotelTileTag);
						hotelTileTag.setAttribute("column",String.valueOf(ht.getColumn()));
						hotelTileTag.setAttribute("row",String.valueOf(ht.getRow()));				
					}
				}
				
				return boardTag;
				
			}
}
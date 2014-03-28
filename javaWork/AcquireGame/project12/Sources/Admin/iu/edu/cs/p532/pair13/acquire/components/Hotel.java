package iu.edu.cs.p532.pair13.acquire.components;


import iu.edu.cs.p532.pair13.acquire.admin.GameParameters;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;


import java.util.ArrayList;


public class Hotel implements GameParameters{

	private String name;
	private int size;
	private ArrayList<Tile> tiles;
	private int StockCost;
	private int bonus1;
	private int bonus2;
	
	public Hotel(String name,ArrayList<Tile> tiles) throws GameException 
	{
		this.setTiles(tiles);
		this.setName(name);
		this.setSize(tiles.size()) ;
	}
	public Hotel(String name) throws GameException 
	{
		this.tiles = new ArrayList<Tile>();
		this.setName(name);
	}
	public Hotel (Hotel h){
		this.name = h.getName();
		this.size = h.getSize();
		this.tiles = new ArrayList<Tile>();
		for(Tile t : h.getTiles())
			this.tiles.add(new Tile(t));
		this.StockCost = h.getStockCost();
		this.bonus1 = h.getBonus1();
		this.bonus2 = h.getBonus2();
	}
	public boolean equals(Hotel hotel){
		if (this.name.equals(hotel.name) && this.size == hotel.size){
			for(Tile t : this.tiles){
				if(!hotel.hasTile(t)){
					return false;
				}
			}
			return true;
		}
		//System.out.println("failed in hotel comp");
		return false;
	}
	public String getName() {
		return name;
	}

	public boolean setName(String name) throws GameException {
		if(Board.isHotel(name)){
			this.name = name;
			return true;
		}
		else
			throw new GameException("Invalid hotel name");
	}
	public int getSize() {
		return size;
	}

	public boolean setSize(int size) throws GameException {
		if(size >= 2 && size <= ROWS*COLS){
			this.size = size;
			if(size<2){
				StockCost = 0;
				bonus1 = 0;
				bonus2 = 0;
			}
			if(size==2){
				if(name.equals(WO)||name.equals(SA)){
					StockCost = 200;
					bonus1 = 2000;
					bonus2 = 1000;
				}
				else if(name.equals(FE)||name.equals(IM)||name.equals(AM)){
					StockCost = 300;
					bonus1 = 3000;
					bonus2 = 1500;

				}
				else if(name.equals(CO)||name.equals(TO)){
					StockCost = 400;
					bonus1 = 4000;
					bonus2 = 2000;
				}
			}
			else if(size==3){
				if(name.equals(WO)||name.equals(SA)){
					StockCost = 300;
					bonus1 = 3000;
					bonus2 = 1500;
				}
				else if(name.equals(FE)||name.equals(IM)||name.equals(AM)){
					StockCost = 400;
					bonus1 = 4000;
					bonus2 = 2000;
				}
				else if(name.equals(CO)||name.equals(TO)){
					StockCost = 500;
					bonus1 = 5000;
					bonus2 = 2500;
				}
			}
			else if(size==4){
				if(name.equals(WO)||name.equals(SA)){
					StockCost = 400;
					bonus1 = 4000;
					bonus2 = 2000;
				}
				else if(name.equals(FE)||name.equals(IM)||name.equals(AM)){
					StockCost = 500;
					bonus1 = 5000;
					bonus2 = 2500;
				}
				else if(name.equals(CO)||name.equals(TO)){
					StockCost = 600;
					bonus1 = 6000;
					bonus2 = 3000;
				}
			}
			else if(size==5){
				if(name.equals(WO)||name.equals(SA)){
					StockCost = 500;
					bonus1 = 5000;
					bonus2 = 2500;
				}
				else if(name.equals(FE)||name.equals(IM)||name.equals(AM)){
					StockCost = 600;
					bonus1 = 6000;
					bonus2 = 3000;
				}
				else if(name.equals(CO)||name.equals(TO)){
					StockCost = 700;
					bonus1 = 7000;
					bonus2 = 3500;
				}	
			}

			else if(size>=6&&size<11){
				if(name.equals(WO)||name.equals(SA)){
					StockCost = 700;
					bonus1 = 7000;
					bonus2 = 3500;
				}
				else if(name.equals(FE)||name.equals(IM)||name.equals(AM)){
					StockCost = 800;
					bonus1 = 8000;
					bonus2 = 4000;
				}
				else if(name.equals(CO)||name.equals(TO)){
					StockCost = 900;
					bonus1 = 9000;
					bonus2 = 4500;
				}	
			}
			else if(size>=11&&size<20){
				if(name.equals(WO)||name.equals(SA)){
					StockCost = 800;
					bonus1 = 8000;
					bonus2 = 4000;
				}
				else if(name.equals(FE)||name.equals(IM)||name.equals(AM)){
					StockCost = 900;
					bonus1 = 9000;
					bonus2 = 4500;
				}
				else if(name.equals(CO)||name.equals(TO)){
					StockCost = 1000;
					bonus1 = 10000;
					bonus2 = 5000;
				}	
			}
			else if(size>=21&&size<30){
				if(name.equals(WO)||name.equals(SA)){
					StockCost = 900;
					bonus1 = 9000;
					bonus2 = 4500;
				}
				else if(name.equals(FE)||name.equals(IM)||name.equals(AM)){
					StockCost = 1000;
					bonus1 = 10000;
					bonus2 = 5000;
				}
				else if(name.equals(CO)||name.equals(TO)){
					StockCost = 1100;
					bonus1 = 11000;
					bonus2 = 5500;
				}	
			}
			else if(size>=31&&size<40){
				if(name.equals(WO)||name.equals(SA)){
					StockCost = 1000;
					bonus1 = 10000;
					bonus2 = 5000;

				}
				else if(name.equals(FE)||name.equals(IM)||name.equals(AM)){
					StockCost = 1100;
					bonus1 = 11000;
					bonus2 = 5500;
				}
				else if(name.equals(CO)||name.equals(TO)){
					StockCost = 1200;
					bonus1 = 12000;
					bonus2 = 6000;
				}	
			}
			else if(size>=41){
				if(name.equals(WO)||name.equals(SA)){
					StockCost = 1100;
					bonus1 = 11000;
					bonus2 = 5500;
				}
				else if(name.equals(FE)||name.equals(IM)||name.equals(AM)){
					StockCost = 1200;
					bonus1 = 12000;
					bonus2 = 6000;
				}
				else if(name.equals(CO)||name.equals(TO)){
					StockCost = 1300;
					bonus1 = 13000;
					bonus2 = 6500;
				}	
			}
			return true;
		}
		else
			throw new GameException("Invalid hotel size");
	}
	public int getStockCost() {
		return StockCost;
	}
	public int getBonus1() {
		return bonus1;
	}
	public int getBonus2() {
		return bonus2;
	}
	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public boolean setTiles(ArrayList<Tile> tiles) throws GameException {
		for(Tile t : tiles){
			if(!t.getStatus().equals(this.name) || t.getAllocated() == 0){
				throw new GameException("Tile cannot be added to hotel");
			}
		}
		this.tiles = tiles;
		return true;
	}
	public boolean hasTile(Tile tile)
	{
		for(Tile TIt : tiles)
			if(TIt!=null && TIt.equals(tile))
				return true;
		return false;
	}
	public Tile getTileByRowCol(String row, int col)
	{
		for(Tile t : tiles)
			if(t!=null && t.getRow().equals(row) && t.getColumn() == col)
				return t;
		return null;
	}
	public boolean hasTileByRowCol(String row, int col)
	{
		for(Tile t : tiles)
			if(t!=null && t.getRow().equals(row) && t.getColumn() == col)
				return true;
		return false;
	}
	public boolean AddTile(Tile tile) throws GameException{
		if (!hasTile(tile) && tile.getStatus().equals(this.name) && tile.getAllocated() == 1){
			tiles.add(tile);
			this.size++;
			return true;
		}
		else{
			return false;
		}
	}
}
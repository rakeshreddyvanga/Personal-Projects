package iu.edu.cs.p532.pair13.acquire.components;
import iu.edu.cs.p532.pair13.acquire.admin.GameParameters;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;

import java.util.ArrayList;




public class Player implements GameParameters{
	
	private String Name;
	private int Cash;
	private ArrayList<Share> shares;
	private ArrayList<Tile> tiles;
	private static final int initialCash = 6000;
	
	public Player(Player p){
		this.Name = p.getName();
		this.Cash = p.getCash();
		this.shares = new ArrayList<Share>();
		for(Share s : p.getShares())
			this.shares.add(new Share(s));
		this.tiles = new ArrayList<Tile>();
		for(Tile t : p.getTiles())
			this.tiles.add(new Tile(t));
	}
	public Player(){
		this.Name = null;
		this.Cash = -1;
		this.shares = new ArrayList<Share>();
		this.tiles = new ArrayList<Tile>();
	}
	public Player(String Name){
		this.Name = Name;
		this.Cash = initialCash;
		this.shares = new ArrayList<Share>();
		this.tiles = new ArrayList<Tile>();
	}
	public Player(String Name,int cash){
		this.Name = Name;
		this.Cash = cash;
		this.shares = new ArrayList<Share>();
		this.tiles = new ArrayList<Tile>();
	}
	public Player(String Name,int cash,ArrayList<Share> shares,ArrayList<Tile> tiles){
		this.Name = Name;
		this.Cash = cash;
		this.shares = shares;
		this.tiles = tiles;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getCash() {
		return Cash;
	}
	public void setCash(int cash) {
		Cash = cash;
	}
	public ArrayList<Share> getShares() {
		return shares;
	}
	public void setShares(ArrayList<Share> shares) {
		this.shares = shares;
	}
	public ArrayList<Tile> getTiles() {
		return tiles;
	}
	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
	}
	public Share getShareByLabel(String label){
		for (Share s : shares){
			if (s.getLabel().equals(label)){
				return s;
			}
		}
		return null;
	}
	public boolean hasShareByLabel(String label){
		for (Share s : shares){
			if (s.getLabel().equals(label)){
				return true;
			}
		}
		return false;
	}
	public void AddShare(Share s){
		shares.add(s);
	}
	public void AddTile(Tile tile) throws GameException{
		for(Tile t : tiles){
			if (t.getRow().equals(tile.getRow()) && t.getColumn() == tile.getColumn()){
				throw new GameException("Tile already exists in the players list");
			}
		}
		tiles.add(tile);
	}
	public void removeTileByRowCol(String row,int col)throws GameException{
		for(Tile t : tiles){
			if (t.getRow().equals(row) && t.getColumn() == col){
				tiles.remove(t);
				return;
			}
		}
		throw new GameException("Player does not have the tile "+row+col);
	}
	public void hasTileByRowCol(String row,int col)throws GameException{
		for(Tile t : tiles){
			if (t.getRow().equals(row) && t.getColumn() == col){
				return;
			}
		}
		throw new GameException("Player does not have the tile "+row+col);
	}
	public void printPlayer(){
		System.out.println(Name + " " + Cash);
		for(Share s : shares){
			System.out.println(s.getLabel() + " " + s.getCount());
		}
		for(Tile t : tiles){
			System.out.println(t.getRow() + " " + t.getColumn());
		}
		
	}
	public String constructPlayerElement(){
		String newline = System.getProperty("line.separator");
		String ret = "<player name=\""+Name+"\" cash=\""+Cash+"\">";
		
		for(Share s : shares){
			ret = ret + newline + "<share name=\""+hotelNameConv.get(s.getLabel())+"\" count=\""+s.getCount()+"\" />";
		}
		for(Tile t : tiles){
			ret = ret + newline + "<tile column=\""+t.getColumn()+"\" row=\""+t.getRow()+"\" />";
		}
		ret = ret + newline + "</player>";
		return ret;
	}
	public boolean equals(Player player)
	{
		int shareCompare = 0, tileCompare = 0;
		if(player.getName().equals(this.Name) && player.getCash() == this.Cash)
		{
			//if cash name are same for both the players checking the shares
			for(Share firstShare : player.getShares())
				for(Share secondShare : this.shares)
					if(firstShare.equals(secondShare))
						shareCompare++;
			if(shareCompare == this.shares.size() && shareCompare == player.getShares().size()){
				//if shares are same comparing tiles for the two players
				for(Tile firstTile : player.getTiles())
					for(Tile secondTile : this.tiles)
						if(firstTile.equals(secondTile))
							tileCompare++;

				if(tileCompare == this.tiles.size() && tileCompare == player.getTiles().size())		 
					return true;
				else{
					//System.out.println("failed in player comp1" +tileCompare +" "+this.tiles.size() +" " +player.getTiles().size());
					return false;
				}
					
			}
			else{
				//System.out.println("failed in player comp2");
				return false;
			}
				
		}
		else{
			//System.out.println("failed in player comp3 " + this.Name + " " + player.getName());
			return false;
		}

	}
	
}
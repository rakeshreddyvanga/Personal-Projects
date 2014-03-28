package iu.edu.cs.p532.pair13.acquire.player.playerstrategies;
import iu.edu.cs.p532.pair13.acquire.player.components.Share;
import iu.edu.cs.p532.pair13.acquire.player.components.Tile;
import iu.edu.cs.p532.pair13.acquire.player.constants.GameParameters;
import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;

import java.util.ArrayList;

public class Player implements GameParameters{
	
	private String Name;
	private int Cash;
	private ArrayList<Share> shares;
	private ArrayList<Tile> tiles;
	
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
		this.setName(null);
		this.Cash = -1;
		this.shares = new ArrayList<Share>();
		this.tiles = new ArrayList<Tile>();
	}
	public Player(int cash){
		this.Cash = cash;
		this.shares = new ArrayList<Share>();
		this.tiles = new ArrayList<Tile>();
	}
	public Player(String name, int cash, ArrayList<Share> shares,
			ArrayList<Tile> tiles) {
		Name = name;
		Cash = cash;
		this.shares = shares;
		this.tiles = tiles;
	}
	public Player(int cash,ArrayList<Share> shares,ArrayList<Tile> tiles){
		this.Cash = cash;
		this.shares = shares;
		this.tiles = tiles;
	}
	public Player(int cash,ArrayList<Share> shares){
		this.Cash = cash;
		this.shares = shares;
		this.tiles = new ArrayList<Tile>();
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
			if (t.getRow() == tile.getRow() && t.getColumn() == tile.getColumn()){
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
		System.out.println("Player Cash: " + Cash);
		for(Share s : shares){
			System.out.println(s.getLabel() + " " + s.getCount());
		}
		for(Tile t : tiles){
			System.out.println(t.getRow() + " " + t.getColumn());
		}
		
	}
	public String constructPlayerElement(){
		String newline = System.getProperty("line.separator");
		String ret = "<player cash=\""+Cash+"\">";
		
		for(Share s : shares){
			ret = ret + newline + "<share name=\""+hotelNameConv.get(s.getLabel())+"\" count=\""+s.getCount()+"\" />";
		}
		for(Tile t : tiles){
			ret = ret + newline + "<tile column=\""+t.getColumn()+"\" row=\""+t.getRow()+"\" />";
		}
		ret = ret + newline + "</player>";
		return ret;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	
}
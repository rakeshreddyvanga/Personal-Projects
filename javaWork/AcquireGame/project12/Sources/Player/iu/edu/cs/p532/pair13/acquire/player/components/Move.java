package iu.edu.cs.p532.pair13.acquire.player.components;


import java.util.ArrayList;



public class Move{
	private Tile placeTile;
	private ArrayList<String> buyShares;

	public Move(){
		this.buyShares = new ArrayList<String>();
	}
	public Move(Move m){
		if(m!=null){
			if(m.getPlaceTile()!=null)
				this.placeTile = new Tile(m.placeTile);
			this.buyShares = new ArrayList<String>();
			if(m.getBuyShares()!=null)
				for(String s : m.getBuyShares())
					this.buyShares.add(new String(s));
			else
				this.buyShares = new ArrayList<String>();
		}
	}
	
	public Tile getPlaceTile() {
		return placeTile;
	}
	public void setPlaceTile(Tile placeTile) {
		this.placeTile = placeTile;
	}
	public ArrayList<String> getBuyShares() {
		return buyShares;
	}
	public void setBuyShares(ArrayList<String> buyShares) {
		this.buyShares = buyShares;
	}
}
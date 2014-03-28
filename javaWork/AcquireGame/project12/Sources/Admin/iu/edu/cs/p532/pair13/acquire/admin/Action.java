package iu.edu.cs.p532.pair13.acquire.admin;

import java.util.ArrayList;

import iu.edu.cs.p532.pair13.acquire.components.Tile;

public class Action{
	
	private Tile placeTile;
	private ArrayList<String> buyShares;
	private boolean win;
	
	
	public Action(Tile placeTile, ArrayList<String> buyShares, boolean win) {
		this.placeTile = placeTile;
		this.buyShares = buyShares;
		this.win = win;
	}


	public Tile getPlaceTile() {
		return placeTile;
	}


	public ArrayList<String> getBuyShares() {
		return buyShares;
	}


	public boolean isWin() {
		return win;
	}

}
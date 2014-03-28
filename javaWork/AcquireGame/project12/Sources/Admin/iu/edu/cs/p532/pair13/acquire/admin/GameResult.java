package iu.edu.cs.p532.pair13.acquire.admin;

import java.util.ArrayList;

public class GameResult {

	private ArrayList<String> playerNames;
	private ArrayList<Integer> playerCash;
	
	public GameResult(){
		playerNames = new ArrayList<String>();
		playerCash = new ArrayList<Integer>();
	}
	public ArrayList<String> getPlayerNames() {
		return playerNames;
	}
	public ArrayList<Integer> getPlayerCash() {
		return playerCash;
	}
	public void addResult(String PlayerName,int Cash){
		playerNames.add(PlayerName);
		playerCash.add(Cash);
	}
	
}

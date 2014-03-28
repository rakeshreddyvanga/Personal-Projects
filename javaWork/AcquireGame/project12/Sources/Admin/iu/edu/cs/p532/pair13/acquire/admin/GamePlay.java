package iu.edu.cs.p532.pair13.acquire.admin;

//import iu.edu.cs.p532.pair13.acquire.components.Hotel;
import iu.edu.cs.p532.pair13.acquire.components.Tile;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;

import java.util.ArrayList;

interface GamePlay{

	public State beginGame(String[] Players) throws GameException;
	public State placePlayerTile(State state,Tile t) throws GameException;
	public State buyStock(State state, ArrayList<String> hotelName) throws GameException;
	//public String sellStock(Hotel h, int quantity);
	//public String exchangeStock(Hotel from, Hotel to, int quantity);
	public State endGame(State state) throws GameException;
	public State endTurn(State state) throws GameException;
	public boolean checkGameTermination(State state,Action action);

}
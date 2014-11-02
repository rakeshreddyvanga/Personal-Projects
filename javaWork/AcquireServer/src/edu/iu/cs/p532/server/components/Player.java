package edu.iu.cs.p532.server.components;
/**
 * 
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.iu.cs.p532.server.utilities.ExceptionHandling;
import edu.iu.cs.p532.server.utilities.TileConversions;

/**
 * @author AssAssinator
 * 
 */
public class Player {

	private String playerName;
	private List<String> tileList = new ArrayList<String>();
	private double cash;
	private Map<String, Integer> shares = new HashMap<String, Integer>();

	public Player(String playerName) {
		this.playerName = playerName;
	}

	public void addTileToPlayer(String tileName) throws ExceptionHandling {
		int safeAdd = 0;
		if (tileList.isEmpty()) {

			tileList.add(tileName);

		} else {
			for (String item : tileList) {
				if (item.equals(tileName)) {
					safeAdd = 1;

				}
			}

			if (safeAdd == 0) {
				if(tileList.size() < 7)
				{
					tileList.add(tileName);
				}
				else
				{
					throw new ExceptionHandling("Cannot add 7th tile to the player");
				}
			}

		}
	}

	public boolean tileExistence(String tileName) {
		for (String item : tileList) {
			if (item.equals(tileName)) {
				return true;
			}
		}
		return false;
	}

	public void removeTile(String tileName) {
		tileList.remove(tileName);
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public List<String> getTileList() {
		return tileList;
	}

	public void setTileList(List<String> tileList) {
		this.tileList = tileList;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public Map<String, Integer> getShares() {
		return shares;
	}
	
	
	

	public void setShares(int shareCount, String hotelName) throws ExceptionHandling {
		if (shares.containsKey(hotelName))
		{
			if((shares.get(hotelName) + shareCount) > 25)
			{
				throw new ExceptionHandling("Exceding the maximum limit of shares of "+hotelName+" to a player ");
			}
			else
			{
				shares.put(hotelName, shares.get(hotelName)+shareCount);
			}
		}
		else
		{
			shares.put(hotelName, shareCount);
		}
	}

	public Element constructPlayerElement(Document stateDocument) {
		
		Element playerTag = stateDocument.createElement("player");
		playerTag.setAttribute("name", playerName);	
		playerTag.setAttribute("cash", String.valueOf(cash));	
		for(String item : tileList)
		{
			Element playerTileTag = stateDocument.createElement("tile");
			playerTag.appendChild(playerTileTag);
			String [] rowColumn = TileConversions.tileNameToIndex(item);
			playerTileTag.setAttribute("column", rowColumn[1]);
			playerTileTag.setAttribute("row", rowColumn[0]);
		}
		return playerTag;
	}
}

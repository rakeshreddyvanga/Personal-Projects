package edu.iu.cs.p532.server.administrators;
/**
 * 
 */


import edu.iu.cs.p532.server.utilities.IConstants;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import edu.iu.cs.p532.server.components.Hotel;
import edu.iu.cs.p532.server.components.Player;
import edu.iu.cs.p532.server.data.DataRepresentation;
import edu.iu.cs.p532.server.utilities.ExceptionHandling;
import edu.iu.cs.p532.server.utilities.TileConversions;

public class Administrator extends BoardManager {

	private List<Player> playerList;	
	private Map<String, Integer> sharesPerHotel;
	public List<DataRepresentation> states ;
	// has only hotels which are found and the no of shares already bought from them
	
	public Administrator() {
		playerList = new ArrayList<Player>();		
		sharesPerHotel = new HashMap<String, Integer>();
		states = new ArrayList<DataRepresentation>();
	}
	
	

	public void createPlayer(String playerName) throws ExceptionHandling {
		int safeAdd = 0;
		if (playerList.isEmpty()) {
			Player playerObj = new Player(playerName);
			playerList.add(playerObj);
			playerObj.setCash(6000.00);
			setPlayerIntialTiles(playerObj);
			
		} else {
			for (Player item : playerList) {
				if (item.getPlayerName().equals(playerName)) {
					safeAdd = 1;
					throw new ExceptionHandling(playerName+ "Player already exists. Please check the setup tag");
					
				}
			}

			if (safeAdd == 0) {
				Player playerObj = new Player(playerName);
				playerList.add(playerObj);
				playerObj.setCash(6000.00);
				setPlayerIntialTiles(playerObj);
			}
		}
	}

	public void initialStateXML() throws ExceptionHandling {
		DataRepresentation state = new DataRepresentation(this,playerList);
		states.add(state);
		System.out.println(state.getOutputXML().toString());		
	}

	
	private boolean setPlayerIntialTiles(Player playerObj) throws ExceptionHandling {
		int noofTiles = 0;
		while (noofTiles < 6) {
			if (playerObj != null) {
				int tileAccepted = 0;
				while (tileAccepted == 0) {
					String tileName = generateRandomTile();
					if(tileName.equals("Empty")){
						noofTiles = 6;
						break;
					}
						
					if (!(checkTileInPlayer(tileName))) {
						playerObj.addTileToPlayer(tileName);
						remTiles.remove(tileName);
						tileAccepted = 1;
						noofTiles++;
					}
				}
			} else {
				throw new ExceptionHandling("<error msg= \"Cannot set the initial tiles to a " +
						"player bacasue player object is null\"/>");
			}
		}
		return true;
	}
	
	public Player getPlayerObj(String playerName) {
		for (Player item : playerList) {
			if (item.getPlayerName().equals(playerName)) {
				return item;
			}
		}
		return null;
	}

	public void buildPlayer(String playerName, double cash) throws ExceptionHandling {
		if (getPlayerObj(playerName) != null) {
			getPlayerObj(playerName).setCash(cash);
		} else {
			throw new ExceptionHandling("<error msg= \"Please set the players and board first\"/>");
			
		}
	}

	public void setPlayerShare(String playerName, int shareCount,
			String hotelName) throws ExceptionHandling {
		if (getPlayerObj(playerName) != null) {
			getPlayerObj(playerName).setShares(shareCount, hotelName);
		} else {
			throw new ExceptionHandling("<error msg= \"Please set the players and board first\"/>");
		}
	}

	public void setPlayerTileList(String playerName, String rowString,
			int columnIndex) throws ExceptionHandling {
		if (getPlayerObj(playerName) != null) {
			getPlayerObj(playerName).addTileToPlayer(
					TileConversions.getTileName(rowString, columnIndex));
		} else {
			throw new ExceptionHandling("<error msg= \"Please set the players and board first\"/>");
		}
	}

	public void placeTile(String rowString, int columnIndex)
			throws  ExceptionHandling {
		
		if(!playerList.get(0).tileExistence(TileConversions.getTileName(rowString, columnIndex)))
			throw new ExceptionHandling("the requested place tile is not from the player list of tiles");
		
		
		String tileType = inspectBoard(rowString, columnIndex);
		if (tileType.equalsIgnoreCase("Singleton")) {
			singletonRequest(rowString, columnIndex);
		} else if (tileType.equalsIgnoreCase("growing")) {
			growingRequest(rowString, columnIndex);
		} else if (tileType.equalsIgnoreCase("merging")) {
			throw new ExceptionHandling("<error msg=\"please provide a hotel name for the request\"/>");
			
		} else if (tileType.equalsIgnoreCase("founding")) {
			throw new ExceptionHandling("<error msg=\"please provide a hotel name for the request\"/>");
		}
		else if(tileType.equalsIgnoreCase("safe"))
			throw new ExceptionHandling("Tile cannot be place it is a safe tile");
		else
			throw new ExceptionHandling("Tile is already oocupied by another hotel");
	}

	private boolean checkTileInPlayer(String tileName) {
		for (Player item : playerList) {
			if (item.tileExistence(tileName)) {
				return true;
			}
		}
		return false;
	}

	private String generateRandomTile() {
		
		if (remTiles.isEmpty()) {
			return "Empty";
		}
		int randomIndex = new Random().nextInt(remTiles.size());
		try {
			String tileName = remTiles.get(randomIndex);
			return tileName;
		} catch (Exception e) {
			return null;
		}
		
		

	}


	public void placeTile(String rowString, int columnIndex, String hotelName) throws ExceptionHandling {
		
		if(!playerList.get(0).tileExistence(TileConversions.getTileName(rowString, columnIndex)))
			throw new ExceptionHandling("the requested place tile is not from the first player's tile list");
		
		
		String tileType = inspectBoard(rowString, columnIndex);
		if (tileType.equalsIgnoreCase("Singleton")) {
			throw new ExceptionHandling("<error msg=\"cannot form a hotel name for the request\"/>");
		} else if (tileType.equalsIgnoreCase("growing")) {
			throw new ExceptionHandling("<error msg=\"cannot form a hotel name for the request\"/>");
		} else if (tileType.equalsIgnoreCase("merging")) {
			mergingRequest(rowString, columnIndex, hotelName);
			giveStockHolderBonuses(); // bonuses give away
			afterMergeShares(acquiredHotelNames); // selling the shares from the player of acquired hotel after the merge
		} else if (tileType.equalsIgnoreCase("founding")) {
			if(hotelList.size() == IConstants.maxHotelsAllowded && 
					sharesPerHotel.size() == IConstants.maxHotelsAllowded && remHotels.size() == 0)
				throw new ExceptionHandling("Already 7 hotels are built. Cannot build more hotels now");
			Player currentPlayerObj = playerList.get(0);
			currentPlayerObj.setShares(1,hotelName);
			sharesPerHotel.put(hotelName, 24);
			remHotels.remove(hotelName);
			foundingRequest(rowString, columnIndex, hotelName);
		}
		else if(tileType.equalsIgnoreCase("safe"))
			throw new ExceptionHandling("Tile cannot be place it is a safe tile");
		else
			throw new ExceptionHandling("Tile is already oocupied by another hotel");
	}
	
	//gives the majority and minority bonuses after ranking them
	private void giveStockHolderBonuses() throws ExceptionHandling
	{
		int majorityShares = 0 , minorityShares = 0;
		List<Player> majorityPlayers = new ArrayList<Player>();
		List<Player> minorityPlayers = new ArrayList<Player>();
		for(int m = 0; m < acquiredHotelNames.size(); m ++) // for each hotel in the merger
		{
			for(Player item : playerList)
			{
				if(item.getShares().containsKey(acquiredHotelNames.get(m)))
				{
					if(majorityShares <= item.getShares().get(acquiredHotelNames.get(m)))
					{
						majorityShares = item.getShares().get(acquiredHotelNames.get(m));
					}
				}
			}
			
			for(Player item : playerList)
			{
				if(item.getShares().containsKey(acquiredHotelNames.get(m)))
				{
					if(majorityShares > item.getShares().get(acquiredHotelNames.get(m))
							&& item.getShares().get(acquiredHotelNames.get(m)) >= minorityShares )
					{
						minorityShares = item.getShares().get(acquiredHotelNames.get(m));
					}
				}
			}
			
			for(Player item : playerList)
			{
				if(item.getShares().containsKey(acquiredHotelNames.get(m)))
				{
					if(majorityShares == item.getShares().get(acquiredHotelNames.get(m)))
					{
						majorityPlayers.add(item);
					}
					else if(minorityShares == item.getShares().get(acquiredHotelNames.get(m)))
					{
						minorityPlayers.add(item);
					}
				}
			}
		
			if(majorityPlayers.size() == 1 && minorityPlayers.size() == 0)
			{
				double bonus = 0;
				// bonus per share
				bonus = getMajorityBonus(acquiredHotelNames.get(m)) + getMinorityBonus(acquiredHotelNames.get(m));
				// bonus for all majority shares
				bonus = bonus * majorityShares;
				
				majorityPlayers.get(0).setCash(majorityPlayers.get(0).getCash() + bonus);
				
				
			}
			else if (majorityPlayers.size() > 1)
			{
				double bonus = 0;
				//bonus per share
				bonus = getMajorityBonus(acquiredHotelNames.get(m)) + getMinorityBonus(acquiredHotelNames.get(m));
				//bonuses divided equally
				bonus = bonus/majorityPlayers.size();
				//bonus for all majority shares
				bonus = bonus * majorityShares;
				
				for(Player item : majorityPlayers)
				{
					item.setCash(item.getCash() + bonus);
				}
				
			}
			else if(majorityPlayers.size() == 1 && minorityPlayers.size() >= 1)
			{
				majorityPlayers.get(0).setCash(majorityPlayers.get(0).getCash() + 
						(majorityShares * getMajorityBonus(acquiredHotelNames.get(m))));
				double bonus = 0;
				//bonus per share
				bonus =  getMinorityBonus(acquiredHotelNames.get(m));
				//bonuses divided equally
				bonus = bonus/minorityPlayers.size();
				//bonus for all majority shares
				bonus = bonus * minorityShares;
				
				for(Player item : minorityPlayers)
				{
					item.setCash(item.getCash() + bonus);
				}				
			}			
		}
	}
	
	//gives the amount to the players in the merging of the defunct hotel
	//this function can be used for giving out the prices at the end of the game
	//take a list of hotels and checks the players who have these hotel names
	// and gives them the cash for selling the hotels stock.
	// checks the size and no of stocks each player has.
	public void afterMergeShares(List<String> mergingHotelNames) throws ExceptionHandling
	{
		// right now implementing only sell shares but can take input from player and handle the case.
		for(int m  = 0 ; m < mergingHotelNames.size() ; m++)
		{
			for(Player item : playerList)
			{
				if(item.getShares().containsKey(mergingHotelNames.get(m)))
				{ 
					//sell cash per share according to the size of the hotel
					double sellCash = getStockPrice(mergingHotelNames.get(m));
					
					//sell cash per all the shares and removing the shares at the same time
					sellCash = sellCash * item.getShares().remove(mergingHotelNames.get(m));
					
					item.setCash(item.getCash() + sellCash); 
					sharesPerHotel.remove(mergingHotelNames.get(m));
				}
			}
		}
	}	

	public void buyShare(String hotelName) throws ExceptionHandling {
		
		
		if(remHotels.contains(hotelName))
		{
			throw new ExceptionHandling("Cannot buy shares of "+ hotelName +" which is not yet found");
		}
		
		if(sharesPerHotel.get(hotelName) == 0)
		{
			throw new ExceptionHandling("shares of "+ hotelName +" are zero cannot buy this hotel");
		}
		else if (sharesPerHotel.get(hotelName) < 0)
		{
			throw new ExceptionHandling("shares of "+ hotelName +" are less than zero cannot buy this hotel");
		}
		
		Player currentPlayerObj = playerList.get(0);
		if (getHotelObject(hotelName) == null) {
			throw new ExceptionHandling("<error> msg = Hotelname " + hotelName
					+ " doesn't exits/>");	
		}
		if (currentPlayerObj.getCash() >= getStockPrice(hotelName)) {		
				currentPlayerObj.setShares(1, hotelName);
				currentPlayerObj.setCash(currentPlayerObj.getCash()
						- getStockPrice(hotelName));
		} else {
			throw new ExceptionHandling("<error> msg=Not enough Cash to buy the "
					+ hotelName + " Share/>");
		}
	}
	
	public int buyShare(String firstHotelName,
			String secondHotelName) throws ExceptionHandling {
		
		if(remHotels.contains(firstHotelName))
		{
			throw new ExceptionHandling("Cannot buy shares of "+ firstHotelName +" which is not yet found");
		}
		if(remHotels.contains(secondHotelName))
		{
			throw new ExceptionHandling("Cannot buy shares of"+ secondHotelName +" which is not yet found");
		}
		
		if(sharesPerHotel.get(firstHotelName) == 0)
		{
			throw new ExceptionHandling("shares of "+ firstHotelName +" are zero cannot buy this hotel");
		}
		else if (sharesPerHotel.get(firstHotelName) < 0)
		{
			throw new ExceptionHandling("shares of "+ firstHotelName +" are less than zero cannot buy this hotel");
		}
		
		if(sharesPerHotel.get(secondHotelName) == 0)
		{
			throw new ExceptionHandling("shares of "+ secondHotelName +" are zero cannot buy this hotel");
		}
		else if (sharesPerHotel.get(secondHotelName) < 0)
		{
			throw new ExceptionHandling("shares of "+ secondHotelName +" are less than zero cannot buy this hotel");
		}
		
		
		Player currentPlayerObj = playerList.get(0);
		if (getHotelObject(firstHotelName) == null) {
			throw new ExceptionHandling("<error> msg = Hotelname " + firstHotelName
					+ " doesn't exits/>");
		}

		if (currentPlayerObj.getCash() >= getStockPrice(firstHotelName)) { // buying first hotel		
				currentPlayerObj.setShares(1, firstHotelName);
				currentPlayerObj.setCash(currentPlayerObj.getCash()
						- getStockPrice(firstHotelName));
		} else {
			throw new ExceptionHandling("<error> msg=Not enough Cash to buy the "
					+ firstHotelName + " Share/>");
			
		}

		if (getHotelObject(secondHotelName) == null) {
			throw new ExceptionHandling("<error> msg = Hotelname " + secondHotelName
					+ " doesn't exits/>");
			
		}

		if (currentPlayerObj.getCash() >= getStockPrice(secondHotelName)) {
			
			currentPlayerObj.setShares(1, secondHotelName);
			currentPlayerObj.setCash(currentPlayerObj.getCash()
					- getStockPrice(secondHotelName));
			
		} else {
			throw new ExceptionHandling("<error> msg=Not enough Cash to buy the "
					+ secondHotelName + " Share/>");
			
		}

		sharesPerHotel.put(firstHotelName,
				sharesPerHotel.get(firstHotelName) - 1);
		sharesPerHotel.put(secondHotelName,
				sharesPerHotel.get(secondHotelName) - 1);

		return 1;
	
	}

	public int buyShare(String firstHotelName,
			String secondHotelName, String thirdHotelName) throws ExceptionHandling {
		
		if(remHotels.contains(firstHotelName))
		{
			throw new ExceptionHandling("Cannot buy shares of "+ firstHotelName +" which is not yet found");
		}
		if(remHotels.contains(secondHotelName))
		{
			throw new ExceptionHandling("Cannot buy shares of"+ secondHotelName +" which is not yet found");
		}
		if(remHotels.contains(thirdHotelName))
		{
			throw new ExceptionHandling("Cannot buy shares of"+ thirdHotelName +" which is not yet found");
		}
		
		if(sharesPerHotel.get(firstHotelName) == 0)
		{
			throw new ExceptionHandling("shares of "+ firstHotelName +" are zero cannot buy this hotel");
		}
		else if (sharesPerHotel.get(firstHotelName) < 0)
		{
			throw new ExceptionHandling("shares of "+ firstHotelName +" are less than zero cannot buy this hotel");
		}
		
		if(sharesPerHotel.get(secondHotelName) == 0)
		{
			throw new ExceptionHandling("shares of "+ secondHotelName +" are zero cannot buy this hotel");
		}
		else if (sharesPerHotel.get(secondHotelName) < 0)
		{
			throw new ExceptionHandling("shares of "+ secondHotelName +" are less than zero cannot buy this hotel");
		}
		
		if(sharesPerHotel.get(thirdHotelName) == 0)
		{
			throw new ExceptionHandling("shares of "+ thirdHotelName +" are zero cannot buy this hotel");
		}
		else if (sharesPerHotel.get(thirdHotelName) < 0)
		{
			throw new ExceptionHandling("shares of "+ thirdHotelName +" are less than zero cannot buy this hotel");
		}
		
		
		
		Player currentPlayerObj = playerList.get(0);
		if (getHotelObject(firstHotelName) == null) {
			throw new ExceptionHandling("<error> msg = Hotelname " + firstHotelName
					+ " doesn't exits/>");
			
		}

		if (currentPlayerObj.getCash() >= getStockPrice(firstHotelName)) {
			
			currentPlayerObj.setShares(1, firstHotelName);
			currentPlayerObj.setCash(currentPlayerObj.getCash()
					- getStockPrice(firstHotelName));
		} else {
			throw new ExceptionHandling("<error> msg=Not enough Cash to buy the "
					+ firstHotelName + " Share/>");
			
		}

		if (getHotelObject(secondHotelName) == null) {
			throw new ExceptionHandling("<error> msg = Hotelname " + secondHotelName
					+ " doesn't exits/>");
			
		}

		if (currentPlayerObj.getCash() >= getStockPrice(secondHotelName)) {
			
			currentPlayerObj.setShares(1, secondHotelName);
			currentPlayerObj.setCash(currentPlayerObj.getCash()
					- getStockPrice(secondHotelName));
			
		} else {
			throw new ExceptionHandling("<error> msg=Not enough Cash to buy the "
					+ secondHotelName + " Share/>");
		
		}

		if (getHotelObject(thirdHotelName) == null) {
			System.out.println("<error> msg = Hotelname " + thirdHotelName
					+ " doesn't exits/>");
			return -1;
		}

		if (currentPlayerObj.getCash() >= getStockPrice(thirdHotelName)) {
			
			currentPlayerObj.setShares(1, thirdHotelName);
			currentPlayerObj.setCash(currentPlayerObj.getCash()
					- getStockPrice(thirdHotelName));
			
		} else {
			throw new ExceptionHandling("<error> msg=Not enough Cash to buy the "
					+ thirdHotelName + " Share/>");
			
		}

		try {
			sharesPerHotel.put(firstHotelName,
					sharesPerHotel.get(firstHotelName) - 1);
			sharesPerHotel.put(secondHotelName,
					sharesPerHotel.get(secondHotelName) - 1);
			sharesPerHotel.put(thirdHotelName,
					sharesPerHotel.get(thirdHotelName) - 1);
		} catch (Exception e) {
			return -3;
		}
		return 1;
	}
	
	
	
	public double getStockPrice(String hotelName) throws ExceptionHandling {
		Hotel hotelObj = getHotelObject(hotelName);
		int noofTiles = hotelObj.getTileListSize();
		if (hotelName.equals("Worldwide") || hotelName.equals("Sackson")) {
			if (noofTiles == 2) {
				return 200.00;
			} else if (noofTiles == 3) {
				return 300.00;
			} else if (noofTiles == 4) {
				return 400.00;
			} else if (noofTiles == 5) {
				return 500.00;
			} else if (noofTiles >= 6 && noofTiles < 11) {
				return 600.00;
			} else if (noofTiles >= 11 && noofTiles < 21) {
				return 700.00;
			} else if (noofTiles >= 21 && noofTiles < 31) {
				return 800.00;
			} else if (noofTiles >= 31 && noofTiles < 41) {
				return 900.00;
			} else if (noofTiles == 41) {
				return 1000.00;
			}
		} else if (hotelName.equals("Festival") || hotelName.equals("Imperial")
				|| hotelName.equals("American")) {

			if (noofTiles == 2) {
				return 300.00;
			} else if (noofTiles == 3) {
				return 400.00;
			} else if (noofTiles == 4) {
				return 500.00;
			} else if (noofTiles == 5) {
				return 600.00;
			} else if (noofTiles >= 6 && noofTiles < 11) {
				return 700.00;
			} else if (noofTiles >= 11 && noofTiles < 21) {
				return 800.00;
			} else if (noofTiles >= 21 && noofTiles < 31) {
				return 900.00;
			} else if (noofTiles >= 31 && noofTiles < 41) {
				return 1000.00;
			} else if (noofTiles == 41) {
				return 1100.00;
			}

		} else if (hotelName.equals("Continental") || hotelName.equals("Tower")) {

			if (noofTiles == 2) {
				return 400.00;
			} else if (noofTiles == 3) {
				return 500.00;
			} else if (noofTiles == 4) {
				return 600.00;
			} else if (noofTiles == 5) {
				return 700.00;
			} else if (noofTiles >= 6 && noofTiles < 11) {
				return 800.00;
			} else if (noofTiles >= 11 && noofTiles < 21) {
				return 900.00;
			} else if (noofTiles >= 21 && noofTiles < 31) {
				return 1000.00;
			} else if (noofTiles >= 31 && noofTiles < 41) {
				return 1100.00;
			} else if (noofTiles == 41) {
				return 1200.00;
			}

		} else {
			throw new ExceptionHandling("Invalid hotel Name to get stock price");
		}

		return 0.0;
	}
	
	

	
	public void playerTurnDone() throws ExceptionHandling {
		Player firstPlayer = playerList.get(0);
		playerList.remove(0);
		String tileName = generateRandomTile();
		
		if(!tileName.equals("Empty")){
		firstPlayer.addTileToPlayer(tileName);
		remTiles.remove(tileName);
		}
		
		playerList.add(firstPlayer);
		DataRepresentation state = new DataRepresentation(playerList);
		System.out.print(state.getOutputXML());
		states.add(state);
		
	}

	public double getMinorityBonus(String hotelName) throws ExceptionHandling {
		Hotel hotelObj = getHotelObject(hotelName);
		int noofTiles = hotelObj.getTileListSize();
		if (hotelName.equals("Worldwide") || hotelName.equals("Sackson")) {
			if (noofTiles == 2) {
				return 200.00 * 5;
			} else if (noofTiles == 3) {
				return 300.00 * 5;
			} else if (noofTiles == 4) {
				return 400.00 * 5;
			} else if (noofTiles == 5) {
				return 500.00 * 5;
			} else if (noofTiles >= 6 && noofTiles < 11) {
				return 600.00 * 5;
			} else if (noofTiles >= 11 && noofTiles < 21) {
				return 700.00 * 5;
			} else if (noofTiles >= 21 && noofTiles < 31) {
				return 800.00 * 5;
			} else if (noofTiles >= 31 && noofTiles < 41) {
				return 900.00 * 5;
			} else if (noofTiles == 41) {
				return 1000.00 * 5;
			}
		} else if (hotelName.equals("Festival") || hotelName.equals("Imperial")
				|| hotelName.equals("American")) {

			if (noofTiles == 2) {
				return 300.00 * 5;
			} else if (noofTiles == 3) {
				return 400.00 * 5;
			} else if (noofTiles == 4) {
				return 500.00 * 5 ;
			} else if (noofTiles == 5) {
				return 600.00 * 5;
			} else if (noofTiles >= 6 && noofTiles < 11) {
				return 700.00 * 5;
			} else if (noofTiles >= 11 && noofTiles < 21) {
				return 800.00 * 5;
			} else if (noofTiles >= 21 && noofTiles < 31) {
				return 900.00 * 5;
			} else if (noofTiles >= 31 && noofTiles < 41) {
				return 1000.00 * 5;
			} else if (noofTiles == 41) {
				return 1100.00 * 5;
			}

		} else if (hotelName.equals("Continental") || hotelName.equals("Tower")) {

			if (noofTiles == 2) {
				return 400.00 * 5;
			} else if (noofTiles == 3) {
				return 500.00 * 5;
			} else if (noofTiles == 4) {
				return 600.00 * 5;
			} else if (noofTiles == 5) {
				return 700.00 * 5;
			} else if (noofTiles >= 6 && noofTiles < 11) {
				return 800.00 * 5;
			} else if (noofTiles >= 11 && noofTiles < 21) {
				return 900.00 * 5;
			} else if (noofTiles >= 21 && noofTiles < 31) {
				return 1000.00 * 5;
			} else if (noofTiles >= 31 && noofTiles < 41) {
				return 1100.00 * 5;
			} else if (noofTiles == 41) {
				return 1200.00* 5;
			}

		} else {
			throw new ExceptionHandling("Invalid hotel Name for calculating minority hotels");
		}

		return 0.0;
	}
	
	
	public double getMajorityBonus(String hotelName) throws ExceptionHandling {
		Hotel hotelObj = getHotelObject(hotelName);
		int noofTiles = hotelObj.getTileListSize();
		if (hotelName.equals("Worldwide") || hotelName.equals("Sackson")) {
			if (noofTiles == 2) {
				return 200.00 * 10;
			} else if (noofTiles == 3) {
				return 300.00 * 10;
			} else if (noofTiles == 4) {
				return 400.00 * 10;
			} else if (noofTiles == 5) {
				return 500.00 * 10;
			} else if (noofTiles >= 6 && noofTiles < 11) {
				return 600.00 * 10;
			} else if (noofTiles >= 11 && noofTiles < 21) {
				return 700.00 * 10;
			} else if (noofTiles >= 21 && noofTiles < 31) {
				return 800.00 * 10;
			} else if (noofTiles >= 31 && noofTiles < 41) {
				return 900.00 * 10;
			} else if (noofTiles == 41) {
				return 1000.00 * 10;
			}
		} else if (hotelName.equals("Festival") || hotelName.equals("Imperial")
				|| hotelName.equals("American")) {

			if (noofTiles == 2) {
				return 300.00 * 10;
			} else if (noofTiles == 3) {
				return 400.00 * 10;
			} else if (noofTiles == 4) {
				return 500.00 * 10;
			} else if (noofTiles == 5) {
				return 600.00 * 10;
			} else if (noofTiles >= 6 && noofTiles < 11) {
				return 700.00 * 10;
			} else if (noofTiles >= 11 && noofTiles < 21) {
				return 800.00 * 10;
			} else if (noofTiles >= 21 && noofTiles < 31) {
				return 900.00 * 10;
			} else if (noofTiles >= 31 && noofTiles < 41) {
				return 1000.00 * 10;
			} else if (noofTiles == 41) {
				return 1100.00 * 10;
			}

		} else if (hotelName.equals("Continental") || hotelName.equals("Tower")) {

			if (noofTiles == 2) {
				return 400.00 * 10;
			} else if (noofTiles == 3) {
				return 500.00 * 10;
			} else if (noofTiles == 4) {
				return 600.00 * 10;
			} else if (noofTiles == 5) {
				return 700.00 * 10;
			} else if (noofTiles >= 6 && noofTiles < 11) {
				return 800.00 * 10;
			} else if (noofTiles >= 11 && noofTiles < 21) {
				return 900.00 * 10;
			} else if (noofTiles >= 21 && noofTiles < 31) {
				return 1000.00 * 10;
			} else if (noofTiles >= 31 && noofTiles < 41) {
				return 1100.00 * 10;
			} else if (noofTiles == 41) {
				return 1200.00 * 10;
			}

		} else {
			throw new ExceptionHandling("Invalid hotel Name for calculating majority bonus");
		}

		return 0.0;
	}

	public void generateStateXML() throws ExceptionHandling {
		DataRepresentation state = new DataRepresentation(this,playerList,boardObj,hotelList);
		System.out.print(state.getOutputXML());
		states.add(state);
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

}

package edu.iu.cs.p532.server.administrators;


import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import edu.iu.cs.p532.server.components.Player;
import edu.iu.cs.p532.server.utilities.TileConversions;

public interface IAdministrator {

	public abstract void createPlayer(String playerName);

	public abstract void setPlayerIntialTiles(Player playerObj);

	public abstract Player getPlayerObj(String playerName);

	public abstract void createHotel(String hotelName);

	public abstract void addTileToHotel(String hotelName, String rowString,
			int columnIndex);

	public abstract void setTile(String rowString, int columnIndex);

	public abstract void buildPlayer(String playerName, double cash);

	public abstract void setPlayerShare(String playerName, int shareCount,
			String hotelName);

	public abstract void setPlayerTileList(String playerName, String rowString,
			int columnIndex);

	public abstract void placeTile(String rowString, int columnIndex)
			throws TransformerException, ParserConfigurationException;

	public abstract boolean checkTileInPlayer(String tileName);

	public abstract String generateRandomTile();

	public abstract boolean checkPlayerFromTile(String tileName);

	public abstract void placeTile(String rowString, int columnIndex,
			String hotelName);

	public abstract void buyShare(String hotelName);

	public abstract void playerTurnDone() throws TransformerException,
			ParserConfigurationException;

	public abstract double getStockPrice(String hotelName);

	public abstract void generateStateXML() throws TransformerException,
			ParserConfigurationException;

	public abstract void createNewBoard();

	public abstract IBoardManager getManagerObj();

	public abstract void setManagerObj(IBoardManager managerObj);

	public abstract List<Player> getPlayerList();

	public abstract void setPlayerList(List<Player> playerList);

	public abstract TileConversions getConvertTile();

	public abstract void setConvertTile(TileConversions convertTile);

	public abstract void initialStateXML();

}

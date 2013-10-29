package edu.iu.cs.p532.server.administrators;


import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import edu.iu.cs.p532.server.components.Board;
import edu.iu.cs.p532.server.components.Hotel;
import edu.iu.cs.p532.server.utilities.TileConversions;

public interface IBoardManager {

	public abstract String[][] getBoard();

	public abstract void setTile(String rowString, int columnIndex);

	public abstract Hotel getHotelObject(String hotelName);

	public abstract void createHotel(String hotelName);

	public abstract void addTileToHotel(String hotelName, String rowString,
			int columnIndex);

	public abstract void addTileToHotel(String hotelName, String tileName);

	public abstract Hotel getHotelFromTile(String tileName);

	public abstract boolean checkHotelFromTile(String tileName);

	public abstract int getHotelSize(String hotelName);

	public abstract boolean checkTileExistence(String tileName);

	public abstract boolean checkTileExistence(int rowIndex, int columnIndex);

	public abstract boolean checkBounds(int rowIndex, int columnIndex);

	public abstract void singletonRequest(String rowString, int columnIndex);

	public abstract void growingRequest(String rowString, int columnIndex);

	public abstract void mergingRequest(String rowString, int columnIndex,
			String hotelName);

	public abstract void mergeRequestOutput(List<String> mergeHotelNames,
			List<String> singletonTiles);

	public abstract void foundingRequest(String rowString, int columnIndex,
			String hotelName);

	public abstract boolean checkSingleton(int rowIndex, int columnIndex);

	public abstract boolean checkFounding(int rowIndex, int columnIndex);

	public abstract String checkTileType(int rowIndex, int columnIndex);

	public abstract String inspectBoard(String rowString, int columnIndex);

	public abstract List<Hotel> getHotelList();

	public abstract void setHotelList(List<Hotel> hotelList);

	public abstract TileConversions getConvertTile();

	public abstract void setConvertTile(TileConversions convertTile);

	public abstract Board getBoardObj();

	public abstract void setBoardObj(Board boardObj);

}

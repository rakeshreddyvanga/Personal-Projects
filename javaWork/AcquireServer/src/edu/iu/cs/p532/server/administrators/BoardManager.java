package edu.iu.cs.p532.server.administrators;
/**
 * 
 */



import java.util.ArrayList;
import java.util.List;



import edu.iu.cs.p532.server.components.Board;
import edu.iu.cs.p532.server.components.Hotel;
import edu.iu.cs.p532.server.utilities.ExceptionHandling;
import edu.iu.cs.p532.server.utilities.IConstants;
import edu.iu.cs.p532.server.utilities.TileConversions;

public class BoardManager  {

	 List<Hotel> hotelList;
	 Board boardObj ;
	 List<String> remTiles ; // tiles not under player and on board
	 List<String> remHotels ; // hotels to be found
	 List<String> acquiredHotelNames;

	
	public BoardManager()
	{
		hotelList = new ArrayList<Hotel>();
		boardObj = new Board();
		remTiles = new ArrayList<String>();
		remHotels = new ArrayList<String>();
		acquiredHotelNames = new ArrayList<String>();
		setup();
	}
	
	private boolean setup() {
		for (int i = 0; i < IConstants.rows; i++) {
			for (int j = 0; j < IConstants.columns; j++) {
				try {
					remTiles.add(TileConversions.getTileName(i, j));
				} catch (Exception e) {
					return false;
				}
			}
		}
		
		try
		{
			remHotels. add("American");
			remHotels. add("Tower");
			remHotels. add("Continental");
			remHotels. add("Festival");
			remHotels. add("Imperial");
			remHotels. add("Worldwide");
			remHotels. add("Sackson");
		}
		catch(Exception e)
		{
			return false;
		}
	
		
		return true;
	}

	public String[][] getBoard() {
		return boardObj.getBoard();
	}


	public boolean setTile(int rowIndex, int columnIndex) throws ExceptionHandling {
		
		remTiles.remove(TileConversions.getTileName(rowIndex, columnIndex));
		String rowString = TileConversions.generateRowString(rowIndex);
		String value = String.valueOf(columnIndex + 1).concat(rowString);
	
		if(boardObj.setTile(rowIndex, columnIndex, value))
			return true;
		else
			return false;
		
	}

	public Hotel getHotelObject(String hotelName) {
		for (Hotel item : hotelList) {
			if (item.getHotelName().equals(hotelName)) {
				return item;
			}
		}
		return null;
	}

	
	public void createHotel(String hotelName) {
		int safeAdd = 0;
		if (hotelList.isEmpty()) {
			Hotel hotelObj = new Hotel(hotelName);
			hotelObj.setHotelName(hotelName);
			hotelList.add(hotelObj);
			remHotels.remove(hotelName);
		} else {
			for (Hotel item : hotelList) {
				if (item.getHotelName().equals(hotelName)) {
					safeAdd = 1;					
				}
			}

			if (safeAdd == 0) {
				Hotel hotelObj = new Hotel(hotelName);
				hotelObj.setHotelName(hotelName);
				hotelList.add(hotelObj);
				remHotels.remove(hotelName);
			}
			
		}

	}

	
	public boolean addTileToHotel(String hotelName, String rowString,
			int columnIndex) throws ExceptionHandling {
		String tileName = TileConversions.getTileName(rowString, columnIndex);
		if(addTileToHotel(hotelName,tileName))
			return true;
		else
			return false;
	}

	
	public boolean addTileToHotel(String hotelName, String tileName) throws ExceptionHandling {
		int safeAdd = 0;
		if (!checkTileExistence(tileName)) {
			throw new ExceptionHandling("<error Msg=\" Adding tile " + tileName
					+ " non-existant on board to " + hotelName + " \"/>");
		}
		for (Hotel item : hotelList) {
			if (item.tileExistence(tileName)) {
				safeAdd = 1;
			}
		}
		if (safeAdd == 0) {
			Hotel hotelObj = getHotelObject(hotelName);
			hotelObj.addTileToHotel(tileName);
			remTiles.remove(tileName);
		} else {

			throw new ExceptionHandling("<error Msg=\""
							+ tileName
							+ " already existing in another hotel cannot add in this hotel\"/>");
		
		}
		return false;
	}

	
	public Hotel getHotelFromTile(String tileName) {
		for (Hotel item : hotelList) {
			if (item.tileExistence(tileName)) {
				return item;
			}
		}
		return null;
	}
	
	public int getHotelSize(String hotelName) {
		return getHotelObject(hotelName).getTileListSize();
	}

	
	public boolean checkTileExistence(String tileName) {
		String [] rowColumn = TileConversions
				.tileNameToIndex(tileName);
		int rowIndex = TileConversions.generateRowIndex(rowColumn[0]);
		int columnIndex = Integer.parseInt(rowColumn[1]);
		return checkTileExistence(rowIndex, columnIndex - 1);
	}

	
	public boolean checkTileExistence(int rowIndex, int columnIndex) {
		return boardObj.checkTileExistence(rowIndex, columnIndex);
	}


	public boolean checkBounds(int rowIndex, int columnIndex) {
		if (columnIndex >= IConstants.LowerBound && columnIndex <= IConstants.columnUpperBound && 
				rowIndex >= IConstants.LowerBound && rowIndex <= IConstants.rowUpperBound) {
			return true;
		} else {
			return false;
		}
	}

	public boolean singletonRequest(String rowString, int columnIndex) throws ExceptionHandling {
		int rowIndex = TileConversions.generateRowIndex(rowString);
		if(setTile(rowIndex, columnIndex))
			return true;
		else 
			return false;
	}

	public boolean growingRequest(String rowString, int columnIndex) throws ExceptionHandling {
		int rowIndex = TileConversions.generateRowIndex(rowString);
		List<String> adjacentHotels = new ArrayList<String>();
		String tileName = "";
		List<String> singletonTiles = new ArrayList<String>();
		if (checkSingleton(rowIndex, columnIndex - 1)) {
			if (checkSingleton(rowIndex, columnIndex - 1)
					&& checkTileExistence(rowIndex, columnIndex - 1)) {
				singletonTiles.add(TileConversions.getTileName(rowIndex,
						columnIndex - 1));
			}

		} else {
			tileName = TileConversions.getTileName(rowIndex, columnIndex - 1);
			if (getHotelFromTile(tileName) != null) {
				adjacentHotels.add(getHotelFromTile(tileName).getHotelName());
			}
		}
		if (checkSingleton(rowIndex, columnIndex + 1)) {
			if (checkSingleton(rowIndex, columnIndex + 1)
					&& checkTileExistence(rowIndex, columnIndex + 1)) {
				singletonTiles.add(TileConversions.getTileName(rowIndex,
						columnIndex + 1));
			}

		} else {
			tileName = TileConversions.getTileName(rowIndex, columnIndex + 1);
			if (getHotelFromTile(tileName) != null) {
				adjacentHotels.add(getHotelFromTile(tileName).getHotelName());
			}
		}
		if (checkSingleton(rowIndex + 1, columnIndex)) {
			if (checkSingleton(rowIndex + 1, columnIndex)
					&& checkTileExistence(rowIndex + 1, columnIndex)) {
				singletonTiles.add(TileConversions.getTileName(rowIndex + 1,
						columnIndex));
			}

		} else {
			tileName = TileConversions.getTileName(rowIndex + 1, columnIndex);
			if (getHotelFromTile(tileName) != null) {
				adjacentHotels.add(getHotelFromTile(tileName).getHotelName());
			}
		}
		if (checkSingleton(rowIndex - 1, columnIndex)) {
			if (checkSingleton(rowIndex - 1, columnIndex)
					&& checkTileExistence(rowIndex - 1, columnIndex)) {
				singletonTiles.add(TileConversions.getTileName(rowIndex - 1,
						columnIndex));
			}

		} else {
			tileName = TileConversions.getTileName(rowIndex - 1, columnIndex);
			if (getHotelFromTile(tileName) != null) {
				adjacentHotels.add(getHotelFromTile(tileName).getHotelName());
			}
		}
		int adjacentHotelSize = adjacentHotels.size(), i = 0, j = 0;
		while (i < adjacentHotelSize) {
			for (j = i + 1; j < adjacentHotelSize; j++) {
				if (!(adjacentHotels.get(j).equals(adjacentHotels.get(i)))) {
					
				}
			}
			i++;
		}

		if(!setTile(rowIndex, columnIndex))
			return false;
		
		if(!addTileToHotel(adjacentHotels.get(0), rowString, columnIndex))
			return false;
		
		for (int singletonTilesSize = 0; singletonTilesSize < singletonTiles
				.size(); singletonTilesSize++) {
			addTileToHotel(adjacentHotels.get(0),singletonTiles.get(singletonTilesSize));
		}
		return true;

	}

	
	public boolean mergingRequest(String rowString, int columnIndex,
			String hotelName) throws ExceptionHandling {
		int mergeFlag = 0, rowIndex = TileConversions.generateRowIndex(rowString);
		List<String> adjacentHotels = new ArrayList<String>();
		List<String> singletonTiles = new ArrayList<String>();
		List<String> mergeHotelNames = new ArrayList<String>();
		String tileName = "";
		if (checkSingleton(rowIndex, columnIndex - 1)
				&& checkTileExistence(rowIndex, columnIndex - 1)) {
			singletonTiles.add(TileConversions.getTileName(rowIndex,
					columnIndex - 1));

		} else {
			tileName = TileConversions.getTileName(rowIndex, columnIndex - 1);
			if (getHotelFromTile(tileName) != null) {
				adjacentHotels.add(getHotelFromTile(tileName).getHotelName());
			}
		}
		if (checkSingleton(rowIndex, columnIndex + 1)
				&& checkTileExistence(rowIndex, columnIndex + 1)) {
			singletonTiles.add(TileConversions.getTileName(rowIndex,
					columnIndex + 1));

		} else {
			tileName = TileConversions.getTileName(rowIndex, columnIndex + 1);
			if (getHotelFromTile(tileName) != null) {
				adjacentHotels.add(getHotelFromTile(tileName).getHotelName());
			}
		}
		if (checkSingleton(rowIndex + 1, columnIndex)
				&& checkTileExistence(rowIndex + 1, columnIndex)) {
			singletonTiles.add(TileConversions.getTileName(rowIndex + 1,
					columnIndex));

		} else {
			tileName = TileConversions.getTileName(rowIndex + 1, columnIndex);
			if (getHotelFromTile(tileName) != null) {
				adjacentHotels.add(getHotelFromTile(tileName).getHotelName());
			}
		}
		if (checkSingleton(rowIndex - 1, columnIndex)
				&& checkTileExistence(rowIndex - 1, columnIndex)) {
			singletonTiles.add(TileConversions.getTileName(rowIndex - 1,
					columnIndex));

		} else {
			tileName = TileConversions.getTileName(rowIndex - 1, columnIndex);
			if (getHotelFromTile(tileName) != null) {
				adjacentHotels.add(getHotelFromTile(tileName).getHotelName());
			}
		}
		int adjacentHotelSize = adjacentHotels.size(), i = 0, j = 0;
		while (i < adjacentHotelSize) {
			for (j = i + 1; j < adjacentHotelSize; j++) {
				if (!(adjacentHotels.get(j).equals(adjacentHotels.get(i)))) {
					mergeFlag = 1;
					if (!(mergeHotelNames.contains(adjacentHotels.get(i)))) {
						mergeHotelNames.add(adjacentHotels.get(i));
					}
					if (!(mergeHotelNames.contains(adjacentHotels.get(j)))) {
						mergeHotelNames.add(adjacentHotels.get(j));
					}
				}
			}
			i++;
		}
		if (mergeFlag == 1) {
			
			if(!setTile(rowIndex, columnIndex))
			{
				return false;
			}
			singletonTiles.add(String.valueOf(columnIndex + 1)
					.concat(rowString));
		}

		int maxSize = 0, maxIndex = -1, sizeLocal = -1;

		for (int m = 0; m < mergeHotelNames.size(); m++) {
			sizeLocal = getHotelObject(mergeHotelNames.get(m))
					.getTileListSize();
			if (sizeLocal > maxSize) {
				maxSize = getHotelObject(mergeHotelNames.get(m))
						.getTileListSize();
				maxIndex = m;
			}
		}
		if (!getHotelObject(mergeHotelNames.get(maxIndex)).getHotelName()
				.equals(hotelName)) {
			throw new ExceptionHandling("<error Msg=\" Wrong hotel name provided for merge\"/>");
			
		}
		for (int n = 0; n < mergeHotelNames.size(); n++) {
			if (maxIndex != n) {
				getHotelObject(mergeHotelNames.get(maxIndex)).getTileList()
						.addAll(getHotelObject(mergeHotelNames.get(n))
								.getTileList());
				hotelList.remove(getHotelObject(mergeHotelNames.get(n)));
				remHotels.add(mergeHotelNames.get(n));
				acquiredHotelNames.add(mergeHotelNames.get(n));
			}
		}
		mergeHotelNames.clear();
		adjacentHotels.clear();
		for (int singletonTilesSize = 0; singletonTilesSize < singletonTiles
				.size(); singletonTilesSize++) {
			if(!addTileToHotel(mergeHotelNames.get(maxIndex),singletonTiles.get(singletonTilesSize)))
				return false;
		}
		
		return true;
	}

	
	public boolean foundingRequest(String rowString, int columnIndex,
			String hotelName) throws ExceptionHandling {
		int rowIndex = TileConversions.generateRowIndex(rowString);
		int neighboursCount = 0, neighbourHotelCount = 0, leftFlag = 0, rightFlag = 0, upperFlag = 0, bottomFlag = 0;
		String tileName = "";
		if (checkBounds(rowIndex - 1, columnIndex)
				&& checkTileExistence(rowIndex - 1, columnIndex)) {
			tileName = TileConversions.getTileName(rowIndex - 1, columnIndex);
			if (getHotelFromTile(tileName) != null) {
				neighbourHotelCount++;
			} else {
				neighboursCount++;
				upperFlag = 1;
			}
		}
		if (checkBounds(rowIndex + 1, columnIndex)
				&& checkTileExistence(rowIndex + 1, columnIndex)) {
			tileName = TileConversions.getTileName(rowIndex + 1, columnIndex);
			if (getHotelFromTile(tileName) != null) {
				neighbourHotelCount++;
			} else {
				neighboursCount++;
				bottomFlag = 1;
			}
		}
		if (checkBounds(rowIndex, columnIndex + 1)
				&& checkTileExistence(rowIndex, columnIndex + 1)) {
			tileName = TileConversions.getTileName(rowIndex, columnIndex + 1);
			if (getHotelFromTile(tileName) != null) {
				neighbourHotelCount++;
			} else {
				neighboursCount++;
				rightFlag = 1;
			}
		}
		if (checkBounds(rowIndex, columnIndex - 1)
				&& checkTileExistence(rowIndex, columnIndex - 1)) {
			tileName = TileConversions.getTileName(rowIndex, columnIndex - 1);
			if (getHotelFromTile(tileName) != null) {
				neighbourHotelCount++;
			} else {
				neighboursCount++;
				leftFlag = 1;
			}
		}

		if (neighboursCount >= 1 && neighbourHotelCount == 0) {
			if(!setTile(rowIndex, columnIndex))
				return false;
			tileName = TileConversions.getTileName(rowIndex, columnIndex);
			createHotel(hotelName);
			if(!addTileToHotel(hotelName, tileName))
				return false;
			if (leftFlag == 1) {
				tileName = TileConversions.getTileName(rowIndex, columnIndex - 1);
				addTileToHotel(hotelName, tileName);
			}
			if (rightFlag == 1) {
				tileName = TileConversions.getTileName(rowIndex, columnIndex + 1);
				addTileToHotel(hotelName, tileName);
			}
			if (upperFlag == 1) {
				tileName = TileConversions.getTileName(rowIndex + 1, columnIndex);
				addTileToHotel(hotelName, tileName);
			}
			if (bottomFlag == 1) {
				tileName = TileConversions.getTileName(rowIndex - 1, columnIndex);
				addTileToHotel(hotelName, tileName);
			}
		}
		// generateBoardXML();
		return true;
	}

	
	public boolean checkSingleton(int rowIndex, int columnIndex) {

		int neighboursCount = 0;
		if (checkBounds(rowIndex - 1, columnIndex)
				&& checkTileExistence(rowIndex - 1, columnIndex)) {
			neighboursCount++;
		}
		if (checkBounds(rowIndex + 1, columnIndex)
				&& checkTileExistence(rowIndex + 1, columnIndex)) {
			neighboursCount++;
		}
		if (checkBounds(rowIndex, columnIndex + 1)
				&& checkTileExistence(rowIndex, columnIndex + 1)) {
			neighboursCount++;
		}
		if (checkBounds(rowIndex, columnIndex - 1)
				&& checkTileExistence(rowIndex, columnIndex - 1)) {
			neighboursCount++;
		}

		if (neighboursCount == 0) {
			return true;
		} else {
			return false;
		}
	}

	
	public boolean checkFounding(int rowIndex, int columnIndex) {
		int neighboursCount = 0, neighbourHotelCount = 0;
		String tileName = "";
		if (checkBounds(rowIndex - 1, columnIndex)
				&& checkTileExistence(rowIndex - 1, columnIndex)) {
			tileName = TileConversions.getTileName(rowIndex - 1, columnIndex);
			if (getHotelFromTile(tileName) != null) {
				neighbourHotelCount++;
			} else {
				neighboursCount++;
			}
		}
		if (checkBounds(rowIndex + 1, columnIndex)
				&& checkTileExistence(rowIndex + 1, columnIndex)) {
			tileName = TileConversions.getTileName(rowIndex + 1, columnIndex);
			if (getHotelFromTile(tileName) != null) {
				neighbourHotelCount++;
			} else {
				neighboursCount++;
			}
		}
		if (checkBounds(rowIndex, columnIndex + 1)
				&& checkTileExistence(rowIndex, columnIndex + 1)) {
			tileName = TileConversions.getTileName(rowIndex, columnIndex + 1);
			if (getHotelFromTile(tileName) != null) {
				neighbourHotelCount++;
			} else {
				neighboursCount++;
			}
		}
		if (checkBounds(rowIndex, columnIndex - 1)
				&& checkTileExistence(rowIndex, columnIndex - 1)) {
			tileName = TileConversions.getTileName(rowIndex, columnIndex - 1);
			if (getHotelFromTile(tileName) != null) {
				neighbourHotelCount++;
			} else {
				neighboursCount++;
			}
		}

		if (neighboursCount >= 1 && neighbourHotelCount == 0) {
			return true;
		} else {
			return false;
		}
	}

	
	private String checkTileType(int rowIndex, int columnIndex) throws ExceptionHandling {
		int mergeFlag = 0;
		List<String> adjacentHotels = new ArrayList<String>();
		List<String> mergeHotelNames = new ArrayList<String>();
		String tileName = "";
		if (checkSingleton(rowIndex, columnIndex - 1)) {

		} else {
			tileName = TileConversions.getTileName(rowIndex, columnIndex - 1);
			if (getHotelFromTile(tileName) != null) {
				adjacentHotels.add(getHotelFromTile(tileName).getHotelName());
			}
		}
		if (checkSingleton(rowIndex, columnIndex + 1)) {

		} else {
			tileName = TileConversions.getTileName(rowIndex, columnIndex + 1);
			if (getHotelFromTile(tileName) != null) {
				adjacentHotels.add(getHotelFromTile(tileName).getHotelName());
			}
		}
		if (checkSingleton(rowIndex + 1, columnIndex)) {

		} else {
			tileName = TileConversions.getTileName(rowIndex + 1, columnIndex);
			if (getHotelFromTile(tileName) != null) {
				adjacentHotels.add(getHotelFromTile(tileName).getHotelName());
			}
		}
		if (checkSingleton(rowIndex - 1, columnIndex)) {

		} else {
			tileName = TileConversions.getTileName(rowIndex - 1, columnIndex);
			if (getHotelFromTile(tileName) != null) {
				adjacentHotels.add(getHotelFromTile(tileName).getHotelName());
			}
		}
		int adjacentHotelSize = adjacentHotels.size(), i = 0, j = 0;
		while (i < adjacentHotelSize) {
			for (j = i + 1; j < adjacentHotelSize; j++) {
				if (!(adjacentHotels.get(j).equals(adjacentHotels.get(i)))) {
					mergeFlag = 1;
					if (!(mergeHotelNames.contains(adjacentHotels.get(i)))) {
						mergeHotelNames.add(adjacentHotels.get(i));
					}
					if (!(mergeHotelNames.contains(adjacentHotels.get(j)))) {
						mergeHotelNames.add(adjacentHotels.get(j));
					}
				}
			}
			i++;
		}
		adjacentHotels.clear();
		if (mergeFlag == 1) {
			int safeHotels = 0;
			for(int m = 0; m<mergeHotelNames.size(); m++)
			{
				if (getHotelObject(mergeHotelNames.get(m)).getTileListSize() >= IConstants.safeHotelSize)
				{
					safeHotels++;
				}
			}
			if(safeHotels >= 2)			
				return "safe";
			
			else					
			return "merging";
			
		}
		if (adjacentHotelSize == 0)
			throw new ExceptionHandling(" Invalid Tile. Should be either founding or singleton.");

		return "growing";

	}

	
	public String inspectBoard(String rowString, int columnIndex) throws ExceptionHandling {
		int rowIndex = TileConversions.generateRowIndex(rowString);
		String tileName = String.valueOf(columnIndex+1).concat(rowString);
		if(getHotelFromTile(tileName) != null)
		{
			return getHotelFromTile(tileName).getHotelName();
		}
		if (checkSingleton(rowIndex, columnIndex)) {
			
			return "singleton";
		} else if (checkFounding(rowIndex, columnIndex)) {
			
			return "founding";
		}
		return checkTileType(rowIndex, columnIndex);
		
	}

}

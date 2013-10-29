package edu.iu.cs.p532.server.components;


import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.iu.cs.p532.server.utilities.TileConversions;

public class Hotel {

	private List<String> tileList = new ArrayList<String>();
	private String hotelName;

	public Hotel(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public List<String> getTileList() {
		return tileList;
	}

	public boolean tileExistence(String tileName) {
		for (String item : tileList) {
			if (item.equals(tileName)) {
				return true;
			}
		}
		return false;
	}

	public int getTileListSize() {
		return tileList.size();
	}

	public void addTileToHotel(String tileName) {
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
				tileList.add(tileName);
			}

		}
	}

	public Element constructHotelElement(Document stateDocument,
			Element boardTag) {
		Element hotelTag = stateDocument.createElement("hotel");
		boardTag.appendChild(hotelTag);
		hotelTag.setAttribute("name", hotelName);		
		for(String item : tileList)
		{
			Element hotelTileTag = stateDocument.createElement("tile");
			hotelTag.appendChild(hotelTileTag);
			String [] rowColumn = TileConversions.tileNameToIndex(item);
			hotelTileTag.setAttribute("column", rowColumn[1]);
			hotelTileTag.setAttribute("row", rowColumn[0]);
		}
		return boardTag;
	}

}

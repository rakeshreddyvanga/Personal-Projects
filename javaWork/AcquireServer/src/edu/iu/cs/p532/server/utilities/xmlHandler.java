package edu.iu.cs.p532.server.utilities;


import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.iu.cs.p532.server.administrators.Administrator;
import edu.iu.cs.p532.server.administrators.IAdministrator;

public class xmlHandler extends DefaultHandler {
	private Administrator adminObj;
	ArrayList<String> shareList = new ArrayList<String>();
	boolean setPlace = false, setState = false, setPlayer = false,
			setBoard = false, setSetup = false, setHotel = false,
			setBuy = false, setDone = false;
	String globalPlayerName = "", globalHotelName = "";
	String setPlaceRow = "", setHotelName = "";
	int setPlaceColumn = -1;

	public xmlHandler(Administrator adminObj) {
		this.adminObj = adminObj;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) {
		try {
			if (qName.equals("setup")) {
				for (int i = 0; i < attributes.getLength(); i++) {
					if (attributes.getQName(i).contains("player") == false)
						throw new ExceptionHandling("<error msg = wrong input for setup tag/>");
					if((!attributes.getValue(i).matches("[a-zA-Z_0-9]+$")) || 
							(attributes.getValue(i).length()>20))
						throw new ExceptionHandling("<error msg = wrong input for player name/>");
					adminObj.createPlayer(attributes.getValue(i));
				}
				adminObj.initialStateXML();
			} else if (qName.equals("place")) {
				setPlace = true;
				setPlaceRow = attributes.getValue("row");
				setPlaceColumn = Integer
						.parseInt(attributes.getValue("column"));
				if(attributes.getValue("hotel") == null)
				setHotelName = null;
				else
					setHotelName = attributes.getValue("hotel");
			} else if (qName.equals("state")) {
				if (setPlace == false && setBuy == false && setDone == false)
					throw new ExceptionHandling("State set before other tags");
				setState = true;
			} else if (qName.equals("board")) {
				if (setState == false)
					throw new ExceptionHandling("Board set without state");
				//adminObj.createNewBoard();
				setBoard = true;
			} else if (qName.equals("player")) {
				if (setState == false)
					throw new ExceptionHandling(
							"Player set without state or board");
				setPlayer = true;
				globalPlayerName = attributes.getValue("name");
				int playerCash = -1;
				try {
					playerCash = Integer.parseInt(attributes.getValue("cash"));
					adminObj.buildPlayer(globalPlayerName, playerCash);
				}catch (NumberFormatException e){
					throw new ExceptionHandling("<error Msg = invalid input for cash/>");
				}
			} else if (qName.equals("tile")) {
				if (setBoard == false && setPlayer == false) {
					throw new ExceptionHandling(
							"Tile set with out board or player tag");
				}
				if (setPlayer == true) {
					adminObj.setPlayerTileList(globalPlayerName,
							attributes.getValue("row"),
							Integer.parseInt(attributes.getValue("column")) - 1);
				} else if (setHotel == true) {
					adminObj.addTileToHotel(globalHotelName,
							attributes.getValue("row"),
							Integer.parseInt(attributes.getValue("column")) - 1);
				} else {
					adminObj.setTile(TileConversions.generateRowIndex(attributes.getValue("row")),
							Integer.parseInt(attributes.getValue("column")) - 1);
				}
			} else if (qName.equals("hotel")) {
				if (setBoard == false)
					throw new ExceptionHandling("Hotel set without board tag");
				setHotel = true;
				globalHotelName = attributes.getValue("name");
				adminObj.createHotel(globalHotelName);
			} else if (qName.equals("done")) {
				setDone = true;
			} else if (qName.equals("buy")) {
				setBuy = true;
				for (int i = 0; i < attributes.getLength(); i++) {
					if (attributes.getQName(i).contains("share") == false)
						throw new ExceptionHandling("wrong input for buy tag");
					shareList.add(attributes.getValue(i));
				}
			} else if (qName.contains("share")) {
				if (setPlayer == false)
					throw new ExceptionHandling("<error Msg = share without buy tag/>");
				int shareCount=-1;
				try{
				shareCount = Integer.parseInt(attributes.getValue("count"));
				}
				catch (NumberFormatException e){
					throw new ExceptionHandling("<error Msg = invalid input for count/>");
				}
				if(shareCount > 25){
					throw new ExceptionHandling("<error Msg = invalid input for count/>");
				}
				adminObj.setPlayerShare(globalPlayerName, shareCount,
						attributes.getValue("name"));
			} else {
				throw new ExceptionHandling("<error Msg unknown tag in XML/>");
			}
		} catch (ExceptionHandling e) {
			System.out.println(e.getMessage());
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		try
		{
		if (qName.equals("board")) {
			setBoard = false;
		} else if (qName.equals("place")) {
			setPlace = false;
			
				if (setHotelName == null)					
						adminObj.placeTile(setPlaceRow, setPlaceColumn - 1);		
					
				else
						adminObj.placeTile(setPlaceRow, setPlaceColumn - 1,
								setHotelName);
				
				adminObj.generateStateXML();
			setPlaceRow = "";
			setPlaceColumn = -1;
		} else if (qName.equals("hotel")) {
			globalHotelName = "";
			setHotel = false;
		} else if (qName.equals("buy")) {
			
			 if(shareList.size() == 1)
				 adminObj.buyShare(shareList.get(0));
			 else if (shareList.size() == 2)
				 adminObj.buyShare(shareList.get(0),shareList.get(1));
			 else if(shareList.size() == 3)
				 adminObj.buyShare(shareList.get(0),shareList.get(1),shareList.get(2));
			 else
				throw new ExceptionHandling("Only three shares are permitted to buy");
			
				adminObj.generateStateXML();
			shareList.clear();
			setBuy = false;
		} else if (qName.equals("state")) {
			setState = false;
		} else if (qName.equals("player")) {
			globalPlayerName = "";
			setPlayer = false;
		} else if (qName.equals("tile")) {
		} else if (qName.equals("done")) {
			setDone = false;			
				adminObj.playerTurnDone();
		} else if (qName.equals("setup")) {

		} else if (qName.equals("share")) {
		} else {
			throw new ExceptionHandling("<error msg=unknown tag></error>");
		}
	}
	catch(Exception e)
	{
		System.out.println(e.getMessage());
	}
}
}

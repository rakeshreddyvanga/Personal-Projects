package iu.edu.cs.p532.pair13.acquire.parsers;
import iu.edu.cs.p532.pair13.acquire.admin.BoardManager;
import iu.edu.cs.p532.pair13.acquire.admin.PlaceTileManager;
import iu.edu.cs.p532.pair13.acquire.admin.State;
import iu.edu.cs.p532.pair13.acquire.admin.StateManager;
import iu.edu.cs.p532.pair13.acquire.components.Player;
import iu.edu.cs.p532.pair13.acquire.admin.GameParameters;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
//import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.Attribute;


public class Parser implements GameParameters{

	@SuppressWarnings("unchecked")
	public static State parseInputRequest(BufferedReader inputBuffer) throws GameException{
		ArrayList<Player> players = new ArrayList<Player>();
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			XMLEventReader eventReader = inputFactory.createXMLEventReader
					(inputBuffer);
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					if (startElement.getName().getLocalPart() == ("setup")) {
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							String attName = attribute.getName().toString().trim();
							if (attName.equals("player1") || attName.equals("player2") ||attName.equals("player3") 
									||attName.equals("player4") ||attName.equals("player5") ||attName.equals("player6")) {
								players.add(new Player(attribute.getValue()));
							}
							else
								throw new GameException("Invalid Attribute under setup tag");							
						}
						if (players.size() < 3)
							throw new GameException("Insufficient number of players");
						else if(players.size() > 6)
							throw new GameException("More than six players are not allowed");
						else{
							State initState = StateManager.GameSetup(players);
							return initState;
						}
					}
					if (startElement.getName().getLocalPart() == ("place")) {
						int hotelFlag = 0;
						State placeState = null;
						String sRow=null,sCol=null;
						String Label = null;
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							String attName = attribute.getName().toString();
							if (attName.equals("row")) 
								sRow  = attribute.getValue();
							else if (attName.equals("column")) 
								sCol  = attribute.getValue();
							else if (attName.equals("hotel")) {
								Label  = attribute.getValue().trim().toUpperCase();
								if(BoardManager.isHotel(Label))
									hotelFlag = 1;
								else
									throw new GameException("Invalid Hotel Name");
							}
							else
								throw new GameException("Invalid Attribute under place tag");
						}
						while (eventReader.hasNext()) {
							event = eventReader.nextEvent();
							if (event.isStartElement()) {
								startElement = event.asStartElement();
								if (startElement.getName().getLocalPart() == ("state")) {
									placeState = StateElementParser.parseState(eventReader);
									break;
								}
								else
									throw new GameException("Invalid element under place tag");


							}
							//else
								//throw new BoardException("Invalid XML : state element missing under place\" />");

						}
						if(validateRowCol(sRow,sCol)){
							if (hotelFlag == 1){
								placeState = PlaceTileManager.PlaceTile(placeState,sRow.trim().toUpperCase()
										,Integer.parseInt(sCol.trim()),Label);
								return placeState;
							}
							else{
								placeState = PlaceTileManager.PlaceTile(placeState,sRow.trim().toUpperCase()
										,Integer.parseInt(sCol.trim()));
								return placeState;
							}
						}
					}
					if (startElement.getName().getLocalPart() == ("buy")) {
						State buyState = null;
						ArrayList<String> share= new ArrayList<String>();
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							String attName = attribute.getName().toString();
							if (attName.equals("share1")) 
								share.add(attribute.getValue().trim().toUpperCase());
							else if (attName.equals("share2")) 
								share.add(attribute.getValue().trim().toUpperCase());
							else if (attName.equals("share3")) {
								share.add(attribute.getValue().trim().toUpperCase());
							}
							else
								throw new GameException("Invalid Attribute under buy tag");							
						}
						while (eventReader.hasNext()) {
							event = eventReader.nextEvent();
							if (event.isStartElement()) {

								startElement = event.asStartElement();
								if (startElement.getName().getLocalPart() == ("state")) {
									buyState = StateElementParser.parseState(eventReader);
									break;
								}
								else
									throw new GameException("Invalid element under buy tag");


							}
							else
								throw new GameException("Invalid XML : state element missing under buy");

						}
						buyState = StateManager.buyShare(buyState,share);
						return buyState;

					}
					if (startElement.getName().getLocalPart() == ("done")) {
						State doneState = null;						
						while (eventReader.hasNext()) {
							event = eventReader.nextEvent();
							if (event.isStartElement()) {

								startElement = event.asStartElement();
								if (startElement.getName().getLocalPart() == ("state")) {
									doneState = StateElementParser.parseState(eventReader);
									break;
								}
								else
									throw new GameException("Invalid element under done tag");


							}
							else
								throw new GameException("Invalid XML : state element missing under done");

						}
						doneState = StateManager.FinishTurn(doneState);
						return doneState;

					}
				}
			}
		}catch(NumberFormatException n){
			throw n;
		}catch (GameException e) {
			throw e;
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		throw new GameException("Invalid XML Input");
	}
	public static boolean validateRowCol(String sRow, String sCol) throws GameException{
		int col = -1;
		if(sRow == null)
			throw new GameException("Invalid Row ID: "+sRow);

		if(rows.inverse().get(sRow) < 1 || rows.inverse().get(sRow) > ROWS  )
			throw new GameException("Invalid Row ID: "+sRow);

		if(sCol == null || sCol.trim().length() == 0)
			throw new GameException("Invalid Column ID: "+sCol);

		if (sCol.trim().matches("[0-9]+(,[0-9]+)*,?"))
			col  = Integer.parseInt(sCol.trim());
		else
			throw new GameException("Invalid Column ID: "+sCol);
		if(col < 1 || col > 12){
			throw new GameException("Invalid Column ID");

		}
		return true;
	}
}
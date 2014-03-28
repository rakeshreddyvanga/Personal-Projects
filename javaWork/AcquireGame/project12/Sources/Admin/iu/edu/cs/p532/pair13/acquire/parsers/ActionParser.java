package iu.edu.cs.p532.pair13.acquire.parsers;

import iu.edu.cs.p532.pair13.acquire.admin.Action;
import iu.edu.cs.p532.pair13.acquire.admin.BoardManager;
import iu.edu.cs.p532.pair13.acquire.components.Tile;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
//import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.Attribute;


public class ActionParser{
	@SuppressWarnings("unchecked")
	public static Action parseInputRequest(String action) throws GameException{
		try {
			
			BufferedReader inputBuffer = new BufferedReader(new StringReader("<root>" + action + "</root>"));
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			XMLEventReader eventReader = inputFactory.createXMLEventReader
					(inputBuffer);
			ArrayList<String> buyShare = new ArrayList<String>();
			Tile placeTile = null;
			boolean win = false;
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					if (startElement.getName().getLocalPart() == ("action")) {
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							String attName = attribute.getName().toString().trim();
							if (attName.equals("hotel1") || attName.equals("hotel2") ||attName.equals("hotel3")){
								if(BoardManager.isHotel(attribute.getValue().trim().toUpperCase()))
									buyShare.add(attribute.getValue().trim().toUpperCase());
								else
									throw new GameException("Invalid Hotel share in action tag");
							}
							else if (attName.equals("win")){
								if(attribute.getValue().trim().equalsIgnoreCase("yes"))
									win = true;
								else if(attribute.getValue().trim().equalsIgnoreCase("no"))
									win = false;
								else
									throw new GameException("Invalid value for win attribute in action tag");
							}
							else
								throw new GameException("Invalid Attribute under action tag");							
						}
					}
					else if (startElement.getName().getLocalPart() == ("place")) {
						String sRow=null,sCol=null;
						String Label = null;
						int hotelFlag = 0;
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
						if(Parser.validateRowCol(sRow,sCol)){
							if (hotelFlag == 1){
								placeTile = new Tile(sRow.trim().toUpperCase(),Integer.parseInt(sCol.trim()),Label,1);

							}
							else{
								placeTile = new Tile(sRow.trim().toUpperCase(),Integer.parseInt(sCol.trim()),"free",1);
							}
						}
					}
				}
			}
			return new Action(placeTile,buyShare,win);
		}catch(NumberFormatException n){
			throw n;
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		throw new GameException("Invalid XML Input");
	}

}
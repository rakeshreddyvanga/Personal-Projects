package iu.edu.cs.p532.pair13.acquire.parsers;
import iu.edu.cs.p532.pair13.acquire.admin.BoardManager;
import iu.edu.cs.p532.pair13.acquire.components.Board;
import iu.edu.cs.p532.pair13.acquire.components.Hotel;
import iu.edu.cs.p532.pair13.acquire.components.Tile;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;

import java.util.Iterator;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


public class BoardElementParser{
	@SuppressWarnings("unchecked")
	public static Board parseBoard(XMLEventReader evtR) throws GameException{
		Board board = new Board();
		String hotelName = null;
		Hotel hotel = null;
		try{
			while (evtR.hasNext()) {
				XMLEvent event = evtR.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					if (startElement.getName().getLocalPart() == ("tile")) {
						String sRow=null,sCol = null;
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						while (attributes.hasNext()) {

							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals("column")) {
								sCol = attribute.getValue();

							}
							else if (attribute.getName().toString().equals("row")) {
								sRow  = attribute.getValue();
							}
						}
						if (Parser.validateRowCol(sRow,sCol)){
							
							if(hotelName == null)
								board.AddTile(new Tile(sRow.trim().toUpperCase(),Integer.parseInt(sCol.trim()),"singleton",1));					
							else
								board.AddTile(new Tile(sRow.trim().toUpperCase(),Integer.parseInt(sCol.trim()),hotelName,1));			
							if(hotelName!=null && !hotel.AddTile(new Tile(sRow.trim().toUpperCase(),Integer.parseInt(sCol.trim()),hotelName,1)))
								throw new GameException(hotelName+" chain has inconsistencies");							
						}
					}
					if (startElement.getName().getLocalPart() == ("hotel")) {
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals("name")) {
								hotelName  = attribute.getValue().trim().toUpperCase();
							}
						}
						if(hotelName == null || !BoardManager.isHotel(hotelName)){
							throw new GameException("Invalid Hotel Name");
						}
						else if(board.getHotelByName(hotelName) != null){
							throw new GameException(hotelName+
									" cannot have two chains on the board");
						}
						else{
							hotel = new Hotel(hotelName);
						}
					}
				}
				else if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart() == ("board")) 
						break;					
					if (endElement.getName().getLocalPart() == ("hotel")) {
						if(hotel!=null)
							board.AddHotel(hotel);
						hotelName = null;
						hotel = null;
					}

				}
			}
		}catch(GameException b){
			throw b;

		}catch (NumberFormatException n) {
			n.printStackTrace();
		}catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return board;
	}
}
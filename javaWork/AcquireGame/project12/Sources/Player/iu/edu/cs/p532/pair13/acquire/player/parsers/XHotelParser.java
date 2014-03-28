package iu.edu.cs.p532.pair13.acquire.player.parsers;
import iu.edu.cs.p532.pair13.acquire.player.components.Board;
import iu.edu.cs.p532.pair13.acquire.player.components.Hotel;
import iu.edu.cs.p532.pair13.acquire.player.constants.GameParameters;
import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;

import java.util.Iterator;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;



public class XHotelParser implements GameParameters{
	@SuppressWarnings("unchecked")
	public static Hotel parseXhotel(XMLEventReader evtR, StartElement stE) throws GameException{
		try{
			String hotelName = null;
			Iterator<Attribute> attributes = stE.getAttributes();
			while (attributes.hasNext()) {
				Attribute attribute = attributes.next();
				if (attribute.getName().toString().equals("label")) {
					hotelName  = attribute.getValue().trim().toUpperCase();
				}
			}
			if(hotelName == null || !Board.isHotel(hotelName)){
				System.out.println(hotelName);
				throw new GameException("Invalid Hotel Name in Xhotel tag");
			}
			else{
				return new Hotel(hotelName);
			}
			
		}catch(GameException g){
			throw g;
		}catch(NumberFormatException n){
			n.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		throw new GameException("Invalid Hotel Element");
	}
}
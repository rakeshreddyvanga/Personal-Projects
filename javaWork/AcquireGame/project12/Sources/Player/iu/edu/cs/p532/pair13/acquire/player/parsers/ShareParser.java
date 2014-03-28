package iu.edu.cs.p532.pair13.acquire.player.parsers;
import iu.edu.cs.p532.pair13.acquire.player.components.Board;
import iu.edu.cs.p532.pair13.acquire.player.components.Share;
import iu.edu.cs.p532.pair13.acquire.player.constants.GameParameters;
import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;

import java.util.Iterator;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;



public class ShareParser implements GameParameters{
	@SuppressWarnings("unchecked")
	public static Share parseShare(XMLEventReader evtR, StartElement stE) throws GameException{
		try{
			String Label = null;
			int Count = 0;
			Iterator<Attribute> shareAttributes = stE.getAttributes();
			while (shareAttributes.hasNext()) {

				Attribute shareAttribute = shareAttributes.next();
				if (shareAttribute.getName().toString().equals("name")) {
					if(Board.isHotel(shareAttribute.getValue().toString().trim().toUpperCase()))
						Label  = shareAttribute.getValue().toString().trim().toUpperCase();
					else
						throw new GameException("Invalid Share label:\" "
								+shareAttribute.getValue());
				}
				else if (shareAttribute.getName().toString().equals("count")) {
					if (shareAttribute.getValue().trim().matches("[0-9]+(,[0-9]+)*,?"))
						Count  = Integer.parseInt(shareAttribute.getValue().trim());
					else
						throw new GameException("Invalid Share count:"
								+shareAttribute.getValue());

				}
				
			}
			if(!Board.isHotel(Label)){
				throw new GameException("Invalid Share Label: " + Label);
			}
			if(Count < 1 || Count > TotalShares){
				throw new GameException("Invalid Share count: " + Count);
			}
			else{
				return new Share(Label,Count);
			}
			
		}catch(GameException g){
			throw g;
		}catch(NumberFormatException n){
			n.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		throw new GameException("Invalid Share Element");
	}
}
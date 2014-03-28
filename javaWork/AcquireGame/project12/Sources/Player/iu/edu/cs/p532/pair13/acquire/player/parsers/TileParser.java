package iu.edu.cs.p532.pair13.acquire.player.parsers;
import iu.edu.cs.p532.pair13.acquire.player.components.Tile;
import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;

import java.util.Iterator;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;



public class TileParser{
	@SuppressWarnings("unchecked")
	public static Tile parseTile(XMLEventReader evtR, StartElement stE) throws GameException{
		try{
			String sRow=null,sCol = null;
			Iterator<Attribute> TileAttributes = stE
					.getAttributes();
			while (TileAttributes.hasNext()) {
				Attribute TileAttribute = TileAttributes.next();
				if (TileAttribute.getName().toString().equals("column")) {
					sCol = TileAttribute.getValue();

				}
				else if (TileAttribute.getName().toString().equals("row")) {
					sRow  = TileAttribute.getValue();
				}
			}
			if (Tile.validateRowCol(sRow,sCol)){
				return new Tile(sRow.trim().toUpperCase(),Integer.parseInt(sCol.trim()),"free",1);						
			}
			
		}catch(GameException g){
			throw g;
		}catch(NumberFormatException n){
			n.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		throw new GameException("Invalid Tile Element");
	}
}
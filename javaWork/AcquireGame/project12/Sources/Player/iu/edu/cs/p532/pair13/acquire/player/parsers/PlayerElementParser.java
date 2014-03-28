package iu.edu.cs.p532.pair13.acquire.player.parsers;
import iu.edu.cs.p532.pair13.acquire.player.components.Board;
import iu.edu.cs.p532.pair13.acquire.player.components.Share;
import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;
import iu.edu.cs.p532.pair13.acquire.player.playerstrategies.Player;

import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


public class PlayerElementParser{
	@SuppressWarnings("unchecked")
	public static Player parsePlayer(XMLEventReader evtR, StartElement stE) throws GameException{
		try{
			ArrayList<Share> shares = new ArrayList<Share>();
			Player player = null;
			int Cash = -1;
			Iterator<Attribute> playerAttributes = stE
					.getAttributes();
			while (playerAttributes.hasNext()) {

				Attribute playerAttribute = playerAttributes.next();
				if (playerAttribute.getName().toString().equals("cash")) {
					if (playerAttribute.getValue().trim().matches("[0-9]+(,[0-9]+)*,?"))
						Cash = Integer.parseInt(playerAttribute.getValue().trim());
					else
						throw new GameException("Invalid Player Cash:\" "
								+playerAttribute.getValue());
				}
			}

			while(evtR.hasNext()){
				XMLEvent subEvent = evtR.nextEvent();
				if (subEvent.isStartElement()) {
					StartElement subStartElement = subEvent.asStartElement();
					if (subStartElement.getName().getLocalPart() == ("share")) {
						String Label = null;
						int Count = 0;

						Iterator<Attribute> shareAttributes = subStartElement.getAttributes();
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
									throw new GameException("Invalid Share count:\" "
											+shareAttribute.getValue());

							}
						}
						shares.add(new Share(Label,Count));
					}
					else{
						throw new GameException("Invalid Element under player tag");
					}
				}
				else if (subEvent.isEndElement()) {
					EndElement endElement = subEvent.asEndElement();
					if (endElement.getName().getLocalPart() == ("player")) {
						player= new Player(Cash, shares);
						return player;
					}
				}

			}

		}catch(GameException g){
			throw g;
		}catch(NumberFormatException n){
			n.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		throw new GameException("Invalid Player Element");
	}
}
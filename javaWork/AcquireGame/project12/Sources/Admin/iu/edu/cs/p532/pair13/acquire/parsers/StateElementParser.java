package iu.edu.cs.p532.pair13.acquire.parsers;
import iu.edu.cs.p532.pair13.acquire.admin.BoardManager;
import iu.edu.cs.p532.pair13.acquire.admin.State;
import iu.edu.cs.p532.pair13.acquire.admin.StateManager;
import iu.edu.cs.p532.pair13.acquire.components.Board;
import iu.edu.cs.p532.pair13.acquire.components.Player;
import iu.edu.cs.p532.pair13.acquire.components.Share;
import iu.edu.cs.p532.pair13.acquire.components.Tile;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;

import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


public class StateElementParser{
	@SuppressWarnings("unchecked")
	public static State parseState(XMLEventReader evtR) throws GameException{
		State state = null;
		Board board = null;
		ArrayList<Player> players =  new ArrayList<Player>();

		try{
			while (evtR.hasNext()) {
				XMLEvent event = evtR.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					if (startElement.getName().getLocalPart() == ("board")) {
						board = BoardElementParser.parseBoard(evtR);
						BoardManager.checkBoard(board);
					}
					else if (startElement.getName().getLocalPart() == ("player")) {
						ArrayList<Share> shares = new ArrayList<Share>();
						ArrayList<Tile> tiles = new ArrayList<Tile>();
						Player player = null;
						String Name = null;
						int Cash = -1;
						Iterator<Attribute> playerAttributes = startElement
								.getAttributes();
						while (playerAttributes.hasNext()) {

							Attribute playerAttribute = playerAttributes.next();
							if (playerAttribute.getName().toString().equals("name")) {
								Name = playerAttribute.getValue().toString().trim();
							}
							else if (playerAttribute.getName().toString().equals("cash")) {
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
											Label  = shareAttribute.getValue().toString().trim().toUpperCase();
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
								else if (subStartElement.getName().getLocalPart() == ("tile")) {
									String sRow = null;
									String sCol = null;
									Iterator<Attribute> tileAttributes = subStartElement.getAttributes();
									while (tileAttributes.hasNext()) {

										Attribute tileAttribute = tileAttributes.next();
										if (tileAttribute.getName().toString().equals("row")) {
											sRow = tileAttribute.getValue();
										}
										else if (tileAttribute.getName().toString().equals("column")) {
											sCol = tileAttribute.getValue();
										}
									}
									if (Parser.validateRowCol(sRow,sCol)){
										tiles.add(new Tile(sRow.trim().toUpperCase(),Integer.parseInt(sCol.trim()),"free",1));
									}
								}
							}
							else if (subEvent.isEndElement()) {
								EndElement endElement = subEvent.asEndElement();
								if (endElement.getName().getLocalPart() == ("player")) {
									player= new Player(Name, Cash, shares, tiles);
									players.add(player);
									break;
								}
							}
						}

					}
				}
				else if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart() == ("state")) {
						state = StateManager.CreateState(board,players);
						break;
					}
				}
			}
		}catch(GameException b){
			throw b;
		}catch(NumberFormatException n){
			n.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return state;
	}
}
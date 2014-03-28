package iu.edu.cs.p532.pair13.acquire.player.parsers;
import iu.edu.cs.p532.pair13.acquire.player.components.Board;
import iu.edu.cs.p532.pair13.acquire.player.components.Hotel;
import iu.edu.cs.p532.pair13.acquire.player.components.Share;
import iu.edu.cs.p532.pair13.acquire.player.components.Tile;
import iu.edu.cs.p532.pair13.acquire.player.components.Turn;
import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;
import iu.edu.cs.p532.pair13.acquire.player.playerstrategies.Player;

import java.io.BufferedReader;
import java.util.ArrayList;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;




public class TurnParser{
	public static Turn parseTurnRequest(BufferedReader inputBuffer) throws GameException{
		try {
			Board board = null;
			Player player = null;
			ArrayList<Tile> tiles = new ArrayList<Tile>();
			ArrayList<Share> shares = new ArrayList<Share>();
			ArrayList<Hotel> xHotels = new ArrayList<Hotel>();
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			XMLEventReader eventReader = inputFactory.createXMLEventReader
					(inputBuffer);
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					if (startElement.getName().getLocalPart() == ("take-turn")) {
						while (eventReader.hasNext()) {
							event = eventReader.nextEvent();
							if (event.isStartElement()) {
								startElement = event.asStartElement();
								if (startElement.getName().getLocalPart() == ("board")) {
									board = BoardElementParser.parseBoard(eventReader);
									//board.printBoard();
									BoardManager.checkBoard(board);
								}
								else if (startElement.getName().getLocalPart() == ("player")) {
									player = PlayerElementParser.parsePlayer(eventReader, startElement);
								}
								else if (startElement.getName().getLocalPart() == ("tile")) {
									tiles.add(TileParser.parseTile(eventReader, startElement));
								}
								else if (startElement.getName().getLocalPart() == ("share")) {
									shares.add(ShareParser.parseShare(eventReader, startElement));
								}
								else if (startElement.getName().getLocalPart() == ("hotel")) {
									xHotels.add(XHotelParser.parseXhotel(eventReader, startElement));
								}
								else
									throw new GameException("Invalid element under take-turn tag");
							}

						}

					}
					
				}
				
			}
			if(player!=null && board!=null ){
				player.setTiles(tiles);
				return new Turn(board,player,shares,xHotels);
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
}
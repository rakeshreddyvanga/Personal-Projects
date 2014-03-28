package iu.edu.cs.p532.pair13.acquire.admin;
import iu.edu.cs.p532.pair13.acquire.components.Board;
import iu.edu.cs.p532.pair13.acquire.components.Hotel;
import iu.edu.cs.p532.pair13.acquire.components.Player;
import iu.edu.cs.p532.pair13.acquire.components.Share;
import iu.edu.cs.p532.pair13.acquire.components.Tile;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class State implements GameParameters{

	private Board board;
	private ArrayList<Player> players;

	public State() throws GameException{
		this.players = new ArrayList<Player>();
		this.board = new Board();
	}
	public State(ArrayList<Player> players) throws GameException{
		this.players = players;
		this.board = new Board();
	}
	public State(Board board, ArrayList<Player> players){
		this.players = players;
		this.board = board;
	}
	public State(State s){
		this.board = new Board(s.getBoard());
		this.players = new ArrayList<Player>();
		for(Player p : s.getPlayers())
			this.players.add(new Player(p));
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public Player removeFirstPlayer(){
		return players.remove(0);
	}
	public void addPlayer(Player p){
		players.add(p);
	}
	public void printState(){
		board.printBoard();
		for(Player p : players){
			p.printPlayer();
		}
	}
	public boolean equals(State state)
	{
		int playerCompare=0;
		if(this.board.equals(state.getBoard()))
		{
			for(Player firstPlayer : state.getPlayers())
				for(Player secondPlayer : this.players)
					if(firstPlayer.equals(secondPlayer))
						playerCompare++;

			if(playerCompare == this.players.size() && playerCompare == state.getPlayers().size())
				return true;
			else{
				//System.out.println("failed in state comp");
				return false;
			}
				
		}
		else 
			return false;
	}	
public String constructStateElement(){
		String newline = System.getProperty("line.separator");
		String ret = "<state>";
		ret = ret + newline + board.constructBoardElement();

		for(Player p : players){
			ret = ret + newline + p.constructPlayerElement();
		}
		ret = ret + newline +"</state>";
		return ret;
	}
	public String getTakeTurnElement(){
		DocumentBuilderFactory build = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Transformer transformer = null;
		try {
			builder = build.newDocumentBuilder();
		} catch (ParserConfigurationException e) {			
			//e.printStackTrace();
		}
		Document takeTurnDocument = builder.newDocument();
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {			
			//e.printStackTrace();
		}
		//Appending the take turn tags to the takeTurnDocument
		Element takeTurnTag = takeTurnDocument.createElement("take-turn");
		takeTurnDocument.appendChild(takeTurnTag);

		//Adding player tag to take turn tag
		Element playerTag = takeTurnDocument.createElement("player");
		takeTurnTag.appendChild(playerTag);
		//playerTag.setAttribute("name",players.get(0).getName());
		playerTag.setAttribute("cash", String.valueOf(players.get(0).getCash()));
		ArrayList<Share> playerShares = players.get(0).getShares();
		for(int s = 0; s< playerShares.size(); s++)
		{
			Element playerSharesTag = takeTurnDocument.createElement("share");
			playerTag.appendChild(playerSharesTag);
			playerSharesTag.setAttribute("name",playerShares.get(s).getLabel());
			playerSharesTag.setAttribute("count",String.valueOf(playerShares.get(s).getCount()));
		}
		//End of player tag with shares

		//Start of board tag to the take turn
		Element boardTag = takeTurnDocument.createElement("board");
		boardTag = board.constructBoardElement(takeTurnDocument,boardTag);
		takeTurnTag.appendChild(boardTag);
		//End of board tag		

		//Start of Players Tiles Tag

		ArrayList<Tile> tiles = players.get(0).getTiles();		
		for(Tile item : tiles){
			Element playerTilesTag = takeTurnDocument.createElement("tile");
			takeTurnTag.appendChild(playerTilesTag);
			playerTilesTag.setAttribute("column", String.valueOf(item.getColumn()));
			playerTilesTag.setAttribute("row", String.valueOf(item.getRow()));
		}

		// Adding the remaining hotel shares and its respective count
		ArrayList<Hotel> hotels = board.getHotels();
		for(Hotel item : hotels)
		{
			Element availableSharesTag = takeTurnDocument.createElement("share");
			availableSharesTag.setAttribute("name",item.getName());
			availableSharesTag.setAttribute("count", String.valueOf(StateManager.calculateRemainingShares(this,item.getName())));
			if(StateManager.calculateRemainingShares(this,item.getName())>0)
				takeTurnTag.appendChild(availableSharesTag);
		}
		//End of the remaining hotel shares

		//Adding the remaining xhotel names to found
		int hotelFlag = 0;
		for(int l = 0 ; l < numberOfHotels ; l++)
		{
			for(Hotel item : hotels) 
			{
				if(item.getName().equals(ListOfHotels[l]))
				{
					hotelFlag = 1;
				}
			}
			if(hotelFlag == 0)
			{
				Element xhotelTag = takeTurnDocument.createElement("hotel");
				takeTurnTag.appendChild(xhotelTag);
				xhotelTag.setAttribute("label",ListOfHotels[l]);					
			}
			else
			{
				hotelFlag = 0;
			}
		}


		//End of the xhotel Names

		transformerFactory = TransformerFactory.newInstance();
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// e.printStackTrace();
		}

		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(takeTurnDocument);
		StringWriter xmlOutput = new StringWriter();

		try {
			transformer.transform(source, new StreamResult(xmlOutput));
		} catch (TransformerException e) {
			// e.printStackTrace();
		}

		return xmlOutput.toString();
	}
}
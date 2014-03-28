package iu.edu.cs.p532.pair13.acquire.player.components;


import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;
import iu.edu.cs.p532.pair13.acquire.player.playerstrategies.Player;

import java.util.ArrayList;
import java.io.StringWriter;


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
public class PState{
	
	private Move move;
	private Board board;
	private ArrayList<Player> players;
	private ArrayList<Share> remainingShares;
	
	public PState() throws GameException{
		this.players = new ArrayList<Player>();
		this.remainingShares = new ArrayList<Share>();
		this.board = new Board();
		this.move = new Move();
	}
	public PState(ArrayList<Player> players) throws GameException{
		this.players = players;
		this.remainingShares = new ArrayList<Share>();
		this.board = new Board();
		this.move = new Move();
	}
	public PState(Board board, ArrayList<Player> players, ArrayList<Share> remainingShares){
		this.players = players;
		this.board = board;
		this.remainingShares = remainingShares;
		this.move = new Move();
	}
	public PState(Move move, Board board, ArrayList<Player> players,ArrayList<Share> remainingShares) {
		this.move = move;
		this.board = board;
		this.remainingShares = remainingShares;
		this.players = players;
	}
	public PState(PState ps){
		this.move = new Move(ps.move);
		this.board = new Board(ps.getBoard());
		this.players = new ArrayList<Player>();
		for(Player p : ps.getPlayers())
			this.players.add(new Player(p));
		this.remainingShares = new ArrayList<Share>();
		for(Share s: ps.getRemainingShares())
			this.remainingShares.add(s);
	}
	public String ActionString() throws GameException{
		DocumentBuilderFactory build = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Transformer transformer = null;
		try {
			builder = build.newDocumentBuilder();
		} catch (ParserConfigurationException e) {			
			//e.printStackTrace();
		}
		Document actionDocument = builder.newDocument();
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {			
			//e.printStackTrace();
		}
		//Appending the take turn tags to the takeTurnDocument
		Element actionTag = actionDocument.createElement("action");
		actionDocument.appendChild(actionTag);
		actionTag.setAttribute("win", "yes");
		if(move!=null){
			if( move.getPlaceTile()!= null){
				Element placeTag = actionDocument.createElement("place");
				actionTag.appendChild(placeTag);
				placeTag.setAttribute("row", move.getPlaceTile().getRow());
				placeTag.setAttribute("column", String.valueOf(move.getPlaceTile().getColumn()));
				if(Board.isHotel(move.getPlaceTile().getStatus()))
					placeTag.setAttribute("hotel", move.getPlaceTile().getStatus());

			}

			for (int i=1;i<move.getBuyShares().size()+1;i++){
				ArrayList<String> shares = move.getBuyShares();
				actionTag.setAttribute("hotel"+String.valueOf(1),shares.get(i-1));
			}
		}
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(actionDocument);
		StringWriter xmlOutput = new StringWriter();

		try {
			transformer.transform(source, new StreamResult(xmlOutput));
		} catch (TransformerException e) {
			// e.printStackTrace();
		}
		return xmlOutput.toString();
	}
	/*public String ActionString() throws GameException{
		String action,place = "",hotel = "";
		action="<action win=\"yes\" ";
		if(move!=null){
			if( move.getPlaceTile()!= null){
				if(Board.isHotel(move.getPlaceTile().getStatus()))
					hotel = "\" hotel=\""+move.getPlaceTile().getStatus();
				place = "<place row=\"" + move.getPlaceTile().getRow() 
						+ "\" column=\"" + move.getPlaceTile().getColumn()+hotel + "\" >  </place>";

			}

			for (int i=1;i<move.getBuyShares().size()+1;i++){
				ArrayList<String> shares = move.getBuyShares();
				action = action + " hotel" + i + "=\"" + shares.get(i-1) + "\"";
			}
		}
		action=action + " >" + place + "</action>";
		
		return action;
	}*/
	public int getRemShareCountByLabel(String label){
		for(Share s : remainingShares)
			if(s.getLabel().equals(label))
				return s.getCount();
		return 0;
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
	public Move getMove() {
		return move;
	}
	public void setMove(Move move) {
		this.move = move;
	}
	public ArrayList<Share> getRemainingShares() {
		return remainingShares;
	}
	public void setRemainingShares(ArrayList<Share> remainingShares) {
		this.remainingShares = remainingShares;
	}
	
}
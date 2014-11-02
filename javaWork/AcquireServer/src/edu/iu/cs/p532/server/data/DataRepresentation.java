package edu.iu.cs.p532.server.data;


import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.collect.*;

import edu.iu.cs.p532.server.administrators.Administrator;
import edu.iu.cs.p532.server.components.Hotel;
import edu.iu.cs.p532.server.components.Player;
import edu.iu.cs.p532.server.components.Board;
import edu.iu.cs.p532.server.utilities.ExceptionHandling;

public class DataRepresentation {

	private BiMap<Integer, Administrator> gameData = HashBiMap.create();
	private int id = 0;
	public void setId(int id) {
		this.id = id;
	}

	private Administrator adminObj;
	
	

	private List<Player> players = null;
	private Board board = null;
	private List<Hotel> hotels = null;
	private StringWriter outputXML;

	public StringWriter getOutputXML() {
		return outputXML;
	}

	public DataRepresentation(List<Player> players) throws ExceptionHandling
	{
		gameData.put(id, adminObj);
		this.players = players;
		constructSetupState();
	}
	
	public DataRepresentation(Administrator adminObj, List<Player> players) throws ExceptionHandling {
		this.adminObj = adminObj;
		this.players = players;
		this.gameData.put(id, adminObj);
		constructSetupState();
	}
	
	public DataRepresentation(Administrator adminObj, List<Player> players, Board board, List<Hotel> hotels)
			throws ExceptionHandling {
		this.adminObj = adminObj;
		this.players = players;
		this.gameData.put(id, adminObj);
		this.board = board;
		this.hotels = hotels;
		constructSetupState();
	}
	
	private void constructSetupState() throws ExceptionHandling {
		DocumentBuilderFactory build = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = build.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document stateDocument = builder.newDocument();
		
		Element stateTag = stateDocument.createElement("state");
		stateDocument.appendChild(stateTag);
		
		Element boardTag = stateDocument.createElement("board");
		stateTag.appendChild(boardTag);
		if(board != null)
		{
			boardTag = board.constructBoardElement(stateDocument,boardTag);
			for(Hotel item : hotels)
			{
				boardTag = item.constructHotelElement(stateDocument,boardTag);
			}
		}
		
		Element playerTag;
		
		for(Player item : players)
		{
			playerTag = item.constructPlayerElement(stateDocument);
			stateTag.appendChild(playerTag);
		}
		
		outputXML = createXMLStringWriter(stateDocument);
		
		
		
	}
	
	
	
	private StringWriter createXMLStringWriter(Document stateDocument) throws ExceptionHandling {
		Transformer transformer = null;
		StringWriter xmlOutput = null;
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
				transformer = transformerFactory.newTransformer();
				transformerFactory = TransformerFactory.newInstance();		
				transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(stateDocument);
				xmlOutput = new StringWriter();
				transformer.transform(source, new StreamResult(xmlOutput));
			}
		catch (Exception e)
		{
			throw new ExceptionHandling(e.getMessage());
		}
		return xmlOutput;
	}

	public BiMap<Integer, Administrator> getGameData() {
		return gameData;
	}

	public void setAdministratorObj(int roundNumber, Administrator adminObj) {
		gameData.put(roundNumber, adminObj);
	}

	public Administrator getAdministratorObj(int roundNumber) {
		return gameData.get(id);
	}
	
	public Administrator getAdminObj() {
		return adminObj;
	}
	
	public Board getBoard() {
		return board;
	}

}

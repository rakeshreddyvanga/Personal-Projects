package edu.iu.cs.p532.server.components;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.iu.cs.p532.server.utilities.ExceptionHandling;
import edu.iu.cs.p532.server.utilities.IConstants;
import edu.iu.cs.p532.server.utilities.TileConversions;

/**
 * 
 */


public class Board {

	private String[][] board = new String[IConstants.rows][IConstants.columns];

	// It will return the XML form of the board converted into String

	public boolean setTile(int rowIndex, int columnIndex, String value) throws ExceptionHandling {
		
		try {
			if(board[rowIndex][columnIndex] ==  null)
			board[rowIndex][columnIndex] = value;
			else
				throw new ExceptionHandling("Tile already present on the board");
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ExceptionHandling("Cannot set value on the board, invalid set tile request");
		}
		return true;
	}

	public String[][] getBoard() {
		return board;
	}

	public boolean checkTileExistence(int rowIndex, int columnIndex) {
		try {
			if (board[rowIndex][columnIndex] != null) {
				return true;
			} else {
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	public Element constructBoardElement(Document stateDocument, Element boardTag){
		for (int i = 0; i < IConstants.rows; i++)
			for (int j = 0; j < IConstants.columns; j++)
				if (board[i][j] != null && !board[i][j].isEmpty()) {
					String [] rowColumn = TileConversions.tileNameToIndex(board[i][j]);
					Element boardTileTag = stateDocument.createElement("tile");
					boardTag.appendChild(boardTileTag);
					boardTileTag.setAttribute("column", rowColumn[1]);
					boardTileTag.setAttribute("row", rowColumn[0]);
				}
		return boardTag;
		
	}

}

package iu.edu.cs.p532.pair13.acquire.tests;

import static org.junit.Assert.*;
import iu.edu.cs.p532.pair13.acquire.admin.BoardManager;
import iu.edu.cs.p532.pair13.acquire.components.Board;
import iu.edu.cs.p532.pair13.acquire.components.Hotel;
import iu.edu.cs.p532.pair13.acquire.components.Tile;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;
import org.junit.Test;

public class BoardManagerTester {

	@Test
	public void testCheckBoard() {
		try {
			Board board = new Board();
			//Tile testTile1 = new Tile(2,1,"AMERICAN",1);
			Tile testTile2 = new Tile(2,2,"AMERICAN",1);
			Tile testTile3 = new Tile(2,3,"AMERICAN",1);
			//Tile testTile4 = new Tile(1,2,"AMERICAN",1);
			Tile testTile5 = new Tile(3,2,"AMERICAN",1);
			Hotel hotel = new Hotel("AMERICAN");
			//hotel.AddTile(testTile1);
			//hotel.AddTile(testTile2);
			hotel.AddTile(testTile3);
			hotel.AddTile(testTile2);
			hotel.AddTile(testTile5);
			board.AddHotel(hotel);
			board.AddTile(testTile3);
			board.AddTile(testTile2);
			board.AddTile(testTile5);
			assertTrue("chkBoard failed",BoardManager.checkBoard(board));
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Board board = new Board();
			//Tile testTile1 = new Tile(2,1,"AMERICAN",1);
			Tile testTile2 = new Tile(2,2,"AMERICAN",1);
			Tile testTile3 = new Tile(2,3,"AMERICAN",1);
			//Tile testTile4 = new Tile(1,2,"AMERICAN",1);
			Tile testTile5 = new Tile(3,2,"AMERICAN",1);
			Hotel hotel = new Hotel("AMERICAN");
			//hotel.AddTile(testTile1);
			//hotel.AddTile(testTile2);
			hotel.AddTile(testTile3);
			hotel.AddTile(testTile2);
			hotel.AddTile(testTile5);
			board.AddHotel(hotel);
			board.AddTile(testTile3);
			board.AddTile(testTile2);
			assertFalse("chkBoard failed",BoardManager.checkBoard(board));
		} catch (GameException e) {
			// TODO Auto-generated catch block
			assertTrue(true);
		}
	}

}

package iu.edu.cs.p532.pair13.acquire.tests;

import static org.junit.Assert.*;

import iu.edu.cs.p532.pair13.acquire.admin.PlaceTileManager;
import iu.edu.cs.p532.pair13.acquire.admin.State;
import iu.edu.cs.p532.pair13.acquire.admin.StateManager;
import iu.edu.cs.p532.pair13.acquire.components.Board;
import iu.edu.cs.p532.pair13.acquire.components.Hotel;
import iu.edu.cs.p532.pair13.acquire.components.Player;
import iu.edu.cs.p532.pair13.acquire.components.Share;
import iu.edu.cs.p532.pair13.acquire.components.Tile;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;

import java.util.ArrayList;

import org.junit.Test;


public class PlaceTileTester {

	@Test
	public void testPlaceTileStateCharInt() throws GameException {
		
		try{
			ArrayList<Player> players = new ArrayList<Player>();
			players.add(new Player("Nishant"
					, 6000, new ArrayList<Share>(), new ArrayList<Tile>()));

			players.add(new Player("Santos"
					, 6000, new ArrayList<Share>(), new ArrayList<Tile>()));
			players.add(new Player("Jose"
					, 6000, new ArrayList<Share>(), new ArrayList<Tile>()));
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
			State state = StateManager.CreateState(board, players);
			state.getPlayers().get(0).AddTile(new Tile(1,2,"AMERICAN",1));
			assertEquals("PlaceTile Failed",state.getBoard().getHotelByName("AMERICAN").getSize(),3);
			PlaceTileManager.PlaceTile(state, "A", 2);
			assertEquals("PlaceTile Failed",state.getBoard().getHotelByName("AMERICAN").getSize(),4);
		}catch (GameException e) {
			e.printStackTrace();
			fail("error occured");
		}
	}

	@Test
	public void testPlaceTileStateCharIntString() {
		try{
			ArrayList<Player> players = new ArrayList<Player>();
			players.add(new Player("Nishant"
					, 6000, new ArrayList<Share>(), new ArrayList<Tile>()));

			players.add(new Player("Santos"
					, 6000, new ArrayList<Share>(), new ArrayList<Tile>()));
			players.add(new Player("Jose"
					, 6000, new ArrayList<Share>(), new ArrayList<Tile>()));
			Board board = new Board();
			//Tile testTile1 = new Tile(2,1,"AMERICAN",1);
			Tile testTile2 = new Tile(2,2,"AMERICAN",1);
			Tile testTile3 = new Tile(2,3,"AMERICAN",1);
			//Tile testTile4 = new Tile(1,2,"AMERICAN",1);
			Tile testTile5 = new Tile(3,2,"AMERICAN",1);
			Tile testTile6 = new Tile(5,2,"TOWER",1);
			Tile testTile7 = new Tile(6,2,"TOWER",1);
			Tile testTile8 = new Tile(7,2,"TOWER",1);
			
			Hotel hotel = new Hotel("AMERICAN");
			Hotel hotel1 = new Hotel("TOWER");
			//hotel.AddTile(testTile1);
			//hotel.AddTile(testTile2);
			hotel.AddTile(testTile3);
			hotel.AddTile(testTile2);
			hotel.AddTile(testTile5);
			
			hotel1.AddTile(testTile6);
			hotel1.AddTile(testTile7);
			hotel1.AddTile(testTile8);
			
			board.AddHotel(hotel);
			board.AddHotel(hotel1);
			
			board.AddTile(testTile3);
			board.AddTile(testTile2);
			board.AddTile(testTile5);
			board.AddTile(testTile6);
			board.AddTile(testTile7);
			board.AddTile(testTile8);
			State state = StateManager.CreateState(board, players);
			state.getPlayers().get(0).AddTile(new Tile(4,2,"AMERICAN",1));
			ArrayList<String> shares = new ArrayList<String>();
			shares.add("AMERICAN");
			shares.add("AMERICAN");
			StateManager.buyShare(state, shares);
			assertEquals("PlaceTile Failed",state.getBoard().getHotelByName("AMERICAN").getSize(),3);
			assertEquals("PlaceTile Failed",state.getBoard().getHotelByName("TOWER").getSize(),3);
			assertEquals("CheckState Failed",players.get(0).getCash(),5200);
			PlaceTileManager.PlaceTile(state, "D", 2,"TOWER");
			assertFalse("PlaceTile Failed",state.getBoard().hasHotelByName("AMERICAN"));
			assertEquals("PlaceTile Failed",state.getBoard().getHotelByName("TOWER").getSize(),7);
			assertEquals("CheckState Failed",players.get(0).getCash(),11200);
		}catch (GameException e) {
			e.printStackTrace();
			fail("error occured");
		}
	}
	

}

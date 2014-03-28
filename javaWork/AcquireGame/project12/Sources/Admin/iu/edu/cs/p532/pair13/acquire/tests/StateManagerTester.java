package iu.edu.cs.p532.pair13.acquire.tests;

import static org.junit.Assert.*;

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


public class StateManagerTester {

	@Test
	public void testBuyShare() {
		
		try {
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
			ArrayList<String> shares = new ArrayList<String>();
			shares.add("AA");
			assertNull("CheckState Failed",StateManager.buyShare(state, shares));
		}catch (GameException e) {
			assertTrue(true);
		}
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
			ArrayList<String> shares = new ArrayList<String>();
			shares.add("AMERICAN");
			shares.add("AMERICAN");
			StateManager.buyShare(state, shares);
			assertEquals("CheckState Failed",players.get(0).getCash(),5200);
			assertEquals("CheckState Failed",players.get(0).getShares().get(0).getLabel(),"AMERICAN");
			assertEquals("CheckState Failed",players.get(0).getShares().get(0).getCount(),2);
		}catch (GameException e) {
			e.printStackTrace();
			fail("error occured");
		}
	}

	@Test
	public void testChkState() {
		
		try {
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
			assertTrue("CheckState Failed",StateManager.chkState(state));
		}catch (GameException e) {
			assertTrue(true);
			//e.printStackTrace();
		}
	}

	@Test
	public void testFinishTurn() {
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
			StateManager.FinishTurn(state);
			assertEquals("Finishturn failed",state.getPlayers().get(0).getName(),"Santos");
		}catch (GameException e) {
			e.printStackTrace();
			fail("error occured");
		}
	}

	@Test
	public void testCalculateRemainingShares() {
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
			ArrayList<String> shares = new ArrayList<String>();
			shares.add("AMERICAN");
			shares.add("AMERICAN");
			StateManager.buyShare(state, shares);
			StateManager.FinishTurn(state);
			assertEquals("Finishturn failed",StateManager.calculateRemainingShares(state, "AMERICAN"),23);
			assertEquals("Finishturn failed",StateManager.calculateRemainingShares(state, "TOWER"),25);
		}catch (GameException e) {
			e.printStackTrace();
			fail("error occured");
		}
	}


}

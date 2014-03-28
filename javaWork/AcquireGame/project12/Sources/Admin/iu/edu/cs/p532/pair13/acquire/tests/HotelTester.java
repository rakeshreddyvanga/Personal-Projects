package iu.edu.cs.p532.pair13.acquire.tests;

import static org.junit.Assert.*;

import iu.edu.cs.p532.pair13.acquire.components.Hotel;
import iu.edu.cs.p532.pair13.acquire.components.Tile;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;

import java.util.ArrayList;

import org.junit.Test;

public class HotelTester {

	@Test
	public void testEqualsHotel() {
		try {
			Tile testTile = new Tile(1,1,"AMERICAN",1);
			Tile testTile2 = new Tile(1,2,"AMERICAN",1);
			Hotel hotel = new Hotel("AMERICAN");
			Hotel hotel2 = new Hotel("AMERICAN");
			hotel.AddTile(testTile);
			hotel.AddTile(testTile2);
			hotel2.AddTile(testTile);
			hotel2.AddTile(testTile2);
			assertTrue("EqualsHotel Fails",hotel.equals(hotel2));
		} catch (GameException e) {
			System.out.println(e.getMessage());
			assertTrue(false);
		}
		try {
			Tile testTile = new Tile(1,1,"AMERICAN",1);
			Tile testTile2 = new Tile(1,2,"AMERICAN",1);
			Hotel hotel = new Hotel("AMERICAN");
			Hotel hotel2 = new Hotel("AMERICAN");
			hotel.AddTile(testTile);
			hotel.AddTile(testTile2);
			hotel2.AddTile(testTile);
			//hotel2.AddTile(testTile2);
			assertFalse("EqualsHotel Fails",hotel.equals(hotel2));
		} catch (GameException e) {
			System.out.println(e.getMessage());
			assertTrue(false);
		}
		try {
			Tile testTile = new Tile(1,1,"AMERICAN",1);
			Tile testTile2 = new Tile(1,2,"AMERICAN",1);
			Hotel hotel = new Hotel("AMERICAN");
			Hotel hotel2 = new Hotel("AMERICAN");
			hotel.AddTile(testTile);
			//hotel.AddTile(testTile2);
			hotel2.AddTile(testTile);
			hotel2.AddTile(testTile2);
			assertFalse("EqualsHotel Fails",hotel.equals(hotel2));
		} catch (GameException e) {
			System.out.println(e.getMessage());
			assertTrue(false);
		}
	}
	@Test
	public void testSetName() {
		try {
			Hotel testHotel= new Hotel("AMERICAN");
			testHotel.setName("");
			fail("invalid hotels name");
		} catch (GameException e){
			assertTrue(true);
		}
	}

	@Test
	public void testSetSize() {
		try {
			Hotel testHotel= new Hotel("AMERICAN");
			testHotel.setSize(1);
			fail("invalid hotels size");
		} catch (GameException e){
			assertTrue(true);
		}
	}

	@Test
	public void testSetTiles(){
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		try {
			tiles.add(new Tile(1,1,"free",0));
			tiles.add(new Tile(1,1,"free",0));
			Hotel hotel = new Hotel("AMERICAN");
			hotel.setTiles(tiles);
			fail("invalid hotels tiles");
		} catch (GameException e) {
			assertTrue(true);
		}
	}


	@Test
	public void testHasTile() {
		try {
			Tile testTile = new Tile(1,1,"AMERICAN",1);
			Hotel hotel = new Hotel("AMERICAN");
			hotel.AddTile(testTile);
			assertTrue("HasTile Fails",hotel.hasTile(testTile));
		} catch (GameException e) {
			System.out.println(e.getMessage());
			assertTrue(false);
		}
		try {
			Tile testTile = new Tile(1,1,"AMERICAN",1);
			Tile testTile2 = new Tile(1,1,"AMERICAN",0);
			Hotel hotel = new Hotel("AMERICAN");
			hotel.AddTile(testTile);
			assertFalse("HasTile Fails",hotel.hasTile(testTile2));
		} catch (GameException e) {
			System.out.println(e.getMessage());
			assertTrue(false);
		}
	}

	@Test
	public void testGetTileByRowCol() {
		try {
			Tile testTile = new Tile(1,1,"AMERICAN",1);
			Hotel hotel = new Hotel("AMERICAN");
			hotel.AddTile(testTile);
			assertTrue("getTileByRowCol Fails",hotel.getTileByRowCol(testTile.getRow(),testTile.getColumn()).equals(testTile));
		} catch (GameException e) {
			System.out.println(e.getMessage());
			assertTrue(false);
		}
		try {
			Tile testTile = new Tile(1,1,"AMERICAN",1);
			Hotel hotel = new Hotel("AMERICAN");
			hotel.AddTile(testTile);
			assertNull("getTileByRowCol Fails",hotel.getTileByRowCol(testTile.getRow(),testTile.getColumn() + 1));
		} catch (GameException e) {
			System.out.println(e.getMessage());
			assertTrue(false);
		}
	}

	@Test
	public void testHasTileByRowCol() {
		try {
			Tile testTile = new Tile(1,1,"AMERICAN",1);
			Hotel hotel = new Hotel("AMERICAN");
			hotel.AddTile(testTile);
			assertTrue("HasTileByRowCol Fails",hotel.hasTileByRowCol(testTile.getRow(),testTile.getColumn()));
		} catch (GameException e) {
			System.out.println(e.getMessage());
			assertTrue(false);
		}
		try {
			Tile testTile = new Tile(1,1,"AMERICAN",1);
			Hotel hotel = new Hotel("AMERICAN");
			hotel.AddTile(testTile);
			assertFalse("HasTileByRowCol Fails",hotel.hasTileByRowCol(testTile.getRow(),testTile.getColumn() + 1));
		} catch (GameException e) {
			System.out.println(e.getMessage());
			assertTrue(false);
		}
	}

	@Test
	public void testAddTile() {

		try {
			Tile testTile = new Tile(1,1,"free",0);
			Tile testTile2 = new Tile(1,1,"AMERICAN",0);
			Hotel hotel = new Hotel("AMERICAN");
			assertFalse("tile doesnot belong to hotel",hotel.AddTile(testTile));
			assertFalse("tile is not allocated",hotel.AddTile(testTile2));
		} catch (GameException e) {
			System.out.println(e.getMessage());
			assertTrue(false);
		}
	}

}

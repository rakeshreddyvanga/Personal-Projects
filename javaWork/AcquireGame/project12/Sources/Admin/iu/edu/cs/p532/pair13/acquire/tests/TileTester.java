package iu.edu.cs.p532.pair13.acquire.tests;

import static org.junit.Assert.*;
import iu.edu.cs.p532.pair13.acquire.admin.GameParameters;
import iu.edu.cs.p532.pair13.acquire.components.Tile;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;

import org.junit.Test;

public class TileTester {

	@Test
	public void testGetStatus() throws GameException {
		Tile testTile = new Tile(1,1,"free",0);
		testTile.setStatus("free");
		assertEquals("tile.getStatus() method fails","free",testTile.getStatus());
	}

	@Test
	public void testSetStatus() {
		
		try {
			Tile testTile = new Tile(1,1,"free",0);
			testTile.setStatus("a");
			fail("invalid tile status value");
		} catch (GameException e){
			assertTrue(true);
		}
		
	}

	@Test
	public void testGetRow() throws GameException {
		Tile testTile = new Tile(1,1,"free",0);
		testTile.setRow(5);
		assertEquals("tile.getStatus() method fails",5,GameParameters.rows.inverse().get(testTile.getRow()).intValue());
	}

	@Test
	public void testSetRowChar() {
		try {
			Tile testTile = new Tile(1,1,"free",0);
			testTile.setRow('K');
			fail("invalid row value");
		} catch (GameException e){
			assertTrue(true);
		}
	}

	@Test
	public void testSetRowInt() {
		try {
			Tile testTile = new Tile(1,1,"free",0);
			testTile.setRow(11);
			fail("invalid row value");
		} catch (GameException e){
			assertTrue(true);
		}
	}

	@Test
	public void testGetColumn() throws GameException {
		Tile testTile = new Tile(1,1,"free",0);
		testTile.setColumn(5);
		assertEquals("tile.getStatus() method fails",5,testTile.getColumn());
	}

	@Test
	public void testSetColumn() {
		try {
			Tile testTile = new Tile(1,1,"free",0);
			testTile.setColumn(14);
			fail("invalid column value");
		} catch (GameException e){
			assertTrue(true);
		}
	}

	@Test
	public void testGetAllocated() throws GameException {
		Tile testTile = new Tile(1,1,"free",0);
		testTile.setAllocated(1);
		assertEquals("tile.getStatus() method fails",1,testTile.getAllocated());
	}

	@Test
	public void testSetAllocated() {
		try {
			Tile testTile = new Tile(1,1,"free",0);
			testTile.setAllocated(2);
			fail("allocated can hold only 0 or 1");
		} catch (GameException e){
			assertTrue(true);
		}
	}

	@Test
	public void testTileCharIntStringInt() {
		try {
			new Tile('f',1,"free",0);
			fail("invalid values passed to constructor");
		} catch (GameException e){
			assertTrue(true);
		}
	}
	@Test
	public void testTileIntIntStringInt() {
		try {
			new Tile(15,1,"free",0);
			fail("invalid values passed to constructor");
		} catch (GameException e){
			assertTrue(true);
		}
	}

	@Test
	public void testEqualsTile() {
		try {
			Tile t1 = new Tile(1,1,"free",0);
			Tile t2 = new Tile(1,1,"free",0);
			assertTrue("Equals fails", t1.equals(t2));
		} catch (GameException e){
			assertTrue(false);
		}
	}

}

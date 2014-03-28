package iu.edu.cs.p532.pair13.acquire.tests;

import static org.junit.Assert.*;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;
import iu.edu.cs.p532.pair13.acquire.parsers.Parser;

import java.io.BufferedReader;
import java.io.StringReader;
import org.junit.Test;


public class ParserTester {

	@Test
	public void testParseInputRequest() {
		//String XML = null;
		try {
			Parser.parseInputRequest(new BufferedReader(new StringReader("<root></root>")));
		} catch (GameException e){
			assertTrue(true);
		}
		try {
			Parser.parseInputRequest(new BufferedReader(new StringReader("<root></root>")));
		} catch (GameException e) {
			assertTrue(true);
		}
		
	}

	@Test
	public void testValidateRowCol() {
		try {
			assertTrue("I9 is a valid row", Parser.validateRowCol("i "  , " 9"));
			assertTrue("A1 is a valid row", Parser.validateRowCol("a "  , " 1"));
		} catch (GameException e) {
			 assertTrue("Test failed : "+e.getMessage(),false);
		}
		try {
			assertFalse("Q5 is an invalid row", Parser.validateRowCol("Q", "5"));
		} catch (GameException e) {
			 assertTrue(true);
		}
		try {
			assertFalse("A13 is an invalid row", Parser.validateRowCol("a ", "13"));
		} catch (GameException e) {
			 assertTrue(true);
		}
		
	}

}

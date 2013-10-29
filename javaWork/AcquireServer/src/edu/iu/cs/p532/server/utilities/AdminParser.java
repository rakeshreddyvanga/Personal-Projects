package edu.iu.cs.p532.server.utilities;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.iu.cs.p532.server.administrators.Administrator;
import edu.iu.cs.p532.server.administrators.IAdministrator;

public class AdminParser {

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException {
		String inputXML = "";
		int test = 0;
		Administrator adminObj = null;
		AdminParser myParser = new AdminParser();
		Scanner scannerObj = new Scanner(System.in);
		while (true) {
			inputXML = scannerObj.nextLine().trim().replaceAll("\n", "");
			adminObj = new Administrator();
			while (test == 0) {
				String lineBreaker = "";
				lineBreaker = scannerObj.nextLine().trim().replaceAll("\n", "");
				if (lineBreaker.contains("exit"))
					test = 1;
				else
					inputXML = inputXML.concat(lineBreaker);
			}
			scannerObj.close();
			myParser.xmlParse(inputXML, adminObj);
		}
	}

	public void xmlParse(String inputXML, Administrator adminObj){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		try{
		SAXParser parser = factory.newSAXParser();
		xmlHandler handler = new xmlHandler(adminObj);	
		parser.parse(new InputSource(new StringReader(inputXML)), handler);
		} catch (Exception e) {
			System.out.println("<error msg=\"" + e.getMessage() + "\"</error>");
		}
	}
}

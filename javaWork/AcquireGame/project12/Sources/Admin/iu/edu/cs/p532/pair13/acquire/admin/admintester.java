package iu.edu.cs.p532.pair13.acquire.admin;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;
import iu.edu.cs.p532.pair13.acquire.parsers.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;



public class admintester{
	public static void main(String argv[]){
		String XML = null, input = null;
		BufferedReader br = 
				new BufferedReader(new InputStreamReader(System.in));
		try {
			while (true){
				XML=null;
				input=null;
			while((input=br.readLine())!=null && !input.equals("")){
				if (XML == null){
					XML = input;
				}
				else{
					XML = XML + input;
				}	
			}	
			State s = Parser.parseInputRequest(new BufferedReader(new StringReader("<root>" + XML + "</root>")));
			//s.printState();
			System.out.println(s.constructStateElement());
			}
		} catch (IOException io) {
			io.printStackTrace();
		} catch (GameException b){
			String ErrorElement = "<error msg=\""+b.getMessage()+"\" />";
			System.out.println(ErrorElement);
			//b.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
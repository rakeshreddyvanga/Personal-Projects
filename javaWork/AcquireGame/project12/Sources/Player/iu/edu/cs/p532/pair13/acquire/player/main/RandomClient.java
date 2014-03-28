package iu.edu.cs.p532.pair13.acquire.player.main;

import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;
import iu.edu.cs.p532.pair13.acquire.player.playerstrategies.SelectPlayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RandomClient {
	String fromServer = "";
	PrintWriter out = null;
	BufferedReader in = null;
	Socket serverSocket = null;

	public static void main(String[] args) {
		RandomClient connection = new RandomClient();
		connection.initiateGame();
	}

	public boolean initiateGame() {

		try {

			//initial start of the client socket to listen to server socket
			serverSocket = new Socket("localhost", 4444);
			out = new PrintWriter(serverSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					serverSocket.getInputStream()));

			while (true) { // while loop to play till the end of the game
				Boolean startTag = false, endTag = false, done = false;
				String buildAction = "";
				while (true) { // while loop for taking the complete take turn element		

					buildAction = in.readLine();
					/*if (buildAction.contains("End")) {
						System.out.println("Rand1 Ended");
						serverSocket.close();
						done = true; // setting this to exit the outer while loop
						break; // exit from the inner loop
					}*/
					if(buildAction == null){
						System.out.println("Game Ended");
						serverSocket.close();
						done = true; // setting this to exit the outer while loop
						break;
					}
					if (buildAction.contains("<take-turn>"))
						startTag = true; // flag to say that start element of the xml has started

					if (startTag == true) { // reading remaining xml information
						fromServer=fromServer.concat(buildAction);
						if (buildAction.contains("</take-turn>")) // flag for end of the xml input
							endTag = true;
					}

					if (startTag == true && endTag == true) 
						break;								// exit from inner while loop

				}
				if(done == true) // signaling that game has ended.
					break;
				String actionStr = TakeTurn(fromServer); // this method will generate the action string
				fromServer = ""; // resetting the string to empty
				out.println(actionStr); // sending the action string to the server
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	public String TakeTurn(String fromServer) {
		String actionStr = "";
		try {
			// calling the player strategies to select build up a action string
			actionStr = SelectPlayer.takeTurn2(fromServer, "rand1");
		} catch (GameException e) {
			actionStr = "<error msg=\"" + e.getMessage() + "\" />";
			/*
			 * String ErrorElement = "<error msg=\"" + e.getMessage() + "\" />";
			 * System.out.println(ErrorElement); actionStr="Exception";
			 */
		}
		//System.out.println(actionStr);
		return actionStr;
	}
}

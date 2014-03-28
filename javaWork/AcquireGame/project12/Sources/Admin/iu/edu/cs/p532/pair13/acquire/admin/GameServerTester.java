package iu.edu.cs.p532.pair13.acquire.admin;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class GameServerTester {
	private PrintWriter outputStream = null;
	private BufferedReader inputStream = null;
	private ServerSocket server = null;

	private List<Socket> clients = new ArrayList<Socket>();
	private int clientNumber = 0;
	public static int noofPlayers = 4;

	public static void main(String[] args) {

		GameServerTester connection = new GameServerTester();

		connection.initiateGame();

	}

	public boolean initiateGame() {
		int count = 0;
		try {
			server = new ServerSocket(4444);
			while (count < noofPlayers) { // loop to accept players sockets
				clients.add(server.accept());
				count++;
			}

			System.out.println("Connection successful"); // signaling that clients have been added successfully

			/* Start with the game play */
			Game game = new Game();
			String[] players = { "player1", "player2", "player3", "player4" };
			GameResult gResult = game.runGame(players, this); // send a reference of this network server to the game admin
			if(gResult.getPlayerNames().size() == 1)
				System.out.println(gResult.getPlayerNames().get(0) + " cheated!!");
			else
				for(int i =0;i<gResult.getPlayerNames().size();i++)
					System.out.println(gResult.getPlayerNames().get(i) + " " + gResult.getPlayerCash().get(i));
			System.out.println("-------------");
			endGame();
			/* End of the game play */

		} catch (Exception e) {
			System.out.println("Error in initial game setup");
		}
		return true; // everything works well 
	}
	/*
	 * returns action string
	 * calling the appropriate client and generates the action string.
	 */
	public String TakeTurn(String takeTurn) {

		Boolean startTag = false, endTag = false;
		String buildAction = "";
		String actionStr = "";

		//setting up stream readers to send data to clients
		try {
			outputStream = new PrintWriter(clients.get(clientNumber)
					.getOutputStream(), true);
			inputStream = new BufferedReader(new InputStreamReader(clients.get(
					clientNumber).getInputStream()));

			outputStream.println(takeTurn); //sending take turn element to a client

			while (true) { // while loop to receive entire message from client
				buildAction = inputStream.readLine(); // building the action string 
				if (buildAction.contains("<error msg")) {
					//System.out.println(buildAction);
					endGame(); // ending the network and notifying the clients
					return "<error msg"; // ending the game on the server side
				}
				if (buildAction.contains("<action")) // checking for start tag
					startTag = true;

				if (startTag == true) {
					//buildAction = buildAction.replaceAll("\n", "");
					actionStr = actionStr.concat(buildAction); 
					if (buildAction.contains("</action>") // checking for end tag
							|| (!buildAction.contains("<place") && buildAction.contains("/>")))
						endTag = true;
				}

				if (startTag == true && endTag == true) // signal to stop parsing for the cml from client
					break;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		clientNumber++;
		if (clientNumber >= noofPlayers)  // changing the the order of the client sockets
			clientNumber = 0;

		return actionStr;
	}

	public boolean endGame() {
		System.out.println("End of the Game on server side");
		clientNumber = clientNumber - 1 == -1 ? noofPlayers - 1 // getting the appropriate client reference 
				: clientNumber - 1;

		try {
			outputStream.println("End"); // closing the current client and notifying the client about it
			clients.remove(clients.get(clientNumber));
			for (int c = 0; c < clients.size(); c++) { // loop to end remaining client sockets.
				/*outputStream = new PrintWriter(
						clients.get(c).getOutputStream(), true);
				inputStream = new BufferedReader(new InputStreamReader(clients
						.get(c).getInputStream()));
				outputStream.println("End");*/
				clients.get(c).close(); // closing the client sockets.
			}
			// closing the server sockets and stream readers.
			outputStream.close(); 
			inputStream.close();
			System.out.println("Closing the server socket");
			server.close();
		} catch (Exception ioe) {
			ioe.printStackTrace();
			System.out.println("Exception in handling the sockets");
			return false;
		}
		return true;
	}
}

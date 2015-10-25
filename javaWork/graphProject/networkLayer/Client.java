package graphProject.networkLayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private static Scanner input;

	public static void main(String args[]) {
		input = new Scanner(System.in);
		System.out.println("Enter host name: ");
		String hostName = input.nextLine();
		System.out.println("Enter a port number: ");
		int portNumber = Integer.parseInt(input.nextLine());
		//System.out.println(hostName+" : "+ portNumber);
		Socket socket = null;
		String request = "";
		String response = "";
		try{
			socket = new Socket(hostName,portNumber);
			PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
		    BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    
		    while( (response = fromServer.readLine()) != null ){
		    	System.out.println("Server: " + response);
		    	
		    	System.out.println("client:");
		    	request = input.nextLine();
		    	if(request != null) {
		    		toServer.println(request);
		    		if(request.equals("end")) {
		    			socket.close();
		    			break;
		    		}
		    	}
		    }
		}catch(IOException ioException) {
			
		}
	}
	
	
}

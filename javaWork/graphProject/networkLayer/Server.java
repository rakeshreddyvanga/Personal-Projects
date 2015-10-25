package graphProject.networkLayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import graphProject.xml.XMLReader;

public class Server {
	private int portNumber;
	
	public Server(int portNumber){
		this.portNumber = portNumber;
	}
	
	public void listen(){
		XMLReader parser = new XMLReader();
		String request = "";
		String response = "Start";
		ServerSocket serverSocket = null;
		try{
			serverSocket = new ServerSocket(portNumber);
		    Socket clientSocket = serverSocket.accept();
		    PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
		    BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		    toClient.println(response);
		    //Communication with the client
		    while((request = fromClient.readLine()) != null){
		    	if(request.equals("end")) {
		    		serverSocket.close();
		    		break;
		    	}
		    	response = parser.XMLParser(request);
		    	toClient.println(response);
		    }
		}
		catch(IOException ioException) {
			
		}
		finally{
			
		}
		
	}

}

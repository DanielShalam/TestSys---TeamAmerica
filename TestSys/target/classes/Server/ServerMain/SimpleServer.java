package Server.ServerMain;

import Server.ocsf.AbstractServer;
import Server.ocsf.ConnectionToClient;



import java.io.IOException;


public class SimpleServer extends AbstractServer {

	public SimpleServer(int port) {
		super(port);
		
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		System.out.println("Received Message From Client");
		// where is the carrier? said unused
		
		Carrier msgFromClient = null;
		msgFromClient = (Carrier)msg;
		String UserNameFromClient = msgFromClient.getUserName();
		String PassFromClient = msgFromClient.getPass();	
		//String strMsg = msgFromClient.getMessage();
		
		System.out.println("user is " + UserNameFromClient);
		System.out.println("pass is " + PassFromClient);
		

	}
	
	
	@Override
	protected void clientConnected(ConnectionToClient client) {
		super.clientConnected(client);
		System.out.println("Client connected: " + client.getInetAddress());
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("Connecting");
		if (args.length != 1) {
			SimpleServer server = new SimpleServer(3002);
			server.listen();
			//System.out.println("Required argument: <port>");
			// TODO - create a login screen for server where we enter the port
		} else {
			SimpleServer server = new SimpleServer(Integer.parseInt(args[0]));
			server.listen();
		}
	}
	

	
	

}

package il.cshaifasweng.HSTS.server;

import il.cshaifasweng.HSTS.server.ocsf.AbstractServer;
import il.cshaifasweng.HSTS.server.ConnectToDB;
import il.cshaifasweng.HSTS.entities.Carrier;
import il.cshaifasweng.HSTS.entities.CarrierType;
import il.cshaifasweng.HSTS.entities.Question;
import il.cshaifasweng.HSTS.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.*; 

public class SimpleServer extends AbstractServer {

	public ConnectToDB dbConnector;	   
	
	public SimpleServer(int port) {
		super(port);
		this.dbConnector = new ConnectToDB();
		ConnectToDB.connectToDB();
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		System.out.println("Received Message From Client");
		// where is the carrier? said unused
		
		Carrier msgFromClient = null;
		msgFromClient = (Carrier)msg;
		
		switch(msgFromClient.carrierType){
			case USER:
				handleUserMessage(msgFromClient, client);
			
			case QUESTION:
				handleQuestionMessage(msgFromClient, client);
		}
	}
	
	
	//// fucntion to handle Message where Carrier type is USER
	protected void handleUserMessage(Carrier carrier, ConnectionToClient client) {
		String UserNameFromClient = (String) carrier.carrierMessageMap.get("userName");
		String PassFromClient = (String) carrier.carrierMessageMap.get("pass");
		int checkedRole = UserController.getRole(UserNameFromClient, PassFromClient);
		
		System.out.println("checkedRole is " + checkedRole);
		
		Carrier msg2SimpleClient = new Carrier();
		msg2SimpleClient.carrierType = CarrierType.USER;
		msg2SimpleClient.carrierMessageMap.put("userName", "Daniel"); 
		msg2SimpleClient.carrierMessageMap.put("pass", "Alexey");
		msg2SimpleClient.carrierMessageMap.put("role", "1");
		System.out.println("user is " + UserNameFromClient);
		System.out.println("pass is " + PassFromClient);
		try{
			client.sendToClient(msg2SimpleClient);
		}
		catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	//// fucntion to handle Message where Carrier type is QUESTION
	protected void handleQuestionMessage(Carrier carrier, ConnectionToClient client) {
		String msg = (String) carrier.carrierMessageMap.get("message");
		
		switch(msg) {
			case "Get By Id":
				int question_id = (int) carrier.carrierMessageMap.get("id");
				Question question = ServerQuestionController.getQuestionById(question_id);
				
				Carrier msg2SimpleClient = new Carrier();
				msg2SimpleClient.carrierType = CarrierType.QUESTION;
				msg2SimpleClient.carrierMessageMap.put("message", "Get By Id"); 
				msg2SimpleClient.carrierMessageMap.put("Question", question); 
			try {
				client.sendToClient(msg2SimpleClient);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			case "Insert New":
				Question new_question = (Question) carrier.carrierMessageMap.get("Question");
				ServerQuestionController.createBeforeCommit(new_question);
		}
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

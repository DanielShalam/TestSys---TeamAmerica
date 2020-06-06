package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.*; 
import java.io.IOException;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Carrier;


public class SimpleClient extends AbstractClient {
	
	private static SimpleClient client = null;
	
	public boolean isAnswerReturned = false;
	public Carrier answerCarrier = new Carrier();

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		
		System.out.println("Received Message From SimpleServer");
		
		
		Carrier msgFromSimpleServer = null;
		msgFromSimpleServer = (Carrier)msg;
		this.answerCarrier =  msgFromSimpleServer;
		if (this.answerCarrier!= null) {
			System.out.println("key set to true");
			this.isAnswerReturned = true;
		}
		
		
		
		//TODO how to send to logInContoroller
		
	
		
		
		// TODO
	}
	
	
	protected void handleMessageFromLogInController(String userName, String pass) {
		Carrier logInCarrier =  new Carrier();
		logInCarrier.carrierMessageMap.put("userName", userName);
		logInCarrier.carrierMessageMap.put("pass", pass);
	
		
		try {
			this.sendToServer(logInCarrier);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3002);
		}
		return client;
	}
	
	
	
	

}
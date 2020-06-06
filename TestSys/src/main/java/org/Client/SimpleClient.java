package org.TestSys;



import java.util.*; 
import java.io.IOException;

import org.TestSys.ocsf.AbstractClient;

import Server.ServerMain.Carrier;


public class SimpleClient extends AbstractClient {
	
	private static SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		
		System.out.println("Received Message From SimpleServer");
	
		
		Carrier msgFromSimpleServer = null;
		msgFromSimpleServer = (Carrier)msg;
		
		String userReceviedName = msgFromSimpleServer.carrierMessageMap.get("userName");
		String userReceviedPass = msgFromSimpleServer.carrierMessageMap.get("pass");
		String userReceviedRole = msgFromSimpleServer.carrierMessageMap.get("role");
		
		System.out.println("data From SimpleServer :" +  userReceviedName +" "+ userReceviedPass +" "+ userReceviedRole);
		
		
	
		
		
		// TODO
	}
	
	
	protected void handleMessageFromLogInController(String userName, String pass) {
		Carrier logInCarrier =  new Carrier();
		logInCarrier.setUserName(userName);
		logInCarrier.setPass(pass);
		
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

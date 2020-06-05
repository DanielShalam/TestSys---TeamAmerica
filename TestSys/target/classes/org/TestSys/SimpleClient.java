package org.TestSys;



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

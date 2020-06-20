package il.cshaifasweng.HSTS.client;

import java.io.IOException;

import il.cshaifasweng.HSTS.client.ocsf.AbstractClient;
import il.cshaifasweng.HSTS.entities.Carrier;
import il.cshaifasweng.HSTS.entities.CarrierType;
import il.cshaifasweng.HSTS.entities.Question;


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
		System.out.println(answerCarrier);
		if (this.answerCarrier!= null) {
			System.out.println("key set to true");
			this.isAnswerReturned = true;
		}
		
		//TODO how to send to logInContoroller
		// TODO
	}
	
	protected void handleLogOut(int userId) {
		Carrier logoutCarrier =  new Carrier();
		logoutCarrier.carrierType = CarrierType.USER;
		logoutCarrier.carrierMessageMap.put("message", "Log me out");
		logoutCarrier.carrierMessageMap.put("ID", userId);
	
		try {
			this.sendToServer(logoutCarrier);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void handleMessageFromLogInController(String first_name, String pass) {
		Carrier logInCarrier =  new Carrier();
		logInCarrier.carrierType = CarrierType.USER;
		logInCarrier.carrierMessageMap.put("message", "Log me in");
		logInCarrier.carrierMessageMap.put("userName", first_name);
		logInCarrier.carrierMessageMap.put("password", pass);
	
		
		try {
			this.sendToServer(logInCarrier);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void handleMessageFromClientQuestionController(String message, int id, Question question) {
		Carrier questionCarrier =  new Carrier();
		questionCarrier.carrierType = CarrierType.QUESTION;	
		
		if (message == "get all questions") 
		{
			questionCarrier.carrierMessageMap.put("message", "get all questions");
		}
		else if (message == "get all teacher questions")
		{
			questionCarrier.carrierMessageMap.put("teacher", id);
			questionCarrier.carrierMessageMap.put("message", "get all teacher questions");
		}
		else if (message == "create question") 
		{
			questionCarrier.carrierMessageMap.put("question", question);
			questionCarrier.carrierMessageMap.put("message", "create question");
		}
		else if (message == "get all course questions")
		{
			questionCarrier.carrierMessageMap.put("course", id);
			questionCarrier.carrierMessageMap.put("message", "get all course questions");
		}
		else if(message == "delete question")
		{
			questionCarrier.carrierMessageMap.put("question", question);
			questionCarrier.carrierMessageMap.put("message", "delete question");
		}
		
		try {
			this.sendToServer(questionCarrier);
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
	
	@Override
	protected void connectionClosed() {
		// TODO Auto-generated method stub	
		System.out.println("Disconnected from server. ");
		super.connectionClosed();
	}

}
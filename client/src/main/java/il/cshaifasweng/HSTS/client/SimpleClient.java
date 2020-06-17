package il.cshaifasweng.HSTS.client;

import java.util.*; 
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
	
	
	protected void handleMessageFromLogInController(String first_name, String pass) {
		Carrier logInCarrier =  new Carrier();
		logInCarrier.carrierType = CarrierType.USER;
		logInCarrier.carrierMessageMap.put("userName", first_name);
		logInCarrier.carrierMessageMap.put("pass", pass);
	
		
		try {
			this.sendToServer(logInCarrier);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void handleMessageFromClientQuestionController(String messageType, 
			int courseID, int teacherID, Question question) {
		Carrier questionCarrier =  new Carrier();
		questionCarrier.carrierType = CarrierType.QUESTION;	
		
		if (messageType == "get all questions") 
		{
			questionCarrier.carrierMessageMap.put("course", courseID);
			questionCarrier.carrierMessageMap.put("message", "get all questions");
		}
		else if (messageType == "get questions by teacher id")
		{
			questionCarrier.carrierMessageMap.put("teacher", teacherID);
			questionCarrier.carrierMessageMap.put("message", "get questions by teacher id");
		}
		else if (messageType == "create question") 
		{
			questionCarrier.carrierMessageMap.put("question", question);
			questionCarrier.carrierMessageMap.put("message", "create question");
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
	
	
	
	

}
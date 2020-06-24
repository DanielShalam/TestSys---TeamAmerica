package il.cshaifasweng.HSTS.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import il.cshaifasweng.HSTS.client.ocsf.AbstractClient;
import il.cshaifasweng.HSTS.client.ocsf.ObservableSWRClient;
import il.cshaifasweng.HSTS.entities.AddTimeRequest;
import il.cshaifasweng.HSTS.entities.Carrier;
import il.cshaifasweng.HSTS.entities.CarrierType;
import il.cshaifasweng.HSTS.entities.Exam;
import il.cshaifasweng.HSTS.entities.Examination;
import il.cshaifasweng.HSTS.entities.Question;


public class SimpleClient extends ObservableSWRClient {
	
	private static SimpleClient client = null;
	
	private SimpleClient(String host, int port) {
		super(host, port);
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
	
	protected Carrier handleMessageFromLogInController(String first_name, String pass) {
		Carrier logInCarrier =  new Carrier();
		logInCarrier.carrierType = CarrierType.USER;
		logInCarrier.carrierMessageMap.put("message", "Log me in");
		logInCarrier.carrierMessageMap.put("userName", first_name);
		logInCarrier.carrierMessageMap.put("password", pass);

		try {
			logInCarrier = (Carrier) this.sendAndWaitForReply(logInCarrier, logInCarrier);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Message recived from server. ");
		return logInCarrier;
	}
	
	protected Carrier handleMessageFromClientQuestionController(String message, int id, Question question) {
		Carrier questionCarrier =  new Carrier();
		questionCarrier.carrierType = CarrierType.QUESTION;	
		
		if (message.equals("get all questions")) 
		{
			questionCarrier.carrierMessageMap.put("message", "get all questions");
		}
		else if (message.equals("get all teacher questions"))
		{
			questionCarrier.carrierMessageMap.put("teacher", id);
			questionCarrier.carrierMessageMap.put("message", "get all teacher questions");
		}
		else if (message.equals("create question")) 
		{
			questionCarrier.carrierMessageMap.put("question", question);
			questionCarrier.carrierMessageMap.put("ID", id);
			questionCarrier.carrierMessageMap.put("message", "create question");
		}
		else if (message.equals("get all course questions"))
		{
			questionCarrier.carrierMessageMap.put("course", id);
			questionCarrier.carrierMessageMap.put("message", "get all course questions");
		}
		else if(message.equals("delete question"))
		{
			questionCarrier.carrierMessageMap.put("question", question);
			questionCarrier.carrierMessageMap.put("message", "delete question");
		}
		
		try {
			questionCarrier = (Carrier) this.sendAndWaitForReply(questionCarrier, questionCarrier);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("recieved from server");
		return questionCarrier;

	}
	
	protected Carrier handleMessageFromClientExamController(String message, int id, Exam exam) {
		Carrier examCarrier =  new Carrier();
		examCarrier.carrierType = CarrierType.EXAM;	
		
		if (message.equals("get all exams")) 
		{
			examCarrier.carrierMessageMap.put("message", "get all exams");
		}
		else if (message.equals("get all teacher exams"))
		{
			examCarrier.carrierMessageMap.put("teacher", id);
			examCarrier.carrierMessageMap.put("message", "get all teacher exams");
		}
		else if (message.equals("create exam")) 
		{
			examCarrier.carrierMessageMap.put("exam", exam);
			examCarrier.carrierMessageMap.put("message", "create exam");
		}
		else if (message.equals("get all course exams"))
		{
			examCarrier.carrierMessageMap.put("course", id);
			examCarrier.carrierMessageMap.put("message", "get all course exams");
		}
		else if(message.equals("delete exam"))
		{
			examCarrier.carrierMessageMap.put("exam", exam);
			examCarrier.carrierMessageMap.put("message", "delete exam");
		}
		
		try {
			examCarrier = (Carrier) this.sendAndWaitForReply(examCarrier, examCarrier);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return examCarrier;
	}
	
	protected Carrier handleMessageFromClientExaminationController(String message, int id, Examination examination) {
		Carrier examCarrier =  new Carrier();
		examCarrier.carrierType = CarrierType.EXAMINATION;	
		
		if (message.equals("create examination")) {
			examCarrier.carrierMessageMap.put("examination", examination);
			examCarrier.carrierMessageMap.put("message", "create examination");
		} else if (message.equals("get teacher examinations")) {
			examCarrier.carrierMessageMap.put("teacher", id);
			examCarrier.carrierMessageMap.put("message", "get teacher examinations");
		}
		
		try {
			examCarrier = (Carrier) this.sendAndWaitForReply(examCarrier, examCarrier);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return examCarrier;
	}
	
	protected Carrier handleMessageFromClientTimeRequestController(String message, AddTimeRequest timeRequest) {
		Carrier examCarrier =  new Carrier();
		examCarrier.carrierType = CarrierType.TIME_REQUEST;	
		
		if (message.equals("ask for time request")) {
			examCarrier.carrierMessageMap.put("request", timeRequest);
			examCarrier.carrierMessageMap.put("message", "ask for time request");
		}
		
		try {
			examCarrier = (Carrier) this.sendAndWaitForReply(examCarrier, examCarrier);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return examCarrier;
	}
	
	protected Carrier handleMessageFromClientStudentController(String message, int id, Examination examination) {
		Carrier studentCarrier =  new Carrier();
		studentCarrier.carrierType = CarrierType.EXAMINATION;	
		
		if (message.equals("get course examinations")) {
			System.out.println("get course examinations");
			studentCarrier.carrierMessageMap.put("message", message);
			studentCarrier.carrierMessageMap.put("course", id);		// AMIT changed "ID" to "course"
		}
		
		try {
			studentCarrier = (Carrier) this.sendAndWaitForReply(studentCarrier, studentCarrier);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return studentCarrier;
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
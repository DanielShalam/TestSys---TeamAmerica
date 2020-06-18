package il.cshaifasweng.HSTS.server;

import il.cshaifasweng.HSTS.server.ocsf.AbstractServer;
import il.cshaifasweng.HSTS.entities.*;
import il.cshaifasweng.HSTS.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.*;


public class SimpleServer extends AbstractServer {

	public ConnectToDB dbConnector;	   
	
	public SimpleServer(int port) {
		super(port);
		this.dbConnector = new ConnectToDB();
		ConnectToDB.connectToDB();
		
		List <Question> qList = ServerQuestionController.getQuestionsByAtrribute("teacherId", 2);
		try {
			ConnectToDB.printQuestions(qList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		System.out.println("Received Message From Client");
		
		Carrier msgFromClient = null;
		msgFromClient = (Carrier)msg;
		
		switch(msgFromClient.carrierType){
			case USER:
				handleUserMessage(msgFromClient, client);
			
			case QUESTION:
				handleQuestionMessage(msgFromClient, client);
		}
	}
	
	
	//// Function to handle Message where Carrier type is USER
	protected void handleUserMessage(Carrier carrier, ConnectionToClient client) {
		String UserNameFromClient = (String) carrier.carrierMessageMap.get("userName");
		String PassFromClient = (String) carrier.carrierMessageMap.get("pass");
		HashMap<String, Object> checkedRole = UserController.getRole(UserNameFromClient, PassFromClient);
		Role user_role = (Role) checkedRole.get("Role"); 
		System.out.println("checkedRole is " + user_role);
		
		Carrier msg2SimpleClient = new Carrier();
		msg2SimpleClient.carrierType = CarrierType.USER;
		msg2SimpleClient.carrierMessageMap.put("Role", user_role); 
		msg2SimpleClient.carrierMessageMap.put("ID", checkedRole.get("ID"));
		if (user_role == Role.STUDENT || user_role == Role.TEACHER) {
			msg2SimpleClient.carrierMessageMap.put("Courses", checkedRole.get("Courses")); 
		}

		try{
			client.sendToClient(msg2SimpleClient);
		}
		catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	//// Function to handle Message where Carrier type is QUESTION
	protected void handleQuestionMessage(Carrier carrier, ConnectionToClient client) {
		String msg = (String) carrier.carrierMessageMap.get("message");
		
		switch(msg) {
			case "get question by id":
				int question_id = (int) carrier.carrierMessageMap.get("id");
				Question question = ServerQuestionController.getQuestionById(question_id);
				
				Carrier msg2SimpleClient = new Carrier();
				msg2SimpleClient.carrierType = CarrierType.QUESTION;
				msg2SimpleClient.carrierMessageMap.put("message", "return question by id for editing"); 
				msg2SimpleClient.carrierMessageMap.put("question", question); 
				try {
					client.sendToClient(msg2SimpleClient);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			case "create question":
				Question new_question = (Question) carrier.carrierMessageMap.get("question");
				String success = ServerQuestionController.createBeforeCommit(new_question);
				Carrier createNewCarrier = new Carrier();
				createNewCarrier.carrierType = CarrierType.QUESTION;
				createNewCarrier.carrierMessageMap.put("message", success);
				try {
					client.sendToClient(createNewCarrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			case "get all questions":
				List <Question> question_list = ServerQuestionController.getAllQuestions();
				Carrier allQuestionsCarrier = new Carrier();
				allQuestionsCarrier.carrierType = CarrierType.QUESTION;
				allQuestionsCarrier.carrierMessageMap.put("message", "return all questions"); 
				allQuestionsCarrier.carrierMessageMap.put("questions", question_list); 
				try {
					client.sendToClient(allQuestionsCarrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			case "get all teacher questions":
				int teacher_id = (int) carrier.carrierMessageMap.get("teacher");
				List <Question> teacher_questions = ServerQuestionController.getQuestionsByAtrribute("teacherId", teacher_id);
				Carrier teacherQuestionsCarrier = new Carrier();
				teacherQuestionsCarrier.carrierType = CarrierType.QUESTION;
				teacherQuestionsCarrier.carrierMessageMap.put("message", "return all teacher questions"); 
				teacherQuestionsCarrier.carrierMessageMap.put("questions", teacher_questions); 
				try {
					client.sendToClient(teacherQuestionsCarrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			case "get all course questions":
				int course_id = (int) carrier.carrierMessageMap.get("course");
				List <Question> course_questions = ServerQuestionController.getQuestionsByAtrribute("courseId", course_id);
				Carrier courseQuestionsCarrier = new Carrier();
				courseQuestionsCarrier.carrierType = CarrierType.QUESTION;
				courseQuestionsCarrier.carrierMessageMap.put("message", "return all course questions"); 
				courseQuestionsCarrier.carrierMessageMap.put("questions", course_questions); 
				try {
					client.sendToClient(courseQuestionsCarrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			case "delete question":
				int id = (int) carrier.carrierMessageMap.get("id");
				String status = ServerQuestionController.deleteQuestionByID(id);
				Carrier deleteCarrier = new Carrier();
				deleteCarrier.carrierType = CarrierType.QUESTION;
				deleteCarrier.carrierMessageMap.put("message", "delete question status"); 
				deleteCarrier.carrierMessageMap.put("status", status); 
				try {
					client.sendToClient(deleteCarrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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

package il.cshaifasweng.HSTS.server;

import il.cshaifasweng.HSTS.server.ocsf.AbstractServer;
import il.cshaifasweng.HSTS.entities.*;
import il.cshaifasweng.HSTS.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.*;

public class SimpleServer extends AbstractServer {

	public ConnectToDB dbConnector;	   
	private static ArrayList<Integer> connectedUsers = new ArrayList<Integer>();
    private final Object lock = new Object();

	public SimpleServer(int port) {
		super(port);
		this.dbConnector = new ConnectToDB();
		ConnectToDB.connectToDB();
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
				
			case EXAM:
				handleExamMessage(msgFromClient, client);
		}
	}
	
	
	//// Function to handle Message where Carrier type is USER
	protected void handleUserMessage(Carrier carrier, ConnectionToClient client) {
		String message = (String) carrier.carrierMessageMap.get("message");
		
		switch (message) {
		case "Log me out": {
			System.out.println("Logging out");
			int id = (Integer) carrier.carrierMessageMap.get("ID");
	        synchronized (lock) {
				SimpleServer.connectedUsers.remove(Integer.valueOf(id));
	        }
		}
		case "Log me in":
			String UserNameFromClient = (String) carrier.carrierMessageMap.get("userName");
			String PassFromClient = (String) carrier.carrierMessageMap.get("password");
			HashMap<String, Object> checkedRole = UserController.getRole(UserNameFromClient, PassFromClient);
			Role userRole = (Role) checkedRole.get("Role"); 
			int userId = (Integer) checkedRole.get("ID");
	        synchronized (lock) {
				if (SimpleServer.connectedUsers.contains(userId) == true && userRole != Role.INVALID) {
					userRole = Role.INVALID;
					userId = -2;
				}
				else {
					connectedUsers.add(userId);
				}
	        }
			System.out.println("checkedRole is " + userRole);
	
			Carrier msg2SimpleClient = new Carrier();
			msg2SimpleClient.carrierType = CarrierType.USER;
			msg2SimpleClient.carrierMessageMap.put("Role", userRole); 
			msg2SimpleClient.carrierMessageMap.put("ID", userId);
			msg2SimpleClient.carrierMessageMap.put("Courses", checkedRole.get("Courses"));
			try {
				client.sendToClient(msg2SimpleClient);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	//// Function to handle Message where Carrier type is QUESTION
	protected void handleQuestionMessage(Carrier carrier, ConnectionToClient client) {
		String msg = (String) carrier.carrierMessageMap.get("message");
		Carrier responseCarrier = new Carrier();

		switch(msg) {
			case "get question by id":
				int question_id = (int) carrier.carrierMessageMap.get("id");
				Question question = ServerQuestionController.getQuestionById(question_id);

				responseCarrier.carrierType = CarrierType.QUESTION;
				responseCarrier.carrierMessageMap.put("message", "return question by id for editing"); 
				responseCarrier.carrierMessageMap.put("question", question); 
				try {
					client.sendToClient(responseCarrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case "create question":
				Question new_question = (Question) carrier.carrierMessageMap.get("question");
				String status = ServerQuestionController.createBeforeCommit(new_question);

				responseCarrier.carrierType = CarrierType.QUESTION;
				responseCarrier.carrierMessageMap.put("message", "create question status");
				responseCarrier.carrierMessageMap.put("status", status);
				try {
					client.sendToClient(responseCarrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case "get all questions":
				List <Question> question_list = ServerQuestionController.getAllQuestions();

				responseCarrier.carrierType = CarrierType.QUESTION;
				responseCarrier.carrierMessageMap.put("message", "return all questions"); 
				responseCarrier.carrierMessageMap.put("questions", question_list); 
				try {
					client.sendToClient(responseCarrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case "get all teacher questions":
				int teacher_id = (int) carrier.carrierMessageMap.get("teacher");
				List <Question> teacher_questions = ServerQuestionController.getQuestionsByAtrribute("teacherId", teacher_id);

				responseCarrier.carrierType = CarrierType.QUESTION;
				responseCarrier.carrierMessageMap.put("message", "return all teacher questions"); 
				responseCarrier.carrierMessageMap.put("questions", teacher_questions); 
				try {
					client.sendToClient(responseCarrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			
			case "get all course questions":
				int course_id = (int) carrier.carrierMessageMap.get("course");
				List <Question> course_questions = ServerQuestionController.getQuestionsByAtrribute("courseId", course_id);

				responseCarrier.carrierType = CarrierType.QUESTION;
				responseCarrier.carrierMessageMap.put("message", "return all course questions"); 
				responseCarrier.carrierMessageMap.put("questions", course_questions); 
				try {
					client.sendToClient(responseCarrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case "delete question":
				Question question_to_delete = (Question) carrier.carrierMessageMap.get("question");
				String delete_status = ServerQuestionController.deleteQuestion(question_to_delete);

				responseCarrier.carrierType = CarrierType.QUESTION;
				responseCarrier.carrierMessageMap.put("message", "delete question status"); 
				responseCarrier.carrierMessageMap.put("status", delete_status); 
				try {
					client.sendToClient(responseCarrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
		}
		
	}
	
	//// Function to handle Message where Carrier type is EXAM
	protected void handleExamMessage(Carrier carrier, ConnectionToClient client) {
		String msg = (String) carrier.carrierMessageMap.get("message");
		Carrier responseCarrier = new Carrier();
		switch(msg) {
			case "get exam by id":
				int exam_id = (int) carrier.carrierMessageMap.get("id");
				Exam exam = ServerExamsController.getExamById(exam_id);
				
				responseCarrier.carrierType = CarrierType.EXAM;
				responseCarrier.carrierMessageMap.put("message", "return exam by id for editing"); 
				responseCarrier.carrierMessageMap.put("exam", exam); 
				try {
					client.sendToClient(responseCarrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			case "create exam":
				Exam new_exam = (Exam) carrier.carrierMessageMap.get("exam");
				String is_success = ServerExamsController.createBeforeCommit(new_exam);

				responseCarrier.carrierType = CarrierType.EXAM;
				responseCarrier.carrierMessageMap.put("message", "create exam status");
				responseCarrier.carrierMessageMap.put("status", is_success);
				try {
					client.sendToClient(responseCarrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			case "get all exams":
				List <Exam> exam_list = ServerExamsController.getAllExam();

				responseCarrier.carrierType = CarrierType.QUESTION;
				responseCarrier.carrierMessageMap.put("message", "return all questions"); 
				responseCarrier.carrierMessageMap.put("exams", exam_list); 
				try {
					client.sendToClient(responseCarrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			case "get all teacher exams":
				int teacher_id = (int) carrier.carrierMessageMap.get("teacher");
				List <Exam> teacher_exams = ServerExamsController.getExamsByAtrribute("teacherId", teacher_id);

				responseCarrier.carrierType = CarrierType.EXAM;
				responseCarrier.carrierMessageMap.put("message", "return all teacher questions"); 
				responseCarrier.carrierMessageMap.put("exams", teacher_exams); 
				try {
					client.sendToClient(responseCarrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			case "get all course exams":
				int course_id = (int) carrier.carrierMessageMap.get("course");
				List <Exam> course_exams = ServerExamsController.getExamsByAtrribute("courseId", course_id);

				responseCarrier.carrierType = CarrierType.EXAM;
				responseCarrier.carrierMessageMap.put("message", "return all course questions"); 
				responseCarrier.carrierMessageMap.put("exams", course_exams); 
				try {
					client.sendToClient(responseCarrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			case "delete exam":
				int id = (int) carrier.carrierMessageMap.get("id");
				String status = ServerExamsController.deleteExamByID(id);

				responseCarrier.carrierType = CarrierType.EXAM;
				responseCarrier.carrierMessageMap.put("message", "delete exams status"); 
				responseCarrier.carrierMessageMap.put("status", status); 
				try {
					client.sendToClient(responseCarrier);
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
	
	@Override
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		System.out.println("Client Disconnected from server. ");
		super.clientDisconnected(client);
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

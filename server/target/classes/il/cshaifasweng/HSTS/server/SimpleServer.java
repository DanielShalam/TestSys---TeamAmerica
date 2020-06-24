package il.cshaifasweng.HSTS.server;

import il.cshaifasweng.HSTS.server.ocsf.AbstractServer;
import il.cshaifasweng.HSTS.entities.*;
import il.cshaifasweng.HSTS.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
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
				break;
				
			case QUESTION:
				handleQuestionMessage(msgFromClient, client);
				break;

			case EXAM:
				handleExamMessage(msgFromClient, client);
				break;

			case EXAMINATION:
				handleExaminationMessage(msgFromClient, client);
				break;

			case TIME_REQUEST:
				handleTimeRequestMessage(msgFromClient, client);
		}
	}
	
	
	//// Function to handle Message where Carrier type is USER
	protected void handleUserMessage(Carrier carrier, ConnectionToClient client) {
		String message = (String) carrier.carrierMessageMap.get("message");
		List<Carrier> carrier_list = new ArrayList<Carrier>();
		
		switch (message) {
		case "Log me out": {
			System.out.println("Logging out");
			int id = (Integer) carrier.carrierMessageMap.get("ID");
			if (id > 0){
		        synchronized (lock) {
					SimpleServer.connectedUsers.remove(Integer.valueOf(id));
		        }
			}
		}
		
		case "Log me in":
			String UserNameFromClient = (String) carrier.carrierMessageMap.get("userName");
			String PassFromClient = (String) carrier.carrierMessageMap.get("password");
			HashMap<String, Object> checkedRole = UserController.getRole(UserNameFromClient, PassFromClient);
			carrier.carrierMessageMap.clear();
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

			carrier.carrierType = CarrierType.USER;
			carrier.carrierMessageMap.put("message", "Log me in");
			carrier.carrierMessageMap.put("Role", userRole);
			carrier.carrierMessageMap.put("ID", userId);
			carrier.carrierMessageMap.put("fullName", checkedRole.get("fullName"));
			carrier.carrierMessageMap.put("Courses", checkedRole.get("Courses"));
			carrier_list.add(carrier);
			try {
				client.sendToClient(carrier);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	//// Function to handle Message where Carrier type is QUESTION
	protected void handleQuestionMessage(Carrier carrier, ConnectionToClient client) {
		String msg = (String) carrier.carrierMessageMap.get("message");

		switch(msg) {
			case "get question by id":
				int question_id = (int) carrier.carrierMessageMap.get("id");
				Question question = ServerQuestionController.getQuestionById(question_id);
				carrier.carrierMessageMap.clear();

				carrier.carrierType = CarrierType.QUESTION;
				carrier.carrierMessageMap.put("message", msg); 
				carrier.carrierMessageMap.put("question", question); 
				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case "create question":
				Question newQuestion = (Question) carrier.carrierMessageMap.get("question");
				int questionID = (int) carrier.carrierMessageMap.get("ID");
				String status = ServerQuestionController.commitQuestionToDB(newQuestion, questionID);
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);

				carrier.carrierType = CarrierType.QUESTION;
				carrier.carrierMessageMap.put("status", status);
				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case "get all questions":
				List <Question> question_list = ServerQuestionController.getAllQuestions();
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);
				
				carrier.carrierType = CarrierType.QUESTION;
				carrier.carrierMessageMap.put("questions", question_list); 
				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case "get all teacher questions":
				int teacher_id = (int) carrier.carrierMessageMap.get("teacher");
				List <Question> teacher_questions = ServerQuestionController.getQuestionsByAtrribute("teacherId", teacher_id);
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);
				
				carrier.carrierType = CarrierType.QUESTION;
				carrier.carrierMessageMap.put("questions", teacher_questions); 
				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				carrier.carrierMessageMap.put("message", "principle approval"); 
//				carrier.carrierMessageMap.put("duration", Duration.ofMinutes(100000)); 
//				sendToAllClients(carrier);
				break;
			
			case "get all course questions":
				int course_id = (int) carrier.carrierMessageMap.get("course");
				List <Question> course_questions = ServerQuestionController.getQuestionsByAtrribute("courseId", course_id);
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);
				
				carrier.carrierType = CarrierType.QUESTION;
				carrier.carrierMessageMap.put("questions", course_questions); 
				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case "delete question":
				Question question_to_delete = (Question) carrier.carrierMessageMap.get("question");
				String delete_status = ServerQuestionController.deleteQuestion(question_to_delete);
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);
				carrier.carrierType = CarrierType.QUESTION;
				carrier.carrierMessageMap.put("status", delete_status); 
				try {
					client.sendToClient(carrier);
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
		switch(msg) {
			case "get exam by id":
				int exam_id = (int) carrier.carrierMessageMap.get("id");
				Exam exam = ServerExamsController.getExamById(exam_id);
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);
				
				carrier.carrierType = CarrierType.EXAM;
				carrier.carrierMessageMap.put("exam", exam); 
				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case "create exam":
				Exam new_exam = (Exam) carrier.carrierMessageMap.get("exam");
				String is_success = ServerExamsController.commitExamToDB(new_exam);
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);
				carrier.carrierType = CarrierType.EXAM;
				carrier.carrierMessageMap.put("status", is_success);
				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case "get all exams":
				List <Exam> exam_list = ServerExamsController.getAllExam();
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);
				
				carrier.carrierType = CarrierType.EXAM;
				carrier.carrierMessageMap.put("exams", exam_list); 
				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case "get all teacher exams":
				int teacher_id = (int) carrier.carrierMessageMap.get("teacher");
				List <Exam> teacher_exams = ServerExamsController.getExamsByAtrribute("teacherId", teacher_id);
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);
				carrier.carrierType = CarrierType.EXAM;
				carrier.carrierMessageMap.put("exams", teacher_exams); 
				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case "get all course exams":
				int course_id = (int) carrier.carrierMessageMap.get("course");
				List <Exam> course_exams = ServerExamsController.getExamsByAtrribute("courseId", course_id);
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);
				carrier.carrierType = CarrierType.EXAM;
				carrier.carrierMessageMap.put("exams", course_exams); 
				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case "delete exam":
				Exam toDelete = (Exam) carrier.carrierMessageMap.get("exam");
				String status = ServerExamsController.deleteExamByEntity(toDelete);
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);
				carrier.carrierType = CarrierType.EXAM;
				carrier.carrierMessageMap.put("status", status); 
				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

		}
		
	}

	//// Function to handle Message where Carrier type is EXAMINATION
	protected void handleExaminationMessage(Carrier carrier, ConnectionToClient client) {

		String msg = (String) carrier.carrierMessageMap.get("message");
		switch(msg) {
			case "get examination by id":
				int exam_id = (int) carrier.carrierMessageMap.get("id");
				Examination examination = ServerExaminationController.getExaminationById(exam_id);
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);
				carrier.carrierType = CarrierType.EXAMINATION;
				carrier.carrierMessageMap.put("examination", examination); 
				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case "create examination":
				Examination new_exam = (Examination) carrier.carrierMessageMap.get("examination");
				String is_success = ServerExaminationController.commitExaminationToDB(new_exam);
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);
				carrier.carrierType = CarrierType.EXAMINATION;
				carrier.carrierMessageMap.put("status", is_success);
				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case "get teacher examinations":
				int teacher_id = (int) carrier.carrierMessageMap.get("teacher");
				List <Examination> teacher_exams = ServerExaminationController.getExamsinationsByAtrribute("teacherId", teacher_id);
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);
				carrier.carrierType = CarrierType.EXAMINATION;
				carrier.carrierMessageMap.put("examinations", teacher_exams); 
				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			
			case "get course examinations":
				System.out.println("Courses hello");
				//int course_id = 1;
				int course_id = (int) carrier.carrierMessageMap.get("course");
				System.out.println("courses2");
				List <Examination> course_exams = ServerExaminationController.getExamsinationsByAtrribute("courseId", course_id);
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);
				carrier.carrierType = CarrierType.EXAMINATION;
				carrier.carrierMessageMap.put("examinations", course_exams); 
				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			
			case "get examination by execution code":
				int exec_code = (int) carrier.carrierMessageMap.get("exec_code");
				List<Examination> examination_list = ServerExaminationController.getExamsinationsByAtrribute("executionCode", exec_code);
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);		
				
				if (examination_list != null) {
					carrier.carrierMessageMap.put("examination", examination_list.get(0)); }
				else {
					carrier.carrierMessageMap.put("examination", examination_list); }
		
				carrier.carrierType = CarrierType.EXAMINATION;

				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case "delete examination":
				Examination examinationToDelete = (Examination) carrier.carrierMessageMap.get("examination");
				String status = ServerExaminationController.deleteExamByEntity(examinationToDelete);
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);		
				carrier.carrierType = CarrierType.EXAMINATION;
				carrier.carrierMessageMap.put("status", status); 
				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
		
			case "approve time request":
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);		
				carrier.carrierType = CarrierType.EXAMINATION;
				carrier.carrierMessageMap.put("status", "Approved"); 
				sendToAllClients(msg);
				break;
		
			case "start student examination":
				System.out.println("server - start student examination");
				ExaminationStudent exmnStdudent = ServerStudentExaminationController.commitToDB(carrier);	
				
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);		
				carrier.carrierMessageMap.put("exmnStudent",exmnStdudent );
				carrier.carrierType = CarrierType.EXAMINATION;
				
				try {
					
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;		
	
			case "submit student examination":
				
				ServerStudentExaminationController.commitToDB(carrier);	
				carrier.carrierMessageMap.clear();
				carrier.carrierMessageMap.put("message", msg);		
				carrier.carrierType = CarrierType.EXAMINATION;
				
				try {
					client.sendToClient(carrier);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}

		}
	
	//// Function to handle Message where Carrier type is EXAMINATION
	protected void handleTimeRequestMessage(Carrier carrier, ConnectionToClient client) {
		String msg = (String) carrier.carrierMessageMap.get("message");
		switch (msg) {
			case "ask for time request": {
				AddTimeRequest request = (AddTimeRequest) carrier.carrierMessageMap.get("request");
				ServerTimeRequestController.commitRequestToDB(request);
				break;
			}
			
			case "principle answer": {
				AddTimeRequest request = (AddTimeRequest) carrier.carrierMessageMap.get("request");
				// Update by principle answer
				ServerTimeRequestController.setPrincipleAnswer(request);
				break;
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

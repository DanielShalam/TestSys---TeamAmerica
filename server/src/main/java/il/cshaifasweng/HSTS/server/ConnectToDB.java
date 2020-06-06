package il.cshaifasweng.HSTS.server;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import il.cshaifasweng.HSTS.entities.User;
import il.cshaifasweng.HSTS.entities.Question;
import il.cshaifasweng.HSTS.entities.Carrier;
import il.cshaifasweng.HSTS.entities.Exam;
import il.cshaifasweng.HSTS.entities.Role;
import il.cshaifasweng.HSTS.entities.*;



public class ConnectToDB {
	
	private static Session session;
	private static ServiceRegistry serviceRegistry;
	
	private static SessionFactory getSessionFactory() throws HibernateException {
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Question.class);
		configuration.addAnnotatedClass(Exam.class);
		/* TODO - add Entities here: "configuration.addAnnotatedClass..." */
		
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	public static <T> List<T> getAll(Class<T> object) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
		Root<T> rootEntry = criteriaQuery.from(object);
		CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);

		TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);
		return allQuery.getResultList();
	}

	public static void printUsers() throws Exception {

		List<User> usersList = getAll(User.class);
		for (User user : usersList) {
			printUser(user);	
		}
		System.out.format("\n");
	}
	
	public static void printUser(User user) throws Exception {
		System.out.format(
				"User Details:  User Id: %-3s, First name: %-12s  , Surename: %-11s, Password: %-12s\n",
				user.getUserId(), user.getFirstName(), user.getLastname(), user.getPassword());
	}
	
	
	public static void printquestions() throws Exception {
		List<Question> questionsList = getAll(Question.class);
		for (Question question : questionsList) {
			printQuestion(question);			
		}
		System.out.format("\n");
	}
	
	public static void printQuestion(Question question) throws Exception {
		System.out.format("question ID:  %d\t",question.getQuestionId());
		System.out.format("course ID  :  %d\n",question.getCourseId());
		System.out.format("instructions  :  %s\n",question.getInstructions());
		System.out.format("question:  %s\n", question.getQuestion());
		String[] answers = question.getAnswers();
		System.out.format("answer1:  %s  \t",answers[0]);
		System.out.format("answer2:  %s\n",answers[1]);
		System.out.format("answer3:  %s  \t",answers[2]);
		System.out.format("answer4:  %s\n",answers[3]);
		System.out.format("correct answer:  %d\n",question.getCorrectAnswer());
		System.out.format("\n");
	}
	
	
	private static void initData() throws Exception {
		
		// initialize users data
		User user1 = new User("Donald", "Trump", "duckface", Role.STUDENT);
		User user2 = new User("Barak", "Obama", "plonter", Role.TEACHER);
		User user3 = new User("Professor", "X", "wheels", Role.PRINCIPLE);
		User user4 = new User("Rudy", "Giuliani", "theBigApple", Role.TEACHER);
		User user5 = new User("John", "Rockefeller", "stillRichest", Role.STUDENT);
		User user6 = new User("Mayer", "Amschel Rothschild", "conspiracy101", Role.STUDENT); 
		User user7 = new User("Linus", "Torvalds", "Linux", Role.TEACHER);
		User user8 = new User("kyle", "broflovski", "ginGer", Role.STUDENT);
		User user9 = new User("Gregory", "House", "meningitis", Role.STUDENT);
		User user10 = new User("raymond", "reddington", "TheCabal",Role.STUDENT);
		User user11 = new User("Rick", "Sanchez", "MeeSeeks",Role.STUDENT);
		User user12 = new User("Abigail", "Lawnmower", "GrassIsGreen",Role.STUDENT);
		//User user13 = new User("Sonny", "Mythroast", "chickenPie",Role.STUDENT);
		
		session.save(user1);
		session.save(user2);
		session.save(user3);
		session.save(user4);
		session.save(user5);
		session.save(user6);
		session.save(user7);
		session.save(user8);
		session.save(user9);
		session.save(user10);
		session.save(user11);
		session.save(user12);
		//session.save(user13);
		
		session.flush();
		/*
		// initialize questions data
		String[] answers = {"Minecraft","Call of duty","Warms","FIFA"};
		int correct_answer = 1;
		String question = "Which game did Rick and Morty play in the final episode of season 3?";
		String instructions = "Watch rick and mortey";
		Question question_1 = new Question(1, question, answers, instructions, correct_answer,1);
		
		String[] answers2 = {"Pickle","Portal gun","Morites shirt","He doesn't travel through the different universes"};
		correct_answer = 2;
		question = "Which item helps Rick travel through the different universes?";
		instructions = "Watch rick and mortey";
		Question question_2 = new Question(1, question, answers2, instructions, correct_answer,1);
		
		String[] answers3 = {"Green","Black","Yellow","Red"};
		correct_answer = 3;
		Question question_3 = new Question(1, "What is the color of Morties shirt?", answers3, "Watch rick and mortey", correct_answer,1);
		
		
		String[] answers4 = {"Gerry smit","Rick sanchez","Bird man","He don't have a father"};
		correct_answer = 1;
		Question question_4 = new Question(2, "What is the name of Morties father?", answers4, "Watch rick and mortey", correct_answer,1);
		
		String[] answers5 = {"Tomato","Onion","Pickle","Rice"};
		correct_answer = 3;
		Question question_5 = new Question(3, "In Season 3, Episode 3 Rick turn himself into?", answers5, "Watch rick and mortey", correct_answer,1);
		
		String[] answers6 = {"T-999","N-97","C-137","D-142"};
		correct_answer = 3;
		Question question_6 = new Question(4, "What is Rick's \"universe number\"?", answers6, "Watch rick and mortey", correct_answer,1);
		
		session.save(question_1);
		session.save(question_2);
		session.save(question_3);
		session.save(question_4);
		session.save(question_5);
		session.save(question_6);
		
		session.flush();
		*/
	}

	public static void connectToDB() {
		
		try {

			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();

			Carrier carry = new Carrier();
			
			initData();
			session.getTransaction().commit();
			printUsers();
			//printQuestions();
			
		} catch (Exception exception) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			System.err.println("An error occured, changes have been rolled back.");
			exception.printStackTrace();
		} finally {
			if (session != null) {
				StandardServiceRegistryBuilder.destroy(serviceRegistry);
				
				session.getSessionFactory().close();
				session.close();
			}
		}
	}
}

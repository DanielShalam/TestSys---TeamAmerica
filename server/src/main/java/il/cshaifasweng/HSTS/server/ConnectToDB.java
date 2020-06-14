package il.cshaifasweng.HSTS.server;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import il.cshaifasweng.HSTS.entities.*;
import net.bytebuddy.asm.Advice.This;


public class ConnectToDB {
	
	private static Session session;
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
    
	private static SessionFactory getSessionFactory() throws HibernateException {
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Question.class);
		configuration.addAnnotatedClass(Exam.class);
		configuration.addAnnotatedClass(Course.class);
		configuration.addAnnotatedClass(Examination.class);
		configuration.addAnnotatedClass(AddTimeRequest.class);
		/* TODO - add Entities here: "configuration.addAnnotatedClass..." */
		
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}
	
	// Function to get all instances of given class
	public static <T> List<T> getAll(Class<T> object) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
		Root<T> rootEntry = criteriaQuery.from(object);
		CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);
		TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);
		return allQuery.getResultList();
	}
	
	// Function to delete object from database
    public <T> void deleteById(final Class<T> type,int entityId) {
    	// Get object
        T entity = ConnectToDB.getById(type, entityId);
        // Delete object
    	Session temp_session = sessionFactory.openSession();
    	temp_session.delete(entity);
    	temp_session.getTransaction().commit();
    	temp_session.close();
    }
	
	// Function to get object using its class and id
    public static <T> T getById(final Class<T> type, int id){
    	Session temp_Session = sessionFactory.openSession();
		T entity =  temp_Session.get(type, id);
		temp_Session.close();
		return entity;
    }
    
	// Function to get user using its name
    public static User getByUser(String user){
    	Session temp_session = sessionFactory.openSession();
    	User entity =  temp_session.get(User.class, user);
    	temp_session.close();
		return entity;
    }
    
	// Function to insert object into the database
    public static <T> int save(T o){
    	Session temp_session = sessionFactory.openSession();
    	int new_id = (Integer) temp_session.save(o);
    	if (o.getClass() == Question.class) {
    		((Question) o).setQuestionId();
    	}
    	else if (o.getClass() == Exam.class) 
    	{	
			((Exam) o).setExamId();
		}
    	temp_session.getTransaction().commit();
    	temp_session.close();
    	return new_id;
      }
    
    public static <T> List<T> getByAttribute(final Class<T> type, String key, int value)  {
        Session temp_session = ConnectToDB.sessionFactory.openSession();
        temp_session.beginTransaction();
        CriteriaBuilder cb = temp_session.getCriteriaBuilder();

        CriteriaQuery<T> cr = cb.createQuery(type);
        Root<T> root = cr.from(type);
        cr.select(root).where(cb.equal(root.get(key), value));  //here you pass a class field, not a table column (in this example they are called the same)

        Query<T> query = temp_session.createQuery(cr);
        List<T> result = query.getResultList();
        temp_session.close();

        return result;
  }
    

	
	public static void printUsers() throws Exception {
		
		System.out.format("Users list: \n\n");
		List<User> usersList = getAll(User.class);
		for (User user : usersList) {
			printUser(user);	
		}
		System.out.format("\n");
	}
	
	public static void printUser(User user) throws Exception {
		Role role = user.getRole();
		System.out.format(
				"User Details:  User Id: %-3s  First name: %-12s Surename: %-20s  Role: %-10s  Password: %-12s\n",
				user.getUserId(), user.getFirstName(), user.getLastname(),user.getRole(), user.getPassword());
		if (role == Role.STUDENT) {
			System.out.format("Courses studying - ");
			printCourses(user.getCoursesStudying());
		}
		if (role == Role.TEACHER) {
			System.out.format("Courses Teaching - ");
			printCourses(user.getCoursesTeaching());
		}
	}
	
	public static void printCourses(List<Course> coursesList) throws Exception {

		System.out.format("Course list:\n");
		for (Course course : coursesList) {
			printCourse(course);	
		}
		System.out.format("\n");
	}
	
	public static void printCourses() throws Exception {

		System.out.format("\nCourse list:\n\n");
		List<Course> coursesList = getAll(Course.class);
		for (Course course : coursesList) {
			printCourse(course);	
		}
		System.out.format("\n");
	}
	
	public static void printCourse(Course course) throws Exception {
		// course_name , subject_id,  student_list, theacher_id, 
		
		System.out.format("Course name:  %-25s", course.getCourseName());
		System.out.format("course ID  :  %-7s", course.getCourseId());
		System.out.format("Subject ID :  %-7s", course.getSubjectId());
		System.out.format("Teacher ID:   %-7s\n", course.getTeacherId());
	}
	
	public static void printQuestions(List<Question> questionsList) throws Exception {
		System.out.format("\nQuestion list:\n\n");
		for (Question question : questionsList) {
			printQuestion(question);			
		}
		System.out.format("\n");
	}
	
	public static void printQuestions() throws Exception {
		System.out.format("\nQuestion list:\n\n");
		List<Question> questionsList = getAll(Question.class);
		for (Question question : questionsList) {
			printQuestion(question);			
		}
		System.out.format("\n");
	}
	
	public static void printQuestion(Question question) throws Exception {
		System.out.format("Question ID:  %-8s",question.getQuestionId());
		System.out.format("Tourse ID  :  %-5s",question.getCourseId());
		System.out.format("Teacher ID  :  %-5s\n",question.getTeacherId());
		System.out.format("Instructions  :  %s\n",question.getInstructions());
		System.out.format("Question:  %-30s\n", question.getQuestion());
		String[] answers = question.getAnswers();
		System.out.format("answer1:  %-15s",answers[0]);
		System.out.format("answer2:  %-15s\n",answers[1]);
		System.out.format("answer3:  %-15s",answers[2]);
		System.out.format("answer4:  %-15s\n",answers[3]);
		System.out.format("correct answer:  %d\n\n",question.getCorrectAnswer());
	}
	
	
	private static void initData() throws Exception {
		
		// initialize users data	
		User principle = new User("Professor", "X", "wheels", Role.PRINCIPLE);
		
		User teacher_1 = new User("Barak", "Obama", "plonter", Role.TEACHER);
		User teacher_2 = new User("Rudy", "Giuliani", "theBigApple", Role.TEACHER);
		User teacher_3 = new User("Linus", "Torvalds", "Linux", Role.TEACHER);
		
		User student_1 = new User("Donald", "Trump", "duckface", Role.STUDENT);
		User student_2 = new User("John", "Rockefeller", "stillRichest", Role.STUDENT);
		User student_3 = new User("Mayer", "Amschel Rothschild", "conspiracy101", Role.STUDENT); 
		User student_4 = new User("kyle", "broflovski", "ginGer", Role.STUDENT);
		User student_5 = new User("Gregory", "House", "meningitis", Role.STUDENT);
		User student_6 = new User("raymond", "reddington", "TheCabal",Role.STUDENT);
		User student_7 = new User("Rick", "Sanchez", "MeeSeeks",Role.STUDENT);
		User student_8 = new User("Abigail", "Lawnmower", "GrassIsGreen",Role.STUDENT);
		User student_9 = new User("Sonny", "Mythroast", "chickenPie",Role.STUDENT);
		
		session.save(teacher_1);
		session.save(teacher_2);
		session.save(teacher_3);
		session.save(principle);
		session.save(student_1);
		session.save(student_2);
		session.save(student_3);
		session.save(student_4);
		session.save(student_5);
		session.save(student_6);
		session.save(student_7);
		session.save(student_8);
		session.save(student_9);
		
		session.flush();
		
		// initialize courses data
		Course course_1 = new Course("introduction to CS", 1, teacher_3);
		Course course_2 = new Course("Algorithms", 1, teacher_1);
		Course course_3 = new Course("OOP", 2, teacher_3);
		Course course_4 = new Course("Data structures", 2, teacher_2);
		
		session.save(course_1);
		session.save(course_2);
		session.save(course_3);
		session.save(course_4);
		
		session.flush();
		
		// enroll students to courses - with: "public void addCoursesToStudent(Course... courses)"
		student_1.addCoursesToStudent(course_1,course_3);
		student_2.addCoursesToStudent(course_2,course_3);
		student_3.addCoursesToStudent(course_3,course_4);
		student_4.addCoursesToStudent(course_2);
		student_5.addCoursesToStudent(course_1,course_2,course_4);
		student_6.addCoursesToStudent(course_3);
		student_7.addCoursesToStudent(course_4,course_3);
		student_8.addCoursesToStudent(course_1,course_2,course_3,course_4);
		student_9.addCoursesToStudent(course_2,course_3);
		
		session.save(student_1);
		session.save(student_2);
		session.save(student_3);
		session.save(student_4);
		session.save(student_5);
		session.save(student_6);
		session.save(student_7);
		session.save(student_8);
		session.save(student_9);
		
		session.flush();
		
		
		// initialize questions data
		String[] answers = {"Minecraft","Call of duty","Worms","FIFA"};
		int correct_answer = 1;
		String question = "Which game did Rick and Morty play in the final episode of season 3?";
		String instructions = "Watch rick and mortey";
		Question question_1 = new Question(course_1.getCourseId(), question, answers, instructions, 
											correct_answer,teacher_1.getUserId());
		
		String[] answers2 = {"Pickle","Portal gun","Morites shirt","He doesn't travel through the different universes"};
		correct_answer = 2;
		question = "Which item helps Rick travel through the different universes?";
		instructions = "Watch rick and mortey";
		Question question_2 = new Question(course_2.getCourseId(), question, answers2, instructions,
											correct_answer,teacher_1.getUserId());
		
		String[] answers3 = {"Green","Black","Yellow","Red"};
		correct_answer = 3;
		Question question_3 = new Question(course_3.getCourseId(), "What is the color of Morties shirt?", answers3,
											"Watch rick and mortey", correct_answer,teacher_2.getUserId());
		
		String[] answers4 = {"Gerry smit","Rick sanchez","Bird man","He don't have a father"};
		correct_answer = 1;
		Question question_4 = new Question(course_4.getCourseId(), "What is the name of Morties father?", answers4,
											"Watch rick and mortey", correct_answer,teacher_2.getUserId());
		
		String[] answers5 = {"Tomato","Onion","Pickle","Rice"};
		correct_answer = 3;
		Question question_5 = new Question(course_1.getCourseId(), "In Season 3, Episode 3 Rick turn himself into?", 
											answers5, "Watch rick and mortey", correct_answer,teacher_3.getUserId());
		
		String[] answers6 = {"T-999","N-97","C-137","D-142"};
		correct_answer = 3;
		Question question_6 = new Question(course_2.getCourseId(), "What is Rick's \"universe number\"?", answers6, 
											"Watch rick and mortey", correct_answer,teacher_3.getUserId());
		
		session.save(question_1);
		session.save(question_2);
		session.save(question_3);
		session.save(question_4);
		session.save(question_5);
		session.save(question_6);
		
		question_1.setQuestionId();
		question_2.setQuestionId();
		question_3.setQuestionId();
		question_4.setQuestionId();
		question_5.setQuestionId();
		question_6.setQuestionId();
		
		session.flush();
	}


	
	

	public static void connectToDB() {
		
		try {

			ConnectToDB.sessionFactory = getSessionFactory();
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
//				StandardServiceRegistryBuilder.destroy(serviceRegistry);
				
//				ConnectToDB.sessionFactory.close();
				session.close();
			}
		}
	}
}

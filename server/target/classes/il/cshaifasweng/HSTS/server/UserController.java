package il.cshaifasweng.HSTS.server;

import il.cshaifasweng.HSTS.entities.User;
import il.cshaifasweng.HSTS.entities.Course;
import il.cshaifasweng.HSTS.entities.Role;
import il.cshaifasweng.HSTS.server.utilities.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;


public class UserController {
	
	// TODO return role as enum when the client controllers will be ready
	
	public static HashMap<String, Object> getRole(String first_name, String password) {
		List<User> users_list = ConnectToDB.getByAttribute(User.class, "first_name", first_name);
		HashMap<String, Object> hash = new HashMap<String, Object>();
		
		if (users_list == null) {
			hash.put("Role", Role.INVALID);
			hash.put("ID", -1);

			return hash;
		}
		
		String encryptedPW = null;
		try {
			encryptedPW = Hashing.hashing(password);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (User user : users_list) {
			
			if (encryptedPW.equals(user.getPassword())){		// If the password is correct
				// return user.getRole();
				hash.put("Role", user.getRole());
				hash.put("ID", user.getUserId());
				if (user.getRole() == Role.STUDENT) {
					hash.put("Courses", user.getCoursesStudying());
				}
				else if (user.getRole() == Role.TEACHER) {
					HashMap<String, Integer> courses = new HashMap<String, Integer>();
					List <Course> course_list = user.getCoursesTeaching();
					System.out.println("Courses");

					System.out.println(course_list);

					for (Course course: course_list) {
						courses.put(course.getCourseName(), course.getCourseId());
					}
					hash.put("Courses", courses);
				}
				return hash;	
			}
		}	
		
		// return Role.INVALID;	// If the password is incorrect	
		hash.put("Role", Role.INVALID);
		hash.put("ID", -1);
		return hash;
	}
	

}

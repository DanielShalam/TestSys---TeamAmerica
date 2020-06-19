package il.cshaifasweng.HSTS.server;

import il.cshaifasweng.HSTS.entities.User;
import il.cshaifasweng.HSTS.entities.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UserController {
	
	// TODO return role as enum when the client controllers will be ready
	
	public static HashMap<String, Object> getRole(String first_name, String pass) {
		List<User> users_list = ConnectToDB.getByAttribute(User.class, "first_name", first_name);
		HashMap<String, Object> hash = new HashMap<String, Object>();
		
		if (users_list == null) {
			hash.put("Role", Role.INVALID);
			hash.put("ID", -1);

			return hash;
		}
		
		for (User user : users_list) {

			if (pass.equals(user.getPassword())){		// If the password is correct
				// return user.getRole();
				hash.put("Role", user.getRole());
				hash.put("ID", user.getUserId());
				if (user.getRole() == Role.STUDENT) {
					hash.put("Courses", user.getCoursesStudying());
				}
				else if (user.getRole() == Role.TEACHER) {
					//List <String> courses = new ArrayList<String>();
					//System.out.println("Courses");
					//List <Course> course_list = user.getCoursesTeaching();
					//System.out.println("Courses");
					hash.put("Courses", user.getCoursesTeaching());
					
					//System.out.println(user.getCoursesTeaching().isEmpty());
					//hash.put("Courses", courses);
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

package il.cshaifasweng.HSTS.server;

import java.io.IOException;
import il.cshaifasweng.HSTS.entities.User;
import il.cshaifasweng.HSTS.entities.Role;


public class UserController {
	
	// TODO return role as enum when the client controllers will be ready
	
	public static int getRole(String userName, String pass) {
		User user = (User) ConnectToDB.session.get(User.class , userName);
		if (user == null) {		// If the use does not exist
			// return Role.INVALID;
			return 1;
		}
		
		if (user.getPassword() ==  pass) {		// If the password is correct
			// return user.getRole();
			return 1;
			
		}
		
		// return Role.INVALID;	// If the password is incorrect	
		return -1;
	}
	

}

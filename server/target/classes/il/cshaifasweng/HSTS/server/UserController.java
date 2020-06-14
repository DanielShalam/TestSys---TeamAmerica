package il.cshaifasweng.HSTS.server;

import java.io.IOException;
import il.cshaifasweng.HSTS.entities.User;
import il.cshaifasweng.HSTS.entities.Role;


public class UserController {
	
	// TODO return role as enum when the client controllers will be ready
	
	public static Role getRole(String userName, String pass) {
		User user = ConnectToDB.getByUser(userName);
		if (user == null) {		// If the use does not exist
			return Role.INVALID;
		}
		
		if (user.getPassword() ==  pass) {		// If the password is correct
			// return user.getRole();
			return user.getRole();
			
		}
		
		// return Role.INVALID;	// If the password is incorrect	
		return Role.INVALID;
	}
	

}

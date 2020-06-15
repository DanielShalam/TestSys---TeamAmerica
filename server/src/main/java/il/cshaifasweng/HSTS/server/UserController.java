package il.cshaifasweng.HSTS.server;

import il.cshaifasweng.HSTS.entities.User;
import il.cshaifasweng.HSTS.entities.Role;


public class UserController {
	
	// TODO return role as enum when the client controllers will be ready
	
	public static Role getRole(String first_name, String pass) {
		User user = (User) ConnectToDB.getByAttribute(User.class, "first_name", first_name);
		
		if (user == null) {		// If the use does not exist
			return Role.INVALID;
		}
		
		if (pass.equals(user.getPassword())){		// If the password is correct
			// return user.getRole();
			return user.getRole();
			
		}
		
		// return Role.INVALID;	// If the password is incorrect	
		return Role.INVALID;
	}
	

}

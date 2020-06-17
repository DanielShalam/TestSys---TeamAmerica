package il.cshaifasweng.HSTS.server;

import il.cshaifasweng.HSTS.entities.*;

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
				return hash;
				
			}
		}	
		
		// return Role.INVALID;	// If the password is incorrect	
		hash.put("Role", Role.INVALID);
		hash.put("ID", -1);
		return hash;
	}
	

}

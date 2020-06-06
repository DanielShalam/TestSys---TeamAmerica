package il.cshaifasweng.HSTS.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="user_table")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int user_id;		//primary key

	private String first_name;
	private String last_name;
	private Role role;
	private String password;

		
	public User(String firstName, String lastName, String password, Role role) {
		this.first_name = firstName;
		this.last_name = lastName;
		this.password = password;
		this.setRole(role);
	}
	
	public User() {
	}
	
	public int getUserId() {
		return user_id;
	}

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String firstName) {
		this.first_name = firstName;
	}

	public String getLastname() {
		return last_name;
	}

	public void setLastname(String lastName) {
		this.last_name = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	
}	

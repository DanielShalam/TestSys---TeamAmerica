package Server.ServerMain;

import java.io.Serializable;
import java.util.List;



public class Carrier implements Serializable {
   
	private static final long serialVersionUID = 1L;
  //  private Questions question = null;
  // List<Questions> questionList = null;
    private String userName = null;
	private String pass = null;	


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPass() {
		return pass;
	}


	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
	
	/* return true if message is empty */
	public boolean isValid() {
		//return question != null || questionList != null || message != null;
		return true;
	}
}
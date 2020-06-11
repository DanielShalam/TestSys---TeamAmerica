package il.cshaifasweng.HSTS.entities;
import java.util.*; 
import java.io.Serializable;
import java.util.List;



public class Carrier implements Serializable {
   
	private static final long serialVersionUID = 1L;
  //  private Questions question = null;
  // List<Questions> questionList = null;
	
	public CarrierType carrierType;
	public Map<String,Object> carrierMessageMap = new HashMap<String,Object>();
	
	/* return true if message is empty */
	public boolean isValid() {
		//return question != null || questionList != null || message != null;
		return true;
	}
}

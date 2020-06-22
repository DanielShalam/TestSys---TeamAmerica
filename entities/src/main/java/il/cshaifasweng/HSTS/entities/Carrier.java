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
	
	@Override
    public boolean equals(Object o)
    {	
		System.out.println("Override equals");
		Carrier other = (Carrier) o;
        // Would still want to check for null etc. first.
        return this.carrierMessageMap.get("message").equals(other.carrierMessageMap.get("message"));
    }
	
}

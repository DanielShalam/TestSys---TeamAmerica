package il.cshaifasweng.HSTS.server;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import il.cshaifasweng.HSTS.entities.AddTimeRequest;

public class ServerTimeRequestController {
	
	public static void commitRequestToDB(AddTimeRequest request){
		// TODO see if thats enough
		ConnectToDB.save(request);
	}
	
	public static List<AddTimeRequest> getAllTimeRequests(){
		Session session = ConnectToDB.getNewSession();
	    CriteriaBuilder cb = session.getCriteriaBuilder();
	    CriteriaQuery<AddTimeRequest> cq = cb.createQuery(AddTimeRequest.class);
	    Root<AddTimeRequest> rootEntry = cq.from(AddTimeRequest.class);
	    CriteriaQuery<AddTimeRequest> all = cq.select(rootEntry);
	    cq.select(rootEntry).where(cb.equal(rootEntry.get("reviewed"), false));
	    TypedQuery<AddTimeRequest> allQuery = session.createQuery(all);
	    List<AddTimeRequest> requests = allQuery.getResultList();
	    return requests;
	}
	
	public static void setPrincipleAnswer(AddTimeRequest answer){
		ConnectToDB.update(answer);
	}
	
}

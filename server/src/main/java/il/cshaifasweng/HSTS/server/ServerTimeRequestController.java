package il.cshaifasweng.HSTS.server;

import il.cshaifasweng.HSTS.entities.AddTimeRequest;

public class ServerTimeRequestController {
	
	public static void commitRequestToDB(AddTimeRequest request){
		// TODO see if thats enough
		ConnectToDB.save(request);
	}
	
	public static void setPrincipleAnswer(AddTimeRequest answer){
		ConnectToDB.update(answer);
	}
	
}

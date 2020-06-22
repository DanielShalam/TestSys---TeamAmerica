package il.cshaifasweng.HSTS.client;


	import java.time.Duration;

	public class Utils {
		
		 public static String dts(Duration duration) {
			    return duration.toString()
			            .substring(2)
			            .replaceAll("(\\d[HMS])(?!$)", "$1 ")
			            .toLowerCase();
			}
	}

	


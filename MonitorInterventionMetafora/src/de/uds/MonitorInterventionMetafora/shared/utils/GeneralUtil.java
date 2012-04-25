package de.uds.MonitorInterventionMetafora.shared.utils;


public class GeneralUtil {
	
	private static final long MAX_LAG_MESSAGE_MILLIS = 20000;

	public static boolean isTimeRecent(long time) {
		return isTimeRecent(time, MAX_LAG_MESSAGE_MILLIS);
	}
	
	public static boolean isTimeRecent(long time, long delay) {
		long currentTime = System.currentTimeMillis();
		if (currentTime - time > delay){
			return false;
		}
		return true;
	}
	
	public static String getStartOfString(String wholeString){
		String start = wholeString.replaceAll("\n", "");
		 if (start.length() > 30){
			 start = start.substring(0,29);
		 }
		 return start;
	}
	
	public static String getAplicationResourceDirectory(){
		return "";
		//System.getProperty("user.dir")
		//return "";
		//System.out.println("Directory:"+".."+File.separator+"webapps"+File.separator+"MonitorInterventionMetafora"+File.separator);
//		return  ".."+File.separator+"webapps"+File.separator+"MonitorInterventionMetafora"+File.separator;
	}
}

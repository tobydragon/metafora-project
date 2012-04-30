package de.uds.MonitorInterventionMetafora.server.utils;

import java.io.File;


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
		//when running locally
		return "";
		
		//when running direct from tomcat
//		return  ".."+File.separator+"webapps"+File.separator+"MonitorInterventionMetafora"+File.separator;
		
		//when running from lasad script
//		return  ".."+File.separator+".."+File.separator+"apache-tomcat-7.0.8"+File.separator+"webapps"+File.separator+"MonitorInterventionMetafora"+File.separator;

	}
}

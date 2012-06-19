package de.uds.MonitorInterventionMetafora.server.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.uds.MonitorInterventionMetafora.server.InitServlet;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;


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
	
	public static String getStartOfString(String wholeString) {
		String start = wholeString.replaceAll("\n", "");
		 if (start.length() > 30){
			 start = start.substring(0,29);
		 }
		 return start;
	}
	
	public static String getRealPath(String string){
		//return string;
		return InitServlet.getStaticServletContext().getRealPath(string);
	}
	
	public static String getCurrentDate(){
		
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
		Date date = new Date();
		System.out.println("DateFile:"+dateFormat.format(date));
		return dateFormat.format(date);
	}
	
	
}

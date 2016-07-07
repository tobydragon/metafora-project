package de.uds.MonitorInterventionMetafora.server.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.InitServlet;


public class GeneralUtil {
	
	private static Logger log = Logger.getLogger(GeneralUtil.class);
	
	private static final long MAX_LAG_MESSAGE_MILLIS = 2000000;

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
		if (wholeString != null){
			String start = wholeString.replaceAll("\n", "");
			 if (start.length() > 50){
				 start = start.substring(0,49);
			 }
			 return start;
		}
		else {
			return null;
		}
	}
	
	public static String getRealPath(String string){
		//if running a server, get path from the servletContext, otherwise just return default path
		if (InitServlet.getStaticServletContext() != null){
			return InitServlet.getStaticServletContext().getRealPath(string);
		}
		else {
			return "war/"+string;
		}
	}
	
	public static String getCurrentDate(){
		
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
		Date date = new Date();
		System.out.println("DateFile:"+dateFormat.format(date));
		return dateFormat.format(date);
	}
	
	public static String readFileToString(String filename) throws FileNotFoundException{
		Scanner scanner = new Scanner( new File(filename) );
		scanner.useDelimiter("\\A");
		String text = scanner.next();
		scanner.close();
		return text;
	}
	
}

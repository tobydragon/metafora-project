package de.uds.MonitorInterventionMetafora.shared.utils;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class GWTUtils {

	public static String getDate(long input){
		Date date = new Date(input);
		
		 
		
		return DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT).format(date);

	}
	public static String getTime(long input){
		Date date = new Date(input);
		
		
		return DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.TIME_MEDIUM).format(date);


	}	
	
	
	public static String getFullDate(long input){
		Date date = new Date(input);
		
		
		return DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_FULL).format(date);

	}	
	
	public static long getDifferenceInSeconds(long input){
		
		  long diff = getTimeStamp() - input;
		  
		  return diff / 1000;
		  
	}
	
	public static long getDifferenceInMinutes(long input){
		
		  long diff = getTimeStamp() - input;
	
	
		return diff / (60 * 1000);
		  
	}
	
	public static long getDifferenceInHours(long input){
		
		  long diff = getTimeStamp() - input;
		  
		  return diff / (60 * 60 * 1000);
		  
	}
	
	public static long getDifferenceInDays(long input){
		
		  long diff = getTimeStamp() - input;
		  return diff / (24 * 60 * 60 * 1000);
	}
	
	
	public static boolean isNumber(String _value){
		
		
		
		try {
		   Integer.parseInt(_value);
		   return true;
		}
		catch(NumberFormatException nFE) {
		    return false;
		}
		
	
	}

	
	public static long getTimeStamp(){
		
		return System.currentTimeMillis();
		
	}
}

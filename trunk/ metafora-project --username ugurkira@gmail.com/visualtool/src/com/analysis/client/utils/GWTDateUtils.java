package com.analysis.client.utils;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class GWTDateUtils {

	public static String getDate(long input){
		Date date = new Date(input);
		
		 
		
		return DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT).format(date);

	}
	public static String getTime(long input){
		Date date = new Date(input);
		
		
		return DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.TIME_MEDIUM).format(date);


	}	
	
	
}

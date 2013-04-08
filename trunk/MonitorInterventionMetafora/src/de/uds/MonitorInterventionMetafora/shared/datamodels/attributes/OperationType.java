package de.uds.MonitorInterventionMetafora.shared.datamodels.attributes;

import java.util.Arrays;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;

public enum OperationType {
	
	//standard
	EQUALS,
	CONTAINS,
	IS_ONE_OF,
	CONTAINS_ONE_OF,
	IS_NOT,
	DOES_NOT_CONTAIN,
	
	//time
	OCCURED_WITHIN,
	IS_BEFORE,
	IS_AFTER
	;
	
	public boolean isTimeOperation(){
		if (getTimeOperations().contains(this)){
			return true;
		}
		return false;
	}
	
	public  static OperationType getFromString(String str){
		try {
			return valueOf(str);
		}
		catch (Exception e){
			Log.error("[OperationType.getFromString] No operation for type:" + str);
			return null;
		}	
	}
	
	public static List<OperationType> getOperations(OperationsCategory category){
		switch (category) {
		case TIME:
			return getTimeOperations();
		default:
			return getStandardOperations();
		}
	}
	
	public static List<OperationType> getTimeOperations(){
		return Arrays.asList(IS_AFTER, IS_BEFORE, OCCURED_WITHIN);
	}
	
	public static List<OperationType> getStandardOperations(){
		return Arrays.asList(EQUALS, CONTAINS, IS_ONE_OF, CONTAINS_ONE_OF, IS_NOT, DOES_NOT_CONTAIN);
	}

}

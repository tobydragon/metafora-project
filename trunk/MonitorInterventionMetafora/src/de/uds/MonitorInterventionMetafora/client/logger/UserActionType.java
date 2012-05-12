package de.uds.MonitorInterventionMetafora.client.logger;

public enum UserActionType {
	FILTER_ADDED,
	FILTER_REMOVED,
	ACTION_RE_GROUP,
	ACTION_FILTERING,
	TAB_CHANGE,
	STATUS_CHANGE;
	
	public static UserActionType getFromString(String str){
		try {
			return valueOf(str);
		}
		catch (Exception e){
			//System.out.println("[FilterElementType.getFromString] bad string input");
			return null;
		}
	}

}

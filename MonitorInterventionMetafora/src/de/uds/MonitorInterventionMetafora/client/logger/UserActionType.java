package de.uds.MonitorInterventionMetafora.client.logger;

public enum UserActionType {
	FILTER_ADDED,
	FILTER_REMOVED,
	CONFIGURATION_RULE_REMOVED,
	CONFIGURATION_RULE_ADDED,
	ACTION_RE_GROUP,
	AUTO_REFRESH_ENABLED,
	AUTO_REFRESH_DISABLED,
	ACTION_FILTERING,
	TAB_CHANGE,
	STATUS_CHANGE,
	SELECT_ALL_USERS,
	DESELECT_ALL_USERS,
	UPDATE_STUDENT_LIST,
	SEND_FEEDBACK,
	FEEDBACK_TEXT_SELECTION
	;
	
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

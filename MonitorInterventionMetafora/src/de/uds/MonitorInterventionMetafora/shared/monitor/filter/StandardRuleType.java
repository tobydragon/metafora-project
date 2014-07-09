package de.uds.MonitorInterventionMetafora.shared.monitor.filter;

public enum StandardRuleType {
	TIME("Time"),
	ACTION_CLASSIFICATION("Classification"),
	ACTION_TYPE("Type"),
	USER_ID("Users"),
	DESCRIPTION("Description"),
	SENDING_TOOL("Tool"),
	INDICATOR_TYPE("Indicator Type"),
	CHALLENGE_NAME("Challenge Name"),
	GROUP_ID("Group Name"),
	TAGS("Keyword Tags"),
	WORD_COUNT("Word Count"),
	CORRECT("Correct"), 
	
	
	
	;
	
	private String label;
	
	StandardRuleType(String label){
		this.label = label;
	}
	
	public String toString(){
		return label;
	}

}

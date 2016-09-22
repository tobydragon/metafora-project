package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

public enum QuestionType {
	
	ACTIVE_CODE("activecode", "activecode"),
	//these exercise types don't appear in log because log keeps them as plain active code. see issue #14
	ACTIVE_CODE_EXERCISE("actex", "actex"),
	DRAG_AND_DROP("parsonsprob", "parsons"),
	MULT_CHOICE ("mchoice", "mchoice");
	
	String sourceString;
	String logString;
	
	private QuestionType(String sourceString, String logString){
		this.sourceString = sourceString;
		this.logString = logString;
	}
	
	public String getSourceString(){
		return sourceString;
	}
	
	public String getLogString(){
		return logString;
	}
}

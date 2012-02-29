package com.analysis.shared.commonformat;

public class CfActionReport {
	
	String reportString;
	CfAction action;
	
	CfActionReport(CfAction action){
		this.action = action;
		reportString = buildReportString(action);
	}

	private String buildReportString(CfAction action2) {
		return "Student x took action x";
	}

}

package de.uds.MonitorInterventionMetafora.shared.commonformat;

import java.io.Serializable;

public class CfActionReport implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 69858960905211822L;
	String reportString;
	CfAction action;
	public CfActionReport(){
		
	}
	
	public CfActionReport(CfAction action){
		this.action = action;
		reportString = buildReportString(action);
	}


	private String buildReportString(CfAction action2) {
		return "Student x took action x";
	}

}

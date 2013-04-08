package de.uds.MonitorInterventionMetafora.server.cfcommunication;

public enum CommunicationChannelType {
	

	command, analysis, testCommand, testAnalysis;
	
	public String toString(){
		if (this == command){
			return "command-agent-connection";
		}
		else if (this == analysis){
			return "analysis-agent-connection";
		}
		else if (this == testCommand){
			return "test-command-agent-connection";
		}
		else if (this == testAnalysis){
			return "test-analysis-agent-connection";
		}
		else {
			return "error-unknown-channel";
		}
	}

}

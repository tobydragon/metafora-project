package com.analysis.server.xmppmanager;

public enum CommunicationChannelType {
	

	command, analysis;
	
	public String toString(){
		if (this == command){
			return "command-agent-connection";
		}
		else if (this == analysis){
			return "analysis-agent-connection";
		}
		else {
			return "error-unknown-channel";
		}
	}

}

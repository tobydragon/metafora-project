package de.uds.MonitorInterventionMetafora.server.cfcommunication;

public enum CommunicationMethodType {
	

	xmpp, file;
	
	public String toString(){
		if (this == xmpp){
			return "xmpp";
		}
		else if (this == file){
			return "file";
		}
		else {
			return "error-unknown-communication-method";
		}
	}


}

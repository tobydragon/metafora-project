package de.uds.MonitorInterventionMetafora.server.cfcommunication;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public interface CfCommunicationListener {
	
	public void processCfAction(String user, CfAction action);

}

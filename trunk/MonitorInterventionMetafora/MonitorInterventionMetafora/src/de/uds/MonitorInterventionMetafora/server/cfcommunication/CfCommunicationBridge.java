package de.uds.MonitorInterventionMetafora.server.cfcommunication;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public interface CfCommunicationBridge {
	
	public void registerListener(CfCommunicationListener listener);
	public void sendAction(CfAction actionToSend);

}

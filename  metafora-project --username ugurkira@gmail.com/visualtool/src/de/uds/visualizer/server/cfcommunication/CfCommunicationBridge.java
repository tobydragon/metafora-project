package de.uds.visualizer.server.cfcommunication;

import de.uds.visualizer.shared.commonformat.CfAction;

public interface CfCommunicationBridge {
	
	public void registerListener(CfCommunicationListener listener);
	public void sendAction(CfAction actionToSend);

}

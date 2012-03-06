package de.uds.visualizer.server.cfcommunication;

import de.uds.visualizer.shared.commonformat.CfAction;

public interface CfCommunicationListener {
	
	public void processCfAction(String user, CfAction action);

}

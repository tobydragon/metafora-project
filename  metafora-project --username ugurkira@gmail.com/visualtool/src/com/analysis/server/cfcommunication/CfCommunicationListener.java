package com.analysis.server.cfcommunication;

import com.analysis.shared.commonformat.CfAction;

public interface CfCommunicationListener {
	
	public void processCfAction(String user, CfAction action);

}

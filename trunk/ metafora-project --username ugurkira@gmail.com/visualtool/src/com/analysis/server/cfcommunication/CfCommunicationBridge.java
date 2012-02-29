package com.analysis.server.cfcommunication;

import com.analysis.shared.commonformat.CfAction;

public interface CfCommunicationBridge {
	
	public void registerListener(CfCommunicationListener listener);
	public void sendAction(CfAction actionToSend);

}

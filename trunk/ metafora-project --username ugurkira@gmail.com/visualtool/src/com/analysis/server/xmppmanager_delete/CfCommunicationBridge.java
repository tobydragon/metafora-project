package com.analysis.server.xmppmanager_delete;

import com.analysis.shared.communication.objects_old.CfAction;


public interface CfCommunicationBridge {
	
	public void registerListener(CfCommunicationListener listener);
	public void sendAction(CfAction actionToSend);

}

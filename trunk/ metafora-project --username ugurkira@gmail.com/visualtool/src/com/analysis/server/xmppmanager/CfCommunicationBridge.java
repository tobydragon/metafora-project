package com.analysis.server.xmppmanager;

import com.analysis.client.communication.objects.CfAction;


public interface CfCommunicationBridge {
	
	public void registerListener(CfCommunicationListener listener);
	public void sendAction(CfAction actionToSend);

}

package com.analysis.server.xmpp;

public interface XMPPMessageListener {

	public void newMessage(String user, String message, String chat);
	
}

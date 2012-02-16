package com.analysis.server.xmppoldx;

public interface XMPPMessageListener {

	public void newMessage(String user, String message, String chat);
	
}

package de.kuei.metafora.gwt.smack.server.xmpp;

public interface XMPPMessageListener {

	public void newMessage(String user, String message, String chat);
	
}

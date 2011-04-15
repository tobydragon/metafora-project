package de.kuei.metafora.xmpp;

public interface XMPPMessageListener {

	public void newMessage(String user, String message, String chat);
	
}

package de.kuei.metafora.xmpp;

public interface XMPPMessageListenerLanguage {

	public void newMessage(String user, String message, String chat, String language);
	
}

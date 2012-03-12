package de.kuei.metafora.xmpp;

import java.util.Date;

public interface XMPPMessageTimeListenerLanguage {

	public void newMessage(String user, String message, String chat, Date time, String language);
	
}

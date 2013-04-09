package de.kuei.metafora.xmpp;

public interface XMPPPresenceListener {

	public void presenceChanged(String user, String device, String presence);
	
	public void presenceChangedChat(String alias, String chat, String presence);
}

package de.kuei.metafora.xmppbridge.xmpp.interfaces;

import java.util.Collection;

import org.jivesoftware.smack.packet.Presence;

import de.kuei.metafora.xmppbridge.xmpp.ServerConnection;

public interface XMPPRosterListener {
	
	public void entriesAdded(Collection<String> addresses);

	public void entriesDeleted(Collection<String> addresses);

	public void entriesUpdated(Collection<String> addresses);

	public void presenceChanged(Presence presence);
	
	public void connectionChanged(ServerConnection connection);
}

package de.kuei.metafora.xmppbridge.util;

import java.util.Collection;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.packet.Presence;

import de.kuei.metafora.xmppbridge.xmpp.ServerConnection;
import de.kuei.metafora.xmppbridge.xmpp.interfaces.XMPPRosterListener;

public class RosterConsolePrinter implements XMPPRosterListener {

	private ServerConnection connection = null;

	public RosterConsolePrinter(ServerConnection connection) {
		this.connection = connection;
		connection.addRosterListener(this);
		init();
	}

	private void init() {
		System.out.println(connection.getRosterEntryCount()+" Entries in "+connection.getRosterGroupCount()+" Groups");
		for (RosterGroup group : connection.getRosterGroups()) {
			System.out.println(group.getName() + " (" + group.getEntryCount()
					+ "):");
			for (RosterEntry entry : group.getEntries()) {
				System.out.print(entry.getName() + " (" + entry.getUser()
						+ "): ");
				Presence presence = connection.getPresence(entry.getUser());
				System.out.println(presence.getStatus() + " ("
						+ presence.getMode() + ")");
			}
			System.out.println();
		}
	}

	@Override
	public void entriesAdded(Collection<String> users) {
		for (String user : users) {
			RosterEntry entry = connection.getRosterEntry(user);
			System.out.print("New: " + entry.getName() + " (" + entry.getUser()
					+ "): ");
			Presence presence = connection.getPresence(entry.getUser());
			System.out.println(presence.getStatus() + " (" + presence.getMode()
					+ ")");
		}
	}

	@Override
	public void entriesDeleted(Collection<String> users) {
		for (String user : users) {
			RosterEntry entry = connection.getRosterEntry(user);
			System.out.print("Deleted: " + entry.getName() + " ("
					+ entry.getUser() + "): ");
			Presence presence = connection.getPresence(entry.getUser());
			System.out.println(presence.getStatus() + " (" + presence.getMode()
					+ ")");
		}
	}

	@Override
	public void entriesUpdated(Collection<String> users) {
		for (String user : users) {
			RosterEntry entry = connection.getRosterEntry(user);
			System.out.print("Updated: " + entry.getName() + " ("
					+ entry.getUser() + "): ");
			Presence presence = connection.getPresence(entry.getUser());
			System.out.println(presence.getStatus() + " (" + presence.getMode()
					+ ")");
		}
	}

	@Override
	public void presenceChanged(Presence presence) {
		System.out.println("Status update of " + presence.getFrom() + ": "
				+ presence.getStatus() + " (" + presence.getMode() + ")");
	}

	@Override
	public void connectionChanged(ServerConnection connection) {
		init();
	}

}

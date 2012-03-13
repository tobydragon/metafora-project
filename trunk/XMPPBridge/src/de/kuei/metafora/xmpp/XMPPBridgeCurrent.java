package de.kuei.metafora.xmpp;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.Occupant;

public class XMPPBridgeCurrent implements RosterListener {

	private static String SERVER = "metafora.ku-eichstaett.de";
	private static String USER = "KUU0004";
	private static String PASSWORD = "KUU0004";
	private static String ALIAS = null;
	private static String DEVICE = "MetaforaXmppBridge";

	public static final String available = "available";
	public static final String donotdisturb = "do not disturb";
	public static final String freetochat = "free to chat";
	public static final String away = "away";
	public static final String longtimeaway = "long time away";
	public static final String unknown = "unknown";
	public static final String notonline = "not online";

	private static XMPPBridgeCurrent instance = null;

	public static void setDevice(String device) {
		DEVICE = device;
	}

	public static void setAlias(String alias) {
		ALIAS = alias;
	}

	public static void setServer(String server) {
		SERVER = server;
	}

	public static void setUser(String user, String password) {
		USER = user;
		PASSWORD = password;
	}

	public static XMPPBridgeCurrent getInstance() {
		if (instance == null) {
			instance = new XMPPBridgeCurrent();
		}

		return instance;
	}

	private XMPPConnection connection;

	private String server;
	private String user;
	private String password;
	private String alias;
	private String device;

	private PacketCollector collector = null;
	private XMPPMessageDistributor distributor = null;
	private Roster roster;
	private XMPPWatcher watcher;

	private Map<String, MultiUserChat> multiUserChats;
	private Map<String, String> userToName;
	private Map<MultiUserChat, String> chatToAlias;

	public XMPPBridgeCurrent() {

		this.server = XMPPBridgeCurrent.SERVER;
		this.user = XMPPBridgeCurrent.USER;
		this.password = XMPPBridgeCurrent.PASSWORD;
		this.device = XMPPBridgeCurrent.DEVICE;

		if (XMPPBridgeCurrent.ALIAS != null) {
			this.alias = XMPPBridgeCurrent.ALIAS;
		} else {
			this.alias = XMPPBridgeCurrent.USER;
		}

		connection = new XMPPConnection(this.server);

		multiUserChats = Collections
				.synchronizedMap(new HashMap<String, MultiUserChat>());
		userToName = Collections.synchronizedMap(new HashMap<String, String>());
		chatToAlias = Collections
				.synchronizedMap(new HashMap<MultiUserChat, String>());
	}

	public boolean login(boolean register) {
		try {
			connection.connect();
		} catch (XMPPException e1) {
			System.err.println("XMPPBridge.login: connect failed!");
			e1.printStackTrace();
		}

		try {
			connection.login(this.user, this.password, this.device);
		} catch (XMPPException e) {
			if (register) {
				AccountManager manager = connection.getAccountManager();

				try {
					manager.createAccount(user, password);

					connection.disconnect();
					connection = new XMPPConnection(this.server);
					try {
						connection.connect();

						try {
							connection.login(this.user, this.password,
									this.device);
						} catch (XMPPException ex) {
							System.err
									.println("XMPPBridge.login: login after account creation failed!");
							ex.printStackTrace();
						}
					} catch (XMPPException ex) {
						System.err
								.println("XMPPBridge.login: connect after account creation failed!");
						ex.printStackTrace();
					}
				} catch (XMPPException e1) {
					System.err
							.println("XMPPBridge.login: create Account failed!");
					e1.printStackTrace();
				}
			} else {
				System.err.println("XMPPBridge.login: login failed!");
				e.printStackTrace();
			}
		}

		if (connection.isConnected() && connection.isAuthenticated()) {
			collector = connection.createPacketCollector(null);
			if (distributor == null) {
				distributor = new XMPPMessageDistributor(this);
			}
			distributor.start(collector);
			roster = connection.getRoster();
			for (RosterEntry entry : roster.getEntries()) {
				if (!userToName.containsKey(entry.getUser())) {
					userToName.put(entry.getUser(), entry.getName());
					System.out.println("Roster: contact: Name:"
							+ entry.getName() + " User: " + entry.getUser());
				} else {
					System.out.println("Roster: known contact: Name:"
							+ entry.getName() + " User: " + entry.getUser());
				}
			}

			watcher = new XMPPWatcher(this);
			new Thread(watcher).start();

			return true;
		} else {
			System.err.println("XMPPBridge.login: collector not initialized!");
			return false;
		}
	}

	public String getPresenceString(String user) {
		if (roster != null) {
			Presence presence = roster.getPresence(user);
			if (presence != null) {
				if (presence.getType() == Presence.Type.available) {
					if (presence.getMode() == Presence.Mode.available) {
						return XMPPBridgeCurrent.available;
					} else if (presence.getMode() == Presence.Mode.dnd) {
						return XMPPBridgeCurrent.donotdisturb;
					} else if (presence.getMode() == Presence.Mode.away) {
						return XMPPBridgeCurrent.away;
					} else if (presence.getMode() == Presence.Mode.chat) {
						return XMPPBridgeCurrent.freetochat;
					} else if (presence.getMode() == Presence.Mode.xa) {
						return XMPPBridgeCurrent.longtimeaway;
					}
				} else if (presence.getType() == Presence.Type.unavailable) {
					return XMPPBridgeCurrent.notonline;
				}
			}
		} else {
			System.err
					.println("XMPPBridge.getPresenceString: roster not initialized!");
		}
		return XMPPBridgeCurrent.unknown;
	}

	public void shutdown() {
		watcher.stop();
		disconnect();
	}

	public void disconnect() {
		if (distributor != null)
			distributor.stop();

		connection.disconnect();
	}

	public void watcher() {
		if (connection == null) {
			connection = new XMPPConnection(this.server);
		}

		if (!connection.isConnected() || !connection.isAuthenticated()) {
			System.err
					.println("XMPPBridge.watcher: Bridge is not connected! Try to reconnect.");

			shutdown();
			login(false);

			if (connection.isConnected() && connection.isAuthenticated()) {
				System.err
						.println("XMPPBridge.watcher: reconnect successful. Try to restart chats.");
				restartMultiChats();
			} else {
				System.err.println("XMPPBridge.watcher: reconnect failed!");
			}
		}
	}

	private void restartMultiChats() {
		for (String chat : multiUserChats.keySet()) {
			System.err.println("XMPPBridge.restartMultiChats: restart chat "
					+ chat);
			MultiUserChat muc = multiUserChats.get(chat);
			if (muc.isJoined())
				muc.leave();
			connectToChat(chat, null);
		}
	}

	public void connectToChat(String chat, String alias) {
		if (connection.isConnected() && connection.isAuthenticated()
				&& distributor != null) {
			MultiUserChat muc = null;

			if (multiUserChats.get(chat) == null) {
				muc = new MultiUserChat(connection, chat);

				multiUserChats.put(chat, muc);
			} else {
				muc = multiUserChats.get(chat);
			}

			joinMultiUserChat(muc, alias);

		} else {
			System.err
					.println("XMPPBridge.connectToChat: user not logged in or not authorized!");
		}
	}

	private void joinMultiUserChat(MultiUserChat muc, String alias) {
		try {
			if (alias == null) {
				if (chatToAlias.containsKey(muc)) {
					alias = chatToAlias.get(muc);
				} else {
					alias = this.alias;
				}
			} else {
				chatToAlias.put(muc, alias);
			}
			muc.join(alias);
		} catch (XMPPException e) {
			try {
				muc.join(this.alias + System.currentTimeMillis());
			} catch (XMPPException e1) {
				System.err
						.println("XMPPBridge.joinMultiUserChat: multiuserchat could not be joined!");
				e1.printStackTrace();
			}
		}
	}

	public void sendMessageToMultiUserChat(String chat, String message,
			String subject) {
		if (connection.isConnected() && connection.isAuthenticated()) {
			MultiUserChat muc = multiUserChats.get(chat);

			if (muc != null) {
				if (!muc.isJoined()) {
					joinMultiUserChat(muc, null);
				}
				try {
					Message msg = muc.createMessage();
					msg.setBody(message);
					if (subject != null)
						msg.setSubject(subject);
					muc.sendMessage(msg);
				} catch (XMPPException e) {
					System.err
							.println("XMPPBridge.sendMessageToMultiUserChat: message send error!");
					e.printStackTrace();
				}
			} else {
				System.err
						.println("XMPPBridge.sendMessageToMultiUserChat: chat not found!");
			}
		} else {
			System.err
					.println("XMPPBridge.sendMessageToMultiUserChat: user not logged in or not authorized!");
		}
	}

	public void sendMessageToUser(String message, String user, String subject) {
		if (connection.isConnected() && connection.isAuthenticated()) {
			Message msg = new Message();
			msg.setTo(user);
			if (subject != null) {
				msg.setSubject(subject);
				msg.setType(Message.Type.headline);
			}
			msg.setBody(message);

			connection.sendPacket(msg);
		} else {
			System.err
					.println("XMPPBridge.sendMessageToMultiUserChat: user not logged in or not authorized!");
		}
	}

	public XMPPMessageDistributor getDistributor() {
		return distributor;
	}

	@Override
	public void entriesAdded(Collection<String> user) {
		for (String uid : user) {
			RosterEntry entry = roster.getEntry(uid);
			if (userToName.containsKey(entry.getUser())) {
				System.err.println("XMPPBridge.entiesAdded: known user added!");
				userToName.remove(entry.getUser());
			}
			userToName.put(entry.getUser(), entry.getName());
		}
	}

	@Override
	public void entriesDeleted(Collection<String> user) {
		for (String uid : user) {
			RosterEntry entry = roster.getEntry(uid);
			userToName.remove(entry.getUser());
		}
	}

	@Override
	public void entriesUpdated(Collection<String> user) {
		for (String uid : user) {
			RosterEntry entry = roster.getEntry(uid);
			if (userToName.containsKey(entry.getUser())) {
				userToName.remove(entry.getUser());
			}
			userToName.put(entry.getUser(), entry.getName());
		}
	}

	@Override
	public void presenceChanged(Presence arg0) {
		// ignore
	}

	public Set<String> getKnownUsers() {
		return userToName.keySet();
	}

	public String getUserName(String user) {
		if (userToName.containsKey(user))
			return userToName.get(user);
		return "unknown user";
	}

	public Vector<String> getChatParticipants(String chat) {
		MultiUserChat muc = multiUserChats.get(chat);

		Vector<String> participants = new Vector<String>();

		if (muc != null) {
			try {
				Collection<Occupant> parts = muc.getParticipants();
				for (Occupant occ : parts) {
					participants.add(occ.getJid());
				}

			} catch (XMPPException e) {
				System.err
						.println("XMPPBridge.getChatParticipants: XMPP exception!");
				e.printStackTrace();
			}
		}

		return participants;
	}

	public String getRole(String chat, String user) {
		MultiUserChat muc = multiUserChats.get(chat);
		if (muc != null) {
			Occupant occ = muc.getOccupant(user);
			return occ.getRole();
		}
		return "unknown role";
	}

	public String getNick(String chat, String user) {
		MultiUserChat muc = multiUserChats.get(chat);
		if (muc != null) {
			Occupant occ = muc.getOccupant(user);
			return occ.getNick();
		}
		return "unknown name";
	}
}

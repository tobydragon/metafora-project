package de.kuei.metafora.xmpp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;

public class XMPPBridge implements PacketListener, MessageListener {

	public static String SERVER = "metafora.ku-eichstaett.de";

	private static HashMap<String, XMPPBridge> connections = new HashMap<String, XMPPBridge>();
	private static HashMap<String, String[]> parameters = new HashMap<String, String[]>();

	public static XMPPBridge getConnection(String name){
		if (connections.containsKey(name)) {
			return connections.get(name);
		}
		return null;
	}
	
	public static XMPPBridge getTestConnection(){
		try {
			createConnection("test", "testuser", "didPfT", "logger@conference.metafora.ku-eichstaett.de", "Testuser", "Testdevice");
			return getConnection("test");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void createConnection(String connectionname, String user,
			String password, String chatroom, String alias, String device)
			throws IOException {
		if (parameters.containsKey(connectionname)) {
			String[] param = parameters.get(connectionname);
			if (param[0].equals(user) && param[1].equals(password)
					&& param[2].equals(chatroom) && param[3].equals(alias)
					&& param[4].equals(device)) {
				System.err.println("XMPPBridge: The connection "
						+ connectionname + " was already created.");
			} else {
				throw new IOException("XMPPBridge: The connection "
						+ connectionname
						+ " already existed with different paremeters! ");
			}
		} else {
			String[] params = new String[5];
			params[0] = user;
			params[1] = password;
			params[2] = chatroom;
			params[3] = alias;
			params[4] = device;

			parameters.put(connectionname, params);
			connections.put(connectionname, new XMPPBridge(XMPPBridge.SERVER,
					user, password, chatroom, alias, device));
		}
	}

	private Vector<XMPPMessageListener> listeners = null;

	private XMPPConnection connection;
	private MultiUserChat multichat;

	private String server;
	private String user;
	private String password;
	private String chatroom;
	private String alias;
	private String device;

	private XMPPBridge(String server, String user, String password,
			String chatroom, String alias, String device) {

		this.server = server;
		this.user = user;
		this.password = password;
		this.chatroom = chatroom;
		this.alias = alias;
		this.device = device;

		listeners = new Vector<XMPPMessageListener>();
		init();
	}

	public void init() {
		connection = new XMPPConnection(this.server);
	}

	public void registerListener(XMPPMessageListener listener) {
		listeners.add(listener);
		if (listeners.size() == 1) {
			connectToChat();
		}
	}

	public void removeListener(XMPPMessageListener listener) {
		listeners.remove(listener);
		if (listeners.size() == 0) {
			disconnect();
		}

	}

	@Override
	public void processPacket(Packet packet) {
		String name = packet.getFrom().substring(
				packet.getFrom().lastIndexOf('/') + 1);
		String chat = packet.getFrom().substring(0,
				packet.getFrom().lastIndexOf('/') + 1);
		Message message = (Message) packet;
		String text = message.getBody();

		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).newMessage(name, text, chat);
		}
	}

	public void disconnect() {
		multichat.leave();
		connection.disconnect();
	}

	public void connect(boolean register) {
		connect(this.user, this.password, this.device, register);
	}

	public void connect(String user, String password, boolean register) {
		connect(user, password, this.device, register);
	}

	public void connect(String user, String password, String device,
			boolean register) {
		try {
			connection.connect();
			try {
				connection.login(user, password, device);
			} catch (XMPPException e) {
				System.err.println("Error: " + e.getMessage());
				if (register) {
					AccountManager manager = connection.getAccountManager();
					manager.createAccount(user, password);
					connection.login(user, password, device);
				}
			}
		} catch (XMPPException e) {
			System.err.println("XMPP-Error: " + e.getMessage());
		}
	}

	public void connectToChat() {
		connectToChat(this.chatroom);
	}

	public void connectToChat(String chat) {
		if (!connection.isConnected()) {
			connect(true);
		}

		if (connection.isConnected()) {
			if (multichat != null) {
				if (multichat.isJoined()) {
					multichat.leave();
				}
			}

			multichat = new MultiUserChat(connection, chat);
			multichat.addMessageListener(this);

			try {
				multichat.join(this.chatroom);
			} catch (XMPPException e) {
				System.err.println("XMPP-Error: " + e.getMessage());
				System.err.println("XMPP: Retry with other user alias.");
				try {
					multichat.join(this.alias + System.currentTimeMillis());
				} catch (XMPPException ex) {
					System.err.println("XMPP-Error: " + e.getMessage());
				}
			}
		}
	}

	public void sendMessage(String message) {
		if (!connection.isConnected()) {
			connect(true);
		}

		if (connection.isConnected()) {
			if (!multichat.isJoined()) {
				connectToChat();
			}

			if (multichat.isJoined()) {
				try {
					multichat.sendMessage(message);
				} catch (XMPPException e) {
					System.err.println("XMPP-Error: " + e.getMessage());
				}
			}
		}
	}

	public void sendMessageToUser(String message, String user) {
		if (!connection.isConnected()) {
			connect(true);
		}

		if (connection.isConnected()) {
			ChatManager cman = connection.getChatManager();
			Chat chat = cman.createChat(user, this);
			try {
				chat.sendMessage(message);
			} catch (XMPPException e) {
				System.err.println("XMPP-Error: " + e.getMessage());
			}
		}
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		String name = message.getFrom();
		String text = message.getBody();

		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).newMessage(name, text, name);
		}
	}

	public Vector<String> getChatParticipants() {
		Vector<String> users = new Vector<String>();
		Iterator<String> iter = multichat.getOccupants();
		while (iter.hasNext()) {
			users.add(iter.next());
		}
		return users;
	}
}

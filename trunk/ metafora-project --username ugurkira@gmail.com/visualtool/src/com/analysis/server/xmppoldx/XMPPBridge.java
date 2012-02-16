package com.analysis.server.xmppoldx;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
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
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.DelayInfo;
import org.jivesoftware.smackx.packet.DelayInformation;

public class XMPPBridge implements PacketListener, MessageListener {
//	static Logger logger = Logger.getLogger(XMPPBridge.class);	
	
	public static String SERVER = "metafora.ku-eichstaett.de";

	private static HashMap<String, XMPPBridge> connections = new HashMap<String, XMPPBridge>();
	private static HashMap<String, String[]> parameters = new HashMap<String, String[]>();

	public static XMPPBridge getConnection(String name){
		//logger.debug("XMPPBridge: getConnection("+name+")");
		
		if (connections.containsKey(name)) {
		//	logger.debug("XMPPBridge: Connection name is known.");
			return connections.get(name);
		}
		
		//logger.debug("XMPPBridge: Connection name isn't known. Return null.");
		return null;
	}
	
	public static XMPPBridge getTestConnection(){
		
		//logger.debug("XMPPBridge: getTestConnection()");
		
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
		
		//logger.debug("XMPPBridge: createConnection "+connectionname);
		
		if (parameters.containsKey(connectionname)) {
			String[] param = parameters.get(connectionname);
			if (param[0].equals(user) && param[1].equals(password)
					&& param[2].equals(chatroom) && param[3].equals(alias)
					&& param[4].equals(device)) {
				
			//	logger.info("XMPPBridge: The connection "
				//		+ connectionname + " was already created.");
			} else {
				/*logger.error("XMPPBridge: The connection "
						+ connectionname
						+ " already existed with different paremeters! ");*/
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

		//	logger.info("XMPPBrdige: create connection "+connectionname);
			
			parameters.put(connectionname, params);
			connections.put(connectionname, new XMPPBridge(XMPPBridge.SERVER,
					user, password, chatroom, alias, device));
		}
	}

	private Vector<XMPPMessageListener> listeners = null;
	private Vector<XMPPMessageTimeListener> timeListeners = null;

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

	//	logger.debug("XMPPBridge: create new xmpp connection: "+this.server +", "+ this.user +", "+ this.password +", "+ this.chatroom +", "+ this.alias +", "+ this.device);
		
		listeners = new Vector<XMPPMessageListener>();
		timeListeners = new Vector<XMPPMessageTimeListener>();
		init();
	}

	public void init() {
		connection = new XMPPConnection(this.server);
	}

	public void registerListener(XMPPMessageListener listener) {
	//	logger.debug("XMPPBridge: registerListener()");
		
		listeners.add(listener);
		if (listeners.size() == 1) {
			//logger.debug("XMPPBridge: registerListener: first listener -> connectToChat()");
			connectToChat();
		}
	}
	
	public void registerTimeListener(XMPPMessageTimeListener listener) {
		//logger.debug("XMPPBridge: registerTimeListener()");
		
		timeListeners.add(listener);
		if (timeListeners.size() == 1) {
			//logger.debug("XMPPBridge: registerListener: first timelistener -> connectToChat()");
			connectToChat();
		}
	}

	public void removeListener(XMPPMessageListener listener) {
		//logger.debug("XMPPBridge:removeListener()");
		
		listeners.remove(listener);
		if (listeners.size() == 0 && timeListeners.size() == 0) {
			//logger.debug("XMPPBridge: removeListener: last listener -> disconnect()");
			disconnect();
		}
	}
	
	public void removeTimeListener(XMPPMessageTimeListener listener) {
		//logger.debug("XMPPBridge: removeTimeListener()");
		
		timeListeners.remove(listener);
		if (timeListeners.size() == 0 && listeners.size() == 0) {
			//logger.debug("XMPPBridge: removeTimeListener: last listener -> disconnect()");
			disconnect();
		}
	}

	public void processPacket(Packet packet) {
		//logger.debug("XMPPBridge: processPacket()");
		
		String name = packet.getFrom().substring(
				packet.getFrom().lastIndexOf('/') + 1);
		String chat = packet.getFrom().substring(0,
				packet.getFrom().lastIndexOf('/') + 1);
		Message message = (Message) packet;
		String text = message.getBody();

		Date time = new Date();
		Collection<PacketExtension> extensions = packet.getExtensions();
		for(PacketExtension e : extensions){
			if(e instanceof DelayInfo){
				DelayInfo d = (DelayInfo)e;
				time = d.getStamp();
				//logger.debug("XMPPBridge: processPacket: DelayInfo found.");
				break;
			}else if(e instanceof DelayInformation){
				DelayInformation d = (DelayInformation)e;
				time = d.getStamp();
			//	logger.debug("XMPPBridge: processPacket: DelayInformation found.");
				break;
			}
		}
		
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).newMessage(name, text, chat);
		}
		
		for (int i = 0; i < timeListeners.size(); i++) {
			timeListeners.get(i).newMessage(name, text, chat, time);
		}
	}

	public void disconnect() {
	//	logger.debug("XMPPBrdige: disconnect");
		
		multichat.leave();
		connection.disconnect();
	}

	public void connect(boolean register) {
		//logger.debug("XMPPBrdige: connect("+register+")");
		
		connect(this.user, this.password, this.device, register);
	}

	public void connect(String user, String password, boolean register) {
		//logger.debug("XMPPBrdige: connect("+user+",..., "+register+")");
		
		connect(user, password, this.device, register);
	}

	public void connect(String user, String password, String device,
			boolean register) {
		//logger.debug("XMPPBrdige: connect("+user+",..., "+register+") (2)");
		
		try {
			connection.connect();
			try {
				connection.login(user, password, device);
			} catch (XMPPException e) {
			//	logger.debug("XMPPBrdige: connect: "+e.getMessage());
				if (register) {
					try{
						AccountManager manager = connection.getAccountManager();
						manager.createAccount(user, password);
					
						connection.login(user, password, device);
					}catch(XMPPException ex){
				//		logger.error("XMPPBrdige: connect: create new account: "+e.getMessage());
					}
				}else{
					//logger.error("XMPPBrdige: connect: "+e.getMessage());
				}
			}
		} catch (XMPPException e) {
			//logger.error("XMPPBrdige: connect: "+e.getMessage()+" (3)");
		}
	}

	public void connectToChat() {
		//logger.debug("XMPPBridge: connectToChat()");
		
		connectToChat(this.chatroom);
	}

	public void connectToChat(String chat) {
		//logger.debug("XMPPBridge: connectToChat("+chat+")");
		
		if (!connection.isConnected()) {
			//logger.debug("XMPPBridge: connectToChat: -> connect(true)");
			connect(true);
		}

		if (connection.isConnected()) {
			if (multichat != null) {
				if (multichat.isJoined()) {
				//	logger.debug("XMPPBridge: connectToChat: leave chat");
					multichat.leave();
				}
			}

			multichat = new MultiUserChat(connection, chat);
			multichat.addMessageListener(this);

			try {
				//logger.debug("XMPPBridge: connectToChat: Join chat as "+this.alias);
				multichat.join(this.alias);
			} catch (XMPPException e) {
				//logger.debug("XMPPBridge: connectToChat: "+e.getMessage()+" -> Retry with other user alias.");
				try {
					this.alias = this.alias + System.currentTimeMillis();
					
					//logger.debug("XMPPBridge: connectToChat: Try with "+this.alias);
					
					multichat.join(this.alias);
				} catch (XMPPException ex) {
					//logger.error(e.getMessage());
					//logger.debug(e.getStackTrace().toString());
				}
			}
		}
	}

	public void sendMessage(String message) {
		//logger.debug("XMPPBridge: sendMessage("+message+")");
		
		if (connection != null){
			if (!connection.isConnected()) {
			//	logger.debug("XMPPBridge: sendMessage: -> connect(true)");
				connect(true);
			}
	
			if (connection.isConnected()) {
				if (multichat == null || !multichat.isJoined()) {
				//	logger.debug("XMPPBridge: sendMessage: -> connectToChat()");
					connectToChat();
				}
	
				if (multichat.isJoined()) {
					try {
						multichat.sendMessage(message);
					} catch (XMPPException e) {
					//	logger.error(e.getMessage());
						//logger.debug(e.getStackTrace().toString());
					}
				}
				else {
					//logger.error("MultiChat is null");
				}
			}
			else {
				//logger.error("Connection is null");
			}
		}
	}

	public void sendMessageToUser(String message, String user) {
		//logger.debug("XMPPBridge: sendMessageToUser("+message+","+user+")");
		
		if (!connection.isConnected()) {
			//logger.debug("XMPPBridge: sendMessageToUser: -> connect(true)");
			connect(true);
		}

		if (connection.isConnected()) {
			ChatManager cman = connection.getChatManager();
			Chat chat = cman.createChat(user, this);
			try {
				chat.sendMessage(message);
			} catch (XMPPException e) {
				//logger.error(e.getMessage());
				//logger.debug(e.getStackTrace().toString());
			}
		}
	}

	
	public void processMessage(Chat chat, Message message) {
		//logger.debug("XMPPBridge: processMessage()");
		
		String name = message.getFrom();
		String text = message.getBody();

		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).newMessage(name, text, name);
		}
	}

	public Vector<String> getChatParticipants() {
		//logger.debug("XMPPBridge: getChatParticipants()");
		
		Vector<String> users = new Vector<String>();
		Iterator<String> iter = multichat.getOccupants();
		while (iter.hasNext()) {
			users.add(iter.next());
		}
		return users;
	}
}

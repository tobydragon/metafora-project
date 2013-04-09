package de.kuei.metafora.xmppbridge.xmpp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smackx.muc.InvitationListener;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.RoomInfo;

import de.kuei.metafora.xmppbridge.xmpp.interfaces.XMPPRosterListener;
import de.kuei.metafora.xmppbridge.xmpp.util.NotNullMessageFilter;
import de.kuei.metafora.xmppbridge.xmpp.util.PacketListenerFilter;

/**
 * This class handles the connection with the xmpp server.
 * 
 * @author thomas
 * 
 */
public class ServerConnection implements ConnectionListener,
		ChatManagerListener, RosterListener, InvitationListener {
	// log4j
	private static final Logger log = Logger.getLogger(ServerConnection.class);

	/**
	 * default xmpp server to use
	 */
	public static final String defaultServer = "metaforaserver.ku.de";
	/**
	 * default port to use
	 */
	public static final int defaultPort = 5222;

	/**
	 * server for multi user chats
	 */
	private String mucserver = null;

	/**
	 * xmpp user name
	 */
	private String user = null;
	/**
	 * xmpp password
	 */
	private String password = null;
	/**
	 * xmpp device string
	 */
	private String device = null;

	/**
	 * flag if connection was already connected
	 */
	private boolean connected = false;
	/**
	 * flag if connection was already authenticated
	 */
	private boolean authenticated = false;

	/**
	 * If this flag is true invitations to xmpp multi user chats will be
	 * accepted automatically.
	 */
	private boolean acceptInvitation = true;

	/**
	 * Smack xmpp connection configuration
	 */
	private ConnectionConfiguration config = null;
	/**
	 * Smack xmpp connection
	 */
	private Connection connection = null;
	/**
	 * Chat manager of the smack connection
	 */
	private ChatManager chatmanager = null;
	/**
	 * Roster of the smack connection
	 */
	private Roster roster = null;

	/**
	 * Vector for our xmpp roster listeners
	 */
	private Vector<XMPPRosterListener> listeners;
	/**
	 * Vector for our xmpp packet listeners
	 */
	private Vector<PacketListenerFilter> packetListeners;

	/**
	 * reference to connection watchdog
	 */
	private WatchDog watchdog = null;

	/**
	 * This method prepares a new xmpp connection to the default server with the
	 * given user and device string-
	 * 
	 * @param user
	 *            xmpp user name
	 * @param password
	 *            xmpp password
	 * @param device
	 *            xmpp device string
	 * @throws Exception
	 */
	public ServerConnection(String user, String password, String device)
			throws Exception {
		this(ServerConnection.defaultServer, user, password, device);
	}

	/**
	 * This method prepares a new xmpp connection to the given server with the
	 * given user and device string.
	 * 
	 * @param server
	 *            xmpp server
	 * @param user
	 *            xmpp user
	 * @param password
	 *            xmpp password
	 * @param device
	 *            xmpp device string
	 * @throws Exception
	 */
	public ServerConnection(String server, String user, String password,
			String device) throws Exception {
		this(server, user, password, device, ServerConnection.defaultPort);
	}

	/**
	 * This method prepares a new xmpp connection to the given server at the
	 * given port with the given user and device string.
	 * 
	 * @param server
	 *            xmpp server
	 * @param user
	 *            xmpp user
	 * @param password
	 *            xmpp users password
	 * @param device
	 *            xmpp device string
	 * @param port
	 *            xmpp server port
	 * @throws Exception
	 */
	public ServerConnection(String server, String user, String password,
			String device, int port) throws Exception {
		this.user = user;
		this.password = password;
		this.device = device;

		this.mucserver = "conference." + server;

		ServerConnectionManager.getInstance().addServerConnection(this, server,
				user, device);

		listeners = new Vector<XMPPRosterListener>();
		packetListeners = new Vector<PacketListenerFilter>();

		log.debug("New server connection: " + server);

		initConfiguration(server, port);
		setupConnection();
	}

	/**
	 * This method returns the current connection state.
	 * 
	 * @return true if connected
	 */
	public boolean isConnected() {
		return connection.isConnected();
	}

	/**
	 * This method returns the current login state.
	 * 
	 * @return true if user is logged in
	 */
	public boolean isAuthenticated() {
		return connection.isAuthenticated();
	}

	/**
	 * This method close the connection an reconnects to the server.
	 * 
	 */
	protected void refreshConnection() {
		log.warn("Refreshing connection to server" + getServer() + " for user "
				+ getUser() + "!");

		connection.disconnect();

		setupConnection();
		restoreConnectionState();
	}

	/**
	 * This method prepares the xmpp connection.
	 * 
	 * @param server
	 *            xmpp server to connect to
	 * @param port
	 *            xmpp server port
	 */
	private void initConfiguration(String server, int port) {
		// Create the configuration for this new connection
		config = new ConnectionConfiguration(server, port);
		// enable compression
		config.setCompressionEnabled(true);
		// set debug mode
		config.setDebuggerEnabled(false);
		// TLS required
		config.setSASLAuthenticationEnabled(true);
		// do not check if certificates are outdated
		config.setExpiredCertificatesCheckEnabled(false);
		// watchdog manages reconnection
		config.setReconnectionAllowed(false);
		// allow self signed certificates
		config.setSelfSignedCertificateEnabled(true);
		// send presence
		config.setSendPresence(true);
		// use ssl encryption
		config.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
	}

	/**
	 * This method prepares the connection to the xmpp server.
	 */
	private void setupConnection() {
		// disconnect if there already is a connection
		if (connection != null && connection.isConnected()) {
			connection.disconnect();
		}

		connection = new XMPPConnection(config);

		chatmanager = connection.getChatManager();
		chatmanager.addChatListener(this);

		roster = connection.getRoster();
		roster.addRosterListener(this);
		roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);

		for (PacketListenerFilter listener : packetListeners) {
			connection.addPacketListener(listener.getListener(),
					listener.getFilter());
		}
	}

	/**
	 * This method restores the connection state. This means it will reconnect
	 * if the connection was already connected and relogin if the user was
	 * already logged in.
	 */
	private void restoreConnectionState() {
		log.info("Restoring connection state.");
		if (connected) {
			log.info("Reconnect to server.");
			connect();
		}
		if (connection.isConnected() && authenticated) {
			log.info("Login to server again.");
			login();
		}

		for (XMPPRosterListener listener : listeners) {
			listener.connectionChanged(this);
		}

		if (connection.isConnected() && connection.isAuthenticated()) {
			XmppMUCManager.getInstance().reconnectToMUCs(this);
		}
	}

	/**
	 * This method starts the connection with the xmpp server.
	 */
	public void connect() {
		try {
			connection.connect();
			connection.addConnectionListener(this);

			try {
				MultiUserChat.addInvitationListener(connection, this);
			} catch (Exception e) {
				log.warn(e.getMessage(), e);
			}

			connected = true;

		} catch (XMPPException e) {
			log.error("Connection to XMPP server " + connection.getHost()
					+ " failed.", e);
			log.debug("Error message: " + e.getMessage());
			log.debug("Localized error message: " + e.getLocalizedMessage());
			log.debug("XMPP error: " + e.getXMPPError());
			log.debug("Stream error: " + e.getStreamError());
		}
	}

	/**
	 * This method stops the connection and resets the connected and
	 * authenticated flags.
	 */
	public void disconnect() {
		if (watchdog != null)
			watchdog.stop();

		connected = false;
		authenticated = false;

		connection.disconnect();
	}

	/**
	 * This method logs the current user into the server.
	 */
	public void login() {
		// connect to server if not connected
		if (!connection.isConnected()) {
			log.info("Start connection to server.");
			connect();
		}

		// try to log in user
		try {
			connection.login(user, password, device);
		} catch (Exception e) {
			log.error("Login on " + connection.getHost() + " failed.", e);
			log.debug("Error message: " + e.getMessage());
			log.debug("Localized error message: " + e.getLocalizedMessage());
			if (e instanceof XMPPException) {
				XMPPException xe = (XMPPException) e;
				log.debug("XMPP error: " + xe.getXMPPError());
				log.debug("Stream error: " + xe.getStreamError());
			}

			// try to create a account for the user if login fails
			AccountManager manager = connection.getAccountManager();
			if (e.getMessage().contains("not-authorized")
					&& manager.supportsAccountCreation()) {

				try {
					log.info("Start creating of new account for " + user + ".");
					manager.createAccount(user, password);

					// after account creation a reset of the connection is
					// necessary to login
					disconnect();
					connect();

					try {
						log.info("Try to login new user " + user + ".");
						connection.login(user, password, device);
					} catch (XMPPException exception) {
						log.error("Login on " + connection.getHost()
								+ " failed.", exception);
						log.debug("Error message: " + exception.getMessage());
						log.debug("Localized error message: "
								+ exception.getLocalizedMessage());
						log.debug("XMPP error: " + exception.getXMPPError());
						log.debug("Stream error: " + exception.getStreamError());
					}

				} catch (XMPPException exception) {
					log.error("Create account on " + connection.getHost()
							+ " failed.", exception);
					log.debug("Error message: " + exception.getMessage());
					log.debug("Localized error message: "
							+ exception.getLocalizedMessage());
					log.debug("XMPP error: " + exception.getXMPPError());
					log.debug("Stream error: " + exception.getStreamError());
				}
			}
		}

		authenticated = connection.isAuthenticated();

		log.info("Logged in to " + connection.getHost() + " as "
				+ connection.getUser());
		log.info(connection.getHost() + " is connected: "
				+ connection.isConnected());
		log.info(connection.getHost() + " is secure connection: "
				+ connection.isSecureConnection());
		log.info(connection.getHost() + " is authenticated: "
				+ connection.isAuthenticated());
		log.info(connection.getHost() + " is using compression: "
				+ connection.isUsingCompression());

		if (watchdog == null || !watchdog.isRunning()) {
			if (watchdog == null) {
				watchdog = new WatchDog(this);
			} else {
				watchdog.reset();
			}

			log.debug("Start watchdog.");

			Thread t = new Thread(watchdog);
			t.start();
		}

		String host = "unknown";
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			log.debug("Unknown host!", e);
		}

		String user = System.getProperty("user.name");

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		String time = calendar.get(GregorianCalendar.DAY_OF_MONTH) + "."
				+ (calendar.get(GregorianCalendar.MONTH) + 1) + "."
				+ calendar.get(GregorianCalendar.YEAR) + " "
				+ calendar.get(GregorianCalendar.HOUR_OF_DAY) + ":"
				+ calendar.get(GregorianCalendar.MINUTE);

		Presence presence = new Presence(Presence.Type.available);
		presence.setStatus(user + " from " + host + " (" + time + ")");
		connection.sendPacket(presence);

	}

	/**
	 * This method updates username and password and logs the user into the
	 * server.
	 * 
	 * @param user
	 *            xmpp user name
	 * @param password
	 *            users password
	 */
	public void login(String user, String password) {
		setUser(user);
		setPassword(password);
		login();
	}

	/**
	 * This method returns the current state of the xmpp debugger.
	 * 
	 * @return true if debug is enabled
	 */
	public boolean isDebugerEnabled() {
		return config.isDebuggerEnabled();
	}

	/**
	 * This method enables the xmpp debugger for this connection.
	 * 
	 * @param debugXmpp
	 *            true for enable debugging
	 */
	public void setDebuggerEnabled(boolean debugXmpp) {
		log.debug("Set debugger enabled: " + debugXmpp);
		if (config.isDebuggerEnabled() != debugXmpp) {
			if (connection.isConnected()) {
				log.info("Disconnect form server for reconfiguration.");
				disconnect();
			}

			config.setDebuggerEnabled(debugXmpp);
			setupConnection();

			restoreConnectionState();
		}
	}

	/**
	 * This method returns the compression state of the connection.
	 * 
	 * @return true if compression enabled
	 */
	public boolean isUsingCompression() {
		return connection.isUsingCompression();
	}

	/**
	 * This method enables the xmpp compression.
	 * 
	 * @param xmppCompression
	 *            true to enable compression
	 */
	public void setXmppCompression(boolean xmppCompression) {
		if (config.isCompressionEnabled() != xmppCompression) {
			if (connection.isConnected()) {
				disconnect();
			}
			config.setCompressionEnabled(xmppCompression);
			setupConnection();

			restoreConnectionState();
		}
	}

	/**
	 * This method return the chosen xmpp server.
	 * 
	 * @return server name
	 */
	public String getServer() {
		return connection.getHost();
	}

	/**
	 * This method returns the chosen port for the xmpp server
	 * 
	 * @return port as int
	 */
	public int getPort() {
		return connection.getPort();
	}

	/**
	 * This method returns the full qualified xmpp user name of the selected
	 * user.
	 * 
	 * @return xmpp user
	 */
	public String getUser() {
		String uid = user;
		if (!uid.contains("@")) {
			uid += "@" + getServer();
		}
		return uid;
	}

	/**
	 * This method updates the current xmpp user.
	 * 
	 * @param user
	 *            xmpp user name
	 */
	public void setUser(String user) {
		this.user = user;

		if (connection.isConnected() && !user.equals(this.user)) {
			disconnect();
			setupConnection();
			restoreConnectionState();
		}
	}

	/**
	 * This method updates the current users password.
	 * 
	 * @param password
	 *            password of the xmpp user
	 */
	public void setPassword(String password) {
		this.password = password;

		if (connection.isConnected() && !password.equals(this.password)) {
			disconnect();
			setupConnection();
			restoreConnectionState();
		}
	}

	/**
	 * This method returns the device string of the connection.
	 * 
	 * @return device string
	 */
	public String getDevice() {
		return device;
	}

	/**
	 * This method updates the xmpp device string.
	 * 
	 * @param device
	 *            device string
	 */
	public void setDevice(String device) {
		this.device = device;

		if (connection.isConnected() && !device.equals(this.device)) {
			disconnect();
			setupConnection();
			restoreConnectionState();
		}
	}

	@Override
	public void connectionClosed() {
		log.info(connection.getHost() + ": Connection closed.");
	}

	@Override
	public void connectionClosedOnError(Exception exception) {
		log.error(connection.getHost() + ": Connection closed on error.",
				exception);
	}

	@Override
	public void reconnectingIn(int arg0) {
		log.info(connection.getHost() + ": Reconnecting to server.");
	}

	@Override
	public void reconnectionFailed(Exception arg0) {
		log.warn(connection.getHost() + ": Reconnection to server failed!");
	}

	@Override
	public void reconnectionSuccessful() {
		log.info(connection.getHost() + ": Reconnecting successfull.");
	}

	@Override
	public void chatCreated(Chat chat, boolean local) {
		if (!local) {
			UserToUserChatManager.getInstance().addChat(chat);
		}
	}

	/**
	 * This method opens a new user to user chat with the given user. The thread
	 * string is used to put the message to the right chat window.
	 * 
	 * @param user
	 *            full qualified xmpp user
	 * @param thread
	 *            thread for the messages
	 * @return Chat with the user or null
	 */
	protected Chat createChatWithUser(String user, String thread) {
		return connection.getChatManager().createChat(user, thread,
				UserToUserChatManager.getInstance());
	}

	@Override
	public void entriesAdded(Collection<String> addresses) {
		for (XMPPRosterListener listener : listeners) {
			listener.entriesAdded(addresses);
		}
	}

	@Override
	public void entriesDeleted(Collection<String> addresses) {
		for (XMPPRosterListener listener : listeners) {
			listener.entriesDeleted(addresses);
		}
	}

	@Override
	public void entriesUpdated(Collection<String> addresses) {
		for (XMPPRosterListener listener : listeners) {
			listener.entriesUpdated(addresses);
		}
	}

	@Override
	public void presenceChanged(Presence presence) {
		for (XMPPRosterListener listener : listeners) {
			listener.presenceChanged(presence);
		}
	}

	/**
	 * Adds a roster listener.
	 * 
	 * @param listener
	 *            roster listener
	 */
	public void addRosterListener(XMPPRosterListener listener) {
		listeners.add(listener);
	}

	/**
	 * Remove roster listener
	 * 
	 * @param listener
	 *            roster listener
	 */
	public void removeRosterListener(XMPPRosterListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Gets the current presence string of the given user.
	 * 
	 * @param user
	 *            full qualified xmpp user name
	 * @return presence string
	 */
	public Presence getPresence(String user) {
		return roster.getPresence(user);
	}

	/**
	 * This method returns the roster groups.
	 * 
	 * @return roster groups
	 */
	public Collection<RosterGroup> getRosterGroups() {
		return roster.getGroups();
	}

	/**
	 * This method returns the count of roster groups.
	 * 
	 * @return roster group count
	 */
	public int getRosterGroupCount() {
		return roster.getGroupCount();
	}

	public RosterGroup getRosterGroup(String name) {
		return roster.getGroup(name);
	}

	public int getRosterEntryCount() {
		return roster.getEntryCount();
	}

	public RosterEntry getRosterEntry(String user) {
		return roster.getEntry(user);
	}

	public Collection<RosterEntry> getRosterEntries() {
		return roster.getEntries();
	}

	public void createRosterGroup(String groupname) {
		roster.createGroup(groupname);
	}

	public boolean rosterContains(String user) {
		return roster.contains(user);
	}

	public void createRosterEntry(String user, String name, String[] groups) {
		try {
			roster.createEntry(user, name, groups);
		} catch (XMPPException e) {
			log.error("Roster entry couldn't be created!", e);
		}
	}

	public void setPresence(String text) {
		setPresence(text, Presence.Type.available);
	}

	public void setPresence(String text, Type state) {
		Presence presence = new Presence(state);
		presence.setStatus(text);
		connection.sendPacket(presence);
	}

	/**
	 * This method prints the current roster to the standard output.
	 */
	public void printRoster() {
		System.out.println("Roster:");
		for (RosterEntry entry : roster.getEntries()) {
			System.out.println(entry);
		}
	}

	public void addPacketListener(PacketListener listener) {
		addPacketListener(listener, new NotNullMessageFilter());
	}

	public void addPacketListener(PacketListener listener, PacketFilter filter) {
		PacketListenerFilter plf = new PacketListenerFilter(listener, filter);
		packetListeners.add(plf);
		connection.addPacketListener(listener, filter);
	}

	public void removePacketListener(PacketListener listener) {
		connection.removePacketListener(listener);

		Vector<PacketListenerFilter> plfs = new Vector<PacketListenerFilter>();

		for (PacketListenerFilter plf : packetListeners) {
			if (plf.getListener().equals(listener)) {
				plfs.add(plf);
			}
		}

		for (PacketListenerFilter plf : plfs) {
			packetListeners.remove(plf);
		}
	}

	/**
	 * This method builds the full qualified room name for the given room.
	 * 
	 * @param roomname
	 *            room name
	 * @return full qualified room name
	 */
	protected String buildMUCName(String roomname) {
		if (!roomname.contains("@")) {
			roomname += "@" + mucserver;
			log.info("Room name changed to " + roomname + ".");
		}
		return roomname;
	}

	public RoomInfo getRoomInfo(String name) {
		try {
			return MultiUserChat.getRoomInfo(connection, buildMUCName(name));
		} catch (XMPPException exception) {
			log.error("Get room info on " + connection.getHost() + " for room "
					+ name + " failed.", exception);
			log.debug("Error message: " + exception.getMessage());
			log.debug("Localized error message: "
					+ exception.getLocalizedMessage());
			log.debug("XMPP error: " + exception.getXMPPError());
			log.debug("Stream error: " + exception.getStreamError());
		}
		return null;
	}

	protected MultiUserChat getMultiUserChat(String roomname) {
		return new MultiUserChat(connection, buildMUCName(roomname));
	}

	/**
	 * If acceptInvitation flag is true connect to the muc.
	 */
	@Override
	public void invitationReceived(Connection connection, String room,
			String inviter, String reason, String password, Message message) {
		log.info(getUser() + " received a room invitation for room " + room
				+ " from " + inviter);
		log.debug("Password: " + password);
		log.debug("Reason: " + reason);
		if (message != null)
			log.debug("Message: " + message.getBody());

		if (acceptInvitation) {
			XmppMUC muc = XmppMUCManager.getInstance().getMultiUserChat(room,
					user, this);
			muc.join(password, 0);
		}
	}

	/**
	 * This method updates the acceptInvitation flag. If it is true all multi
	 * user chat invitations will be accepted.
	 * 
	 * @param accept
	 *            accept invitations
	 */
	public void setAcceptInvitations(boolean accept) {
		acceptInvitation = accept;
	}

	protected boolean wasReady() {
		return connected & authenticated;
	}
}

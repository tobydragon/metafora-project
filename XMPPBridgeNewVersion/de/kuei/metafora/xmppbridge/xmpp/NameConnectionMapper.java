package de.kuei.metafora.xmppbridge.xmpp;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class NameConnectionMapper {

	private static final Logger log = Logger
			.getLogger(NameConnectionMapper.class);

	private static NameConnectionMapper instance = null;

	public static NameConnectionMapper getInstance() {
		if (instance == null) {
			instance = new NameConnectionMapper();
		}
		return instance;
	}

	private HashMap<String, ServerConnection> nameToConnection;

	private NameConnectionMapper() {
		nameToConnection = new HashMap<String, ServerConnection>();
	}

	public ServerConnection getConnection(String name) {
		return nameToConnection.get(name);
	}

	public boolean addConnection(String name, ServerConnection connection) {
		if (nameToConnection.containsKey(name)) {
			log.error("Name was already used!");
			return false;
		} else {
			nameToConnection.put(name, connection);
			return true;
		}
	}

	public ServerConnection createConnection(String name, String user,
			String password, String device) {
		return createConnection(name, ServerConnection.defaultServer, user,
				password, device);
	}

	public ServerConnection createConnection(String name, String server,
			String user, String password, String device) {
		return createConnection(name, server, user, password, device,
				ServerConnection.defaultPort);
	}

	public ServerConnection createConnection(String name, String server,
			String user, String password, String device, int port) {
		if (nameToConnection.containsKey(name)) {
			log.error("Connection name was already used!");
			return null;
		}

		ServerConnection connection = ServerConnectionManager.getInstance()
				.getConnection(server, user, password, device, port);
		nameToConnection.put(name, connection);
		
		return connection;
	}

}

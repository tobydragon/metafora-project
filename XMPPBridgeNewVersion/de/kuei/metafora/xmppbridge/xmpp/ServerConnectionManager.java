package de.kuei.metafora.xmppbridge.xmpp;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class ServerConnectionManager {
	
	private static final Logger log = Logger.getLogger(ServerConnectionManager.class);
	
	private static ServerConnectionManager instance = null;
	
	public static ServerConnectionManager getInstance(){
		if(instance == null){
			instance = new ServerConnectionManager();
		}
		return instance;
	}

	private HashMap<String, ServerConnection> connections;
	
	private ServerConnectionManager(){
		connections = new HashMap<String, ServerConnection>();
	}
	
	private String buildName(String server, String user, String device){
		return user+"@"+server+"/"+device;
	}
	
	protected void addServerConnection(ServerConnection connection, String server, String user, String device) throws Exception{
		String name = buildName(server, user, device);
		
		if(connections.containsKey(name)){
			log.error("Server connection already exists!");
			throw new Exception("Server connection already exists!");
		}
		
		connections.put(name, connection);
	}
	
	public ServerConnection getConnection(String server, String user, String password, String device){
		String name = buildName(server, user, device);
		
		if(connections.containsKey(name)){
			return connections.get(name);
		}else{
			try {
				return new ServerConnection(server, user, password, device);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return null;
			}
		}
	}
	
	public ServerConnection getConnection(String user, String password, String device){
		String server = ServerConnection.defaultServer;
		String name = buildName(server, user, device);
		
		if(connections.containsKey(name)){
			return connections.get(name);
		}else{
			try {
				return new ServerConnection(server, user, password, device);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return null;
			}
		}
	}
	
	public ServerConnection getConnection(String server, String user, String password, String device, int port){
		String name = buildName(server, user, device);
		
		if(connections.containsKey(name)){
			return connections.get(name);
		}else{
			try {
				return new ServerConnection(server, user, password, device);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return null;
			}
		}
	}
	
	
	public void closeAllConnections(){
		for(ServerConnection connection : connections.values()){
			connection.disconnect();
		}
	}

	
}

package de.kuei.metafora.xmppbridge.xmpp;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class XmppMUCManager {
	
	private static final Logger log = Logger.getLogger(XmppMUCManager.class);
	
	private static XmppMUCManager instance = null;
	
	public static XmppMUCManager getInstance(){
		if(instance == null){
			instance = new XmppMUCManager();
		}
		return instance;
	}
	
	private HashMap<String, XmppMUC> rooms;
	
	private XmppMUCManager(){
		rooms = new HashMap<String, XmppMUC>();
	}
	
	protected void addXmppMUC(XmppMUC muc, ServerConnection connection) throws Exception{
		String name = connection.buildMUCName(muc.getRoomName());
		name += "/"+muc.getAlias();
		
		if(rooms.containsKey(name)){
			log.error("Room "+name+" for "+connection.getUser()+" already exists!");
			throw new Exception("Room "+name+" for "+connection.getUser()+" already exists!");
		}
		
		rooms.put(name, muc);
	}
	
	protected void reconnectToMUCs(ServerConnection connection){
		
		for(String room : rooms.keySet()){
			XmppMUC muc = rooms.get(room);
			if(muc.getConnection().equals(connection)){
				muc.reconnect();
			}
		}
	}
	
	public XmppMUC getMultiUserChat(String roomname, String alias, String connectionName){
		ServerConnection connection = NameConnectionMapper.getInstance().getConnection(connectionName);
		if(connection == null){
			log.error("No connection found for "+connectionName);
			return null;
		}else{
			return getMultiUserChat(roomname, alias, connection);
		}
		
	}
	
	public XmppMUC getMultiUserChat(String roomname, String alias, ServerConnection connection){
		String name = connection.buildMUCName(roomname);
		name += "/"+alias;
		
		if(rooms.containsKey(name)){
			log.debug("Returning existing MUC "+name);
			return rooms.get(name);
		}else{
			try {
				return new XmppMUC(roomname, alias, connection);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return null;
			}
		}
	}

}

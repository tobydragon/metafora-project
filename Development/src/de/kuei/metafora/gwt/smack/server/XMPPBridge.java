package de.kuei.metafora.gwt.smack.server;

import java.util.Iterator;
import java.util.Vector;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;

import de.kuei.metafora.gwt.smack.server.xmpp.XMPPMessageListener;

public class XMPPBridge implements PacketListener, MessageListener{
 
	public static String SERVER = "metafora.ku-eichstaett.de";
	public static String USER = "testuser";
	public static String PASSWORD = "didPfT";
	public static String CHATROOM = "logger@conference.metafora.ku-eichstaett.de";
	public static String USERALIAS = "Development";
	public static String DEVICE = "DevelopmentServer";	
	
	private static XMPPBridge instance = null;
	
	public static XMPPBridge getInstance() throws Exception{
		if(instance == null){
			instance = new XMPPBridge();
		}
		return instance;
	}
	
	private Vector<XMPPMessageListener> listeners = null;
	
	private XMPPConnection connection;
	private MultiUserChat multichat;
	
	private XMPPBridge() throws Exception{
		if(instance == null){
			instance = this;
		}else{
			throw new Exception("Startup Servelt was already built!");
		}
		listeners = new Vector<XMPPMessageListener>();
		init();
	}
	
    public void init() {
    	connection = new XMPPConnection(SERVER);
	}

    public void registerListener(XMPPMessageListener listener){
    	listeners.add(listener);
    	if(listeners.size() == 1){
    		connectToChat();
    	}
    }
    public void removeListener(XMPPMessageListener listener){
    	listeners.remove(listener);
    	if(listeners.size() == 0){
    		disconnect();
    	}
    	
    }
    
	@Override
	public void processPacket(Packet packet) {
		String name = packet.getFrom().substring(packet.getFrom().lastIndexOf('/')+1);
		String chat = packet.getFrom().substring(0, packet.getFrom().lastIndexOf('/')+1);
		Message message = (Message) packet;
		String text = message.getBody();
		
		for(int i=0; i<listeners.size(); i++){
			listeners.get(i).newMessage(name, text, chat);
		}
    }
	
	public void disconnect(){
		multichat.leave();
		connection.disconnect();
	}
	
	public void connect(boolean register){
		connect(USER, PASSWORD, DEVICE, register);
	}
	
	public void connect(String user, String password, boolean register){
		connect(user, password, DEVICE, register);
	}
	
	public void connect(String user, String password, String device, boolean register){
		try {
			connection.connect();
			try {
				connection.login(user, password, device);
			} catch (XMPPException e) {
				int errorcode = e.getXMPPError().getCode();
				System.err.println("XMPP-Error: error-code: "+errorcode+", message: "+e.getMessage());
				if(errorcode == 401 && register){
					//TODO register user
				}
			}
		} catch (XMPPException e) {
			int errorcode = e.getXMPPError().getCode();
			System.err.println("XMPP-Error: error-code: "+errorcode+", message: "+e.getMessage());
		}
	}
	
	public void connectToChat(){
		connectToChat(CHATROOM);
	}
	
	public void connectToChat(String chat){
		if(!connection.isConnected()){
			connect(false);
		}
		
		if(connection.isConnected()){
			if(multichat != null){
				if(multichat.isJoined()){
					multichat.leave();
				}
			}
			
			multichat = new MultiUserChat(connection, chat);
			multichat.addMessageListener(this);
			
			try {
				multichat.join(USERALIAS);
			} catch (XMPPException e) {
				int errorcode = e.getXMPPError().getCode();
				System.err.println("XMPP-Error: error-code: "+errorcode+", message: "+e.getMessage());
				if(errorcode == 409){
					System.err.println("XMPP: Retry with other user alias.");
					try {
						multichat.join(USERALIAS+System.currentTimeMillis());
					} catch (XMPPException ex) {
						errorcode = ex.getXMPPError().getCode();
						System.err.println("XMPP-Error: error-code: "+errorcode+", message: "+e.getMessage());
					}
				}
			}
		}
	}
	
	public void sendMessage(String message){
		if(!connection.isConnected()){
			connect(false);
		}
		
		if(connection.isConnected()){
			if(!multichat.isJoined()){
				connectToChat();
			}
			
			if(multichat.isJoined()){
				try {
					multichat.sendMessage(message);
				} catch (XMPPException e) {
					int errorcode = e.getXMPPError().getCode();
					System.err.println("XMPP-Error: error-code: "+errorcode+", message: "+e.getMessage());
				}
			}
		}
	}
	
	public void sendMessageToUser(String message, String user){
		if(!connection.isConnected()){
			connect(false);
		}
		
		if(connection.isConnected()){
			ChatManager cman = connection.getChatManager();
			Chat chat = cman.createChat(user, this);
			try {
				chat.sendMessage(message);
			} catch (XMPPException e) {
				int errorcode = e.getXMPPError().getCode();
				System.err.println("XMPP-Error: error-code: "+errorcode+", message: "+e.getMessage());
			}
		}
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		String name = message.getFrom();
		String text = message.getBody();
		
		for(int i=0; i<listeners.size(); i++){
			listeners.get(i).newMessage(name, text, name);
		}
	}
	
	public Vector<String> getChatParticipants(){
		Vector<String> users  = new Vector<String>();
		Iterator<String> iter = multichat.getOccupants();
		while(iter.hasNext()){
			users.add(iter.next());
		}
		return users;
	}
}

package de.kuei.metafora.xmpp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class XMPPBridge {

	private static Map<String, XMPPBridge> nameToBridge = Collections
			.synchronizedMap(new HashMap<String, XMPPBridge>());
	private static Map<String, XMPPBridgeCurrent> userToCurrentBridge = Collections
			.synchronizedMap(new HashMap<String, XMPPBridgeCurrent>());

	public static XMPPBridge getConnection(String name) {
		return nameToBridge.get(name);
	}

	public static XMPPBridge getTestConnection() {
		if(!nameToBridge.containsKey("test")){
			XMPPBridge.createConnection("test", "testuser", "didPfT",
					"logger@conference.metafora.ku-eichstaett.de", "Testuser",
					"Testdevice");
		}
		return getConnection("test");
		
	}

	public static synchronized void createConnection(String connectionname, String user,
			String password, String chatroom, String alias, String device){
		XMPPBridgeCurrent bridgeCurrent = null;
		
		if(!userToCurrentBridge.containsKey(user)){
			XMPPBridgeCurrent.setUser(user, password);
			XMPPBridgeCurrent.setAlias(alias);
			XMPPBridgeCurrent.setDevice(device);
			bridgeCurrent = new XMPPBridgeCurrent();
			
			bridgeCurrent.login(true);
			
			userToCurrentBridge.put(user, bridgeCurrent);
		}else{
			bridgeCurrent = userToCurrentBridge.get(user);
		}
		
		XMPPBridge bridge = new XMPPBridge(bridgeCurrent, chatroom);
		nameToBridge.put(connectionname, bridge);
	}

	private XMPPBridgeCurrent bridge;
	private String chat;

	private XMPPBridge(XMPPBridgeCurrent bridge, String chat) {
		this.bridge = bridge;
		this.chat = chat;
		
		this.bridge.connectToChat(chat, null);
	}

	public void init() {
		// ignore
	}

	public void registerPresenceListener(XMPPPresenceListener listener) {
		this.bridge.getDistributor().addPresenceListener(listener);
	}
	
	public void removePresenceListener(XMPPPresenceListener listener) {
		this.bridge.getDistributor().removePresenceListener(listener);
	}
	
	public void registerListener(XMPPMessageListener listener) {
		this.bridge.getDistributor().addListener(listener);
	}

	public void registerTimeListener(XMPPMessageTimeListener listener) {
		this.bridge.getDistributor().addTimeListener(listener);
	}

	public void removeListener(XMPPMessageListener listener) {
		this.bridge.getDistributor().removeListener(listener);
	}

	public void removeTimeListener(XMPPMessageTimeListener listener) {
		this.bridge.getDistributor().removeTimeListener(listener);
	}

	public void disconnect() {
		this.bridge.shutdown();
	}

	public void connect(boolean register) {
		bridge.login(register);
	}

	public void connect(String user, String password, boolean register) {
		bridge.login(register);
	}

	public void connect(String user, String password, String device,
			boolean register) {
		bridge.login(register);
	}

	public void connectToChat() {
		bridge.connectToChat(chat, null);
	}

	public void connectToChat(String chat) {
		bridge.connectToChat(chat, null);
	}

	public void sendMessage(String message) {
		bridge.sendMessageToMultiUserChat(chat, message, null);
	}

	public void sendMessageToUser(String message, String user) {
		bridge.sendMessageToUser(message, user, null);
	}

	public Vector<String> getChatParticipants() {
		return bridge.getChatParticipants(chat);
	}

}

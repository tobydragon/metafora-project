package de.kuei.metafora.xmpp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class XMPPBridgeMulti {

	private static XMPPBridgeMulti instance = null;
	
	public static XMPPBridgeMulti getInstance(){
		if(instance == null){
			instance = new XMPPBridgeMulti();
		}
		return instance;
	}
	
	private Map<String, XMPPBridgeCurrent> userToBridge;
	
	private XMPPBridgeMulti(){
		userToBridge = Collections.synchronizedMap(new HashMap<String, XMPPBridgeCurrent>());
		
	}
	
	public boolean isKnownUser(String user, String server){
		if(userToBridge.containsKey(user+"@"+server)){
			return true;
		}
		return false;
	}
	
	public XMPPBridgeCurrent getBridgeForUser(String user, String server){
		return userToBridge.get(user+"@"+server);
	}
	
	public synchronized XMPPBridgeCurrent createBridge(String server, String user, String password, String device){
		XMPPBridgeCurrent.setServer(server);
		XMPPBridgeCurrent.setUser(user, password);
		XMPPBridgeCurrent.setDevice(device);
		
		XMPPBridgeCurrent bridge = new XMPPBridgeCurrent();
		userToBridge.put(user+"@"+server, bridge);
		
		return bridge;
	}
}

package de.kuei.metafora.xmpp;

import java.util.Date;

public class XmppTest {

	public static void main(String args[]){
		XMPPMessageListener listener = new XMPPMessageListener() {
			
			@Override
			public void newMessage(String user, String message, String chat) {
				System.out.println(chat+": "+user+": "+message);
			}
		};
		
		XMPPMessageTimeListener timeListener = new XMPPMessageTimeListener() {
			
			@Override
			public void newMessage(String user, String message, String chat, Date time) {
				if(time != null)
					System.out.println(time.toString()+" "+user+": "+message);
				else
					System.out.println("no time "+user+": "+message);
			}
		};
		
		
		try {
			XMPPBridge.createConnection("myconn", "MyUser", "MyPassword", "logger@conference.metafora.ku-eichstaett.de", "MyUser", "XMPPBridgeTestApp");
			XMPPBridge bridge = XMPPBridge.getConnection("myconn");
			bridge.connect(true);
			bridge.registerTimeListener(timeListener);
			bridge.connectToChat();
			bridge.sendMessage("XmppTest is online and waiting for messages!");
			
			/*
			XMPPBridge bridge = XMPPBridge.getTestConnection();
			bridge.connect(true);
			bridge.registerListener(listener);
			bridge.connectToChat();
			bridge.sendMessage("XmppTest is online and waiting for messages!");
			
			/*
			XMPPBridge.createConnection("thomas", "thomas", "didPfT", "logger@conference.metafora.ku-eichstaett.de", "TestThomas", "Test");
			XMPPBridge bridge2 = XMPPBridge.getConnection("thomas");
			bridge2.connect(false);
			bridge2.registerListener(listener);
			bridge2.sendMessage("XmppTest 2 is online and waiting for messages!");
			
			XMPPBridge.createConnection("newuser", "ANewUser", "didPfNU", "logger@conference.metafora.ku-eichstaett.de", "new User", "Test");
			XMPPBridge bridge3 = XMPPBridge.getConnection("newuser");
			bridge3.connect(true);
			bridge3.registerListener(listener);
			bridge3.sendMessage("XmppTest 3 is online and waiting for messages!");
			*/
			
			while(true){
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

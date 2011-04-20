package de.kuei.metafora.xmpp;

public class XmppTest {

	public static void main(String args[]){
		XMPPMessageListener listener = new XMPPMessageListener() {
			
			@Override
			public void newMessage(String user, String message, String chat) {
				System.err.println(chat+": "+user+": "+message);
			}
		};
		
		try {
			XMPPBridge bridge = XMPPBridge.getInstance();
			bridge.connect(true);
			bridge.registerListener(listener);
			bridge.sendMessage("XmppTest is online and waiting for messages!");
			while(true){
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

package de.kuei.metafora.xmpp;

import java.util.Date;

public class XmppTest {

	public static void main(String args[]) {
		boolean simpeltest = false;
		boolean compatibilitytest = false;
		
		if(simpeltest)
			test();
		else if(compatibilitytest)
			compatible();
		else
			testMultiUser();
	}

	private static void compatible(){
		XMPPBridge.createConnection("logger", "KUU0004", "KUU0004", "logger@conference.metafora.ku-eichstaett.de", "KU User 0004", "newBridgeTest");
		XMPPBridge bridge = XMPPBridge.getConnection("logger");
		bridge.registerTimeListener(new XMPPMessageTimeListener() {
			
			@Override
			public void newMessage(String user, String message, String chat, Date time) {
				System.err.println("MessageTimeListener: "+user+": "+message);	
			}
		});
		bridge.removeListener(new XMPPMessageListener() {
			
			@Override
			public void newMessage(String user, String message, String chat) {
				System.err.println("MessageListener: "+user+": "+message);
			}
		});
		

		while (true);
	}
	
	private static void testMultiUser() {
		boolean test = true;
		boolean productive = false;

		String commandDevice = "planningToolCommandListener";
		String commandAlias = "PlanningToolCommand";
		String loggerDevice = "planningToolLoggerListener";
		String loggerAlias = "PlanningToolLogger";
		String planningDevice = "planningToolListener";
		String planningAlias = "PlanningTool";

		if (productive) {
			commandDevice += "Productive";
			commandAlias += "Productive";
			loggerDevice += "Productive";
			loggerAlias += "Productive";
			planningDevice += "Productive";
			planningAlias += "Productive";
		}

		if (test) {
			commandDevice += "Test";
			commandAlias += "Test";
			loggerDevice += "Test";
			loggerAlias += "Test";
			planningDevice += "Test";
			planningAlias += "Test";
		}

		try {
			XMPPBridge.createConnection("planningcommand",
					"planningsolocommand", "didPfPSC",
					"command@conference.metafora.ku-eichstaett.de",
					commandAlias, commandDevice);

			XMPPBridge.getConnection("planningcommand").connectToChat();

			XMPPBridge.createConnection("planningtool", "planningsolo",
					"didPfPS", "logger@conference.metafora.ku-eichstaett.de",
					loggerAlias, loggerDevice);

			XMPPBridge.getConnection("planningcommand").registerTimeListener(new XMPPMessageTimeListener() {
				
				@Override
				public void newMessage(String user, String message, String chat, Date time) {
					System.out.println(time.toString()+" Message from "+user+" in "+chat+":\n"+message);
				}
			});

			XMPPBridge.getConnection("planningtool").connectToChat();

			// listens to messages
			XMPPBridge.createConnection("planningtoolinput",
					"planningtoolinput", "didPfPI",
					"planningtool@conference.metafora.ku-eichstaett.de",
					planningAlias, planningDevice);

			XMPPBridge.getConnection("planningtoolinput").connectToChat();
			XMPPBridge.getConnection("planningtoolinput").registerTimeListener(new XMPPMessageTimeListener() {
				
				@Override
				public void newMessage(String user, String message, String chat, Date time) {
					System.out.println(time.toString()+" Message from "+user+" in "+chat+":\n"+message);
				}
			});
			
			XMPPBridge.getConnection("planningtoolinput").sendMessage("eine nachricht");

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		while (true);
	}

	private static void test() {
		XMPPBridgeCurrent.setUser("KUU0004", "KUU0004");

		try {
			XMPPBridgeCurrent bridge = XMPPBridgeCurrent.getInstance();
			bridge.login(true);

			XMPPMessageDistributor distributor = bridge.getDistributor();
			if (distributor != null) {
				distributor
						.addTimeLanguageListener(new XMPPMessageTimeListenerLanguage() {

							@Override
							public void newMessage(String user, String message,
									String chat, Date time, String language) {
								System.out.println("neue Nachricht:\n"
										+ time.toString() + ": " + chat + ", "
										+ user + ": " + message
										+ "\nlanguage: " + language);
							}
						});
			}

			bridge.connectToChat("logger@conference.metafora.ku-eichstaett.de");
			bridge.sendMessageToMultiUserChat(
					"logger@conference.metafora.ku-eichstaett.de", "test!",
					null);
			bridge.sendMessageToMultiUserChat(
					"logger@conference.metafora.ku-eichstaett.de", "test!",
					"subject");
			bridge.sendMessageToUser("testnachricht", "irgangla@jabber.org",
					null);
			bridge.sendMessageToUser("testnachricht", "irgangla@jabber.org",
					"subject");

			while (true) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

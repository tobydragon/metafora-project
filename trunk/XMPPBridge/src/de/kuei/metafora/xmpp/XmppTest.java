package de.kuei.metafora.xmpp;

import java.util.Date;

public class XmppTest {

	public static void main(String args[]) {
		boolean simpeltest = true;
		boolean compatibilitytest = false;
		boolean juggler = false;
		
		if(simpeltest)
			test();
		else if(compatibilitytest)
			compatible();
		else if(juggler)
			juggler();
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
				distributor.addTimeLanguageSubjectListener(new XMPPMessageTimeListenerLanguageSubject() {
					
					@Override
					public void newMessage(String user, String message, String chat, Date time,
							String language, String subject) {
						System.out.println("\nneue Nachricht:\n"
								+ time.toString() + ": " + chat + ", "
								+ user + ":\n" 
								+ "subject: " + subject + "\n"
								+ message
								+ "\nlanguage: " + language+"\n");
					}
				});
			}

			bridge.connectToChat("test@conference.metafora.ku-eichstaett.de", null);
			
			bridge.sendMessageToMultiUserChat("test@conference.metafora.ku-eichstaett.de", 
					"a test message", "Metafora Log Messages", "english");
			
			bridge.sendMessageToUser("a private message", "KUU0004", "a subject", "en");
			bridge.sendMessageToUser("eine private Nachricht", "KUU0004", "der Betreff", "deutsch");
			
			while (true) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void juggler(){
		
		String chat = "logger@conference.metafora.ku-eichstaett.de";
		String token = "atoken";
		
		XMPPBridgeCurrent.setUser("KUU0010", "KUU0010");
		XMPPBridgeCurrent.setDevice(token);
		XMPPBridgeCurrent.setAlias(token);
		
		XMPPBridgeCurrent bridge = new XMPPBridgeCurrent();
		
		bridge.login(true);
		
		bridge.getDistributor().addTimeLanguageListener(new XMPPMessageTimeListenerLanguage() {
			
			@Override
			public void newMessage(String user, String message, String chat, Date time,
					String language) {
				System.out.println("atoken: "+time.toString()+": "+user+": "+message);
			}
		});
		
		bridge.connectToChat(chat, null);
		
		bridge.sendMessageToMultiUserChat(chat, "a log message", null);
		
		token = "anothertoken";
		
		XMPPBridgeCurrent.setUser("KUU0010", "KUU0010");
		XMPPBridgeCurrent.setDevice(token);
		XMPPBridgeCurrent.setAlias(token);
		
		XMPPBridgeCurrent secondBridge = new XMPPBridgeCurrent();
		
		secondBridge.login(true);
		
		secondBridge.getDistributor().addTimeLanguageListener(new XMPPMessageTimeListenerLanguage() {
			
			@Override
			public void newMessage(String user, String message, String chat, Date time,
					String language) {
				System.out.println("anothertoken: "+time.toString()+": "+user+": "+message);
			}
		});
		
		secondBridge.connectToChat(chat, null);
		
		secondBridge.sendMessageToMultiUserChat(chat, "a other log message", null);
		
		while(true);
		
	}

}

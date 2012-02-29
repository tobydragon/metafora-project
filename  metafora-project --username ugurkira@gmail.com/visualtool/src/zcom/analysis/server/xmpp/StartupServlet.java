package zcom.analysis.server.xmpp;

import javax.servlet.http.HttpServlet;


public class StartupServlet extends HttpServlet {

	private static final boolean test = true;
	
	public void init() {
		try {
			if(test){
				
				XMPPBridge.createConnection("BasilicaLoggerTestUgur","BasilicaLoggerTestUgur", "BasilicaLoggerTestUgur", "logger@conference.metafora.ku-eichstaett.de", "BasilicaLoggerTestUgur", "BasilicaLoggerTestUgur");
				XMPPBridge.getConnection("BasilicaLoggerTestUgur").connect(true);
				XMPPBridge.getConnection("BasilicaLoggerTestUgur").connectToChat();
				XMPPBridge.getConnection("BasilicaLoggerTestUgur").sendMessage("Annotator is online and waiting for messages!");
				
				
				/*
				XMPPBridge.createConnection("planningcommand", "planningsolocommandTest",
						"didPfPSC",
						"command@conference.metafora.ku-eichstaett.de",
						"PlanningToolCommandTest", "planningToolCommandListenerTest");
				
				XMPPBridge.getConnection("planningcommand").connect(true);
				XMPPBridge.getConnection("planningcommand").connectToChat();
				
				//
				XMPPBridge.createConnection("planningtool", "planningsoloTest",
						"didPfPS",
						"logger@conference.metafora.ku-eichstaett.de",
						"PlanningToolLoggerTest", "planningToolServerAppTest");
				
				XMPPBridge.getConnection("planningcommand").registerTimeListener(new XMPPListener());
				
				XMPPBridge.getConnection("planningtool").connect(true);
				XMPPBridge.getConnection("planningtool").connectToChat();
	
				// listens to messages
				XMPPBridge.createConnection("planningtoolinput", "planningtoolinputTest",
						"didPfPI",
						"planningtool@conference.metafora.ku-eichstaett.de",
						"PlanningTool Input Test", "planningToolServerAppTest");
				
				XMPPBridge.getConnection("planningtoolinput").connect(true);
				XMPPBridge.getConnection("planningtoolinput").connectToChat();
				XMPPBridge.getConnection("planningtoolinput").registerTimeListener(new PlanningToolInput());
			
			*/}else{
				
				/*
				XMPPBridge.createConnection("planningcommand", "planningsolocommand",
						"didPfPSC",
						"command@conference.metafora.ku-eichstaett.de",
						"PlanningToolCommand", "planningToolCommandListener");
				
				XMPPBridge.getConnection("planningcommand").connectToChat();
				
				XMPPBridge.createConnection("planningtool", "planningsolo",
						"didPfPS",
						"logger@conference.metafora.ku-eichstaett.de",
						"PlanningToolLogger", "planningToolServerApp");
				
				XMPPBridge.getConnection("planningcommand").registerTimeListener(new XMPPListener());
				
				XMPPBridge.getConnection("planningtool").connectToChat();

				// listens to messages
				XMPPBridge.createConnection("planningtoolinput", "planningtoolinput",
						"didPfPI",
						"planningtool@conference.metafora.ku-eichstaett.de",
						"PlanningTool Input", "planningToolServerApp");
				
				XMPPBridge.getConnection("planningtoolinput").connectToChat();
				XMPPBridge.getConnection("planningtoolinput").registerTimeListener(new PlanningToolInput());
*/
			}
			
			
			//MysqlConnector.getInstance().loadAllNetsFromDatabase();
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}

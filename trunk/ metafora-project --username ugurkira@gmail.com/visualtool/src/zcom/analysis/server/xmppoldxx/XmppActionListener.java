package zcom.analysis.server.xmppoldxx;

import zcom.analysis.server.xmpp.XMPPBridge;
import zcom.analysis.server.xmpp.XMPPMessageListener;

//import com.analysis.server.xmppold.XMPPBridge;
//import com.analysis.server.xmppold.XMPPMessageListener;










public class XmppActionListener  implements Runnable{
static String connectionName="BasilicaLoggerTestUgur";

	
public static XMPPBridge getXMPPConnection(){
		
	return XMPPBridge.getConnection(connectionName);
		
		
}


	public void run() {
		XMPPMessageListener listener = new XMPPMessageListener() {
				

			
				public void newMessage(String user, String message, String chat) {
				System.out.println("Message:"+ message);
				System.out.println("************************************************************************");
					
					}
				
			};
			
			
		
			try {	
				
					
					XMPPBridge.createConnection(connectionName,"BasilicaLoggerTestUgur", "BasilicaLoggerTestUgur", "logger@conference.metafora.ku-eichstaett.de", "BasilicaLoggerTestUgur", "BasilicaLoggerTestUgur");
					XMPPBridge bridge2 = XMPPBridge.getConnection(connectionName);
				bridge2.connect(true);
					bridge2.registerListener(listener);
				bridge2.sendMessage("Visual Tool is online and waiting for messages!");
				
				
				
				
				
			while(true){
				
			}
				
				
			} catch (Exception e) {
			System.out.println("Error:"	+e.getMessage());
				e.printStackTrace();
			}
			
		}
	
}

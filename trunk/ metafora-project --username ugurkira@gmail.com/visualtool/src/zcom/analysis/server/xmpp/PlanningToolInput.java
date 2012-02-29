package zcom.analysis.server.xmpp;

import java.util.Date;


public class PlanningToolInput implements XMPPMessageTimeListener{

	@Override
	public void newMessage(String user, String message, String chat, Date time) {
		if(message != null){
			
			System.out.println("New Message:"+message);
			
		}
			
			
			//UserManager.getInstance().actionReceived("127.0.0.1", message);
	}

}

package zcom.analysis.server.xmppoldx;

import java.util.Date;

public interface XMPPMessageTimeListener {

	public void newMessage(String user, String message, String chat, Date time);
	
}

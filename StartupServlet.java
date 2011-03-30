package de.kuei.metafora.gwt.smack.server;

import javax.servlet.http.HttpServlet;

import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;

import de.kuei.metafora.gwt.smack.server.xmpp.XmppMessages;

public class StartupServlet extends HttpServlet implements PacketListener{
 
	public static XmppMessages messages = new XmppMessages();
	
	private XMPPConnection connection;
	private MultiUserChat logger;
	
    public void init() {
    	connection = new XMPPConnection("metafora.ku-eichstaett.de");

		try {
			connection.connect();
			connection.login("development", "didPfD");
			
			logger = new MultiUserChat(connection,
					"logger@conference.metafora.ku-eichstaett.de");
			logger.addMessageListener(this);
			logger.join("Development");
			
			logger.sendMessage("Development Updater is watching you");

		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void processPacket(Packet packet) {
		String name = packet.getFrom().substring(packet.getFrom().lastIndexOf('/')+1);
		Message message = (Message) packet;
		String text = message.getBody();
		
		messages.addMessage(name+": "+text);
    }
 
}
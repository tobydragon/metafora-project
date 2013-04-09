package de.kuei.metafora.xmppbridge.xmpp.interfaces;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

public interface ChatMessageListener {

	public void processMessage(Chat chat, Message message);
	
}

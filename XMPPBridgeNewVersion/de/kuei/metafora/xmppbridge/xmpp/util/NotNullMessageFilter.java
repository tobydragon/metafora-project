package de.kuei.metafora.xmppbridge.xmpp.util;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

public class NotNullMessageFilter implements PacketFilter{

	@Override
	public boolean accept(Packet packet) {
		if(packet instanceof Message){
			if(((Message)packet).getBody() != null){
				return true;
			}
		}
		return false;
	}
}

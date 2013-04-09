package de.kuei.metafora.xmppbridge.xmpp.util;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

public class RegExpFilter implements PacketFilter{

	private String regexp = ".*";
	
	public RegExpFilter(String regexp){
		this.regexp = regexp;
	}
	
	public String getRegExp(){
		return regexp;
	}
	
	public void setRegExp(String regexp){
		this.regexp = regexp;
	}
	
	@Override
	public boolean accept(Packet packet) {
		if(packet instanceof Message){
			Message message = (Message)packet;
			String body = message.getBody();
			if(body != null){
				body = body.replaceAll("\n", "");
				if (body.matches(regexp)){
					return true;
				}
			}
		}
		return false;
	}

}

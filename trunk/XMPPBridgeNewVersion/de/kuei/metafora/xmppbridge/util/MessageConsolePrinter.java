package de.kuei.metafora.xmppbridge.util;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

public class MessageConsolePrinter implements PacketListener{

	private String name;
	
	public MessageConsolePrinter(String name){
		this.name = name;
	}
	
	@Override
	public void processPacket(Packet packet) {
		Message message = (Message)packet;
		
		System.out.println(name+": New message from "+message.getFrom()+" to "+message.getTo()+" in thread "+message.getThread()+":");
		System.out.println(name+": Language: "+message.getLanguage());
		System.out.println(name+": Subject: "+message.getSubject());
		System.out.println(name+": Message: "+message.getBody());
		System.out.println();
	}
}

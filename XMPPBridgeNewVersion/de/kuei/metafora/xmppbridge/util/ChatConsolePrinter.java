package de.kuei.metafora.xmppbridge.util;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Body;
import org.jivesoftware.smack.packet.PacketExtension;

import de.kuei.metafora.xmppbridge.xmpp.interfaces.ChatMessageListener;

public class ChatConsolePrinter implements ChatMessageListener{

	private static final Logger log = Logger.getLogger(ChatConsolePrinter.class);
	
	@Override
	public void processMessage(Chat chat, Message message) {
		System.out.println("New message from chat "+chat.getParticipant()+" in thread "+chat.getThreadID()+":");
		System.out.println(message.getFrom()+": "+message.getBody());
		System.out.println("Subject: "+message.getSubject());
		System.out.println("Language: "+message.getLanguage());
		System.out.println("Receiver: "+message.getTo());
		System.out.println("Thread: "+message.getThread());
		
		for(Body body : message.getBodies()){
			System.out.println(body.getLanguage()+": "+body.getLanguage());
		}
		
		for(String lang : message.getSubjectLanguages()){
			System.out.println(lang+": "+message.getSubject(lang));
		}
		
		for(String property : message.getPropertyNames()){
			System.out.println(property+": "+message.getProperty(property));
		}
		
		for(PacketExtension extension : message.getExtensions()){
			System.out.println("extension: "+extension.getElementName());
		}
		
		if(true){
			try {
				chat.sendMessage(message.getBody());
			} catch (XMPPException e) {
				log.error("Message sending error!", e);
			}
		}
	}
}

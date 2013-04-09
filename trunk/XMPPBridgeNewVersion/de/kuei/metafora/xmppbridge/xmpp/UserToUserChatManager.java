package de.kuei.metafora.xmppbridge.xmpp;

import java.util.Vector;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import de.kuei.metafora.xmppbridge.xmpp.interfaces.ChatMessageListener;

public class UserToUserChatManager implements MessageListener{

	private static final Logger log = Logger.getLogger(UserToUserChatManager.class);
	
	private static UserToUserChatManager instance = null;
	
	public static UserToUserChatManager getInstance(){
		if(instance == null){
			log.info("Creating new UserToUserChatManager.");
			instance = new UserToUserChatManager();
		}
		return instance;
	}
	
	private Vector<Chat> chats = null;
	private Vector<ChatMessageListener> listeners;
	
	private UserToUserChatManager(){
		chats = new Vector<Chat>();
		listeners = new Vector<ChatMessageListener>();
	}
	
	public void addChat(Chat chat){
		chats.add(chat);
		chat.addMessageListener(this);
	}
	
	public void closeChat(Chat chat){
		chat.removeMessageListener(this);
		chats.remove(chat);
	}
	
	public Chat findAChatWith(String user){
		for(Chat chat : chats){
			if(chat.getParticipant().startsWith(user)){
				return chat;
			}
		}
		return null;
	}
	
	public Chat openChatWithUser(String user, String connectionName){
		ServerConnection connection = NameConnectionMapper.getInstance().getConnection(connectionName);
		
		if(connection == null){
			log.error("No connection found for "+connectionName);
			return null;
		}else{
			return openChatWithUser(user, connection);
		}
	}
	
	public Chat openChatWithUser(String user, ServerConnection connection){
		return openChatWithUser(user, "default"+System.currentTimeMillis(), connection);
	}
	
	public Chat openChatWithUser(String user, String thread, ServerConnection connection){
		log.info("Creating new chat with "+user);
		
		if(!user.contains("@")){
			user += "@"+connection.getServer();
			log.info("User changed to "+user);
		}
		Chat chat = connection.createChatWithUser(user, thread);
		chats.add(chat);
		return chat;
	}
	
	public Vector<Chat> getChatsForUser(String user){
		Vector<Chat> userchats = new Vector<Chat>();
		for(Chat chat : chats){
			if(chat.getParticipant().startsWith(user)){
				userchats.add(chat);
			}
		}
		return chats;
	}
	
	public void addMessageListener(ChatMessageListener listener){
		log.info("Adding new listener "+listener);
		listeners.add(listener);
	}
	
	public void RemoveMessageListener(ChatMessageListener listener){
		log.info("Removing listener "+listener);
		listeners.remove(listener);
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		log.info("New chat message in "+chat.getThreadID()+" from user "+chat.getParticipant());
		
		if(message.getBody() == null && message.getSubject() == null){
			log.info("Message with null body dropped!");
		}else{
			for(ChatMessageListener listener : listeners){
				listener.processMessage(chat, message);
			}
		}
	}
	
}

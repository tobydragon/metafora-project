package de.uds.MonitorInterventionMetafora.server.feedback;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.shared.messages.Locale;
import de.uds.MonitorInterventionMetafora.shared.messages.MessageType;

public class FeedbackModel implements Runnable {
	Logger logger = Logger.getLogger(this.getClass());
	
	private Map<Locale, Map <MessageType, String>> defaultMessagesModel;
	
	private Map<String, String> suggestedMessagesModel;
	
	public FeedbackModel() {
		suggestedMessagesModel = new HashMap<String, String>();
		defaultMessagesModel = new HashMap<Locale, Map<MessageType, String>>();
	}
	
	public synchronized void updateSuggestedMessages(String username, String messagesXml) {
		suggestedMessagesModel.put(username, messagesXml);
	}

	public synchronized String getSuggestedMessages(String username) {
		logger.info("Save suggested messages for user: " + username);
		return suggestedMessagesModel.get(username);
	}

	public void updateDefaultMessagesModel(MessageType messageType, Locale locale, String messagesXml){
		Map <MessageType, String> type2messages = defaultMessagesModel.get(locale);
		if (type2messages == null){
			type2messages = new HashMap<MessageType, String>();
			defaultMessagesModel.put(locale, type2messages);
		}
		type2messages.put(messageType, messagesXml);
	}
	
	public String getDefaultMessages(MessageType messageType, Locale locale){
		String defaultMessages = null;
		Map <MessageType, String> type2messages = defaultMessagesModel.get(locale);
		if (type2messages != null){
			return type2messages.get(messageType);
		}
		return defaultMessages;
		
	}
	
	@Override
	public void run() {
	}
}

package messages;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.MessageType;

public class SuggestedMessagesModel {
	Logger logger = Logger.getLogger(this.getClass());
	
	private Map<Locale, Map <MessageType, String>> defaultMessagesModel;
	
	private Map<String, String> suggestedMessages2user;
	
	public SuggestedMessagesModel() {
		suggestedMessages2user = new HashMap<String, String>();
		defaultMessagesModel = new HashMap<Locale, Map<MessageType, String>>();
	}
	
	public synchronized void updateSuggestedMessages(String username, String messagesXml) {
		suggestedMessages2user.put(username, messagesXml);
	}

	public synchronized String getSuggestedMessages(String username) {
		logger.debug("[getSuggestedMessages] for user: " + username);
		return suggestedMessages2user.get(username);
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
	
}

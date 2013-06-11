package messages;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.mmftparser.SuggestedMessagesModelParserForServer;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.MessageType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesModel;

public class SuggestedMessages4AllUsersModel {
	Logger logger = Logger.getLogger(this.getClass());
	
	private Map<Locale, Map <MessageType, SuggestedMessagesModel>> defaultMessagesModel;
	
	private Map<String, String> suggestedMessages2user;
	
	public SuggestedMessages4AllUsersModel() {
		suggestedMessages2user = new HashMap<String, String>();
		defaultMessagesModel = new HashMap<Locale, Map<MessageType, SuggestedMessagesModel>>();
	}
	
	public synchronized void updateSuggestedMessages(String username, String messagesXml) {
		suggestedMessages2user.put(username, messagesXml);
	}
	
	public synchronized void clearAllSuggestedMessages(){
		suggestedMessages2user.clear();
	}

	public synchronized String getSuggestedMessages(String username) {
		logger.debug("[getSuggestedMessages] for user: " + username);
		return suggestedMessages2user.get(username);
	}

	public void updateDefaultMessagesModel(MessageType messageType, Locale locale, SuggestedMessagesModel currentMessageModel){
		if (currentMessageModel != null){
			Map <MessageType, SuggestedMessagesModel> type2messages = defaultMessagesModel.get(locale);
			if (type2messages == null){
				type2messages = new HashMap<MessageType, SuggestedMessagesModel>();
				defaultMessagesModel.put(locale, type2messages);
			}
			type2messages.put(messageType, currentMessageModel);
		}
		else {
			logger.error("[updateDefaultMessagesModel] null model for messageType: " + messageType +" - locale: "+ locale);
		}
	}
	
	public SuggestedMessagesModel getCopyOfDefaultMessages(MessageType messageType, Locale locale){
		Map <MessageType, SuggestedMessagesModel> type2messages = defaultMessagesModel.get(locale);
		if (type2messages != null){
			SuggestedMessagesModel defaultModel = type2messages.get(messageType);
			return new SuggestedMessagesModel(defaultModel);
		}
		return null;
	}
	
}

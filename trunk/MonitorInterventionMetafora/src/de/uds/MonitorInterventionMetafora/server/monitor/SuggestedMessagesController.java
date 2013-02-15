package de.uds.MonitorInterventionMetafora.server.monitor;

import java.util.HashMap;
import java.util.Map;

import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfCommunicationListener;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.utils.Logger;

public class SuggestedMessagesController implements CfCommunicationListener{
	Logger logger = Logger.getLogger(SuggestedMessagesController.class);
	static SuggestedMessagesController singletonInstance;
	Map<String, String> messages4Users;
	
	private SuggestedMessagesController() {
		messages4Users = new HashMap<String, String>();
		
		CfAgentCommunicationManager.getInstance(CommunicationChannelType.command).register(this);
	}
	
	public static SuggestedMessagesController getInstance() {
		if (singletonInstance == null)
			singletonInstance = new SuggestedMessagesController();
		
		return singletonInstance;
	}
	
	@Override
	public synchronized void processCfAction(String user, CfAction action) {
		logger.info("");
		if (action.getCfActionType().getType().equals(MetaforaStrings.ACTION_TYPE_SUGGESTED_MESSAGES_STRING)) {
			for (CfUser cfUser : action.getUsersWithRole("receiver")) {
				String suggestionsXML = action.getCfContent().getDescription();
				messages4Users.put(cfUser.getid(), suggestionsXML);
			}
		}
	}

	
	public String getSuggestedMessages(String userId) {
		if (messages4Users.containsKey(userId))
			return messages4Users.get(userId);
		return "";
	}
}

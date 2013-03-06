package de.uds.MonitorInterventionMetafora.server.feedback;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class FeedbackModel implements Runnable {
	Logger logger = Logger.getLogger(this.getClass());
	
	private Map<String, String> suggestedMessagesModel;
	
	public FeedbackModel() {
		suggestedMessagesModel = new HashMap<String, String>();
	}
	
	public synchronized void updateSuggestedMessages(String username, String messagesXml) {
		suggestedMessagesModel.put(username, messagesXml);
	}

	public synchronized String getSuggestedMessages(String username) {
		logger.info("Save suggested messages for user: " + username);
		return suggestedMessagesModel.get(username);
	}

	@Override
	public void run() {
	}
}

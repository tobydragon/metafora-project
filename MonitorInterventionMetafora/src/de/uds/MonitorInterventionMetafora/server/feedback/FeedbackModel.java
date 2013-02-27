package de.uds.MonitorInterventionMetafora.server.feedback;

import java.util.HashMap;
import java.util.Map;

public class FeedbackModel implements Runnable {
	
	private Map<String, String> suggestedMessagesModel;
	
	public FeedbackModel() {
		suggestedMessagesModel = new HashMap<String, String>();
	}
	
	public synchronized void updateSuggestedMessages(String username, String messagesXml) {
		suggestedMessagesModel.put(username, messagesXml);
	}

	public synchronized String getSuggestedMessages(String username) {
		return suggestedMessagesModel.get(username);
	}

	@Override
	public void run() {
	}
}

package de.uds.MonitorInterventionMetafora.client.communication.actionresponses;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uds.MonitorInterventionMetafora.client.display.DisplayUtil;
import de.uds.MonitorInterventionMetafora.client.feedback.MessagesPanel;
import de.uds.MonitorInterventionMetafora.client.feedback.SuggestedMessagesModelParserForClient;
import de.uds.MonitorInterventionMetafora.client.messages.MessagesBundle;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesModel;

public class RequestSuggestedMessagesCallback implements AsyncCallback<String>{
	public static MessagesBundle messagesBundle = GWT.create(MessagesBundle.class);
	
	private MessagesPanel messagesPanel;
	
	public RequestSuggestedMessagesCallback(MessagesPanel messagesPanel){
		this.messagesPanel = messagesPanel;
	}
	
	public void onSuccess(String result) {
		Log.info("[RequestSuggestedMessagesCallback.onSuccess]");
		if (result != null && !result.equals("")) {
			SuggestedMessagesModel suggestedMessagesModel = SuggestedMessagesModelParserForClient.fromXML(result);
			if (suggestedMessagesModel != null){
				MessagesPanel.getTemplatePool().populateTabs(suggestedMessagesModel);
				DisplayUtil.postNotificationMessage(messagesBundle.NewSuggestedMessages());
			}
			else {
				Log.error("RequestSuggestedMessagesCallback - Bad XML: " + result);
				DisplayUtil.postNotificationMessage(messagesBundle.NoSuggestedMessages() + ": Error retrieving");
			}
		}
		else {
			DisplayUtil.postNotificationMessage(messagesBundle.NoSuggestedMessages());
			//TODO: make the messages refresh with defaults when nothing is available
//			messagesPanel.loadFreshSuggestedMessageFromFile();
		}
	}
	
	public void onFailure(Throwable caught) {
		Log.error("RequestSuggestedMessagesCallback - Fail");
		DisplayUtil.postNotificationMessage(messagesBundle.NoSuggestedMessages() + ": Error retrieving");
	}

}

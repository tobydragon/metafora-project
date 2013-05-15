package de.uds.MonitorInterventionMetafora.client.communication.actionresponses;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uds.MonitorInterventionMetafora.client.display.DisplayUtil;
import de.uds.MonitorInterventionMetafora.client.feedback.MessagesPanel;
import de.uds.MonitorInterventionMetafora.client.messages.MessagesBundle;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesModel;

public class RequestSuggestedMessagesCallback implements AsyncCallback<String>{
	public static MessagesBundle messagesBundle = GWT.create(MessagesBundle.class);
	
	public void onSuccess(String result) {
		Log.info("RequestSuggestedMessagesCallback - Success");
		if (result != null && !result.equals("")) {
			MessagesPanel.getTemplatePool().populateTabs(SuggestedMessagesModel.fromXML(result));
			DisplayUtil.postNotificationMessage(messagesBundle.NewSuggestedMessages());
		}
		else {
			DisplayUtil.postNotificationMessage(messagesBundle.NoSuggestedMessages());
		}
	}
	
	public void onFailure(Throwable caught) {
		Log.error("RequestSuggestedMessagesCallback - Fail");
		DisplayUtil.postNotificationMessage(messagesBundle.NoSuggestedMessages() + ": Error retrieving");
	}

}

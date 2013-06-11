package de.uds.MonitorInterventionMetafora.client.feedback;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestSuggestedMessagesCallback;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.InterventionCreator;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessage;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesModel;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesCategory;

public class SuggestedMessagesController {
	private SuggestedMessagesPanel suggestedMessagesView;
	private SuggestedMessagesModel suggestedMessagesModel;
	
	private CommunicationServiceAsync commServiceServlet;
	private MessagesPanel parentPanel;
	
	public SuggestedMessagesController(SuggestedMessagesModel messagesModel, CommunicationServiceAsync commServiceServlet, MessagesPanel parentPanel) {
		this.setSuggestedMessagesModel(messagesModel);
		this.commServiceServlet = commServiceServlet;
		this.parentPanel = parentPanel;
	}

	public void sendSuggestedMessages(String XML) {
		MessageSendingPanel outbox = MessagesPanel.getOutbox();
		List<String> userIds = outbox.getSelectedRecipients();
		CfAction suggesteMessages = InterventionCreator.createSendSuggestedMessages(userIds, XML);
		outbox.sendSuggestedMessageToServer(suggesteMessages);		
	}

	public SuggestedMessagesPanel getView() {
		return suggestedMessagesView;
	}
	
	public void setView(SuggestedMessagesPanel view) {
		this.suggestedMessagesView = view;
	}
	
	public SuggestedMessagesModel getSuggestedMessagesModel() {
		return suggestedMessagesModel;
	}

	public void setSuggestedMessagesModel(SuggestedMessagesModel suggestedMessagesModel) {
		this.suggestedMessagesModel = suggestedMessagesModel;
	}

	public void changeMessageStyle(String categoryName, int i, boolean isHighlight) {
		SuggestedMessagesCategory category = suggestedMessagesModel.getSuggestionCategory(categoryName);
		if (category != null)
			category.getSuggestedMessage(i).setHighlight(isHighlight);
		else
			System.err.println("SuggestedMessagesController.changeMessageStyle(): category is null");
		
		String isBoldStr = (isHighlight) ? "" : " not"; 
		Log.info("Message: " +category.getSuggestedMessage(i).getText()+ " in category: "+ categoryName+ " is" + isBoldStr + " bold now");
	}

	
	public void addNewMessage(String categoryName, String messageText) {
		SuggestedMessagesCategory category = suggestedMessagesModel.getSuggestionCategory(categoryName);
		if (category != null)
			category.addMessage(new SuggestedMessage(messageText));
	}

	public void refreshTabs() {
		suggestedMessagesView.populateTabs(suggestedMessagesModel);
	}

	public void removeMessage(String categoryName, int messageIndex) {
		SuggestedMessagesCategory category = suggestedMessagesModel.getSuggestionCategory(categoryName);
		if (category != null)
			category.removeMessage(messageIndex);
	}

	public void highlightCategory(String categoryName, boolean isHighlight) {
		SuggestedMessagesCategory category = suggestedMessagesModel.getSuggestionCategory(categoryName);
		if (category != null)
			category.setHighlight(isHighlight);
	}
	
	public void refreshSuggestedMessages(String username) {
		XmppServerType xmppServerType = UrlParameterConfig.getInstance().getXmppServerType();
		if (xmppServerType != null){
			commServiceServlet.requestSuggestedMessages(xmppServerType, username, new RequestSuggestedMessagesCallback(parentPanel));
		}
		else {
			commServiceServlet.requestSuggestedMessages(username, new RequestSuggestedMessagesCallback(parentPanel));
		}
	}
	
}

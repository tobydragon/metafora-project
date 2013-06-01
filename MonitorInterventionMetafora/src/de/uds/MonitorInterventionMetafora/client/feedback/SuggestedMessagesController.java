package de.uds.MonitorInterventionMetafora.client.feedback;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestSuggestedMessagesCallback;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessage;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesModel;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesCategory;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

public class SuggestedMessagesController {
	private SuggestedMessagesPanel suggestedMessagesView;
	private SuggestedMessagesModel suggestedMessagesModel;
	
	private CommunicationServiceAsync commServiceServlet;
	
	public SuggestedMessagesController(SuggestedMessagesModel messagesModel, CommunicationServiceAsync commServiceServlet) {
		this.setSuggestedMessagesModel(messagesModel);
		this.commServiceServlet = commServiceServlet;
	}

	public void sendSuggestedMessages(String XML) {
		MessageSendingPanel outbox = MessagesPanel.getOutbox();
		
		// Create cfAction
		CfActionType cfActionType = new CfActionType();
		cfActionType.setType(MetaforaStrings.ACTION_TYPE_SUGGESTED_MESSAGES_STRING);
		cfActionType.setClassification("create");
		cfActionType.setLogged("false");

		CfAction cfAction = new CfAction(GWTUtils.getTimeStamp(), cfActionType);
		List<String> userIds = outbox.getSelectedRecipients();
		if (userIds.size() < 1) {
			System.out.println("[TemplatePool.sendSuggestedMessages()] No users selected. 'Send Suggestions' ignored.");
			return;
		}
		for (String userId : userIds) {
			cfAction.addUser(new CfUser(userId, "receiver"));
		}
		
		String receiver = UrlParameterConfig.getInstance().getReceiver();
		receiver = (receiver == null || receiver.equals("")) ? MetaforaStrings.RECEIVER_METAFORA : receiver; 
		
		CfContent cfContent = new CfContent(XML);
		cfContent.addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_RECEIVING_TOOL, receiver));
		cfContent.addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_SENDING_TOOL,"FEEDBACK_CLIENT"));
		cfAction.setCfContent(cfContent);
		
		CfObject cfObject = new CfObject("0", MetaforaStrings.PROPERTY_VALUE_MESSAGE_STRING);
 	 	cfObject.addProperty(new CfProperty("INTERRUPTION_TYPE", outbox.getSelectedIntteruptionType()));
 	 	cfAction.addObject(cfObject);
		
		outbox.sendSuggestedMessageToServer(cfAction);		
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
			commServiceServlet.requestSuggestedMessages(xmppServerType, username, new RequestSuggestedMessagesCallback());
		}
		else {
			commServiceServlet.requestSuggestedMessages(username, new RequestSuggestedMessagesCallback());
		}
	}
	
}

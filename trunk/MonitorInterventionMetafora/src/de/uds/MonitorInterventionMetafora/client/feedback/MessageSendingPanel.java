package de.uds.MonitorInterventionMetafora.client.feedback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.uds.MonitorInterventionMetafora.client.messages.MessagesBundle;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.NoActionResponse;
import de.uds.MonitorInterventionMetafora.client.display.DisplayUtil;
import de.uds.MonitorInterventionMetafora.client.logger.ComponentType;
import de.uds.MonitorInterventionMetafora.client.logger.Logger;
import de.uds.MonitorInterventionMetafora.client.logger.UserActionType;
import de.uds.MonitorInterventionMetafora.client.logger.UserLog;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig.UserType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.InterventionCreator;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.L2L2category;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessage;

public class MessageSendingPanel extends VerticalPanel {
    
    
    	private static String HIGH_LABEL = "High";
    	private static String LOW_LABEL = "Low";
    	private static String NONE_LABEL = "None";
    	
    	public static MessagesBundle messagesBundle = GWT.create(MessagesBundle.class);

	private int PANEL_HEIGHT = 290;
	
	private TextArea messageTextArea;
	private TextBox objectIdsTextBox;
	private HorizontalPanel hPanel;
	private VerticalPanel vPanel;
	private VerticalPanel sendModeRadioColumn;
	private VerticalPanel sendOptionsRow;
	private HorizontalPanel objectIdsRow;
	public RadioButton sendModeRadioButtonPopup;
	public RadioButton sendModeRadioButtonSuggestion;
	public RadioButton sendModeRadioButtonResponse;
	public int sectionWidth = 400;
	public VerticalPanel recipientNamesColumn;
	private SuggestedMessagesController suggestedMessagesController;
	private CommunicationServiceAsync commServiceServlet;
	
	private SuggestedMessage lastSelectedMessage;
	
	public MessageSendingPanel(CommunicationServiceAsync commServiceServlet, String[] userIDsArray) {
		this.commServiceServlet = commServiceServlet;
		
		vPanel = new VerticalPanel();
		hPanel = new HorizontalPanel();
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setHeight(PANEL_HEIGHT +"px");
		scrollPanel.setAlwaysShowScrollBars(false);
		
		//section label
		HorizontalPanel labelAndSendButtonPanel = new HorizontalPanel();
		labelAndSendButtonPanel.setWidth(sectionWidth + "px");
		final Label sectionLabel = new Label(messagesBundle.EditInstructions());
		sectionLabel.setStyleName("sectionLabel");
		labelAndSendButtonPanel.setSpacing(5);
		
		UserType userType = UrlParameterConfig.getInstance().getUserType();

		//send options
		sendOptionsRow = new VerticalPanel();
		sendOptionsRow.setSpacing(5);
		

		// object ids
		if (userType.equals(UserType.RECOMMENDING_WIZARD)) {
			objectIdsRow = new HorizontalPanel();
			objectIdsRow.setSpacing(3);
			objectIdsRow.add(new Label("object ids"));
			objectIdsTextBox = new TextBox();
			objectIdsTextBox.setText("");
			objectIdsTextBox.selectAll();
			objectIdsRow.add(objectIdsTextBox);
			hPanel.add(objectIdsRow);
		}
		
		// send mode
		addInterruptionLevels();
		
		//recipients
		final VerticalPanel userGroupColumn = new VerticalPanel();
		userGroupColumn.add(new Label(messagesBundle.To()));
		recipientNamesColumn = new VerticalPanel();
		userGroupColumn.add(recipientNamesColumn);
		
		
		if (UrlParameterConfig.getInstance().getUserType().equals(UserType.MESSAGING_WIZARD)
		   || UrlParameterConfig.getInstance().getUserType().equals(UserType.RECOMMENDING_WIZARD)) {


		class SelectAllRecipientsButton extends Button{
			SelectAllRecipientsButton(String name, final boolean positive)
			{
				super(name);
				this.setStyleName("selectAllRecipientsButton");
				addClickHandler(new ClickHandler(){					

				@Override
				public void onClick(ClickEvent event) {
					String users="";
					for(int i=0; i<recipientNamesColumn.getWidgetCount(); i++)
					{
						CheckBox cb = (CheckBox) recipientNamesColumn.getWidget(i);
						cb.setValue(positive, false);
						users=users+","+cb.getText();
					}
					
					UserLog userActionLog=new UserLog();
	            	userActionLog.setComponentType(ComponentType.FEEDBACK_OUTBOX);
	            	if(positive){
	            		userActionLog.setDescription("All users were selected.Users:"+users);
	            		userActionLog.setUserActionType(UserActionType.SELECT_ALL_USERS);
	            	} else {
	            		userActionLog.setDescription("All users were deselected.");
		            	userActionLog.setUserActionType(UserActionType.DESELECT_ALL_USERS);
	            	}
	            	userActionLog.setTriggeredBy(ComponentType.FEEDBACK_OUTBOX);
	            	Logger.getLoggerInstance().log(userActionLog);
				}				
			});
			}
		}
		
		HorizontalPanel allNone = new HorizontalPanel();
		allNone.add(new SelectAllRecipientsButton(messagesBundle.All(),true));
		allNone.add(new SelectAllRecipientsButton(messagesBundle.None(),false));
		userGroupColumn.add(allNone);

		//edit students button
		final Button editStudentsButton = new Button(messagesBundle.Edit());
		editStudentsButton.setStyleName("selectAllRecipientsButton");
		editStudentsButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				String recepientNames = "";
				for(int i=0; i<recipientNamesColumn.getWidgetCount(); i++){
					CheckBox checkbox = (CheckBox) recipientNamesColumn.getWidget(i);
					recepientNames = recepientNames + checkbox.getText() + "\n" ;
			    }
				
				UserLog userActionLog=new UserLog();
            	userActionLog.setComponentType(ComponentType.FEEDBACK_OUTBOX);
            	userActionLog.setDescription("Students  list is updated.New Users:"+recepientNames);
            	userActionLog.setUserActionType(UserActionType.UPDATE_STUDENT_LIST);
             	userActionLog.setTriggeredBy(ComponentType.FEEDBACK_OUTBOX);
            	Logger.getLoggerInstance().log(userActionLog);
				
				editRecipientNames(recepientNames);
			}
		});
		allNone.add(editStudentsButton);
		} //end of if that checks if that's the wizard interface
		
		//if there are users, list them
		if (userIDsArray != null && userIDsArray.length > 0){
		    createCheckBoxes(userIDsArray);
		    sendOptionsRow.add(userGroupColumn);
		}

				
		if (userType.equals(UserType.RECOMMENDING_WIZARD)) {
			labelAndSendButtonPanel.add(createSendSugesstionsButton());
		} else {
			//send button
			final Button sendButton = new Button(messagesBundle.Send());
			sendButton.addStyleName("sendButton");
			sendButton.addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					sendMessageToServer();
				}
			});
			
			labelAndSendButtonPanel.add(sendButton);
			labelAndSendButtonPanel.add(sectionLabel);
	
		}

		
		//vertical panel has first the send button and the label�
		vPanel.add(labelAndSendButtonPanel);

		//then the horizontal panel has the checkboxes to send
		hPanel.add(sendOptionsRow);

		hPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		//and then the text box
		messageTextArea = new TextArea();
		messageTextArea.setText("");
		messageTextArea.setPixelSize(sectionWidth, 100);		
		messageTextArea.setFocus(true);
		messageTextArea.selectAll();
		if (!UrlParameterConfig.getInstance().getUserType().equals(UserType.RECOMMENDING_WIZARD)) {
		    hPanel.add(messageTextArea);
		    hPanel.setCellHorizontalAlignment(messageTextArea, HasHorizontalAlignment.ALIGN_LEFT);

		}
		//these two are added on the hpanel
		vPanel.add(hPanel);
		
		//and all of these in the scrollpanel
		scrollPanel.add(vPanel);
		this.add(scrollPanel);
	}
	
	private Button createSendSugesstionsButton(){
		Button sendRecommendationsButton = new Button(messagesBundle.SendSuggestedMessages());
		sendRecommendationsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (suggestedMessagesController != null) {
					String suggestions = SuggestedMessagesModelParserForClient.toXML(suggestedMessagesController.getSuggestedMessagesModel());
					suggestedMessagesController.sendSuggestedMessages(suggestions);
					DisplayUtil.postNotificationMessage(messagesBundle.SuggestedMessagesSent());
				}
			}
		});
		return sendRecommendationsButton;
	}

	private void addInterruptionLevels() {
		
		sendModeRadioColumn = new VerticalPanel();
		sendModeRadioColumn.add(new Label("Interruption:"));
		sendModeRadioButtonSuggestion = new RadioButton("sendMode", HIGH_LABEL);
		sendModeRadioButtonSuggestion.setValue(true, true);
		sendModeRadioColumn.add(sendModeRadioButtonSuggestion);

		sendModeRadioButtonSuggestion = new RadioButton("sendMode", LOW_LABEL);
		sendModeRadioButtonSuggestion.setEnabled(true);
		sendModeRadioColumn.add(sendModeRadioButtonSuggestion);
		
		sendModeRadioButtonPopup = new RadioButton("sendMode", NONE_LABEL);
		sendModeRadioColumn.add(sendModeRadioButtonPopup);
		
		
		if (UrlParameterConfig.getInstance().getUserType().equals(UserType.MESSAGING_WIZARD)) {
			sendOptionsRow.add(sendModeRadioColumn);
		}
	}

	public void createCheckBoxes(String userNames[]) {
		recipientNamesColumn.clear();
		for(int i=0; i<userNames.length; i++)
		{
			final String userName = userNames[i];
			CheckBox chbox = new CheckBox(userName,true);
			chbox.setValue(false);
			recipientNamesColumn.add(chbox);
			
		}
	}
//	public TextArea getMessageTextArea()
//	{
//		return messageTextArea;
//	}
	
	public void suggestedMessageSelected(SuggestedMessage message){
		lastSelectedMessage = message;
		messageTextArea.setText(message.getText());
	}
	
	private void editRecipientNames(String recepientNames) {

		final DecoratedPopupPanel popup = new DecoratedPopupPanel(true);
		final TextArea textArea = new TextArea();
		textArea.setText(recepientNames);
		final Button createButton = new Button("Done");
		ClickHandler createClickHandler = new ClickHandler() {

		    @Override
		    public void onClick(ClickEvent event) {
		    	String s = textArea.getText();
		    	//spit the text on carriage return
		    	String[] newUserNames = s.split("\n");
		    	createCheckBoxes(newUserNames);    			
		    	popup.hide();
		    }
		    
		};
		createButton.addClickHandler(createClickHandler);
		Button cancelButton = new Button("Cancel");
		ClickHandler cancelHandler = new ClickHandler() {

		    @Override
		    public void onClick(ClickEvent event) {
			popup.hide();	
		    }
		    
		};
		cancelButton.addClickHandler(cancelHandler);
		VerticalPanel textBoxWithOKAndCancel = new VerticalPanel();
		HorizontalPanel okAndCancelPanel = new HorizontalPanel();
		okAndCancelPanel.setSpacing(6);
		okAndCancelPanel.add(createButton);
		okAndCancelPanel.add(cancelButton);
		textBoxWithOKAndCancel.setSpacing(6);
		textBoxWithOKAndCancel.add(new Label("Names (one per line)"));
		textBoxWithOKAndCancel.add(textArea);
		textBoxWithOKAndCancel.add(okAndCancelPanel);
	        popup.setWidget(textBoxWithOKAndCancel);
	        popup.show();
	        popup.center();	
	}
	
	public List<String> getSelectedRecipients() {
		List<String> selectedRecipients = new ArrayList<String>();
		for(int i=0; i<recipientNamesColumn.getWidgetCount(); i++) {
			CheckBox cb = (CheckBox) recipientNamesColumn.getWidget(i);
			if (cb.getValue()) {
				selectedRecipients.add(cb.getText());
			}
		}
		return selectedRecipients;
	}
	
	private void sendMessageToServer() {
		if(messageTextArea.getText().length()>0) {
	    	String receivingTool = UrlParameterConfig.getInstance().getReceiver();
	    	//this shouldn't be needed
			if (receivingTool == null || receivingTool.equals("")) {
				receivingTool = MetaforaStrings.RECEIVER_METAFORA_TEST;
			}
			
			List<String> sendingUsers = UrlParameterConfig.getInstance().getLoggedInUsers();
			
			List<String> objectIds = null;	
	    	if (UrlParameterConfig.getInstance().getUserType().equals(UserType.RECOMMENDING_WIZARD)) {
	 	 		String objectIdsString = objectIdsTextBox.getText();
	 	 		String[] split = objectIdsString.replaceAll("\\D*", " ").replaceAll("\\s+", ",").split(",");
	 	 		objectIds = Arrays.asList(split);
	 	 	}
	    	
	    	L2L2category l2l2category = null;
	    	if (lastSelectedMessage != null){
	    		l2l2category = lastSelectedMessage.getL2L2Category();
	    	}
	 	 		    	
	    	CfAction feedbackMessage = InterventionCreator.createDirectMessage(receivingTool, sendingUsers, getSelectedRecipients(), 
	    			UrlParameterConfig.getInstance().getGroupId(), getSelectedIntteruptionType(), messageTextArea.getText(), 
	    			l2l2category, objectIds, UrlParameterConfig.getInstance().getChallengeId(), UrlParameterConfig.getInstance().getChallengeName());
	 	 	sendMessageToServer(feedbackMessage);
	 		 	
	 		MessagesPanel.getTemplatePool().addMessageToHistory(messageTextArea.getText());
	 		messageTextArea.setText("");
    		//check if objectIdsTextBox has been created because if user type is different it will not be there
    		if (objectIdsTextBox != null) {
    			objectIdsTextBox.setText("");
    		}
	 		
    		UserLog userActionLog = new UserLog();
        	userActionLog.setComponentType(ComponentType.FEEDBACK_OUTBOX);
        	userActionLog.setUserActionType(UserActionType.SEND_FEEDBACK);
         	userActionLog.setTriggeredBy(ComponentType.FEEDBACK_OUTBOX);
         	userActionLog.addProperty("TEXT", messageTextArea.getText());
         	userActionLog.addProperty("INTERUPTION_TYPE", getSelectedIntteruptionType());
        	Logger.getLoggerInstance().log(userActionLog);	
		}
		else {
			Log.info("[sendMessageToServer] no text in message box to send");
		}
	}
	
	
	public void sendMessageToServer(CfAction cfAction) {
		XmppServerType xmppServerType = UrlParameterConfig.getInstance().getXmppServerType();
		if (xmppServerType != null){
			commServiceServlet.sendMessage(xmppServerType, cfAction, new NoActionResponse());
		}
		else {
			commServiceServlet.sendMessage(cfAction, new NoActionResponse());
		}
	}
	
	
	public void sendSuggestedMessageToServer(CfAction cfAction) {
		XmppServerType xmppServerType = UrlParameterConfig.getInstance().getXmppServerType();
		if (xmppServerType != null){
			commServiceServlet.sendSuggestedMessages(xmppServerType, cfAction, new NoActionResponse());
		}
		else {
			commServiceServlet.sendSuggestedMessages(cfAction, new NoActionResponse());
		}
	}
	
	/*
	 * Returns the selected interruption type
	 */
	public String getSelectedIntteruptionType() {
		if (UrlParameterConfig.getInstance().getUserType().equals(UserType.MESSAGING_WIZARD)) {
			for(int i=0; i<sendModeRadioColumn.getWidgetCount(); i++) {
				if (sendModeRadioColumn.getWidget(i) instanceof RadioButton) {
					RadioButton rb = (RadioButton) sendModeRadioColumn.getWidget(i);
					if (rb.getValue()) {
						return getInterruptionTypeString(rb.getText());
					}
				}
			}
		} else {
			return "HIGH_INTERRUPTION";
		}
		return null;
	}
	
	/*
	 * Returns the interruption level from None, Low, High mapped to the Metafora strings
	 */
	public String getInterruptionTypeString(String s) {
	    	if (s.equalsIgnoreCase(NONE_LABEL)) {
	    	    return MetaforaStrings.NO_INTERRUPTION;
	    	} else if (s.equalsIgnoreCase(LOW_LABEL)) {
	    	    return MetaforaStrings.LOW_INTERRUPTION;
	    	} else return MetaforaStrings.HIGH_INTERRUPTION; 
	}
	
	public void setSuggestedMessagesController(SuggestedMessagesController controller) {
		this.suggestedMessagesController = controller;
	}
}
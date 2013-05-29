package de.uds.MonitorInterventionMetafora.client.feedback;

import java.util.ArrayList;
import java.util.List;

import de.uds.MonitorInterventionMetafora.client.messages.MessagesBundle;

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
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesModel;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

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
	//private boolean recipientsSelectionButtonSetToAll=true;
	private SuggestedMessagesController suggestedMessagesController;
	private CommunicationServiceAsync commServiceServlet;
	
	
	public MessageSendingPanel(CommunicationServiceAsync commServiceServlet) {
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
		
		createCheckBoxes(MessagesPanel.userIDsArray);

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
		}
		//end of if wizard 
		sendOptionsRow.add(userGroupColumn);
				
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
			chbox.setValue(true);
			recipientNamesColumn.add(chbox);
			
		}
	}
	public TextArea getMessageTextArea()
	{
		return messageTextArea;
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
		for(int i=0; i<recipientNamesColumn.getWidgetCount(); i++)
		{
			CheckBox cb = (CheckBox) recipientNamesColumn.getWidget(i);
			if (cb.getValue()) {
				//usernames = usernames + cb.getText() + "|";
				selectedRecipients.add(cb.getText());
			}
		}
		return selectedRecipients;
	}
	
	private void sendMessageToServer() {
		CfAction feedbackMessage=new CfAction();
	 	feedbackMessage.setTime(GWTUtils.getTimeStamp());
	 	  
		 CfActionType _cfActionType=new CfActionType();
	 	 _cfActionType.setType("FEEDBACK");
	 	_cfActionType.setClassification("create");
	 	_cfActionType.setSucceed("UNKNOWN");
	 	//abusing testServer parameter to set logged or not
	 	//as of email with KP and TD "Metafora | updated XML for feedback messages"
	 	String testServer = UrlParameterConfig.getInstance().getTestServer() + "";
	 	_cfActionType.setLogged(testServer);
	 	 	
 	 	feedbackMessage.setCfActionType(_cfActionType);

 	 	for (String userId : getSelectedRecipients()) {
 	 		feedbackMessage.addUser(new CfUser(userId, "receiver"));
 	 	}
 	 	
 	 	//TODO: what happens with IDs? And do they matter? 
 	 	//Can we uniquely auto-increment them? Perhaps use that java UUID library? 
 	 	CfObject cfObject = new CfObject("0", MetaforaStrings.PROPERTY_VALUE_MESSAGE_STRING);
 	 	
 	 	cfObject.addProperty(new CfProperty("INTERRUPTION_TYPE", getSelectedIntteruptionType()));
 	 	cfObject.addProperty(new CfProperty("TEXT", messageTextArea.getText()));
 	 	cfObject.addProperty(new CfProperty("GROUP_ID",UrlParameterConfig.getInstance().getGroupId()));
 	 	feedbackMessage.addObject(cfObject);

 	 	if (UrlParameterConfig.getInstance().getUserType().equals(UserType.RECOMMENDING_WIZARD)) {
 	 		String objectIdsString = objectIdsTextBox.getText();
 	 		String[] split = objectIdsString.replaceAll("\\D*", " ").replaceAll("\\s+", ",").split(",");
 	 		for (String id : split) {
 	 			if (!"".equals(id))
 	 				feedbackMessage.addObject(new CfObject(id, MetaforaStrings.REFERABLE_OBJECT_STRING));
 	 		}
 	 	}
 	 	
 	 	
		String receiver = UrlParameterConfig.getInstance().getReceiver();
		//this shouldn't be needed but jic
		if (receiver == null || receiver.equals("")) {
			receiver = MetaforaStrings.RECEIVER_METAFORA_TEST;
		}
		
		CfContent myContent = new CfContent();
		myContent.addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_RECEIVING_TOOL,receiver));
		myContent.addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_SENDING_TOOL, MetaforaStrings.MONITOR_AND_MESSAGE_TOOL_NAME));
 	 	feedbackMessage.setCfContent(myContent);

	 	sendActionToServer(feedbackMessage);
	 	if(messageTextArea.getText().length()>0)
	 	{
	 		MessagesPanel.getTemplatePool().addMessageToHistory(messageTextArea.getText());
	 	}
	
		UserLog userActionLog = new UserLog();
    	userActionLog.setComponentType(ComponentType.FEEDBACK_OUTBOX);
    	//userActionLog.setDescription("Wizard sent feedback to the students:"+usernames+", Message: "+messageTextArea.getValue());
    	userActionLog.setUserActionType(UserActionType.SEND_FEEDBACK);
     	userActionLog.setTriggeredBy(ComponentType.FEEDBACK_OUTBOX);
     	//userActionLog.addProperty("USER_NAMES", usernames);
     	userActionLog.addProperty("TEXT", messageTextArea.getText());
     	userActionLog.addProperty("INTERUPTION_TYPE", getSelectedIntteruptionType());
    	Logger.getLoggerInstance().log(userActionLog);
		
    	messageTextArea.setText("");
	//check if objectIdsTextBox has been created because if user type is different it will not be there
	if (objectIdsTextBox != null) objectIdsTextBox.setText("");
	}
	
	public void sendActionToServer(CfAction cfAction) {
		XmppServerType xmppServerType = UrlParameterConfig.getInstance().getXmppServerType();
		if (xmppServerType != null){
			commServiceServlet.sendAction(xmppServerType, MetaforaStrings.MONITOR_AND_MESSAGE_TOOL_NAME,cfAction, new NoActionResponse());
		}
		else {
			commServiceServlet.sendAction(MetaforaStrings.MONITOR_AND_MESSAGE_TOOL_NAME,cfAction, new NoActionResponse());
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
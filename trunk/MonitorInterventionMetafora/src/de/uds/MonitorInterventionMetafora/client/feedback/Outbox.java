package de.uds.MonitorInterventionMetafora.client.feedback;

import java.util.ArrayList;
import java.util.List;

import de.uds.MonitorInterventionMetafora.client.messages.MessagesBundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

import de.uds.MonitorInterventionMetafora.client.communication.ServerCommunication;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.CfActionCallBack;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.NoActionResponse;
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
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

public class Outbox extends VerticalPanel implements CfActionCallBack {
    
    
    	public static MessagesBundle messagesBundle = GWT.create(MessagesBundle.class);

	private int PANEL_HEIGHT = 290;
	
	private TextArea messageTextArea;
	private TextBox objectIdsTextBox;
	private VerticalPanel vPanel;
	private VerticalPanel sendModeRadioColumn;
	private HorizontalPanel sendOptionsRow;
	private HorizontalPanel objectIdsRow;
	public RadioButton sendModeRadioButtonPopup;
	public RadioButton sendModeRadioButtonSuggestion;
	public RadioButton sendModeRadioButtonResponse;
	public int sectionWidth = 400;
	public VerticalPanel recipientNamesColumn;
	//private boolean recipientsSelectionButtonSetToAll=true;
	private String TOOL_NAME = "FEEDBACK_CLIENT";
	private ClientFeedbackDataModelUpdater feedbackDataModelUpdater;
	private SuggestedMessagesController suggestedMessagesController;
	
	
	public Outbox(/*ComplexPanel parent, */ClientFeedbackDataModelUpdater updater)
	{
		this.feedbackDataModelUpdater = updater;
		
//		final ScrollPanel scrollPanel = new ScrollPanel();
		vPanel = new VerticalPanel();
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setHeight(PANEL_HEIGHT +"px");
		scrollPanel.setAlwaysShowScrollBars(false);

		
		//section label
		String receiver = UrlParameterConfig.getInstance().getReceiver();
		final Label sectionLabel = new Label(messagesBundle.EditInstructions());
		sectionLabel.setStyleName("sectionLabel");
		vPanel.add(sectionLabel);

		UserType userType = UrlParameterConfig.getInstance().getUserType();

		//text box
		addMessageTextArea();


		//send options
		sendOptionsRow = new HorizontalPanel();
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
//			this.add(objectIdsRow);
			vPanel.add(objectIdsRow);
		}
		
		// send mode
		addInterruptionLevels();
		
		//recipients
		final VerticalPanel userGroupColumn = new VerticalPanel();
		userGroupColumn.add(new Label(messagesBundle.To()));
		recipientNamesColumn = new VerticalPanel();
		userGroupColumn.add(recipientNamesColumn);
		
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
		createCheckBoxes(FeedbackPanelContainer.userIDsArray);

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
		sendOptionsRow.add(userGroupColumn);
		
		VerticalPanel buttonsVPanel = new VerticalPanel();
		
		if (userType.equals(UserType.RECOMMENDING_WIZARD)) {
			Button sendRecommendationsButton = new Button("Send recommendations");
			sendRecommendationsButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (suggestedMessagesController != null) {
						String suggestions = SuggestedMessagesModel.toXML(suggestedMessagesController.getSuggestedMessagesModel());
						suggestedMessagesController.sendSuggestedMessages(suggestions);
					}
				}
			});
			buttonsVPanel.add(sendRecommendationsButton);
		} else {
			//send button
			final Button sendButton = new Button(messagesBundle.Send());
			sendButton.addStyleName("sendButton");
			sendButton.addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					sendMessageToServer();
				}
			});
			
			sendOptionsRow.setWidth(""+sectionWidth+"px");
			sendOptionsRow.setCellHorizontalAlignment(sendButton, HasHorizontalAlignment.ALIGN_RIGHT);
			buttonsVPanel.add(sendButton);			
		}
		
		sendOptionsRow.add(buttonsVPanel);
		vPanel.add(sendOptionsRow);
		scrollPanel.add(vPanel);
		this.add(scrollPanel);
	}
	
	private void addMessageTextArea() {
		messageTextArea = new TextArea();
		messageTextArea.setText("");
		messageTextArea.setPixelSize(sectionWidth, 100);		
		messageTextArea.setFocus(true);
		messageTextArea.selectAll();
		if (!UrlParameterConfig.getInstance().getUserType().equals(UserType.RECOMMENDING_WIZARD))
			vPanel.add(messageTextArea);		
	}

	private void addInterruptionLevels() {
		
		sendModeRadioColumn = new VerticalPanel();
		sendModeRadioColumn.add(new Label("Interruption:"));
		sendModeRadioButtonSuggestion = new RadioButton("sendMode", "High");
		sendModeRadioButtonSuggestion.setValue(true, true);
		sendModeRadioColumn.add(sendModeRadioButtonSuggestion);

		sendModeRadioButtonSuggestion = new RadioButton("sendMode", "Low");
		sendModeRadioButtonSuggestion.setEnabled(true);
		sendModeRadioColumn.add(sendModeRadioButtonSuggestion);
		
		sendModeRadioButtonPopup = new RadioButton("sendMode", "None");
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
			recipientNamesColumn.add(new CheckBox(userName));
			
		}
	}
	public TextArea getMessageTextArea()
	{
		return messageTextArea;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSuccess(CfAction result) {
		// TODO Auto-generated method stub
		
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
		myContent.addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_SENDING_TOOL,TOOL_NAME));
 	 	feedbackMessage.setCfContent(myContent);

	 	processAction(feedbackMessage);
	 	if(messageTextArea.getText().length()>0)
	 	{
	 		FeedbackPanelContainer.getTemplatePool().addMessageToHistory(messageTextArea.getText());
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
	
	public void processAction(CfAction cfAction) {
		ServerCommunication.getInstance().sendAction("FeedbackClient", cfAction, this);	
		if (UrlParameterConfig.getInstance().getXmppServerType() != null){
			ServerCommunication.getInstance().sendAction(UrlParameterConfig.getInstance().getXmppServerType(), "FeedbackClient", cfAction, new NoActionResponse());	
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
			return "High";
		}
		return null;
	}
	
	/*
	 * Returns a space separated string in lowercase and hyphenated 
	 */
	public String getInterruptionTypeString(String s) {
		String result = s.toLowerCase(); 
		result = result.replace(' ', '_');
		return result;
	}
	
	public void setSuggestedMessagesController(SuggestedMessagesController controller) {
		this.suggestedMessagesController = controller;
	}
}
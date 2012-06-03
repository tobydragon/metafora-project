package de.uds.MonitorInterventionMetafora.client.feedback;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

import de.uds.MonitorInterventionMetafora.client.communication.ServerCommunication;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.CfActionCallBack;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

public class Outbox implements CfActionCallBack {
	
	private TextArea messageTextArea;
	private VerticalPanel vpanel,sendModeRadioColumn;
	private HorizontalPanel sendOptionsRow;
	public RadioButton sendModeRadioButtonPopup;
	public RadioButton sendModeRadioButtonSuggestion;
	public RadioButton sendModeRadioButtonResponse;
	public int sectionWidth = 400;
	public VerticalPanel recipientNamesColumn;
//	private boolean recipientsSelectionButtonSetToAll=true;
	
	public Outbox(ComplexPanel parent)
	{
		vpanel = new VerticalPanel();		
		parent.add(vpanel);
		
		//section label
		final Label sectionLabel = new Label("Type your message below");
		sectionLabel.setStyleName("sectionLabel");
		vpanel.add(sectionLabel);

		//text box
		messageTextArea = new TextArea();
		messageTextArea.setText("");
		messageTextArea.setPixelSize(sectionWidth, 100);		
		messageTextArea.setFocus(true);
		messageTextArea.selectAll();
		vpanel.add(messageTextArea);
		//vpanel.setce;

		//send options
		sendOptionsRow = new HorizontalPanel();
		sendOptionsRow.setSpacing(3);
		sendOptionsRow.add(new Label("send as"));
		//send mode
		sendModeRadioColumn = new VerticalPanel();
		sendModeRadioButtonPopup = new RadioButton("sendMode", "No Interruption");
		sendModeRadioButtonPopup.setValue(true, true);
		sendModeRadioColumn.add(sendModeRadioButtonPopup);
		sendModeRadioButtonSuggestion = new RadioButton("sendMode", "Low Interruption");
		sendModeRadioButtonSuggestion.setEnabled(true);
		sendModeRadioColumn.add(sendModeRadioButtonSuggestion);
		sendModeRadioButtonSuggestion = new RadioButton("sendMode", "High Interruption");
		sendModeRadioColumn.add(sendModeRadioButtonSuggestion);
		//TODO: not needed anymore. Remove
		sendModeRadioButtonResponse = new RadioButton("sendMode", "Response to request");
		sendModeRadioButtonResponse.setEnabled(false);
		//sendModeRadioColumn.add(sendModeRadioButtonResponse);
		sendOptionsRow.add(sendModeRadioColumn);
		vpanel.add(sendOptionsRow);
		sendOptionsRow.add(new Label("to"));
		//recipients
		final VerticalPanel userGroupColumn = new VerticalPanel();
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
					for(int i=0; i<=recipientNamesColumn.getWidgetCount(); i++)
					{
						CheckBox cb = (CheckBox) recipientNamesColumn.getWidget(i);
						cb.setValue(positive, false);
					}					
				}				
			});
			}
		}
		HorizontalPanel allNone = new HorizontalPanel();
		allNone.add(new SelectAllRecipientsButton("all",true));
		allNone.add(new SelectAllRecipientsButton("none",false));
		userGroupColumn.add(allNone);
		
		createCheckBoxes(FeedbackPanelContainer.userNames);

		//edit students button
		final Button editStudentsButton = new Button("edit");
		editStudentsButton.setStyleName("selectAllRecipientsButton");
		editStudentsButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				String recepientNames = "";
				for(int i=0; i<recipientNamesColumn.getWidgetCount(); i++){
					CheckBox checkbox = (CheckBox) recipientNamesColumn.getWidget(i);
					recepientNames = recepientNames + checkbox.getText() + "\n" ;
			    }
				editRecipientNames(recepientNames);
			}
		});
		allNone.add(editStudentsButton);
		sendOptionsRow.add(userGroupColumn);

		
		//send button
		final Button sendButton = new Button("Send");
		sendButton.addStyleName("sendButton");
		sendButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				sendMessageToServer();
			}
		});

		sendOptionsRow.add(sendButton);
		sendOptionsRow.setWidth(""+sectionWidth+"px");
		sendOptionsRow.setCellWidth(sendButton, "70");
		sendOptionsRow.setCellHorizontalAlignment(sendButton, HasHorizontalAlignment.ALIGN_RIGHT);
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
		final Button createButton = new Button("Create");
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
		textBoxWithOKAndCancel.add(textArea);
		textBoxWithOKAndCancel.add(okAndCancelPanel);
	        popup.setWidget(textBoxWithOKAndCancel);
	        popup.show();
	        popup.center();	
	}
	
	private void sendMessageToServer() {
		CfAction feedbackMessage=new CfAction();
	 	feedbackMessage.setTime(GWTUtils.getTimeStamp());
	 	  
		 CfActionType _cfActionType=new CfActionType();
	 	 _cfActionType.setType("feedback");
	 	_cfActionType.setClassification("create");
	 	_cfActionType.setSucceed("UNKOWN");
	 	_cfActionType.setLogged("true");
	 	
 	 	feedbackMessage.setCfActionType(_cfActionType);
 	 	feedbackMessage.addUser(new CfUser("FeedbackClient", MetaforaStrings.USER_ROLE_ORIGINATOR_STRING));
 	 	feedbackMessage.addUser(new CfUser("Metafora", MetaforaStrings.USER_ROLE_RECEIVER_STRING));
 	 	
 	 	//TODO: what happens with IDs? And do they matter? 
 	 	//Can we uniquely auto-increment them? Perhaps use that java UUID library? 
 	 	CfObject cfObject = new CfObject("0","message");
		for(int i=0; i<recipientNamesColumn.getWidgetCount(); i++)
		{
			CheckBox cb = (CheckBox) recipientNamesColumn.getWidget(i);
			if (cb.getValue()) {
				//TODO: deal with multiple usernames (properties can't have same name value)
				cfObject.addProperty(new CfProperty("username",cb.getText()));				
			}
		}					
 	 	cfObject.addProperty(new CfProperty("text",messageTextArea.getText()));	
 	 	cfObject.addProperty(new CfProperty("interruption_type",getSelectedIntteruptionType()));
 	 	feedbackMessage.addObject(cfObject);
 	 	
	 	ServerCommunication.getInstance().processAction("FeedbackClient",feedbackMessage,this);	

	 	if(messageTextArea.getText().length()>0)
	 	{
	 		FeedbackPanelContainer.getTemplatePool().addMessageToHistory(messageTextArea.getText());
	 	}
		messageTextArea.setText("");
		
		
		
	}
	
	/*
	 * Returns the selected interruption type
	 */
	public String getSelectedIntteruptionType() {

		for(int i=0; i<sendModeRadioColumn.getWidgetCount(); i++) {
			RadioButton rb = (RadioButton) sendModeRadioColumn.getWidget(i);
			if (rb.getValue()) {
				return getInterruptionTypeString(rb.getText());
			}
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
}
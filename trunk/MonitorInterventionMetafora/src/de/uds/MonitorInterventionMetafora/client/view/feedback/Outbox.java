package de.uds.MonitorInterventionMetafora.client.view.feedback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

import de.uds.MonitorInterventionMetafora.client.actionresponse.CfActionCallBack;
import de.uds.MonitorInterventionMetafora.client.communication.ServerCommunication;
import de.uds.MonitorInterventionMetafora.client.view.containers.FeedbackPanelContainer;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

public class Outbox implements CfActionCallBack {
	private TextBox messageTextBox;
	private VerticalPanel vpanel;
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
		messageTextBox = new TextBox();
		messageTextBox.setText("");
		messageTextBox.setPixelSize(sectionWidth, 100);		
		messageTextBox.setFocus(true);
		messageTextBox.selectAll();
		vpanel.add(messageTextBox);
		//vpanel.setce;

		//send options
		sendOptionsRow = new HorizontalPanel();
		sendOptionsRow.setSpacing(3);
		sendOptionsRow.add(new Label("send as"));
		//send mode
		VerticalPanel sendModeRadioColumn = new VerticalPanel();
		sendModeRadioButtonPopup = new RadioButton("sendMode", "No Interruption");
		ClickHandler noRequestClickHandler = new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				FeedbackPanelContainer.getRequestStack().clearDetails();
				FeedbackPanelContainer.getRequestResponse().setEnabled(false);
			}
		};
		sendModeRadioButtonPopup.setValue(true, true);
		sendModeRadioButtonPopup.addClickHandler(noRequestClickHandler);
		sendModeRadioColumn.add(sendModeRadioButtonPopup);
		sendModeRadioButtonSuggestion = new RadioButton("sendMode", "Low Interruption");
		sendModeRadioButtonSuggestion.addClickHandler(noRequestClickHandler);
		sendModeRadioColumn.add(sendModeRadioButtonSuggestion);
		sendModeRadioButtonSuggestion = new RadioButton("sendMode", "High Interruption");
		sendModeRadioButtonSuggestion.addClickHandler(noRequestClickHandler);
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
		String[] userNames = FeedbackPanelContainer.userNames;
		for(int i=0; i<userNames.length; i++)
		{
			final String userName = userNames[i];
			recipientNamesColumn.add(new CheckBox(userName));
			
		}
		class SelectAllRecipientsButton extends Button{
			SelectAllRecipientsButton(String name, final boolean positive)
			{
				super(name);
				this.setStyleName("selectAllRecipientsButton");
				addClickHandler(new ClickHandler(){					

				@Override
				public void onClick(ClickEvent event) {
					for(int i=0; i<recipientNamesColumn.getWidgetCount(); i++)
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
		sendOptionsRow.add(userGroupColumn);
		//send button
		final Button sendButton = new Button("Send");
		sendButton.addStyleName("sendButton");
		sendButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}


		});
		sendOptionsRow.add(sendButton);
		sendOptionsRow.setWidth(""+sectionWidth+"px");
		sendOptionsRow.setCellWidth(sendButton, "70");
		sendOptionsRow.setCellHorizontalAlignment(sendButton, HasHorizontalAlignment.ALIGN_RIGHT);
	}
	public TextBox getMessageTextBox()
	{
		return messageTextBox;
	}
	public void incomingRequest(UserRequest r) {
		sendModeRadioButtonResponse.setValue(true, true);
		//select user as recipient
		for(int i=0; i<recipientNamesColumn.getWidgetCount(); i++)
		{
			CheckBox cb = (CheckBox) recipientNamesColumn.getWidget(i);
			cb.setValue(cb.getText().equals(r.name));
		}
	}
	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSuccess(CfAction result) {
		// TODO Auto-generated method stub
		
	}
	
	private void sendNameToServer() {
		//String textToServer = messageTextBox.getText();
		System.out.println("Sending messages being tested (again)");
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
 	 	feedbackMessage.addUser(new CfUser("Bob", "student"));
 	 	feedbackMessage.addUser(new CfUser("Alice", "student"));
 	 	
 	 	CfContent cfContent = new CfContent("Please mind the gap!");
 	 	cfContent.addProperty(new CfProperty("FEEDBACK_TYPE","NO_INTERRUPTION"));
 	 	feedbackMessage.setCfContent(cfContent);
 	 	
	 	ServerCommunication.getInstance().processAction("FeedbackClient",feedbackMessage,this);	

		messageTextBox.setText("");
		if(sendModeRadioButtonResponse.getValue())
		{
			FeedbackPanelContainer.getInstance().declareRequestHandled();
		}
	}
}
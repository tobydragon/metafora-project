package de.uds.MonitorInterventionMetafora.client.view.intervention;

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

import de.uds.MonitorInterventionMetafora.client.view.containers.InterventionPanelContainer;

public class Outbox {
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
				InterventionPanelContainer.getRequestStack().clearDetails();
				InterventionPanelContainer.getRequestResponse().setEnabled(false);
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
		String[] userNames = InterventionPanelContainer.userNames;
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

			private void sendNameToServer() {
				//String textToServer = messageTextBox.getText();
				//Window.alert("Sending messages not yet implemented");
				messageTextBox.setText("");
				if(sendModeRadioButtonResponse.getValue())
				{
					InterventionPanelContainer.getInstance().declareRequestHandled();
				}
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
}
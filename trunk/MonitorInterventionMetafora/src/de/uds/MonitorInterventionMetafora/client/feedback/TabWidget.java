package de.uds.MonitorInterventionMetafora.client.feedback;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.uds.MonitorInterventionMetafora.client.logger.ComponentType;
import de.uds.MonitorInterventionMetafora.client.logger.Logger;
import de.uds.MonitorInterventionMetafora.client.logger.UserActionType;
import de.uds.MonitorInterventionMetafora.client.logger.UserLog;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig.UserType;

public class TabWidget {
	String title = "untitled";
	protected VerticalPanel mainVPanel;
	protected VerticalPanel headerVPanel;
	protected VerticalPanel buttonsVPanel;
	
	private ScrollPanel buttonsScrollPanel;

	// Controller
	protected SuggestedMessagesController controller;
	
	public TabWidget(String title) {
		this.title = title;
		mainVPanel = new VerticalPanel();
		mainVPanel.setSpacing(3);
		headerVPanel = new VerticalPanel();
		headerVPanel.setWidth("100%");

		buttonsScrollPanel = new ScrollPanel();
		buttonsScrollPanel.setWidth("100%");
		buttonsScrollPanel.setHeight("450px");
		buttonsVPanel = new VerticalPanel();
//		buttonsVPanel.setSpacing(2);
		buttonsScrollPanel.add(buttonsVPanel);
		
		mainVPanel.add(headerVPanel);
		mainVPanel.add(buttonsScrollPanel);
	}

	/**
	 *  Adds some parameters to set 'tab color', 'add new message'
	 */
	public void enableTabConfig() {
		HorizontalPanel configPanel = new HorizontalPanel();
//		configPanel.setSpacing(5);
		
		Label tabColorLabel = new Label("Highlight tab:");
		CheckBox highlightCheckBox = new CheckBox();
		highlightCheckBox.setTitle("Set tab as highlighted?");
		highlightCheckBox.setValue(controller.getSuggestedMessagesModel().getSuggestionCategory(title).isHighlight());
		highlightCheckBox.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int selectedTab = controller.getView().getTabBar().getSelectedTab();
				CheckBox source = ((CheckBox) event.getSource());
				controller.highlightCategory(title, source.getValue());
				controller.refreshTabs();
				controller.getView().getTabBar().selectTab(selectedTab);
			}
		});
		configPanel.add(tabColorLabel);
		configPanel.add(highlightCheckBox);
		headerVPanel.add(configPanel);

		// Add new message
		Button addMessageButton = new Button("Add new message");
		addMessageButton.setTitle("Add suggested message to \"" +title+ "\" tab.");
		addMessageButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				System.err.println("Add new message");
				
				MessageBox box = MessageBox.prompt("Add new message", "Please type the text of a message:", true);
			    box.addCallback(new Listener<MessageBoxEvent>() {
			    	public void handleEvent(MessageBoxEvent be) {
			    		if (be.getValue() != null && !be.getValue().equals("")) {
				    		int selectedTab = controller.getView().getTabBar().getSelectedTab();
				    		controller.addNewMessage(title, be.getValue()); 
				    		controller.refreshTabs();
				    		controller.getView().getTabBar().selectTab(selectedTab);
			    		}
			    	}
			    });
				
			}
		});
		buttonsVPanel.add(addMessageButton);
	}
	
	private void deleteMessage(String title, int rowIndex, int selectedTab) {		
		controller.removeMessage(title, rowIndex);
		controller.refreshTabs();
		controller.getView().getTabBar().selectTab(selectedTab);
	}
	
	public void addSuggestedMessageRow(final SuggestedMessage message, final String tabTitle) {
		HorizontalPanel row = new HorizontalPanel();
		row.setSpacing(1);
		if (UrlParameterConfig.getInstance().getUserType().equals(UserType.POWER_WIZARD)) {
			com.extjs.gxt.ui.client.widget.button.Button deleteButton = new com.extjs.gxt.ui.client.widget.button.Button();
			deleteButton.setIconStyle("delete_suggested_message_button");
			deleteButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent event) {
					final int selectedTab = controller.getView().getTabBar().getSelectedTab();
					com.extjs.gxt.ui.client.widget.button.Button source = (com.extjs.gxt.ui.client.widget.button.Button) event.getSource();
					Widget hpanel = source.getParent();
					final int rowIndex = ((VerticalPanel)hpanel.getParent()).getWidgetIndex(hpanel);
					final Listener<MessageBoxEvent> l = new Listener<MessageBoxEvent>() {
						public void handleEvent(MessageBoxEvent be) {
							if (be.getButtonClicked().getText().equalsIgnoreCase("yes")) {
								deleteMessage(tabTitle, rowIndex, selectedTab);
							}
						}
					};
					MessageBox.confirm("Confirm", "Are you sure you want to delete this message?", l);
				}
			});
			row.add(deleteButton);
			
			CheckBox checkBox = new CheckBox();
			checkBox.setTitle("Highlight?");
			checkBox.setValue(message.isHighlight());
			checkBox.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					int selectedTab = controller.getView().getTabBar().getSelectedTab();
					CheckBox source = ((CheckBox) event.getSource());
					HorizontalPanel horizontalPanel = (HorizontalPanel) source.getParent();
					
					int checkBoxIndex = horizontalPanel.getWidgetIndex(source);
					int rowIndex = ((VerticalPanel) horizontalPanel.getParent()).getWidgetIndex(horizontalPanel);
					
					// next widget to checkbox is our message-suggestion-button
					Button button = (Button) horizontalPanel.getWidget(checkBoxIndex+1);
					button.setStyleDependentName("highlight", source.getValue());
					controller.changeMessageStyle(title, rowIndex, source.getValue());	// make it bold
					controller.refreshTabs();
					controller.getView().getTabBar().selectTab(selectedTab);
				}
			});
			row.add(checkBox);
		}
		
		String msgText = message.getText();
		Button b = new Button(msgText);
		if (message.isHighlight()) 
			b.addStyleDependentName("highlight");
		b.setWidth("460px");
		b.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				FeedbackPanelContainer.getMessageTextArea().setText(message.getText());
				UserLog userActionLog = new UserLog();
				userActionLog.setComponentType(ComponentType.FEEDBACK_TEMPLATE_POOL);
				userActionLog.setDescription("Text selection for feedback: Feedback_Type=" + tabTitle + "," + "Text=" + message.getText());
				userActionLog.setUserActionType(UserActionType.FEEDBACK_TEXT_SELECTION);
				userActionLog.setTriggeredBy(ComponentType.FEEDBACK_TEMPLATE_POOL);
				userActionLog.addProperty("FEEDBACK_TYPE", tabTitle);
				userActionLog.addProperty("FEEDBACK_TEXT", message.getText());

				Logger.getLoggerInstance().log(userActionLog);
			}
		});
		row.add(b);
		buttonsVPanel.add(row);
	}
	
//	public VerticalPanel getButtonsVPanel() {
//		return buttonsVPanel;
//	}

	public void setController(SuggestedMessagesController controller) {
		this.controller = controller;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public VerticalPanel getMainVPanel() {
		return mainVPanel;
	}
}
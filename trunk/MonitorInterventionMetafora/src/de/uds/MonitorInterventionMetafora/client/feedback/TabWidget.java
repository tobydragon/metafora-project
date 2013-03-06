package de.uds.MonitorInterventionMetafora.client.feedback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

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
	
	public TabWidget() {
		mainVPanel = new VerticalPanel();
		// mainVPanel.
		headerVPanel = new VerticalPanel();
		buttonsScrollPanel = new ScrollPanel();
		buttonsScrollPanel.setWidth("500px");
		buttonsScrollPanel.setHeight("450px");
		buttonsVPanel = new VerticalPanel();
		buttonsScrollPanel.add(buttonsVPanel);
		mainVPanel.add(headerVPanel);
		mainVPanel.add(buttonsScrollPanel);
	}

	public void addSuggestedMessageRow(final SuggestedMessage message, final String tabTitle) {
		HorizontalPanel row = new HorizontalPanel();
		if (UrlParameterConfig.getInstance().getUserType().equals(UserType.POWER_WIZARD)) {
			CheckBox checkBox = new CheckBox();
			checkBox.setValue(message.isBold());
			checkBox.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					CheckBox source = ((CheckBox) event.getSource());
					HorizontalPanel horizontalPanel = (HorizontalPanel) source.getParent();
					
					int checkBoxIndex = horizontalPanel.getWidgetIndex(source);
					int rowIndex = ((VerticalPanel) horizontalPanel.getParent()).getWidgetIndex(horizontalPanel);
					
					// next widget to checkbox is our message-suggestion-button
					Button button = (Button) horizontalPanel.getWidget(checkBoxIndex+1);
					String buttonText = button.getText();
					if (source.getValue()) {
						button.setHTML("<b>" +buttonText+ "</b>");
						controller.changeMessageStyle(getTitle(), rowIndex, true);	// make it bold
					} else {
						buttonText = buttonText.replaceAll("<b>", "").replaceAll("</b>", "");
						button.setHTML(buttonText);
						controller.changeMessageStyle(getTitle(), rowIndex, false);	// make it NOT bold
					}
					
				}
			});
			row.add(checkBox);
		}
		
		String msgText = message.isBold() ? "<b>" +message.getText()+ "</b>" : message.getText();
		Button b = new Button(msgText);
		b.setWidth("480px");
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
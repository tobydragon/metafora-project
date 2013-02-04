package de.uds.MonitorInterventionMetafora.client.feedback;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

import de.uds.MonitorInterventionMetafora.client.logger.ComponentType;
import de.uds.MonitorInterventionMetafora.client.logger.Logger;
import de.uds.MonitorInterventionMetafora.client.logger.UserActionType;
import de.uds.MonitorInterventionMetafora.client.logger.UserLog;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

public class TemplatePool {
	private VerticalPanel vpanel;
	private TabBar tabBar;
	private ArrayList<TabWidget> tabWidgets;
	private HistoryTabWidget messageHistory;
	private VerticalPanel xmlVPanel;
	private String tabTitle = "";

	public TemplatePool(ComplexPanel parent, String XML) {
		vpanel = new VerticalPanel();
		// vpanel.setSpacing(20);
		parent.add(vpanel);

		// section label
		final Label sectionLabel = new Label("Select any of the message templates from any of the tabs below");
		sectionLabel.setStyleName("sectionLabel");
		vpanel.add(sectionLabel);

		tabBar = new TabBar();

		// create and populate TABs based on XML
		populateTabs(XML);

		final ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setHeight("500px");
		// scrollPanel.setWidget(tools.get(0).getButtonsVPanel());
		tabBar.addSelectionHandler(new SelectionHandler<Integer>() {

			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if (tabBar.getTabHTML(event.getSelectedItem()).equals("XML")) {
					scrollPanel.setWidget(xmlVPanel);
				} else {
					scrollPanel.setWidget(tabWidgets.get(
							event.getSelectedItem()).getMainVPanel());
				}

				tabTitle = tabBar.getTabHTML(event.getSelectedItem());

			}

		});
		tabBar.selectTab(0, true);

		vpanel.add(tabBar);
		vpanel.add(scrollPanel);
	}

	private void populateTabs(String XML) {
		// create new list of tab widgets
		tabWidgets = new ArrayList<TabWidget>();
		// clean tabs
		int numberOfTabsToremove = tabBar.getTabCount();
		for (int i = 0; i < numberOfTabsToremove; i++) {
			// Yes, it is removeTab(0) not i. You need to remove always the
			// first
			// because they get re-numbered !
			tabBar.removeTab(0);
		}

		// create tabs based on top-level XML nodes
		Document xmlDocument = XMLParser.parse(XML);
		Element docElement = xmlDocument.getDocumentElement();
		XMLParser.removeWhitespace(docElement);

		// get the elements called <sets>
		NodeList sets = docElement.getElementsByTagName("set");
		int numberOfSets = sets.getLength();
		for (int iset = 0; iset < numberOfSets; iset++) {
			Element setOfMessages = (Element) sets.item(iset);
			String tabTitle = setOfMessages.getAttributes().getNamedItem("id").getNodeValue();
			// check here if there are more sets
			NodeList messages = setOfMessages.getElementsByTagName("message");
			int numberOfMessages = messages.getLength();
			TabWidget tabWidget = new TabWidget();
			tabWidgets.add(tabWidget);
			tabWidget.setTitle(tabTitle);
			if (iset == 0) {
				this.tabTitle = tabTitle;
			}
			tabBar.addTab(tabTitle);

			for (int imessage = 0; imessage < numberOfMessages; imessage++) {
				Node messageItem = messages.item(imessage);
				String msgText = messageItem.getFirstChild().getNodeValue();
				StringBuffer strBuffer = new StringBuffer(msgText);
				if (messageItem.hasAttributes()) {
					NamedNodeMap msgAttributes = messageItem.getAttributes();
					for (int iattribute = 0; iattribute < msgAttributes.getLength(); iattribute++) {
						Node msgAttribute = msgAttributes.item(iattribute);
						if (msgAttribute.getNodeName().equals("style") && msgAttribute.getNodeValue().equals("bold")) {
							strBuffer.insert(0, "<b>");
							strBuffer.append("</b>");
						}
					}
				}
				addButtonToTabWidget(tabWidget, strBuffer.toString());
			}
		}

		// create tab for message history
		messageHistory = new HistoryTabWidget();
		String sentMessagesTitle = "Sent";
		messageHistory.setTitle(sentMessagesTitle);
		tabWidgets.add(messageHistory);
		tabBar.insertTab(sentMessagesTitle, tabBar.getTabCount());

		// create tab for the XML content
		tabBar.addTab("XML");

		xmlVPanel = new VerticalPanel();
		final TextArea textArea = new TextArea();
		textArea.setText(XML);
		textArea.setSize("480px", "450px");

		// Re-populate button
		Button repopulateButton = new Button("Re-populate");
		repopulateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				populateTabs(textArea.getText());
			}
		});
		
		// Send button
		Button sendButton = new Button("Send");
		sendButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendSuggestedMessages(textArea.getText());
			}
		});
		
		// Refresh button
		Button refreshButton = new Button("Refresh");
		refreshButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateMessages();
			}

		});
		
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		buttonsPanel.setSpacing(3);
		buttonsPanel.add(repopulateButton);
		buttonsPanel.add(sendButton);
		buttonsPanel.add(refreshButton);
		xmlVPanel.add(buttonsPanel);
		
		xmlVPanel.add(textArea);
		
	}

	/**
	 * 
	 * @param XML
	 */
	private void sendSuggestedMessages(String XML) {
		Outbox outbox = FeedbackPanelContainer.getInstance().getOutbox();
		List<String> userIds = outbox.getSelectedRecipients();
		
		// Create cfAction
		CfActionType cfActionType = new CfActionType();
		cfActionType.setType(MetaforaStrings.ACTION_TYPE_SUGGESTED_MESSAGES_STRING);
		cfActionType.setClassification("create");
		cfActionType.setSucceed(MetaforaStrings.ACTION_TYPE_SUCCEEDED_UNKNOWN_STRING);
		cfActionType.setLogged("false");

		CfAction cfAction = new CfAction(GWTUtils.getTimeStamp(), cfActionType);
		for (String userId : userIds) {
			cfAction.addUser(new CfUser(userId, "receiver"));
		}
		
		String receiver = UrlParameterConfig.getInstance().getReceiver();
		receiver = (receiver == null || receiver.equals("")) ? MetaforaStrings.RECEIVER_METAFORA : receiver; 
				
		CfContent cfContent = new CfContent("<![CDATA[ <suggestions>" + XML + "</suggestions> ]]>");
		cfContent.addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_RECEIVING_TOOL, receiver));
		cfContent.addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_SENDING_TOOL,"FEEDBACK_CLIENT"));
		
		cfAction.setCfContent(cfContent);
		
//		outbox.processAction(cfAction);
	}

	/**
	 * 
	 */
	private void updateMessages() {
		// TODO Auto-generated method stub
		
	}
	
	private void addButtonToTabWidget(TabWidget tabWidget, final String msgText) {
		Button b = new Button(msgText);
		b.setWidth("480px");
		b.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				FeedbackPanelContainer.getMessageTextArea().setText(msgText);
				UserLog userActionLog = new UserLog();
				userActionLog.setComponentType(ComponentType.FEEDBACK_TEMPLATE_POOL);
				userActionLog.setDescription("Text selection for feedback: Feedback_Type=" + tabTitle + "," + "Text=" + msgText);
				userActionLog.setUserActionType(UserActionType.FEEDBACK_TEXT_SELECTION);
				userActionLog.setTriggeredBy(ComponentType.FEEDBACK_TEMPLATE_POOL);
				userActionLog.addProperty("FEEDBACK_TYPE", tabTitle);
				userActionLog.addProperty("FEEDBACK_TEXT", msgText);

				Logger.getLoggerInstance().log(userActionLog);

			}
		});
		tabWidget.getButtonsVPanel().add(b);
	}

	class TabWidget {
		String title = "untitled";
		protected VerticalPanel mainVPanel;
		protected VerticalPanel headerVPanel;
		protected VerticalPanel buttonsVPanel;
		private ScrollPanel buttonsScrollPanel;

		TabWidget() {
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

		public VerticalPanel getButtonsVPanel() {
			return buttonsVPanel;
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

	class HistoryTabWidget extends TabWidget {
		private TextArea xmlCodeArea;
		private ToggleButton xmlToggleButton;

		HistoryTabWidget() {
			super();
			xmlCodeArea = new TextArea();
			xmlCodeArea.setWidth("480px");
			xmlCodeArea.setHeight("450px");
			xmlToggleButton = new ToggleButton("");
			toggleCodeView(false);
			xmlToggleButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (xmlToggleButton.isDown()) {
						toggleCodeView(true);

					} else {
						toggleCodeView(false);
					}
				}
			});
			headerVPanel.add(xmlToggleButton);
			headerVPanel.add(xmlCodeArea);
		}

		private void toggleCodeView(boolean codeViewEnabled) {
			xmlToggleButton.setText(codeViewEnabled ? "view as buttons" : "view as XML code");
			xmlToggleButton.setWidth("150px");
			updateXmlCodeArea();
			buttonsVPanel.setVisible(!codeViewEnabled);
			xmlCodeArea.setVisible(codeViewEnabled);
		}

		private void updateXmlCodeArea() {
			String code = "";
			for (int i = 0; i < buttonsVPanel.getWidgetCount(); i++) {
				String msg = ((Button) buttonsVPanel.getWidget(i)).getText().replaceAll("[\n\r]", " ");
				code += "<message>" + msg + "</message>\n";
			}
			xmlCodeArea.setText(code);
		}
	}

	public void addMessageToHistory(String messageToStudent) {
		addButtonToTabWidget(messageHistory, messageToStudent);
		messageHistory.updateXmlCodeArea();
	};

}

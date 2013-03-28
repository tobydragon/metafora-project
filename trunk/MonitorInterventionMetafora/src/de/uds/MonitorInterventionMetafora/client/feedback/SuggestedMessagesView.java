package de.uds.MonitorInterventionMetafora.client.feedback;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
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
import com.google.gwt.xml.client.impl.DOMParseException;

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

public class SuggestedMessagesView {
	private VerticalPanel vpanel;
	private TabBar tabBar;
	private ArrayList<TabWidget> tabWidgets;
	private HistoryTabWidget messageHistory;
	private VerticalPanel xmlVPanel;
	private String tabTitle = "";
	private SuggestedMessagesController controller;
	private SuggestedMessagesModel model; 
	private ClientFeedbackDataModelUpdater updater;
	
	public SuggestedMessagesView(ComplexPanel parent, SuggestedMessagesModel model, SuggestedMessagesController controller, ClientFeedbackDataModelUpdater updater) {
		this.controller = controller;
		controller.setView(this);
		this.model = model;
		this.updater = updater;
		vpanel = new VerticalPanel();
		// vpanel.setSpacing(20);
		parent.add(vpanel);

		// section label
		final Label sectionLabel = new Label("Select any of the message templates from any of the tabs below");
		sectionLabel.setStyleName("sectionLabel");
		vpanel.add(sectionLabel);

		tabBar = new TabBar();
		tabBar.setWidth("100%");

		// create and populate TABs based on XML
		populateTabs(model);

		final ScrollPanel scrollPanel = new ScrollPanel();
//		scrollPanel.setHeight("500px");
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

	public void populateTabs(SuggestedMessagesModel model) {
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

		// get suggested messages categories
		boolean isetZero = false;
		for (SuggestionCategory suggestionCategory : model.getSuggestionCategories()) {
			String tabTitle = suggestionCategory.getName();
			
			TabWidget tabWidget = new TabWidget(tabTitle);
			tabWidget.setController(controller);
			tabWidgets.add(tabWidget);
//			tabWidget.setTitle();
			
			
			if (!isetZero) {
				isetZero = true;
				this.tabTitle = tabTitle;
			}
//			tabBar.addTab(tabTitle);
//			tabBar.addTab("<font color=\"blue\">" +tabTitle+ "</font>", true);	// sample way of setting style
			tabBar.addTab("<b>" +tabTitle+ "</b>", true);	// sample way of setting style

			for (SuggestedMessage msg : suggestionCategory.getSuggestedMessages()) {
				tabWidget.addSuggestedMessageRow(msg, tabTitle);
			}

			if (UrlParameterConfig.getInstance().getUserType().equals(UserType.POWER_WIZARD)) {
				tabWidget.enableTabConfig();
			}
		}

		// create tab for message history
		String sentMessagesTitle = "Sent";
		messageHistory = new HistoryTabWidget(sentMessagesTitle);
//		messageHistory.setTitle(sentMessagesTitle);
		tabWidgets.add(messageHistory);
		tabBar.insertTab(sentMessagesTitle, tabBar.getTabCount());

		// create tab for the XML content
		tabBar.addTab("XML");

		xmlVPanel = new VerticalPanel();
		final TextArea textArea = new TextArea();
		textArea.setText(SuggestedMessagesModel.toXML(model));
		textArea.setSize("480px", "450px");

		// Re-populate button
		Button repopulateButton = new Button("Re-populate");
		repopulateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				populateTabs(SuggestedMessagesModel.fromXML(textArea.getText()));
			}
		});
		
		// Send button
		Button sendButton = new Button("Send Suggestions");
		sendButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				controller.sendSuggestedMessages(textArea.getText());
			}
		});
		
		// Refresh button
		Button refreshButton = new Button("Refresh");
		refreshButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String currentUserId = UrlParameterConfig.getInstance().getUsername();
				updater.refreshSuggestedMessages(currentUserId);
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

	public void addMessageToHistory(String messageToStudent) {
		messageHistory.addSuggestedMessageRow(new SuggestedMessage(messageToStudent), tabTitle);
		messageHistory.updateXmlCodeArea();
	};

	public void setUpdater(SuggestedMessagesController controller) {
		this.controller = controller;
	}
	
	public SuggestedMessagesController getController() {
		return controller;
	}

	public void setModel(SuggestedMessagesModel suggestedMessagesModel) {
		this.model = suggestedMessagesModel;
		
	}
}

package de.uds.MonitorInterventionMetafora.client.feedback;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.uds.MonitorInterventionMetafora.client.messages.MessagesBundle;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig.UserType;


public class SuggestedMessagesView extends VerticalPanel {
    
    	// MESSAGE BUNDLE 
    	public static MessagesBundle messagesBundle = GWT.create(MessagesBundle.class);


	// CONSTANSTS
	private int PANEL_HEIGHT = 360;
	
	private TabBar tabBar;
	private ArrayList<TabWidget> tabWidgets;
	private HistoryTabWidget messageHistory;
	private VerticalPanel xmlVPanel;
	private String tabTitle = "";
	private SuggestedMessagesController controller;
	private SuggestedMessagesModel suggestedMessagesModel; 
	private ClientFeedbackDataModelUpdater updater;
	
	public SuggestedMessagesView(/*ComplexPanel parent, */final SuggestedMessagesModel model, SuggestedMessagesController controller, final ClientFeedbackDataModelUpdater updater) {
		this.controller = controller;
		controller.setView(this);
		this.suggestedMessagesModel = model;
		this.updater = updater;
		
		this.setBorderWidth(1);

		// section label
		final Label sectionLabel = new Label(messagesBundle.MessagesInstruction());
		sectionLabel.setStyleName("sectionLabel");
		sectionLabel.setWordWrap(true);
		
		HorizontalPanel panel = new HorizontalPanel();

		panel.add(sectionLabel);
		
		sectionLabel.setWidth("300px");
		
    		//If not recommender add 'Get Recommendations' button
		if ( UrlParameterConfig.getInstance().getUserType() != UserType.RECOMMENDING_WIZARD) {
        		Button receiveRecommendationsButton = new Button(messagesBundle.GetRecommendationsButton());
        		receiveRecommendationsButton.addClickHandler(new ClickHandler() {
        			@Override
        			public void onClick(ClickEvent event) {
        				String currentUserId = UrlParameterConfig.getInstance().getUsername();
        				updater.refreshSuggestedMessages(currentUserId);
        			}
        
        		});
        		
        		panel.add(receiveRecommendationsButton);
    		}	
		this.add(panel);
		
		tabBar = new TabBar();
		tabBar.setWidth("100%");

		// create and populate TABs based on XML
		populateTabs(model);
		
		// Maybe there are suggested messages waiting for the user already
		String currentUserId = UrlParameterConfig.getInstance().getUsername();
		updater.refreshSuggestedMessages(currentUserId);

		final ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setHeight(PANEL_HEIGHT +"px");
		scrollPanel.setAlwaysShowScrollBars(false);
		// scrollPanel.setWidget(tools.get(0).getButtonsVPanel());
		tabBar.addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {
				if (tabBar.getTabHTML(event.getSelectedItem()).equals("XML")) {
					scrollPanel.setWidget(xmlVPanel);
				} else {
					scrollPanel.setWidget(tabWidgets.get(event.getSelectedItem()).getMainVPanel());
					HTML html = new HTML(tabBar.getTabHTML(event.getSelectedItem()));
					tabTitle = html.getText();
					
					SuggestionCategory suggestionCategory = suggestedMessagesModel.getSuggestionCategory(tabTitle);
					if (suggestionCategory != null) {
						if (suggestionCategory.isHighlight()) {
							HTML newHtml = new HTML(tabTitle);
							newHtml.addStyleDependentName("selected-highlight");
							tabBar.setTabHTML(event.getSelectedItem(), newHtml.toString());
						} else {
							tabBar.setTabHTML(event.getSelectedItem(), tabTitle);
						}
					}
				}

			}
		});
		tabBar.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {
			@Override
			public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
				TabBar source = (TabBar) event.getSource();
				int index = source.getSelectedTab();
				if (index > -1 && index < suggestedMessagesModel.getSuggestionCategories().size()) {
					HTML html = new HTML(source.getTabHTML(index));
					HTML newHtml = new HTML(html.getText());
					
					if (suggestedMessagesModel.getSuggestionCategory(index).isHighlight()) {
						newHtml.addStyleDependentName("unselected-highlight");
						source.setTabHTML(index, newHtml.toString());
					} else {
						source.setTabHTML(index, newHtml.toString());
					}
				}
			}
		});
		tabBar.selectTab(0, true);
		this.add(tabBar);
		this.add(scrollPanel);
	}

	public void populateTabs(SuggestedMessagesModel model) {
		setModel(model);
		// create new list of tab widgets
		tabWidgets = new ArrayList<TabWidget>();

		
		int selectedTab = tabBar == null ? 0 : tabBar.getSelectedTab();
		
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
						
			if (!isetZero) {
				isetZero = true;
				this.tabTitle = tabTitle;
			}
			
			HTML html = new HTML(tabTitle);
			if (suggestionCategory.isHighlight()) {
				html.addStyleDependentName("unselected-highlight");
			}
			tabBar.addTab(html.toString(), true);	// sample way of setting style

			for (SuggestedMessage msg : suggestionCategory.getSuggestedMessages()) {
				tabWidget.addSuggestedMessageRow(msg, tabTitle);
			}

			if (UrlParameterConfig.getInstance().getUserType().equals(UserType.RECOMMENDING_WIZARD)) {
				tabWidget.enableTabConfig();
			}
		}

		// create tab for message history
		String sentMessagesTitle = messagesBundle.Sent();
		messageHistory = new HistoryTabWidget(sentMessagesTitle);
		tabWidgets.add(messageHistory);
		tabBar.insertTab(sentMessagesTitle, tabBar.getTabCount());

		// create tab for the XML content
		tabBar.addTab("XML");

		final TextArea textArea = new TextArea();
		textArea.setText(SuggestedMessagesModel.toXML(model));
		textArea.setSize("95%", "450px");
		

		// Re-populate button
		Button repopulateButton = new Button("Re-populate");
		repopulateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				populateTabs(SuggestedMessagesModel.fromXML(textArea.getText()));
			}
		});
		
		// Send button
		Button sendButton = new Button("Send recommendations");
		sendButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				controller.sendSuggestedMessages(textArea.getText());
				
				
			}
		});
		
		
		if (selectedTab >= tabBar.getTabCount())
			selectedTab = 0;
		tabBar.selectTab(selectedTab, true);
		
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		buttonsPanel.setSpacing(3);
		buttonsPanel.add(repopulateButton);
		buttonsPanel.add(sendButton);

		xmlVPanel = new VerticalPanel();
		xmlVPanel.setWidth("100%");
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
		this.suggestedMessagesModel = suggestedMessagesModel;
	}
	
	public TabBar getTabBar() {
		return tabBar;
	}
}

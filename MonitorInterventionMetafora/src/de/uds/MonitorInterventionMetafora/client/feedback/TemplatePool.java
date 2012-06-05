package de.uds.MonitorInterventionMetafora.client.feedback;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;



public class TemplatePool {
	private VerticalPanel vpanel;
	private TabBar tabBar;
	private ArrayList<TabWidget> tabWidgets;
	private HistoryTabWidget messageHistory;
	private VerticalPanel xmlVPanel;
	
	public TemplatePool(ComplexPanel parent, String XML)
	{
		vpanel = new VerticalPanel();		
		//vpanel.setSpacing(20);
		parent.add(vpanel);
		 
		//section label
		final Label sectionLabel = new Label("Select any of the message templates from any of the tabs below");
		sectionLabel.setStyleName("sectionLabel");
		vpanel.add(sectionLabel);

		tabBar = new TabBar();

		//create and populate TABs based on XML
		populateTabs(XML);
		
		final ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setHeight("500px");
		//scrollPanel.setWidget(tools.get(0).getButtonsVPanel());
		tabBar.addSelectionHandler(new SelectionHandler<Integer>(){

			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if (tabBar.getTabHTML(event.getSelectedItem()).equals("XML")) {
				   scrollPanel.setWidget(xmlVPanel);
				} else {
				   scrollPanel.setWidget(tabWidgets.get(event.getSelectedItem()).getMainVPanel());
				}
			}
			
		});
		tabBar.selectTab(3, true);

		vpanel.add(tabBar);
		vpanel.add(scrollPanel);
	}

	private void populateTabs(String XML) {
	//create new list of tab widgets
	tabWidgets = new ArrayList<TabWidget>();
	//clean tabs
	int numberOfTabsToremove = tabBar.getTabCount(); 	
	for (int i=0; i<numberOfTabsToremove; i++) {
		//Yes, it is removeTab(0) not i. You need to remove always the first
		//because they get re-numbered !
		tabBar.removeTab(0);
	}
	
	//create tabs based on top-level XML nodes
	Document xmlDocument = XMLParser.parse(XML);
	Element docElement = xmlDocument.getDocumentElement();
	XMLParser.removeWhitespace(docElement);
	
	//get the elements called <sets>
	NodeList sets = docElement.getElementsByTagName("set");
	int numberOfSets = sets.getLength();
	for (int iset=0; iset<numberOfSets; iset++) {
	   Element setOfMessages = (Element) sets.item(iset);
	   String tabTitle = setOfMessages.getAttributes().getNamedItem("id").getNodeValue();
	 	//check here if there are more sets
	   NodeList messages = setOfMessages.getElementsByTagName("message");
	   int numberOfMessages = messages.getLength();
	   TabWidget tabWidget = new TabWidget();
	   tabWidgets.add(tabWidget);
	   tabWidget.setTitle(tabTitle);
	   tabBar.addTab(tabTitle);
	                                      
		for (int imessage=0; imessage<numberOfMessages; imessage++) {
			final String msgText = messages.item(imessage).getFirstChild().getNodeValue();
			addButtonToTabWidget(tabWidget, msgText);
		}
	}
	
	//create tab for message history
	messageHistory = new HistoryTabWidget();
	String sentMessagesTitle = "Sent";
	messageHistory.setTitle(sentMessagesTitle);
	tabWidgets.add(messageHistory);
	tabBar.insertTab(sentMessagesTitle, tabBar.getTabCount());


	//create tab for the XML content
	tabBar.addTab("XML");
	
	xmlVPanel = new VerticalPanel();
	final TextArea textArea = new TextArea(); 
	textArea.setText(XML);
	textArea.setSize("480px","450px");
	
	Button xmlVPanelButton = new Button("Re-populate");
	xmlVPanelButton.addClickHandler(new ClickHandler()
	{
		@Override
		public void onClick(ClickEvent event) {
			populateTabs(textArea.getText());
		}
	});
	
	xmlVPanel.add(xmlVPanelButton);
	xmlVPanel.add(textArea);

	}
	
	private void addButtonToTabWidget(TabWidget tabWidget, final String msgText) {
		Button b = new Button(msgText);
		b.setWidth("480px");
		b.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event) {
				FeedbackPanelContainer.getMessageTextArea().setText(msgText);
			}		
		});
		tabWidget.getButtonsVPanel().add(b);
	}


	class TabWidget{
		String title="untitled";
		protected VerticalPanel mainVPanel;
		protected VerticalPanel headerVPanel;
		protected VerticalPanel buttonsVPanel;
		private ScrollPanel buttonsScrollPanel;
		TabWidget()
		{
			mainVPanel = new VerticalPanel();
//			mainVPanel.
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
	
	class HistoryTabWidget extends TabWidget{
		private TextArea xmlCodeArea;
		private ToggleButton xmlToggleButton;
		HistoryTabWidget()
		{
			super();
			xmlCodeArea = new TextArea();
			xmlCodeArea.setWidth("480px");
			xmlCodeArea.setHeight("450px");
			xmlToggleButton = new ToggleButton("");
			toggleCodeView(false);
			xmlToggleButton.addClickHandler(new ClickHandler()
			{
				@Override
				public void onClick(ClickEvent event) {
					if(xmlToggleButton.isDown())
					{
						toggleCodeView(true);
						
					}
					else
					{
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
			for(int i=0; i<buttonsVPanel.getWidgetCount(); i++)
			{
				String msg = ((Button)buttonsVPanel.getWidget(i)).getText().replaceAll("[\n\r]", " ");
				code += "<message>"+msg+"</message>\n";
			}
			xmlCodeArea.setText(code);
		}		
	}

	public void addMessageToHistory(String messageToStudent) {
		addButtonToTabWidget(messageHistory, messageToStudent);
		messageHistory.updateXmlCodeArea();
	};

}

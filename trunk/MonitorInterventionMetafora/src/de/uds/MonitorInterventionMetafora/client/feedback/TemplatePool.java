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
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;



public class TemplatePool {
	private VerticalPanel vpanel;

	public TemplatePool(ComplexPanel parent, String XML)
	{
		vpanel = new VerticalPanel();		
		//vpanel.setSpacing(20);
		parent.add(vpanel);
	
		
		//section label
		final Label sectionLabel = new Label("Select any of the message templates from any of the tabs below");
		sectionLabel.setStyleName("sectionLabel");
		vpanel.add(sectionLabel);

		
		
		class Tool{

			private VerticalPanel buttonsVPanel;
			public VerticalPanel getButtonsVPanel() {
				return buttonsVPanel;
			}
			
			Tool(String[] messageTemplates)
			{
				buttonsVPanel = new VerticalPanel();

				for(int i=0; i<messageTemplates.length; i++)
					{
						final String mt = messageTemplates[i];
						Button b = new Button(mt);
						b.setWidth("500px");
						b.addClickHandler(new ClickHandler()
						{
							@Override
							public void onClick(ClickEvent event) {
								FeedbackPanelContainer.getMessageTextBox().setText(mt);
							}		
						});
						buttonsVPanel.add(b);
					}
				
				
			}
		};
		
		//create tools based on number of top-level XML elements
		Document xmlDocument = XMLParser.parse(XML);
		Element docElement = xmlDocument.getDocumentElement();
		XMLParser.removeWhitespace(docElement);

		//get the elements called <sets>
		NodeList sets = docElement.getElementsByTagName("set");
		int numberOfSets = sets.getLength();
	
		String[] toolNames = new String[numberOfSets];

		final ArrayList<Tool> tools = new ArrayList<Tool>();
		final TabBar tabBar = new TabBar();

		for (int i=0; i<numberOfSets; i++) {
		   Element setOfMessages = (Element) sets.item(i);
		   toolNames[i] = setOfMessages.getAttributes().getNamedItem("id").getNodeValue();
		 	//check here if there are more sets
		   NodeList messages = setOfMessages.getElementsByTagName("message");
		   int numberOfMessages = messages.getLength();
		   String[] messageTemplates = new String[numberOfMessages];
		                                         
			for (int j=0; j<numberOfMessages; j++) {
				messageTemplates[j] = messages.item(j).getFirstChild().getNodeValue();
			}
			Tool tool = new Tool(messageTemplates);
			tabBar.addTab(toolNames[i]);
			tools.add(tool);
		}
	
		//put the XML content
		tabBar.addTab("XML");
		
		final VerticalPanel xmlVPanel = new VerticalPanel();
		TextArea textArea = new TextArea(); 
		textArea.setText(XML);
		textArea.setSize("400px","500px");
		
		xmlVPanel.add(textArea);
		
		//
		final ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setHeight("500px");
//		scrollPanel.setWidget(tools.get(0).getButtonsVPanel());
		tabBar.addSelectionHandler(new SelectionHandler<Integer>(){

			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if (tabBar.getTabHTML(event.getSelectedItem()).equals("XML")) {
				   scrollPanel.setWidget(xmlVPanel);
				} else {
				   scrollPanel.setWidget(tools.get(event.getSelectedItem()).getButtonsVPanel());
				}
			}
			
		});
		tabBar.selectTab(3, true);

		vpanel.add(tabBar);
		vpanel.add(scrollPanel);
	}
	
	

}

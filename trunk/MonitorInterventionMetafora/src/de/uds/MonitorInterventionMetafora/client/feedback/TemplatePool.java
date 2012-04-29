package de.uds.MonitorInterventionMetafora.client.feedback;

import java.util.ArrayList;

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



public class TemplatePool {
	private VerticalPanel vpanel;
	public TemplatePool(ComplexPanel parent)
	{
		vpanel = new VerticalPanel();		
		//vpanel.setSpacing(20);
		parent.add(vpanel);
		
		//section label
		final Label sectionLabel = new Label("Select any of the message templates from any of the tabs below");
		sectionLabel.setStyleName("sectionLabel");
		vpanel.add(sectionLabel);

		class Tool{
			String name;
			private VerticalPanel buttonsVPanel;
			public VerticalPanel getButtonsVPanel() {
				return buttonsVPanel;
			}
			Tool(String _name)
			{
				name = _name;
				buttonsVPanel = new VerticalPanel();
				if(name.equals("Planning"))
				{
					final String[] messageTemplates = {"Don't forget to reflect on your plan.","There seem to be too many cards that are not linked.","There seem to be too many process cards.","Consider using stages."};
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
				else if(name.equals("[Sent Messages]"))
				{
					//
				}
				else
				{
					buttonsVPanel.add(new Label("Not implemented yet for "+name));
				}
			}
		};
		//create tools
		String[] toolNames = {"L2L2","Planning","Discussion","Building", "Reflecting", "[Sent Messages]"};
		final ArrayList<Tool> tools = new ArrayList<Tool>();
		TabBar tabBar = new TabBar();
		for(String tn : toolNames)
		{
			tabBar.addTab(tn);
			tools.add(new Tool(tn));
		}
		//
		final ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setHeight("500px");
//		scrollPanel.setWidget(tools.get(0).getButtonsVPanel());
		tabBar.addSelectionHandler(new SelectionHandler<Integer>(){

			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				scrollPanel.setWidget(tools.get(event.getSelectedItem()).getButtonsVPanel());
				
			}
			
		});
		tabBar.selectTab(3, true);
		vpanel.add(tabBar);
		vpanel.add(scrollPanel);
	}
}

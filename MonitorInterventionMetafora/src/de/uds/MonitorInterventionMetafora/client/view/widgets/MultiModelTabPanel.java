package de.uds.MonitorInterventionMetafora.client.view.widgets;

import java.util.List;
import java.util.Map;



import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.TabItem;
//import com.extjs.gxt.ui.client.widget.TabPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class MultiModelTabPanel extends VerticalPanel {

	
	
	private  TabPanel tabPanel;
	
	public MultiModelTabPanel(){
		
		tabPanel = new TabPanel();
		this.add(tabPanel);
		
	}

	
	public MultiModelTabPanel(String _lbl){
		
	if(_lbl!=null){
		this.add(new Label(_lbl));
		
		
		tabPanel = new TabPanel();
		
		this.add(tabPanel);
		
	
	}
	}
	
	
	
	public void addTab(String _lbl,Widget _widget){
		
	 
				
		tabPanel.add(_widget,_lbl);
		tabPanel.selectTab(0);
	
		
	}
	
	

	public void switchToTab(int index){
				
		tabPanel.selectTab(index);
	}
	

}

package com.analysis.client.view.widgets;

import java.util.List;
import java.util.Map;



import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.TabItem;
//import com.extjs.gxt.ui.client.widget.TabPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class TabDataViewPanel extends VerticalPanel {

	
	FilterListPanel flp;
	private  TabPanel tabPanel = new TabPanel();
	public TabDataViewPanel(String _lbl){
		
	if(_lbl!=null){
		this.add(new Label(_lbl));
		
		flp=new FilterListPanel();
		//flp.addFilter("User", "Ugur");
		this.add(flp);
		
		this.add(tabPanel);
		
	
	}
	}
	
	
	
	public void addTab(String _lbl,Widget _widget){
		
	 
				
		tabPanel.add(_widget,_lbl);
		tabPanel.selectTab(0);
	
		
	}
	
	
	
/*	public Widget renderExtendedTabPanel(){
		
		//tabPanel.selectTab(0);
		//this.add(tabPanel);
		//return this;
	}
	*/

	public void switchToTab(int index){
				
		tabPanel.selectTab(index);
	}
	

}

package de.uds.MonitorInterventionMetafora.client.view.containers;

import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.ui.Widget;

public class MainContainerTabPanel extends TabPanel {

	
	public MainContainerTabPanel(){
		
	this.setHeight("600");
	this.setWidth("900");
	this.setLayout(new FitLayout());
		
	}
	
	
	public void addTab(String _lbl,Widget _widget,boolean _closable){
		
		 TabItem _item=new TabItem(_lbl);
		 _item.setLayout(new FitLayout());
		 _item.setHeight(500);
		 _item.setWidth(600);
		 
		 _item.setClosable(_closable);
		 _item.add(_widget);
		this.add(_item);
		 
		}
}

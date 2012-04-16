package de.uds.MonitorInterventionMetafora.client.view.containers;

import com.extjs.gxt.ui.client.widget.TabItem;

import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.ui.Widget;

public class MainContainerTabPanel extends VerticalPanel {

	
	TabPanel _containerTabPanel;
	public MainContainerTabPanel(){
		_containerTabPanel=new TabPanel();
		_containerTabPanel.setHeight(720);
		_containerTabPanel.setWidth(1000);
		_containerTabPanel.setLayoutData(new FitLayout());
		
		this.add(_containerTabPanel);
		
	this.setHeight(720);
	this.setWidth(1000);
	this.setLayout(new FitLayout());
		
	}
	
	
	public void addTab(String _lbl,Widget _widget,boolean _closable){
		
		 TabItem _item=new TabItem(_lbl);
		 _item.setLayout(new FitLayout());
		 _item.setHeight(800);
		 _item.setWidth(500);
		 
		 _item.setClosable(_closable);
		 _item.add(_widget);
		 _item.layout();
		 _containerTabPanel.add(_item);
		 _containerTabPanel.setTabIndex(0);
		 
		}
}

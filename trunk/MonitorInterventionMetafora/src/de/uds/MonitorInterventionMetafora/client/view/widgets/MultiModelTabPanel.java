package de.uds.MonitorInterventionMetafora.client.view.widgets;

import java.util.List;
import java.util.Map;



import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
//import com.google.gwt.user.client.ui.TabPanel;

import com.google.gwt.user.client.ui.Widget;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.TabPanelEvent;


public class MultiModelTabPanel extends VerticalPanel {

	
	
	private  TabPanel tabPanel;
	
	public MultiModelTabPanel(){
		
	
		tabPanel = new TabPanel();
		tabPanel.setWidth(601);
		tabPanel.setHeight(430);
		//tabPanel.set
		tabPanel.addListener(Events.Select, new SelectionListener<TabPanelEvent>() {

            @Override
            public void componentSelected(TabPanelEvent ce) {
            	
            	
            	//Info.display("","a"+ce.getItem().getTabIndex());
            	
            }
        });
    
		tabPanel.setId("_multiModelTabPanel");
		//this.setId("_multiModelVerticalPanel");
		this.add(tabPanel);
		
		
	}

	
	public MultiModelTabPanel(String _lbl){
		
	if(_lbl!=null){
		this.add(new Label(_lbl));
		
		
		tabPanel = new TabPanel();
		
		
		this.add(tabPanel);
		
	
	}
	}
	
	
	public TabPanel getTabPanel(){
		
		return tabPanel;
	}
	
	public void addTab(String _lbl,Widget _widget,boolean _closable){
		
	 TabItem _item=new TabItem(_lbl);
	 _item.setLayout(new FitLayout());
	 
	 _item.setClosable(_closable);
	 _item.add(_widget);
	 _item.setHeight(450);
	 _item.setId(_lbl);
	 
	
 
	 tabPanel.add(_item);
	 
	//	tabPanel.getTabBar().get
		//tabPanel.add(item)(_widget,_lbl);
		//tabPanel.selectTab(0);
		
	
		
	}
	
	public void addTab(String id,String _lbl,Widget _widget,boolean _closable){
		
		 TabItem _item=new TabItem(_lbl);
		 _item.setLayout(new FitLayout());
		 
		 _item.setClosable(_closable);
		 _item.add(_widget);
		 _item.setHeight(450);
		 _item.setId(id);
		 tabPanel.add(_item);
		 
		//	tabPanel.getTabBar().get
			//tabPanel.add(item)(_widget,_lbl);
			//tabPanel.selectTab(0);
			
		
			
		}

	public void switchToTab(int index){
				
		tabPanel.setTabIndex(index);
	}
	

}

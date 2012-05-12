package de.uds.MonitorInterventionMetafora.client.monitor.dataview;

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

import de.uds.MonitorInterventionMetafora.client.logger.ComponentType;
import de.uds.MonitorInterventionMetafora.client.logger.Log;
import de.uds.MonitorInterventionMetafora.client.logger.Logger;
import de.uds.MonitorInterventionMetafora.client.logger.UserActionType;


public class TabbedDataViewPanel extends VerticalPanel {

	
	
	private  TabPanel tabPanel;
	
	public TabbedDataViewPanel(){
		
		tabPanel = new TabPanel();
		tabPanel.setWidth(970);
		tabPanel.setHeight(662);
		tabPanel.addListener(Events.Select, new SelectionListener<TabPanelEvent>() {

            @Override
            public void componentSelected(TabPanelEvent ce) {
            	
            	Log userActionLog=new Log();
            	userActionLog.setComponentType(ComponentType.VIEW_TAB_PANEL);
            	userActionLog.setDescription(ce.getItem().getText()+" Tab is activated in Views Tab Panel");
            	userActionLog.setTriggeredBy(ComponentType.VIEW_TAB_PANEL);
            	userActionLog.setUserActionType(UserActionType.TAB_CHANGE);
            	Logger.getLoggerInstance().log(userActionLog);
            	
            }
        });
		this.add(tabPanel);
	}

	
	public TabbedDataViewPanel(String _lbl){	
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
	}
	
	public void addTab(String id,String _lbl,Widget _widget,boolean _closable){
		 
		TabItem _item=new TabItem(_lbl);
		 _item.setLayout(new FitLayout());
		 _item.setClosable(_closable);
		 _item.add(_widget);
		 _item.setHeight(450);
		 _item.setId(id);
		 tabPanel.add(_item);	
		}

	public void switchToTab(int index){
	
		tabPanel.setTabIndex(index);
	}
	

}

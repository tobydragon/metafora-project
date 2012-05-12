package de.uds.MonitorInterventionMetafora.client.monitor.filter;


import java.util.HashMap;

import java.util.Map;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.ContentPanel;

import de.uds.MonitorInterventionMetafora.client.logger.ComponentType;
import de.uds.MonitorInterventionMetafora.client.logger.Log;
import de.uds.MonitorInterventionMetafora.client.logger.Logger;
import de.uds.MonitorInterventionMetafora.client.logger.UserActionType;
import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;


public class FilterListPanel extends ContentPanel {
	
	
	Map<String, String> _filterList;
	FilterGrid filterGird;
	private ClientMonitorController interfaceManager;
	ClientMonitorDataModel maintenance;
	public FilterListPanel(ClientMonitorDataModel _maintenance, ClientMonitorController controller){
		
		maintenance=_maintenance;
		interfaceManager= controller;
		this.setCollapsible(true);
		this.setHeading("Filter Options");
		this.setExpanded(false);
	    this.setHeight("241px");	
		_filterList=new HashMap<String,String>();
		 filterGird=new FilterGrid(_maintenance, interfaceManager);
		this.add(filterGird);	
		
		 this.addListener(Events.Collapse, new Listener<BaseEvent>()
        {

            public void handleEvent(BaseEvent be)
            {
            	//TODO: Put drop down menu for filter here and Make this work for the whole tab pane, not just the table view
             //    Make this work for the whole tab pane, not just the table view
           //interfaceManager.getGroupedGridContentPanel().setWidth(600);
//            	if(interfaceManager.getTableViewEditorGrid()!=null)
//           interfaceManager.getTableViewEditorGrid().setHeight(540);	
            	// Info.display("Collapse", "Collapse");
            	
            	
            	
            	Log userActionLog=new Log();
            	userActionLog.setComponentType(ComponentType.FILTER_TABLE);
            	userActionLog.setDescription("Filter Panel is collapsed.");
            	userActionLog.setTriggeredBy(ComponentType.FILTER_TABLE);
            	userActionLog.setUserActionType(UserActionType.STATUS_CHANGE);
            	Logger.getLoggerInstance().log(userActionLog);
            };
        });
		 
		this.addListener(Events.Expand, new Listener<BaseEvent>()
        {

            public void handleEvent(BaseEvent be)
            {
            	//TODO: Make this work for the whole tab pane, not just the table view
            //interfaceManager.getGroupedGridContentPanel().setWidth(600);
//            	interfaceManager.getTableViewEditorGrid().setHeight(326);     	
            	Log userActionLog=new Log();
            	userActionLog.setComponentType(ComponentType.FILTER_TABLE);
            	userActionLog.setDescription("Filter Panel is expanded.");
            	userActionLog.setTriggeredBy(ComponentType.FILTER_TABLE);
            	userActionLog.setUserActionType(UserActionType.STATUS_CHANGE);
            	Logger.getLoggerInstance().log(userActionLog);
            };
        });

	}
	
	
	
	public FilterGrid getFilterGrid(){
		return filterGird;
	}
	
	
	

}

package de.uds.MonitorInterventionMetafora.client.monitor.filter;


import java.util.HashMap;

import java.util.Map;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.ContentPanel;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.logger.ComponentType;
import de.uds.MonitorInterventionMetafora.client.logger.UserLog;
import de.uds.MonitorInterventionMetafora.client.logger.Logger;
import de.uds.MonitorInterventionMetafora.client.logger.UserActionType;
import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;


public class FilterListPanel extends ContentPanel {
	
	
	Map<String, String> _filterList;
	FilterGrid filterGird;
	private ClientMonitorController interfaceManager;
	ClientMonitorDataModel maintenance;
	CommunicationServiceAsync serverlet;
	boolean isMainFilterSet;
	public FilterListPanel(ClientMonitorDataModel _maintenance, ClientMonitorController controller,CommunicationServiceAsync serverlet){
		this.serverlet=serverlet;
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
            	
            	
            	
            	UserLog userActionLog=new UserLog();
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
            	UserLog userActionLog=new UserLog();
            	userActionLog.setComponentType(ComponentType.FILTER_TABLE);
            	userActionLog.setDescription("Filter Panel is expanded.");
            	userActionLog.setTriggeredBy(ComponentType.FILTER_TABLE);
            	userActionLog.setUserActionType(UserActionType.STATUS_CHANGE);
            	Logger.getLoggerInstance().log(userActionLog);
            };
        });

	}
	
	
	public ContentPanel getFilterGridPanel(){
		
		return this;
		
		
	}
	
	public FilterListPanel(ClientMonitorDataModel _maintenance, ClientMonitorController controller,CommunicationServiceAsync serverlet,boolean isMainFilterSet){
		this.serverlet=serverlet;
		maintenance=_maintenance;
		this.isMainFilterSet=isMainFilterSet;
		interfaceManager= controller;
		this.setCollapsible(true);
		if(isMainFilterSet){
			this.setHeading("Main Configuration Options");
			
			//this.setHeight(280);
		}
		else{
		this.setHeading("Filter Options");
		}
		this.setExpanded(false);
	    this.setHeight("363px");	
		_filterList=new HashMap<String,String>();
		 filterGird=new FilterGrid(_maintenance.getServiceServlet(),_maintenance,controller,isMainFilterSet);
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
            	
            	
            	
            	UserLog userActionLog=new UserLog();
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
            	UserLog userActionLog=new UserLog();
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

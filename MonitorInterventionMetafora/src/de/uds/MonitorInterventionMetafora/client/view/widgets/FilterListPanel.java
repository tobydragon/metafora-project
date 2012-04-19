package de.uds.MonitorInterventionMetafora.client.view.widgets;


import java.util.HashMap;

import java.util.Map;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;

import com.google.gwt.user.client.DOM;


import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.datamodels.FilterListGridModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientInterfaceManager;
import de.uds.MonitorInterventionMetafora.client.view.grids.ExtendedFilterGrid;


public class FilterListPanel extends ContentPanel {
	
	
	Map<String, String> _filterList;
	private ClientInterfaceManager interfaceManager;
	ActionMaintenance maintenance;
	public FilterListPanel(ActionMaintenance _maintenance, ClientInterfaceManager controller){
		
		maintenance=_maintenance;
		interfaceManager= controller;
		this.setCollapsible(true);
		this.setHeading("Filter Options");
		this.setExpanded(false);
	    this.setHeight("241px");	
		_filterList=new HashMap<String,String>();
		
		
		
		
		 this.addListener(Events.Collapse, new Listener<BaseEvent>()
		            {

		                public void handleEvent(BaseEvent be)
		                {
		               //interfaceManager.getGroupedGridContentPanel().setWidth(600);
		                	if(interfaceManager.getTableViewEditorGrid()!=null)
		               interfaceManager.getTableViewEditorGrid().setHeight(540);	
		                	// Info.display("Collapse", "Collapse");
		                };
		            });
		this.addListener(Events.Expand, new Listener<BaseEvent>()
		            {

		                public void handleEvent(BaseEvent be)
		                {
		                  
		                //interfaceManager.getGroupedGridContentPanel().setWidth(600);
		                	interfaceManager.getTableViewEditorGrid().setHeight(326);     	
		                	
		                	//  Info.display("Expand", "Expand");
		                };
		            });

		
		
		
		
		
		
		//maintenance.
		FilterListGridModel flm=new FilterListGridModel(maintenance);
		ExtendedFilterGrid ef=new ExtendedFilterGrid(flm, interfaceManager);		
		this.add(ef);	
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	

}

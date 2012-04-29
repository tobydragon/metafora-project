package de.uds.MonitorInterventionMetafora.client.view.widgets;


import java.util.HashMap;

import java.util.Map;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import de.uds.MonitorInterventionMetafora.client.datamodels.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.view.grids.ExtendedFilterGrid;


public class FilterListPanel extends ContentPanel {
	
	
	Map<String, String> _filterList;
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
		
		//maintenance.
//		FilterListGridModel flm=new FilterListGridModel(maintenance);
		ExtendedFilterGrid ef=new ExtendedFilterGrid(_maintenance, interfaceManager);		
		this.add(ef);	
		
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

	}
	
	
	
	
	
	
	

}

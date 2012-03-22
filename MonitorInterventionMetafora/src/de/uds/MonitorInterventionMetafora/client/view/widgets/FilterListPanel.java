package de.uds.MonitorInterventionMetafora.client.view.widgets;


import java.util.HashMap;

import java.util.Map;

import com.extjs.gxt.ui.client.widget.ContentPanel;

import com.google.gwt.user.client.DOM;


import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.datamodels.FilterListGridModel;
import de.uds.MonitorInterventionMetafora.client.view.grids.ExtendedFilterGrid;


public class FilterListPanel extends ContentPanel {
	
	
	Map<String, String> _filterList;
	
	ActionMaintenance maintenance;
	public FilterListPanel(ActionMaintenance _maintenance){
		
		maintenance=_maintenance;
		
		this.setCollapsible(true);
		this.setHeading("Filter Options");
		
		 DOM.setStyleAttribute(this.getElement(), "border", "1px solid #000");
	     DOM.setStyleAttribute(this.getElement(), "borderBottom", "0");
	     this.setHeight("241px");	
		_filterList=new HashMap<String,String>();
		
		//ExtendedFilterItem efi=new ExtendedFilterItem(_property,_value);
		//this.add(efi);
		
		//maintenance.
		FilterListGridModel flm=new FilterListGridModel(maintenance);
		ExtendedFilterGrid ef=new ExtendedFilterGrid(flm);
		
		//ExtendedFilterManagementPanel fm=new ExtendedFilterManagementPanel(maintenance);
		//ExtendedSaveFilterSet saveFilterSet=new ExtendedSaveFilterSet();
		
		
		this.add(ef);
		//this.add(fm);
		
	}
	
	
	
	
	

}

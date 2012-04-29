package de.uds.MonitorInterventionMetafora.client.view.charts;

import com.google.gwt.visualization.client.events.SelectHandler;

import de.uds.MonitorInterventionMetafora.client.datamodels.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.view.widgets.DataViewPanel2;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.ActionPropertyRule;

public class ChartSelectionHandler  extends SelectHandler{

	DataViewPanel2 view;
	ClientMonitorDataModel model;
	ClientMonitorController controller;
	
	public ChartSelectionHandler (DataViewPanel2 view, ClientMonitorDataModel model, ClientMonitorController controller){
		this.view = view;
		this.model = model;
		this.controller = controller;
	}
	
	@Override
	public void onSelect(SelectEvent event) {
		ActionPropertyRule currentGroupingProp = view.getGroupingProperty();
		int selection = view.getSelectedRow();
		
		//Make copy, and fill in extra info
	    ActionPropertyRule newFilterRule = new ActionPropertyRule(currentGroupingProp.getType(), currentGroupingProp.getPropertyName());
	   
	    //get the value from the data table used to populate the char that was clicked
	    newFilterRule.setValue(model.getDataTable(currentGroupingProp).getValueString(selection, 0));
	    newFilterRule.setOperationType(OperationType.EQUALS);
	    
	    controller.addFilterItem(newFilterRule);
	}
	
	
	
	
	
}

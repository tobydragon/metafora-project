package de.uds.MonitorInterventionMetafora.client.monitor.dataview.chart;

import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.google.gwt.visualization.client.events.SelectHandler;

import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanel;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class ChartSelectionHandler  extends SelectHandler{

	DataViewPanel view;
	ClientMonitorDataModel model;
	ClientMonitorController controller;
	SimpleComboBox<String> filterGroupCombo;
	public ChartSelectionHandler (DataViewPanel view, ClientMonitorDataModel model, ClientMonitorController controller,SimpleComboBox<String> filterGroupCombo){
		this.view = view;
		this.model = model;
		this.filterGroupCombo=filterGroupCombo;
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
	    newFilterRule.setOrigin(view.getViewType());
	    controller.addRule(newFilterRule);
	    filterGroupCombo.clearSelections();
	}
	
	
	
	
	
}

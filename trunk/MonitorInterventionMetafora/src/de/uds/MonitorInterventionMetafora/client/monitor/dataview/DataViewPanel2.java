package de.uds.MonitorInterventionMetafora.client.monitor.dataview;

import com.extjs.gxt.ui.client.widget.VerticalPanel;

import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.ActionPropertyRule;

public abstract class DataViewPanel2 extends VerticalPanel{

	protected ActionPropertyRule groupingProperty;
	protected ClientMonitorDataModel model;
	
	public DataViewPanel2(ActionPropertyRule groupingProperty, ClientMonitorDataModel model){
		this.groupingProperty = groupingProperty;
		this.model = model;
	}
		
	public ActionPropertyRule getGroupingProperty() {
		return groupingProperty;
	}
	
	public void setGroupingProperty(ActionPropertyRule groupingProperty) {
		this.groupingProperty = groupingProperty;
		refresh();
	}

	public void refresh(){
		this.layout();
	}
	
	public abstract int getSelectedRow();

}

package de.uds.MonitorInterventionMetafora.client.monitor.dataview;

import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.google.gwt.user.client.ui.HTML;

import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;

public class StatusPanel extends HorizontalPanel {
	
	
private Label label;
private ClientMonitorDataModel dataModel;


public StatusPanel(ClientMonitorDataModel dataModel){
	this.dataModel=dataModel;
	label=new Label("Active Indicator Count: 0 out of 0");

	this.setBorders(true);
	this.setStyleName("statusPanel");
	this.add(label);
	
}

public void refresh(){
	
	
	label.setText("Active Indicator Count: "+ dataModel.getFilteredActionCount()+" out of "+dataModel.getAllActionCount());
}
}

package de.uds.MonitorInterventionMetafora.client.monitor.dataview;

import com.extjs.gxt.ui.client.widget.VerticalPanel;

import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.chart.BarChartPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.chart.PieChartPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.table.TablePanel;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public abstract class DataViewPanel extends VerticalPanel{

	protected ActionPropertyRule groupingProperty;
	protected ClientMonitorDataModel model;
	

	public DataViewPanel(ActionPropertyRule groupingProperty, ClientMonitorDataModel model){
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
	
	public static DataViewPanel createDataViewPanel(DataViewPanelType dataViewPanelType, ClientMonitorDataModel model, 
			ClientMonitorController controller, GroupedDataViewPanel groupedDataViewController, ActionPropertyRule  groupingProperty){
		DataViewPanel dataViewPanel = null;
		
		if (dataViewPanelType == DataViewPanelType.TABLE){  
			dataViewPanel = new TablePanel(model, groupingProperty); 

		}
		else if (dataViewPanelType == DataViewPanelType.PIE_CHART){  
			dataViewPanel = new PieChartPanel(model, controller, groupingProperty); 

		}
		else if (dataViewPanelType == DataViewPanelType.BAR_CHART){
			  dataViewPanel = new BarChartPanel(model, controller, groupedDataViewController, groupingProperty); 
		}
		return dataViewPanel;
	}

}

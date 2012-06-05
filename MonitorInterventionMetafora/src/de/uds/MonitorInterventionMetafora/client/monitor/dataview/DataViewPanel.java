package de.uds.MonitorInterventionMetafora.client.monitor.dataview;

import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;

import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.chart.BarChartPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.chart.PieChartPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.table.TablePanel;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterListPanel;
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
		this.repaint();
	
	}
	
	public abstract int getSelectedRow();
	public abstract void enableAdjustSize();
	public abstract DataViewPanelType getViewType();
	
	
	public static DataViewPanel createDataViewPanel(DataViewPanelType dataViewPanelType, ClientMonitorDataModel model, 
			ClientMonitorController controller, GroupedDataViewPanel groupedDataViewController, ActionPropertyRule  groupingProperty,SimpleComboBox<String> filterGroupCombo,
			FilterListPanel filterPanel,TabbedDataViewPanel tabbedDataViewPanel){
		
		DataViewPanel dataViewPanel = null;
		if (dataViewPanelType == DataViewPanelType.TABLE){  
			dataViewPanel = new TablePanel(model, groupingProperty,filterPanel,tabbedDataViewPanel); 

		}
		else if (dataViewPanelType == DataViewPanelType.PIE_CHART){  
			dataViewPanel = new PieChartPanel(model, controller, groupingProperty,filterGroupCombo,filterPanel,tabbedDataViewPanel); 

		}
		else if (dataViewPanelType == DataViewPanelType.BAR_CHART){
			  dataViewPanel = new BarChartPanel(model, controller, groupedDataViewController, groupingProperty,filterGroupCombo,filterPanel,tabbedDataViewPanel); 
		}
		
		
		
		return dataViewPanel;
	}

}

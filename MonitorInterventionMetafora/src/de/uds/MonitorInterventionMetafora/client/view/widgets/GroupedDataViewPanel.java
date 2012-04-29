package de.uds.MonitorInterventionMetafora.client.view.widgets;

import com.extjs.gxt.ui.client.widget.VerticalPanel;

import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.view.charts.BarChartPanel2;
import de.uds.MonitorInterventionMetafora.client.view.charts.PieChartPanel2;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.ActionPropertyRule;

public class GroupedDataViewPanel extends VerticalPanel {

	GroupingChooserPanel groupingChooserPanel;
	DataViewPanel2 dataViewPanel;
	
	public GroupedDataViewPanel(DataViewPanelType dataViewPanelType, ClientMonitorDataModel model, 
			ClientMonitorController controller, ActionPropertyRule  groupingProperty, String panelId, String groupingChooserId){
		this.dataViewPanel = createDataViewPanel(dataViewPanelType, model, controller, this, groupingProperty);
		
		groupingChooserPanel = new GroupingChooserPanel(this, model.getPropertiesComboBoxModel(), groupingProperty, groupingChooserId);

		this.setWidth(600);
		this.setId(panelId);

		this.add(groupingChooserPanel);
		this.add(dataViewPanel);
	}
	
	public void changeGroupingProperty(ActionPropertyRule newPropToGroupBy){
		dataViewPanel.setGroupingProperty(newPropToGroupBy);
	}

	public void refresh() {
//		groupingChooserPanel.refresh();
		dataViewPanel.refresh();
		layout();
	}

	public ActionPropertyRule getSelectedGroupingProperty() {
		return groupingChooserPanel.getSelectedProperty();
	}
	
	public DataViewPanel2 createDataViewPanel(DataViewPanelType dataViewPanelType, ClientMonitorDataModel model, 
			ClientMonitorController controller, GroupedDataViewPanel groupedDataViewController, ActionPropertyRule  groupingProperty){
		DataViewPanel2 dataViewPanel = null;
		if (dataViewPanelType == DataViewPanelType.PIE_CHART){  
//			  PieChartPanel barChartPanel = new PieChartPanel(groupedModel, controller, groupedDataViewController, groupingProperty);
			dataViewPanel = new PieChartPanel2(model, controller, groupingProperty); 

		}
		else if (dataViewPanelType == DataViewPanelType.BAR_CHART){
			  dataViewPanel = new BarChartPanel2(model, controller, groupedDataViewController, groupingProperty); 
		}
		return dataViewPanel;
	}
	
}

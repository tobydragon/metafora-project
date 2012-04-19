package de.uds.MonitorInterventionMetafora.client.view.widgets;

import com.extjs.gxt.ui.client.widget.VerticalPanel;

import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.datamodels.GroupedByPropertyModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientInterfaceManager;
import de.uds.MonitorInterventionMetafora.client.view.charts.BarChartPanel;
import de.uds.MonitorInterventionMetafora.client.view.charts.PieChartPanel;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.FilterAttributeName;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.FilterItemType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorProperty;

public class GroupedDataViewPanel extends VerticalPanel {

	GroupingChooserPanel groupingChooserPanel;
	DataViewPanel dataViewPanel;
	
	public GroupedDataViewPanel(DataViewPanelType dataViewPanelType, GroupedByPropertyModel groupedModel, 
			ClientInterfaceManager controller, IndicatorProperty  groupingProperty, String panelId, String groupingChooserId){
		this.dataViewPanel = createDataViewPanel(dataViewPanelType, groupedModel, controller, this, groupingProperty);
		
		//TODO: This should be set with initial groupingProperty, and if null, set to nothing
		groupingChooserPanel = new GroupingChooserPanel(this, groupedModel, groupingChooserId);

		this.setWidth(600);
		this.setId(panelId);

		this.add(groupingChooserPanel);
		this.add(dataViewPanel);
	}
	
	public void changeGroupingProperty(IndicatorProperty newPropToGroupBy){
		dataViewPanel.changeGroupingProperty(newPropToGroupBy);
	}

	public void refresh(GroupedByPropertyModel groupedModelUpdate) {
		groupingChooserPanel.refresh();
		dataViewPanel.refresh(groupedModelUpdate);
		layout();
	}

	public IndicatorProperty getSelectedGroupingProperty() {
		return groupingChooserPanel.getSelectedProperty();
	}
	
	public DataViewPanel createDataViewPanel(DataViewPanelType dataViewPanelType, GroupedByPropertyModel groupedModel, 
			ClientInterfaceManager controller, GroupedDataViewPanel groupedDataViewController, IndicatorProperty  groupingProperty){
		DataViewPanel dataViewPanel = null;
		if (dataViewPanelType == DataViewPanelType.PIE_CHART){  
//			  PieChartPanel barChartPanel = new PieChartPanel(groupedModel, controller, groupedDataViewController, groupingProperty);
			dataViewPanel = new PieChartPanel(groupedModel, controller, groupingProperty); 

		}
		else if (dataViewPanelType == DataViewPanelType.BAR_CHART){
			  dataViewPanel = new BarChartPanel(groupedModel, controller, groupedDataViewController, groupingProperty); 
		}
		return dataViewPanel;
	}
	
}

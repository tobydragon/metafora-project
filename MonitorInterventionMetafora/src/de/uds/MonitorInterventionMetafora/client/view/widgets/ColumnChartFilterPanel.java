package de.uds.MonitorInterventionMetafora.client.view.widgets;

import com.extjs.gxt.ui.client.widget.VerticalPanel;

import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.datamodels.EntityViewModel;
import de.uds.MonitorInterventionMetafora.client.view.charts.ExtendedColumnChart;
import de.uds.MonitorInterventionMetafora.client.view.charts.ExtendedPieChart;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.FilterAttributeName;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.FilterItemType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;

public class ColumnChartFilterPanel extends VerticalPanel {

	ActionMaintenance maintenance;
	EntityViewModel model;
	ColumnChartGroupTypeComboBox filterTypeComboBox;
	
	public ColumnChartFilterPanel(ActionMaintenance _maintenance){
		this.setWidth(600);
		maintenance=_maintenance;
		this.setId("barChartFilterPanel");
		model=new EntityViewModel(maintenance);
		
		
		filterTypeComboBox =new ColumnChartGroupTypeComboBox(model);
		IndicatorEntity _defaltEntity=new IndicatorEntity();
		_defaltEntity.setEntityName(FilterAttributeName.CLASSIFICATION.toString());
		_defaltEntity.setType(FilterItemType.ACTION_TYPE);
		
		ExtendedColumnChart _barChart=new ExtendedColumnChart(_defaltEntity,model);
		this.add(filterTypeComboBox);
		this.add(_barChart);
		
	}
}

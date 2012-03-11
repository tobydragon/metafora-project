package de.uds.MonitorInterventionMetafora.client.view.widgets;

import com.extjs.gxt.ui.client.widget.VerticalPanel;

import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.datamodels.PieChartViewModel;
import de.uds.MonitorInterventionMetafora.client.view.charts.ExtendedPieChart;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.FilterAttributeName;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.FilterItemType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;

public class PieChartFilterPanel extends VerticalPanel {

	ActionMaintenance maintenance;
	PieChartViewModel model;
	PieChartFilterTypeComboBox filterTypeComboBox;
	
	public PieChartFilterPanel(ActionMaintenance _maintenance){
		this.setWidth(600);
		maintenance=_maintenance;
		this.setId("_pieChartFilterPanel");
		model=new PieChartViewModel(maintenance);
		
		
		filterTypeComboBox =new PieChartFilterTypeComboBox(model);
		IndicatorEntity _defaltEntity=new IndicatorEntity();
		_defaltEntity.setEntityName(FilterAttributeName.CLASSIFICATION.toString());
		_defaltEntity.setType(FilterItemType.ACTION_TYPE);
		
		ExtendedPieChart _pieChart=new ExtendedPieChart(_defaltEntity,model);
		this.add(filterTypeComboBox);
		//this.add(_pieChart);
		
	}
}

package de.uds.MonitorInterventionMetafora.client.view.widgets;

import com.extjs.gxt.ui.client.widget.VerticalPanel;

import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.datamodels.EntityViewModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientInterfaceManager;
import de.uds.MonitorInterventionMetafora.client.view.charts.ExtendedPieChart;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.FilterAttributeName;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.FilterItemType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;

public class PieChartFilterPanel extends VerticalPanel {

	ActionMaintenance maintenance;
	EntityViewModel model;
	PieChartGroupTypeComboBox filterTypeComboBox;
	
	public PieChartFilterPanel(ActionMaintenance _maintenance, ClientInterfaceManager controller){
		this.setWidth(600);
		maintenance=_maintenance;
		this.setId("pieChartFilterPanel");
		model=new EntityViewModel(maintenance);
		
		
		filterTypeComboBox =new PieChartGroupTypeComboBox(model);
		IndicatorEntity _defaltEntity=new IndicatorEntity();
		_defaltEntity.setEntityName(FilterAttributeName.CLASSIFICATION.toString());
		_defaltEntity.setType(FilterItemType.ACTION_TYPE);
		
		ExtendedPieChart _pieChart=new ExtendedPieChart(_defaltEntity,model, controller);
		this.add(filterTypeComboBox);
		this.add(_pieChart);
		
	}
}

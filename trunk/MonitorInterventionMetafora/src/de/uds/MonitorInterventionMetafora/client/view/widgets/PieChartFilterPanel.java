package de.uds.MonitorInterventionMetafora.client.view.widgets;

import com.extjs.gxt.ui.client.widget.VerticalPanel;

import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.datamodels.GroupedByPropertyModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientInterfaceManager;
import de.uds.MonitorInterventionMetafora.client.view.charts.PieChartPanel;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.FilterAttributeName;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.FilterItemType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorProperty;

public class PieChartFilterPanel extends VerticalPanel {

	ActionMaintenance maintenance;
	GroupedByPropertyModel model;
	PieChartGroupTypeComboBox filterTypeComboBox;
	
	public PieChartFilterPanel(ActionMaintenance _maintenance, ClientInterfaceManager controller){
		this.setWidth(600);
		maintenance=_maintenance;
		this.setId("pieChartFilterPanel");
		model=new GroupedByPropertyModel(maintenance);
		
		
		filterTypeComboBox =new PieChartGroupTypeComboBox(model, controller);
		IndicatorProperty _defaltEntity=new IndicatorProperty();
		_defaltEntity.setEntityName(FilterAttributeName.CLASSIFICATION.toString());
		_defaltEntity.setType(FilterItemType.ACTION_TYPE);
		
		PieChartPanel _pieChart=new PieChartPanel(_defaltEntity,model, controller);
		this.add(filterTypeComboBox);
		this.add(_pieChart);
		
	}
}

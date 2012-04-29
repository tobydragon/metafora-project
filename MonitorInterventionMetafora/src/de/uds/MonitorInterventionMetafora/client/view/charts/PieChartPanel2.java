package de.uds.MonitorInterventionMetafora.client.view.charts;

import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart.PieOptions;

import de.uds.MonitorInterventionMetafora.client.datamodels.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.GroupedByPropertyModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.view.widgets.DataViewPanel;
import de.uds.MonitorInterventionMetafora.client.view.widgets.DataViewPanel2;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.ActionPropertyRule;

public class PieChartPanel2 extends  DataViewPanel2 {
	
	private PieChart pieChartView;
	private ClientMonitorDataModel model;
	private ActionPropertyRule  groupingProperty;
	
	
	public PieChartPanel2(ClientMonitorDataModel _model, ClientMonitorController controller, ActionPropertyRule  groupingProperty){
		this.groupingProperty= groupingProperty;
		
		this.setId("pieChartVerticalPanel");
		model=_model;
		createPieChart(controller);
	}

	public PieOptions  getPieChartOptions(){
		PieOptions options = PieChart.createPieOptions();
	    options.setWidth(500);
	    options.setHeight(400);
	    options.set3D(true);
	    options.setTitle("Indicator Overview");	
		    
	    return options;
	}
	
	public void createPieChart(ClientMonitorController controller) {
		if (groupingProperty != null){
			this.add(pieChartView= new PieChart(model.getDataTable(groupingProperty), getPieChartOptions()));  
		    pieChartView.addSelectHandler(new PieChartSelectionHandler(this, pieChartView, model, controller));
		    pieChartView.setLayoutData(new FitLayout());
		    refresh();
		}
	}
	 
	public void changeGroupingProperty(ActionPropertyRule propToGroupBy){
		 groupingProperty = propToGroupBy;
		 refresh();
	}
	 
	public void refresh(){
		pieChartView.draw(model.getDataTable(groupingProperty), getPieChartOptions());
		this.layout();
//		this.doLayout(); 
	}
	
	public ActionPropertyRule getGroupingProperty(){
		return groupingProperty;
	}
	
}

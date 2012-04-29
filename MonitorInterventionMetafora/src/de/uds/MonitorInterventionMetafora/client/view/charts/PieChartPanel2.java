package de.uds.MonitorInterventionMetafora.client.view.charts;

import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart.PieOptions;

import de.uds.MonitorInterventionMetafora.client.datamodels.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.view.widgets.DataViewPanel2;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.ActionPropertyRule;

public class PieChartPanel2 extends  DataViewPanel2 {
	
	private PieChart pieChartView;
	
	public PieChartPanel2(ClientMonitorDataModel _model, ClientMonitorController controller, ActionPropertyRule  groupingProperty){
		super(groupingProperty, _model);
		
		this.setId("pieChartVerticalPanel");
		createPieChart(controller);
	}
 
	public void refresh(){
		pieChartView.draw(model.getDataTable(groupingProperty), getPieChartOptions());
		super.refresh();
	}
	
	private void createPieChart(ClientMonitorController controller) {
		if (groupingProperty != null){
			this.add(pieChartView= new PieChart(model.getDataTable(groupingProperty), getPieChartOptions()));  
		    pieChartView.addSelectHandler(new ChartSelectionHandler(this, model, controller));
		    pieChartView.setLayoutData(new FitLayout());
		    refresh();
		}
	}
	
	private PieOptions  getPieChartOptions(){
		PieOptions options = PieChart.createPieOptions();
	    options.setWidth(500);
	    options.setHeight(400);
	    options.set3D(true);
	    options.setTitle("Indicator Overview");	
		    
	    return options;
	}
	
	@Override
	public int getSelectedRow() {
		//get currently selected item in the pie chart
		int selection=-1;
		JsArray<Selection> s = pieChartView.getSelections();
	    for (int i = 0; i < s.length(); ++i) {
	    	if (s.get(i).isRow()) {
		      selection=s.get(i).getRow();
		    }
	    }
	    return selection;
	}
}

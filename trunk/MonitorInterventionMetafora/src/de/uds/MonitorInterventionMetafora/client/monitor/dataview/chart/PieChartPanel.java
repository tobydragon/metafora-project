package de.uds.MonitorInterventionMetafora.client.monitor.dataview.chart;

import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart.PieOptions;

import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanelType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class PieChartPanel extends  DataViewPanel {
	
	private PieChart pieChartView;
	
	public PieChartPanel(ClientMonitorDataModel _model, ClientMonitorController controller, ActionPropertyRule  groupingProperty){
		super(groupingProperty, _model);
		
		this.setId("pieChartVerticalPanel");
		createPieChart(controller);
	}
 
	public void refresh(){
		//pieChartView.draw(model.getDataTable(groupingProperty));
	
		pieChartView.draw(model.getDataTable(groupingProperty), getPieChartOptions());
		pieChartView.setLayoutData(new FitLayout());
		
		//pieChartView.
		
		this.repaint();
		super.refresh();
		
	}
	
	private void createPieChart(ClientMonitorController controller) {
		if (groupingProperty != null){
			pieChartView = new PieChart(model.getDataTable(groupingProperty), getPieChartOptions());
			this.add(pieChartView);  
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

	@Override
	public DataViewPanelType getViewType() {
		
		return DataViewPanelType.PIE_CHART;
	}
}

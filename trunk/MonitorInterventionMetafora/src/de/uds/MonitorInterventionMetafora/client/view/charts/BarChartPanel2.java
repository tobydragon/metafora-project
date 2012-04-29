package de.uds.MonitorInterventionMetafora.client.view.charts;

import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

import de.uds.MonitorInterventionMetafora.client.datamodels.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.GroupedByPropertyModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.view.widgets.DataViewPanel;
import de.uds.MonitorInterventionMetafora.client.view.widgets.DataViewPanel2;
import de.uds.MonitorInterventionMetafora.client.view.widgets.GroupedDataViewPanel;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.ActionPropertyRule;

public class BarChartPanel2 extends DataViewPanel2 {
	
	private ColumnChart barChartView;
	private ClientMonitorDataModel model;
	private ActionPropertyRule  groupingProperty;
	
	
	public BarChartPanel2(ClientMonitorDataModel _model, ClientMonitorController controller, GroupedDataViewPanel groupedDataViewController, ActionPropertyRule  groupingProperty){
		this.groupingProperty= groupingProperty;
		
		this.setId("barChartVerticalPanel");
		model=_model;
		createBarChart();
	}

	public Options  getBarChartOptions(int _maxValue){
		
		 Options options = Options.create();
		    options.setHeight(380);
		    //options.setTitle("Entity Bars");
		    options.setWidth(550);
		    options.setColors("#1876E9");
		    
		    AxisOptions vAxisOptions = AxisOptions.create();
		 
		    vAxisOptions.setMinValue(0);
		    vAxisOptions.setMaxValue(_maxValue);
		    options.setVAxisOptions(vAxisOptions);
			return options;
		
		}
	
	public void createBarChart() {  
		if (groupingProperty != null){
			this.add(barChartView = new ColumnChart(model.getDataTable(groupingProperty), getBarChartOptions(model.getMaxValue(groupingProperty))));  
//		    pieChartView.addSelectHandler(new PieChartSelectionHandler(pieChartView, model, controller));
		    barChartView.draw(model.getDataTable(groupingProperty), getBarChartOptions(model.getMaxValue(groupingProperty)));
		    barChartView.setLayoutData(new FitLayout());
		}
	}
	 
	public void changeGroupingProperty(ActionPropertyRule propToGroupBy){
		 groupingProperty = propToGroupBy;
		 refresh();
	}
	
	public void refresh(){
	    barChartView.draw(model.getDataTable(groupingProperty), getBarChartOptions(model.getMaxValue(groupingProperty)));
		this.layout();
	}
	
}

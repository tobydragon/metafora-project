package de.uds.MonitorInterventionMetafora.client.monitor.dataview.chart;

import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import com.google.gwt.visualization.client.events.OnMouseOutHandler;
import com.google.gwt.visualization.client.events.OnMouseOverHandler;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.GroupedDataViewPanel;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class BarChartPanel extends DataViewPanel {
	
	private ColumnChart barChartView;
	int selection=-1;
	
	public BarChartPanel(ClientMonitorDataModel _model, ClientMonitorController controller, GroupedDataViewPanel groupedDataViewController, ActionPropertyRule  groupingProperty){
		super(groupingProperty, _model);
		
		this.setId("barChartVerticalPanel");
		createBarChart(controller);
		barChartView.addOnMouseOutHandler(createMouseOutHandler());
		barChartView.addOnMouseOverHandler(createOnMouseOverHandler());
	}

	public void createBarChart(ClientMonitorController controller) {  
		if (groupingProperty != null){
			barChartView = new ColumnChart(model.getDataTable(groupingProperty), getBarChartOptions(model.getMaxValue(groupingProperty)));
			this.add(barChartView);  
		    barChartView.addSelectHandler(new ChartSelectionHandler(this, model, controller));
		    barChartView.setLayoutData(new FitLayout());
		    refresh();
		}
	}
	
	public void refresh(){
	    barChartView.draw(model.getDataTable(groupingProperty), getBarChartOptions(model.getMaxValue(groupingProperty)));
	    super.refresh();
	}
	
	public Options  getBarChartOptions(int _maxValue){
		Options options = Options.create();
	    options.setHeight(380);
	    options.setWidth(550);
	    options.setColors("#1876E9");
	    
	    AxisOptions vAxisOptions = AxisOptions.create();
	 
	    vAxisOptions.setMinValue(0);
	    vAxisOptions.setMaxValue(_maxValue);
	    options.setVAxisOptions(vAxisOptions);
		return options;
	}

	@Override
	public int getSelectedRow() {
		return selection;
	}
	
	private  OnMouseOutHandler createMouseOutHandler(){
		 return new OnMouseOutHandler(){

			@Override
			public void onMouseOutEvent(OnMouseOutEvent event) {
				selection=event.getRow();
//				StringBuffer b = new StringBuffer();
//			    b.append(" row: ");
//			    b.append(event.getRow());
//			    b.append(", column: ");
//			    b.append(event.getColumn());
			    //onMouseOverAndOutStatus.setText("Mouse out of " + b.toString()); 
			}};
	 }
	 
	 private OnMouseOverHandler createOnMouseOverHandler(){
		return new OnMouseOverHandler(){

			@Override
			public void onMouseOverEvent(OnMouseOverEvent event) {
				
				  int row = event.getRow();
				  selection=row;
//				    int column = event.getColumn();
//				    StringBuffer b = new StringBuffer();
//				    b.append(" row: ");
//				    b.append(row);
//				    b.append(", column: ");
//				    b.append(column);
				   // onMouseOverAndOutStatus.setText("Mouse over " + b.toString()); 
			}}; 
		 
	 }
	
	
}

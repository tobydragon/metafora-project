package de.uds.MonitorInterventionMetafora.client.monitor.dataview.chart;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.events.OnMouseOutHandler;
import com.google.gwt.visualization.client.events.OnMouseOverHandler;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ActionPropertyValueGroupingTableModel;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanelType;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.GroupedDataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.TabbedDataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.table.TablePanel;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterListPanel;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class BarChartPanel extends DataViewPanel {
	
	private ColumnChart barChartView;
	int selection=-1;
	private SimpleComboBox<String> filterGroupCombo;
	FilterListPanel filterPanel;
	TabbedDataViewPanel tabbedDataViewPanel;
	
	public BarChartPanel(ClientMonitorDataModel _model, ClientMonitorController controller, GroupedDataViewPanel groupedDataViewController, 
			ActionPropertyRule  groupingProperty,
			SimpleComboBox<String> filterGroupCombo,FilterListPanel filterPanel,TabbedDataViewPanel tabbedDataViewPanel){
		super(groupingProperty, _model);
		
		this.filterPanel=filterPanel;
		this.tabbedDataViewPanel=tabbedDataViewPanel;
		this.filterGroupCombo=filterGroupCombo;
		//this.setId("barChartVerticalPanel");
		createBarChart(controller);
		barChartView.addOnMouseOutHandler(createMouseOutHandler());
		barChartView.addOnMouseOverHandler(createOnMouseOverHandler());
	}

	public void createBarChart(ClientMonitorController controller) {  
		if (groupingProperty != null){
			barChartView = new ColumnChart(model.getDataTable(groupingProperty), getBarChartOptions(model.getMaxValue(groupingProperty)));
			this.add(barChartView);  
		    barChartView.addSelectHandler(new ChartSelectionHandler(this, model, controller,filterGroupCombo));
		    barChartView.setLayoutData(new FitLayout());
		    refresh();
		}
	}
	
	public void refresh(){
		DataTable tableModel = model.getDataTable(groupingProperty);
	    barChartView.draw(tableModel, getBarChartOptions(model.getMaxValue(groupingProperty)));
		//barChartView.draw(tableModel);
	    barChartView.setLayoutData(new FitLayout());
	  
	    super.refresh();
	    this.repaint();
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

	@Override
	public DataViewPanelType getViewType() {
		
		return DataViewPanelType.BAR_CHART;
	}

	@Override
	public void enableAdjustSize() {
		
		
		filterPanel.addListener(Events.Collapse, new Listener<BaseEvent>()
		        {

		            public void handleEvent(BaseEvent be)
		            {
		            	tabbedDataViewPanel.addjustSize(false);
		            	
		            	
		            	for(Component item:tabbedDataViewPanel.getItems()){
		            		
		            		item.recalculate();
		            		item.repaint();
		            		
		            	}
		            	
		            };
		        });
				 
		filterPanel.addListener(Events.Expand, new Listener<BaseEvent>()
		        {

		            public void handleEvent(BaseEvent be)
		            {
		            	
		            	tabbedDataViewPanel.addjustSize(true);
		            	
for(Component item:tabbedDataViewPanel.getItems()){
		            		
		            		item.recalculate();
		            		item.repaint();
		            		
		            	}
		            	
		            };
		        });
	
	}
	
	
}

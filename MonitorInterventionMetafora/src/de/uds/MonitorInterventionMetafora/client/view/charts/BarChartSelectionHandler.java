package de.uds.MonitorInterventionMetafora.client.view.charts;

import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.events.OnMouseOutHandler;
import com.google.gwt.visualization.client.events.OnMouseOverHandler;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.events.OnMouseOutHandler.OnMouseOutEvent;
import com.google.gwt.visualization.client.events.OnMouseOverHandler.OnMouseOverEvent;
import com.google.gwt.visualization.client.events.SelectHandler.SelectEvent;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

import de.uds.MonitorInterventionMetafora.client.datamodels.GroupedByPropertyModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientInterfaceManager;
import de.uds.MonitorInterventionMetafora.client.view.widgets.GroupedDataViewPanel;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorProperty;

public class BarChartSelectionHandler extends SelectHandler{

	private final ColumnChart chartView;
	private ClientInterfaceManager filteringDataViewController;
	private GroupedByPropertyModel model;
	private int selection = -1;
	private GroupedDataViewPanel groupedDataController;
	
	public BarChartSelectionHandler(final ColumnChart chart, GroupedByPropertyModel model, ClientInterfaceManager controllerIn, GroupedDataViewPanel groupedDataController){
		this.chartView = chart;
		this.model = model;
		filteringDataViewController = controllerIn;
		this.groupedDataController = groupedDataController;
		
		chartView.addOnMouseOutHandler(createMouseOutHandler());
		chartView.addOnMouseOverHandler(createOnMouseOverHandler());
	}
	
	public void onSelect(SelectEvent event) {
    	
		//Get the right property name
		//IndicatorProperty entity = groupedDataController.getSelectedGroupingProperty();
	    
		//this operates on the first grouped item, need the current one		
		IndicatorProperty entity = model.getIndicatorEntity(selection);
		
	    filteringDataViewController.addFilterItem(entity);
	    filteringDataViewController.refreshTabPanel(); 
    }
	
	private  OnMouseOutHandler createMouseOutHandler(){
		 return new OnMouseOutHandler(){

			@Override
			public void onMouseOutEvent(OnMouseOutEvent event) {
				selection=event.getRow();
				StringBuffer b = new StringBuffer();
			    b.append(" row: ");
			    b.append(event.getRow());
			    b.append(", column: ");
			    b.append(event.getColumn());
			    //onMouseOverAndOutStatus.setText("Mouse out of " + b.toString()); 
			}};
	 }
	 
	 private OnMouseOverHandler createOnMouseOverHandler(){
		return new OnMouseOverHandler(){

			@Override
			public void onMouseOverEvent(OnMouseOverEvent event) {
				
				  int row = event.getRow();
				  selection=row;
				    int column = event.getColumn();
				    StringBuffer b = new StringBuffer();
				    b.append(" row: ");
				    b.append(row);
				    b.append(", column: ");
				    b.append(column);
				   // onMouseOverAndOutStatus.setText("Mouse over " + b.toString()); 
			}}; 
		 
	 }

}

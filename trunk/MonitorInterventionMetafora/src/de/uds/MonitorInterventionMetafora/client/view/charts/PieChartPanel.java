package de.uds.MonitorInterventionMetafora.client.view.charts;

import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart.PieOptions;

import de.uds.MonitorInterventionMetafora.client.datamodels.GroupedByPropertyModel;
import de.uds.MonitorInterventionMetafora.client.manager.FilteredDataViewManager;
import de.uds.MonitorInterventionMetafora.client.view.widgets.DataViewPanel;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorProperty;

public class PieChartPanel extends  DataViewPanel {
	
	private PieChart pieChartView;
	private GroupedByPropertyModel model;
	private IndicatorProperty  groupingProperty;
	
	
	public PieChartPanel(GroupedByPropertyModel _model, FilteredDataViewManager controller, IndicatorProperty  groupingProperty){
		this.groupingProperty= groupingProperty;
		
		this.setId("pieChartVerticalPanel");
		model=_model;
		this.removeAll();
		this.add(createPieChart(model.getEntityDataTable(groupingProperty), controller));
		
		pieChartView.setLayoutData(new FitLayout());	
		this.layout(true);
		this.doLayout();
	}

	public PieOptions  getPieChartOptions(){
		PieOptions options = PieChart.createPieOptions();
	    options.setWidth(500);
	    options.setHeight(400);
	    options.set3D(true);
	    options.setTitle("Indicator Overview");	
		    
	    return options;
	}
	
	public PieChart createPieChart(DataTable data, FilteredDataViewManager controller) {  
	    pieChartView= new PieChart(data, getPieChartOptions());  
	    pieChartView.addSelectHandler(new PieChartSelectionHandler(pieChartView, model, controller));
		
	    pieChartView.draw(model.getEntityDataTable(groupingProperty), getPieChartOptions());
	    return pieChartView;
	}
	 
	public void changeGroupingProperty(IndicatorProperty propToGroupBy){
		 groupingProperty = propToGroupBy;
		 refresh(model);
	}
	 
	public void refresh(GroupedByPropertyModel modelUpdate){
		this.model= modelUpdate;
		pieChartView.draw(model.getEntityDataTable(groupingProperty), getPieChartOptions());
		this.layout(); 
	}
	
}

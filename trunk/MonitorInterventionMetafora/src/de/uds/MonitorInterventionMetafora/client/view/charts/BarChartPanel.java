package de.uds.MonitorInterventionMetafora.client.view.charts;

import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;

import de.uds.MonitorInterventionMetafora.client.datamodels.GroupedByPropertyModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientInterfaceManager;
import de.uds.MonitorInterventionMetafora.client.view.widgets.DataViewPanel;
import de.uds.MonitorInterventionMetafora.client.view.widgets.GroupedDataViewPanel;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorProperty;

public class BarChartPanel extends DataViewPanel {
	
	private ColumnChart barChartView;
	private GroupedByPropertyModel model;
	private IndicatorProperty  groupingProperty;
	
	
	public BarChartPanel(GroupedByPropertyModel _model, ClientInterfaceManager controller, GroupedDataViewPanel groupedDataViewController, IndicatorProperty  groupingProperty){
		this.groupingProperty= groupingProperty;
		
		this.setId("barChartVerticalPanel");
		model=_model;
		this.removeAll();
		this.add(createBarChart(model.getEntityDataTable(groupingProperty), controller, groupedDataViewController));
		
		barChartView.setLayoutData(new FitLayout());	
		this.layout(true);
		this.doLayout();
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
	
	public ColumnChart createBarChart(DataTable data, ClientInterfaceManager controller, GroupedDataViewPanel groupedDataViewController) {  
		
		barChartView = new ColumnChart(data, getBarChartOptions(model.getMaxValue()));
		barChartView.addSelectHandler(new BarChartSelectionHandler(barChartView, model, controller, groupedDataViewController));
		
		barChartView.setLayoutData(new FitLayout());
		return barChartView;
	}
	 
	public void changeGroupingProperty(IndicatorProperty propToGroupBy){
		 groupingProperty = propToGroupBy;
		 refresh(model);
	}
	 
	public void refresh(GroupedByPropertyModel modelUpdate){
		this.model= modelUpdate;
		barChartView.draw(model.getEntityDataTable(groupingProperty), getBarChartOptions(model.getMaxValue()));
		this.layout();
	}
	
}

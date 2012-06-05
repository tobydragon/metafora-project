package de.uds.MonitorInterventionMetafora.client.monitor.dataview.chart;

import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart.PieOptions;

import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanelType;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.TabbedDataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterListPanel;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class PieChartPanel extends  DataViewPanel {
	
	private PieChart pieChartView;
	private SimpleComboBox<String> filterGroupCombo;
	
	public PieChartPanel(ClientMonitorDataModel _model, ClientMonitorController controller, ActionPropertyRule  groupingProperty,
			SimpleComboBox<String> filterGroupCombo,FilterListPanel filterPanel,TabbedDataViewPanel tabbedDataViewPanel){
		super(groupingProperty, _model);
		this.filterGroupCombo=filterGroupCombo;
		//this.setId("pieChartVerticalPanel");
		createPieChart(controller);
	}
 
	public void refresh(){
		//pieChartView.draw(model.getDataTable(groupingProperty));
	
		pieChartView.draw(model.getDataTable(groupingProperty), getPieChartOptions());
		pieChartView.setLayoutData(new FitLayout());
		this.repaint();
		super.refresh();
		
	}
	
	private void createPieChart(ClientMonitorController controller) {
		if (groupingProperty != null){
			pieChartView = new PieChart(model.getDataTable(groupingProperty), getPieChartOptions());
			this.add(pieChartView);  
		    pieChartView.addSelectHandler(new ChartSelectionHandler(this, model, controller,filterGroupCombo));
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

	@Override
	public void enableAdjustSize() {
		// TODO Auto-generated method stub
		
	}
}

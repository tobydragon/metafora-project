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
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.events.SelectHandler.SelectEvent;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

import de.uds.MonitorInterventionMetafora.client.datamodels.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.GroupedByPropertyModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.view.widgets.GroupedDataViewPanel;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.ActionPropertyRule;

public class PieChartSelectionHandler extends SelectHandler{

	private PieChartPanel2 piePanel;
	private final PieChart chartView;
	private ClientMonitorController controller;
	private ClientMonitorDataModel model;
		
	public PieChartSelectionHandler(PieChartPanel2 piePanel, final PieChart chart, ClientMonitorDataModel model, ClientMonitorController controllerIn){
		this.piePanel = piePanel;
		this.chartView = chart;
		this.model = model;
		controller = controllerIn;
//		this.groupedDataController = groupedDataController;

	}
	
	public void onSelect(SelectEvent event) {

		//get currently selected item in the pie chart
		int selection=-1;
		JsArray<Selection> s = chartView.getSelections();
	    for (int i = 0; i < s.length(); ++i) {
	    	if (s.get(i).isRow()) {
		      selection=s.get(i).getRow();
		    }
	    }
    	
	    ActionPropertyRule currentGroupingProp = piePanel.getGroupingProperty();
	    
	    //Make copy, and fill in extra info
	    ActionPropertyRule newFilterRule = new ActionPropertyRule(currentGroupingProp.getType(), currentGroupingProp.getPropertyName());
	   
	    //get the value from the data table used to populate the char that was clicked
	    newFilterRule.setValue(model.getDataTable(currentGroupingProp).getValueString(selection, 0));
	    newFilterRule.setOperationType(OperationType.EQUALS);
	    
  	  	//TODO: explain: why entity is null if filter is already in the list?
//	    if(entity==null){
//  	  		MessageBox.info("Message","Selected Filter is<ul><li> already in  the filter list</li></ul>", null);
//  	  		return; 
//  	  	}
	    
	    controller.addFilterItem(newFilterRule);
	    controller.refreshTabPanel(); 
    }

}

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

import de.uds.MonitorInterventionMetafora.client.datamodels.EntityViewModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientInterfaceManager;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;

public class PieChartSelectionHandler extends SelectHandler{

	private final PieChart chart;
	private ClientInterfaceManager interfaceManager;
	private EntityViewModel model;
		
	public PieChartSelectionHandler(final PieChart chart, EntityViewModel model, ClientInterfaceManager controller){
		this.chart = chart;
		this.model = model;
		interfaceManager = controller;
	}
	
	public void onSelect(SelectEvent event) {

		//get currently selected item in the pie chart
		int selection=-1;
		JsArray<Selection> s = chart.getSelections();
	    for (int i = 0; i < s.length(); ++i) {
	    	if (s.get(i).isRow()) {
		      selection=s.get(i).getRow();
		    }
	    }
    	
	    IndicatorEntity entity=model.getIndicatorEntity(selection);
  	  	//TODO: explain
	    if(entity==null){
  	  		MessageBox.info("Message","Selected Filter is<ul><li> already in  the filter list</li></ul>", null);
  	  		return; 
  	  	}
	    
	    interfaceManager.addFilterItem(entity);
	    interfaceManager.refreshTabPanel(); 
    }

}

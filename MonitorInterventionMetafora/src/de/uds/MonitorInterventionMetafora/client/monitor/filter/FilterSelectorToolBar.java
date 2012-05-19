package de.uds.MonitorInterventionMetafora.client.monitor.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;


import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.communication.ServerCommunication;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestConfigurationCallBack;
import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class FilterSelectorToolBar extends ToolBar implements RequestConfigurationCallBack{
	
	
	
	 private Map<String, ActionFilter> filterSets;
	 private EditorGrid<FilterGridRow> grid;
	 private ClientMonitorController controller;
	 private ClientMonitorDataModel model;
	 private CommunicationServiceAsync serverlet;
	 private SimpleComboBox<String> filterGroupCombo;
	 private Button clearbtn;
	 private Button saveAsBtn;
	 
	 public FilterSelectorToolBar(EditorGrid<FilterGridRow> grid,ClientMonitorDataModel model,ClientMonitorController controller){
		 filterGroupCombo = new SimpleComboBox<String>(); 
		 	this.grid=grid;	
		 	this.model=model;
		 	this.controller=controller;
		 	model.getServiceServlet().requestConfiguration(null, this);
		 	filterSets=new HashMap<String, ActionFilter>();
		 	
		 	
		 	
		    
	 }

	 
	 
	 void renderToolBar(){
		 this.add(new LabelToolItem("Filter Set:"));
		 this.add(renderFilterSelectorComboBox());
		 saveAsBtn= new Button("Save As",new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					MessageBox.info("Message","All filters are removed!!", null);		    
				    grid.getStore().removeAll();
				    filterGroupCombo.clearSelections();
				} });
		 saveAsBtn.setIcon(Resources.ICONS.save());
		 saveAsBtn.setToolTip("Save  property rules as a filter");
		 
		 clearbtn = new Button("Clear",new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					MessageBox.info("Message","All filters are removed!!", null);		    
				    grid.getStore().removeAll();
				    filterGroupCombo.clearSelections();
				} });
		 
		 clearbtn.setIcon(Resources.ICONS.clear());
		 clearbtn.setToolTip("Clear all filter rules.");
		 this.add(saveAsBtn);
		 this.add(clearbtn);
		 
	 }
	 
	 public SimpleComboBox<String> getFilterSelectorComboBox(){
		 
		 return filterGroupCombo;
	 }
	 
	 public SimpleComboBox<String> renderFilterSelectorComboBox(){
		 
	    filterGroupCombo.setTriggerAction(TriggerAction.ALL);  
	    filterGroupCombo.setEditable(false);  
	    filterGroupCombo.setFireChangeEventOnSetValue(true);  
	    filterGroupCombo.setWidth(100);
	   
	    for(String filtername: filterSets.keySet())
		  {
			    filterGroupCombo.add(filtername);  
		  }
	    
	    
	    
	    filterGroupCombo.addListener(Events.Change, new Listener<FieldEvent>() {  
	      public void handleEvent(FieldEvent be) {  
	        String filterSetKey = filterGroupCombo.getSimpleValue();
	        
	        
	        if(filterSets.containsKey(filterSetKey)){
	        	
	        	ActionFilter filter=filterSets.get(filterSetKey);
	        	grid.getStore().removeAll();
	        	
	        	for(ActionPropertyRule rule: filter.getActionPropertyRules()){
	 
	        		 FilterGridRow _filterItem=new FilterGridRow(rule);
	        	     grid.stopEditing();  
	        	     grid.getStore().insert(_filterItem, 0);  
	        	     grid.startEditing(grid.getStore().indexOf(_filterItem), 0); 

	        	}
	        	
//	        	controller.filtersUpdated();
	        	
	        	
	        }
	        
	        
	      }  
	    }); 
	    
	    
		 return filterGroupCombo;
	 }


	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSuccess(Configuration result) {
		filterSets=result.getActionFilters();
		renderToolBar();
		
		
	}


	 
}

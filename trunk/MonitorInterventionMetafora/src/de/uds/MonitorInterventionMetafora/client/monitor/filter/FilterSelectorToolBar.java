package de.uds.MonitorInterventionMetafora.client.monitor.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;


import de.uds.MonitorInterventionMetafora.client.User;
import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.communication.ServerCommunication;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestConfigurationCallBack;
import de.uds.MonitorInterventionMetafora.client.logger.ComponentType;
import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.UpdaterToolbar;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.PropertyLocation;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

public class FilterSelectorToolBar extends ToolBar implements RequestConfigurationCallBack{
	
	
	
	 private Map<String, ActionFilter> filterSets;
	 private EditorGrid<FilterGridRow> grid;
	 private ClientMonitorController controller;
	 private ClientMonitorDataModel model;
	 
	 private SimpleComboBox<String> filterGroupCombo;
	 private Button clearbtn;
	 public Button saveAsBtn;
	 public Button applyMainFilterBtn;
	 public Button applyFilterBtn;
	 private Button deleteBtn;
	 private CheckBox autoRefreshCharts;
	 boolean isMainFilterSet;
	 boolean updateViews=false;
	 

	 
	 public FilterSelectorToolBar(final EditorGrid<FilterGridRow> grid,final ClientMonitorDataModel model,final ClientMonitorController controller,boolean isMainFilterSet){
		
		 this.isMainFilterSet=isMainFilterSet;
		 applyFilterBtn=new Button("Apply");
		 applyMainFilterBtn=new Button("Apply");
		 autoRefreshCharts= new CheckBox();
		 autoRefreshCharts.setBoxLabel("Auto View Update");
		 autoRefreshCharts.setValue(false);
		 //controller.removeFilterModelListeners(grid.getStore());
		 autoRefreshCharts.addListener(Events.Change, 
					new Listener<BaseEvent>() {
			        	public void handleEvent(BaseEvent be) {
			        		if (autoRefreshCharts.getValue()) {
			        			updateViews=true;
			        			controller.setOnlyRefreshTable(true);
			        			//applyFilterBtn.setEnabled(false);
			        			//controller.addFilterModelListeners(grid.getStore());
			        			model.updateFilteredList();
								controller.filtersUpdated();
			        		
			        			
			        		}
			        		else {
			        			
			        			updateViews=false;
			        			
			        			controller.setOnlyRefreshTable(false);
			        			//controller.filtersUpdated();
			        			//model.updateFilteredList();
			        			//controller.removeFilterModelListeners(grid.getStore());
			        			//applyFilterBtn.setEnabled(true);
			        		}
			        	}
					});
		 filterGroupCombo = new SimpleComboBox<String>(); 
		
		 	this.grid=grid;	
		 	this.model=model;
		 	this.controller=controller;
		 	
		 	if(isMainFilterSet){
		 	model.getServiceServlet().requestMainConfiguration( this);
		 	}
		 	else{		 		
		 		model.getServiceServlet().requestConfiguration(this);
		 	}
		    
	 }

	 
	 public Button getApplyButton(){
		 
		 return applyMainFilterBtn;
	 }
	 boolean isAlreadyIn(String name){
		for(SimpleComboValue<String> filterName:filterGroupCombo.getStore().getRange(0, filterGroupCombo.getStore().getCount())){

			if(name.equalsIgnoreCase(filterName.getValue())){
			
			return true;
			}
			
		}
		
		 return false;
	 }
	 void renderToolBar(){
		 this.add(new LabelToolItem("Filter Set:"));
		 this.add(renderFilterSelectorComboBox());
		 saveAsBtn= new Button("Save Filter",new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					
				String filterName=filterGroupCombo.getSimpleValue();
				
				if(filterName.equalsIgnoreCase(""))
					return;
				if(isAlreadyIn(filterName)){
					
					MessageBox.info("Info","Filter Name is already in the list!", null);
					return ;
					}
							
			
				saveAsBtn.setEnabled(false);
				
				
					ActionFilter filter=new ActionFilter();
					filter.setEditable(false);
					filter.setName(filterName.toLowerCase());
					
					for(FilterGridRow row : grid.getStore().getRange(0,grid.getStore().getCount()-1)){
					
						filter.addFilterRule(row.getActionPropertyRule());
					}
				
		
					model.getServiceServlet().saveNewFilter(isMainFilterSet, filter,new AsyncCallback<Boolean>(){

						
						@Override
						public void onFailure(Throwable caught) {
							MessageBox.info("Error","New filter is not saved", null);
							
							saveAsBtn.setEnabled(true);
							
						}

						@Override
						public void onSuccess(Boolean result) {
							
							saveAsBtn.setEnabled(true);
							if(!result){
							MessageBox.info("Error","New filter cannot be  saved!" +result, null);
							}
							else{
								MessageBox.info("Info","New filter is added successfully!", null);
								if(isMainFilterSet){
								model.getServiceServlet().requestMainConfiguration(new AsyncCallback<Configuration>(){

									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void onSuccess(Configuration result) {
										filterSets.clear();
								
										filterSets=result.getActionFilters();
										update();
										
									}});
	
							}
								
								else{
									
									model.getServiceServlet().requestConfiguration(new AsyncCallback<Configuration>(){

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub
											
										}

										@Override
										public void onSuccess(Configuration result) {
											filterSets.clear();
									
											filterSets=result.getActionFilters();
											update();
											
										}});
								}
							}
							
						}});
					
				    
				} });
		 saveAsBtn.setIcon(Resources.ICONS.save());
		 saveAsBtn.setToolTip("Save  property rules as a filter");
		 
		 
		 deleteBtn = new Button("Remove",new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					
					String filterName=filterGroupCombo.getSimpleValue();
					
				    
				} });
		 deleteBtn.setIcon(Resources.ICONS.delete());
		 
		 
		 applyMainFilterBtn.addSelectionListener(new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					
				
					model.applyMainFilter();
					model.updateFilteredList();
					controller.filtersUpdated();
					applyMainFilterBtn.setEnabled(false);
					
				MessageBox.info("Info","Selected configurations are applied!",null);
					
					
				    
				} });
		 
		 applyMainFilterBtn.setIcon(Resources.ICONS.ok());
		 applyMainFilterBtn.setEnabled(false);
		 
		 applyFilterBtn.addSelectionListener(new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					
					
					model.updateFilteredList();
					controller.filtersUpdated();
				
					MessageBox.info("Info","Selected filters are applied!",null);
					
				} });
					
		 applyFilterBtn.setIcon(Resources.ICONS.ok());
		
		 
		 clearbtn = new Button("Clear",new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					MessageBox.info("Message","All filters are removed!!", null);		    
				    grid.getStore().removeAll();
				    filterGroupCombo.clearSelections();
				  
					model.updateFilteredList();
					controller.filtersUpdated();
				} });
		 
		 clearbtn.setIcon(Resources.ICONS.clear());
		 clearbtn.setToolTip("Clear all filter rules.");
		 this.add(saveAsBtn);
		// this.add(deleteBtn);
		 this.add(clearbtn);
		 if(isMainFilterSet){
		 this.add(applyMainFilterBtn);
		 }
		 else{
			// this.add(applyFilterBtn);
			// this.add(autoRefreshCharts);
		 }
		 
		 
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
	        	
	        	filterGroupCombo.setEditable(false);
	        	
	        	

	        }
	        applyMainFilterBtn.setEnabled(true);
	        
	      }  
	    }); 
	    
	    
		 return filterGroupCombo;
	 }


	 public void update(){
		 
		 filterGroupCombo.clear();
		 filterGroupCombo.getStore().clearFilters();
		 for(String filtername: filterSets.keySet())
		  {
			   filterGroupCombo.add(filtername);  
		  }
		 grid.getStore().removeAll();
		
		 filterGroupCombo.setEditable(false);
		 controller.filtersUpdated();
	 }
	 
	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSuccess(Configuration result) {
		filterSets=new HashMap<String, ActionFilter>();
		filterSets=result.getActionFilters();
		renderToolBar();
		if(isMainFilterSet){
		if(User.mainConfig!=null){
			 filterGroupCombo.setSimpleValue(User.mainConfig);
			 	model.applyMainFilter();
				model.updateFilteredList();
				controller.filtersUpdated();
				applyMainFilterBtn.setEnabled(false);
			 }
		}
		
		
	}


	 
}

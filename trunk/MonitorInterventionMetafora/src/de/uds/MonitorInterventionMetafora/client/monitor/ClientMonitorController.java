package de.uds.MonitorInterventionMetafora.client.monitor;

import java.util.Vector;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;

import de.uds.MonitorInterventionMetafora.client.logger.ComponentType;
import de.uds.MonitorInterventionMetafora.client.logger.UserLog;
import de.uds.MonitorInterventionMetafora.client.logger.Logger;
import de.uds.MonitorInterventionMetafora.client.logger.UserActionType;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.OperationsComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.GroupedDataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterGridRow;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class ClientMonitorController {
	
	private ClientMonitorDataModel dataModel;
	
	private Vector<GroupedDataViewPanel> dataViewPanels;
	private Listener<StoreEvent<FilterGridRow>> storeAddListener;
	private Listener<StoreEvent<FilterGridRow>> storeRemoveListener;
	
	public ClientMonitorController(ClientMonitorDataModel actionModel){
		this.dataModel = actionModel;
		
		dataViewPanels = new Vector<GroupedDataViewPanel>();
	}
	
	
	public void addFilterModelListeners(ListStore<FilterGridRow> filterGridStore){
		
		
		storeAddListener=new Listener<StoreEvent<FilterGridRow>>() {
		        public void handleEvent(StoreEvent<FilterGridRow> be) {
		        	filtersUpdated();
		        	UserLog userActionLog=new UserLog();
		        	userActionLog.setComponentType(ComponentType.FILTER_TABLE);
		        	userActionLog.setDescription("New Filter Rule is added to the filter.",be.getModels().get(0).getActionPropertyRule());
		        	userActionLog.setTriggeredBy(be.getModels().get(0).getActionPropertyRule().getOrigin());
		        	userActionLog.setUserActionType(UserActionType.FILTER_ADDED);	
		        	Logger.getLoggerInstance().log(userActionLog);
		        	}
		      };
		
		      storeRemoveListener=new Listener<StoreEvent<FilterGridRow>>() {
			        public void handleEvent(StoreEvent<FilterGridRow> be) {
			        	filtersUpdated();
			        	UserLog userActionLog=new UserLog();
			        	userActionLog.setComponentType(ComponentType.FILTER_TABLE);
			        	userActionLog.setDescription("Filter Rule is removed from the filter.",be.getModel().getActionPropertyRule());
			        	userActionLog.setTriggeredBy(be.getModel().getActionPropertyRule().getOrigin());
			        	userActionLog.setUserActionType(UserActionType.FILTER_REMOVED);
			        	Logger.getLoggerInstance().log(userActionLog);
			        }
			      };
		
		filterGridStore.addListener(Store.Add,storeAddListener);
		filterGridStore.addListener(Store.Remove,storeRemoveListener);
		
		
		
	}
	
	
	
	public void removeFilterModelListeners(ListStore<FilterGridRow> filterGridStore){
		
		filterGridStore.removeListener(Store.Add,storeAddListener);
		filterGridStore.removeListener(Store.Remove,storeRemoveListener);
		
	}
	
	
	
	
	
	
	public void addMainFilterListeners(ListStore<FilterGridRow> filterGridStore){
		
		filterGridStore.addListener(Store.Add, new Listener<StoreEvent<FilterGridRow>>() {
	        public void handleEvent(StoreEvent<FilterGridRow> be) {
	        
	     
	        	
	        	UserLog userActionLog=new UserLog();
	        	userActionLog.setComponentType(ComponentType.MAIN_CONFIGURATION_TABLE);
	        	userActionLog.setDescription("New Filter Rule is added to the Main Configuration.",be.getModels().get(0).getActionPropertyRule());
	        	userActionLog.setTriggeredBy(be.getModels().get(0).getActionPropertyRule().getOrigin());
	        	userActionLog.setUserActionType(UserActionType.CONFIGURATION_RULE_ADDED);
	        
	        	
	        	Logger.getLoggerInstance().log(userActionLog);
	      
	        }
	      });

	
		filterGridStore.addListener(Store.Remove, new Listener<StoreEvent<FilterGridRow>>() {
	        public void handleEvent(StoreEvent<FilterGridRow> be) {
	       
	        	UserLog userActionLog=new UserLog();
	        	userActionLog.setComponentType(ComponentType.MAIN_CONFIGURATION_TABLE);
	        	userActionLog.setDescription("Filter Rule is removed from the Main Configuration.",be.getModel().getActionPropertyRule());
	        	userActionLog.setTriggeredBy(be.getModel().getActionPropertyRule().getOrigin());
	        	userActionLog.setUserActionType(UserActionType.CONFIGURATION_RULE_REMOVED);
	        	Logger.getLoggerInstance().log(userActionLog);
	        	
	        	
	        }
	      });
		
		
		
	}
	
	public void addDataView(GroupedDataViewPanel panel){
		dataViewPanels.add(panel);
	}
	
	public void refreshViews() {
		Log.debug("Refreshing View is started");
		for (GroupedDataViewPanel panel : dataViewPanels){
			panel.refresh();
			Log.debug(panel.getTitle()+" is refreshed");
		}
		Log.debug("Refreshing View is completed");
	}
	
	public void filtersUpdated(){
		dataModel.updateFilteredList();
        refreshViews();
	}
	
//	 ------------------------ Code that should be moved  to filter class ---------------------------//

	public void addRule(ActionPropertyRule newFilterEntity){		
		String _key= newFilterEntity.getKey();
		if(!isInFilterList(_key) && !newFilterEntity.getValue().equalsIgnoreCase("")){
	        FilterGridRow  _newRow = new FilterGridRow(newFilterEntity); 
	        dataModel.getFilterGridViewModel().insert(_newRow, 0);  
	       //* _grid.startEditing(_grid.getStore().indexOf(_newRow), 0); 
	       //* _filterCombo.clearSelections();
	       
	       
	        //TODO: make a new way to set the tabs back to table tab
//	        TabPanel tabPanel = getMultiModelTabPanel();
//	        TabItem tabItem= getTableViewTabItem();	
//	        tabPanel.setTabIndex(0);
//	        tabPanel.repaint();
//	        tabPanel.setLayoutData(new FitLayout());
//	        tabPanel.setSelection(tabItem);
	        
//	        filtersUpdated();
//		    MessageBox.info("Message","Filter is added to the list!", null);
	        
	        
   	    }
        else {	
        	MessageBox.info("Message","Selected Filter is<ul><li> already in  the filter list</li></ul>", null);
        }
	}
	
	
	
	boolean isInFilterList(String _key){
		 for (int i = 0; i < dataModel.getFilterGridViewModel().getCount(); i++) {
			 FilterGridRow _item =	 dataModel.getFilterGridViewModel().getAt(i);
			 String rowKey= _item.getKey();
			 
			 if(rowKey.equalsIgnoreCase(_key)){	
				return true;
			}
		 }
		 return false;
	 }
	
	 
//	 ------------------------ Code that should be removed ---------------------------//
		
	
	
	
	/*
public DataViewPanel getView(String id){
		
		return (DataViewPanel)ComponentManager.get().get(id);
	}


	public SimpleComboBox<String> getFilterListComboBox(){
		
		return (SimpleComboBox<String>) ComponentManager.get().get("_filterGroupCombo");
	}
	
	public ComboBox<OperationsComboBoxModel> getOperationsComboBox(){
		
		return (ComboBox<OperationsComboBoxModel>) ComponentManager.get().get("_operationComboBox");
		
	}
		
	
	public TextField<String> getFilterEntityValueTextField(){
		
		return (TextField<String>) ComponentManager.get().get("entityValueText");
	}
*/
}

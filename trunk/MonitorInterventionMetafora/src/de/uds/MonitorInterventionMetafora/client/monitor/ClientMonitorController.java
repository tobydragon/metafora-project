package de.uds.MonitorInterventionMetafora.client.monitor;

import java.util.Vector;

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
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.OperationsComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.GroupedDataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterGridRow;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class ClientMonitorController {
	
	private ClientMonitorDataModel dataModel;
	
	private Vector<GroupedDataViewPanel> dataViewPanels;
	
	public ClientMonitorController(ClientMonitorDataModel actionModel){
		this.dataModel = actionModel;
		addFilterModelListeners(actionModel.getFilterGridViewModel());
		dataViewPanels = new Vector<GroupedDataViewPanel>();
	}
	
	
	void addFilterModelListeners(ListStore<FilterGridRow> filterGridStore){
		
		filterGridStore.addListener(Store.Add, new Listener<StoreEvent<FilterGridRow>>() {
	        public void handleEvent(StoreEvent<FilterGridRow> be) {
	        	filtersUpdated();        	
	        }
	      });
		
		filterGridStore.addListener(Store.Remove, new Listener<StoreEvent<FilterGridRow>>() {
	        public void handleEvent(StoreEvent<FilterGridRow> be) {
	        	filtersUpdated();        	
	        }
	      });
		
		
		
	}
	public void addDataView(GroupedDataViewPanel panel){
		dataViewPanels.add(panel);
	}
	
	public void refreshViews() {
		for (GroupedDataViewPanel panel : dataViewPanels){
			panel.refresh();
		}
	}
	
	public void filtersUpdated(){
		dataModel.updateFilteredList();
        refreshViews();
	}
	
//	 ------------------------ Code that should be moved  to filter class ---------------------------//
//TODO: Remove this method
	public void addFilterItem(ActionPropertyRule newFilterEntity){		
		EditorGrid<FilterGridRow> _grid = getFilterListEditorGrid();
		SimpleComboBox<String> _filterCombo = getFilterListComboBox();
		String _key= newFilterEntity.getKey();
          
		if(!isInFilterList(_key,_grid) && !newFilterEntity.getValue().equalsIgnoreCase("")){
        
//	        if(!reset){
//	        	reset=true;
//	        	_filterCombo.clearSelections();
//	        }        
	
	        FilterGridRow  _newRow = new FilterGridRow(newFilterEntity); 
	
	        _grid.stopEditing();  
	        _grid.getStore().insert(_newRow, 0);  
	        _grid.startEditing(_grid.getStore().indexOf(_newRow), 0); 
	        _filterCombo.clearSelections();
	       
	       
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
	
	
	
	boolean isInFilterList(String _key,EditorGrid<FilterGridRow> _grid){
		 for (int i = 0; i < _grid.getStore().getCount(); i++) {
			 FilterGridRow _item =	 _grid.getStore().getAt(i);
			 String rowKey= _item.getKey();
			 
			 if(rowKey.equalsIgnoreCase(_key)){	
				return true;
			}
		 }
		 return false;
	 }
	
	 
//	 ------------------------ Code that should be removed ---------------------------//
		
	public EditorGrid<FilterGridRow>  getFilterListEditorGrid(){
		
		EditorGrid<FilterGridRow> editorGrid = (EditorGrid<FilterGridRow>) ComponentManager.get().get("_filterItemGrid");
		return editorGrid;
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

}

package de.uds.MonitorInterventionMetafora.client.monitor;

import java.util.Vector;

import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.OperationsComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.TableViewModel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.GroupedDataViewPanel;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.table.IndicatorGridRowItem;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.FilterAttributeName;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.ActionElementType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class ClientMonitorController {
	
	private ClientMonitorDataModel dataModel;
	
	private Vector<GroupedDataViewPanel> dataViewPanels;
	
	public ClientMonitorController(ClientMonitorDataModel actionModel){
		this.dataModel = actionModel;
		dataViewPanels = new Vector<GroupedDataViewPanel>();
	}
	
	public void addDataView(GroupedDataViewPanel panel){
		dataViewPanels.add(panel);
	}

	public void addFilterItem(ActionPropertyRule newFilterEntity){		
		EditorGrid<IndicatorFilterItemGridRowModel> _grid = getFilterListEditorGrid();
		SimpleComboBox<String> _filterCombo = getFilterListComboBox();
		String _key= newFilterEntity.getKey();
          
		if(!isInFilterList(_key,_grid) && !newFilterEntity.getValue().equalsIgnoreCase("")){
        
//	        if(!reset){
//	        	reset=true;
//	        	_filterCombo.clearSelections();
//	        }        
	
	        IndicatorFilterItemGridRowModel  _newRow = new IndicatorFilterItemGridRowModel(newFilterEntity.getPropertyName(),newFilterEntity.getValue(),newFilterEntity.getType().toString(),newFilterEntity.getDisplayText(),newFilterEntity.getOperationType().toString().toUpperCase()); 
	
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
	
	public void filtersUpdated(){
		dataModel.updateFilteredList();
        refreshViews();
	}
	
	boolean isInFilterList(String _key,EditorGrid<IndicatorFilterItemGridRowModel> _grid){
		 for (int i = 0; i < _grid.getStore().getCount(); i++) {
			 IndicatorFilterItemGridRowModel _item =	 _grid.getStore().getAt(i);
			 String rowKey= _item.getKey();
			 
			 if(rowKey.equalsIgnoreCase(_key)){	
				return true;
			}
		 }
		 return false;
	 }
	
	 
	 
	 public void refreshTabPanel() {
			VerticalPanel verticalPanel = getTabPanelContainer();
			verticalPanel.layout();
		    verticalPanel.repaint();
			
	}

	public void refreshViews() {
		
		//TODO: Table view should just be another data view, accepting a GroupedByPropoertyModel
//		refreshTableView();

		for (GroupedDataViewPanel panel : dataViewPanels){
			panel.refresh();
		}
		
	}
	
	public ActionPropertyRule getDefaultGroupingOption(){
		ActionPropertyRule _defaltEntity=new ActionPropertyRule();
		_defaltEntity.setEntityName(FilterAttributeName.CLASSIFICATION.toString());
		_defaltEntity.setType(ActionElementType.ACTION_TYPE);
		return _defaltEntity;
	}
	 
//	 ------------------------ Code that should be removed ---------------------------//
	
//	public void refreshTableView(){
//		 TableViewModel tvm=new TableViewModel(dataModel);
//			
//		   Grid<IndicatorGridRowItem> _grid = getTableViewEditorGrid();
//		   _grid.getStore().removeAll();
//		   _grid.getStore().add(tvm.parseToIndicatorGridRowList(true, false));
//		 
//	 }
	
	
	
	public EditorGrid<IndicatorFilterItemGridRowModel>  getFilterListEditorGrid(){
		
		EditorGrid<IndicatorFilterItemGridRowModel> editorGrid = (EditorGrid<IndicatorFilterItemGridRowModel>) ComponentManager.get().get("_filterItemGrid");
		return editorGrid;
	}
	
//	public Grid<IndicatorGridRowItem>  getTableViewEditorGrid(){
//		
//		return (Grid<IndicatorGridRowItem>) ComponentManager.get().get("_tableViewGrid");
//	}
	
	public SimpleComboBox<String> getFilterListComboBox(){
		
		return (SimpleComboBox<String>) ComponentManager.get().get("_filterGroupCombo");
	}
	
	public TabPanel getMultiModelTabPanel(){
		
		return (TabPanel) ComponentManager.get().get("_multiModelTabPanel");
	}
	
//	public TabItem getTableViewTabItem(){
//		
//		return (TabItem) ComponentManager.get().get("Table");
//	}
	public VerticalPanel getTabPanelContainer(){
		
		return (VerticalPanel) ComponentManager.get().get("_tabMainPanel");
	}

public ComboBox<OperationsComboBoxModel> getOperationsComboBox(){
	
	return (ComboBox<OperationsComboBoxModel>) ComponentManager.get().get("_operationComboBox");
	
}
	

public TextField<String> getFilterEntityValueTextField(){
	
	return (TextField<String>) ComponentManager.get().get("entityValueText");
}


public ContentPanel getGroupedGridContentPanel(){
	
	return (ContentPanel) ComponentManager.get().get("_groupedGridPanel");

}






}

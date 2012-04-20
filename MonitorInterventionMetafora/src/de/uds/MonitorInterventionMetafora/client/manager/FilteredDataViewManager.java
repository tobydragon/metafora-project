package de.uds.MonitorInterventionMetafora.client.manager;

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

import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.UpdatingDataModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.EntitiesComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.GroupedByPropertyModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.OperationsComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.TableViewModel;
import de.uds.MonitorInterventionMetafora.client.view.grids.IndicatorGridRowItem;
import de.uds.MonitorInterventionMetafora.client.view.widgets.GroupedDataViewPanel;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.FilterAttributeName;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.FilterItemType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorProperty;

public class FilteredDataViewManager {
	
	private boolean reset=false;
	private UpdatingDataModel actionModel;
	
	private Vector<GroupedDataViewPanel> dataViewPanels;
	
	public FilteredDataViewManager(UpdatingDataModel actionModel){
		this.actionModel = actionModel;
		dataViewPanels = new Vector<GroupedDataViewPanel>();
	}
	
	public void addDataView(GroupedDataViewPanel panel){
		dataViewPanels.add(panel);
	}

	public void addFilterItem(IndicatorProperty newFilterEntity){		
		EditorGrid<IndicatorFilterItemGridRowModel> _grid = getFilterListEditorGrid();
		SimpleComboBox<String> _filterCombo = getFilterListComboBox();
		String _key= newFilterEntity.getKey();
          
		if(!isInFilterList(_key,_grid) && !newFilterEntity.getValue().equalsIgnoreCase("")){
        
	        if(!reset){
	        	reset=true;
	        	_filterCombo.clearSelections();
	        }        
	
	        IndicatorFilterItemGridRowModel  _newRow = new IndicatorFilterItemGridRowModel(newFilterEntity.getEntityName(),newFilterEntity.getValue(),newFilterEntity.getType().toString(),newFilterEntity.getDisplayText(),newFilterEntity.getOperationType().toString().toUpperCase()); 
	
	        _grid.stopEditing();  
	        _grid.getStore().insert(_newRow, 0);  
	        _grid.startEditing(_grid.getStore().indexOf(_newRow), 0); 
	        _filterCombo.clearSelections();
	       
	        TabPanel tabPanel = getMultiModelTabPanel();
	        TabItem tabItem= getTableViewTabItem();	
	        tabPanel.setTabIndex(0);
	        tabPanel.repaint();
	        tabPanel.setLayoutData(new FitLayout());
	        tabPanel.setSelection(tabItem);
//		    MessageBox.info("Message","Filter is added to the list!", null);
    	
   	    }
        else {	
        	MessageBox.info("Message","Selected Filter is<ul><li> already in  the filter list</li></ul>", null);
        }
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
		refreshTableView();
		
		//TODO: Explain, why do we need a new model to be formed here? Can't we update?
		//this will clearly not work for large data sets and many users...
		GroupedByPropertyModel model=new GroupedByPropertyModel(actionModel);
		for (GroupedDataViewPanel panel : dataViewPanels){
			panel.refresh(model);
		}
		
	}
	
	public IndicatorProperty getDefaultGroupingOption(){
		IndicatorProperty _defaltEntity=new IndicatorProperty();
		_defaltEntity.setEntityName(FilterAttributeName.CLASSIFICATION.toString());
		_defaltEntity.setType(FilterItemType.ACTION_TYPE);
		return _defaltEntity;
	}
	 
//	 ------------------------ Code that should be removed ---------------------------//
	
	public void refreshTableView(){
		 TableViewModel tvm=new TableViewModel(actionModel);
			
		   Grid<IndicatorGridRowItem> _grid = getTableViewEditorGrid();
		   _grid.getStore().removeAll();
		   _grid.getStore().add(tvm.parseToIndicatorGridRowList(true, false));
		 
	 }
	
	
	
	public EditorGrid<IndicatorFilterItemGridRowModel>  getFilterListEditorGrid(){
		
		EditorGrid<IndicatorFilterItemGridRowModel> editorGrid = (EditorGrid<IndicatorFilterItemGridRowModel>) ComponentManager.get().get("_filterItemGrid");
		return editorGrid;
	}
	
	public Grid<IndicatorGridRowItem>  getTableViewEditorGrid(){
		
		return (Grid<IndicatorGridRowItem>) ComponentManager.get().get("_tableViewGrid");
	}
	
	public SimpleComboBox<String> getFilterListComboBox(){
		
		return (SimpleComboBox<String>) ComponentManager.get().get("_filterGroupCombo");
	}
	
	public TabPanel getMultiModelTabPanel(){
		
		return (TabPanel) ComponentManager.get().get("_multiModelTabPanel");
	}
	
	public TabItem getTableViewTabItem(){
		
		return (TabItem) ComponentManager.get().get("Table");
	}
	public VerticalPanel getTabPanelContainer(){
		
		return (VerticalPanel) ComponentManager.get().get("_tabMainPanel");
	}

public ComboBox<EntitiesComboBoxModel> getFilterEntitiesComboBox(){
	
	return (ComboBox<EntitiesComboBoxModel>) ComponentManager.get().get("_entityComboBox");
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

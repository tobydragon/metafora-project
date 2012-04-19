package de.uds.MonitorInterventionMetafora.client.manager;

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

import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.datamodels.EntitiesComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.OperationsComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.TableViewModel;
import de.uds.MonitorInterventionMetafora.client.view.charts.ExtendedColumnChart;
import de.uds.MonitorInterventionMetafora.client.view.charts.ExtendedPieChart;
import de.uds.MonitorInterventionMetafora.client.view.grids.IndicatorGridRowItem;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;

public class ClientInterfaceManager {
	
	private boolean reset=false;
	private ActionMaintenance actionModel;
	
	public ClientInterfaceManager(ActionMaintenance actionModel){
		this.actionModel = actionModel;
	}

	public void addFilterItem(IndicatorEntity newFilterEntity){
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
	
	 public void refreshTableView(){
		 TableViewModel tvm=new TableViewModel(actionModel);
			
		   Grid<IndicatorGridRowItem> _grid = getTableViewEditorGrid();
		   _grid.getStore().removeAll();
		   _grid.getStore().add(tvm.parseToIndicatorGridRowList(true, false));
		 
	 }
	 
//	 ------------------------
	
	
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
	
	public ExtendedColumnChart getColumnChart(){
		
		return (ExtendedColumnChart) ComponentManager.get().get("barChartVerticalPanel");
	}
	
	public VerticalPanel getColumnChartVerticalPanel(){
		
		
		return (VerticalPanel) ComponentManager.get().get("barChartFilterPanel");
	}
	
	public ComboBox<EntitiesComboBoxModel>  getColumnChartGroupingComboBox(){

		return (ComboBox<EntitiesComboBoxModel>) ComponentManager.get().get("comboColumnChartType");
	}
	
	
public TabItem getColumChartViewTabItem(){
		
		return (TabItem) ComponentManager.get().get("barChartViewTab");
	}

public ExtendedPieChart getExtendedPieChart(){
	
	return (ExtendedPieChart) ComponentManager.get().get("pieChartVerticalPanel");
}


public VerticalPanel getPieChartGroupingComboContainer(){
	
	return (VerticalPanel) ComponentManager.get().get("pieChartFilterPanel");
}

public ComboBox<EntitiesComboBoxModel> getPieChartGroupingComboBox(){
	
	ComboBox<EntitiesComboBoxModel> comboBox = (ComboBox<EntitiesComboBoxModel>) ComponentManager.get().get("comboPieChartType");
return comboBox;
}


public TabItem getPieChartViewTabItem(){
	
	return (TabItem) ComponentManager.get().get("pieChartViewTab");
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

public void refreshTabPanel() {
	VerticalPanel verticalPanel = getTabPanelContainer();
	verticalPanel.layout();
    verticalPanel.repaint();
	
}


}

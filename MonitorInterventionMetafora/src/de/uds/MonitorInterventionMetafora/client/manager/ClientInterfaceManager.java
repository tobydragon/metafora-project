package de.uds.MonitorInterventionMetafora.client.manager;

import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;

import de.uds.MonitorInterventionMetafora.client.datamodels.EntitiesComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.OperationsComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.view.charts.ExtendedColumnChart;
import de.uds.MonitorInterventionMetafora.client.view.charts.ExtendedPieChart;
import de.uds.MonitorInterventionMetafora.client.view.grids.IndicatorGridRowItem;

public class ClientInterfaceManager {

	
	
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


}

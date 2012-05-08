package de.uds.MonitorInterventionMetafora.client.monitor.datamodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.google.gwt.visualization.client.DataTable;

import de.uds.MonitorInterventionMetafora.client.monitor.dataview.table.CfActionGridRow;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.ActionElementType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRuleSelectorModel;

public class ClientMonitorDataModel {
	
	private List<CfAction> allActions;
	
	private ActionFilter actionFilter;
	
	private  List<CfAction> filteredActions;
	
	private GroupingStore<CfActionGridRow> tableViewModel;
	
	ActionPropertyRuleSelectorModel ruleSelectorModel;
	//for aggregated data views (charts so far)
	//a mapping of IndicatorPropertyTables, which each tracking the different values 
	//and occurrence counts for an AcionPropertyRule
	private Map<String, ActionPropertyValueGroupingTableModel> rule2ValueGroupingTableMap;
	
	public ClientMonitorDataModel(ActionPropertyRuleSelectorModel ruleSelectorModel){
		this.ruleSelectorModel = ruleSelectorModel;
		actionFilter = new ActionFilter();
		allActions = new Vector<CfAction>();
		tableViewModel = new GroupingStore<CfActionGridRow>();
		clearFilteredData();
	}
	
	public void clearFilteredData(){
		filteredActions = new Vector<CfAction>();
		tableViewModel.removeAll();
		rule2ValueGroupingTableMap = createIndicatorPropertyTableMap();	
	}
	
	public void updateFilteredList(){
		clearFilteredData();
		addFilteredData(allActions);
	}
	
	public void addFilteredData(List<CfAction> actionsToFilter){
		for (CfAction action : actionsToFilter){
			if (actionFilter.currentFilterGridIncludesAction(action)){
				tableViewModel.add(new CfActionGridRow(action));
				
				for (ActionPropertyValueGroupingTableModel indicatorPropertyTable : rule2ValueGroupingTableMap.values()){
					indicatorPropertyTable.addAction(action);
				}
				filteredActions.add(action);
			}
		}
	}
	
	public void addData(List<CfAction> actions){
		allActions.addAll(actions);
		addFilteredData(actions);
	}
	
	public CfAction getLastAction(){
		if(allActions.size()<=0){
			return null;
		}
		int index=allActions.size()-1;
		return allActions.get(index);
	}
	
	//Creates table for each rule, where each row will represent one value for each rule
	private Map<String, ActionPropertyValueGroupingTableModel> createIndicatorPropertyTableMap() {
		Map<String, ActionPropertyValueGroupingTableModel> inMap = new HashMap<String, ActionPropertyValueGroupingTableModel>();
		//Make a table for each possible rule that can be grouped by
		for (ActionPropertyRule actionPropertyRule : ruleSelectorModel.getAssociatedRules()){
			ActionPropertyValueGroupingTableModel actionPropertyValueGroupingTable = new ActionPropertyValueGroupingTableModel(actionPropertyRule);
			inMap.put(actionPropertyRule.getKey(), actionPropertyValueGroupingTable);
		}
		return inMap;
	}
	
	public DataTable getDataTable(ActionPropertyRule propertyToGroupBy){
		ActionPropertyValueGroupingTableModel table = rule2ValueGroupingTableMap.get(propertyToGroupBy.getKey());
		if (table != null){
			return table.getDataTable();
		}
		else {
			return null;
		}
	}

	public int getMaxValue(ActionPropertyRule propertyToGroupBy){
		ActionPropertyValueGroupingTableModel table = rule2ValueGroupingTableMap.get(propertyToGroupBy.getKey());
		if (table != null){
			return table.getMaxValue();
		}
		else {
			return 0;
		}
	}
	
	public List<CfAction> getFilteredActions(){
		return filteredActions;
	}
	
	public GroupingStore<CfActionGridRow> getTableViewModel(){
		return tableViewModel;
	}
	
}

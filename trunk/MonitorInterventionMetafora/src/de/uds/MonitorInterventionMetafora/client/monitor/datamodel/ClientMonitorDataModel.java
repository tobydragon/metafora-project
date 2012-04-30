package de.uds.MonitorInterventionMetafora.client.monitor.datamodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.extjs.gxt.ui.client.store.ListStore;
import com.google.gwt.visualization.client.DataTable;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.ActionElementType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class ClientMonitorDataModel {
	
	private List<CfAction> allActions;
	private ActionFilter actionFilter;
	
	private List<ActionPropertyRule> rulesToGroupBy;
	private ListStore<PropertyComboBoxItemModel> groupingRuleComboBoxItems;
	
	private List<ActionPropertyRule> rulesToFilterBy;
	private ListStore<PropertyComboBoxItemModel> filterRuleComboBoxItems;

	
	private  List<CfAction> filteredActions;
	//a mapping of IndicatorPropertyTables, which each tracking the different values 
	//and occurrence counts for an AcionPropertyRule
	private Map<String, ActionPropertyValueGroupingTable> rule2ValueGroupingTableMap;
	
	public ClientMonitorDataModel(){
		actionFilter = new ActionFilter();
		allActions = new Vector<CfAction>();
		
		rulesToGroupBy = createGroupingRules();
		rulesToFilterBy = createFilteringRules();
		groupingRuleComboBoxItems = createRuleComboBoxItems(rulesToGroupBy);
		filterRuleComboBoxItems = createRuleComboBoxItems(rulesToFilterBy);
		
		clearFilteredData();
	}
	
	public void clearFilteredData(){
		filteredActions = new Vector<CfAction>();
		rule2ValueGroupingTableMap = createIndicatorPropertyTableMap(rulesToGroupBy);	
	}
	
	public void updateFilteredList(){
		clearFilteredData();
		addFilteredData(allActions);
	}
	
	public void addFilteredData(List<CfAction> actionsToFilter){
		for (CfAction action : actionsToFilter){
			if (actionFilter.currentFilterGridIncludesAction(action)){
				for (ActionPropertyValueGroupingTable indicatorPropertyTable : rule2ValueGroupingTableMap.values()){
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
	private Map<String, ActionPropertyValueGroupingTable> createIndicatorPropertyTableMap(List<ActionPropertyRule> actionPropertyRulesIn) {
		Map<String, ActionPropertyValueGroupingTable> inMap = new HashMap<String, ActionPropertyValueGroupingTable>();
		for (ActionPropertyRule actionPropertyRule : actionPropertyRulesIn){
			ActionPropertyValueGroupingTable actionPropertyValueGroupingTable = new ActionPropertyValueGroupingTable(actionPropertyRule);
			inMap.put(actionPropertyRule.getKey(), actionPropertyValueGroupingTable);
		}
		return inMap;
	}

	private List<ActionPropertyRule> createGroupingRules() {
		List<ActionPropertyRule> newGroupings = new Vector<ActionPropertyRule>();
	
		//ACTION_TYPE
		newGroupings.add( new ActionPropertyRule(ActionElementType.ACTION_TYPE, "Classification", "Action Classification"));
		newGroupings.add(new ActionPropertyRule(ActionElementType.ACTION_TYPE, "type", "Action type"));

		//USER
		newGroupings.add( new ActionPropertyRule(ActionElementType.USER, "id", "User ID"));
		
		//CONTENT
		newGroupings.add(new ActionPropertyRule(ActionElementType.CONTENT, "Tool", "Tool"));
		newGroupings.add(new ActionPropertyRule(ActionElementType.CONTENT, "INDICATOR_TYPE", "Indicator Type"));
		newGroupings.add(new ActionPropertyRule(ActionElementType.CONTENT, "CHALLENGE_NAME", "Challenge Name"));
		newGroupings.add(new ActionPropertyRule(ActionElementType.CONTENT, "GROUP_ID", "Group ID"));
		
		return newGroupings;
	}
	
	private List<ActionPropertyRule> createFilteringRules(){
		//groupings are a subset of filters
		List<ActionPropertyRule> newFilters = createGroupingRules();

		newFilters.add(new ActionPropertyRule(ActionElementType.ACTION, "time", "Action Time"));
		newFilters.add(new ActionPropertyRule(ActionElementType.CONTENT, "description", "Description"));

		return newFilters;
	}
	
	private ListStore<PropertyComboBoxItemModel> createRuleComboBoxItems(List<ActionPropertyRule> rules){
		ListStore<PropertyComboBoxItemModel> propertyComboBoxItems = new ListStore<PropertyComboBoxItemModel>();
		for (ActionPropertyRule rule : rules){
			propertyComboBoxItems.add(new PropertyComboBoxItemModel(rule));
		}
		return propertyComboBoxItems;
	}
	
	public DataTable getDataTable(ActionPropertyRule propertyToGroupBy){
		ActionPropertyValueGroupingTable table = rule2ValueGroupingTableMap.get(propertyToGroupBy.getKey());
		if (table != null){
			return table.getDataTable();
		}
		else {
			return null;
		}
	}

	public int getMaxValue(ActionPropertyRule propertyToGroupBy){
		ActionPropertyValueGroupingTable table = rule2ValueGroupingTableMap.get(propertyToGroupBy.getKey());
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
	
	
	
	public ListStore<PropertyComboBoxItemModel> getGroupingRulesComboBoxModel(){
		return groupingRuleComboBoxItems;
	}
	
	public ListStore<PropertyComboBoxItemModel> getFilterRulesComboBoxModel(){
		return filterRuleComboBoxItems;
	}
	
}

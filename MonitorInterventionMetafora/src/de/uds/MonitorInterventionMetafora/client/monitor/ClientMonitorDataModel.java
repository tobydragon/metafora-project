package de.uds.MonitorInterventionMetafora.client.monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.eclipse.jdt.internal.compiler.lookup.UpdatedMethodBinding;

import com.extjs.gxt.ui.client.store.ListStore;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;

import de.uds.MonitorInterventionMetafora.client.datamodels.ActionPropertyValueGroupingTable;
import de.uds.MonitorInterventionMetafora.client.datamodels.PropertyComboBoxItemModel;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.ActionElementType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.ActionPropertyRule;

public class ClientMonitorDataModel {
	
	//all actions received from client
	private  List<CfAction> allCfActions;
	
	//a mapping of strings to ActionPropertyRules for grouping/filtering lists
	private Map<String, ActionPropertyRule> propertyDisplayName2ActionPropertyRuleMap;
	
	//a mapping of IndicatorPropertyTables, which each track the different values 
	//and occurrence counts for an AcionPropertyRule
	private Map<String, ActionPropertyValueGroupingTable> rule2ValueGroupingTableMap;
	
	private ListStore<PropertyComboBoxItemModel> propertyComboBoxItems;
	
	public ClientMonitorDataModel(){
		allCfActions=new ArrayList<CfAction>();
		propertyDisplayName2ActionPropertyRuleMap = createActionProperties();
		propertyComboBoxItems = new ListStore<PropertyComboBoxItemModel>();
		rule2ValueGroupingTableMap = createIndicatorPropertyTableMap(propertyDisplayName2ActionPropertyRuleMap.values());
		
		
	}
	
	private Map<String, ActionPropertyValueGroupingTable> createIndicatorPropertyTableMap(Collection<ActionPropertyRule> actionPropertyRulesIn) {
		Map<String, ActionPropertyValueGroupingTable> inMap = new HashMap<String, ActionPropertyValueGroupingTable>();
		for (ActionPropertyRule actionPropertyRule : actionPropertyRulesIn){
			ActionPropertyValueGroupingTable actionPropertyValueGroupingTable = new ActionPropertyValueGroupingTable(actionPropertyRule);
			inMap.put(actionPropertyRule.getKey(), actionPropertyValueGroupingTable);
			propertyComboBoxItems.add(new PropertyComboBoxItemModel(actionPropertyRule));
		}
		return inMap;
	}

	private Map<String, ActionPropertyRule> createActionProperties() {
		Map<String, ActionPropertyRule> newGroupings = new HashMap<String, ActionPropertyRule>();
		
		newGroupings.put("TOOL", new ActionPropertyRule(ActionElementType.CONTENT, "Tool"));
		newGroupings.put("Classification", new ActionPropertyRule(ActionElementType.ACTION_TYPE, "Classification"));
		newGroupings.put("Username", new ActionPropertyRule(ActionElementType.USER, "id"));
		return newGroupings;
	}

	public void addData(List<CfAction> actions){
		for (CfAction action : actions){
			for (ActionPropertyValueGroupingTable indicatorPropertyTable : rule2ValueGroupingTableMap.values()){
				indicatorPropertyTable.addAction(action);
			}
		}
		allCfActions.addAll(actions);
	}
	
	public CfAction getLastAction(){
		if(allCfActions.size()<=0){
			return null;
		}
		int index=allCfActions.size()-1;
		return allCfActions.get(index);
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
	
	public List<CfAction> getAllActions(){
		return allCfActions;
	}
	
	public Collection<String> getPropertyDisplayNames(){
		return propertyDisplayName2ActionPropertyRuleMap.keySet();
	}
	
	public ActionPropertyRule getRuleFromDisplayName(String displayName){
		return propertyDisplayName2ActionPropertyRuleMap.get(displayName);
	}
	
	public ListStore<PropertyComboBoxItemModel> getPropertiesComboBoxModel(){
		return propertyComboBoxItems;
	}
	
}

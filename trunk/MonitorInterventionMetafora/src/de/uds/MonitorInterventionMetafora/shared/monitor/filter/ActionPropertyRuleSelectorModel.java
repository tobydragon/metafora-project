package de.uds.MonitorInterventionMetafora.shared.monitor.filter;

import java.util.List;
import java.util.Vector;

import com.extjs.gxt.ui.client.store.ListStore;

import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.PropertyComboBoxItemModel;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.ActionElementType;
import de.uds.MonitorInterventionMetafora.shared.monitor.MonitorConstants;

public class ActionPropertyRuleSelectorModel {
	
	private ListStore<PropertyComboBoxItemModel> ruleComboBoxItems;
		
	public ActionPropertyRuleSelectorModel(ActionPropertyRuleSelectorModelType selectorType, List<ActionPropertyRule> propertyRulesIn){
		this.ruleComboBoxItems = buildModels(propertyRulesIn);
	}
	
	public ListStore<PropertyComboBoxItemModel> getRuleComboBoxItems(){
		return ruleComboBoxItems;
	}
	
	private ListStore<PropertyComboBoxItemModel> buildModels(List<ActionPropertyRule> propertyRulesIn){
		ListStore<PropertyComboBoxItemModel> ruleComboBoxItems = new ListStore<PropertyComboBoxItemModel>();
		for (ActionPropertyRule rule: propertyRulesIn){
			ruleComboBoxItems.add(new PropertyComboBoxItemModel(rule));
		}
		return ruleComboBoxItems;
	}
	

	public List<ActionPropertyRule> getAssociatedRules() {
		List<ActionPropertyRule> rules = new Vector<ActionPropertyRule>();
		for (PropertyComboBoxItemModel item : ruleComboBoxItems.getModels()){
			rules.add(item.getActionPropertyRule());
		}
		return rules;
	}

	
//	------------------------------
	
	public static ActionPropertyRuleSelectorModel getActionPropertyRuleSelectorModel(ActionPropertyRuleSelectorModelType selectorType){
		List<ActionPropertyRule> propertyRulesIn;
		if (selectorType == ActionPropertyRuleSelectorModelType.GROUPING){
			propertyRulesIn = createGroupingRules();
		}
		else if (selectorType == ActionPropertyRuleSelectorModelType.FILTER){
			propertyRulesIn = createFilteringRules();
		}
		else {
			System.err.println("ERROR:\t\t [ActionPropertyRuleSelectorModel constructor] Creating blank list, Unrecognized selector type:" +  selectorType);
			propertyRulesIn = new Vector<ActionPropertyRule>();
		}
		return new ActionPropertyRuleSelectorModel(selectorType, propertyRulesIn); 
	}
	
	public static List<ActionPropertyRule> createGroupingRules() {
		List<ActionPropertyRule> newGroupings = new Vector<ActionPropertyRule>();
	
		//ACTION_TYPE
		newGroupings.add(new ActionPropertyRule(ActionElementType.ACTION_TYPE, "Classification", MonitorConstants.ACTION_CLASSIFICATION_LABEL));
		newGroupings.add(new ActionPropertyRule(ActionElementType.ACTION_TYPE, "type", MonitorConstants.ACTION_TYPE_LABEL));

		//USER
		newGroupings.add(new ActionPropertyRule(ActionElementType.USER, "id", MonitorConstants.USER_ID_LABEL));
		
		//CONTENT
		newGroupings.add(new ActionPropertyRule(ActionElementType.CONTENT, "Tool", MonitorConstants.TOOL_LABEL));
		newGroupings.add(new ActionPropertyRule(ActionElementType.CONTENT, "INDICATOR_TYPE", MonitorConstants.INDICATOR_TYPE_LABEL));
		newGroupings.add(new ActionPropertyRule(ActionElementType.CONTENT, "CHALLENGE_NAME", MonitorConstants.CHALLENGE_NAME_LABEL));
		newGroupings.add(new ActionPropertyRule(ActionElementType.CONTENT, "GROUP_ID", MonitorConstants.GROUP_ID));
		
		return newGroupings;
	}
	
	public static  List<ActionPropertyRule> createFilteringRules(){
		//groupings are a subset of filters
		List<ActionPropertyRule> newFilters =  createGroupingRules();

		newFilters.add(new ActionPropertyRule(ActionElementType.ACTION, "time", MonitorConstants.ACTION_TIME_LABEL));
		newFilters.add(new ActionPropertyRule(ActionElementType.CONTENT, "description", MonitorConstants.DESCRIPTION_LABEL));

		return newFilters;
	}
	
	public static ActionPropertyRule getDefaultGrouping(){
		return new ActionPropertyRule(ActionElementType.CONTENT, "Tool", MonitorConstants.TOOL_LABEL);
	}

}

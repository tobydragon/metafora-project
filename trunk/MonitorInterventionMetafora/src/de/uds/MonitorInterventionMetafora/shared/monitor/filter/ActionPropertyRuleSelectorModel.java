package de.uds.MonitorInterventionMetafora.shared.monitor.filter;

import java.util.List;
import java.util.Vector;

import com.extjs.gxt.ui.client.store.ListStore;

import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.PropertyComboBoxItemModel;

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
//		TODO: figure out how this was used and fix it
//		newGroupings.add(new ActionPropertyRule(PropertyLocation.ACTION_TYPE, "UnGroup", MonitorConstants.ACTION_REMOVE_GROUPING_LABEL));

		newGroupings.addAll(createCommonRules());
		return newGroupings;
	}
	
	public static List <ActionPropertyRule> createCommonRules(){
		List<ActionPropertyRule> newGroupings = new Vector<ActionPropertyRule>();
		newGroupings.add(StandardRuleBuilder.buildStandardRule(StandardRuleType.ACTION_CLASSIFICATION));
		newGroupings.add(StandardRuleBuilder.buildStandardRule(StandardRuleType.ACTION_TYPE));
		newGroupings.add(StandardRuleBuilder.buildStandardRule(StandardRuleType.USER_ID));
		newGroupings.add(StandardRuleBuilder.buildStandardRule(StandardRuleType.SENDING_TOOL));
		//newGroupings.add(StandardRuleBuilder.buildStandardRule(StandardRuleType.INDICATOR_TYPE));
		newGroupings.add(StandardRuleBuilder.buildStandardRule(StandardRuleType.CHALLENGE_NAME));
		newGroupings.add(StandardRuleBuilder.buildStandardRule(StandardRuleType.GROUP_ID));
		newGroupings.add(StandardRuleBuilder.buildStandardRule(StandardRuleType.TAGS));
		newGroupings.add(StandardRuleBuilder.buildStandardRule(StandardRuleType.CORRECT));
		newGroupings.add(StandardRuleBuilder.buildStandardRule(StandardRuleType.OBJECT_ID));
		
		
		
		return newGroupings;
	}
	
	public static  List<ActionPropertyRule> createFilteringRules(){
		List<ActionPropertyRule> newGroupings = new Vector<ActionPropertyRule>();

		newGroupings.add(StandardRuleBuilder.buildStandardRule(StandardRuleType.TIME));
		newGroupings.add(StandardRuleBuilder.buildStandardRule(StandardRuleType.DESCRIPTION));
		newGroupings.addAll(createCommonRules());
		return newGroupings;
	}

}

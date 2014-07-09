package de.uds.MonitorInterventionMetafora.shared.monitor.filter;

import java.util.HashMap;
import java.util.Map;

import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.PropertyLocation;

public class StandardRuleBuilder {
	
	private static Map<StandardRuleType, ActionPropertyRule> ALL_RULES;
	
	
	static {
		ALL_RULES = new HashMap<StandardRuleType, ActionPropertyRule>();
		ALL_RULES.put(StandardRuleType.TIME, new ActionPropertyRule(PropertyLocation.ACTION, "time", StandardRuleType.TIME.toString()));
		ALL_RULES.put(StandardRuleType.ACTION_CLASSIFICATION, new ActionPropertyRule(PropertyLocation.ACTION_TYPE, "classification", StandardRuleType.ACTION_CLASSIFICATION.toString()));
		ALL_RULES.put(StandardRuleType.ACTION_TYPE, new ActionPropertyRule(PropertyLocation.ACTION_TYPE, "type", StandardRuleType.ACTION_TYPE.toString()));
		ALL_RULES.put(StandardRuleType.USER_ID, new ActionPropertyRule(PropertyLocation.USER, "id", StandardRuleType.USER_ID.toString()));
		ALL_RULES.put(StandardRuleType.DESCRIPTION, new ActionPropertyRule(PropertyLocation.CONTENT, "description", StandardRuleType.DESCRIPTION.toString()));
		ALL_RULES.put(StandardRuleType.SENDING_TOOL, new ActionPropertyRule(PropertyLocation.CONTENT, "SENDING_TOOL", StandardRuleType.SENDING_TOOL.toString()));
		ALL_RULES.put(StandardRuleType.INDICATOR_TYPE, new ActionPropertyRule(PropertyLocation.CONTENT, "INDICATOR_TYPE", StandardRuleType.INDICATOR_TYPE.toString()));
		ALL_RULES.put(StandardRuleType.CHALLENGE_NAME, new ActionPropertyRule(PropertyLocation.CONTENT, "CHALLENGE_NAME", StandardRuleType.CHALLENGE_NAME.toString()));
		ALL_RULES.put(StandardRuleType.GROUP_ID, new ActionPropertyRule(PropertyLocation.CONTENT, "GROUP_ID", StandardRuleType.GROUP_ID.toString()));
		ALL_RULES.put(StandardRuleType.TAGS, new ActionPropertyRule(PropertyLocation.OBJECT, "TAGS", StandardRuleType.TAGS.toString()));
		ALL_RULES.put(StandardRuleType.WORD_COUNT, new ActionPropertyRule(PropertyLocation.OBJECT, "WORD_COUNT", StandardRuleType.WORD_COUNT.toString()));
		ALL_RULES.put(StandardRuleType.CORRECT, new ActionPropertyRule(PropertyLocation.CONTENT, "Correct", StandardRuleType.CORRECT.toString()));
		
		
	}
				
		
	public static ActionPropertyRule buildStandardRule(StandardRuleType type){
		return ALL_RULES.get(type).clone();
	}
	
	public static ActionPropertyRule getDefaultGroupingRule(){
		return ALL_RULES.get(StandardRuleType.USER_ID).clone();
	}
}

package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.PropertyLocation;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.RuleRelation;

public class BehaviorFilters {
	
	public static ActionFilter createDiscussionFilter(){
		List<ActionPropertyRule> sharedRules = new Vector<ActionPropertyRule>();
		sharedRules.add(new ActionPropertyRule("SENDING_TOOL", "LASAD", PropertyLocation.CONTENT, OperationType.CONTAINS));
		sharedRules.add(new ActionPropertyRule("ACTIVITY_TYPE", "CHAT_MESSAGE", PropertyLocation.CONTENT, OperationType.EQUALS));
		ActionFilter discussionFilter = new ActionFilter("Discussion", true, null, null, sharedRules, RuleRelation.OR);
		return discussionFilter;
	}
	
	public static ActionFilter createAttitudeFilter(){
		List<ActionPropertyRule> sharedRules = new Vector<ActionPropertyRule>();
		sharedRules.add(new ActionPropertyRule("type", "attitudes", PropertyLocation.OBJECT, OperationType.CONTAINS));
		ActionFilter discussionFilter = new ActionFilter("Discussion", true, sharedRules);
		return discussionFilter;
	}
	
	public static ActionFilter createRoleFilter(){
		List<ActionPropertyRule> sharedRules = new Vector<ActionPropertyRule>();
		sharedRules.add(new ActionPropertyRule("type", "roles", PropertyLocation.OBJECT, OperationType.CONTAINS));
		ActionFilter discussionFilter = new ActionFilter("Discussion", true, sharedRules);
		return discussionFilter;
	}
	
	public static ActionFilter createPlanningFilter(){
		List<ActionPropertyRule> sharedRules = new Vector<ActionPropertyRule>();
		sharedRules.add(new ActionPropertyRule("SENDING_TOOL", "PLANNING", PropertyLocation.CONTENT, OperationType.CONTAINS));
		ActionFilter discussionFilter = new ActionFilter("Planning", true, sharedRules);
		return discussionFilter;
	}
	
	public static ActionFilter createNewIdeaFilter(){
		List<ActionPropertyRule> newIdeaRules = new Vector<ActionPropertyRule>();
		newIdeaRules.add(new ActionPropertyRule("ACTIVITY_TYPE", "MODIFY_STATE_STARTED", PropertyLocation.CONTENT, OperationType.EQUALS));
		ActionFilter newIdeaFilter = new ActionFilter("NEW_IDEA", true, newIdeaRules);
		return newIdeaFilter;
	}
	
	public static ActionFilter createBehaviorFilter(BehaviorType behaviorType){
		List<ActionPropertyRule> newIdeaRules = new Vector<ActionPropertyRule>();
		newIdeaRules.add(new ActionPropertyRule(MetaforaStrings.PROPERTY_NAME_BEHAVIOR_TYPE, behaviorType.toString(), PropertyLocation.CONTENT, OperationType.EQUALS));
		ActionFilter newIdeaFilter = new ActionFilter("STRUGGLE", true, newIdeaRules);
		return newIdeaFilter;
	}
	
	public static ActionFilter createActionsAfterFilter(long time){
		List <ActionPropertyRule> afterRules = new Vector<ActionPropertyRule>();
		afterRules.add (new ActionPropertyRule("time", Long.toString(time), PropertyLocation.ACTION, OperationType.IS_AFTER));
		ActionFilter afterFilter = new ActionFilter("Time after", true, afterRules);
		return afterFilter;
	}
	
	public static ActionFilter createSharedSolutionFilter(){
		List<ActionPropertyRule> rules = new Vector<ActionPropertyRule>();
		rules.add(new ActionPropertyRule("type", "My Microworld,Help Request", PropertyLocation.OBJECT, OperationType.IS_ONE_OF));
		ActionFilter newIdeaFilter = new ActionFilter("ComparedSolution", true, rules);
		return newIdeaFilter;
	}
}

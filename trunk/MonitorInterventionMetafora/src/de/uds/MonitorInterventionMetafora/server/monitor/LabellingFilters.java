package de.uds.MonitorInterventionMetafora.server.monitor;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.PropertyLocation;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class LabellingFilters {
	
	public static ActionFilter createStruggleLabelFilter(){
		List<ActionPropertyRule> rules = new Vector<ActionPropertyRule>();
		rules.add(new ActionPropertyRule("FEEDBACK_STRATEGY_ID", "900", PropertyLocation.CONTENT, OperationType.EQUALS));
		rules.add(new ActionPropertyRule("SENDING_TOOL", "EXPRESSER", PropertyLocation.CONTENT, OperationType.EQUALS));
		ActionFilter struggleFilter = new ActionFilter("Struggle", true, rules);
		return struggleFilter;
		
	}
	
	public static ActionFilter createPerceivedSolutionLabelFilter(){
		List<ActionPropertyRule> rules = new Vector<ActionPropertyRule>();
		rules.add(new ActionPropertyRule("value", "true", PropertyLocation.CONTENT, OperationType.EQUALS));
		rules.add(new ActionPropertyRule("ANALYSIS_TYPE", "ApparentSolutionVerifier", PropertyLocation.CONTENT, OperationType.EQUALS));
		ActionFilter perceivedSolutionFilter = new ActionFilter("PerceivedSolution", true, rules);
		return perceivedSolutionFilter;
		
	}
	
	public static ActionFilter createViewOthersObjectsLabelFilter(){
		List<ActionPropertyRule> rules = new Vector<ActionPropertyRule>();
		rules.add(new ActionPropertyRule("description", "My Microworld,Help Request", PropertyLocation.CONTENT, OperationType.CONTAINS_ONE_OF));
		rules.add(new ActionPropertyRule("description", "visited", PropertyLocation.CONTENT, OperationType.CONTAINS));
		ActionFilter discussionFilter = new ActionFilter("ObjectShare", true, rules);
		return discussionFilter;
		
	}
	
	public static ActionFilter createShareObjectsLabelFilter(){
		List<ActionPropertyRule> rules = new Vector<ActionPropertyRule>();
		rules.add(new ActionPropertyRule("description", "My Microworld,Help Request", PropertyLocation.CONTENT, OperationType.CONTAINS_ONE_OF));
		rules.add(new ActionPropertyRule("classification", "CREATE", PropertyLocation.ACTION_TYPE, OperationType.EQUALS));
		ActionFilter discussionFilter = new ActionFilter("ObjectShare", true, rules);
		return discussionFilter;
		
	}
	
	public static ActionFilter createDivergenceObjectsToLabelFilter(){
		List<ActionPropertyRule> rules = new Vector<ActionPropertyRule>();
		rules.add(new ActionPropertyRule("SENDING_TOOL", "PLATO", PropertyLocation.CONTENT, OperationType.CONTAINS));
		rules.add(new ActionPropertyRule("type", "SPLIT", PropertyLocation.OBJECT, OperationType.EQUALS));
		ActionFilter filter = new ActionFilter("Divergence", true, rules);
		return filter;
		
	}
	
	public static ActionFilter createConvergenceObjectsToLabelFilter(){
		List<ActionPropertyRule> rules = new Vector<ActionPropertyRule>();
		rules.add(new ActionPropertyRule("SENDING_TOOL", "PLATO", PropertyLocation.CONTENT, OperationType.CONTAINS));
		rules.add(new ActionPropertyRule("type", "JOIN", PropertyLocation.OBJECT, OperationType.EQUALS));
		ActionFilter filter = new ActionFilter("Convergence", true, rules);
		return filter;
		
	}

}

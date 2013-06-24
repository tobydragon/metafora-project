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
		rules.add(new ActionPropertyRule("FEEDBACK_STRATEGY_ID", "900", PropertyLocation.CONTENT, OperationType.CONTAINS));
		rules.add(new ActionPropertyRule("SENDING_TOOL", "EXPRESSER", PropertyLocation.CONTENT, OperationType.CONTAINS));
		ActionFilter discussionFilter = new ActionFilter("Discussion", true, rules);
		return discussionFilter;
		
	}

}

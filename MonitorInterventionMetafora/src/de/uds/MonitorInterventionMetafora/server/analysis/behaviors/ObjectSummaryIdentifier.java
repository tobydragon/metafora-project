package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;

public class ObjectSummaryIdentifier implements BehaviorIdentifier{

	@Override
	public List<BehaviorInstance> identifyBehaviors(List<CfAction> actionsToConsider, List<String> involvedUsers,List<CfProperty> groupProperties) {
		List<BehaviorInstance> identifiedBehaviors = new Vector<BehaviorInstance>();
		
		//TODO @Caitlin: this is where we get all the actions (actions to consider) and you return a list of BehaviorInstances, one for each object (problem)
		
		
		return identifiedBehaviors;
	}

}

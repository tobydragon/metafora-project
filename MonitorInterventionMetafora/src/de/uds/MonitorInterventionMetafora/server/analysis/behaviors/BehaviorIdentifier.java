package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;


import java.util.List;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;



public interface BehaviorIdentifier  {

	List<BehaviorInstance> identifyBehaviors(List<CfAction> actionsToConsider, List<String> involvedUsers);	
}

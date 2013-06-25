package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;




import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.PropertyLocation;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.RuleRelation;

public class PerceivedSolutionSharingIdentifier  implements  BehaviorIdentifier {	
	Logger log = Logger.getLogger(this.getClass());
	
	private static final long serialVersionUID = -8109458748846339834L;

	ActionFilter perceivedSolutionFilter;
	ActionFilter sharedFilter;
	
	public PerceivedSolutionSharingIdentifier(){
		perceivedSolutionFilter = BehaviorFilters.createBehaviorFilter(BehaviorType.PERCEIVED_SOLUTION);
		sharedFilter = BehaviorFilters.createSharedSolutionFilter();
	}
	
	@Override
	public List<BehaviorInstance> identifyBehaviors ( List<CfAction> cfActions, List<String> involvedUsers, List<CfProperty> groupProperties) {
		List<BehaviorInstance> identifiedBehaviors = new Vector<BehaviorInstance>();
		
		List<CfAction> perceivedSolutions = perceivedSolutionFilter.getFilteredList(cfActions);
		List<CfAction> sharedObjects = sharedFilter.getFilteredList(cfActions);
		
		//if there has been a new idea in the group
		if (!perceivedSolutions.isEmpty()){
			log.debug("[shouldFireNotification] Perceived Solutions found");
			CfAction firstSolution = perceivedSolutions.get(0);
			
			ActionFilter afterFilter = BehaviorFilters.createActionsAfterFilter(firstSolution.getTime());
			
			//and there has been no sharing  afterwards
			//TODO: should check the share is from the right user
			if (afterFilter.getFilteredList(sharedObjects).size() <= 0 ){
				log.debug("[shouldFireNotification] perceived solution found, but no sharing, creating behaviorInstance");
				identifiedBehaviors.add(new BehaviorInstance(BehaviorType.PERCEIVED_SOLUTION_NOT_SHARED, involvedUsers, groupProperties));
			}
			else {
				log.debug("[shouldFireNotification] percievedSolution possibly shared, checking for other users viewing shared items");
				List<CfAction> viewingOthersObjectsActions = BehaviorFilters.createBehaviorFilter(BehaviorType.VIEWING_OTHERS_OBJECTS).getFilteredList(cfActions);
				if ( afterFilter.getFilteredList(viewingOthersObjectsActions).size() > 0 ){
					log.debug("[shouldFireNotification] perceived solution found, potentially shared, and potentially viewed by another");
					identifiedBehaviors.add(new BehaviorInstance(BehaviorType.PERCEIVED_SOLUTION_SHARED_AND_VIEWED, involvedUsers, groupProperties));
				}
				else {
					log.debug("[shouldFireNotification] perceived solution found, shared, but not viewed by others");
					identifiedBehaviors.add(new BehaviorInstance(BehaviorType.SHARES_NOT_VIEWED, involvedUsers, groupProperties));
				}
			}
		}
		return identifiedBehaviors;
	}

}

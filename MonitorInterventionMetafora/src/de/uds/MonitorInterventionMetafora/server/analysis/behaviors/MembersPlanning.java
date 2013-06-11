package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;

public class MembersPlanning implements BehaviorIdentifier{

	private ActionFilter planningFilter;
	
	public MembersPlanning(){
		planningFilter = BehaviorFilters.createPlanningFilter();
		
	}
	
	
	@Override
	public List<BehaviorInstance> identifyBehaviors(List<CfAction> actionsToConsider, List<String> involvedUsers, List<CfProperty> groupProperties) {
		List<BehaviorInstance> identifiedBehaviors = new Vector<BehaviorInstance>();

		List<CfAction> planningActions = planningFilter.getFilteredList(actionsToConsider);
		for (String username : involvedUsers){
			boolean userContributed = false;
			for (CfAction action : planningActions){
				for (CfUser user : action.getCfUsers()){
					if (username.equalsIgnoreCase(user.getid())){
						userContributed = true;
					}
				}
			}
			if ( !userContributed){
				identifiedBehaviors.add(new BehaviorInstance(BehaviorType.MEMBER_NOT_PLANNING, Arrays.asList(username), groupProperties));
			}
		}
		if (identifiedBehaviors.isEmpty()){
			identifiedBehaviors.add(new BehaviorInstance(BehaviorType.ALL_MEMBERS_PLANNING, involvedUsers, groupProperties));
		}
		return identifiedBehaviors;
	}
	
	

}

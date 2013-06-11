package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;

public class MemberNotDiscussing implements BehaviorIdentifier{

	private ActionFilter discussionFilter;
	
	public MemberNotDiscussing(){
		discussionFilter = BehaviorFilters.createDiscussionFilter();
		
	}
	
	
	@Override
	public List<BehaviorInstance> identifyBehaviors(List<CfAction> actionsToConsider, List<String> involvedUsers, List<CfProperty> groupProperties) {
		List<BehaviorInstance> identifiedBehaviors = new Vector<BehaviorInstance>();

		List<CfAction> discussionActions = discussionFilter.getFilteredList(actionsToConsider);
		for (String username : involvedUsers){
			boolean userContributed = false;
			for (CfAction action : discussionActions){
				for (CfUser user : action.getCfUsers()){
					if (username.equalsIgnoreCase(user.getid())){
						userContributed = true;
					}
				}
			}
			if ( !userContributed){
				identifiedBehaviors.add(new BehaviorInstance(BehaviorType.MEMBER_NOT_DISCUSSING, Arrays.asList(username), groupProperties));
			}
		}
		return identifiedBehaviors;
	}
	
	

}

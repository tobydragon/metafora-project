package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;

public class UsingAttitudesAndRoles implements BehaviorIdentifier{
	private ActionFilter attitideFilter;
	private ActionFilter roleFilter;

	
	public UsingAttitudesAndRoles(){
		attitideFilter = BehaviorFilters.createAttitudeFilter();
		roleFilter = BehaviorFilters.createRoleFilter();

	}
	
	
	@Override
	public List<BehaviorInstance> identifyBehaviors(List<CfAction> actionsToConsider, List<String> involvedUsers, List<CfProperty> groupProperties) {
		List<BehaviorInstance> identifiedBehaviors = new Vector<BehaviorInstance>();
		boolean usingBoth = true;

		List<CfAction> attitudeActions = attitideFilter.getFilteredList(actionsToConsider);
		if (attitudeActions.isEmpty()){
			usingBoth = false;
		}
		List<CfAction> roleActions = roleFilter.getFilteredList(actionsToConsider);
		if (roleActions.isEmpty()){
			usingBoth = false;
		}
		
		if (usingBoth){
			identifiedBehaviors.add(new BehaviorInstance(BehaviorType.USING_ATTITUDES_AND_ROLES, involvedUsers, groupProperties));
		}
		else {
			identifiedBehaviors.add(new BehaviorInstance(BehaviorType.NOT_USING_ATTITUDES_OR_ROLES, involvedUsers, groupProperties));
		}
		
		return identifiedBehaviors;
	}

}

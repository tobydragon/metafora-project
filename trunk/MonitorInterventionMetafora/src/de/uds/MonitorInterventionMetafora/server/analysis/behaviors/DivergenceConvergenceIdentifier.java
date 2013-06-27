package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;

public class DivergenceConvergenceIdentifier implements BehaviorIdentifier{

	private ActionFilter divergenceFilter;
	private ActionFilter convergenceFilter;

	
	public DivergenceConvergenceIdentifier(){
		divergenceFilter = BehaviorFilters.createBehaviorFilter(BehaviorType.DIVERGENCE);
		convergenceFilter = BehaviorFilters.createBehaviorFilter(BehaviorType.CONVERGENCE);
		
	}
	
	@Override
	public List<BehaviorInstance> identifyBehaviors(List<CfAction> actionsToConsider, List<String> involvedUsers, List<CfProperty> groupProperties) {
		List<BehaviorInstance> identifiedBehaviors = new Vector<BehaviorInstance>();

		List<CfAction> divergenceActions = divergenceFilter.getFilteredList(actionsToConsider);
		List<CfAction> convergenceActions = convergenceFilter.getFilteredList(actionsToConsider);

		if ( divergenceActions.size() > 0 && convergenceActions.size() <= 0){
			identifiedBehaviors.add(new BehaviorInstance(BehaviorType.DIVERGENCE_WITHOUT_CONVERGENCE, involvedUsers, groupProperties));
		}

		return identifiedBehaviors;
	}
	
	

}

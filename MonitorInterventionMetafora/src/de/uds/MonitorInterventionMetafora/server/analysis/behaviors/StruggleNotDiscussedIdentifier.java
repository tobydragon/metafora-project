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

public class StruggleNotDiscussedIdentifier  implements  BehaviorIdentifier {	
	Logger log = Logger.getLogger(this.getClass());
	
	private static final long serialVersionUID = -8109458748846339834L;

	public long delayTime = 120000;
	
	ActionFilter struggleFilter;
	ActionFilter discussionFilter;
	
	public StruggleNotDiscussedIdentifier(){
		struggleFilter = BehaviorFilters.createBehaviorFilter(BehaviorType.STRUGGLE);
		discussionFilter = BehaviorFilters.createDiscussionFilter();
	}
	
	@Override
	public List<BehaviorInstance> identifyBehaviors ( List<CfAction> cfActions, List<String> involvedUsers, List<CfProperty> groupProperties) {
		List<BehaviorInstance> identifiedBehaviors = new Vector<BehaviorInstance>();
		
		List<CfAction> struggle = struggleFilter.getFilteredList(cfActions);
		List<CfAction> discussion = discussionFilter.getFilteredList(cfActions);
		
		//if there has been a new idea in the group
		if (!struggle.isEmpty()){
			log.debug("[shouldFireNotification] struggle found");
			CfAction lastStruggle = struggle.get(struggle.size()-1);
			
			ActionFilter afterFilter = BehaviorFilters.createActionsAfterFilter(lastStruggle.getTime());
			
			//and there has been no discussion afterwards
			if ( ! (afterFilter.getFilteredList(discussion).size() > 0) ){
				log.debug("[shouldFireNotification] Struggle found, but no discussion, creating BehaviorInstance");
				identifiedBehaviors.add(new BehaviorInstance(BehaviorType.STRUGGLE_NOT_DISCUSSED, involvedUsers, groupProperties));
			}
			else {
				log.debug("[shouldFireNotification] Struggle and discussion found");
			}
		}
		return identifiedBehaviors;
	}
}

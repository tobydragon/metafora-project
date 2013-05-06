package de.uds.MonitorInterventionMetafora.server.analysis.notification;




import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.NotificationType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.PropertyLocation;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.RuleRelation;

public class NewIdeaNotDiscussedNotification  extends  Notification {	
	Logger log = Logger.getLogger(this.getClass());
	
	private static final long serialVersionUID = -8109458748846339834L;

	ActionFilter newIdeaFilter;
	ActionFilter discussionFilter;
	
	public NewIdeaNotDiscussedNotification(){
		this (null);
		
	}
	
	public NewIdeaNotDiscussedNotification(ActionFilter filter){
//		this.filter = filter;
		type = NotificationType.NEW_IDEA_NOT_DISCUSSED;
		buildFilters();
	}

	@Override
	public boolean shouldFireNotification(final List<CfAction> cfActions) {
		List<CfAction> newIdeas = newIdeaFilter.getFilteredList(cfActions);
		List<CfAction> discussion = discussionFilter.getFilteredList(cfActions);
		
		//if there has been a new idea in the group
		if (!newIdeas.isEmpty()){
			log.debug("[shouldFireNotification] new ideas found");
			CfAction lastIdea = newIdeas.get(newIdeas.size()-1);
			
			List <ActionPropertyRule> afterRules = new Vector<ActionPropertyRule>();
			afterRules.add (new ActionPropertyRule("time", Long.toString(lastIdea.getTime()), PropertyLocation.ACTION, OperationType.IS_AFTER));
			ActionFilter afterFilter = new ActionFilter("Time after", false, afterRules);
			
			//and there has been no discussion afterwards
			if ( ! (afterFilter.getFilteredList(discussion).size() > 0) ){
				log.debug("[shouldFireNotification] new idea found, but no discussion, firing");
				return true;
			}
			log.debug("[shouldFireNotification] new idea and discussion found");
		}
		return false;
	}
	
	public void buildFilters(){
		List<ActionPropertyRule> newIdeaRules = new Vector<ActionPropertyRule>();
		newIdeaRules.add(new ActionPropertyRule("ACTIVITY_TYPE", "MODIFY_STATE_STARTED", PropertyLocation.CONTENT, OperationType.EQUALS));
		newIdeaFilter = new ActionFilter("NEW_IDEA", true, newIdeaRules);
		
		List<ActionPropertyRule> sharedRules = new Vector<ActionPropertyRule>();
		sharedRules.add(new ActionPropertyRule("SENDING_TOOL", "LASAD", PropertyLocation.CONTENT, OperationType.CONTAINS));
		sharedRules.add(new ActionPropertyRule("ACTIVITY_TYPE", "CHAT_MESSAGE", PropertyLocation.CONTENT, OperationType.EQUALS));
		discussionFilter = new ActionFilter("Discussion", true, null, null, sharedRules, RuleRelation.OR);
	}
}

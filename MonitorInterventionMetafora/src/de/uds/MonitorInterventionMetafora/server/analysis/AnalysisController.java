package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.List;
import java.util.Vector;

import messages.MessagesController;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorIdentifier;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorInstance;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.NewIdeaNotDiscussedIdentifier;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorController;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.PropertyLocation;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;

public class AnalysisController {
	Logger log = Logger.getLogger(this.getClass());
	
	List<BehaviorIdentifier> behaviorIdentifiers;
	
	MonitorController monitorController;
	ReasonedInterventionController reasonedInterventionController;
	
	public AnalysisController(MonitorController monitorController, MessagesController feedbackController, XmppServerType xmppServerType){
		this.monitorController = monitorController;
		
		behaviorIdentifiers = new Vector<BehaviorIdentifier>();
		behaviorIdentifiers.add(new NewIdeaNotDiscussedIdentifier());
		
		reasonedInterventionController = new ReasonedInterventionController(feedbackController, monitorController.getAnalysisChannelManager(), xmppServerType);
	}
	
	public void analyzeGroup(String groupName, Locale locale){
		List <CfAction> groupActions = getGroupActions(groupName, monitorController.getActionList());
		log.info("[analyzeGroup] number of actions found for group: " + groupActions.size());
		List<String> involvedUsers = getOriginatingUsernames(groupActions);
		log.info("[analyzeGroup] users found for group: " + involvedUsers);
		
		
		List<BehaviorInstance> identifiedBehaviors = new Vector<BehaviorInstance>();
		for (BehaviorIdentifier identifier : behaviorIdentifiers){
			identifiedBehaviors.addAll(identifier.identifyBehaviors(groupActions, involvedUsers));
		}
		log.info("[AnalysisController.analyzeGroup] "+ identifiedBehaviors.size() +" behaviors identified : \n" + identifiedBehaviors);

		reasonedInterventionController.sendInterventions(identifiedBehaviors, locale);
	}
	
	public static List<CfAction> getGroupActions(String groupName, List<CfAction> actions){
		List<ActionPropertyRule> groupRules1 =new Vector<ActionPropertyRule>();
		groupRules1.add(new ActionPropertyRule("GROUP_ID", groupName, PropertyLocation.OBJECT, OperationType.EQUALS));
		ActionFilter groupFilter1 = new ActionFilter("GroupFilter", true, groupRules1);
		return groupFilter1.getFilteredList(actions);
	}
	
	public static List<String> getOriginatingUsernames(List<CfAction> actions) {
		List<String> usernames = new Vector<String>();
		for (CfAction action : actions){
			for (CfUser user : action.getCfUsers()){
				if (MetaforaStrings.USER_ROLE_ORIGINATOR_STRING.equalsIgnoreCase(user.getrole())){
					usernames.add(user.getid());
				}
			}
		}
		return usernames;
	}

	public static List<String> getInvolvedGroups(List<CfAction> actions) {
		List<String> groups = new Vector<String>();
		
		for (CfAction action : actions){
			if (action.getCfContent() != null){
				String groupName = action.getCfContent().getPropertyValue(MetaforaStrings.PROPERTY_TYPE_GROUP_ID_STRING);
				if (groupName != null){
					if (! groups.contains(groupName)){
						groups.add(groupName);
					}
				}
			}
			for (CfObject object : action.getCfObjects()){
				String groupName = object.getPropertyValue(MetaforaStrings.PROPERTY_TYPE_GROUP_ID_STRING);
				if (groupName != null){
					if (! groups.contains(groupName)){
						groups.add(groupName);
					}
				}
			}
		}
		return groups;
	}
	

}

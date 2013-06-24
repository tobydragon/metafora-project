package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.List;
import java.util.Vector;


import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorIdentifier;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorInstance;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.MemberNotDiscussing;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.MembersPlanning;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.NewIdeaNotDiscussedIdentifier;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.UsingAttitudesAndRoles;
import de.uds.MonitorInterventionMetafora.server.messages.MessagesController;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorController;
import de.uds.MonitorInterventionMetafora.shared.analysis.AnalysisActions;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.InterventionCreator;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;

public class AnalysisController {
	Logger log = Logger.getLogger(this.getClass());
	
	List<BehaviorIdentifier> behaviorIdentifiers;
	
	
	MonitorController monitorController;
	ReasonedInterventionController reasonedInterventionController;
	MessagesController commandController;
	
	public AnalysisController(MonitorController monitorController, MessagesController feedbackController, XmppServerType xmppServerType){
		this.monitorController = monitorController;
		this.commandController = feedbackController;
		
		behaviorIdentifiers = new Vector<BehaviorIdentifier>();
		behaviorIdentifiers.add(new NewIdeaNotDiscussedIdentifier());
		behaviorIdentifiers.add(new MembersPlanning());
		behaviorIdentifiers.add(new MemberNotDiscussing());
		behaviorIdentifiers.add(new UsingAttitudesAndRoles());
		
		reasonedInterventionController = new ReasonedInterventionController(feedbackController, monitorController.getAnalysisChannelManager(), xmppServerType);
	}
	
	public void analyzeGroup(String groupName, Locale locale){
		List <CfAction> groupActions = AnalysisActions.getGroupActions(groupName, monitorController.getActionList());
		log.info("[analyzeGroup] number of actions found for group: " + groupActions.size());
		List<String> involvedUsers = AnalysisActions.getOriginatingUsernames(groupActions);
		log.info("[analyzeGroup] users found for group: " + involvedUsers);
		List<CfProperty> groupProperties = AnalysisActions.getPropertiesFromActions(groupActions);
		log.info("[analyzeGroup] properties found for group: " + groupProperties);

		commandController.sendActionToChannel(InterventionCreator.buildSendAnalyisRequestMessage(involvedUsers, groupName, groupProperties));
		//TODO: should probably have a wait for some responses, maybe 30 seconds?
		
		List<BehaviorInstance> identifiedBehaviors = new Vector<BehaviorInstance>();
		for (BehaviorIdentifier identifier : behaviorIdentifiers){
			identifiedBehaviors.addAll(identifier.identifyBehaviors(groupActions, involvedUsers, groupProperties));
		}
		log.info("[AnalysisController.analyzeGroup] "+ identifiedBehaviors.size() +" behaviors identified : \n" + identifiedBehaviors);
		if (identifiedBehaviors.size() > 0){
			reasonedInterventionController.sendInterventions(identifiedBehaviors, involvedUsers, locale);
		}
	}
}

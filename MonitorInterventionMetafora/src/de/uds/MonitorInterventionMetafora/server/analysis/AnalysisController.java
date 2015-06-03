package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorIdentifier;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorInstance;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.messages.MessagesController;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorController;
import de.uds.MonitorInterventionMetafora.shared.analysis.AnalysisActions;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.InterventionCreator;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;

public abstract class AnalysisController {
	Logger log = Logger.getLogger(this.getClass());
	
	List<BehaviorIdentifier> behaviorIdentifiers;
	
	
	MonitorController monitorController;
	InterventionController interventionController;
	MessagesController commandController;
	
	public AnalysisController(MonitorController monitorController, MessagesController feedbackController, XmppServerType xmppServerType){
		this.monitorController = monitorController;
		this.commandController = feedbackController;
		
		behaviorIdentifiers = createIdentifiers();
		
		interventionController = createInterventionController(feedbackController, monitorController.getAnalysisChannelManager(), xmppServerType); 
	}
	
	protected abstract InterventionController createInterventionController(MessagesController feedbackController, CfAgentCommunicationManager analysisChannelManager,  XmppServerType xmppServerType);

	protected abstract List<BehaviorIdentifier> createIdentifiers();
	
	public void analyzeGroup(String groupName, Locale locale){
		List <CfAction> groupActions = null;
		//if all groups, analyze everything together
		if (groupName.equalsIgnoreCase(MetaforaStrings.ALL_GROUPS)){
			groupActions = monitorController.getActionList();
		}
		else {
			groupActions = AnalysisActions.getGroupActions(groupName, monitorController.getActionList());
		}
		List<String> involvedUsers = AnalysisActions.getOriginatingUsernames(groupActions);
		List<CfProperty> groupProperties = AnalysisActions.getPropertiesFromActions(groupActions);
		
		log.info("[analyzeGroup] number of actions found for group: " + groupActions.size());
		log.info("[analyzeGroup] users found for group: " + involvedUsers);
		log.info("[analyzeGroup] properties found for group: " + groupProperties);

		commandController.sendActionToChannel(InterventionCreator.buildSendAnalyisRequestMessage(involvedUsers, groupName, groupProperties));
		//TODO: should probably have a wait for some responses, maybe 30 seconds?
		
		List<BehaviorInstance> identifiedBehaviors = new ArrayList<BehaviorInstance>();
		for (BehaviorIdentifier identifier : behaviorIdentifiers){
			identifiedBehaviors.addAll(identifier.identifyBehaviors(groupActions, involvedUsers, groupProperties));
		}
		log.info("[AnalysisController.analyzeGroup] "+ identifiedBehaviors.size() +" behaviors identified : \n" + identifiedBehaviors);
		if (identifiedBehaviors.size() > 0){
			interventionController.sendInterventions(identifiedBehaviors, involvedUsers, locale);
		}
	}
}

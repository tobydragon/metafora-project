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
		List<ActionPropertyRule> groupRules1 = new Vector<ActionPropertyRule>();
		groupRules1.add(new ActionPropertyRule("GROUP_ID", groupName, PropertyLocation.OBJECT, OperationType.EQUALS));
		ActionFilter groupFilter1 = new ActionFilter("GroupFilter", true, groupRules1);
		
		List <CfAction> actions = monitorController.getActionList();
		List <CfAction> groupActions = groupFilter1.getFilteredList(actions);
		
		log.info("[analyzeGroup] actions found for group: " + groupActions.size());
		
		
		List<BehaviorInstance> identifiedBehaviors = new Vector<BehaviorInstance>();
		for (BehaviorIdentifier identifier : behaviorIdentifiers){
			identifiedBehaviors.addAll(identifier.identifyBehaviors(groupActions));
		}
		log.info("[AnalysisController.analyzeGroup] "+ identifiedBehaviors.size() +" behaviors identified : \n" + identifiedBehaviors);

		reasonedInterventionController.sendInterventions(identifiedBehaviors, locale);
	}
	

}

package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import messages.MessagesController;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorFilters;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorIdentifier;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorInstance;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.StruggleNotDiscussedIdentifier;
import de.uds.MonitorInterventionMetafora.server.monitor.LabellingListener;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorModel;
import de.uds.MonitorInterventionMetafora.shared.analysis.AnalysisActions;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.PropertyLocation;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.InterventionCreator;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.MessageType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessage;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesModel;

public class AnalyzingListener extends LabellingListener{
	Logger logger = Logger.getLogger(this.getClass());

	
	private MessagesController messagesController;
	
	public AnalyzingListener(MonitorModel monitorModel, MessagesController messagesController) {
		super(monitorModel);
		this.messagesController = messagesController;
	}
	
	@Override
	public synchronized void processCfAction(String user, CfAction action) {
		logger.info("[processCfAction] received new message.");
		super.processCfAction(user, action);
		checkEventDrivenResponses(action);
	}

	private void checkEventDrivenResponses(CfAction action) {
		if (BehaviorFilters.createStruggleFilter().filterIncludesAction(action)){
			logger.info("[checkEventCrivenResponses] Struggle identified, checking for discussion");
			BehaviorIdentifier behaviorIdentifier = new StruggleNotDiscussedIdentifier();
			
			List<String> users = AnalysisActions.getOriginatingUsernames(Arrays.asList(action));
			List<ActionPropertyRule> userRules = new Vector<ActionPropertyRule>(); 
			for (String user : users){
				userRules.add(new ActionPropertyRule("id", user, PropertyLocation.USER, OperationType.EQUALS));					
			}
			ActionFilter userActions = new ActionFilter(userRules);
			List<CfAction> actionsToConsider = userActions.getFilteredList(model.getActionList());
			
			List<CfProperty> properties = AnalysisActions.getPropertiesFromActions(actionsToConsider);
			List<BehaviorInstance> behaviorInstances = behaviorIdentifier.identifyBehaviors(actionsToConsider, users, properties);
			
			sendDirectMessages(behaviorInstances);
		}
	}
	
	private void sendDirectMessages(List<BehaviorInstance> behaviorsIdentified){
		for (BehaviorInstance instanceForDirectFeedback : behaviorsIdentified) {
			//TODO: locale should be included as a property on the indicators, until then, assuming english
			String localeStr = instanceForDirectFeedback.getPropertyValue("LOCALE");
			Locale locale = Locale.en;
			if (localeStr != null){
				locale = Locale.valueOf(localeStr);
			}

			setDirectMessagesForBehaviors(instanceForDirectFeedback, locale);
			SuggestedMessage message = instanceForDirectFeedback.getBestSuggestedMessage();
			if (message != null){
				messagesController.sendMessage( InterventionCreator.createDirectMessage(messagesController.getXmppServerType().toString(), Arrays.asList("System"), 
						instanceForDirectFeedback.getUsernames(), null, MetaforaStrings.HIGH_INTERRUPTION, message.getText(), message.getL2L2Category(),  null,
						instanceForDirectFeedback.getPropertyValue("CHALLENGE_ID"), instanceForDirectFeedback.getPropertyValue("CHALLENGE_NAME"), false));
			}
		}
	}
	
	private void setDirectMessagesForBehaviors(BehaviorInstance behaviorInstance, Locale locale){
		//TODO: No need to make a new model each time, could be referencing same model to copy messages
		SuggestedMessagesModel externalMessageModel = messagesController.getCopyOfDefaultMessages(locale, MessageType.EXTERNAL);
		behaviorInstance.addSuggestedMessages(externalMessageModel.getSuggestedMessagesForBehaviorType(behaviorInstance.getBehaviorType()));
	}

}

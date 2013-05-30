package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import messages.MessagesController;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorInstance;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.mmftparser.SuggestedMessagesModelParserForServer;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.InterventionCreator;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.MessageType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessage;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesModel;

public class ReasonedInterventionController {
	
	MessagesController messagesController;
	CfAgentCommunicationManager analysisChannelManager;
	XmppServerType xmppServerType;
		
	
	public ReasonedInterventionController(MessagesController feedbackController, CfAgentCommunicationManager analysisChannelManagaer, XmppServerType xmppServerType) {
		this.messagesController = feedbackController;
		this.analysisChannelManager = analysisChannelManagaer;
		this.xmppServerType = xmppServerType;
	}

	//	method that	 decides whether to send suggestions, landmarks, and messages
	public void sendInterventions(List<BehaviorInstance> behaviorsIdentified, Locale locale){
		for (BehaviorInstance behaviorInstance : behaviorsIdentified){
			sendLandmarkForBehavior(behaviorInstance);
		}

		sendSuggestionsForAllBehaviors(behaviorsIdentified, locale);
		
		setDirectMessagesForBehaviors(behaviorsIdentified, locale);
		
		BehaviorInstance instanceForDirectFeedback = chooseOneForDirectFeedback(behaviorsIdentified);
		if (instanceForDirectFeedback != null) {
			SuggestedMessage message = instanceForDirectFeedback.getBestSuggestedMessage();
			if (instanceForDirectFeedback != null && message != null){
				messagesController.sendMessage( InterventionCreator.createDirectMessage(xmppServerType.toString(), instanceForDirectFeedback.getUsernames(), null, "HIGH", message.getText(), null));
			}
		}
	}
	

	private BehaviorInstance chooseOneForDirectFeedback(List<BehaviorInstance> behaviorsIdentified) {
		//TODO: do something smarter than just the first one
		int index = behaviorsIdentified.size()-1;
		if (index >= 0){
			return behaviorsIdentified.get(0);
		}
		return null;
	}

	public void sendLandmarkForBehavior(BehaviorInstance behaviorInstance){
		String description = "Possible " + behaviorInstance.getBehaviorType() + " detected.";

		CfAction cfAction = InterventionCreator.createLandmark(behaviorInstance.getUsernames(), description, behaviorInstance.getProperties());
		analysisChannelManager.sendMessage(cfAction);
	}
	
	public void sendSuggestionsForAllBehaviors(List<BehaviorInstance> behaviorsIdentified, Locale locale){
		SuggestedMessagesModel peerMessageModel = messagesController.getCopyOfDefaultMessages(locale, MessageType.PEER);
		for (BehaviorInstance behaviorInstance : behaviorsIdentified){
			peerMessageModel.highlightMessagesForBehaviorType(behaviorInstance.getBehaviorType());
		}
		//TODO: get users
		CfAction intervention = InterventionCreator.createSendSuggestedMessages(Arrays.asList("Bob"), SuggestedMessagesModelParserForServer.toXml(peerMessageModel).toString());
		if( intervention != null){
			messagesController.sendSuggestedMessages(intervention);
		}
	}
	
	public void setDirectMessagesForBehaviors(List<BehaviorInstance> behaviorInstances, Locale locale){
		//TODO: No need to make a new model each time, could be referencing same model to copy messages
		SuggestedMessagesModel externalMessageModel = messagesController.getCopyOfDefaultMessages(locale, MessageType.EXTERNAL);
		for (BehaviorInstance behaviorInstance : behaviorInstances){
			behaviorInstance.addSuggestedMessages(externalMessageModel.getSuggestedMessagesForBehaviorType(behaviorInstance.getBehaviorType()));
		}
	}
}

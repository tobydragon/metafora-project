package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.List;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorInstance;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.messages.MessagesController;
import de.uds.MonitorInterventionMetafora.server.mmftparser.SuggestedMessagesModelParserForServer;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.InterventionCreator;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.MessageType;
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
	public void sendInterventions(List<BehaviorInstance> behaviorsIdentified, List<String> involovedUsers, Locale locale){
		for (BehaviorInstance behaviorInstance : behaviorsIdentified){
			sendLandmarkForBehavior(behaviorInstance);
		}

		sendSuggestionsForAllBehaviors(behaviorsIdentified,involovedUsers, locale);
	}

	public void sendLandmarkForBehavior(BehaviorInstance behaviorInstance){
		String description = "Possible " + behaviorInstance.getBehaviorType() + " detected involving user(s):" + behaviorInstance.getUsernames();

		CfAction cfAction = InterventionCreator.createLandmark(behaviorInstance.getUsernames(), description, behaviorInstance.getProperties(), null);
		analysisChannelManager.sendMessage(cfAction);
	}
	
	public void sendSuggestionsForAllBehaviors(List<BehaviorInstance> behaviorsIdentified, List<String> involvedUsers, Locale locale){
		SuggestedMessagesModel peerMessageModel = messagesController.getCopyOfDefaultMessages(locale, MessageType.PEER);
		for (BehaviorInstance behaviorInstance : behaviorsIdentified){
			peerMessageModel.highlightMessagesForBehaviorType(behaviorInstance.getBehaviorType());
		}
		
		CfAction intervention = InterventionCreator.createSendSuggestedMessages(involvedUsers, SuggestedMessagesModelParserForServer.toXml(peerMessageModel).toString());
		if( intervention != null){
			messagesController.sendSuggestedMessages(intervention);
		}
	}
	
}

package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.ArrayList;
import java.util.List;

import messages.MessagesController;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorInstance;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.MessageType;

public class ReasonedInterventionController {
	
	MessagesController feedbackController;
	CfAgentCommunicationManager analysisChannelManager;
		
	
	public ReasonedInterventionController(MessagesController feedbackController, CfAgentCommunicationManager analysisChannelManagaer) {
		this.feedbackController = feedbackController;
		this.analysisChannelManager = analysisChannelManagaer;
	}

	//	method that takes list of notifications, and decides whether to send suggestion, landmark, or message
	public void sendInterventions(List<BehaviorInstance> behaviorsIdentified){
		for (BehaviorInstance behaviorInstance : behaviorsIdentified){
			sendLandmarkForBehavior(behaviorInstance);
		}
		sendSuggestionsForAllBehaviors(behaviorsIdentified);
	}
	
	public void sendLandmarkForBehavior(BehaviorInstance behaviorInstance){
		CfContent content=new CfContent("Possible " + behaviorInstance.getBehaviorType() + " detected.");

		content.addProperty(new CfProperty(CommonFormatStrings.INDICATOR_TYPE, CommonFormatStrings.ACTIVITY));
		content.addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_SENDING_TOOL, MetaforaStrings.ANAYLSIS_MANAGER));
		
		CfActionType cfActionType = new CfActionType(CommonFormatStrings.LANDMARK, CommonFormatStrings.OTHER, CommonFormatStrings.TRUE);
		
		final List<CfUser> users=new ArrayList<CfUser>();
		for (String username: behaviorInstance.getUsernames()){
			users.add(new CfUser(username, "originator"));
		}
		
		CfAction cfAction = new CfAction(System.currentTimeMillis(), cfActionType, users, new ArrayList<CfObject>(),content);	
		feedbackController.sendAction(MetaforaStrings.ANAYLSIS_MANAGER, cfAction);
	}
	
	public void sendSuggestionsForAllBehaviors(List<BehaviorInstance> behaviorsIdentified){
		String englishPeer = feedbackController.getDefaultMessages(Locale.en, MessageType.PEER);
		String englishExternal = feedbackController.getDefaultMessages(Locale.en, MessageType.EXTERNAL);
		
		String hePeer = feedbackController.getDefaultMessages(Locale.he, MessageType.PEER);
	}
	
//	public 
	
//	method to send message to control
//	public CfAction createNotificationCfAction() {	
//		CfContent content=new CfContent(getDescriptionString());
//
//		content.addProperty(new CfProperty(CommonFormatStrings.INDICATOR_TYPE, CommonFormatStrings.ACTIVITY));
//		content.addProperty(new CfProperty(CommonFormatStrings.TOOL, CommonFormatStrings.VISAULIZER_ANALYZER));
//		content.addProperty(new CfProperty(CommonFormatStrings.ANALYSIS_TYPE, CommonFormatStrings.NOTIFICATION));
////		String color = filter.getColor();
////		if(color!=null && color!=""){
////			content.addProperty(new CfProperty(CommonFormatStrings.COLOR,color));
////		}
//
//		final CfActionType cfActionType = new CfActionType(CommonFormatStrings.LANDMARK, 
//				CommonFormatStrings.OTHER, CommonFormatStrings.TRUE);
//		
//		final CfUser _user=new CfUser("NotificationManager", "Manager");
//		final List<CfUser> _users=new ArrayList<CfUser>();
//		_users.add(_user);
//		
//		return new CfAction(System.currentTimeMillis(), cfActionType,_users, new ArrayList<CfObject>(),content);	
//	}
	
//	method to send suggestions to control
	
	public String messageLookup(BehaviorType behaviorType){
		return behaviorType.toString();
	}
}

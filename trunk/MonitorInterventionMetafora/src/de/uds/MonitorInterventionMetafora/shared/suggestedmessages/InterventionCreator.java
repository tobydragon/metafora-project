package de.uds.MonitorInterventionMetafora.shared.suggestedmessages;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.allen_sauer.gwt.log.client.Log;

import de.uds.MonitorInterventionMetafora.server.analysis.AnalysisController;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

public class InterventionCreator {
	
	public static CfAction createSendSuggestedMessages(List <String> userIds, String XML){
		CfActionType cfActionType = new CfActionType();
		cfActionType.setType(MetaforaStrings.ACTION_TYPE_SUGGESTED_MESSAGES_STRING);
		cfActionType.setClassification("create");
		cfActionType.setLogged("true");

		CfAction cfAction = new CfAction(GWTUtils.getTimeStamp(), cfActionType);

		if (userIds.size() < 1) {
			Log.warn("[MessageCreator.createSendSuggestedMessages()] No users, returning null.");
			return null;
		}
		for (String userId : userIds) {
			cfAction.addUser(new CfUser(userId, "receiver"));
		}
		
		CfContent cfContent = new CfContent(XML);
		cfContent.addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_RECEIVING_TOOL, MetaforaStrings.MONITOR_AND_MESSAGE_TOOL_NAME));
		cfContent.addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_SENDING_TOOL, MetaforaStrings.MONITOR_AND_MESSAGE_TOOL_NAME));
		cfAction.setCfContent(cfContent);
		
		return cfAction;
	}
	
	public static CfAction createDirectMessage(String receiver, List<String> userIds, String interruptionType, String message, List<String> objectIds){
		CfAction feedbackMessage=new CfAction();
	 	feedbackMessage.setTime(GWTUtils.getTimeStamp());
	 	  
		 CfActionType cfActionType=new CfActionType();
	 	 cfActionType.setType("FEEDBACK");
	 	 cfActionType.setClassification("create");
	 	 cfActionType.setLogged("true");
	 	 	
 	 	feedbackMessage.setCfActionType(cfActionType);

 	 	for (String userId : userIds) {
 	 		feedbackMessage.addUser(new CfUser(userId, "receiver"));
 	 	}
 	 	
 	 	//TODO: what happens with IDs? And do they matter? 
 	 	//Can we uniquely auto-increment them? Perhaps use that java UUID library? 
 	 	CfObject cfObject = new CfObject("0", MetaforaStrings.PROPERTY_VALUE_MESSAGE_STRING);
 	 	
 	 	cfObject.addProperty(new CfProperty("INTERRUPTION_TYPE", interruptionType));
 	 	cfObject.addProperty(new CfProperty("TEXT", message));
 	 	feedbackMessage.addObject(cfObject);

 	 	if (objectIds != null){
	 		for (String id : objectIds) {
	 			if (!"".equals(id))
	 				feedbackMessage.addObject(new CfObject(id, MetaforaStrings.REFERABLE_OBJECT_STRING));
	 		}
 	 	}
		
		CfContent myContent = new CfContent();
		myContent.addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_RECEIVING_TOOL,receiver));
		myContent.addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_SENDING_TOOL, MetaforaStrings.MONITOR_AND_MESSAGE_TOOL_NAME));
 	 	feedbackMessage.setCfContent(myContent);

	 	return feedbackMessage;
		
	}
	
	public static CfAction createLandmark(List<String> usernames, String description, List<CfProperty> properties){
		CfContent content=new CfContent(description);
		for (CfProperty property : properties){
			content.addProperty(property);
		}

		content.addProperty(new CfProperty(CommonFormatStrings.INDICATOR_TYPE, CommonFormatStrings.ACTIVITY));
		content.addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_SENDING_TOOL, MetaforaStrings.ANAYLSIS_MANAGER));
		
		CfActionType cfActionType = new CfActionType(CommonFormatStrings.LANDMARK, CommonFormatStrings.OTHER, CommonFormatStrings.TRUE);
		
		final List<CfUser> users=new ArrayList<CfUser>();
		for (String username: usernames){
			users.add(new CfUser(username, "originator"));
		}
		
		
		CfAction cfAction = new CfAction(System.currentTimeMillis(), cfActionType, users, new ArrayList<CfObject>(),content);
		return cfAction;
	}
	
	public static CfAction createLandmarkForOutgoingMessage (CfAction message){
		List<String> usernames = new Vector<String>();
		String senderString = message.getListofUsersAsStringWithRole("sender");
		String receiverString = message.getListofUsersAsStringWithRole("receiver");
		String text = message.getCfObjects().get(0).getPropertyValue("TEXT");
		for (CfUser user : message.getCfUsers()){
			usernames.add(user.getid());
		}
		String description = senderString + " sent message: \"" + text + "\" to " + receiverString;
		
		List<CfAction> actions = new Vector<CfAction>();
		actions.add(message);
		return createLandmark(usernames, description, AnalysisController.getPropertiesFromActions(actions) );
	}

}

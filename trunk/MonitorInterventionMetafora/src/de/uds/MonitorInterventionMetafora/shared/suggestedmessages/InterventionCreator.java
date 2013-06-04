package de.uds.MonitorInterventionMetafora.shared.suggestedmessages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.allen_sauer.gwt.log.client.Log;

import de.uds.MonitorInterventionMetafora.shared.analysis.AnalysisActions;
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
	
	public static CfAction createDirectMessage(String receivingTool, List<String> sendingUsers, List<String> receivingUserIds,
												String groupId, String interruptionType, String message, L2L2category l2l2category,
												List<String> objectIds, String challengeId, String challengeName){
		CfAction feedbackMessage=new CfAction();
	 	feedbackMessage.setTime(GWTUtils.getTimeStamp());
	 	  
		 CfActionType cfActionType=new CfActionType();
	 	 cfActionType.setType("FEEDBACK");
	 	 cfActionType.setClassification("create");
	 	 cfActionType.setLogged("true");
	 	 	
 	 	feedbackMessage.setCfActionType(cfActionType);

 	 	for (String senderUser : sendingUsers){
 	 		feedbackMessage.addUser(new CfUser(senderUser, "sender"));
 	 	}
 	 	
 	 	for (String userId : receivingUserIds) {
 	 		feedbackMessage.addUser(new CfUser(userId, "receiver"));
 	 	}
 	 	
 	 	//IDs don't seem to matter. 
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
		myContent.addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_RECEIVING_TOOL,receivingTool));
		myContent.addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_SENDING_TOOL, MetaforaStrings.MONITOR_AND_MESSAGE_TOOL_NAME));
		if (groupId != null){
			myContent.addProperty(new CfProperty("GROUP_ID",groupId));	
		}
		
		if (l2l2category != null){
			myContent.addProperty(new CfProperty("L2L2_TAG", l2l2category.toString()));
		}
		feedbackMessage.setCfContent(myContent);

		if (challengeName != null){
			myContent.addProperty(new CfProperty("CHALLENGE_NAME", challengeName));
		}
		feedbackMessage.setCfContent(myContent);
		
		if (challengeId != null){
			myContent.addProperty(new CfProperty("CHALLENGE_ID", challengeId));
		}
		feedbackMessage.setCfContent(myContent);
		
	 	return feedbackMessage;	
	}
	
	public static CfAction createLandmark(List<String> usernames, String description, List<CfProperty> properties, CfObject object){
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
		
		if (object != null){
			cfAction.addObject(object);
		}
		return cfAction;
	}
	
	public static CfAction createLandmarkForOutgoingMessage (CfAction message){
		List<String> usernames = new Vector<String>();
		String senderString = message.getListofUsersAsStringWithRole("sender");
		String receiverString = message.getListofUsersAsStringWithRole("receiver");
		String text = message.getCfObjects().get(0).getPropertyValue("TEXT");
		
		
		
		if (receiverString == null || receiverString.length()<1){
			String groupId = message.getCfContent().getPropertyValue("GROUP_ID");
			receiverString = "group " + groupId;
		}
		
		for (CfUser user : message.getCfUsers()){
			usernames.add(user.getid());
		}
		
		String description = senderString + " sent message: \"" + text + "\" to " + receiverString;
		
		List<CfAction> actions = new Vector<CfAction>();
		actions.add(message);
		
		List<CfProperty> properties = AnalysisActions.getPropertiesFromActions(actions);
		String l2l2Tag = message.getCfContent().getPropertyValue("L2L2_TAG");
		if (l2l2Tag != null){
			properties.add(new CfProperty("L2L2_TAG", l2l2Tag));
		}
		
		return createLandmark(usernames, description,  properties, message.getCfObjects().get(0));
	}

	public static CfAction createNewSuggestedMessagesNotification(String receiver, CfAction cfAction) {
		List<CfUser> cfUsers = cfAction.getCfUsers();
		if (cfUsers != null && cfUsers.size() > 0){
			List<String> usernames = new Vector<String>();
			for (CfUser user : cfUsers){
				usernames.add(user.getid());
			}
			String message = "New Tips available in Messaging Tool";
			return createDirectMessage(receiver, Arrays.asList("System"), usernames, null, MetaforaStrings.LOW_INTERRUPTION, message, null, null, null, null);
		}
		Log.error("[createNewSuggestedMessagesNotification] Send Suggested messages with no users.");
		return null;
	}

}

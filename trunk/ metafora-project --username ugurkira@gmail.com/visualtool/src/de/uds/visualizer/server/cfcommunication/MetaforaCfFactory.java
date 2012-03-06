package de.uds.visualizer.server.cfcommunication;

import de.uds.visualizer.shared.commonformat.CfAction;
import de.uds.visualizer.shared.commonformat.CfActionType;
import de.uds.visualizer.shared.commonformat.CfObject;
import de.uds.visualizer.shared.commonformat.CfProperty;
import de.uds.visualizer.shared.commonformat.CfUser;

public class MetaforaCfFactory {
	
	public static CfAction buildDisplayStateUrlMessage(String mapId, String userId, String stateUrl){
		CfObject element = new CfObject("0", "element");
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_MAP_ID_STRING, mapId));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_ELEMENT_TYPE_STRING, MetaforaStrings.REFERABLE_OBJECT_STRING));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_REFERENCE_URL_STRING, stateUrl));
		CfActionType cfActionType = new CfActionType(MetaforaStrings.ACTION_TYPE_DISPLAY_STATE_URL_STRING, 
				MetaforaStrings.CLASSIFICATION_OTHER_STRING, MetaforaStrings.ACTION_TYPE_SUCCEEDED_UNKNOWN_STRING);
		CfAction cfAction = new CfAction(System.currentTimeMillis(), cfActionType);
		cfAction.addUser(new CfUser(userId, MetaforaStrings.USER_ROLE_ORIGINATOR_STRING));
		cfAction.addObject(element);
		return cfAction;
	}
	
	public static CfAction buildMyMicroworldObjectMessage(String mapId, String userId, String viewUrl, String referenceUrl, String text, String username){
		CfObject element = new CfObject("0", "element");
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_MAP_ID_STRING, mapId));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_ELEMENT_TYPE_STRING, MetaforaStrings.OBJECT_TYPE_MY_MICROWORLD_STRING));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_VIEW_URL_STRING, viewUrl));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_TEXT_STRING, text));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_REFERENCE_URL_STRING, referenceUrl));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_USERNAME_STRING, username));
		CfActionType cfActionType = new CfActionType(MetaforaStrings.ACTION_TYPE_CREATE_ELEMENT_STRING, 
				MetaforaStrings.CLASSIFICATION_OTHER_STRING, MetaforaStrings.ACTION_TYPE_SUCCEEDED_UNKNOWN_STRING);
		CfAction cfAction = new CfAction(System.currentTimeMillis(), cfActionType);
		cfAction.addUser(new CfUser(userId, MetaforaStrings.USER_ROLE_ORIGINATOR_STRING));
		cfAction.addObject(element);
		return cfAction;
	}
	
	public static CfAction buildHelpRequestObjectMessage(String mapId, String userId, String viewUrl, String referenceUrl, String text, String username){
		CfObject element = new CfObject("0", "element");
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_MAP_ID_STRING, mapId));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_ELEMENT_TYPE_STRING, MetaforaStrings.OBJECT_TYPE_HELP_REQUEST_STRING));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_VIEW_URL_STRING, viewUrl + "&thumbnail=150"));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_TEXT_STRING, text));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_REFERENCE_URL_STRING, referenceUrl));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_USERNAME_STRING, username));
		CfActionType cfActionType = new CfActionType(MetaforaStrings.ACTION_TYPE_CREATE_ELEMENT_STRING, 
				MetaforaStrings.CLASSIFICATION_OTHER_STRING, MetaforaStrings.ACTION_TYPE_SUCCEEDED_UNKNOWN_STRING);
		CfAction cfAction = new CfAction(System.currentTimeMillis(), cfActionType);
		cfAction.addUser(new CfUser(userId, MetaforaStrings.USER_ROLE_ORIGINATOR_STRING));
		cfAction.addObject(element);
		return cfAction;
	}
	
	public static CfAction buildQuestionMessage(String mapId, String senderId, String text, String username){
		CfObject element = new CfObject("0", "element");
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_USERNAME_STRING, username));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_MAP_ID_STRING, mapId));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_ELEMENT_TYPE_STRING, MetaforaStrings.OBJECT_TYPE_QUESTION_STRING));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_TEXT_STRING, text));
		CfActionType cfActionType = new CfActionType(MetaforaStrings.ACTION_TYPE_CREATE_ELEMENT_STRING, 
				MetaforaStrings.CLASSIFICATION_OTHER_STRING, MetaforaStrings.ACTION_TYPE_SUCCEEDED_UNKNOWN_STRING);
		CfAction cfAction = new CfAction(System.currentTimeMillis(), cfActionType);
		cfAction.addUser(new CfUser(senderId, MetaforaStrings.USER_ROLE_ORIGINATOR_STRING));
		cfAction.addObject(element);
		return cfAction;
	}
	
	public static CfAction buildCreateUserMessage(String senderName, String username, String password, String role){
		CfObject element = new CfObject("0", "element");
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_USERNAME_STRING, username));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_PASSWORD_STRING, password));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_ROLE_STRING, role));
		CfActionType cfActionType = new CfActionType(MetaforaStrings.ACTION_TYPE_CREATE_USER_STRING, 
				MetaforaStrings.CLASSIFICATION_OTHER_STRING, MetaforaStrings.ACTION_TYPE_SUCCEEDED_UNKNOWN_STRING);
		CfAction cfAction = new CfAction(System.currentTimeMillis(), cfActionType);
		cfAction.addUser(new CfUser(senderName, MetaforaStrings.USER_ROLE_ORIGINATOR_STRING));
		cfAction.addObject(element);
		return cfAction;

	}

	public static CfAction buildCreateMapMessage(String senderName, String mapname, String template){
		CfObject element = new CfObject("0", "element");
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_MAPNAME_STRING, mapname));
		element.addProperty(new CfProperty(MetaforaStrings.PROPERTY_TYPE_TEMPLATE_STRING, template));
		CfActionType cfActionType = new CfActionType(MetaforaStrings.ACTION_TYPE_CREATE_MAP_STRING, 
				MetaforaStrings.CLASSIFICATION_OTHER_STRING, MetaforaStrings.ACTION_TYPE_SUCCEEDED_UNKNOWN_STRING);
		CfAction cfAction = new CfAction(System.currentTimeMillis(), cfActionType);
		cfAction.addUser(new CfUser(senderName, MetaforaStrings.USER_ROLE_ORIGINATOR_STRING));
		cfAction.addObject(element);
		return cfAction;

	}
}

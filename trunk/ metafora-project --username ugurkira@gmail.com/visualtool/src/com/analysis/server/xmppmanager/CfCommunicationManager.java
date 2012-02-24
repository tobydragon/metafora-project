package com.analysis.server.xmppmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.analysis.server.xml.XmlFragment;
import com.analysis.shared.communication.objects.CfAction;

//import de.dfki.lasad.agents.instances.xmpp.CfAgentInterface;
//import de.dfki.lasad.agents.instances.xmpp.CfManagementActionAgent;
//import de.uds.commonformat.CfAction;
//import de.uds.util.GeneralUtil;
//import de.uds.xml.XmlFragment;

public class CfCommunicationManager implements CfCommunicationListener{
	Log logger = LogFactory.getLog(CfCommunicationManager.class);
	
	private static Map<CommunicationMethodType, Map<CommunicationChannelType, CfCommunicationManager>> instanceMatrix = new Hashtable <CommunicationMethodType, Map<CommunicationChannelType, CfCommunicationManager>>();	
	
	// We only maintain one instance of the communication manager, for each method and each channel.
	// All connections for a specific method and channel speak through this one instance.
	public static CfCommunicationManager getInstance(CommunicationMethodType methodType, CommunicationChannelType channelType){
		
		Map<CommunicationChannelType, CfCommunicationManager> channelMap = instanceMatrix.get(methodType);
		if (channelMap == null){
			channelMap = new HashMap<CommunicationChannelType, CfCommunicationManager>();
			instanceMatrix.put(methodType, channelMap);
		}
		CfCommunicationManager instance = channelMap.get(channelType);
		if (instance == null){
			instance = new CfCommunicationManager(methodType, channelType);
			channelMap.put(channelType, instance);
		}
		return instance;
	}
	
	//default to xmpp
	public static CfCommunicationManager getInstance(CommunicationChannelType channelType){
		return getInstance(CommunicationMethodType.xmpp, channelType);
	}

	
//-----------------------------Object level (non-static) -----------------//
	
	 
	 
	// One management agent to handle actions outside of maps (held directly by the sessionManager)
	//private CfManagementActionAgent managementAgent;
	// One agent for each map to handle actions associated with a specific map (held by the individual agents)
	//private Map<String, CfAgentInterface> session2agentMap;
	
	private CfCommunicationBridge cfCommnicationBridge;
	
	List<String> controllingUsers = new ArrayList<String>();
	
	public CfCommunicationManager(CommunicationMethodType methodType, CommunicationChannelType type){
		//session2agentMap = new HashMap<String, CfAgentInterface>();
	//	managementAgent = null;
		
		if (methodType == CommunicationMethodType.xmpp){
			cfCommnicationBridge = new CfXmppCommunicationBridge(type);
			cfCommnicationBridge.registerListener(this);
		}
		else if (methodType == CommunicationMethodType.file){
			cfCommnicationBridge = new CfFileCommunicationBridge(type);
			cfCommnicationBridge.registerListener(this);
		}
		
		// TODO: make controlling users read from separate file
		controllingUsers.add("Metafora-test");
	}
	
	/*
	public void register(CfAgentInterface agent){
		if (agent instanceof CfManagementActionAgent){
			if (managementAgent != null){
				logger.warn("[register] second managment agent, not expected...");
			}
			managementAgent = (CfManagementActionAgent)agent;
		}
		else {
			session2agentMap.put(agent.getSessionID().getIdAsString(), agent);
		}
	}
	
	public void unregister(CfAgentInterface agent){
		if (agent instanceof CfManagementActionAgent){
			managementAgent = null;
		}
		else {
			session2agentMap.remove(agent.getSessionID().getIdAsString());
		}
	}*/
	
	@Override
	public void receiveMessage(String user, String message) {
		logger.info("[newMessage] from user(" + user + ")\n" +  message);
		processNewMessage(message);	
		
	}
	
	public void sendMessage(CfAction actionToSend){
			cfCommnicationBridge.sendAction(actionToSend);
	}
	
//---------------- private helper methods --------------------------//
	
	private void processNewMessage(String message){
		message = XmlFragment.convertSpecialCharactersToDescripitons(message);
		XmlFragment actionXml = XmlFragment.getFragmentFromString(message);
		if (actionXml != null){
		/*	CfAction action = CfAction.fromXml(actionXml);
			if (shouldTakeActionOnMessage(action)){
				findAgentAndSendAction(action);	
			}*/
		}
		else {
			logger.info("[newMessage] No Action xml recognized" );
		}
	}
	/*
	private void findAgentAndSendAction(CfAction action){
		CfAgentInterface agentToCall = findAgent(action);
		if (agentToCall != null){
			agentToCall.processCfAction(action);
		}
		else {
			logger.error("[findAgentAndSendAction] agent not found for action - " + action.toString());
		}
	}
	
	private CfAgentInterface findAgent(CfAction action){
		//if it's management, use any agent, else, find the right agent.
		String cfActionType = action.getCfActionType().getType();	
		if (isManagementAction(action)){
			if (managementAgent != null){
				return managementAgent;
			}
			else {
				logger.error("[findAgent] management action with no managment agent for action - " + action.toString());
				return null;
			}
		}
		else if (MetaforaStrings.ACTION_TYPE_CREATE_ELEMENT_STRING.equalsIgnoreCase(cfActionType)){
			String sessionId = action.getCfObjects().get(0).getPropertyValue(MetaforaStrings.PROPERTY_TYPE_MAP_ID_STRING);
			return session2agentMap.get(sessionId);
		}
		else {
			logger.error("[findAgent] Unrecognized actionType for action - " + action.toString());
			return null;
		}
	}
	
	private boolean shouldTakeActionOnMessage(CfAction action) {
		String userId = action.getUserWithRole(MetaforaStrings.USER_ROLE_ORIGINATOR_STRING).getid();
		if (shouldTakeActionsFromUser(userId)){
			if ( GeneralUtil.isTimeRecent(action.getTime())){
				if (isSupportedType(action)){
					return true;
				}
				else {
					logger.info("[shouldTakeActionOnMessage] Uknown action being ignored");
				}
			}
			else {
				logger.info("[shouldTakeActionOnMessage] Old action being ignored");
			}
		}
		else {
			logger.info("[shouldTakeActionOnMessage] Ignoring messages from user - " + userId );
		}
		return false;
	}
	
	private boolean isSupportedType(CfAction action){
		String actionType = action.getCfActionType().getType();
		if (isManagementAction(actionType)){
			return true;
		}
		else if (MetaforaStrings.ACTION_TYPE_CREATE_ELEMENT_STRING.equalsIgnoreCase(actionType)){
			String elementType = action.getCfObjects().get(0).getPropertyValue(MetaforaStrings.PROPERTY_TYPE_ELEMENT_TYPE_STRING);
			if(isSupportedElementType(elementType)){
				return true;
			}
			logger.error("[isSupportedType]" + " Bad element type:" + elementType);
			return false;
		}
		logger.error("[isSupportedType]" + " Uknown action :" + action);
		return false;
		
	}
	
	private boolean isManagementAction(String actionType) {
		
		if (actionType.equalsIgnoreCase(MetaforaStrings.ACTION_TYPE_CREATE_USER_STRING)
				|| actionType.equalsIgnoreCase(MetaforaStrings.ACTION_TYPE_CREATE_MAP_STRING)){
			return true;
		}
		return false;
	}
	
	private boolean isSupportedElementType(String elementType){
		//TODO this should be looking to the actual ontology in the future versions, not hard-coded.
		if (elementType != null){
			if (MetaforaStrings.OBJECT_TYPE_HELP_REQUEST_STRING.equalsIgnoreCase(elementType)
					|| MetaforaStrings.OBJECT_TYPE_MY_MICROWORLD_STRING.equalsIgnoreCase(elementType)){
				return true;
			}
		}
		return false;
	}
	
	private boolean shouldTakeActionsFromUser(String userId){
		for (String controllingUser : controllingUsers){
			if (controllingUser.equalsIgnoreCase(userId)){
				return true;
			}
		}
		return false;
	}
	
	private boolean isManagementAction(CfAction action) {
		String actionType = action.getCfActionType().getType();
		if (actionType.equalsIgnoreCase(MetaforaStrings.ACTION_TYPE_CREATE_USER_STRING)
				|| actionType.equalsIgnoreCase(MetaforaStrings.ACTION_TYPE_CREATE_MAP_STRING)){
			return true;
		}
		return false;
	}*/

	

}
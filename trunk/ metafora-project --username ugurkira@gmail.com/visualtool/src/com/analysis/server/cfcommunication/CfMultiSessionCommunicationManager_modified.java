package com.analysis.server.cfcommunication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.analysis.server.utils.GeneralUtil;

import com.analysis.shared.commonformat.CfAction;

//import de.dfki.lasad.agents.instances.xmpp.CfAgentInterface;
//import de.dfki.lasad.agents.instances.xmpp.CfManagementActionAgent;
//import de.uds.util.GeneralUtil;


//used when there are management actions and actions for specific sessions, and XMPP messages contain references for those sessions
public class CfMultiSessionCommunicationManager_modified implements CfCommunicationListener{
	Log logger = LogFactory.getLog(CfMultiSessionCommunicationManager_modified.class);
	
	private static Map<CommunicationMethodType, Map<CommunicationChannelType, CfMultiSessionCommunicationManager_modified>> instanceMatrix = new Hashtable <CommunicationMethodType, Map<CommunicationChannelType, CfMultiSessionCommunicationManager_modified>>();	
	
	// We only maintain one instance of the communication manager, for each method and each channel.
	// All connections for a specific method and channel speak through this one instance.
	public static CfMultiSessionCommunicationManager_modified getInstance(CommunicationMethodType methodType, CommunicationChannelType channelType){
		
		Map<CommunicationChannelType, CfMultiSessionCommunicationManager_modified> channelMap = instanceMatrix.get(methodType);
		if (channelMap == null){
			channelMap = new HashMap<CommunicationChannelType, CfMultiSessionCommunicationManager_modified>();
			instanceMatrix.put(methodType, channelMap);
		}
		CfMultiSessionCommunicationManager_modified instance = channelMap.get(channelType);
		if (instance == null){
			instance = new CfMultiSessionCommunicationManager_modified(methodType, channelType);
			channelMap.put(channelType, instance);
		}
		return instance;
	}
	
	//default to xmpp
	public static CfMultiSessionCommunicationManager_modified getInstance(CommunicationChannelType channelType){
		return getInstance(CommunicationMethodType.xmpp, channelType);
	}

	
//-----------------------------Object level (non-static) -----------------//
	
	 
	 
	// One management agent to handle actions outside of maps (held directly by the sessionManager)
	//*private CfManagementActionAgent managementAgent;
	// One agent for each map to handle actions associated with a specific map (held by the individual agents)
	//*private Map<String, CfAgentInterface> session2agentMap;
	
	private CfCommunicationBridge cfCommnicationBridge;
	
	List<String> controllingUsers = new ArrayList<String>();
	
	public CfMultiSessionCommunicationManager_modified(CommunicationMethodType methodType, CommunicationChannelType type){
		//*session2agentMap = new HashMap<String, CfAgentInterface>();
		//*managementAgent = null;
		
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
	
	
	/*public void register(CfAgentInterface agent){
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
	public void processCfAction(String user, CfAction action) {
		processNewMessage(user, action);
	}
	
	public void sendMessage(CfAction actionToSend){
			cfCommnicationBridge.sendAction(actionToSend);
	}
	
//---------------- private helper methods --------------------------//
	
	private void processNewMessage(String user, CfAction action){
		if (shouldTakeActionOnMessage(action)){
			findAgentAndSendAction(user, action);	
		}
	}
	
	private void findAgentAndSendAction(String user, CfAction action){
		//*CfAgentInterface agentToCall = findAgent(action);
		//*if (agentToCall != null){
		//*	agentToCall.processCfAction(user, action);
		//*	}
		//*else {
		//*	logger.error("[findAgentAndSendAction] agent not found for action - " + action.toString());
		//*	}
	}
	
	/*private CfAgentInterface findAgent(CfAction action){
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
	*/
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
	}

	

}
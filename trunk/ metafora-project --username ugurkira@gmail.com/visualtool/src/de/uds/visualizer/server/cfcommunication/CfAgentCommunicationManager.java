package de.uds.visualizer.server.cfcommunication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.uds.visualizer.server.utils.GeneralUtil;
import de.uds.visualizer.server.xml.XmlFragment;
import de.uds.visualizer.server.xml.XmlFragmentInterface;
import de.uds.visualizer.shared.commonformat.CfAction;

//import de.dfki.lasad.agents.instances.xmpp.CfAgentInterface;
//import de.dfki.lasad.agents.instances.xmpp.CfManagementActionAgent;
//import de.uds.util.GeneralUtil;

//used when all agents should be notified of all messages.
public class CfAgentCommunicationManager implements CfCommunicationListener{
	Log logger = LogFactory.getLog(CfAgentCommunicationManager.class);
	
	private static Map<CommunicationMethodType, Map<CommunicationChannelType, CfAgentCommunicationManager>> instanceMatrix = new Hashtable <CommunicationMethodType, Map<CommunicationChannelType, CfAgentCommunicationManager>>();	
	
	// We only maintain one instance of the communication manager, for each method and each channel.
	// All connections for a specific method and channel speak through this one instance.
	public static CfAgentCommunicationManager getInstance(CommunicationMethodType methodType, CommunicationChannelType channelType){
		
		Map<CommunicationChannelType, CfAgentCommunicationManager> channelMap = instanceMatrix.get(methodType);
		if (channelMap == null){
			channelMap = new HashMap<CommunicationChannelType, CfAgentCommunicationManager>();
			instanceMatrix.put(methodType, channelMap);
		}
		CfAgentCommunicationManager instance = channelMap.get(channelType);
		if (instance == null){
			instance = new CfAgentCommunicationManager(methodType, channelType);
			channelMap.put(channelType, instance);
		}
		return instance;
	}
	
	//default to xmpp
	public static CfAgentCommunicationManager getInstance(CommunicationChannelType channelType){
		return getInstance(CommunicationMethodType.xmpp, channelType);
	}

	
//-----------------------------Object level (non-static) -----------------//
	
	 
	 
	private Vector <CfCommunicationListener> allListeners;
	
	private CfCommunicationBridge cfCommnicationBridge;
	
	List<String> controllingUsers = new ArrayList<String>();
	boolean ignoreOldMessages = true;
	
	public CfAgentCommunicationManager(CommunicationMethodType methodType, CommunicationChannelType type){
		allListeners = new Vector<CfCommunicationListener>();
		
		if (methodType == CommunicationMethodType.xmpp){
			cfCommnicationBridge = new CfXmppCommunicationBridge(type);
			cfCommnicationBridge.registerListener(this);
		}
		else if (methodType == CommunicationMethodType.file){
			cfCommnicationBridge = new CfFileCommunicationBridge(type);
			cfCommnicationBridge.registerListener(this);
			ignoreOldMessages = false;
		}
		
		// TODO: make controlling users read from separate file
		controllingUsers.add("Metafora-test");
	}
	
	
	public void register(CfCommunicationListener agent){
		allListeners.add(agent);
	}
	
	public void unregister(CfCommunicationListener agent){
		allListeners.remove(agent);
	}
	
	@Override
	public void processCfAction(String user, CfAction action) {
		processNewMessage(user, action);
	}
	
	public void sendMessage(CfAction actionToSend){
			cfCommnicationBridge.sendAction(actionToSend);
	}
	
//---------------- private helper methods --------------------------//
	
	protected void processNewMessage(String user, CfAction action){
		if (shouldTakeActionOnMessage(action)){
			sendToAllAgents(user, action);	
		}
	}
	
	protected void sendToAllAgents(String user, CfAction action){
		for (CfCommunicationListener agent : allListeners){
			agent.processCfAction(user, action);
		}
	}
	
	protected boolean shouldTakeActionOnMessage(CfAction action) {
			if (!ignoreOldMessages){
				return true;
			}
			if (GeneralUtil.isTimeRecent(action.getTime())){
					return true;
			}
			else {
				logger.info("[shouldTakeActionOnMessage] Old action being ignored");
			}
			return false;
	}
	
	protected boolean shouldTakeActionsFromUser(String userId){
		for (String controllingUser : controllingUsers){
			if (controllingUser.equalsIgnoreCase(userId)){
				return true;
			}
		}
		return false;
	}

}
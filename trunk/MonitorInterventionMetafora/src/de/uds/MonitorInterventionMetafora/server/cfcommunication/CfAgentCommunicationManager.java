package de.uds.MonitorInterventionMetafora.server.cfcommunication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.uds.MonitorInterventionMetafora.server.monitor.MonitorController;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;

public class CfAgentCommunicationManager implements CfCommunicationListener{
	Log logger = LogFactory.getLog(CfAgentCommunicationManager.class);
	
	// A Matrix, can picture as a table with each comm method as a row, and each channel as a column
	//for example, file input row contains the comm manager for both command and analysis channel
	private static Map<CfCommunicationMethodType, Map<CommunicationChannelType, CfAgentCommunicationManager>> instanceMatrix = new Hashtable <CfCommunicationMethodType, Map<CommunicationChannelType, CfAgentCommunicationManager>>();	
	
	// We only maintain one instance of the communication manager, for each method and each channel.
	// All connections for a specific method and channel speak through this one instance.
	public static CfAgentCommunicationManager getInstance(CfCommunicationMethodType methodType, CommunicationChannelType channelType, XmppServerType xmppServerType){
		
		//convert requests to appropriate server info
		if (xmppServerType == XmppServerType.METAFORA_TEST){
			if (channelType == CommunicationChannelType.command){
				channelType = CommunicationChannelType.testCommand;
			}
			else if (channelType == CommunicationChannelType.analysis){
				channelType = CommunicationChannelType.testAnalysis;
			}
		}
		
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
		return getInstance(CfCommunicationMethodType.XMPP, channelType, XmppServerType.METAFORA_TEST);
	}

	
//-----------------------------Object level (non-static) -----------------//
	
	 
	 
	private Vector <CfCommunicationListener> allListeners;
	private CfCommunicationBridge cfCommnicationBridge;
	List<String> controllingUsers = new ArrayList<String>();
	boolean ignoreOldMessages = true;
	
	public CfAgentCommunicationManager(MonitorController directController){
		allListeners = new Vector<CfCommunicationListener>();
		cfCommnicationBridge = new DirectCommunicationBridge(directController);
		cfCommnicationBridge.registerListener(this);
	}
	
	public CfAgentCommunicationManager(CfCommunicationMethodType methodType, CommunicationChannelType type){
		allListeners = new Vector<CfCommunicationListener>();
		
		if (methodType == CfCommunicationMethodType.XMPP){
			cfCommnicationBridge = new CfXmppCommunicationBridge(type);
			cfCommnicationBridge.registerListener(this);
		}
		else {
			if (methodType != CfCommunicationMethodType.FILE){
				logger.error("Unknown CfCommunicationMethodType, defaulting to FILE");
			}
			cfCommnicationBridge = new MetaforaCfFileCommunicationBridge(type);
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
	
	//might have many connections, calls should be synced
	@Override
	public synchronized void processCfAction(String user, CfAction action) {
		processNewMessage(user, action);
	}
	
	//might have many connections, calls should be synced
	public synchronized void sendMessage(CfAction actionToSend){
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

}
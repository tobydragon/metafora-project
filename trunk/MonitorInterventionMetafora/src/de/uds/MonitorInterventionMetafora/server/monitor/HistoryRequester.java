package de.uds.MonitorInterventionMetafora.server.monitor;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfCommunicationListener;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfFileLocation;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.SimpleCfFileCommunicationBridge;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.server.utils.ErrorUtil;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaCommObjects;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;
import de.uds.MonitorInterventionMetafora.shared.utils.LogLevel;
import de.uds.MonitorInterventionMetafora.shared.utils.Logger;

public class HistoryRequester implements CfCommunicationListener{
	Logger logger = Logger.getLogger(HistoryRequester.class, LogLevel.INFO);
	
	private List<CfAction> pendingRequests;
	private MonitorModel model;
	
	public HistoryRequester(MonitorModel model){
		pendingRequests = new Vector<CfAction>();
		this.model = model;
	}
	
	public void sendHistoryRequest(CfCommunicationMethodType communicationMethodType, String currentTimeMillis, XmppServerType xmppServerType, String historyStartFilepath){
		//send extra history also loaded from a file
		if (historyStartFilepath != null){
			loadHistoryFromLocalFile(historyStartFilepath);
		}
		//then send normal history
		sendHistoryRequest(communicationMethodType, currentTimeMillis, xmppServerType);
	}
	
	public void sendHistoryRequest(CfCommunicationMethodType communicationMethodType, String currentTimeMillis, XmppServerType xmppServerType){
		if (communicationMethodType == CfCommunicationMethodType.xmpp){
			CfAgentCommunicationManager command = CfAgentCommunicationManager.getInstance(communicationMethodType, CommunicationChannelType.command, xmppServerType);
			command.register(this);
			sendXmppHistoryRequest(command, currentTimeMillis);
		}
		else if (communicationMethodType == CfCommunicationMethodType.file){
			CfAgentCommunicationManager manager= CfAgentCommunicationManager.getInstance(communicationMethodType, CommunicationChannelType.analysis, xmppServerType);
			manager.sendMessage(buildLocalFileHistoryRequest());	
		}
	}

	private CfAction buildLocalFileHistoryRequest(){
		CfAction _action=new CfAction();
	 	  _action.setTime(GWTUtils.getTimeStamp());
	 	  
	 	 CfActionType _cfActionType=new CfActionType();
	 	 _cfActionType.setType("START_FILE_INPUT");
	 	_cfActionType.setClassification("Test");
	 	_cfActionType.setSucceed("true");
	 	_cfActionType.setLogged("true");
	 	_action.setCfActionType(_cfActionType);
	 	return _action;
	}
	
	private void sendXmppHistoryRequest(CfAgentCommunicationManager communicationManager, String currentStartTimeMillis) {
		// TODO: Need to be able to "shut off" command channel listening, only listen when request is sent
		logger.info("[sendXmppHistoryRequest] start time=" + currentStartTimeMillis);
		if ( ! MetaforaStrings.CURRENT_TIME.equalsIgnoreCase(currentStartTimeMillis)){
			try {
				long time = Long.valueOf(currentStartTimeMillis);
				CfAction cfAction = createHistoryRequest(time);
				pendingRequests.add(cfAction);
			 	communicationManager.sendMessage(cfAction);
			}
			catch (Exception e){
				logger.error("[sendXmppHistoryRequest] No history request made. Probably bad value in time=" + currentStartTimeMillis + ErrorUtil.getStackTrace(e));
			}
		}
		else {
			logger.info("[sendXmppHistoryRequest] No history request made. CURRENT_TIME submitted");
		}
	}
	
	public CfAction createHistoryRequest(long startTimeMillis){
		
		CfActionType cfActionType = new CfActionType("REQUEST_ANALYSIS_HISTORY", "OTHER", "UNKNOWN");
		CfAction historyRequest = new CfAction(System.currentTimeMillis(), cfActionType);
		historyRequest.addUser(MetaforaCommObjects.MONITOR_ORGINATOR);
		historyRequest.addUser(MetaforaCommObjects.PLATFORM_RECEIVER);
		
		CfObject cfObject = new CfObject("0", "element");
		cfObject.addProperty(new CfProperty("REQUEST_ID", Long.toString(historyRequest.getTime())));
		cfObject.addProperty(new CfProperty("START_TIME", Long.toString(startTimeMillis)));
		
		historyRequest.addObject(cfObject);

		return historyRequest;
	}

	@Override
	public synchronized void processCfAction(String user, CfAction action) {
		if (!pendingRequests.isEmpty()){
			try {
				if (actionIsMyType(action)){
					logger.debug("[processCfAction] Action identified as potential response: \n" + action);
					String incomingRequestId = action.getCfObjects().get(0).getPropertyValue(MetaforaStrings.PROPERTY_TYPE_REQUEST_ID_STRING);
					CfAction myRequest = null;
					for (CfAction pendingRequest : pendingRequests){
						String pendingRequestId = pendingRequest.getCfObjects().get(0).getPropertyValue(MetaforaStrings.PROPERTY_TYPE_REQUEST_ID_STRING);
						if (pendingRequestId.equalsIgnoreCase(incomingRequestId)){
							myRequest = pendingRequest;
							break;
						}
					}
					String historyUrl = action.getCfObjects().get(0).getPropertyValue(MetaforaStrings.PROPERTY_TYPE_FILE_URL_STRING);
					if (myRequest != null){
						logger.debug("[processCfAction] Action identified as this response");
						loadHistoryFromRemoteFile(historyUrl);
						pendingRequests.remove(myRequest);
					}
					else {
						logger.info("[processCfAction] No request pending for response id=" + incomingRequestId);
					}
				}
			}
			catch (Exception e){
				logger.error("[processCfAction] Possibly bad xml in request response:\n" + action + ErrorUtil.getStackTrace(e));
			}
		}
	}

	private boolean actionIsMyType(CfAction action){
		
		if (GeneralUtil.isTimeRecent(action.getTime())){
			for (CfUser user : action.getUsersWithRole(MetaforaStrings.USER_ROLE_RECEIVER_STRING)){
				if (user.getid().equalsIgnoreCase("visualizer")){
					if (action.getCfActionType().getType().equalsIgnoreCase("REQUEST_ANALYSIS_ANSWER")){
						return true;
					}
				}
			}
			return false;
		}
		return false;
	}
	
	private void loadHistoryFromRemoteFile(String historyUrl) {
		logger.info("[loadHistoryFromRemoteFile] URL="+historyUrl);
		if (historyUrl != null && model != null){
			LabellingListener myListener = new LabellingListener(model);
			SimpleCfFileCommunicationBridge historyBridge = new SimpleCfFileCommunicationBridge(historyUrl, "", CfFileLocation.REMOTE);
			historyBridge.registerListener(myListener);
			historyBridge.sendMessages();
		}
		else {
			logger.error("[loadHistoryFromRemoteFile] called with null url or model");
		}
	}
	
	private void loadHistoryFromLocalFile(String historyUrl) {
		logger.info("[loadHistoryFromLocalFile] URL="+historyUrl);
		if (historyUrl != null && model != null){
			LabellingListener myListener = new LabellingListener(model);
			SimpleCfFileCommunicationBridge historyBridge = new SimpleCfFileCommunicationBridge(historyUrl, "", CfFileLocation.LOCAL);
			historyBridge.registerListener(myListener);
			historyBridge.sendMessages();
		}
		else {
			logger.error("[loadHistoryFromLocalFile] called with null url or model");
		}
	}
}

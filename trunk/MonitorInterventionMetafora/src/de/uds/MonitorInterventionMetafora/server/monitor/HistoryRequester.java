package de.uds.MonitorInterventionMetafora.server.monitor;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfCommunicationListener;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfFileLocation;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationMethodType;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.SimpleCfFileCommunicationBridge;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.server.utils.ErrorUtil;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaCommObjects;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;
import de.uds.MonitorInterventionMetafora.shared.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.shared.utils.Logger;

public class HistoryRequester implements CfCommunicationListener{
	Logger logger = Logger.getLogger(HistoryRequester.class);
	
	private List<CfAction> pendingRequests;
	private MonitorModel model;
	
	public HistoryRequester(MonitorModel model){
		pendingRequests = new Vector<CfAction>();
		this.model = model;
	}
	
	public void sendHistoryRequest(CommunicationMethodType communicationMethodType){
		if (communicationMethodType == CommunicationMethodType.xmpp){
			CfAgentCommunicationManager command = CfAgentCommunicationManager.getInstance(communicationMethodType, CommunicationChannelType.command);
			command.register(this);
			sendXmppHistoryRequest(command);
		}
		else if (communicationMethodType == CommunicationMethodType.file){
			sendFileHistoryRequest(CfAgentCommunicationManager.getInstance(communicationMethodType, CommunicationChannelType.analysis));
		}
	}

	private void sendFileHistoryRequest(CfAgentCommunicationManager communicationManager) {
		CfAction _action=new CfAction();
	 	  _action.setTime(GWTUtils.getTimeStamp());
	 	  
	 	 CfActionType _cfActionType=new CfActionType();
	 	 _cfActionType.setType("START_FILE_INPUT");
	 	_cfActionType.setClassification("Test");
	 	_cfActionType.setSucceed("true");
	 	_cfActionType.setLogged("true");
	 	_action.setCfActionType(_cfActionType);
	 	
	 	communicationManager.sendMessage(_action);
		
	}

	private void sendXmppHistoryRequest(CfAgentCommunicationManager communicationManager) {
		// TODO: Need to be able to "shut off" command channel listening, only listen when request is sent
		
		
		
		CfAction cfAction = createHistoryRequest();
		pendingRequests.add(cfAction);
	 	communicationManager.sendMessage(cfAction);
	}
	
	public CfAction createHistoryRequest(){
//		XmlFragment requestFragment = XmlFragment.getFragmentFromLocalFile("conffiles/xml/message/RequestHistory.xml");
//		XmlFragment requestFragment = createHistoryRequest();
//		logger.debug("[sendXmppHistoryRequest] fragment  = \n" + requestFragment);
//		if (requestFragment != null){
//			return CfActionParser.fromXml(requestFragment);
//		}
//		else {
//			logger.error("[sendXmppHistoryRequest] failed to create request xml");
//			return null;
//		}
		
		CfActionType cfActionType = new CfActionType("REQUEST_ANALYSIS_HISTORY", "OTHER", "UNKNOWN");
		CfAction historyRequest = new CfAction(System.currentTimeMillis(), cfActionType);
		historyRequest.addUser(MetaforaCommObjects.MONITOR_ORGINATOR);
		historyRequest.addUser(MetaforaCommObjects.PLATFORM_RECEIVER);
		
		CfObject cfObject = new CfObject("0", "element");
		cfObject.addProperty(new CfProperty("REQUEST_ID", Long.toString(historyRequest.getTime())));
		//TODO make a timing schema that reads from somewhere and passes it to here.
		cfObject.addProperty(new CfProperty("START_TIME", "1335363632000"));
		
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
			AnalysisMonitorListener myListener = new AnalysisMonitorListener(model);
			SimpleCfFileCommunicationBridge historyBridge = new SimpleCfFileCommunicationBridge(historyUrl, "", CfFileLocation.REMOTE);
			historyBridge.registerListener(myListener);
			historyBridge.sendMessages();
		}
		else {
			logger.error("[loadHistoryFromRemoteFile] called with null url or model");
		}
	}
}

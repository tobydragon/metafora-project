package de.uds.MonitorInterventionMetafora.server.ExternalCommunication;

import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationMethodType;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.server.utils.Logger;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

public class HistoryRequester {
	static Logger logger = Logger.getLogger(HistoryRequester.class);
	
	public static void sendHistoryRequest(CommunicationMethodType communicationMethodType){
		CfAgentCommunicationManager communicationManager = CfAgentCommunicationManager.getInstance(communicationMethodType, CommunicationChannelType.command);
		if (communicationMethodType == CommunicationMethodType.xmpp){
			sendXmppHistoryRequest(communicationManager);
		}
		else if (communicationMethodType == CommunicationMethodType.file){
			sendFileHistoryRequest(communicationManager);
		}
	}

	private static void sendFileHistoryRequest(CfAgentCommunicationManager communicationManager) {
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

	private static void sendXmppHistoryRequest(CfAgentCommunicationManager communicationManager) {
		// TODO Auto-generated method stub
		//TODO: Need to be able to "shut off" command channel listening, only listen when request is sent,  
//		CfAction _action=new CfAction();
//	 	 _action.setTime(GWTUtils.getTimeStamp());
//	 	  
//	 	 CfActionType _cfActionType=new CfActionType();
//	 	 _cfActionType.setType("REQUEST_ANALYSIS_HISTORY");
//	 	 List <>
//	 	_cfActionType.setClassification("Test");
//	 	_cfActionType.setSucceed("true");
//	 	_cfActionType.setLogged("true");
//	 	_action.setCfActionType(_cfActionType);
		
		XmlFragment requestFragment = XmlFragment.getFragmentFromLocalFile("conffiles/xml/message/RequestHistory.xml");
		logger.debug("[sendXmppHistoryRequest] fragment from file = \n" + requestFragment);
		CfAction cfAction = CfActionParser.fromXml(requestFragment);
	 	
	 	communicationManager.sendMessage(cfAction);
	}
}

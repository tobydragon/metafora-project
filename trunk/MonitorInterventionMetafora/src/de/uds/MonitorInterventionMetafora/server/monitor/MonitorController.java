package de.uds.MonitorInterventionMetafora.server.monitor;

import java.util.List;

import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;

public class MonitorController {
	
	MonitorModel monitorModel;
	HistoryRequester historyRequestor;
	
	public MonitorController(CfCommunicationMethodType communicationMethodType, String historyStartTime, XmppServerType xmppServerType) {
		
		monitorModel = new MonitorModel();
		LabellingListener monitorListener = new LabellingListener(monitorModel);
		
		HistoryRequester historyRequester = new HistoryRequester(monitorModel);
		
		CfAgentCommunicationManager analysisManager = CfAgentCommunicationManager.getInstance(communicationMethodType, CommunicationChannelType.analysis, xmppServerType);				
		analysisManager.register(monitorListener);
		
		historyRequester.sendHistoryRequest(communicationMethodType, historyStartTime, xmppServerType);
	}

	public List<CfAction> requestUpdate(CfAction cfAction) {
		return monitorModel.requestUpdate(cfAction);
	}
	
	public List<CfAction> getActionList(){
		return monitorModel.getActionList();
	}

}

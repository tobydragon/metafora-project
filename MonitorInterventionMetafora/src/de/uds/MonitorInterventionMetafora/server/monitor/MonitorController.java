package de.uds.MonitorInterventionMetafora.server.monitor;

import java.util.List;

import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;

public class MonitorController {
	
	CfAgentCommunicationManager analysisChannelManager;
	MonitorModel monitorModel;
	HistoryRequester historyRequestor;
	
	public MonitorController(CfCommunicationMethodType communicationMethodType, String historyStartTime, XmppServerType xmppServerType, String historyFilepath) {
		
		monitorModel = new MonitorModel();
		LabellingListener monitorListener = new LabellingListener(monitorModel);
		
		HistoryRequester historyRequester = new HistoryRequester(monitorModel);
		
		analysisChannelManager = CfAgentCommunicationManager.getInstance(communicationMethodType, CommunicationChannelType.analysis, xmppServerType);				
		analysisChannelManager.register(monitorListener);
		
		historyRequester.sendHistoryRequest(communicationMethodType, historyStartTime, xmppServerType, historyFilepath);
	}

	public List<CfAction> requestUpdate(CfAction cfAction) {
		return monitorModel.requestUpdate(cfAction);
	}
	
	public List<CfAction> getActionList(){
		return monitorModel.getActionList();
	}
	
	public CfAgentCommunicationManager getAnalysisChannelManager(){
		return analysisChannelManager;
	}

}

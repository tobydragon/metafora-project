package de.uds.MonitorInterventionMetafora.server.monitor;

import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationMethodType;

public class MonitorController {
	
	MonitorModel monitorModel;
	HistoryRequester historyRequestor;
	
	public MonitorController(CommunicationMethodType communicationMethodType) {
		
		monitorModel = new MonitorModel();
		AnalysisMonitorListener monitorListener = new AnalysisMonitorListener(monitorModel);
		
		HistoryRequester historyRequester = new HistoryRequester(monitorModel);
		
		CfAgentCommunicationManager analysisManager = CfAgentCommunicationManager.getInstance(communicationMethodType, CommunicationChannelType.analysis);				
		analysisManager.register(monitorListener);
		
		historyRequester.sendHistoryRequest(communicationMethodType);
	}

	public MonitorModel getModel() {
		return monitorModel;
	}

}

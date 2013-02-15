package de.uds.MonitorInterventionMetafora.server.monitor;

import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;

public class MonitorController {
	
	MonitorModel monitorModel;
	HistoryRequester historyRequestor;
	
	public MonitorController(CfCommunicationMethodType communicationMethodType, String historyStartTime) {
		
		monitorModel = new MonitorModel();
		AnalysisMonitorListener monitorListener = new AnalysisMonitorListener(monitorModel);
		
		HistoryRequester historyRequester = new HistoryRequester(monitorModel);
		
		// initialize controller
		SuggestedMessagesController.getInstance();
		
		CfAgentCommunicationManager analysisManager = CfAgentCommunicationManager.getInstance(communicationMethodType, CommunicationChannelType.analysis);				
		analysisManager.register(monitorListener);
		
		historyRequester.sendHistoryRequest(communicationMethodType, historyStartTime);
	}

	public MonitorModel getModel() {
		return monitorModel;
	}

}

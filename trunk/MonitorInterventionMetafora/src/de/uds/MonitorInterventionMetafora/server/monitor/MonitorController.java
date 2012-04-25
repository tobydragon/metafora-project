package de.uds.MonitorInterventionMetafora.server.monitor;

import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationMethodType;

public class MonitorController {
	
	MonitorModel monitorModel;
	
	public MonitorController(CommunicationMethodType communicationMethodType) {
		monitorModel = new MonitorModel();
		MonitorListener monitorListener = new MonitorListener(monitorModel);
		
		CfAgentCommunicationManager analysisManager = CfAgentCommunicationManager.getInstance(communicationMethodType, CommunicationChannelType.analysis);				
		analysisManager.register(monitorListener);
		
		CfAgentCommunicationManager commandManager = CfAgentCommunicationManager.getInstance(communicationMethodType, CommunicationChannelType.command);				
		commandManager.register(monitorListener);
		
		HistoryRequester.sendHistoryRequest(communicationMethodType);
	}

	public MonitorModel getModel() {
		return monitorModel;
	}

}

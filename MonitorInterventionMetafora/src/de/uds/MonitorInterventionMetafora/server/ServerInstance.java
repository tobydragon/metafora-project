package de.uds.MonitorInterventionMetafora.server;

import java.util.List;
import java.util.Vector;

import messages.MessagesController;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.analysis.AnalysisController;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorController;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.monitor.UpdateResponse;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;

public class ServerInstance {
	Logger logger = Logger.getLogger(this.getClass());
	
	MessagesController messagesController;
	
	MonitorController monitorController;
	//Analysis requires a Monitor
	AnalysisController analysisController;
	
		
	public ServerInstance(CfCommunicationMethodType communicationMethodType, XmppServerType xmppServerType, boolean monitoringOn, String startTime,  String historyFilepath ){
		messagesController = new MessagesController(communicationMethodType, xmppServerType);
		
		if (monitoringOn){
			monitorController = new MonitorController(communicationMethodType, startTime, xmppServerType, historyFilepath);
			analysisController = new AnalysisController(monitorController, messagesController, xmppServerType );
		}
	}
	
	public String requestSuggestedMessages(String username) {
		logger.debug("[requestSuggestedMessages]  for user: " + username );
		return messagesController.requestSuggestedMessages(username);

	}
	
	public void sendMessage( CfAction cfAction) {
		messagesController.sendMessage(cfAction);
	}

	public void sendSuggestedMessages( CfAction cfAction) {
		messagesController.sendSuggestedMessages(cfAction);
	}
	
	public UpdateResponse requestUpdate(CfAction cfAction) {
		if (monitorController != null){
			logger.info("[requestUpdate]  requesting update is received  by the server");
			List<CfAction> actionUpdates = monitorController.requestUpdate(cfAction);
			List<String> involvedGroups = AnalysisController.getInvolvedGroups(actionUpdates);
			
			return new UpdateResponse(actionUpdates, involvedGroups);
		}
		else {
			logger.warn("[requestUpdate]  requesting update is recieved by a server set to not monitor");
			return new UpdateResponse();
		}
	}

	public void requestAnalysis(String groupId, Locale locale) {
		if (analysisController != null){
			analysisController.analyzeGroup(groupId, locale);
		}
	}


}

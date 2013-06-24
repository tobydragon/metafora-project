package de.uds.MonitorInterventionMetafora.server;

import java.util.List;
import java.util.Vector;


import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.analysis.AnalysisController;
import de.uds.MonitorInterventionMetafora.server.analysis.AnalyzingListener;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfCommunicationListener;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.server.messages.MessagesController;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorController;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorModel;
import de.uds.MonitorInterventionMetafora.shared.analysis.AnalysisActions;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.PropertyLocation;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.monitor.UpdateResponse;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
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
			MonitorModel monitorModel = new MonitorModel();
			CfCommunicationListener monitorListener = new AnalyzingListener(monitorModel, messagesController);

			monitorController = new MonitorController(monitorModel, monitorListener, communicationMethodType, startTime, xmppServerType, historyFilepath);
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
			logger.info("[requestUpdate] " + actionUpdates.size() + " actions retrieved, start checking groups");
			List<String> involvedGroups = AnalysisActions.getInvolvedGroups(actionUpdates);
			List<CfAction> filteredActions = getOverallActionFilter().getFilteredList(actionUpdates);
			logger.info("[requestUpdate] " +involvedGroups.size() + " groups found, "+ filteredActions.size()+" filtered actions being sent as Response");

			return new UpdateResponse(filteredActions, involvedGroups);
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

	public void requestClearAllRecommendations() {
		messagesController.requestClearAllSuggestedMessages();
		
	}
	
	private ActionFilter getOverallActionFilter(){
		List <ActionPropertyRule> afterRules = new Vector<ActionPropertyRule>();
//		afterRules.add (new ActionPropertyRule("type", "LANDMARK", PropertyLocation.ACTION_TYPE, OperationType.EQUALS));
//		afterRules.add (new ActionPropertyRule("SENDING_TOOL", MetaforaStrings.ANAYLSIS_MANAGER, PropertyLocation.CONTENT, OperationType.EQUALS));
		ActionFilter afterFilter = new ActionFilter("Landmarks", true, afterRules);
		return afterFilter; 
	}


}

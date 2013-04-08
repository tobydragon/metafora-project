package de.uds.MonitorInterventionMetafora.server;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.analysis.manager.AnalysisManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.server.feedback.FeedbackController;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorController;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorModel;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;

public class ServerInstance {
	Logger logger = Logger.getLogger(this.getClass());
	
	MonitorController monitorController;
	FeedbackController feedbackController;
	
		
	public ServerInstance(CfCommunicationMethodType communicationMethodType, XmppServerType xmppServerType, boolean monitoringOn, String startTime ){
		if (monitoringOn){
			monitorController = new MonitorController(communicationMethodType, startTime, xmppServerType);
		}
		
		feedbackController = new FeedbackController(communicationMethodType, xmppServerType);
	}
	
	public String requestSuggestedMessages(String username) {
		logger.info("[requestSuggestedMessages]  for user: " + username );
		return feedbackController.requestSuggestedMessages(username);

	}
	
	public CfAction sendAction(String _user, CfAction cfAction) {
		feedbackController.sendAction(_user, cfAction);
		return null;
	}

	
	public List<CfAction> requestUpdate(CfAction cfAction) {
		if (monitorController != null){
			logger.info("[requestUpdate]  requesting update is received  by the server");
			return monitorController.requestUpdate(cfAction);
		}
		else {
			logger.warn("[requestUpdate]  requesting update is revieced by a server set to not monitor");
			return new Vector<CfAction>();
		}
	}

	void sendUpdateRequest(CfAction action) {	}

	public CfAction sendNotificationToAgents(CfAction cfAction) {

		AnalysisManager.getAnalysisManagerInstance().sendToAllAgents(
				"Notification", cfAction);

		System.out.println("Notifications are sent to the agents!!");
		return new CfAction();
	}


}

package de.uds.MonitorInterventionMetafora.server;

import java.util.List;
import java.util.Vector;

import messages.MessagesController;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.analysis.AnalysisController;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorController;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;

public class ServerInstance {
	Logger logger = Logger.getLogger(this.getClass());
	
	MessagesController feedbackController;
	
	MonitorController monitorController;
	//Analysis requires a Monitor
	AnalysisController analysisController;
		
	public ServerInstance(CfCommunicationMethodType communicationMethodType, XmppServerType xmppServerType, boolean monitoringOn, String startTime ){
		feedbackController = new MessagesController(communicationMethodType, xmppServerType);
		
		if (monitoringOn){
			monitorController = new MonitorController(communicationMethodType, startTime, xmppServerType);
			analysisController = new AnalysisController(monitorController, feedbackController, xmppServerType );
		}
		
	}
	
	public String requestSuggestedMessages(String username) {
		logger.debug("[requestSuggestedMessages]  for user: " + username );
		return feedbackController.requestSuggestedMessages(username);

	}
	
	public void sendAction(String _user, CfAction cfAction) {
		feedbackController.sendAction(_user, cfAction);
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

	public void requestAnalysis(String groupId, Locale locale) {
		if (analysisController != null){
			analysisController.analyzeGroup(groupId, locale);
		}
	}


}

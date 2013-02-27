package de.uds.MonitorInterventionMetafora.server.feedback;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;

public class FeedbackController { //implements CfCommunicationListener
	Logger logger = Logger.getLogger(this.getClass());
	
	CfAgentCommunicationManager feedbackCommunicationManager;
	//FeedbackModel
	
	public FeedbackController(CfCommunicationMethodType communicationMethodType){
		feedbackCommunicationManager = CfAgentCommunicationManager.getInstance(
				communicationMethodType, CommunicationChannelType.command);
	}
	
	public CfAction sendAction(String _user, CfAction cfAction) {
		logger.debug("action = \n" + CfActionParser.toXml(cfAction));
		feedbackCommunicationManager.sendMessage(cfAction);
		// TODO: should be void, not have a callback...
		return null;
	}

	
}

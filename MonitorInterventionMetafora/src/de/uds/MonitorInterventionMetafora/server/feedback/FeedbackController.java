package de.uds.MonitorInterventionMetafora.server.feedback;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfCommunicationListener;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;

public class FeedbackController implements CfCommunicationListener {
	Logger logger = Logger.getLogger(this.getClass());
	
	CfAgentCommunicationManager feedbackCommunicationManager;
	FeedbackModel feedbackModel;
	
	public FeedbackController(CfCommunicationMethodType communicationMethodType, XmppServerType xmppServerType){
		feedbackCommunicationManager = CfAgentCommunicationManager.getInstance(communicationMethodType, CommunicationChannelType.command, xmppServerType);
		feedbackModel = new FeedbackModel();
		
		feedbackCommunicationManager.register(this);
	}
	
	public CfAction sendAction(String _user, CfAction cfAction) {
		logger.debug("action = \n" + CfActionParser.toXml(cfAction));
		feedbackCommunicationManager.sendMessage(cfAction);
		// TODO: should be void, not have a callback...
		return null;
	}

	@Override
	public synchronized void processCfAction(String user, CfAction action) {
		logger.info("");
		if (action.getCfActionType().getType().equals(MetaforaStrings.ACTION_TYPE_SUGGESTED_MESSAGES_STRING)) {
			String messagesXml = action.getCfContent().getDescription();
			for (CfUser cfUser : action.getUsersWithRole("receiver")) {
				feedbackModel.updateSuggestedMessages(cfUser.getid(), messagesXml);
			}
		}
	}

	public String requestSuggestedMessages(String username) {
		return feedbackModel.getSuggestedMessages(username);
	}
}

package messages;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfCommunicationListener;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.server.mmftparser.SuggestedMessagesModelParserForServer;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.InterventionCreator;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesFileHandler;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.MessageType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesFileTextReceiver;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesModel;

public class MessagesController implements CfCommunicationListener {
	Logger logger = Logger.getLogger(this.getClass());
	
	CfAgentCommunicationManager commandChannelCommunicationManager;
	SuggestedMessages4AllUsersModel suggestedMessagesModel;
	
	//purely to send landmarks when messages are sent
	CfAgentCommunicationManager analysisChannelManager;
	
	XmppServerType xmppServerType;

	
	public MessagesController(CfCommunicationMethodType communicationMethodType, XmppServerType xmppServerType){
		this.xmppServerType = xmppServerType;
		commandChannelCommunicationManager = CfAgentCommunicationManager.getInstance(communicationMethodType, CommunicationChannelType.command, xmppServerType);
		suggestedMessagesModel = new SuggestedMessages4AllUsersModel();
		commandChannelCommunicationManager.register(this);
		initializeDefaultMessageModel();
		
		//purely to send landmarks when messages are sent
		analysisChannelManager = CfAgentCommunicationManager.getInstance(communicationMethodType, CommunicationChannelType.analysis, xmppServerType);				
	}
		
	public void	initializeDefaultMessageModel(){
		for (MessageType messageType : MessageType.values()){
			for (Locale locale : Locale.values()){
				String path = GeneralUtil.getRealPath(SuggestedMessagesFileHandler.getPartialFilepath(messageType, locale));
				logger.info("[initializeDefaultMessageModel] reading model from path: " + path);
				XmlFragment messagesFragment = XmlFragment.getFragmentFromLocalFile(path);
				if (messagesFragment != null){
					SuggestedMessagesModel defaultMessagesModel = SuggestedMessagesModelParserForServer.fromXml(messagesFragment);
					suggestedMessagesModel.updateDefaultMessagesModel(messageType, locale, defaultMessagesModel);
				}
			}
		}
	}
	
	public String requestSuggestedMessages(String username) {
		return suggestedMessagesModel.getSuggestedMessages(username);
	}
	
	public SuggestedMessagesModel getCopyOfDefaultMessages(Locale locale, MessageType messageType){
		return suggestedMessagesModel.getCopyOfDefaultMessages(messageType, locale);
	}
	
	public void sendMessage( CfAction cfAction){
		//Sending a message creates a landmark
		CfAction landmark = InterventionCreator.createLandmarkForOutgoingMessage(cfAction);
		logger.debug("[sendMessage] Sending Landmark = \n" + CfActionParser.toXml(landmark));
		analysisChannelManager.sendMessage(landmark);
		sendAction(cfAction);
	}
	
	public void sendSuggestedMessages(CfAction cfAction){
		sendAction(cfAction);
		//Send low interruption message to tell user that tips are available
		CfAction notificationMessage = InterventionCreator.createNewSuggestedMessagesNotification(xmppServerType.toString(), cfAction);
		if (notificationMessage != null){
			logger.debug("[sendSuggestedMessages] Sending Notification Message = \n" + CfActionParser.toXml(notificationMessage));
			sendAction (notificationMessage);
		}
	}
	
	private void sendAction( CfAction cfAction) {
		logger.debug("[sendAction] Sending Action = \n" + CfActionParser.toXml(cfAction));
		commandChannelCommunicationManager.sendMessage(cfAction);
	}	
	
	@Override
	public synchronized void processCfAction(String user, CfAction action) {
		logger.info("");
		if (action.getCfActionType().getType().equals(MetaforaStrings.ACTION_TYPE_SUGGESTED_MESSAGES_STRING)) {
			String messagesXml = action.getCfContent().getDescription();
			if (MetaforaStrings.CLEAR_ALL_SUGGESTED_MESSAGES.equalsIgnoreCase(messagesXml)){
				suggestedMessagesModel.clearAllSuggestedMessages();
			}
			for (CfUser cfUser : action.getUsersWithRole("receiver")) {
				suggestedMessagesModel.updateSuggestedMessages(cfUser.getid(), messagesXml);
			}
		}
	}

	public void requestClearAllSuggestedMessages() {
		sendAction(InterventionCreator.createClearAllSuggestedMessages());
	}
	
}

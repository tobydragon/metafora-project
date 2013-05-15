package messages;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfCommunicationListener;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesFileHandler;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.MessageType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesFileTextReceiver;

public class MessagesController implements CfCommunicationListener {
	Logger logger = Logger.getLogger(this.getClass());
	
	CfAgentCommunicationManager commandChannelCommunicationManager;
	SuggestedMessagesModel suggestedMessagesModel;
	
	public MessagesController(CfCommunicationMethodType communicationMethodType, XmppServerType xmppServerType){
		commandChannelCommunicationManager = CfAgentCommunicationManager.getInstance(communicationMethodType, CommunicationChannelType.command, xmppServerType);
		suggestedMessagesModel = new SuggestedMessagesModel();
		commandChannelCommunicationManager.register(this);
		initializeDefaultMessageModel();
	}
		
	public void	initializeDefaultMessageModel(){
		for (MessageType messageType : MessageType.values()){
			for (Locale locale : Locale.values()){
				String path = GeneralUtil.getRealPath(SuggestedMessagesFileHandler.getPartialFilepath(messageType, locale));
				String fileContents = GeneralUtil.readFileToString(path);
				suggestedMessagesModel.updateDefaultMessagesModel(messageType, locale, fileContents);
			}
		}
	}
	
	public String requestSuggestedMessages(String username) {
		return suggestedMessagesModel.getSuggestedMessages(username);
	}
	
	public String getDefaultMessages(Locale locale, MessageType messageType){
		return suggestedMessagesModel.getDefaultMessages(messageType, locale);
	}
	
	public void sendAction(String _user, CfAction cfAction) {
		logger.debug("action = \n" + CfActionParser.toXml(cfAction));
		commandChannelCommunicationManager.sendMessage(cfAction);
	}	
	
	@Override
	public synchronized void processCfAction(String user, CfAction action) {
		logger.info("");
		if (action.getCfActionType().getType().equals(MetaforaStrings.ACTION_TYPE_SUGGESTED_MESSAGES_STRING)) {
			String messagesXml = action.getCfContent().getDescription();
			for (CfUser cfUser : action.getUsersWithRole("receiver")) {
				suggestedMessagesModel.updateSuggestedMessages(cfUser.getid(), messagesXml);
			}
		}
	}
	
}

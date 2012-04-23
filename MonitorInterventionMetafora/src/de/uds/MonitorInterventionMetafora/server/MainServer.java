package de.uds.MonitorInterventionMetafora.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationService;
import de.uds.MonitorInterventionMetafora.server.analysis.manager.AnalysisManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationMethodType;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorController;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorModel;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.server.utils.Logger;
import de.uds.MonitorInterventionMetafora.server.xml.XmlConfigParser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MainServer extends RemoteServiceServlet implements CommunicationService {
	Logger logger = Logger.getLogger(this.getClass());

	String configFilepath = GeneralUtil.getAplicationResourceDirectory()+"conffiles/toolconf/configuration.xml";
	public Configuration _configuration;
	
	MonitorModel monitorModel;
	MonitorController monitorController;
	AnalysisManager analysisManager;
	
	CfAgentCommunicationManager feedbackCommunicationManager;
	
	public MainServer(){		
		//the only thing we're using from config here is the action source, xmpp or file
		XmlConfigParser connectionParser = new XmlConfigParser(configFilepath);
		_configuration=connectionParser.toActiveConfiguration();
		String communicationMethod = _configuration.getActionSource(); 
		CommunicationMethodType communicationMethodType = CommunicationMethodType.valueOf(communicationMethod);
		
		monitorController = new MonitorController(communicationMethodType);
		monitorModel = monitorController.getModel();
		
		feedbackCommunicationManager = CfAgentCommunicationManager.getInstance(communicationMethodType, CommunicationChannelType.analysis);							
	}

	@Override
	public CfAction sendAction(String _user,CfAction cfAction) {
		System.out.println("INFO: [MainServer.sendAction] action = \n" + CfActionParser.toXml(cfAction));
		feedbackCommunicationManager.sendMessage(cfAction);
		//TODO: should be void, not have a callback...
		return null;
	}

	@Override
	public Configuration sendRequestConfiguration(String _user,CfAction cfAction) {
		//TODO: should take no params, if not used... What is _user?
		logger.info("sendRequestConfiguration]");
		return _configuration;
	}

	@Override
	public List<CfAction> sendRequestHistoryAction(String _user,CfAction cfAction) {
		return monitorModel.requestUpdate(null);
	}

	@Override
	public List<CfAction> requestUpdate(CfAction cfAction) {
		return monitorModel.requestUpdate(cfAction);
	}

	@Override
	public CfAction sendNotificationToAgents(CfAction cfAction) {
	
		AnalysisManager.getAnalysisManagerInstance().sendToAllAgents("Notification",cfAction);
	
		System.out.println("Notifications are send to the agentss!!");
		return new CfAction();
	}
	
}

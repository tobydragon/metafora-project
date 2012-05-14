package de.uds.MonitorInterventionMetafora.server;

import java.util.List;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.uds.MonitorInterventionMetafora.client.communication.CommunicationService;
import de.uds.MonitorInterventionMetafora.server.analysis.manager.AnalysisManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CommunicationChannelType;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorController;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorModel;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.server.xml.XmlConfigParser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import org.apache.log4j.Logger;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MainServer extends RemoteServiceServlet implements CommunicationService {
	Logger logger = Logger.getLogger(this.getClass());

	public Configuration configuration;
	
	MonitorModel monitorModel;
	MonitorController monitorController;
	AnalysisManager analysisManager;
	
	CfAgentCommunicationManager feedbackCommunicationManager;
	
	public MainServer(){	
		super();
	
		configuration = readConfiguration();
		CfCommunicationMethodType communicationMethodType = configuration.getDataSouceType();
		
		monitorController = new MonitorController(communicationMethodType, configuration.getHistoryStartTime());
		monitorModel = monitorController.getModel();
		
		
		feedbackCommunicationManager = CfAgentCommunicationManager.getInstance(communicationMethodType, CommunicationChannelType.command);							
	}
	
	private Configuration readConfiguration(){
		String configFilepath = GeneralUtil.getRealPath("conffiles/toolconf/configuration.xml");

		XmlConfigParser connectionParser = new XmlConfigParser(configFilepath);
		Configuration configuration =connectionParser.toActiveConfiguration();
		return configuration;
	}

	@Override
	public CfAction sendAction(String _user,CfAction cfAction) {
		logger.debug("action = \n" + CfActionParser.toXml(cfAction));
		feedbackCommunicationManager.sendMessage(cfAction);
		//TODO: should be void, not have a callback...
		return null;
	}

	@Override
	public Configuration requestConfiguration(CfAction cfAction) {
		//TODO: should take no params, if not used... What is _user?
		logger.info("sendRequestConfiguration]");
		return configuration;
	}
	
	@Override
	public List<CfAction> requestUpdate(CfAction cfAction) {
		
	
		return monitorModel.requestUpdate(cfAction);
	}

	@Override
	public CfAction sendNotificationToAgents(CfAction cfAction) {
	
		AnalysisManager.getAnalysisManagerInstance().sendToAllAgents("Notification",cfAction);
	
		System.out.println("Notifications are send to the agents!!");
		return new CfAction();
	}

	@Override
	public String sendLogAction(CfAction logAction) {
		System.out.println("1*********************************************");
		System.out.println("Log Action:"+logAction.toString());
		System.out.println("2*********************************************");
		
		return null;
	}
	
	 
}

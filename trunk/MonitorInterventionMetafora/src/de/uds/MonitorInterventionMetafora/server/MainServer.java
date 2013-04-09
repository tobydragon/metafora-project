package de.uds.MonitorInterventionMetafora.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationService;
import de.uds.MonitorInterventionMetafora.server.analysis.manager.AnalysisManager;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.server.feedback.FeedbackController;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorController;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorModel;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.server.xml.XmlConfigParser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MainServer extends RemoteServiceServlet implements CommunicationService {
	Logger logger = Logger.getLogger(this.getClass());

	static String generalConfigFile = "conffiles/toolconf/configuration.xml";
	static String mainFiltersFile = "conffiles/toolconf/mainfilters.xml";
	static String logDir="useractionlogs/";
	private Configuration generalConfiguration;
	private XmlConfigParser configuratinParser;
	
	ServerInstance mainServer; 
	ServerInstance testServer;
	
	AnalysisManager analysisManager;
	private boolean isConfigurationUpdated = false;

	public MainServer() {
		super();

		generalConfiguration = readConfiguration(false);

		CfCommunicationMethodType communicationMethodType = generalConfiguration.getDataSouceType();
		mainServer = new ServerInstance(communicationMethodType, XmppServerType.DEPLOY, generalConfiguration.isDeployServerMonitoring(), generalConfiguration.getHistoryStartTime() );
		testServer = new ServerInstance(communicationMethodType, XmppServerType.TEST, generalConfiguration.isTestServerMonitoring(), generalConfiguration.getHistoryStartTime() );		
		
	}

	private synchronized Configuration readConfiguration(boolean isMainConfig) {
		String configFilepath = "";
		if (isMainConfig) {
			configFilepath = GeneralUtil.getRealPath(mainFiltersFile);
		} else {
			configFilepath = GeneralUtil.getRealPath(generalConfigFile);
		}
		XmlConfigParser configuratinParser = new XmlConfigParser(configFilepath);
		
		
		Configuration configuration = configuratinParser.toActiveConfiguration();
		return configuration;
	}
	
	@Override
	public Configuration requestConfiguration() {
		logger.info("[requestConfiguration]");
		if (isConfigurationUpdated) {
			generalConfiguration = readConfiguration(false);
			isConfigurationUpdated = false;

			return generalConfiguration;
		}

		else {
			return generalConfiguration;
		}

	}
	
	@Override
	public String sendLogAction(CfAction logAction) {
		//System.out.println("LOG_ACTION_START");
		String actionString=CfActionParser.toXml(logAction).toString();
		//System.out.println(actionString);
		try {
			toLogFile(actionString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("LOG_ACTION_END");
		return null;
	}

	@Override
	public boolean saveNewFilter(boolean isMainFilters, ActionFilter filter) {
		if (!isMainFilters) {
			isConfigurationUpdated = true;
			configuratinParser = new XmlConfigParser(
					GeneralUtil.getRealPath(generalConfigFile));

			return configuratinParser.saveNewFilterToConfiguration(filter);
		} else {
			configuratinParser = new XmlConfigParser(
					GeneralUtil.getRealPath(mainFiltersFile));
			return configuratinParser.saveNewFilterToConfiguration(filter);
		}

	}

	@Override
	public String removeNewFilter(String filterName) {
		generalConfiguration.removeFilter(filterName);
		return "";
	}

	@Override
	public Configuration requestMainConfiguration() {

		return readConfiguration(true);
	}

	synchronized void toLogFile(String logAction) throws IOException {
		String fileName =logDir+GeneralUtil.getCurrentDate()+".txt";

		fileName=GeneralUtil.getRealPath(fileName);
		File file = new File(fileName);
		System.out.println("File:"+fileName);
		if (!file.exists()) {
			logger.info("[LogFile] File does not exist. Creating file:" + fileName);
			file.createNewFile();
		}

		FileWriter fileWriter = new FileWriter(file, true);
		BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
		bufferWriter.append(logAction);
	
		bufferWriter.close();
		fileWriter.close();
		logger.info("[LogFile] Log is written to file.File:" + fileName);

	}
	
	//-------  Remote methods handled by serverInstances  ------------------------
	
	@Override
	public String requestSuggestedMessages(String username) {
		logger.info("[requestSuggestedMessages]  for user: " + username );
		return requestSuggestedMessages(generalConfiguration.getDefaultXmppServer(), username);
	}
	
	public String requestSuggestedMessages(XmppServerType xmppServerType, String username) {
		logger.info("[requestSuggestedMessages]  for user: " + username );
		if (xmppServerType == XmppServerType.DEPLOY){
			 return mainServer.requestSuggestedMessages(username);
		}
		else {
			return testServer.requestSuggestedMessages(username);
		}		
	}

	@Override
	public CfAction sendAction(String _user, CfAction cfAction) {
		sendAction(generalConfiguration.getDefaultXmppServer(), _user, cfAction);
		return null;
	}
	
	@Override
	public void sendAction(XmppServerType serverType, String user, CfAction action) {
		if (serverType == XmppServerType.DEPLOY){
			mainServer.sendAction(user, action);
		}
		else {
			testServer.sendAction(user, action);
		}
	}


	@Override
	public List<CfAction> requestUpdate(CfAction cfAction) {
		logger.info("[requestUpdate]  requesting update is revieced  by the server");
		return requestUpdate(generalConfiguration.getDefaultXmppServer(), cfAction);
	}
	
	public List<CfAction> requestUpdate(XmppServerType xmppServerType, CfAction cfAction) {
		logger.info("[requestUpdate]  requesting update is revieced  by the server");
		if (xmppServerType == XmppServerType.DEPLOY){
			 return mainServer.requestUpdate(cfAction);
		}
		else {
			 return testServer.requestUpdate(cfAction);
		}
	}

	void sendUpdateRequest(CfAction action) {	}

	@Override
	public CfAction sendNotificationToAgents(CfAction cfAction) {

		AnalysisManager.getAnalysisManagerInstance().sendToAllAgents(
				"Notification", cfAction);

		System.out.println("Notifications are sent to the agents!!");
		return new CfAction();
	}


}

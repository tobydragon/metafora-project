package de.uds.MonitorInterventionMetafora.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Vector;

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
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;

import org.apache.log4j.Logger;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MainServer extends RemoteServiceServlet implements
		CommunicationService {
	Logger logger = Logger.getLogger(this.getClass());

	static String generalConfigFile = "conffiles/toolconf/configuration.xml";
	static String mainFiltersFile = "conffiles/toolconf/mainfilters.xml";
	static String logDir="useractionlogs/";
	private Configuration generalConfiguration;
	private XmlConfigParser configuratinParser;
	MonitorModel monitorModel;
	MonitorController monitorController;
	AnalysisManager analysisManager;
	private boolean isConfigurationUpdated = false;

	CfAgentCommunicationManager feedbackCommunicationManager;

	public MainServer() {
		super();

		generalConfiguration = readConfiguration(false);

		CfCommunicationMethodType communicationMethodType = generalConfiguration
				.getDataSouceType();

		monitorController = new MonitorController(communicationMethodType,
				generalConfiguration.getHistoryStartTime());
		monitorModel = monitorController.getModel();

		feedbackCommunicationManager = CfAgentCommunicationManager.getInstance(
				communicationMethodType, CommunicationChannelType.command);
	}

	private synchronized Configuration readConfiguration(boolean isMainConfig) {
		String configFilepath = "";
		if (isMainConfig) {
			configFilepath = GeneralUtil.getRealPath(mainFiltersFile);
		} else {
			configFilepath = GeneralUtil.getRealPath(generalConfigFile);
		}
		XmlConfigParser configuratinParser = new XmlConfigParser(configFilepath);
		Configuration configuration = configuratinParser
				.toActiveConfiguration();
		return configuration;
	}

	@Override
	public CfAction sendAction(String _user, CfAction cfAction) {
		logger.debug("action = \n" + CfActionParser.toXml(cfAction));
		feedbackCommunicationManager.sendMessage(cfAction);
		// TODO: should be void, not have a callback...
		return null;
	}

	@Override
	public Configuration requestConfiguration() {
		// TODO: should take no params, if not used... What is _user?
		logger.info("sendRequestConfiguration]");
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
	public List<CfAction> requestUpdate(CfAction cfAction) {

		logger.info("[requestUpdate]  requesting update is revieced  by the server");

		return monitorModel.requestUpdate(cfAction);

	}

	void sendUpdateRequest(CfAction action) {

	}

	@Override
	public CfAction sendNotificationToAgents(CfAction cfAction) {

		AnalysisManager.getAnalysisManagerInstance().sendToAllAgents(
				"Notification", cfAction);

		System.out.println("Notifications are sent to the agents!!");
		return new CfAction();
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

	synchronized void  toLogFile(String logAction) throws IOException {

		String fileName =logDir+GeneralUtil.getCurrentDate()+".txt";

		fileName=GeneralUtil.getRealPath(fileName);
		File file = new File(fileName);
		System.out.println("File:"+fileName);
		if (!file.exists()) {
			logger.info("[LogFile] File does not exist.Creating file:"
					+ fileName);
			file.createNewFile();
		}

		
	
		
		FileWriter fileWritter = new FileWriter(file.getName(), true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.append(logAction);
	
	
		bufferWritter.close();
		logger.info("[LogFile] Log is written to file.File:" + fileName);
		
		
		

	}

}

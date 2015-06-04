package de.uds.MonitorInterventionMetafora.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationService;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfInteractionDataParser;
import de.uds.MonitorInterventionMetafora.server.utils.ErrorUtil;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.server.xml.XmlConfigParser;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;
import de.uds.MonitorInterventionMetafora.shared.monitor.UpdateResponse;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MainServer extends RemoteServiceServlet implements CommunicationService {
	Logger logger = Logger.getLogger(this.getClass());

	static String generalConfigFile = "conffiles/toolconf/configuration.xml";
	static String mainFiltersFile = "conffiles/toolconf/mainfilters.xml";
	static String startupDataFile = "conffiles/xml/test/300-testTriggerFeedback.xml";
	static String logDir="useractionlogs/";
	private Configuration generalConfiguration;
	private XmlConfigParser configuratinParser;
	
	ServerInstance mainServer; 
	ServerInstance testServer;
	
	private boolean isConfigurationUpdated = false;

	public MainServer() {
		super();

		generalConfiguration = readConfiguration(false);

		CfCommunicationMethodType communicationMethodType = generalConfiguration.getDataSouceType();
		mainServer = new ServerInstance(communicationMethodType, XmppServerType.METAFORA, generalConfiguration.isDeployServerMonitoring(),
				generalConfiguration.getHistoryStartTime(), null );
		testServer = new ServerInstance(communicationMethodType, XmppServerType.METAFORA_TEST, generalConfiguration.isTestServerMonitoring(), 
				generalConfiguration.getHistoryStartTime(), GeneralUtil.getRealPath(startupDataFile));		
		
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
		}
		return generalConfiguration;
	}
	
	@Override
	public void logAction(CfAction logAction) {
		String actionString=CfActionParser.toXml(logAction).toString();
		try {
			toLogFile(actionString);
		} catch (IOException e) {
			logger.error("[logAction] problem writing log: " + ErrorUtil.getStackTrace(e));
		}
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
		return requestSuggestedMessages(generalConfiguration.getDefaultXmppServer(), username);
	}
	
	public String requestSuggestedMessages(XmppServerType xmppServerType, String username) {
		logger.info("[requestSuggestedMessages]  for user: " + username + "- Server: " + xmppServerType);
		if (xmppServerType == XmppServerType.METAFORA){
			 return mainServer.requestSuggestedMessages(username);
		}
		else {
			return testServer.requestSuggestedMessages(username);
		}		
	}

	@Override
	public void sendMessage( CfAction cfAction) {
		sendMessage(generalConfiguration.getDefaultXmppServer(), cfAction);
	}
	
	@Override
	public void sendMessage(XmppServerType serverType, CfAction action) {
		if (serverType == XmppServerType.METAFORA){
			mainServer.sendMessage(action);
		}
		else {
			testServer.sendMessage(action);
		}
	}
	
	@Override
	public void sendSuggestedMessages( CfAction cfAction) {
		sendMessage(generalConfiguration.getDefaultXmppServer(), cfAction);
	}
	
	@Override
	public void sendSuggestedMessages(XmppServerType serverType, CfAction action) {
		if (serverType == XmppServerType.METAFORA){
			mainServer.sendSuggestedMessages(action);
		}
		else {
			testServer.sendSuggestedMessages(action);
		}
	}
	

	@Override
	public UpdateResponse requestUpdate(CfAction cfAction) {
		logger.info("[requestUpdate]  requesting update is received  by the server");
		return requestUpdate(generalConfiguration.getDefaultXmppServer(), cfAction);
	}
	
	@Override
	public UpdateResponse requestUpdate(XmppServerType xmppServerType, CfAction cfAction) {
		logger.info("[requestUpdate]  requesting update is received  by the server");
		if (xmppServerType == XmppServerType.METAFORA){
			 return mainServer.requestUpdate(cfAction);
		}
		else {
			 return testServer.requestUpdate(cfAction);
		}
	}

	@Override
	public void requestAnalysis(String groupId, Locale locale) {
		requestAnalysis(generalConfiguration.getDefaultXmppServer(), groupId, locale);
	}
	
	@Override
	public void requestAnalysis(XmppServerType xmppServerType, String groupId, Locale locale) {
		logger.info("[requesAnalysis]  requesting analysis for group ID:" + groupId);
		if (xmppServerType == XmppServerType.METAFORA){
			 mainServer.requestAnalysis(groupId, locale);
		}
		else {
			 testServer.requestAnalysis(groupId, locale);
		}
	}

	@Override
	public void requestClearAllAnalysis() {
		requestClearAllAnalysis(generalConfiguration.getDefaultXmppServer());		
	}

	@Override
	public void requestClearAllAnalysis(XmppServerType xmppServerType) {
		logger.info("[requesAnalysis]  Clearing all analysis.");
		if (xmppServerType == XmppServerType.METAFORA){
			 mainServer.requestClearAllRecommendations();
		}
		else {
			 testServer.requestClearAllRecommendations();
		}
		
	}
	public UpdateResponse requestDataFromFile(String filename){
		return requestDataFromFile(generalConfiguration.getDefaultXmppServer(), filename);
	}
	
	public UpdateResponse requestDataFromFile(XmppServerType xmppServerType, String filename){
		String path = GeneralUtil.getRealPath("upload/"+filename);
		XmlFragment xmlFrag = XmlFragment.getFragmentFromLocalFile(path);
		
		List<CfAction> cfActions = new ArrayList<CfAction>();
		for (XmlFragment cfActionElement : xmlFrag.getChildren(RunestoneStrings.ROW_STRING)){
			cfActions.add(CfActionParser.fromRunsetoneXml(cfActionElement));
		}
		logger.info("requestDataFromFile:\t\t read " +cfActions.size() + " indicators from "+ path);		 
		
		if (xmppServerType == XmppServerType.METAFORA){
			 mainServer.replaceAllMonitorActions(cfActions);
		}
		else {
			 testServer.replaceAllMonitorActions(cfActions);
		}
		
		return new UpdateResponse(cfActions, null);
		
	}
	
}

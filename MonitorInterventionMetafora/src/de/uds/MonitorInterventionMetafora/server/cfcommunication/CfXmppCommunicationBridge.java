package de.uds.MonitorInterventionMetafora.server.cfcommunication;

import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import de.kuei.metafora.xmpp.XMPPBridge;
import de.kuei.metafora.xmpp.XMPPBridgeCurrent;
import de.kuei.metafora.xmpp.XMPPMessageListener;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.server.utils.ErrorUtil;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.server.xml.XmlConfigParser;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.server.xml.XmlUtil;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;


public class CfXmppCommunicationBridge implements CfCommunicationBridge, XMPPMessageListener{
	static Logger logger = Logger.getLogger(CfXmppCommunicationBridge.class);
	
	private static String commandConnectionName = null;
	private static String analysisConnectionName = null;
	private static String server = null;
	
	private static String testCommandConnectionName = null;
	private static String testAnalysisConnectionName = null;
	private static String testServer = null;
	
	static {
		//config file location
		String instanceConfigFilepath = GeneralUtil.getRealPath("conffiles/xmpp/xmpp-settings.xml");
		XmlConfigParser instanceParser = new XmlConfigParser(instanceConfigFilepath);
		
		server = instanceParser.getConfigValue("server");
		if(server !=null){
			XMPPBridgeCurrent.setServer(server);	
		}
		else {
			logger.warn("server not defined in config file, not setting XMPPBridgeCurrent.setServer");
		}
		commandConnectionName = createConnection(CommunicationChannelType.command, instanceParser);
		analysisConnectionName = createConnection(CommunicationChannelType.analysis, instanceParser);
		
		testServer = instanceParser.getConfigValue("test-server");
		if(testServer !=null){
			XMPPBridgeCurrent.setServer(testServer);	
		}
		else {
			logger.warn("test-server not defined in config file, not setting XMPPBridgeCurrent.setServer");
		}
		testCommandConnectionName = createConnection(CommunicationChannelType.testCommand, instanceParser);
		testAnalysisConnectionName = createConnection(CommunicationChannelType.testAnalysis, instanceParser);
		
	}

	static String createConnection(CommunicationChannelType configType, XmlConfigParser instanceParser){
		try {
			String connectionConfigFilepath = GeneralUtil.getRealPath("conffiles/xmpp/xmpp-connect-settings.xml");
			XmlConfigParser connectionParser = new XmlConfigParser(connectionConfigFilepath);
			
			String connectionInstance = instanceParser.getConfigValue(configType.toString());
			
			connectionParser = connectionParser.getfragmentById("xmpp-channel-setting", "channelid", connectionInstance);
			
			String connectionName = connectionParser.getConfigValue("connection-name");
	
			String userName = connectionParser.getConfigValue("username");
			String password = connectionParser.getConfigValue("password");
			String chatroom = connectionParser.getConfigValue("chatroom");
			String alias = connectionParser.getConfigValue("alias");
			String device = connectionParser.getConfigValue("device");
			XMPPBridge.createConnection(connectionName, userName, password, chatroom, alias, device);
			return connectionName;
		}
		catch(Exception e){
			System.out.println("[CfXmppWriter] error creating connection - " + e.getMessage());
			return null;
		}
	}
	
	private XMPPBridge xmppBridge;
	
	private Vector<CfCommunicationListener> listeners;
	private XmppServerType xmppServerType;
	
	public CfXmppCommunicationBridge(CommunicationChannelType type){
		listeners = new Vector<CfCommunicationListener>();
		
		String connectionName;
		if (type == CommunicationChannelType.command){
			xmppServerType = XmppServerType.DEPLOY;
			XMPPBridgeCurrent.setServer(server);	
			connectionName = commandConnectionName;
		}
		else if (type == CommunicationChannelType.analysis){
			xmppServerType = XmppServerType.DEPLOY;
			XMPPBridgeCurrent.setServer(server);	
			connectionName = analysisConnectionName;
		} 
		else if (type == CommunicationChannelType.testAnalysis){
			xmppServerType = XmppServerType.TEST;
			XMPPBridgeCurrent.setServer(testServer);	
			connectionName = testAnalysisConnectionName;
		} 
		else if (type == CommunicationChannelType.testCommand){
			xmppServerType = XmppServerType.TEST;
			XMPPBridgeCurrent.setServer(testServer);	
			connectionName = testCommandConnectionName;
		} 
		else {
			logger.error("[constructor]Uknown connectionType");
			xmppServerType = XmppServerType.TEST;
			connectionName = "";
		}

		try{
			xmppBridge = XMPPBridge.getConnection(connectionName);
//			xmppBridge.connect(true);
			xmppBridge.registerListener(this);
			xmppBridge.sendMessage(connectionName + " connected at " + System.currentTimeMillis());
			logger.info(connectionName + " connected to xmppBridge at " + new Date());

		}
		catch(Exception e){
			logger.error("[constructor] " + ErrorUtil.getStackTrace(e) );
		}
		
	}
	
	@Override
	public void registerListener(CfCommunicationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void sendAction(CfAction actionToSend) {
		if (xmppServerType == XmppServerType.DEPLOY){
			XMPPBridgeCurrent.setServer(server);	
		}
		else {
			XMPPBridgeCurrent.setServer(testServer);	
		}
		String  messageToSend = ( CfActionParser.toXml(actionToSend) ).toString();
		if (messageToSend != null){
			xmppBridge.sendMessage(messageToSend);	
		}
		else {
			logger.error("[sendMessage] no message to send for [action - " + actionToSend +"]");
		}
		
		
	}
	@Override
	public void newMessage(String user, String message, String arg2) {
		logger.info("[newMessage] from user(" + user + "), starts with: "  + GeneralUtil.getStartOfString(message) );
		logger.debug("[newMessage] from user(" + user + ") \n" + message);
		
		message = XmlUtil.convertSpecialCharactersToDescripitons(message);
		message = message.trim();
		XmlFragment actionXml = XmlFragment.getFragmentFromString(message);
		if (actionXml != null){
			CfAction action = CfActionParser.fromXml(actionXml);
			for (CfCommunicationListener listener : listeners){
				listener.processCfAction(user, action);
			}	
		}
		else {
			logger.info("[newMessage] No Action xml recognized" );
		}
	}

}

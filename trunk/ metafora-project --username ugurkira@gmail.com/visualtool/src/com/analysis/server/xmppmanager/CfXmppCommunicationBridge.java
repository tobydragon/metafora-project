package com.analysis.server.xmppmanager;

import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;


import com.analysis.client.communication.objects.CfAction;
import com.analysis.server.io.ErrorUtil;
import com.analysis.server.xml.XmlConfigParser;
import com.analysis.server.xmpp.XMPPBridge;
import com.analysis.server.xmpp.XMPPMessageListener;

public class CfXmppCommunicationBridge implements CfCommunicationBridge, XMPPMessageListener{
	Logger logger = Logger.getLogger(CfCommunicationBridge.class);
	
	private static String commandConnectionName = null;
	private static String analysisConnectionName = null;
	
	static {
		//config file location
		String instanceConfigFilepath = "conf/metafora/details/agents/types/xmpp/xmpp-settings.xml";
		XmlConfigParser instanceParser = new XmlConfigParser(instanceConfigFilepath);

		commandConnectionName = createConnection(CommunicationChannelType.command, instanceParser);
		analysisConnectionName = createConnection(CommunicationChannelType.analysis, instanceParser);	
}

static String createConnection(CommunicationChannelType configType, XmlConfigParser instanceParser){
	
	try {
		String connectionConfigFilepath = "conf/metafora/details/agents/types/xmpp/xmpp-connect-settings.xml";
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
	
	public CfXmppCommunicationBridge(CommunicationChannelType type){
		listeners = new Vector<CfCommunicationListener>();
		
		String connectionName;
		if (type == CommunicationChannelType.command){
			connectionName = commandConnectionName;
		}
		else if (type == CommunicationChannelType.analysis){
			connectionName = analysisConnectionName;
		} 
		else {
			logger.error("[constructor]Uknown connectionType");
			connectionName = "";
		}

		try{
			xmppBridge = XMPPBridge.getConnection(connectionName);
			xmppBridge.connect(true);
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
		//String  messageToSend = actionToSend.toXml().toString();
		String  messageToSend = actionToSend.toString();
		
		if (messageToSend != null){
			xmppBridge.sendMessage(messageToSend);
		}
		else {
			logger.error("[sendMessage] no message to send for [action - " + actionToSend +"]");
		}
		
		
	}
	@Override
	public void newMessage(String user, String message, String arg2) {
		for (CfCommunicationListener listener : listeners){
			listener.receiveMessage(user, message);
		}
	}

}

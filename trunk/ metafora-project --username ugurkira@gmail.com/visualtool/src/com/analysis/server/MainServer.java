package com.analysis.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zcom.analysis.server.io.FileOperation;
import zcom.analysis.server.io.SourceManager;
import zcom.analysis.server.io.XmlFragment;
import zcom.analysis.server.xmpp.StartupServlet;
import zcom.analysis.server.xmppoldxx.XmppActionListener;

import com.analysis.client.communication.server.CommunicationService;

import com.analysis.server.cfcommunication.CfAgentCommunicationManager;
import com.analysis.server.cfcommunication.CfCommunicationListener;
import com.analysis.server.cfcommunication.CommunicationChannelType;
import com.analysis.server.cfcommunication.CommunicationMethodType;
import com.analysis.server.utils.ServerFormatStrings;
import com.analysis.server.xml.XmlConfigParser;
import com.analysis.shared.commonformat.CfAction;
import com.analysis.shared.commonformat.CfInteractionData;
import com.analysis.shared.interactionmodels.Configuration;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MainServer extends RemoteServiceServlet implements
		CommunicationService,CfCommunicationListener {


	String configFilepath = "conf/visualtool/configuration.xml";
	public Configuration _configuration;
	List<CfAction> _cfActions;
	
	CfAgentCommunicationManager communicationManager;
	
	public MainServer(){		
		_cfActions=new ArrayList<CfAction>();
				
		XmlConfigParser connectionParser = new XmlConfigParser(configFilepath);
		_configuration=connectionParser.toActiveConfiguration();		
		communicationManager = CfAgentCommunicationManager.getInstance(getCommunicationType(_configuration.getActionSource()), CommunicationChannelType.analysis);				
		communicationManager.register(this);
				
	}
	
	

	@Override
	public CfAction sendAction(String _user,CfAction cfAction) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Configuration sendRequestConfiguration(String _user,CfAction cfAction) {

		return _configuration;
	}



	@Override
	public CfInteractionData sendRequestHistoryAction(String _user,CfAction cfAction) {
		
		communicationManager.sendMessage(cfAction);
		
		//Synchronizatio
		CfInteractionData _interaction=new CfInteractionData();
		_interaction.setCfActions(_cfActions);
		
		return _interaction;
	}



	@Override
	public void processCfAction(String user, CfAction action) {
		
		_cfActions.add(action);
		System.out.println("Action Added!:"+action.getCfActionType().getClassification());
	}
	
public CommunicationMethodType getCommunicationType(String _type){
		
		if(_type.equalsIgnoreCase("file"))
			return CommunicationMethodType.file;
		else if(_type.equalsIgnoreCase("xmpp"))
		return CommunicationMethodType.xmpp;		
		return null;
	}


	
}

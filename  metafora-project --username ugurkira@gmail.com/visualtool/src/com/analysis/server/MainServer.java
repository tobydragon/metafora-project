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
		CommunicationService {


	String configFilepath = "conf/visualtool/configuration.xml";
	public Configuration _configuration;
	List<CfAction> _cfActions;
	public MainServer(){		
		_cfActions=new ArrayList<CfAction>();
				
		XmlConfigParser connectionParser = new XmlConfigParser(configFilepath);
		_configuration=connectionParser.toActiveConfiguration();
				
	}
	
	

	@Override
	public CfAction sendAction(CfAction cfAction) {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public List<CfAction> sendRequestHistoryAction(CfAction cfAction) {
	
		return _activeAction;
	}*/

	@Override
	public Configuration sendRequestConfiguration(CfAction cfAction) {

		return _configuration;
	}



	@Override
	public CfInteractionData sendRequestHistoryAction(CfAction cfAction) {
		
		CfInteractionData _interaction=new CfInteractionData();
		_interaction.setCfActions(_cfActions);
		
		return _interaction;
	}


	
}

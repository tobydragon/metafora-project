package com.analysis.server.cfcommunication;

import org.apache.log4j.Logger;

import com.analysis.server.commonformatparser.CfActionParser;
import com.analysis.shared.commonformat.CfAction;
//import com.analysis.shared.commonformatparser.CfActionParser;


public class CfCommunicationListenerTest implements CfCommunicationListener{

	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public void processCfAction(String user, CfAction action) {
		logger.debug("[processCfAction] \n" + CfActionParser.toXml(action));		
	}
	
	

}

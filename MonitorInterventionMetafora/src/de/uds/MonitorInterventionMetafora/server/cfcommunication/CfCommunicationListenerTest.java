package de.uds.MonitorInterventionMetafora.server.cfcommunication;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;


public class CfCommunicationListenerTest implements CfCommunicationListener{

	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public void processCfAction(String user, CfAction action) {
		logger.debug("[processCfAction] \n" + CfActionParser.toXml(action));		
	}
	
	

}

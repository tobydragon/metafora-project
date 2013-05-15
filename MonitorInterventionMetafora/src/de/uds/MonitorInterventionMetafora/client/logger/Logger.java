package de.uds.MonitorInterventionMetafora.client.logger;

import com.google.gwt.core.client.GWT;
import de.uds.MonitorInterventionMetafora.client.communication.CommunicationService;
import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.NoActionResponse;

public class Logger {

	
	static Logger instance;
	CommunicationServiceAsync loggingServiceServlet = GWT.create(CommunicationService.class);

	public static Logger getLoggerInstance(){		
		if(instance==null){
			instance= new Logger();
			}
		return instance;
	}

	public void log(UserLog actionLog){
		loggingServiceServlet.logAction(actionLog.toCfAction(), new NoActionResponse());
	}

}

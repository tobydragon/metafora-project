package de.uds.MonitorInterventionMetafora.client.logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationService;
import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;



public class Logger implements AsyncCallback<String> {

	
static Logger instance;
	
CommunicationServiceAsync loggingServiceServlet = GWT.create(CommunicationService.class);
	
	
public static Logger getLoggerInstance(){		
		if(instance==null){
			instance= new Logger();
			}
		return instance;
		}


	

public void log(UserLog actionLog){
	loggingServiceServlet.sendLogAction(actionLog.toCfAction(),this);
}




@Override
public void onFailure(Throwable caught) {
	// TODO Auto-generated method stub
	
}




@Override
public void onSuccess(String result) {
	// TODO Auto-generated method stub
	
}

}

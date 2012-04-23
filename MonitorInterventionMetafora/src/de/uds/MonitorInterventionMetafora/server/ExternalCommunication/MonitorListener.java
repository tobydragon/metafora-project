package de.uds.MonitorInterventionMetafora.server.ExternalCommunication;

import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfCommunicationListener;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorModel;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class MonitorListener implements CfCommunicationListener{

	private MonitorModel model;
	
	public MonitorListener(MonitorModel monitorModel){
		this.model = monitorModel;
	}
	
	//synchronized because it can be registered to more than one manager, and so calls should be synchronized
	public synchronized void processCfAction(String user, CfAction action) {
		model.addAction(action);
	}

}

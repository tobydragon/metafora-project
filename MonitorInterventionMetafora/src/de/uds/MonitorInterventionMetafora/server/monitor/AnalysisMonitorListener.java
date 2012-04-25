package de.uds.MonitorInterventionMetafora.server.monitor;

import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfCommunicationListener;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class AnalysisMonitorListener implements CfCommunicationListener{

	private MonitorModel model;
	
	public AnalysisMonitorListener(MonitorModel monitorModel){
		this.model = monitorModel;
	}
	
	//synchronized because it can be registered to more than one manager, and so calls should be synchronized
	public synchronized void processCfAction(String user, CfAction action) {
		model.addAction(action);
	}

}

package de.uds.MonitorInterventionMetafora.server.monitor;

import de.uds.MonitorInterventionMetafora.server.analysis.manager.TaggingManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfCommunicationListener;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class AnalysisMonitorListener implements CfCommunicationListener{

	private MonitorModel model;
	private TaggingManager taggingManger;
	
	public AnalysisMonitorListener(MonitorModel monitorModel){
		this.model = monitorModel;
		taggingManger=new TaggingManager();
	}
	
	//synchronized because it can be registered to more than one manager, and so calls should be synchronized
	public synchronized void processCfAction(String user, CfAction action) {
		model.addAction(taggingManger.tagAction(action));
	}

}

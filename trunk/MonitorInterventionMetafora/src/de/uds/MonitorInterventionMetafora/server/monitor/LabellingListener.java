package de.uds.MonitorInterventionMetafora.server.monitor;

import de.uds.MonitorInterventionMetafora.server.analysis.text.TextManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfCommunicationListener;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;


//This class receives each action and adds appropriate labels to allow analysis work
public class LabellingListener implements CfCommunicationListener{

	private MonitorModel model;
	private TextManager taggingManager;
	
	//TODO add labels for PERCEIVED_SOLUTION, POSSIBLE_SOLUTION, POSSIBLE_STRUGGLE
	
	public LabellingListener(MonitorModel monitorModel){
		this.model = monitorModel;
		taggingManager=new TextManager(true);
	}
	
	//synchronized because it can be registered to more than one manager, and so calls should be synchronized
	public synchronized void processCfAction(String user, CfAction action) {
		model.addAction(taggingManager.tagAction(action));
	}

}

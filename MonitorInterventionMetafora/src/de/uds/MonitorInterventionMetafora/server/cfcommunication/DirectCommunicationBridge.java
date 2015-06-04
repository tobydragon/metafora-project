package de.uds.MonitorInterventionMetafora.server.cfcommunication;

import java.util.ArrayList;

import de.uds.MonitorInterventionMetafora.server.monitor.MonitorController;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class DirectCommunicationBridge implements CfCommunicationBridge{
	
	private ArrayList<CfCommunicationListener> listeners;
	private MonitorController monitorModel;
	
	public DirectCommunicationBridge(MonitorController monitorController) {
		listeners = new ArrayList<CfCommunicationListener>();
		this.monitorModel = monitorController;
	}
	
	@Override
	public void registerListener(CfCommunicationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void sendAction(CfAction actionToSend) {
		monitorModel.addAction(actionToSend);
	}

}

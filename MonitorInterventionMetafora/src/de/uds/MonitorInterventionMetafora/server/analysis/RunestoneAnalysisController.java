package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorIdentifier;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.ObjectSummaryIdentifier;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.DirectCommunicationBridge;
import de.uds.MonitorInterventionMetafora.server.messages.MessagesController;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorController;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;

public class RunestoneAnalysisController extends AnalysisController{

	public RunestoneAnalysisController(MonitorController monitorController, MessagesController feedbackController, XmppServerType xmppServerType){
		super(monitorController, feedbackController, xmppServerType); 
	}

	@Override
	protected InterventionController createInterventionController(MonitorController monitorController, MessagesController feedbackController,XmppServerType xmppServerType) {
		//runestone will use a direct analysis controller for now...
		CfAgentCommunicationManager analysisChannelManager = new CfAgentCommunicationManager(monitorController);
		return new RunestoneInterventionController(analysisChannelManager);
	}

	@Override
	protected List<BehaviorIdentifier> createIdentifiers() {
		behaviorIdentifiers = new Vector<BehaviorIdentifier>();
		behaviorIdentifiers.add(new ObjectSummaryIdentifier());
		return behaviorIdentifiers;
	}
}

package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorIdentifier;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.DivergenceConvergenceIdentifier;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.MemberNotDiscussing;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.MembersPlanning;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.NewIdeaNotDiscussedIdentifier;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.PerceivedSolutionSharingIdentifier;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.UsingAttitudesAndRoles;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.messages.MessagesController;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorController;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.XmppServerType;

public class MetaforaAnalysisController extends AnalysisController{
	
	public MetaforaAnalysisController(MonitorController monitorController, MessagesController feedbackController, XmppServerType xmppServerType){
		super(monitorController, feedbackController, xmppServerType); 
	}
	
	protected InterventionController createInterventionController(MonitorController monitorController, MessagesController feedbackController,  XmppServerType xmppServerType) {
		CfAgentCommunicationManager analysisChannelManager = monitorController.getAnalysisChannelManager();
		return new SuggestionsAndLandmarkInterventionController(feedbackController, analysisChannelManager, xmppServerType);
	}

	protected List<BehaviorIdentifier> createIdentifiers(){
		behaviorIdentifiers = new Vector<BehaviorIdentifier>();
		behaviorIdentifiers.add(new NewIdeaNotDiscussedIdentifier());
		behaviorIdentifiers.add(new MembersPlanning());
		behaviorIdentifiers.add(new MemberNotDiscussing());
		behaviorIdentifiers.add(new UsingAttitudesAndRoles());
		behaviorIdentifiers.add(new PerceivedSolutionSharingIdentifier());
		behaviorIdentifiers.add(new DivergenceConvergenceIdentifier());
		return behaviorIdentifiers;
	}

}

package de.uds.MonitorInterventionMetafora.server.analysis;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public interface AnalysisListener {
	
	public void processAnalysis(String user, CfAction action);

}

package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.List;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorInstance;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;

public class RunestoneInterventionController implements InterventionController{

	@Override
	public void sendInterventions(List<BehaviorInstance> behaviorsIdentified, List<String> involvedUsers, Locale locale) {
		//TODO: @Caitlin: This is where we will create CfActions for each problem, from each BehaviorInstance that represents one problem and all it's details
		//For now, you can just pint the list of BehaviorInstances, to see that they are being created correctly
		
	}

}

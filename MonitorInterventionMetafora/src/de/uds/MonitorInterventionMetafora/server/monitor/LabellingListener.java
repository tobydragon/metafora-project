package de.uds.MonitorInterventionMetafora.server.monitor;

import de.uds.MonitorInterventionMetafora.server.analysis.text.TextManager;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfCommunicationListener;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;


//This class receives each action and adds appropriate labels to allow analysis work
public class LabellingListener implements CfCommunicationListener{

	protected MonitorModel model;
	private TextManager taggingManager;
	
	//TODO add labels for PERCEIVED_SOLUTION, POSSIBLE_SOLUTION, STRUGGLE
	
	public LabellingListener(MonitorModel monitorModel){
		this.model = monitorModel;
		taggingManager=new TextManager(true);
	}
	
	//synchronized because it can be registered to more than one manager, and so calls should be synchronized
	public synchronized void processCfAction(String user, CfAction action) {
		taggingManager.tagAction(action);
		labelStruggle(action);
		labelConvergenceAndDivergence(action);
		labelShareObject(action);
		labelViewingOthersObjects(action);
		labelPerceivedSolution(action);
		model.addAction(action);
	}

	private void labelConvergenceAndDivergence(CfAction action) {
		if (LabellingFilters.createConvergenceObjectsToLabelFilter().filterIncludesAction(action)){
			action.getCfContent().addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_BEHAVIOR_TYPE, BehaviorType.CONVERGENCE.toString()));
		}
		if (LabellingFilters.createDivergenceObjectsToLabelFilter().filterIncludesAction(action)){
			action.getCfContent().addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_BEHAVIOR_TYPE, BehaviorType.DIVERGENCE.toString()));
		}
		
	}

	private void labelViewingOthersObjects(CfAction action) {
		if (LabellingFilters.createViewOthersObjectsLabelFilter().filterIncludesAction(action)){
			String viewer = action.getListofUsersAsStringWithRole(MetaforaStrings.USER_ROLE_ORIGINATOR_STRING);
			String creator = action.getCfObjects().get(0).getPropertyValue("CREATOR");
			if ( !(viewer.equalsIgnoreCase(creator)) ){
				action.getCfContent().addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_BEHAVIOR_TYPE, BehaviorType.VIEWING_OTHERS_OBJECTS.toString()));
			}
		}
	}

	private void labelStruggle(CfAction action) {
		if (LabellingFilters.createStruggleLabelFilter().filterIncludesAction(action)){
			action.getCfContent().addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_BEHAVIOR_TYPE, BehaviorType.STRUGGLE.toString()));
		}
	}
	
	private void labelShareObject(CfAction action) {
		if (LabellingFilters.createShareObjectsLabelFilter().filterIncludesAction(action)){
			action.getCfContent().addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_BEHAVIOR_TYPE, BehaviorType.SHARE_OBJECT.toString()));
		}
	}
	
	private void labelPerceivedSolution(CfAction action) {
		if (LabellingFilters.createPerceivedSolutionLabelFilter().filterIncludesAction(action)){
			action.getCfContent().addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_BEHAVIOR_TYPE, BehaviorType.PERCEIVED_SOLUTION.toString()));
		}
	}


}

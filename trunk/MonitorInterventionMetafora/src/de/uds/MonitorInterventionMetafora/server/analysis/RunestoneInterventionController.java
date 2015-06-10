package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorInstance;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfInteractionDataParser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;

public class RunestoneInterventionController implements InterventionController{
	
	Logger log = Logger.getLogger(this.getClass());
	CfAgentCommunicationManager analysisChannelManager;
	
	public RunestoneInterventionController(CfAgentCommunicationManager analysisChannelManagaer){
		this.analysisChannelManager = analysisChannelManagaer;
		
	}

	@Override
	public void sendInterventions(List<BehaviorInstance> behaviorsIdentified, List<String> involvedUsers, Locale locale) {
		//TODO: This is where we will create CfActions for each problem, from each BehaviorInstance that represents one problem and all it's details
		
		List<CfAction> actions = new ArrayList<CfAction>();
		
		//go through the behavior instances and create a CfAction for each 
		for(BehaviorInstance behaviorInstance : behaviorsIdentified){
			actions.add(buildCFAction(behaviorInstance));		
		}
		
		for(CfAction cfAction : actions){
			analysisChannelManager.sendMessage(cfAction);
		}
		System.out.println(CfInteractionDataParser.toXml(new CfInteractionData(actions)));
	
	}

	
	
	
	private CfAction buildCFAction(BehaviorInstance behaviorInstance) {
		
		long time = System.currentTimeMillis();
		
		CfActionType cfActionType = new CfActionType(behaviorInstance.getBehaviorType().toString(), RunestoneStrings.INDICATOR_STRING, null, null);
		
		List<CfUser> cfUsers = new ArrayList<CfUser>();
		cfUsers.add(new CfUser(behaviorInstance.getUsernames().toString().replace("[", "").replace("]", ""),RunestoneStrings.ORIGINATOR_STRING));
		
		
		//if the instance is a Per User Per Object then create a CfObject 
		List<CfObject> cfObjects = new ArrayList<CfObject>();
		if(behaviorInstance.getBehaviorType() == BehaviorType.PER_USER_PER_OBJECT_SUMMARY){
			cfObjects.add(new CfObject(behaviorInstance.getPropertyValue(RunestoneStrings.OBJECT_ID_STRING), behaviorInstance.getPropertyValue(CommonFormatStrings.TYPE_STRING)));
		}

		
		Map<String, CfProperty> cfPropertiesContent = new HashMap<String, CfProperty>();
		String description = "";
		
		//if the property is the description save into variable instead of adding it as a content property 
		for (CfProperty prop : behaviorInstance.getProperties()){
			if(prop.getName().equalsIgnoreCase(RunestoneStrings.DESCRIPTION_STRING)){
				description = prop.getValue();
			}
			//add all other properties as content properties
			else{
				cfPropertiesContent.put(prop.getName(), prop);
			}
				
		}
		// create CfContent with the description separate from the other properties 
		CfContent cfContent = new CfContent(description, cfPropertiesContent);
		
		CfAction cfAction = new CfAction (time, cfActionType, cfUsers, cfObjects, cfContent);
		
		return cfAction;
	}
	
}

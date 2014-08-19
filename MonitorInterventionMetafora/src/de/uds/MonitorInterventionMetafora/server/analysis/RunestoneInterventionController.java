package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorInstance;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfAgentCommunicationManager;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfInteractionDataParser;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfObjectParser;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfPropertyParser;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfUserParser;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.Locale;

public class RunestoneInterventionController implements InterventionController{
	
	CfAgentCommunicationManager analysisChannelManager;
	
	public RunestoneInterventionController(CfAgentCommunicationManager analysisChannelManagaer){
		this.analysisChannelManager = analysisChannelManagaer;
		
	}

	@Override
	public void sendInterventions(List<BehaviorInstance> behaviorsIdentified, List<String> involvedUsers, Locale locale) {
		//TODO: @Caitlin: This is where we will create CfActions for each problem, from each BehaviorInstance that represents one problem and all it's details
		//For now, you can just print the list of BehaviorInstances, to see that they are being created correctly
		
		
		List<CfAction> actions = new ArrayList<CfAction>();
		
		for(BehaviorInstance behaviorInstance : behaviorsIdentified){
		
			long time = System.currentTimeMillis();
			
			CfActionType cfActionType = new CfActionType(behaviorInstance.getBehaviorType().toString(), RunestoneStrings.INDICATOR_STRING, null, null);
			
			List<CfUser> cfUsers = new ArrayList<CfUser>();
			cfUsers.add(new CfUser(behaviorInstance.getUsernames().toString(),RunestoneStrings.ORIGINATOR_STRING));
			
			Map<String, CfProperty> cfPropertiesObject = new HashMap<String, CfProperty>();
			cfPropertiesObject.put(behaviorInstance.getProperties().get(0).getName(), behaviorInstance.getProperties().get(0));
			cfPropertiesObject.put(behaviorInstance.getProperties().get(1).getName(), behaviorInstance.getProperties().get(1));
			cfPropertiesObject.put(behaviorInstance.getProperties().get(2).getName(), behaviorInstance.getProperties().get(2));
		
			List<CfObject> cfObjects = new ArrayList<CfObject>();
			cfObjects.add(new CfObject(behaviorInstance.getPropertyValue(RunestoneStrings.OBJECT_ID_STRING), behaviorInstance.getPropertyValue(CommonFormatStrings.TYPE_STRING), cfPropertiesObject));
			
			String user = behaviorInstance.getUsernames().toString();
			String seconds = String.valueOf(behaviorInstance.getPropertyValue(RunestoneStrings.TIME_SPENT_STRING));
			String objectId = behaviorInstance.getPropertyValue(RunestoneStrings.OBJECT_ID_STRING);
			String timesFalse = behaviorInstance.getPropertyValue(RunestoneStrings.TIMES_FALSE_STRING);
			String isCorrect = behaviorInstance.getPropertyValue(RunestoneStrings.IS_EVER_CORRECT_STRING);
			
			String description = user + " spent " + seconds + " seconds on " + objectId + " and answered incorrectly "
					+ timesFalse + " time(s), "; 
			//description depends on whether the user answered the question correct
			if(isCorrect.equalsIgnoreCase("true")){
			
				description = description + "before answering correct";
			}
			else{
				description = description + "never answered correct";
			}
					
			
			Map<String, CfProperty> cfPropertiesContent = new HashMap<String, CfProperty>();
			cfPropertiesContent.put(behaviorInstance.getProperties().get(3).getName(), behaviorInstance.getProperties().get(3));
			CfContent cfContent = new CfContent(description, cfPropertiesContent);
			
			CfAction cfAction = new CfAction (time, cfActionType, cfUsers, cfObjects, cfContent);
			actions.add(cfAction);
			analysisChannelManager.sendMessage(cfAction);
		}
			
			System.out.println(CfInteractionDataParser.toXml(new CfInteractionData(actions)));
			
			
			
	}

}

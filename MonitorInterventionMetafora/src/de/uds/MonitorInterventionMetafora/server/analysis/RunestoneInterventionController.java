package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

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
		
		for(BehaviorInstance behaviorInstance : behaviorsIdentified){
			
			if(behaviorInstance.getBehaviorType() == BehaviorType.PER_USER_PER_OBJECT_SUMMARY){
				actions.add(buildPerUserPerObjectSummaryCFAction(behaviorInstance));
			}
			
			else if(behaviorInstance.getBehaviorType() == BehaviorType.PER_USER_ALL_OBJECTS_SUMMARY){
				actions.add(buildPerUserAllObjectsSummaryCFAction(behaviorInstance));
			}
			
			else if(behaviorInstance.getBehaviorType() == BehaviorType.ALL_USERS_PER_OBJECT_SUMMARY){
				actions.add(buildAllUsersPerObjectSummaryCFAction(behaviorInstance));
			}
			
			else{
				log.warn("Behavior type not recognized");
			}
		
			
		
		}
		for(CfAction cfAction : actions){
			analysisChannelManager.sendMessage(cfAction);
		}
		System.out.println(CfInteractionDataParser.toXml(new CfInteractionData(actions)));
			
			
			
	}
	
	private CfAction buildAllUsersPerObjectSummaryCFAction(BehaviorInstance behaviorInstance) {
		// TODO Auto-generated method stub
		long time = System.currentTimeMillis();
		
		CfActionType cfActionType = new CfActionType(behaviorInstance.getBehaviorType().toString(), RunestoneStrings.INDICATOR_STRING, null, null);
		
		List<CfUser> cfUsers = new ArrayList<CfUser>();
		cfUsers.add(new CfUser(behaviorInstance.getUsernames().toString(),RunestoneStrings.ORIGINATOR_STRING));
		
		List<CfObject> cfObjects = new ArrayList<CfObject>();
		
		String user = behaviorInstance.getUsernames().toString();
		String objectId = String.valueOf(behaviorInstance.getPropertyValue(RunestoneStrings.OBJECT_ID_STRING));
		String totalAttempted = String.valueOf(behaviorInstance.getPropertyValue(RunestoneStrings.TOTAL_ATTEMPTED_STRING));
		String numCorrect = String.valueOf(behaviorInstance.getPropertyValue(RunestoneStrings.TOTAL_NUMBER_CORRECT_STRING));
		String correctUsers = String.valueOf(behaviorInstance.getPropertyValue(RunestoneStrings.TOTAL_CORRECT_USERS_STRING));
		String numIncorrect = String.valueOf(behaviorInstance.getPropertyValue(RunestoneStrings.TOTAL_NUMBER_INCORRECT_STRING));
		String incorrectUsers = String.valueOf(behaviorInstance.getPropertyValue(RunestoneStrings.TOTAL_INCORRECT_USERS_STRING));
		String numBoth = String.valueOf(behaviorInstance.getPropertyValue(RunestoneStrings.TOTAL_NUMBER_BOTH_STRING));
		String bothUsers = String.valueOf(behaviorInstance.getPropertyValue(RunestoneStrings.TOTAL_BOTH_USERS_STRING));
				
		String description = objectId + " was attempted by " + totalAttempted + " different user(s).  There were "
				+ numCorrect + " only correct responses, " + numBoth + " first incorrect then correct responses, and "+ numIncorrect + " only incorrect responses."; 
		
		Map<String, CfProperty> cfPropertiesContent = new HashMap<String, CfProperty>();
		for (CfProperty prop : behaviorInstance.getProperties()){
			cfPropertiesContent.put(prop.getName(), prop);
		}
		CfContent cfContent = new CfContent(description, cfPropertiesContent);
		
		CfAction cfAction = new CfAction (time, cfActionType, cfUsers, cfObjects, cfContent);
		
		return cfAction;
	}

	private CfAction buildPerUserAllObjectsSummaryCFAction(
			BehaviorInstance behaviorInstance) {
		// TODO Auto-generated method stub
		long time = System.currentTimeMillis();
		
		CfActionType cfActionType = new CfActionType(behaviorInstance.getBehaviorType().toString(), RunestoneStrings.INDICATOR_STRING, null, null);
		
		List<CfUser> cfUsers = new ArrayList<CfUser>();
		cfUsers.add(new CfUser(behaviorInstance.getUsernames().toString(),RunestoneStrings.ORIGINATOR_STRING));
		
		List<CfObject> cfObjects = new ArrayList<CfObject>();
		//TODO: Do we need the separate question objects? for now, we just have the ids in a string property of the action
//		Map<String, CfProperty> cfPropertiesObject = new HashMap<String, CfProperty>();
//		cfPropertiesObject.put(behaviorInstance.getProperties().get(0).getName(), behaviorInstance.getProperties().get(0));
//		cfPropertiesObject.put(behaviorInstance.getProperties().get(1).getName(), behaviorInstance.getProperties().get(1));
//		cfPropertiesObject.put(behaviorInstance.getProperties().get(2).getName(), behaviorInstance.getProperties().get(2));
//		cfObjects.add(new CfObject(behaviorInstance.getPropertyValue(RunestoneStrings.OBJECT_ID_STRING), behaviorInstance.getPropertyValue(CommonFormatStrings.TYPE_STRING), cfPropertiesObject));
		
		String user = behaviorInstance.getUsernames().toString();
		String totalAttempted = String.valueOf(behaviorInstance.getPropertyValue(RunestoneStrings.TOTAL_ATTEMPTED_STRING));
		String numCorrect = behaviorInstance.getPropertyValue(RunestoneStrings.TOTAL_NUMBER_CORRECT_STRING);
		String correctQuestions = behaviorInstance.getPropertyValue(RunestoneStrings.TOTAL_CORRECT_ANSWERS_STRING);
		String numIncorrect = behaviorInstance.getPropertyValue(RunestoneStrings.TOTAL_NUMBER_INCORRECT_STRING);
		String incorrectQuestions = behaviorInstance.getPropertyValue(RunestoneStrings.TOTAL_INCORRECT_ANSWERS_STRING);
		String numOthers = behaviorInstance.getPropertyValue(RunestoneStrings.TOTAL_NUMBER_OTHERS_STRING);
		String totalTime = behaviorInstance.getPropertyValue(RunestoneStrings.TOTAL_TIME_SPENT_STRING);
				
		String description = user + " spent " + totalTime + " seconds on " + totalAttempted + " questions.  There were "
				+ numCorrect + " correct responses and " + numIncorrect + " incorrect responses"; 
		
		Map<String, CfProperty> cfPropertiesContent = new HashMap<String, CfProperty>();
		for (CfProperty prop : behaviorInstance.getProperties()){
			cfPropertiesContent.put(prop.getName(), prop);
		}
		CfContent cfContent = new CfContent(description, cfPropertiesContent);
		
		CfAction cfAction = new CfAction (time, cfActionType, cfUsers, cfObjects, cfContent);
		
		return cfAction;
	}

	private CfAction buildPerUserPerObjectSummaryCFAction(BehaviorInstance behaviorInstance) {
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
		
		return cfAction;
	}

}

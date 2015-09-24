package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;

public class AssessablePerUserPerProblemSummary extends PerUserPerProblemSummary {

	private boolean isCorrect;
	private boolean assessable;
	private int numberTimesFalse;
	private String falseEntries;
	private String description;
	
	
	public AssessablePerUserPerProblemSummary(List <CfAction> actionsFilteredByObjectId, String currentUser, String currentObjectId) {
		super(actionsFilteredByObjectId, currentUser, currentObjectId);
		
		//gets the time stamp of the first CfAction in the list and sets this as both the startTime and endTime 
		//these will be compared with the time of the current action in calculateTime
//		long startTime = actionsFilteredByObjectId.get(0).getTime();
//		long endTime = actionsFilteredByObjectId.get(0).getTime();
		
		//sets the assessable properties to these initial values, they will be updated as needed 
		isCorrect = false;
		assessable = true;
		numberTimesFalse = 0;
		falseEntries = "";
	
	
		//goes through each entry for each objectId for each user
		for (CfAction action : actionsFilteredByObjectId){	
			
//			calculateTime(action, startTime, endTime);
			determineisCorrect(action);
			
			if(isCorrect == false){
				numberTimesFalse++;
				constructFalseEntries(action);	
			}
		}	
		time = calculateTime(actionsFilteredByObjectId);
	}
	
	
	public BehaviorInstance buildBehaviorInstance(){
		
		buildDescription();
		List <CfProperty >instanceProperties = new Vector<CfProperty>();
		instanceProperties.add(new CfProperty(RunestoneStrings.TIME_SPENT_STRING, String.valueOf(getTime())));
		instanceProperties.add(new CfProperty(RunestoneStrings.IS_EVER_CORRECT_STRING,String.valueOf(isCorrect)));
		instanceProperties.add(new CfProperty(RunestoneStrings.IS_ASSESSABLE_STRING,String.valueOf(assessable)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TIMES_FALSE_STRING, String.valueOf(numberTimesFalse)));
		instanceProperties.add(new CfProperty(RunestoneStrings.FALSE_ENTRIES_STRING, falseEntries));
		instanceProperties.add(new CfProperty(RunestoneStrings.OBJECT_ID_STRING, getObjectId()));
		instanceProperties.add(new CfProperty(RunestoneStrings.TYPE_STRING, getType()));
		instanceProperties.add(new CfProperty(RunestoneStrings.DESCRIPTION_STRING, description));
		
		List <String> userList = new Vector<String>();
		userList.add(getUser());
		
		BehaviorInstance instance = new BehaviorInstance(BehaviorType.ASSESSABLE_PER_USER_PER_OBJECT_SUMMARY, userList, instanceProperties);
		return instance;
	}


	/*
	 * determines the isCorrect field and the assessable field
	 * takes in the current action as a parameter
	 * isCorrect is set to false by default and assessable is set to true by default
	 */
	public void determineisCorrect(CfAction action){
		
		//gets the correct value from the action
		String correctField = action.getCfContent().getPropertyValue(RunestoneStrings.CORRECT_STRING);
		//if the correctField is true, update isCorrect as true
		if (correctField.equalsIgnoreCase("true")){
			isCorrect = true;
		}
	}
	
	
	/*
	 * updates the falseEntries string whenever an action is incorrect
	 * takes in the current action as a parameter
	 */
	public void constructFalseEntries(CfAction action){
		
		//get the act value from the action
		String act = action.getCfObjects().get(0).getPropertyValue("ACT");
		
		//if the action represents a multiple choice question then strip away the unwanted characters 
		if(action.getCfObjects().get(0).getType().equalsIgnoreCase(RunestoneStrings.MCHOICE_STRING)){
			int startIndex = act.indexOf(":");
			startIndex = startIndex + 1;
			int endIndex = act.indexOf(":", (startIndex));	
			String actSubstring = act.substring(startIndex, endIndex);
			//update falseEntries with the cleaned act string
			falseEntries = falseEntries + "/" + actSubstring;
		}
		//if it is not a multiple choice question the act does not need to be stripped, update falseEntries with the act string as is
		else{
			falseEntries = falseEntries + "/" + act;
		}
	}
	
	private void buildDescription(){
		
		description = getUser() + " spent " + getTime() + " seconds on " + getObjectId() + " and answered incorrectly "
				+ numberTimesFalse + " time(s), "; 
		//description depends on whether the user answered the question correct
		if(isCorrect == true){
		
			description = description + "before answering correct";
		}
		else{
			description = description + "never answered correct";
		}
	}
	

	public boolean isCorrect() {
		return isCorrect;
	}

	public boolean getAssessable() {
		return assessable;
	}
	
	public int getNumberTimesFalse() {
		return numberTimesFalse;
	}

	public String getFalseEntries() {
		return falseEntries;
	}	
}

package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;

public class PerUserPerProblemSummary {
	private String user;
	private long time;
	private boolean isCorrect;
	private boolean assessable;
	private int numberTimesFalse;
	private String falseEntries;
	private String objectId;
	private String type;
	
	
	
	public PerUserPerProblemSummary(List <CfAction> actionsFilteredByObjectId, String currentUser, String currentObjectId){
		
		//sets the user and objectId from the parameters
		this.user = currentUser;
		this.objectId = currentObjectId;
		
		//gets the time stamp of the first CfAction in the list and sets this as both the startTime and endTime 
		//these will be compared with the time of the current action in calculateTime
		long startTime = actionsFilteredByObjectId.get(0).getTime();
		long endTime = actionsFilteredByObjectId.get(0).getTime();
		
		//sets the properties to these initial values, they will be updated as needed 
		isCorrect = false;
		assessable = true;
		numberTimesFalse = 0;
		falseEntries = "";
		//type = "";
	
	
		//goes through each entry for each objectId for each user
		for (CfAction action : actionsFilteredByObjectId){
			//gets the type from the action and sets it for the summary
			type = action.getCfObjects().get(0).getType();		
			
			calculateTime(action, startTime, endTime);
			determineisCorrect(action);
			
			if(isCorrect == false){
				numberTimesFalse++;
				constructFalseEntries(action);	
			}
		}		
	}
	
	
	public BehaviorInstance buildBehaviorInstance(){
		List <CfProperty >instanceProperties = new Vector<CfProperty>();
		instanceProperties.add(new CfProperty(RunestoneStrings.TIME_SPENT_STRING, String.valueOf(time)));
		instanceProperties.add(new CfProperty(RunestoneStrings.IS_EVER_CORRECT_STRING,String.valueOf(isCorrect)));
		instanceProperties.add(new CfProperty(RunestoneStrings.IS_ASSESSABLE_STRING,String.valueOf(assessable)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TIMES_FALSE_STRING, String.valueOf(numberTimesFalse)));
		instanceProperties.add(new CfProperty(RunestoneStrings.FALSE_ENTRIES_STRING, falseEntries));
		instanceProperties.add(new CfProperty(RunestoneStrings.OBJECT_ID_STRING, objectId));
		instanceProperties.add(new CfProperty(RunestoneStrings.TYPE_STRING, type));
		
		List <String> userList = new Vector<String>();
		userList.add(user);
		
		BehaviorInstance instance = new BehaviorInstance(BehaviorType.PER_USER_PER_OBJECT_SUMMARY, userList, instanceProperties);
		return instance;
	}

	/* 
	 *  determines the time taken for a single question
	 *  takes in the current action, the startTime, and endTime
	 *  startTime and endTime both begin as the same time, the time stamp from the first CfAction in the list
	 */
	public void calculateTime(CfAction action, long startTime, long endTime){
		//gets the time stamp of the action passed in
		long currentTime = action.getTime();
		
		//if currentTime is less than startTime then currentTime becomes the new startTime
		if(currentTime < startTime){
			startTime = currentTime;
		}
		//if currentTime is greater then the endTime then currentTime becomes the new endTime
		else if (currentTime > endTime){
			endTime = currentTime;
		}
		
		//takes the difference between startTime and endTime and converts to seconds
		time = (endTime - startTime) / 1000;
		
	}
	

	/*
	 * determines the isCorrect field and the assessable field
	 * takes in the current action as a parameter
	 * isCorrect is set to false by default and assessable is set to true by default
	 */
	public void determineisCorrect(CfAction action){
		
		//gets the correct value from the action
		String correctField = action.getCfContent().getPropertyValue(RunestoneStrings.CORRECT_STRING);
		
		//if the correctField is null then it is not assessable, set to false, set isCorrect to true
		if(correctField == null){
			isCorrect = true;
			assessable = false;
		}
		//if the correctField is true, update isCorrect as true
		else if (correctField.equalsIgnoreCase("true")){
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
	
	public String getUser() {
		return user;
	}

	public long getTime() {
		return time;
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

	public String getObjectId() {
		return objectId;
	}

	public String getType() {
		return type;
	}
	
}

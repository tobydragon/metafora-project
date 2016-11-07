package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.Concept;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.SummaryInfo;

public abstract class PerUserPerProblemSummary implements Concept{
	public String user;
	public long time;
	private boolean assessable;
	public String objectId;
	private String type;
	private String description;
	//time interval is so that we wait longer between answering questions before we assume it only took 30 seconds
	public final static long TIME_INTERVAL = 180;
	//standard time is our "old" timeInterval, changed to standard time because it is now our standard go to if it seems
	//like there was too much time taken between questions to assume that the user was still trying to answer them the
	//whole time
	public final static long STANDARD_TIME = 30;
	private int actualCompetency;
	private int predictedCompetency;

	
	public PerUserPerProblemSummary(List<CfAction> actionsFilteredByObjectId, String currentUser, String currentObjectId){
		
		
		this.user = currentUser;
		this.objectId = currentObjectId;
		
		//gets the time stamp of the first CfAction in the list and sets this as both the startTime and endTime 
		//these will be compared with the time of the current action in calculateTime
//		long startTime = actionsFilteredByObjectId.get(0).getTime();
//		long endTime = actionsFilteredByObjectId.get(0).getTime();
		
		assessable = false;

	
		//goes through each entry for each objectId for each user
		for (CfAction action : actionsFilteredByObjectId){
			//gets the type from the action and sets it for the summary
			type = action.getCfObjects().get(0).getType();		
//			calculateTime(action, startTime, endTime);
		}	
		time = calculateTime(actionsFilteredByObjectId);
	}
	
	
	public BehaviorInstance buildBehaviorInstance(){
		buildDescription();
		List <CfProperty >instanceProperties = new Vector<CfProperty>();
		instanceProperties.add(new CfProperty(RunestoneStrings.TIME_SPENT_STRING, String.valueOf(time)));
		instanceProperties.add(new CfProperty(RunestoneStrings.IS_ASSESSABLE_STRING,String.valueOf(assessable)));
		instanceProperties.add(new CfProperty(RunestoneStrings.OBJECT_ID_STRING, objectId));
		instanceProperties.add(new CfProperty(RunestoneStrings.TYPE_STRING, type));
		instanceProperties.add(new CfProperty(RunestoneStrings.DESCRIPTION_STRING, description));
		
		List <String> userList = new Vector<String>();
		userList.add(user);
		
		BehaviorInstance instance = new BehaviorInstance(BehaviorType.PER_USER_PER_OBJECT_SUMMARY, userList, instanceProperties);
		return instance;
	}
	
	
	
	
	 
		//determines the time taken for a single question
		//takes in the list of actions which is filtered by user and objectId 
		public static long calculateTime(List<CfAction> actionsList){
			
			long tempTime = 0;
			//time = timeInterval;
			//check this size of the list, make sure it is not empty
			if(actionsList.size() <= 0){
				System.out.println ("List is empty");
				return 0;
			}
			//if the list only has one action that use the time constant for the total time
			else if(actionsList.size() == 1){
				return STANDARD_TIME;
			}
			//if there are at least two actions in the list compare the time of the first action to the time of the next action
			//else if(actionsList.size() > 1){
			else{	
				// go through list of actions
				for(int i = 0; i < actionsList.size() - 1; i++){
					//get the time for the current action and the time for the following action
					long tempTime1 = actionsList.get(i).getTime();
					long tempTime2 = actionsList.get(i+1).getTime();
					
					//Begins totaling the time for all actions
					//System.out.println("Time 1: "+tempTime1+"  Time 2: "+tempTime2);
					tempTime += calcTime(tempTime1, tempTime2);
					//System.out.println("tempTime = "+tempTime);
					
				}
				
				return tempTime;
			}		
		}

	public static long calcTime(long time1, long time2){
		
		//determine the difference in the times and convert to seconds
		long timeDiff = (time2 - time1)/1000;
		
		//System.out.println("Time Diff: " + timeDiff + " - user: " + getUser() + " - objectId: " + getObjectId());
		//shouldn't happen but if the larger time is subtracted from the smaller time convert to a positive number
		if(timeDiff < 0){
			timeDiff = timeDiff * -1;
		}
		//if the time difference is less than the time interval constant than add the difference to the total time
		if(timeDiff < TIME_INTERVAL){
			//System.out.println("Time returned: "+timeDiff);
			return timeDiff;
		}
		//if the time difference is greater than the time interval constant 
		//then return our standard question time we created. 
		else{
			//System.out.println("Time returned: "+STANDARD_TIME);
			return STANDARD_TIME;
		}
	}
	
	
	

//	/* 
//	 *  determines the time taken for a single question
//	 *  takes in the current action, the startTime, and endTime
//	 *  startTime and endTime both begin as the same time, the time stamp from the first CfAction in the list
//	 */
//	public void calculateTime(CfAction action, long startTime, long endTime){
//		//gets the time stamp of the action passed in
//		long currentTime = action.getTime();
//		
//		//if currentTime is less than startTime then currentTime becomes the new startTime
//		if(currentTime < startTime){
//			startTime = currentTime;
//		}
//		//if currentTime is greater then the endTime then currentTime becomes the new endTime
//		else if (currentTime > endTime){
//			endTime = currentTime;
//		}
//		
//		//takes the difference between startTime and endTime and converts to seconds
//		time = (endTime - startTime) / 1000;
//		
//	}
//	
	private void buildDescription(){
		
		description = user + " spent " + time + " seconds on " + objectId + " which is not assessable.";
	}
	
	public String getUser() {
		return user;
	}

	public long getTime() {
		return time;
	}

	public boolean getAssessable() {
		return assessable;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getType() {
		return type;
	}
	public String getConceptTitle(){
		buildDescription();
		String title = objectId + ": description: " + description;
		return title;
	}
	
	public SummaryInfo getSummaryInfo(){
		List <String> users = new ArrayList<String> ();
		users.add(user);
	
		List <String> objectIds = new ArrayList<String> ();
		objectIds.add(objectId);

		
		//deafault values for SummaryInfo as this is a non assessable summary
		int numAssessable = 0;
		int numTimesFalse = 0;
		int numCorrect = 1;
		int numSummaries = 1;
		SummaryInfo info = new SummaryInfo(users, time, objectIds, numAssessable, numTimesFalse, numCorrect, numSummaries);
		
		return info;
	}
	
	//Move to PUPPS File and make Public Static, create tests in PUPPS Test file
	public static SortedSet<String> getUsers(List<PerUserPerProblemSummary> puppsList){
		SortedSet<String> users = new TreeSet<>();
		for(PerUserPerProblemSummary summary: puppsList){
			users.add(summary.user);
		}
		return users;
	}
	
	public static List<PerUserPerProblemSummary> getUserSummaries(List<PerUserPerProblemSummary> summaries, String user){
		List<PerUserPerProblemSummary> userSummaries = new ArrayList<PerUserPerProblemSummary>();
		
		
		for(PerUserPerProblemSummary summary: summaries){
			if(summary.getUser().equals(user)){
				userSummaries.add(summary);
			}
		}
		return userSummaries;
	}
	
	public String toString(){
		return getConceptTitle();
	}
	
}

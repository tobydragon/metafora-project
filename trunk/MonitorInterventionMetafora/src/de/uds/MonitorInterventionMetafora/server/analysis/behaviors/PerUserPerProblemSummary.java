package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.Concept;

public abstract class PerUserPerProblemSummary implements Concept{
	private String user;
	private long time;
	private boolean assessable;
	private String objectId;
	private String type;
	private String description;

	
	public PerUserPerProblemSummary(List<CfAction> actionsFilteredByObjectId, String currentUser, String currentObjectId){
		
		
		this.user = currentUser;
		this.objectId = currentObjectId;
		
		//gets the time stamp of the first CfAction in the list and sets this as both the startTime and endTime 
		//these will be compared with the time of the current action in calculateTime
		long startTime = actionsFilteredByObjectId.get(0).getTime();
		long endTime = actionsFilteredByObjectId.get(0).getTime();
		
		assessable = false;

	
		//goes through each entry for each objectId for each user
		for (CfAction action : actionsFilteredByObjectId){
			//gets the type from the action and sets it for the summary
			type = action.getCfObjects().get(0).getType();		
			calculateTime(action, startTime, endTime);
		}	

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
		return description;
	}
	public long getSummaryInfo(){
		return time;
	}

}

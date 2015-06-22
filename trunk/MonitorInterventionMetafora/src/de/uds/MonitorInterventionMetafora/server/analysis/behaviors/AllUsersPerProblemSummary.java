package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;

public class AllUsersPerProblemSummary {
	private String objectID;
	private List <String> userList = new Vector<String>();
	private int totalAttempted;
	private boolean assessable;
	private String description;
	
	
	
	public AllUsersPerProblemSummary(PerUserPerProblemSummary summary, String objectID){
		
		String user = summary.getUser();
		userList.add(user);
		
		this.objectID = objectID;
		totalAttempted = 1;
		this.assessable = summary.getAssessable();
	}
	
	
	
	public BehaviorInstance buildBehaviorInstance(){
		buildDescription();
		
		List <CfProperty >instanceProperties = new Vector<CfProperty>();
		instanceProperties.add(new CfProperty(RunestoneStrings.OBJECT_ID_STRING,String.valueOf(objectID)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_ATTEMPTED_STRING,String.valueOf(totalAttempted)));
		instanceProperties.add(new CfProperty(RunestoneStrings.IS_ASSESSABLE_STRING,String.valueOf(assessable)));
		instanceProperties.add(new CfProperty(RunestoneStrings.DESCRIPTION_STRING, description));
		
		BehaviorInstance instance = new BehaviorInstance(BehaviorType.ALL_USERS_PER_OBJECT_SUMMARY, userList, instanceProperties);
		return instance;
	}
	
	public void addInfo(PerUserPerProblemSummary summary){
		
		this.totalAttempted = totalAttempted + 1;
		
		String user = summary.getUser();
		this.userList.add(user);

	}
	
	private void buildDescription(){
		description = objectID + " was attempted by " + totalAttempted + " different user(s). This question is not assessable.";
		
	}
	
	public String getObjectID() {
		return objectID;
	}
	
	public List<String> getUserList() {
		return userList;
	}
	
	public int getTotalAttempted(){
		return totalAttempted;
	}
	
	public boolean getAssessable(){
		return assessable;
	}	
}

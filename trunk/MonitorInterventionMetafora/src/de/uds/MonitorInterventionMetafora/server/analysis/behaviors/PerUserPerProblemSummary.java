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
		
		this.user = currentUser;
		this.objectId = currentObjectId;
		long startTime = actionsFilteredByObjectId.get(0).getTime();
		long endTime = actionsFilteredByObjectId.get(0).getTime();
		isCorrect = false;
		assessable = true;
		numberTimesFalse = 0;
		falseEntries = "";
		type = "";
	
	
		//goes through each entry for each objectId for each user
		for (CfAction action : actionsFilteredByObjectId){
			
			long currentTime = action.getTime();
			String correctField = action.getCfContent().getPropertyValue(RunestoneStrings.CORRECT_STRING);
			String act = action.getCfObjects().get(0).getPropertyValue("ACT");
			type = action.getCfObjects().get(0).getType();
			
			
			if(currentTime < startTime){
				startTime = currentTime;
			}
			else if (currentTime > endTime){
				endTime = currentTime;
			}
			
			
			if(correctField == null){
				isCorrect = true;
				assessable = false;
			}
			else if (correctField.equalsIgnoreCase("true")){
				isCorrect = true;
			}
			else if(correctField.equalsIgnoreCase("false")){
				numberTimesFalse++;
				
				//gets the multiple choice input without the extra characters
				if(action.getCfObjects().get(0).getType().equalsIgnoreCase(RunestoneStrings.MCHOICE_STRING)){
					int startIndex = act.indexOf(":");
					startIndex = startIndex + 1;
					int endIndex = act.indexOf(":", (startIndex));	
					String actSubstring = act.substring(startIndex, endIndex);
					falseEntries = falseEntries + "/" + actSubstring;
				}
				else{
					falseEntries = falseEntries + "/" + act;
				}
			}
			
			
		}
		
		time = (endTime - startTime) / 1000;
		
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

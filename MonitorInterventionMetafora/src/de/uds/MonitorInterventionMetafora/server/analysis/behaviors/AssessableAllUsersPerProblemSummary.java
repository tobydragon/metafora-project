package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;

public class AssessableAllUsersPerProblemSummary extends AllUsersPerProblemSummary {

	
	private List <String> userList = new Vector<String>();
	private int totalAttempted;
	private boolean assessable;
	private int numCorrect;
	private String correctUsers;
	private int numBoth;
	private String bothUsers;
	private int numIncorrect;
	private String incorrectUsers;
	private String description;
	
	
	public AssessableAllUsersPerProblemSummary (AssessablePerUserPerProblemSummary summary, String objectID){
		super(summary, objectID);
		
		String user = summary.getUser();
		userList.add(user);
		

		totalAttempted = 1;
		this.assessable = summary.getAssessable();
		numCorrect = 0;
		correctUsers = "";
		numBoth = 0;
		bothUsers = "";
		numIncorrect = 0;
		incorrectUsers = "";
		
		//if question was answered false, update numIncorrect and incorrectUsers
		if (summary.isCorrect() == false){
			numIncorrect = 1;
			incorrectUsers = user + "/";
		}else{
			//if question was never answered false then update numCorrect and correctUsers
			if (summary.getNumberTimesFalse() == 0){
				numCorrect = 1;
				correctUsers = user + "/";
			}else{
				//if answered false then correct update numBoth and bothUsers
				numBoth = 1;
				bothUsers = user + "/";
			}
		}
	}

	
	public BehaviorInstance buildBehaviorInstance(){
		buildDescription();
		
		List <CfProperty >instanceProperties = new Vector<CfProperty>();
		instanceProperties.add(new CfProperty(RunestoneStrings.OBJECT_ID_STRING,String.valueOf(getObjectID())));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_ATTEMPTED_STRING,String.valueOf(totalAttempted)));
		instanceProperties.add(new CfProperty(RunestoneStrings.IS_ASSESSABLE_STRING,String.valueOf(assessable)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_NUMBER_CORRECT_STRING,String.valueOf(numCorrect)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_CORRECT_USERS_STRING,String.valueOf(correctUsers)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_NUMBER_BOTH_STRING,String.valueOf(numBoth)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_BOTH_USERS_STRING,String.valueOf(bothUsers)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_NUMBER_INCORRECT_STRING,String.valueOf(numIncorrect)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_INCORRECT_USERS_STRING,String.valueOf(incorrectUsers)));
		instanceProperties.add(new CfProperty(RunestoneStrings.DESCRIPTION_STRING, description));
		
		BehaviorInstance instance = new BehaviorInstance(BehaviorType.ASSESSABLE_ALL_USERS_PER_OBJECT_SUMMARY, userList, instanceProperties);
		return instance;
	}	
	

public void addInfo(AssessablePerUserPerProblemSummary summary){
		
		this.totalAttempted = totalAttempted + 1;
		
		String user = summary.getUser();
		this.userList.add(user);
		
		if (summary.isCorrect() == false){
			this.numIncorrect = numIncorrect + 1;
			this.incorrectUsers = incorrectUsers + user + "/";
		}else{
			if (summary.getNumberTimesFalse() == 0){
				this.numCorrect = numCorrect + 1;
				this.correctUsers = correctUsers + user + "/";
			}else{
				this.numBoth = numBoth + 1;
				this.bothUsers = bothUsers + user + "/";
			}
		}
		

	}

	private void buildDescription(){
		description = getObjectID() + " was attempted by " + totalAttempted + " different user(s).  There were "
				+ numCorrect + " only correct responses, " + numBoth + " first incorrect then correct responses, and "+ numIncorrect + " only incorrect responses."; 	
	}
	
	public int getNumCorrect(){
		return numCorrect;
	}
	
	public String getCorrectUsers(){
		return correctUsers;
	}
	
	public int getNumBoth(){
		return numBoth;
	}
	
	public String getBothUsers(){
		return bothUsers;
	}
	
	public int getNumIncorrect(){
		return numIncorrect;
	}
	
	public String getIncorrectUsers(){
		return incorrectUsers;
	}
	
}
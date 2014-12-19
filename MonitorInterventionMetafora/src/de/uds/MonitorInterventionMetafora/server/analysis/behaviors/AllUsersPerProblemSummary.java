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
	private int numCorrect;
	private String correctUsers;
	private int numBoth;
	private String bothUsers;
	private int numIncorrect;
	private String incorrectUsers;
	
	public AllUsersPerProblemSummary(String objectID, List<String> userList, int totalAttempted, boolean assessable, int numCorrect, String correctUsers, int numBoth, String bothUsers,
			int numIncorrect, String incorrectUsers) {
		super();
		this.objectID = objectID;
		this.userList = userList;
		this.totalAttempted = totalAttempted;
		this.assessable = assessable;
		this.numCorrect = numCorrect;
		this.correctUsers = correctUsers;
		this.numBoth = numBoth;
		this.bothUsers = bothUsers;
		this.numIncorrect = numIncorrect;
		this.incorrectUsers = incorrectUsers;
	}
	
	public BehaviorInstance buildBehaviorInstance(){
		List <CfProperty >instanceProperties = new Vector<CfProperty>();
		instanceProperties.add(new CfProperty(RunestoneStrings.OBJECT_ID_STRING,String.valueOf(objectID)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_ATTEMPTED_STRING,String.valueOf(totalAttempted)));
		instanceProperties.add(new CfProperty(RunestoneStrings.IS_ASSESSABLE_STRING,String.valueOf(assessable)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_NUMBER_CORRECT_STRING,String.valueOf(numCorrect)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_CORRECT_USERS_STRING,String.valueOf(correctUsers)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_NUMBER_BOTH_STRING,String.valueOf(numBoth)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_BOTH_USERS_STRING,String.valueOf(bothUsers)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_NUMBER_INCORRECT_STRING,String.valueOf(numIncorrect)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_INCORRECT_USERS_STRING,String.valueOf(incorrectUsers)));
		
		BehaviorInstance instance = new BehaviorInstance(BehaviorType.ALL_USERS_PER_OBJECT_SUMMARY, userList, instanceProperties);
		return instance;
	}
	
	public void addInfo(PerUserPerProblemSummary summary){
		
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

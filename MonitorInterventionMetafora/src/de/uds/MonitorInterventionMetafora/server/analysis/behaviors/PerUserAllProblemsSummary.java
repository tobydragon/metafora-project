package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;

public class PerUserAllProblemsSummary {
	private String user;
	private int totalAttempted;
	private int numNotAssessable;
	private String notAssessableQuestions;
	private int numCorrect;
	private String correctQuestions;
	private int numIncorrect;
	private String incorrectQuestions;
	private int numOthers;
	private long totalTime;
	private String description;
	

	

	public PerUserAllProblemsSummary (PerUserPerProblemSummary summary, String oldUser){
		
		this.user = oldUser;
		totalAttempted = 1;
		numNotAssessable = 0;
		notAssessableQuestions = "";
		numCorrect = 0;
		correctQuestions = "";
		numIncorrect = 0;
		incorrectQuestions = "";
		numOthers = 0;
		totalTime = summary.getTime();
		
		
	//currently this is only updating the correct fields if the question is non assessable
	//isCorrect is defaulted to true for non assessable questions	
		if(summary.getAssessable() == false){
			numNotAssessable = 1;
			notAssessableQuestions = summary.getObjectId() + "/";
		}else if(summary.isCorrect() == true){
			numCorrect = 1;
			correctQuestions = summary.getObjectId() + "/";
		}else{
			numIncorrect = 1;
			incorrectQuestions = summary.getObjectId() + "/";
		}
		
	}
	
		
	
	public BehaviorInstance buildBehaviorInstance(){
		List <CfProperty >instanceProperties = new Vector<CfProperty>();
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_ATTEMPTED_STRING,String.valueOf(totalAttempted)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_NUMBER_NOT_ASSESSABLE_STRING,String.valueOf(numNotAssessable)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_NOT_ASSESSABLE_ANSWERS_STRING, String.valueOf(notAssessableQuestions)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_NUMBER_CORRECT_STRING, String.valueOf(numCorrect)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_CORRECT_ANSWERS_STRING, String.valueOf(correctQuestions)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_NUMBER_INCORRECT_STRING, String.valueOf(numIncorrect)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_INCORRECT_ANSWERS_STRING, String.valueOf(incorrectQuestions)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_NUMBER_OTHERS_STRING, String.valueOf(numOthers)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TOTAL_TIME_SPENT_STRING, String.valueOf(totalTime)));
		instanceProperties.add(new CfProperty(RunestoneStrings.DESCRIPTION_STRING, description));
		
		List <String> userList = new Vector<String>();
		userList.add(user);
		
		BehaviorInstance instance = new BehaviorInstance(BehaviorType.PER_USER_ALL_OBJECTS_SUMMARY, userList, instanceProperties);
		return instance;
	}

	public String getUser() {
		return user;
	}

	public int getTotalAttempted() {
		return totalAttempted;
	}
	
	public int getNumNotAssessable() {
		return numNotAssessable;
	}
	
	public String getNotAssessableQuestions(){
		return notAssessableQuestions;
	}

	public int getNumCorrect() {
		return numCorrect;
	}
	
	public String getCorrectQuestions(){
		return correctQuestions;
	}
	
	public int getNumIncorrect(){
		return numIncorrect;
	}
	
	public String getIncorrectQuestions(){
		return incorrectQuestions;
	}
	
	
	public void addInfo(PerUserPerProblemSummary summary){
	
		this.totalAttempted = totalAttempted + 1;
		this.totalTime = totalTime + summary.getTime();
		
		//only update correct/incorrect fields if the question is assessable 
		if (summary.getAssessable() == false){
			this.numNotAssessable = numNotAssessable + 1;
			this.notAssessableQuestions = notAssessableQuestions + "/" + summary.getObjectId();
		}else if (summary.isCorrect() == true){
			this.numCorrect = numCorrect + 1;
			this.correctQuestions = correctQuestions + "/" + summary.getObjectId();
		}
		else if (summary.isCorrect() == false){
			this.numIncorrect = numIncorrect + 1;
			this.incorrectQuestions = incorrectQuestions + "/" + summary.getObjectId();
		}

	}

	public void buildDescription(){
		description = user + " spent " + totalTime + " seconds on " + totalAttempted + " questions.  There were "
				+ numCorrect + " correct responses, " + numIncorrect + " incorrect responses, and " + numNotAssessable + " not assessable questions."; 
			
	}
	
	
	public int getNumOthers(){
		return numOthers;
	}
	
	public long getTotalTime() {
		return totalTime;
	}
	
	public String toString(){
		return buildBehaviorInstance().toString() + "Total Attempted: " + getTotalAttempted() + " Number Correct: " + getNumCorrect();
	}
}

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
	
	public PerUserAllProblemsSummary(String user, int totalAttempted, int numNotAssessable, String notAssessableQuestions, int numCorrect, String correctQuestions, int numIncorrect, 
			String incorrectQuestions, int numOthers, long totalTime) {
		super();
		this.user = user;
		this.totalAttempted = totalAttempted;
		this.numNotAssessable = numNotAssessable;
		this.notAssessableQuestions = notAssessableQuestions;
		this.numCorrect = numCorrect;
		this.correctQuestions = correctQuestions;
		this.numIncorrect = numIncorrect;
		this.incorrectQuestions = incorrectQuestions;
		this.numOthers = numOthers;
		this.totalTime = totalTime;
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
	
	public void addTotalAttempted(){
		this.totalAttempted = totalAttempted + 1;
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
	
	public void addQuestion(boolean attempt, boolean assessable, String objectID) {
		if (assessable = false){
			this.numNotAssessable = numNotAssessable + 1;
			this.notAssessableQuestions = notAssessableQuestions + "/" + objectID;
		}
		if (attempt == true){
			this.numCorrect = numCorrect + 1;
			this.correctQuestions = correctQuestions + "/" + objectID;
		}
		else if (attempt == false){
			this.numIncorrect = numIncorrect + 1;
			this.incorrectQuestions = incorrectQuestions + "/" + objectID;
		}
	}
	
	public int getNumOthers(){
		return numOthers;
	}
	
	public long getTotalTime() {
		return totalTime;
	}

	public void addTotalTime(long time){
		this.totalTime = totalTime + time;
	}
	
	public String toString(){
		return buildBehaviorInstance().toString() + "Total Attempted: " + getTotalAttempted() + " Number Correct: " + getNumCorrect();
	}
}

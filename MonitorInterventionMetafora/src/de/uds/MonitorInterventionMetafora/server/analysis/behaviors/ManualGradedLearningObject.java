package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.ArrayList;
import java.util.List;

public class ManualGradedLearningObject {
	String id;
	double maxPossibleScore;
	List<ManualGradedResponse> studentAnswers;
	
	public ManualGradedLearningObject(String id, double max){
		this.id = id;
		this.maxPossibleScore = max;
		this.studentAnswers = new ArrayList<ManualGradedResponse>();
	}
	
	public void addStudentAnswer(ManualGradedResponse studentA){
		this.studentAnswers.add(studentA);
	}
	
	public List<ManualGradedResponse> getManualGradedResponses(){
		return studentAnswers;
	}
	
	public String getID(){
		return this.id;
	}
	
	public double getMaxPossibleScore(){
		return this.maxPossibleScore;
	}
}

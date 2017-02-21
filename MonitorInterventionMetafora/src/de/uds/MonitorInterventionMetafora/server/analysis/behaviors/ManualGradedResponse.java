package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.Concept;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.GraphConstants;

public class ManualGradedResponse implements Concept{
	String questionID;
	double maxScore;
	double studentScore;
	String studentID;
	double normalizedScore;

	public ManualGradedResponse(String id, double max, double studScore, String stuId){
		this.questionID = id;
		this.maxScore = max;
		this.studentScore = studScore;
		this.studentID = stuId;
		this.normalizedScore = studentScore/maxScore;
	}
	@Override
	public String getConceptTitle() {
		// TODO Auto-generated method stub
		return questionID;
	}

	@Override
	public double getDataImportance() {
		// TODO Auto-generated method stub
		return GraphConstants.HAND_GRADED_QUESTIONS_WEIGHT;
	}

	@Override
	public double getScore() {
		// TODO Auto-generated method stub
		return normalizedScore;
	}
	
	public double getRealScore(){
		return studentScore;
	}
	
	public double getMaxScore(){
		return maxScore;
	}
	public String getStudentID(){
		return studentID;
	}
}

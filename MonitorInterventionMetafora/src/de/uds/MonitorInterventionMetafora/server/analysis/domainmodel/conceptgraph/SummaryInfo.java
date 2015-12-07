package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.List;

public class SummaryInfo {
	

	private List<String> users;
	public long time;
	private List<String> answeredObjectIds;
	private List<String> unansweredObjectIds;
	
	//represents the number of assessable questions that exist in that section of the graph
	private int numAssessable;
	//represents the number of times a false entry was submitted in that section of the graph
	private int totalTimesFalse;
//	private String conceptType;
	int numCorrect;
	private int numSummaries;
	
	
	
	
public SummaryInfo(List<String> userList, long timeIn, List<String> objectIdList, int numAssessableIn, int numTimesFalse, int numCorrectIn, int numSummariesIn) {
		
		// TODO Auto-generated constructor stub

		users = userList;
		time = timeIn;
		answeredObjectIds = objectIdList;
		unansweredObjectIds = new ArrayList<String>();
		numAssessable = numAssessableIn;
		
		totalTimesFalse = numTimesFalse;
		numCorrect = numCorrectIn;
//		actualComp = actualCompIn;
//		predictedComp = predictedCompIn;
		numSummaries = numSummariesIn;
	}
	
	public SummaryInfo(){
		users = new ArrayList<String>();
		time = 0;
		answeredObjectIds = new ArrayList<String>();
		unansweredObjectIds = new ArrayList<String>();
		numAssessable = 0;
		totalTimesFalse = 0;
		numCorrect = 0;
//		actualComp = 0;
//		predictedComp = 0;
		numSummaries = 0;
		
		
	}

	
	public List<String> getUsers(){
		return users;
	}
	public long getTime(){
		return time;
	}
	public List<String> getObjectIds(){
		return answeredObjectIds;
	}
	public List<String> getUnansweredObjectIds(){
		return unansweredObjectIds;
	}
	
	public int getNumAssessable(){
		return numAssessable;
	}
	public int getTotalFalseEntries(){
		return totalTimesFalse;
	}

	public int getNumSummaries(){
		return  numSummaries;
	}
	

	public void update (ConceptNode nodeIn, SummaryInfo currentSumInfo){

		//this makes sure that there are no duplicates in the list
		users.removeAll(currentSumInfo.getUsers());
		users.addAll(currentSumInfo.getUsers());
		
		answeredObjectIds.removeAll(currentSumInfo.getObjectIds());
		answeredObjectIds.addAll(currentSumInfo.getObjectIds());
		
		
			
		if(currentSumInfo.getObjectIds().size() == 0 && nodeIn.getChildren().isEmpty() == true){
			if(nodeIn.getConcept().getClass().getName().contains("Question") == true){
			unansweredObjectIds.add(nodeIn.getConcept().getConceptTitle());
			}
		}
		
		unansweredObjectIds.removeAll(currentSumInfo.getUnansweredObjectIds());
		unansweredObjectIds.addAll(currentSumInfo.getUnansweredObjectIds());
		
		time = time + currentSumInfo.getTime();		
		numAssessable = numAssessable + currentSumInfo.getNumAssessable();
		totalTimesFalse = totalTimesFalse + currentSumInfo.getTotalFalseEntries();
		numSummaries = numSummaries + currentSumInfo.getNumSummaries();
	}

	public String toString(){
		
		String stringToReturn = "NumFalse: " + totalTimesFalse + " NumCorrect: " + numCorrect;
		
//		String stringToReturn = ("Actual Comp: " + actualComp);
//		String stringToReturn = " numAsses " + numAssessable + " False: " + totalTimesFalse + " Users: "
//				+ users.size() + " answeredObjectIds: " + answeredObjectIds.size() + " unansweredObjectIds: " + unansweredObjectIds.size()+ " Time: " + time 
//				+ " ActualComp: " + actualComp + " PredictedComp: " + predictedComp + " NumSummaries: " + numSummaries;
		return stringToReturn;
	}
	
	public int getNumCorrect() {
		return numCorrect;
	}
	
	
	
}



	
		
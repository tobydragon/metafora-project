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
	
	private double actualComp;
	private double predictedComp;
	private int numSummaries;
	
	
	
	
	public SummaryInfo(List<String> userList, long timeIn, List<String> objectIdList, int numAssessableIn, int numTimesFalse, double actualCompIn, double predictedCompIn, int numSummariesIn) {
		
		// TODO Auto-generated constructor stub

		users = userList;
		time = timeIn;
		answeredObjectIds = objectIdList;
		unansweredObjectIds = new ArrayList<String>();
		numAssessable = numAssessableIn;
		totalTimesFalse = numTimesFalse;
		actualComp = actualCompIn;
		predictedComp = predictedCompIn;
		numSummaries = numSummariesIn;
	}
	
	public SummaryInfo(){
		users = new ArrayList<String>();
		time = 0;
		answeredObjectIds = new ArrayList<String>();
		unansweredObjectIds = new ArrayList<String>();
		numAssessable = 0;
		totalTimesFalse = 0;
		actualComp = 0;
		predictedComp = 0;
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
	public double getActualComp(){
		return actualComp;
	}
	public double getPredictedComp(){
		return predictedComp;
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
		
		
		
		double tempActual = 0;
		double tempPredicted = 0;
		if(nodeIn.getChildren().size() == 0){
			actualComp = currentSumInfo.getActualComp();
			predictedComp = currentSumInfo.getPredictedComp();
		}
		else{
			System.out.println("NodeIn: " + nodeIn.getConcept().getConceptTitle());
			
			for(ConceptNode child : nodeIn.getChildren()){
				tempActual = tempActual + child.getSummaryInfo().getActualComp();
				tempPredicted += child.getSummaryInfo().getPredictedComp();	
			}
			
			System.out.println("tempActual: " + tempActual + " nodeIn.children/size: " + nodeIn.getChildren().size());
			actualComp = tempActual / (nodeIn.getChildren().size());
			System.out.println("actualComp: " + actualComp  + "\n");
			predictedComp = tempPredicted / (nodeIn.getChildren().size());
		}
	}

	public String toString(){
		String stringToReturn = ("Actual Comp: " + actualComp);
//		String stringToReturn = " numAsses " + numAssessable + " False: " + totalTimesFalse + " Users: "
//				+ users.size() + " answeredObjectIds: " + answeredObjectIds.size() + " unansweredObjectIds: " + unansweredObjectIds.size()+ " Time: " + time 
//				+ " ActualComp: " + actualComp + " PredictedComp: " + predictedComp + " NumSummaries: " + numSummaries;
		return stringToReturn;
	}
	
	private int getRealScore() {
		// not sure if this goes here, but it needs to be only called on the lowest tier
		// of nodes (the one's going up will be calculated by taking averages)
		
		// also need to make sure it actually was answered, otherwise we should return
		// 0 as its actual score will be 0 (predicted will be based on stuff above)
		
		// sudo code until we have a better idea of how this will work
		// if lowest tier
		//     if hasBeenAnswered
		//         score = 0
		//		   if gotItCorrect
		//             score += .5
		//		   if totalTimesWrong == 0
		//			   score += .4
		//		   if totalTimesWrong == 1
		//			   score += .3
		//		   if totalTimesWrong == 2
		//			   score += .15
		//	       
		//     else
		//         return 0
		// else
		//     calculate score from below
		
		
		return 0;
	}

	public void setActualComp(double actualComp) {
		this.actualComp = actualComp;
	}

	public void setPredictedComp(double predictedComp) {
		this.predictedComp = predictedComp;
	}
	
	
}



	
		
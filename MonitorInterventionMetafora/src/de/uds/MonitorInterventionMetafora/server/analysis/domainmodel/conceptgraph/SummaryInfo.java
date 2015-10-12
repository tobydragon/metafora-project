package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.List;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class SummaryInfo {
	

	private List<String> users;
	public long time;
	private List<String> objectIds;
//	private List<String> unansweredObjectIds;
	
	//represents the number of assessable questions that exist in that section of the graph
	private int numAssessable;
	//represents the number of times a false entry was submitted in that section of the graph
	private int totalTimesFalse;
//	private String conceptType;
	
	
	
	
	public SummaryInfo(List<String> userList, long timeIn, List<String> objectIdList, int numAssessableIn, int numTimesFalse) {
		
		// TODO Auto-generated constructor stub

		users = userList;
		time = timeIn;
		objectIds = objectIdList;
//		unansweredObjectIds = unansweredObjectIdList;
		numAssessable = numAssessableIn;
		totalTimesFalse = numTimesFalse;
//		conceptType = conceptTypeIn;
	}
	
	
	
	public SummaryInfo(){
		users = new ArrayList<String>();
		time = 0;
		objectIds = new ArrayList<String>();
		numAssessable = 0;
		totalTimesFalse = 0;
		
	}
	
//	public SummaryInfo(String conceptTypeIn){
//		users = new ArrayList<String>();
//		time = 0;
//		objectIds = new ArrayList<String>();
//		unansweredObjectIds = new ArrayList<String>();
//		numAssessable = 0;
//		totalTimesFalse = 0;
//		conceptType = conceptTypeIn;
//		
//	}


	public List<String> getUsers(){
		return users;
	}
	public long getTime(){
		return time;
	}
	public List<String> getObjectIds(){
		return objectIds;
	}
//	public List<String> getUnansweredObjectIds(){
//		return unansweredObjectIds;
//	}
	
	public int getNumAssessable(){
		return numAssessable;
	}
	public int getTotalFalseEntries(){
		return totalTimesFalse;
	}
	
	public void update (SummaryInfo currentSumInfo){

		//this makes sure that there are no duplicates in the list
		users.removeAll(currentSumInfo.getUsers());
		users.addAll(currentSumInfo.getUsers());
		
		objectIds.removeAll(currentSumInfo.getObjectIds());
		objectIds.addAll(currentSumInfo.getObjectIds());
		
		
		//needs something that checks if it is a Question object and whether or not it has any children(summaires)
			//no summaries means no student submissions

		//unansweredObjectIds.removeAll(currentSumInfo.getUnansweredObjectIds());
		//unansweredObjectIds.addAll(currentSumInfo.getUnansweredObjectIds());
		
		
		time = time + currentSumInfo.getTime();		
		numAssessable = numAssessable + currentSumInfo.getNumAssessable();
		totalTimesFalse = totalTimesFalse + currentSumInfo.getTotalFalseEntries();
		
		}

	public String toString(){
		
		String stringToReturn = " numAsses " + numAssessable + " False: " + totalTimesFalse + " Users: "
				+ users.size() + " ObjectIds: " + objectIds.size() + " Time: " + time;
		return stringToReturn;
	}
}



	
		
package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.List;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class SummaryInfo {
	

	private List<String> users;
	public long time;
	private List<String> objectIds;
	
	//represents the number of assessable questions that exist in that section of the graph
	private int numAssessable;
	//represents the number of times a false entry was submitted in that section of the graph
	private int totalTimesFalse;
	
	
	
	
	public SummaryInfo(List<String> userList, long timeIn, List<String> objectIdList, int numAssessableIn, int numTimesFalse) {
		
		// TODO Auto-generated constructor stub

		users = userList;
		time = timeIn;
		objectIds = objectIdList;
		numAssessable = numAssessableIn;
		totalTimesFalse = numTimesFalse;
		

	}
	
	public SummaryInfo(){
		users = new ArrayList<String>();
		time = 0;
		objectIds = new ArrayList<String>();
		numAssessable = 0;
		totalTimesFalse = 0;
		
	}

	public List<String> getUsers(){
		return users;
	}
	public long getTime(){
		return time;
	}
	public List<String> getObjectIds(){
		return objectIds;
	}
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
		
		//this makes sure that there are no duplicates in the list
		objectIds.removeAll(currentSumInfo.getObjectIds());
		objectIds.addAll(currentSumInfo.getObjectIds());
		
		time = time + currentSumInfo.getTime();		
		numAssessable = numAssessable + currentSumInfo.getNumAssessable();
		totalTimesFalse = totalTimesFalse + currentSumInfo.getTotalFalseEntries();
	}

	public String toString(){
		
		String stringToReturn = " numAsses " + numAssessable + " False: " + totalTimesFalse + " Users: " + users.size() + " ObjectIds: " + objectIds.size() + " Time: " + time;
		return stringToReturn;
	}
}



	
		
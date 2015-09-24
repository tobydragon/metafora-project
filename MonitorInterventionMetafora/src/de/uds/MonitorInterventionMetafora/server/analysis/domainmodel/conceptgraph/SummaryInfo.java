package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.List;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class SummaryInfo {
	

	private List<String> users;
	public long time;
	private List<String> objectIds;

	
	public SummaryInfo(List<String> userList, long timeIn, List<String> objectIdList) {
		
		// TODO Auto-generated constructor stub
	

		users = userList;
		time = timeIn;
		objectIds = objectIdList;
		

	}
	
	public SummaryInfo(){
		users = new ArrayList<String>();
		time = 0;
		objectIds = new ArrayList<String>();
		
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
	
	public void update (SummaryInfo currentSumInfo){
		
		users.addAll(currentSumInfo.getUsers());
		objectIds.addAll(currentSumInfo.getObjectIds());
		time = time + currentSumInfo.getTime();		
	}

	public String toString(){
		
		String stringToReturn = "Users: " + users.size() + " ObjectIds: " + objectIds.size() + " Time: " + time;
		return stringToReturn;
	}
}



	
		
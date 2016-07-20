package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;

public class ObjectSummaryIdentifier implements BehaviorIdentifier{
	Logger logger = Logger.getLogger(this.getClass());

	@Override
	/**
	 * runs all available object analysis, and returns all corresponding behavior
	 */
	public List<BehaviorInstance> identifyBehaviors(List<CfAction> actionsToConsider, List<String> involvedUsers,List<CfProperty> groupProperties){
		List<BehaviorInstance> identifiedBehaviors = new Vector<BehaviorInstance>();
		
		List<PerUserPerProblemSummary> perUserPerProblemSummaries = buildPerUserPerProblemSummaries(actionsToConsider, involvedUsers);
		for (PerUserPerProblemSummary summary : perUserPerProblemSummaries){
			identifiedBehaviors.add(summary.buildBehaviorInstance());
		}
		
		List<AllUsersPerProblemSummary> allUserPerObjectSummaries = buildAllUsersPerObjectSummaries(perUserPerProblemSummaries);
		for (AllUsersPerProblemSummary summary : allUserPerObjectSummaries){
			identifiedBehaviors.add(summary.buildBehaviorInstance());
		}
		
		List<PerUserAllProblemsSummary> perUserAllObjectsSummaries = buildPerUserAllObjectsSummaries(perUserPerProblemSummaries);
		for (PerUserAllProblemsSummary summary : perUserAllObjectsSummaries){
			identifiedBehaviors.add(summary.buildBehaviorInstance());
		}
		
		return identifiedBehaviors;
	}
	
	public List<PerUserPerProblemSummary> buildPerUserPerProblemSummaries(List<CfAction> actionsToConsider, List<String> involvedUsers){
		List<PerUserPerProblemSummary> perUserPerProblemSummaries = new Vector<PerUserPerProblemSummary>();
		
		List<String> objectIds = new Vector<String>();
		for (CfAction action : actionsToConsider){
					
				//This creates a list of all the object Ids
				String currectObjectId = action.getCfObjects().get(0).getId();
				
				if ((objectIds.contains(currectObjectId)) == false){
					objectIds.add(action.getCfObjects().get(0).getId());
				}
		}
		
		//goes through each user
		for(String user : involvedUsers){
			
			ActionFilter userFilter = BehaviorFilters.createUserFilter(user);		
			
			//list of all the actions for the current user
			List<CfAction> actionsFilteredByUser = userFilter.getFilteredList(actionsToConsider);
			
		    //goes through each objectId for each user
			for(String objectId : objectIds){	
				
				ActionFilter objectIdFilter = BehaviorFilters.createObjectIdFilter(objectId);
				
				//list of all actions for the current user for the current objectId
				List<CfAction> actionsFilteredByObjectId = objectIdFilter.getFilteredList(actionsFilteredByUser);
				
				//if there is at least one action in the list, create a PerUserPerProblemSummary 
				if (actionsFilteredByObjectId.size() > 0){

					
					//get the first action in the list and check the correct field, if it is null then the question is non assessable
					//only need to check the first entry as the list is filtered by objectId
					//if non assessable then create a PerUserPerProblemSummary
					PerUserPerProblemSummary summary;
					if(actionsFilteredByObjectId.get(0).getCfContent().getPropertyValue(RunestoneStrings.CORRECT_STRING) == null){
						summary =  new PerUserPerProblemSummary(actionsFilteredByObjectId, user, objectId){};
					}
					//if the field isn't null then it is assessable, so create an AssessablePerUserPerProblemSummary
					else{
						summary = new AssessablePerUserPerProblemSummary(actionsFilteredByObjectId, user, objectId);
					}

					perUserPerProblemSummaries.add(summary);
				}
			}
		}
		return perUserPerProblemSummaries;
	}
	
	public List<AllUsersPerProblemSummary> buildAllUsersPerObjectSummaries(List<PerUserPerProblemSummary> perUserPerProblemSummaries){
		
		List<AllUsersPerProblemSummary> allUsersPerProblemSummaries = new Vector<AllUsersPerProblemSummary>();


		AllUsersPerProblemSummary firstSummary;

		//TODO: remove instance of call
		//check the class name to determine if the summary is for an assessable question or not and then create the corresponding AllUsersPerProblemSummary
		if(perUserPerProblemSummaries.get(0).getClass().getName().contains("AssessablePerUserPerProblemSummary")){
			firstSummary = new AssessableAllUsersPerProblemSummary((AssessablePerUserPerProblemSummary)perUserPerProblemSummaries.get(0), perUserPerProblemSummaries.get(0).getObjectId());
		}else{
			firstSummary = new AllUsersPerProblemSummary(perUserPerProblemSummaries.get(0), perUserPerProblemSummaries.get(0).getObjectId());
		}

		//adds summary to list
		allUsersPerProblemSummaries.add(firstSummary);
		
		//go through the behavior list
		for(int i=1; i<perUserPerProblemSummaries.size(); i++){
			
			
			//get the per user per problem summary
			PerUserPerProblemSummary summary = perUserPerProblemSummaries.get(i);
	
			
			//get the object ID from the per user per problem summary
			String oldID = summary.getObjectId();
			boolean addedSummary = false;
			int j = 0;
			
			while(j<allUsersPerProblemSummaries.size() && !addedSummary){
				//get the object ID from the per user all problems summary
				String newID = allUsersPerProblemSummaries.get(j).getObjectID();
				//see if that ID has already been added to the list
				if(oldID.equals(newID)){
					//update user list
					
					//TODO: remove instance of call 
					//check the class name to determine if the summary is for an assessable question or not update with info from the new summary
					if(allUsersPerProblemSummaries.get(j).getClass().getName().contains("AssessableAllUsersPerProblemSummary")){
						AssessableAllUsersPerProblemSummary tempSummary = (AssessableAllUsersPerProblemSummary)allUsersPerProblemSummaries.get(j);
						if(summary.getClass().getName().contains("AssessablePerUserPerProblemSummary")){
							tempSummary.addInfo((AssessablePerUserPerProblemSummary)summary);
						}
						}else{
							allUsersPerProblemSummaries.get(j).addInfo(summary);
					}

					addedSummary = true;					
				}
				j++;
			}
			
			if(addedSummary == false){
				
				AllUsersPerProblemSummary newSummary;
				//TODO: remove instance of call
				//check the class name to determine if the summary is for an assessable question or not and then create the corresponding AllUsersPerProblemSummary
				if(summary.getClass().getName().contains("AssessablePerUserPerProblemSummary")){
					newSummary = new AssessableAllUsersPerProblemSummary((AssessablePerUserPerProblemSummary)summary, oldID);
				}else{
					newSummary = new AllUsersPerProblemSummary(summary, oldID);
				}
				
				//adds summary to list
				allUsersPerProblemSummaries.add(newSummary);
			}
			
		}
		
		logger.debug(allUsersPerProblemSummaries);
		return allUsersPerProblemSummaries;
	}	
	
	public List <PerUserAllProblemsSummary> buildPerUserAllObjectsSummaries(List<PerUserPerProblemSummary> perUserPerProblemSummaries){
		
		List<PerUserAllProblemsSummary> perUserAllProblemsSummaries = new Vector<PerUserAllProblemsSummary>();		
		PerUserAllProblemsSummary firstSummary;

		//check the class name to determine if the summary is for an assessable question or not and call the corresponding constructor
		if(perUserPerProblemSummaries.get(0).getClass().getName().contains("AssessablePerUserPerProblemSummary")){
			firstSummary = new PerUserAllProblemsSummary((AssessablePerUserPerProblemSummary)perUserPerProblemSummaries.get(0), perUserPerProblemSummaries.get(0).getUser());
		}else{
			firstSummary = new PerUserAllProblemsSummary(perUserPerProblemSummaries.get(0), perUserPerProblemSummaries.get(0).getUser());
		}

		//adds summary to list
		perUserAllProblemsSummaries.add(firstSummary);
		
		//go through the behavior list
		for(int i=1; i<perUserPerProblemSummaries.size(); i++){
			
			//get the per user per problem summary
			PerUserPerProblemSummary summary = perUserPerProblemSummaries.get(i);
			//get the user name from the per user per problem summary
			String oldUser = summary.getUser();
			boolean addedSummary = false;
			int j = 0;
			
			while(j<perUserAllProblemsSummaries.size() && !addedSummary){
				//get the user name from the per user all problems summary
				String newUser = perUserAllProblemsSummaries.get(j).getUser();
				
				//see if that user has already been added to the list
				if(oldUser.equals(newUser)){
					//add the information to already made newUser
					
					//TODO: remove instance of call
					//check the class name to determine if the summary is for an assessable question or not then call the corresponding addInfo method
					if(summary.getClass().getName().contains("AssessablePerUserPerProblemSummary")){
						perUserAllProblemsSummaries.get(j).addInfo((AssessablePerUserPerProblemSummary)summary);					
						addedSummary = true;
					}else{
						perUserAllProblemsSummaries.get(j).addInfo(summary);	
						addedSummary = true;
					}
				}
			
				j++;
			}
			
			//if the summary wasn't added to an existing summary, create a new summary 
			if (addedSummary == false){		
				
				PerUserAllProblemsSummary newSummary;
				//TODO: remove instance of call
				//check the class name to determine if the summary is for an assessable question or not and then call the corresponding constructor
				if(summary.getClass().getName().contains("AssessablePerUserPerProblemSummary")){
					newSummary = new PerUserAllProblemsSummary((AssessablePerUserPerProblemSummary)summary, oldUser);	
				}else{
					newSummary = new PerUserAllProblemsSummary(summary, oldUser);	
				}

				//adds summary to list
				perUserAllProblemsSummaries.add(newSummary);						
			}	
		}
		
		logger.debug(perUserAllProblemsSummaries);
		return perUserAllProblemsSummaries;
	}
	
	//takes in a list of users to filter by and a list of PerUserPerProblemSummaries
	//if userList is empty or null, it returns the original list of summaries
	public List<PerUserPerProblemSummary> filterSummariesByUser(List<String> userList, List<PerUserPerProblemSummary> summaries){
		
		//if the user list is null, or empty, return the original list of summaries
		if(userList == null){
			return summaries;
		}
		else if(userList.isEmpty() == true){
			return summaries;
		}
		
		//initialize a list of summaries to return
		List<PerUserPerProblemSummary> filteredSummaries = new Vector<PerUserPerProblemSummary>();
		
		//go through each summary and compare the user to the list of users, if the summary user is in the user list, add that summary to filteredSummaries
		for(PerUserPerProblemSummary summary : summaries){
			if(userList.contains(summary.getUser())){
				filteredSummaries.add(summary);
			}
		}
		return filteredSummaries;
	}

}


	

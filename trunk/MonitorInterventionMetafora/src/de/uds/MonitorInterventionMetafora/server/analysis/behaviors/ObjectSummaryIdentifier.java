package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;

public class ObjectSummaryIdentifier implements BehaviorIdentifier{
	Logger log = Logger.getLogger(this.getClass());
	
	ActionFilter userFilter;
	ActionFilter objectIdFilter;

	@Override
	public List<BehaviorInstance> identifyBehaviors(List<CfAction> actionsToConsider, List<String> involvedUsers,List<CfProperty> groupProperties) {
		List<BehaviorInstance> identifiedBehaviors = new Vector<BehaviorInstance>();
		List<PerUserPerProblemSummary> perUserPerProblemSummaries = new Vector<PerUserPerProblemSummary>();
		//TODO @Caitlin: this is where we get all the actions (actions to consider) and you return a list of BehaviorInstances, one for each object (problem)
		//create instance for each student each problem
		
		
		
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
			
			userFilter = BehaviorFilters.createUserFilter(user);		
			
			//list of all the actions for the current user
			List<CfAction> actionsFilteredByUser = userFilter.getFilteredList(actionsToConsider);
			
			    //goes through each objectId for each user
				for(String objectId : objectIds){	
					
					objectIdFilter = BehaviorFilters.createObjectIdFilter(objectId);
					
					//list of all actions for the current user for the current objectId
					List<CfAction> actionsFilteredByObjectId = objectIdFilter.getFilteredList(actionsFilteredByUser);
					
					//if there is at least one action in the list, create a PerUserPerObjectSummary 
					if (actionsFilteredByObjectId.size() > 0){
						PerUserPerProblemSummary summary = new PerUserPerProblemSummary(actionsFilteredByObjectId, user, objectId);
						perUserPerProblemSummaries.add(summary);
						identifiedBehaviors.add(summary.buildBehaviorInstance());
					}
				}
			}
		
		List <BehaviorInstance> perUserSummary = buildPerUserAllObjectsSummaries(perUserPerProblemSummaries);
		identifiedBehaviors.addAll(perUserSummary);
		
		List <BehaviorInstance> perObjectSummary = buildAllUsersPerObjectSummaries(perUserPerProblemSummaries);
		identifiedBehaviors.addAll(perObjectSummary);
		
		return identifiedBehaviors;
	}
	
	private List <BehaviorInstance> buildAllUsersPerObjectSummaries(List<PerUserPerProblemSummary> perUserPerProblemSummaries){
		List<BehaviorInstance> userBehaviors = new Vector<BehaviorInstance>();
		List<AllUsersPerProblemSummary> allUsersPerProblemSummaries = new Vector<AllUsersPerProblemSummary>();

		AllUsersPerProblemSummary firstSummary = new AllUsersPerProblemSummary(perUserPerProblemSummaries.get(0), perUserPerProblemSummaries.get(0).getObjectId());

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
					allUsersPerProblemSummaries.get(j).addInfo(summary);
					addedSummary = true;
				}
				j++;
			}
			
			if(addedSummary == false){
				//add a new ID to the allUsersPerProblemSummaries
				AllUsersPerProblemSummary newSummary = new AllUsersPerProblemSummary(summary, oldID);
				//adds summary to list
				allUsersPerProblemSummaries.add(newSummary);
			}
			
		}
		
		log.debug(allUsersPerProblemSummaries);
		for(AllUsersPerProblemSummary newSummary: allUsersPerProblemSummaries){
			//makes summary a behavior instance
			newSummary.buildDescription();
			userBehaviors.add(newSummary.buildBehaviorInstance());
		}
		
		return userBehaviors;
	}
	
	
	private List <BehaviorInstance> buildPerUserAllObjectsSummaries(List<PerUserPerProblemSummary> perUserPerProblemSummaries){
		List<BehaviorInstance> userBehaviors = new Vector<BehaviorInstance>();
		List<PerUserAllProblemsSummary> perUserAllProblemsSummaries = new Vector<PerUserAllProblemsSummary>();
		
		PerUserAllProblemsSummary firstSummary = new PerUserAllProblemsSummary(perUserPerProblemSummaries.get(0), perUserPerProblemSummaries.get(0).getUser());
		
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
					perUserAllProblemsSummaries.get(j).addInfo(summary);					
					addedSummary = true;
				}
			
				j++;
			}
			
			if (addedSummary == false){	
				//add a newUser to the perUserAllProblemsSummaries	
				PerUserAllProblemsSummary newSummary = new PerUserAllProblemsSummary(summary, oldUser);	
				//adds summary to list
				perUserAllProblemsSummaries.add(newSummary);
						
			}
			
		}
		
		log.debug(perUserAllProblemsSummaries);
		for(PerUserAllProblemsSummary newSummary: perUserAllProblemsSummaries){
			//makes summary a behavior instance
			newSummary.buildDescription();
			userBehaviors.add(newSummary.buildBehaviorInstance());
		}
		
		return userBehaviors;
	}
}

	




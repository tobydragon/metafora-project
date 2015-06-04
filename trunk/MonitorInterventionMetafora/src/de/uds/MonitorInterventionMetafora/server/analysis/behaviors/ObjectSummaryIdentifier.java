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

		AllUsersPerProblemSummary firstSummary = createNewObjectAllUsersSummary(perUserPerProblemSummaries.get(0), perUserPerProblemSummaries.get(0).getObjectId());
		
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
						
				AllUsersPerProblemSummary newSummary = createNewObjectAllUsersSummary(summary, oldID);
						
				//adds summary to list
				allUsersPerProblemSummaries.add(newSummary);
			}
			
		}
		
		log.debug(allUsersPerProblemSummaries);
		for(AllUsersPerProblemSummary newSummary: allUsersPerProblemSummaries){
			//makes summary a behavior instance
			userBehaviors.add(newSummary.buildBehaviorInstance());
		}
		
		return userBehaviors;
	}
	
	private AllUsersPerProblemSummary createNewObjectAllUsersSummary(PerUserPerProblemSummary summary, String objectID){
		//add a new question to the allUsersPerProblemSummaries
		
		List <String> userList = new Vector<String>();
		String user = summary.getUser();
		userList.add(user);
		
		int numCorrect = 0;
		String correctUsers = "";
		int numBoth = 0;
		String bothUsers = "";
		int numIncorrect = 0;
		String incorrectUsers = "";
		
		if (summary.isCorrect() == false){
			numIncorrect = 1;
			incorrectUsers = user + "/";
		}else{
			if (summary.getNumberTimesFalse() == 0){
				numCorrect = 1;
				correctUsers = user + "/";
			}else{
				numBoth = 1;
				bothUsers = user + "/";
			}
		}
		
		//makes a new summary
		AllUsersPerProblemSummary newSummary = new AllUsersPerProblemSummary(objectID, userList, 1, summary.getAssessable() ,numCorrect, correctUsers, numBoth, bothUsers, numIncorrect, incorrectUsers);
		
		return newSummary;

	}
	
	private List <BehaviorInstance> buildPerUserAllObjectsSummaries(List<PerUserPerProblemSummary> perUserPerProblemSummaries){
		List<BehaviorInstance> userBehaviors = new Vector<BehaviorInstance>();
		List<PerUserAllProblemsSummary> perUserAllProblemsSummaries = new Vector<PerUserAllProblemsSummary>();
		
		PerUserAllProblemsSummary firstSummary = createNewUserAllProblemsSummary(perUserPerProblemSummaries.get(0), perUserPerProblemSummaries.get(0).getUser());
		
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
					
					//update total attempted
					perUserAllProblemsSummaries.get(j).addTotalAttempted();
					
					//update total correct, correct string, total incorrect, and incorrect string
					perUserAllProblemsSummaries.get(j).addQuestion(summary.isCorrect(), summary.getAssessable(), summary.getObjectId());
					
					//update total time
					perUserAllProblemsSummaries.get(j).addTotalTime(summary.getTime());
					
					addedSummary = true;
				}
			
				j++;
			}
			if (addedSummary == false){
					
				//add a newUser to the perUserAllProblemsSummaries
						
				PerUserAllProblemsSummary newSummary = createNewUserAllProblemsSummary(summary, oldUser);
						
				//adds summary to list
				perUserAllProblemsSummaries.add(newSummary);
						
			}
			
		}
		
		log.debug(perUserAllProblemsSummaries);
		for(PerUserAllProblemsSummary newSummary: perUserAllProblemsSummaries){
			//makes summary a behavior instance
			userBehaviors.add(newSummary.buildBehaviorInstance());
		}
		
		return userBehaviors;
	}

	private PerUserAllProblemsSummary createNewUserAllProblemsSummary(PerUserPerProblemSummary summary, String oldUser){
		//add a newUser to the perUserAllProblemsSummaries
		
		//if the first one is correct or not
		int numNotAssessable = 0;
		int numCorrect = 0;
		int numIncorrect = 0;
		String notAssessableID = "";
		String correctID = "";
		String incorrectID = "";
		
		if(summary.getAssessable() == false){
			numNotAssessable = 1;
			notAssessableID = summary.getObjectId() + "/";
		}else if(summary.isCorrect() == true){
			numCorrect = 1;
			correctID = summary.getObjectId() + "/";
		}else{
			numIncorrect = 1;
			incorrectID = summary.getObjectId() + "/";
		}
		
		//makes a new summary
		PerUserAllProblemsSummary newSummary = new PerUserAllProblemsSummary(oldUser, 1, numNotAssessable, notAssessableID, numCorrect, correctID, numIncorrect, 
				incorrectID, 0, summary.getTime());
		
		return newSummary;

	}
}

	




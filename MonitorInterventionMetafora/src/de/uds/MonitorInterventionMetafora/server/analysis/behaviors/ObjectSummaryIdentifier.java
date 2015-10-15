package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.ConceptGraph;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.ConceptNode;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.ConceptLink;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.NodeAndLinkLists;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Book;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;

public class ObjectSummaryIdentifier implements BehaviorIdentifier{
	Logger log = Logger.getLogger(this.getClass());
	
	ActionFilter userFilter;
	ActionFilter objectIdFilter;

	@Override
	public List<BehaviorInstance> identifyBehaviors(List<CfAction> actionsToConsider, List<String> involvedUsers,List<CfProperty> groupProperties){
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
						identifiedBehaviors.add(summary.buildBehaviorInstance());

					}
				}
		}	
		

		//get the path of the book and then create a Book object
		String bookPath = GeneralUtil.getRealPath("conffiles/domainfiles/thinkcspy/");
		Book b = new Book("Interacitve Python", bookPath);
		//create a ConceptGraph of the book and then call createConceptGraph in order to add the summaries to the graph
		ConceptGraph graph = new ConceptGraph(b);
		createConceptGraph(graph.getRoot(), perUserPerProblemSummaries);
		System.out.println(graph);

		NodeAndLinkLists lists =  graph.buildNodeAndLinkLists(graph.getRoot());
		
		ObjectMapper mapper = new ObjectMapper();
	
		// example of changing code that doesn't matter
		// to write to JSON
//        try {
//
//            // convert user object to json string, and save to a file
//            mapper.writeValue(new File("nodesAndEdges.json"), lists);
//
//            // display to console
//            System.out.println(mapper.writeValueAsString(lists));
//
//        } catch (JsonGenerationException e) {
//
//            e.printStackTrace();
//
//        }  catch (IOException e) {
//
//            e.printStackTrace();
//
//        }
		
// Read in JSON and build nodes and edges lists (class)
        try {
        	NodeAndLinkLists lists2 = mapper.readValue(new File("/Users/David/Desktop/nodesAndEdgesBasic.json"), NodeAndLinkLists.class);
        	ConceptGraph graph2 = new ConceptGraph(lists2);
        	System.out.println(graph2);
        	
        } catch (JsonGenerationException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
		
		
		
		
		//currently this sends in the list of all the objectIds for which there exists a summary for - so any objectId that
		//at least one student has submitted an action for
		//this calls the searchGraph function for each id and if found it adds it to the found list and if not, then its added to the not found list
		List<String> notFoundObjectIds = new Vector<String>();
		List<String> foundObjectIds = new Vector<String>();
		for(String currObjectId : objectIds){
			boolean isFound = graph.getRoot().searchGraph(currObjectId);
			if(isFound == false){
				notFoundObjectIds.add(currObjectId);
			}
			else{
				foundObjectIds.add(currObjectId);
			}
		}
		
		System.out.println("All object ids: " + objectIds.toString());
		System.out.println();
		System.out.println("Not found object Ids: " + notFoundObjectIds.toString());
		System.out.println();
		System.out.println("Found object ids: " + foundObjectIds.toString());
		System.out.println();
		
		
		
		List <BehaviorInstance> perUserSummary = buildPerUserAllObjectsSummaries(perUserPerProblemSummaries);
		identifiedBehaviors.addAll(perUserSummary);
		
		List <BehaviorInstance> perObjectSummary = buildAllUsersPerObjectSummaries(perUserPerProblemSummaries);
		identifiedBehaviors.addAll(perObjectSummary);
		
		return identifiedBehaviors;
	}
	
	

	private List <BehaviorInstance> buildAllUsersPerObjectSummaries(List<PerUserPerProblemSummary> perUserPerProblemSummaries){
		
		List<BehaviorInstance> userBehaviors = new Vector<BehaviorInstance>();
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
		
		log.debug(allUsersPerProblemSummaries);
		for(AllUsersPerProblemSummary newSummary: allUsersPerProblemSummaries){
			//makes summary a behavior instance
			userBehaviors.add(newSummary.buildBehaviorInstance());
		}
		
		return userBehaviors;
	}	
	
	private List <BehaviorInstance> buildPerUserAllObjectsSummaries(List<PerUserPerProblemSummary> perUserPerProblemSummaries){
		
		List<BehaviorInstance> userBehaviors = new Vector<BehaviorInstance>();
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
		
		log.debug(perUserAllProblemsSummaries);
		for(PerUserAllProblemsSummary newSummary: perUserAllProblemsSummaries){
			//makes summary a behavior instance
			userBehaviors.add(newSummary.buildBehaviorInstance());
		}
		
		return userBehaviors;
	}
	
	
	
	
	//rename to reflect purpose
	private static void createConceptGraph(ConceptNode node, List<PerUserPerProblemSummary> summaries){

		//go through each child of the node
		for(ConceptNode child : node.getChildren()){
			//go through each summary in the list passed in as a parameter
			for(PerUserPerProblemSummary summary : summaries){	
				//if the object Id for the concept and for the summary match then create node from the summary and add as a child
				if(child.getConcept().getConceptTitle().equalsIgnoreCase(summary.getObjectId())){
					ConceptNode summaryNode = new ConceptNode(summary);
					child.addChild(summaryNode);
				}
			}
			//recursively call the function with a child as the root
			createConceptGraph(child, summaries);
		}
	}
}

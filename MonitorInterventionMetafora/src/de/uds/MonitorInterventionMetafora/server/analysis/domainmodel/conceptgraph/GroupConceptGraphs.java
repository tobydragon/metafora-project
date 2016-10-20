package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.PerUserPerProblemSummary;

public class GroupConceptGraphs {
	ConceptGraph averageGraph;
	Map<String, ConceptGraph> userToGraph;
	
	
	public GroupConceptGraphs(ConceptGraph structureGraph,List<PerUserPerProblemSummary> summaries){
		
		
		averageGraph = new ConceptGraph(structureGraph);
		averageGraph.addSummariesToGraph(summaries);
		
		SortedSet<String> users = getUsers(summaries);

		userToGraph = new HashMap<String, ConceptGraph>();
		
		for(String user: users){
			ConceptGraph structureCopy = new ConceptGraph(structureGraph);
			userToGraph.put(user, structureCopy);
			List<PerUserPerProblemSummary> userSummaries = getUserSummaries(summaries, user);			
			userToGraph.get(user).addSummariesToGraph(userSummaries);
		}
		
		//Build a new concept graph that is the average graph
		//Take the structure graph and make a copy with each student's data
		//loop to get each student's data
		//C
		//make new XML file of summaries to actually connect summaries to structureGraph (never change structureGraph)
	}
	
	//Move to PUPPS File and make Public Static, create tests in PUPPS Test file
	private SortedSet<String> getUsers(List<PerUserPerProblemSummary> puppsList){
		SortedSet<String> users = new TreeSet<>();
		for(PerUserPerProblemSummary summary: puppsList){
			users.add(summary.user);
		}
		return users;
	}
	
	private List<PerUserPerProblemSummary> getUserSummaries(List<PerUserPerProblemSummary> summaries, String user){
		List<PerUserPerProblemSummary> userSummaries = new ArrayList<PerUserPerProblemSummary>();
		for(PerUserPerProblemSummary summary: summaries){
			if(summary.user == user){
				userSummaries.add(summary);
			}
		}
		
		return userSummaries;
	}
	
	//getters for user count, get user map, get all graphs, 

}

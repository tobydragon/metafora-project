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
	ConceptGraph structureGraph;
	Map<String, ConceptGraph> userToGraph;
	
	
	public GroupConceptGraphs(ConceptGraph structureGraph,List<PerUserPerProblemSummary> summaries){
		
		this.structureGraph = structureGraph;
		averageGraph = new ConceptGraph(structureGraph);
		averageGraph.addSummariesToGraph(summaries);
		
		SortedSet<String> users = PerUserPerProblemSummary.getUsers(summaries);

		userToGraph = new HashMap<String, ConceptGraph>();
		
		for(String user: users){
			ConceptGraph structureCopy = new ConceptGraph(structureGraph);
			//Move this to the end 
			userToGraph.put(user, structureCopy);
			List<PerUserPerProblemSummary> userSummaries = PerUserPerProblemSummary.getUserSummaries(summaries, user);			
			userToGraph.get(user).addSummariesToGraph(userSummaries);
		}
		
		//Build a new concept graph that is the average graph
		//Take the structure graph and make a copy with each student's data
		//loop to get each student's data
		//C
		//make new XML file of summaries to actually connect summaries to structureGraph (never change structureGraph)
	}
	
	public int userCount(){
		int count = 0;
		for(String user: userToGraph.keySet()){
			count++;
		}
		return count;
	}
	
	public ConceptGraph getUserGraph(String user){
		return userToGraph.get(user);
	}
	
	public List<ConceptGraph> getAllGraphs(){
		List<ConceptGraph> allGraphs = new ArrayList<ConceptGraph>();
		for(String user: userToGraph.keySet()){
			allGraphs.add(userToGraph.get(user));
		}
		
		return allGraphs;
	}
	
	public Map<String, ConceptGraph> getUserToGraphMap(){
		return userToGraph;
	}
	
	//getters for user count, get user map, get all graphs, 

}

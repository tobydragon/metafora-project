package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.PerUserPerProblemSummary;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.output.NamedGraph;

public class GroupConceptGraphs {
	ConceptGraph averageGraph;
	Map<String, ConceptGraph> userToGraph;
	List<NamedGraph> allNamedGraphs;
	
	
	public GroupConceptGraphs(ConceptGraph structureGraph,List<PerUserPerProblemSummary> summaries){
		
		averageGraph = new ConceptGraph(structureGraph);
		averageGraph.addSummariesToGraph(summaries);
		averageGraph.calcActualComp();
		averageGraph.calcPredictedScores();
		
		SortedSet<String> users = new TreeSet<String>();
		for(String user : PerUserPerProblemSummary.getUsers(summaries)){
			users.add(user);
		}
		System.out.println(users);
		userToGraph = new HashMap<String, ConceptGraph>();
		
		for(String user: users){
			ConceptGraph structureCopy = new ConceptGraph(structureGraph);
			List<PerUserPerProblemSummary> userSummaries = PerUserPerProblemSummary.getUserSummaries(summaries, user);			
			
			structureCopy.addSummariesToGraph(userSummaries);
			//System.out.println(structureCopy);
			structureCopy.calcActualComp();
			structureCopy.calcPredictedScores();
			userToGraph.put(user, structureCopy);
		}
		calcDistanceFromAvg();
		buildNamedGraph();
		
		//Build a new concept graph that is the average graph
		//Take the structure graph and make a copy with each student's data
		//loop to get each student's data
		//C
		//make new XML file of summaries to actually connect summaries to structureGraph (never change structureGraph)
	}
	
	public GroupConceptGraphs(String filename,ConceptGraph structureGraph,List<PerUserPerProblemSummary> summaries){
		this(structureGraph,summaries);
		namedGraphToJSON(filename);
	}
	
	public void calcDistanceFromAvg(){
		NodesAndIDLinks avgLinks = averageGraph.buildNodesAndLinks();
		for(String user: userToGraph.keySet()){
			NodesAndIDLinks tempLinks = userToGraph.get(user).buildNodesAndLinks();
			
			for(ConceptNode tempNode: tempLinks.getNodes()){
				for(ConceptNode avgNode: avgLinks.getNodes()){
					if(tempNode.getConcept().getConceptTitle().equals(avgNode.getConcept().getConceptTitle())){
						double avgCalc = avgNode.getActualComp();
						tempNode.calcDistanceFromAvg(avgCalc);
					}
				}
			}
		}
	}
	
	public List<NamedGraph> getAllNamedGraphs(){
		return this.allNamedGraphs;
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
	
	@JsonIgnore
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
	
	public void buildNamedGraph(){
		allNamedGraphs = new ArrayList<NamedGraph>();
		NamedGraph avgGraph = new NamedGraph("Average Graph", averageGraph);
		allNamedGraphs.add(avgGraph);
		for(String user: userToGraph.keySet()){
			
			NamedGraph temp = new NamedGraph(user, getUserGraph(user));
			allNamedGraphs.add(temp);
		}
	}
	
	public void namedGraphToJSON(String filename){
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			//writes JSON to file
			mapper.writeValue(new File(filename+".json"), allNamedGraphs);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

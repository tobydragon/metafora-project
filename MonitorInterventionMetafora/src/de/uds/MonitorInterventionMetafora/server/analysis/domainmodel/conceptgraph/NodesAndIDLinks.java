package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NodesAndIDLinks {
	
	private List<ConceptNode> nodes;
	private List<IDLink> links;
	
	public static NodesAndIDLinks buildfromJson(String filename) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		NodesAndIDLinks lists = mapper.readValue(new File(filename), NodesAndIDLinks.class);
		return lists;
	}
	
	public void writeToJson(String filename) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(new File(filename), this);
		
	}
	
	public NodesAndIDLinks(){}
	
	public NodesAndIDLinks(List<ConceptNode> nodesIn, List<IDLink> linksIn){
		this.nodes = nodesIn;
		this.links = linksIn;
		
	}
	
	public List<ConceptNode> getNodes(){
		return nodes;
	}
	
	public List<IDLink> getLinks(){
		return links;
	}
	
	public void setNodes(List<ConceptNode> nodesIn){
		this.nodes = nodesIn;
	}
	
	public void setLinks(List<IDLink> linksIn){
		this.links = linksIn;
	}
	
	public String toString(){
		
		String linkString = "";
		String nodeString = "";
		String combinedString = "";
		
		for(IDLink link : links){
			linkString += "Parent: " + link.getParent() + "\n" + 
					"Child: " + link.getChild() + "\n\n";
		}
		
		for(ConceptNode node : nodes){
			nodeString += node.getConcept().getConceptTitle() + "\n";
		}
		
		combinedString = linkString + "\n\n\n" + nodeString;

		return combinedString;
	}
}

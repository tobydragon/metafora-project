package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class NodesAndIDLinks {
	
	private List<ConceptNode> nodes;
	private List<IDLink> links;
	
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

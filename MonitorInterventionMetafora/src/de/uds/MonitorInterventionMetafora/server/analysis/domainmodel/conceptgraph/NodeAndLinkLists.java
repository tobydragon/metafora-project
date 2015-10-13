package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.List;

public class NodeAndLinkLists {
	
	public List<ConceptNode> nodes;
	public List<ConceptLink> links;
	
	public NodeAndLinkLists(List<ConceptNode> nodesIn, List<ConceptLink> linksIn){
		this.nodes = nodesIn;
		this.links = linksIn;
		
	}
	
	public List<ConceptNode> getNodes(){
		return nodes;
	}
	
	public List<ConceptLink> getLinks(){
		return links;
	}
	
	public String toString(){
		
		String linkString = "";
		String nodeString = "";
		String combinedString = "";
		
		for(ConceptLink link : links){
			linkString += "Parent: " + link.getParent().getConcept().getConceptTitle() + "\n" + 
					"Child: " + link.getChild().getConcept().getConceptTitle() + "\n\n";
		}
		
		for(ConceptNode node : nodes){
			nodeString += node.getConcept().getConceptTitle() + "\n";
		}
		
		combinedString = linkString + "\n\n\n" + nodeString;

		return combinedString;
	}
}

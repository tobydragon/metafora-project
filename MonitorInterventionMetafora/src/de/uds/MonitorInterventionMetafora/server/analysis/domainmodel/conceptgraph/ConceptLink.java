package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;


public class ConceptLink {
	
	public ConceptNode parent;
	public ConceptNode child;
	
	public ConceptLink(ConceptNode parentIn, ConceptNode childIn){
		this.parent = parentIn;
		this.child = childIn;
		
	}
	
	public ConceptNode getParentNode(){
		return parent;
	}
	
	public ConceptNode getChildNode(){
		return child;
	}
}

package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;


public class ConceptLink {
	
	public ConceptNode parent;
	public ConceptNode child;
	
	public ConceptLink() {
		
	}
	
	public ConceptLink(ConceptNode parentIn, ConceptNode childIn){
		this.parent = parentIn;
		this.child = childIn;
		
	}
	
	public ConceptNode getParent(){
		return parent;
	}
	
	public ConceptNode getChild(){
		return child;
	}
	
	public String toString(){
		return"[Parent: " + this.parent.getID() + " Child: " + this.child.getID() + "]\n";
	}
}

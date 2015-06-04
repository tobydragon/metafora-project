package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.List;

public class ConceptNode {
	
	Concept concept;
	List<ConceptNode> children;
	
	public ConceptNode(Concept concept){
		this.concept = concept;
		children = new ArrayList<ConceptNode>();
	}
	
	
	
	public void addChild(ConceptNode child){
		children.add(child);
	}
	
	public List<ConceptNode> getChildren(){
		return children;
	}

	public Concept getConcept(){
		return concept;
	}
	
	public String toString(){
		return toString("\n");
	}
	
	 public String toString(String indent){
		 
		 String stringToReturn = indent + getConcept().getConceptTitle();
		 for (ConceptNode child :getChildren()){
			 stringToReturn += child.toString(indent + "\t");
		 }
		 return stringToReturn;
		 
	 }
}

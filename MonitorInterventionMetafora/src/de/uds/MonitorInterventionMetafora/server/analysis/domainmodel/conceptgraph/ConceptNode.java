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
	
	
	//Currently printing with the time spent on all children gotten from calcSummaryInfo
	public String toString(String indent){
		 
		String stringToReturn = indent + getConcept().getConceptTitle() + " " + calcSummaryInfo(); 
		for (ConceptNode child :getChildren()){
			stringToReturn += child.toString(indent + "\t");
		}
		return stringToReturn;
		 
	}
	
	public long calcSummaryInfo(){
		
		long summaryInfo = getConcept().getSummaryInfo();
		for (ConceptNode child :getChildren()){
			summaryInfo += child.calcSummaryInfo();
		}
		return summaryInfo;
	}
}

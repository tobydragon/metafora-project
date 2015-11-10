
package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ConceptNode {
	
	int color;
	Concept concept;
	List<ConceptNode> children;
	
	public ConceptNode() {
		children = new ArrayList<ConceptNode>();
	}
	
	public ConceptNode(Concept concept){
		this.concept = concept;
		children = new ArrayList<ConceptNode>();
	}
	
	
	
	public void addChild(ConceptNode child){
		children.add(child);
	}
	
	@JsonIgnore
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
		 
		String stringToReturn = indent + getConcept().getConceptTitle() +  "\t" + calcSummaryInfo(); 
		for (ConceptNode child :getChildren()){
			stringToReturn += child.toString(indent + "\t");
		}
		return stringToReturn;
		 
	}
	
	
	//getting SummaryInfo objects from the Nodes and then combining into one SummaryInfo object
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public SummaryInfo getSummaryInfo() {
		return calcSummaryInfo();
	}

	public SummaryInfo calcSummaryInfo(){
		
		//need to use the return object from the recursive call
		SummaryInfo summaryInfo = getConcept().getSummaryInfo();
		
		for (ConceptNode child : getChildren()){
			SummaryInfo childSumInfo = child.calcSummaryInfo();
			//include one update function in summaryinfo
			summaryInfo.update(child, childSumInfo);			
		}
		return summaryInfo;
	}
	
	
	//searches for a certain objectId in the graph
	//takes in the objectId to search for as a parameter
	//returns true if found, and false otherwise
	public boolean searchGraph(String objectIdToSearchFor){
	
		if(getConcept().getConceptTitle().contains(objectIdToSearchFor)){
			return true;
		 }
		   
		for(ConceptNode child : getChildren()){
			boolean isTrue = child.searchGraph(objectIdToSearchFor);
			if(isTrue == true){
				return true;
			}
		}
		
		return false;
	}
	
}

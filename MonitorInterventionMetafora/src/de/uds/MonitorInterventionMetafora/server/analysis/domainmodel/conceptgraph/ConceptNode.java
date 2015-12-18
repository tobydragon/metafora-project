
package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ConceptNode {
	
	int level;
	Concept concept;
	List<ConceptNode> children;
	
	// used for AI scores
	private double actualComp;
	private double predictedComp;
	private int numParents;
	
	public ConceptNode() {
		children = new ArrayList<ConceptNode>();
		numParents = 0;
		predictedComp = 0;
		actualComp = 0;
	}
	
	public ConceptNode(Concept concept){
		this.concept = concept;
		children = new ArrayList<ConceptNode>();
		numParents = 0;
		actualComp = 0;
		predictedComp = 0;
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
		 
		
		
		String stringToReturn = indent + getConcept().getConceptTitle() +  "\t actual: " + getActualComp() + " pred: " +getPredictedComp(); 
		for (ConceptNode child :getChildren()){
			stringToReturn += child.toString(indent + "\t");
		}
		return stringToReturn;
		 
	}
	
	
	//getting SummaryInfo objects from the Nodes and then combining into one SummaryInfo object
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public SummaryInfo getSummaryInfo() {
		return calcSummaryInfo();
	}

	public SummaryInfo calcSummaryInfo(){
		
		//need to use the return object from the recursive call
		SummaryInfo summaryInfo = getConcept().getSummaryInfo();
		
		for (ConceptNode child : getChildren()){
			SummaryInfo childSumInfo = child.calcSummaryInfo();
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

	public double getActualComp() {
		return Math.round(actualComp*100.0)/100.0;
	}

	public void setActualComp(double actualComp) {
		this.actualComp = actualComp;
	}

	public double getPredictedComp() {
		return Math.round(predictedComp*100.0)/100.0;
	}

	public void setPredictedComp(double predictedComp) {
		this.predictedComp = predictedComp;
	}
	
	public int getNumParents() {
		return numParents;
	}

	public void setNumParents(int numParents) {
		this.numParents = numParents;
	}

	public double calcActualComp() {
	
		if(getChildren().size() == 0){
			//then take in the summaryInfo information and calculate the actualComp
			SummaryInfo sumInfo = getConcept().getSummaryInfo();
			if((sumInfo.getNumCorrect() == 0) && (sumInfo.getTotalFalseEntries()==0)){
				actualComp = 0;
			}
			else{
				actualComp = sumInfo.getNumCorrect() * .5 + (.5 - .1* sumInfo.getTotalFalseEntries());	
			}
			return actualComp;
		}

			//recursively call this on each child of the node
			double tempComp;
			tempComp = 0;
			for(ConceptNode child : getChildren()){
				double childComp = child.calcActualComp();
				child.setNumParents(child.getNumParents() + 1);
				tempComp = tempComp + (childComp / getChildren().size());
			}
			actualComp = tempComp;
			return actualComp;
		}
	}
	
	


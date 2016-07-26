
package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ConceptNode {
	
	String id;
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
		this (concept, concept.getConceptTitle());
	}
	
	public ConceptNode(Concept concept, String newID){
		children = new ArrayList<ConceptNode>();
		numParents = 0;
		predictedComp = 0;
		actualComp = 0;
		this.concept = concept;
		this.id = newID;	
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
	
	public String getID(){
		return id;
	}
	
	public String toString(){
		return toString("\n");
	}
	
	public void addToNodesAndLinksLists(List<ConceptNode> nodes, List<IDLink> links){
		//if I am not in the list
		if(!nodes.contains(this)){
			//add me to the nodes list
			nodes.add(this);
			for(ConceptNode child : this.children){
				//recurse call on children
				child.addToNodesAndLinksLists(nodes,links);
				//add the links between me and my children to link list
				links.add(new IDLink(this.getID(),child.getID()));
			}
			
		}
	
	}
	
	public ConceptNode makeTree(HashMap<String, List<String>> multCopies){
		ConceptNode nodeCopy;
		List<String> nodeCopies = multCopies.get(this.getConcept().getConceptTitle());
		if(nodeCopies == null){
			nodeCopy = new ConceptNode(this.getConcept(),makeName(this.getConcept().getConceptTitle()));
			nodeCopies = new ArrayList<String>();
			nodeCopies.add(nodeCopy.getID());
			multCopies.put(nodeCopy.getConcept().getConceptTitle(), nodeCopies);
		}else{
			String prevName = nodeCopies.get(nodeCopies.size()-1);
			nodeCopy = new ConceptNode(this.getConcept(), makeName(prevName));
			nodeCopies.add(nodeCopy.getID());
			multCopies.put(nodeCopy.getConcept().getConceptTitle(), nodeCopies);
		}
		
		try{
		for(ConceptNode origChild : this.getChildren()){
			ConceptNode childCopy = origChild.makeTree(multCopies);
			nodeCopy.addChild(childCopy);
		}
		}catch(NullPointerException e){
			System.out.println("Broke on this node: "+this.getID());
		}
		
		return nodeCopy;
	}

	public static String makeName(String prevName) {
		if(prevName != "" && prevName != null){
			String[] nameList = prevName.split("-");
			String name = nameList[0];
			int num = 0;
			try{
			String numString = nameList[1];
			num = Integer.parseInt(numString);
			num += 1;
			}catch(ArrayIndexOutOfBoundsException e){
				num = 1;
			}
			
			String fullName = name+"-"+num;
			return fullName;
		}else{
			return "";
		}
    }
	
	//recursive function that adds a single summary as a child of the node with the matching name
	public boolean addSummaryNode(ConceptNode summaryNode){
	
		//if there is a title
		if(this.getConcept().getConceptTitle().isEmpty()==false){

			//if titles match
			if(summaryNode.getConcept().getConceptTitle().startsWith(this.getConcept().getConceptTitle())){	
				//if this summary node is not already in children list, add it
				if(!(this.getChildren().contains(summaryNode))){
					this.addChild(summaryNode);
					return true;
				}						
			}
			//else titles don't match, check all your children for a match
			else{
				for(ConceptNode child : this.getChildren()){
					boolean addedToChild = child.addSummaryNode(summaryNode);
					if(addedToChild){
						return true; 
					}
				}
				return false;
			
			}
		}
		
		return false;
	}
	
	//Currently printing with the time spent on all children gotten from calcSummaryInfo
	public String toString(String indent){
		
//		String stringToReturn = indent + getConcept().getConceptTitle() +  "\t actual: " + getActualComp() + " pred: " +getPredictedComp(); 
//		for (ConceptNode child :getChildren()){
//			stringToReturn += child.toString(indent + "\t");
//		}
		
		String stringToReturn = "Concept: " + this.getConcept().getConceptTitle() + " ID: " + this.getID()+"\n";
		return stringToReturn;
		 
	}
	
	
	//getting SummaryInfo objects from the Nodes and then combining into one SummaryInfo object
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
	
	



package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ConceptNode {
	
	private static final String symbol = "-";
	String id;
	Concept concept;
	List<ConceptNode> children;
	
	// used for AI scores
	private double actualComp;
	private double predictedComp;
	private int numParents;
	private double distanceFromAvg;
	private double dataImportance;
	
	public ConceptNode() {
		children = new ArrayList<ConceptNode>();
		numParents = 0;
		predictedComp = 0;
		actualComp = 0;
		distanceFromAvg = 0;
		
	}
	
	
	public ConceptNode(Concept concept, String newID){
		this();
		this.concept = concept;
		this.id = newID;
		dataImportance = concept.getDataImportance();
	}
	
	public ConceptNode(Concept concept){
		this (concept, concept.getConceptTitle());
	}
	
	public ConceptNode(ConceptNode copyNode){
		this.children = new ArrayList<ConceptNode>();
		this.numParents = copyNode.getNumParents();
		this.actualComp = copyNode.getActualComp();
		this.predictedComp = copyNode.getPredictedComp();
		this.distanceFromAvg = copyNode.getDistanceFromAvg();
		this.concept = copyNode.getConcept();
		this.id = copyNode.getID();
	}
	
	public ConceptNode(ConceptNode cn, String newID){
		this(cn);
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
	
	public double getDataImportance(){
		return dataImportance;
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
			nodeCopy = new ConceptNode(this,makeName(this.getConcept().getConceptTitle()));
			/*
			nodeCopy.setActualComp(this.actualComp);
			nodeCopy.setPredictedComp(this.predictedComp);
			nodeCopy.setDistanceFromAvg(this.distanceFromAvg);
			*/
			nodeCopies = new ArrayList<String>();
			nodeCopies.add(nodeCopy.getID());
			multCopies.put(nodeCopy.getConcept().getConceptTitle(), nodeCopies);
			
		}else{
			String prevName = nodeCopies.get(nodeCopies.size()-1);
			nodeCopy = new ConceptNode(this, makeName(prevName));
			/*
			nodeCopy.setActualComp(this.actualComp);
			nodeCopy.setPredictedComp(this.predictedComp);
			nodeCopy.setDistanceFromAvg(this.distanceFromAvg);
			*/
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

	/***
	 * makes next name based on prevName, everything before "-" symbol is the name and the number after it will iterate
	 * @param prevName
	 * @return String next name
	 */
	public static String makeName(String prevName) {
		if(prevName != "" && prevName != null){
			String[] nameList = prevName.split(symbol);
			String name = "";
			for(int i = 0; i < nameList.length-1; i++){
				name += nameList[i];
			}
			int num = 0;
			
			if(nameList.length <= 1){
				String fullName = nameList[0] + "-1";
				return fullName;
			}else{
				try{
					String numString = nameList[nameList.length-1];
					num = Integer.parseInt(numString);
					num += 1;
				}catch(NumberFormatException e){
					name += nameList[nameList.length-1];
					num = 1;
				}
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
	
	/*public SummaryInfo getSummaryInfo() {
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
	}*/
	
	
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
	
	public void calcDistanceFromAvg(double avgCalc){
	
		this.distanceFromAvg = this.actualComp - avgCalc;
	}
	
	public void setDistanceFromAvg(double setTo){
		this.distanceFromAvg = setTo;
	}
	
	public double getDistanceFromAvg(){
		return distanceFromAvg;
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
	
	/* a + (x - A) (b - a) / (B - A)
	 * B is original data set max, A is original data set min
	 * b is normalize range max, a is normalized range min
	 * x is the number being normalized
	 * 
	 */
	public double normalizeDataImportance(){
		double myDI = 0;
		double totalDI = 0;
		for(ConceptNode child : getChildren()){
			double childDI = child.concept.getDataImportance();
		
			totalDI = totalDI + (childDI);
		}
		myDI = totalDI / getChildren().size();
		return myDI;
	}

	public double calcActualComp() {
		if(getChildren().size() == 0){
			actualComp = getConcept().getScore();
			//System.out.println("Actual Comp = "+actualComp);
			return actualComp;
		} else {

			//recursively call this on each child of the node
			double tempComp;
			
			
			tempComp = 0;
			int idCounter = 0;
			double childDI = 0;
			for(ConceptNode child : getChildren()){
				String id = Integer.toString(idCounter);
				double childComp = child.calcActualComp();
				
				dataImportance+=child.getDataImportance();
				
				child.setNumParents(child.getNumParents() + 1);
				tempComp = tempComp + (childComp / getChildren().size());// * normalizedDI;
				//numChildren++;
			}
			//double dataImportance = normalizeDataImportance();
			actualComp = tempComp; //* dataImportance;
			//this is so we can't end up with a score being 0 unless all it's children are 0
			//which is useful because a 0 displays that they haven't answered something
			//so if the average score of something ends up being 0, it will change to -.1 showing that
			//improvements in this topic are possible and not that it is unanswered.
			
			/*System.out.println("AC: "+actualComp+", Num c: "+numChildren+", Num c z: "+numChildrenZero);
			if(actualComp == 0 && numChildren != numChildrenZero){
				actualComp = -.1;
				System.out.println("hit");
			}*/
			
			//can't do this without making averages look weird because if you get a 0 average on something it shows up as -1 but it's an average so you don't want to change that
			
			return actualComp;
		}
		
	}
}
	
	


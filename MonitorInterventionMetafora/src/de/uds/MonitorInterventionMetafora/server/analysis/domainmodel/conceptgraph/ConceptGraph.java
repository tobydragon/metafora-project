package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.List;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Book;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Chapter;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Question;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.SubChapter;

public class ConceptGraph {
	
	public static final Integer DIVISION_FACTOR = 2;
	ConceptNode root;
	String stringToReturn = "";
	List<ConceptNode> nodes;
	List<ConceptLink> links;

	/*
	 *Takes in a book, starts at the root, then goes through each level (chapters, sub chapters, questions) and creates
	 *a node for each concept, and adds it as a child.
	 */
	public ConceptGraph(Book b){
		root = new ConceptNode(b);
		nodes = new ArrayList<ConceptNode>();
		links = new ArrayList<ConceptLink>();
		
		
		//get the list of chapters of the book
		//create a new node for each chapter
		List<Chapter> chapters = b.getChapters();
		for (Chapter chap : chapters){
			ConceptNode chapNode = new ConceptNode(chap);
			root.addChild(chapNode);
			
			//get the list of sub chapters for each chapter
			//create a new node for each sub chapter
			List<SubChapter> subChaps = chap.getSubChapters();
			for (SubChapter subChap : subChaps){
				ConceptNode subChapNode = new ConceptNode(subChap);
				chapNode.addChild(subChapNode);
				
				//get the list of questions for each sub chapter
				//create a new node for each question
				List<Question> questions = subChap.getQuestions();
				for(Question ques : questions){
					ConceptNode quesNode = new ConceptNode(ques);
					subChapNode.addChild(quesNode);
				}
			}
		}		
	}
	
	public ConceptGraph(NodeAndLinkLists lists) {
		this.nodes = lists.getNodes();
		this.links = lists.getLinks();
		this.root = findRoot();
		
		addChildren(root);
	}
	
	public void addSummariesToGraph(List<ConceptNode> summaryNodes){
		//call the recursive function addSummaryNode - send in node and a single summary (loop through summaryList to call that function)
		for(ConceptNode summaryNode : summaryNodes){
			this.root.addSummaryNode(summaryNode);
		//TODO: If this ever returns false, return false
		}
	}	

	
	private void addChildren(ConceptNode current) {
		//for every link
		for (ConceptLink link : links) { 
			//if this node is the parent
			if (link.getParent().getConcept().getConceptTitle().equals(current.getConcept().getConceptTitle()) ) {
				//add the child to the current node's list of children
				for (ConceptNode node: nodes) {
					if (node.getConcept().getConceptTitle().equals(link.getChild().getConcept().getConceptTitle())) {
						addChildren(node);
						break;
					}			
				}
				
				for (ConceptNode node: nodes) {
					if (node.getConcept().getConceptTitle().equals(link.getChild().getConcept().getConceptTitle())) {
						current.addChild(node);
						break;
					}			
				}
			}
		}
	}
	
//	
//	//side effect, current will be complete after the call
//	private void makeGraph(ConceptNode current, List<ConceptLink> inputLinks){
//		for (ConceptLink link : links) { 
//			//if this node is the parent
//			if (link.getParent().getConcept().getConceptTitle().equals(current.getConcept().getConceptTitle()) ) {
//		
//				
//			}
//		}
//		
//		
//	}
	
 	private ConceptNode findRoot() {
		List<ConceptNode> runningTotal = new ArrayList<ConceptNode>();
		for (ConceptNode node: nodes) {
			runningTotal.add(node);
		}
		for (ConceptLink link : links) {
			if (runningTotal.contains(link.getChild())) {
				runningTotal.remove(link.getChild());
			}
		}
		//TODO: give warning if runningTotal has more than one entry, meaning more than one root 
		return runningTotal.get(0);
	}

	
	public String toString(){
		return root.toString();
	}
	public ConceptNode getRoot(){
		return root;
	}
	
	//takes in a ConceptNode and creates an object to hold on to two lists - a list of nodes and a list of links
	private NodeAndLinkLists buildNodeAndLinkLists(ConceptNode currNode, int level){
		currNode.setLevel(level);
		
		//checks to see if the current node is already in the list, if not it adds it

		if(nodes.contains(currNode) == false) {
			nodes.add(currNode);
		}
		
		//goes through each child of the current node and checks to see if there is a link already for that parent/child pair
		//if not then it adds it
		for(ConceptNode child : currNode.getChildren()){
			ConceptLink linkToAdd = new ConceptLink (currNode, child);
			if(links.contains(linkToAdd) == false){
				links.add(linkToAdd);
				
				//recursively calls the function again with the child as the current node
				buildNodeAndLinkLists(child, level+1);
			}
			
			
		}
		List<ConceptNode> finalNodes = new ArrayList<ConceptNode>();
		List<ConceptLink> finalLinks = new ArrayList<ConceptLink>();
		
		for	(ConceptNode node : nodes) {
			if (node.getLevel() != 0) {
				finalNodes.add(node);
			}
		}
		for (ConceptLink link : links) {
			if (link.getParent().getLevel() != 0 && link.getChild().getLevel() != 0) {
				finalLinks.add(link);
			}
		}
		
		//creates the LinksAndNodes object to hold on to both lists, then returns that object
		NodeAndLinkLists finalLists = new NodeAndLinkLists(finalNodes, finalLinks);
		
		
		return finalLists;
	}
	
	public NodeAndLinkLists buildNodesAndLinks() {
		return buildNodeAndLinkLists(root, 1);
	}
		

	public void calcActualComp(){
		root.calcActualComp();
	}

	public void calcPredictedScores() {
		calcPredictedScores(root);
	}
	
	private void calcPredictedScores(ConceptNode currentRoot) {
		calcPredictedScores(currentRoot, root.getActualComp(), currentRoot);
	}
	
	// pre order traversal
	private static void calcPredictedScores(ConceptNode current, double passedDown, ConceptNode currentRoot) {
		
		// simple check for if we're dealing with the root, which has its own rule
		if (current == currentRoot) {
			current.setPredictedComp(current.getActualComp());
		} else {
			current.setNumParents(current.getNumParents() + 1);
			
			// Calculating the new predicted, take the the old predicted with the weight it has based off of the number of parents
			// calculate the new pred from the new information passed down and the adding it to old pred
			double oldPred = current.getPredictedComp() * (1.0 - (1.0/current.getNumParents()));
			double newPred = (passedDown * (1.0/current.getNumParents())) + oldPred;
			
			current.setPredictedComp(newPred);
		}
		
		for (ConceptNode child : current.getChildren()) {
			if (current.getActualComp() == 0) {
				
				calcPredictedScores(child, current.getPredictedComp()/ DIVISION_FACTOR, currentRoot);
			} else {
				calcPredictedScores(child, current.getActualComp(), currentRoot);
			}
		}
		
	}


	
}

package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.List;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Book;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Chapter;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Question;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.SubChapter;

public class ConceptGraph {
	
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
	
	private void addChildren(ConceptNode current) {
		for (ConceptLink link : links) {
			if (link.getParent().getConcept().getConceptTitle().equals(current.getConcept().getConceptTitle()) ) {
				addChildren(link.getChild());
				current.addChild(link.getChild());
			}
		}
	}
	
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
		currNode.setComps();
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
	
	public void calcPredictedScores() {
		
		// TODO just to get the "made up" scores in this line will be deleted
		buildNodesAndLinks();
		
		
		calcPredictedScores(root, root.getActualComp());
	}
	
	// pre order traversal
	private void calcPredictedScores(ConceptNode current, double passedDown) {
		
		// TODO this is the like actual person's answers or whatever. IDK what the exact thing to do is so I'm ignoring for now
		if (current.getActualComp() == -1) {
			return;
		}
		
		// TODO Not working exactly correct, one error that is apparent is when it goes to print the same node multiple times
		// there's different answers for predicted comps...
		
		if (current == root) {
			current.setPredictedComp(current.getActualComp());
		} else {
			current.setNumParents(current.getNumParents() + 1);
			current.setPredictedComp((passedDown * (1/current.getNumParents())) + (current.getPredictedComp() * (1-(1/current.getNumParents()))));
		}
		for (ConceptNode child : current.getChildren()) {
			if (current.getActualComp() == 0) {
				calcPredictedScores(child, current.getPredictedComp()/2);
			} else {
				calcPredictedScores(child, current.getActualComp());
			}
		}
		
	}
}

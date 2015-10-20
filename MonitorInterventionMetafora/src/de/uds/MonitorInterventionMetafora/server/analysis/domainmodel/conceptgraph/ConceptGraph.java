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

	
	
	
	public ConceptGraph(String rootTitle){
		
		
		//root = new ConceptNode(concept);
		nodes = new ArrayList<ConceptNode>();
		links = new ArrayList<ConceptLink>();

		buildGraph(rootTitle);
	}
	
	
	
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
		// optimization needed
		for (ConceptLink link : links) {
			if (link.getParent() == current) {
				current.addChild(link.getChild());
				addChildren(link.getChild());
			}
		}
	}
	
	
	private void buildGraph(String rootTitle){
		
		//hard coding questions as the last level of tree
		//had to 
		ConceptImpl impl1 = new ConceptImpl("test_question5_1_1");
		ConceptNode node1 = new ConceptNode(impl1);
		
		ConceptImpl impl2 = new ConceptImpl("test_question5_1_2");
		ConceptNode node2 = new ConceptNode(impl2);
		
		
		List<ConceptImpl> list1 =  new ArrayList<ConceptImpl>();
		list1.add(impl1);
		list1.add(impl2);
		
		ConceptImpl impl3 = new ConceptImpl("test_question5_1_3");
		ConceptNode node3 = new ConceptNode(impl3);
		
		ConceptImpl impl4 = new ConceptImpl("test_question5_1_5");
		ConceptNode node4 = new ConceptNode(impl4);
		
		List<ConceptImpl> list2 =  new ArrayList<ConceptImpl>();
		list1.add(impl3);
		list1.add(impl4);
		
		ConceptImpl impl5 = new ConceptImpl("Function Purpose", list1);
		ConceptNode node5 = new ConceptNode(impl5);
		
		ConceptImpl impl6 = new ConceptImpl("Function Syntax", list2);
		ConceptNode node6 = new ConceptNode(impl6);
		
		
		List<ConceptImpl> list3 =  new ArrayList<ConceptImpl>();
		list3.add(impl5);
		list3.add(impl6);
		ConceptImpl rootImpl = new ConceptImpl(rootTitle, list3);
		this.root = new ConceptNode(rootImpl);
		
		
		node5.addChild(node1);
		node5.addChild(node2);
		
		node6.addChild(node3);
		node6.addChild(node4);
		
		root.addChild(node5);
		root.addChild(node6);
		
	}
	
	
	
 	private ConceptNode findRoot() {
		List<ConceptNode> runningTotal = new ArrayList<ConceptNode>();
		for (ConceptNode node: nodes) {
			runningTotal.add(node);
		}
		for (ConceptLink link : links) {
			runningTotal.remove(link.getChild());
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
	public NodeAndLinkLists buildNodeAndLinkLists(ConceptNode currNode){
		
		//checks to see if the current node is already in the list, if not it adds it
		if(nodes.contains(currNode) == false){
			nodes.add(currNode);
		}
		
		//goes through each child of the current node and checks to see if there is a link already for that parent/child pair
		//if not then it adds it
		for(ConceptNode child : currNode.getChildren()){
			ConceptLink linkToAdd = new ConceptLink (currNode, child);
			if(links.contains(linkToAdd) == false){
				links.add(linkToAdd);
			}
			//recursively calls the function again with the child as the current node
			buildNodeAndLinkLists(child);
		}
		
		//creates the LinksAndNodes object to hold on to both lists, then returns that object
		NodeAndLinkLists finalLists = new NodeAndLinkLists(nodes, links);
		return finalLists;
	}	
}

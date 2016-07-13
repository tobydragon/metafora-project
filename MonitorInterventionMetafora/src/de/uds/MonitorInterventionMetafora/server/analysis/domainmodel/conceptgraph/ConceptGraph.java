package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.PerUserPerProblemSummary;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Book;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Chapter;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Question;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.SubChapter;

public class ConceptGraph {
	
	public static final Integer DIVISION_FACTOR = 2;
	ConceptNode root;
	List<ConceptNode> roots;
	String stringToReturn = "";
	List<ConceptNode> nodes;
	List<ConceptLink> links;
	
	HashMap<String, ConceptNode> nodesMap;
	List<IDLink> idLinks;

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
		this.roots = findRoot();
		
		addChildren();
	}
	
	public ConceptGraph(HashMap<String, ConceptNode> nodesMapIn, List<IDLink> idLinksIn){
		this.nodesMap = nodesMapIn;
		this.idLinks = idLinksIn;
	}
	
	public List<ConceptNode> getNodes(){
		return this.nodes;
	}
	
	public List<ConceptLink> getLinks(){
		return this.links;
	}
	
	public List<IDLink> getIDLinks(){
		return this.idLinks;
	}
	
	public void addSummariesToGraph(List<PerUserPerProblemSummary> summaries){

		List<ConceptNode> graphSummaryNodeList = new ArrayList<ConceptNode>();
		for(PerUserPerProblemSummary summary : summaries){
			//System.out.println(summary.getObjectId());
			ConceptNode sumNode = new ConceptNode(summary);
			graphSummaryNodeList.add(sumNode);
		}
		
		//call the recursive function addSummaryNode - send in node and a single summary (loop through summaryList to call that function)
		for(ConceptNode summaryNode : graphSummaryNodeList){
			this.root.addSummaryNode(summaryNode);
		//TODO: If this ever returns false, return false
		}
	}	

	private void addChildren(){
		//TODO: Make take nodes and links as parameters
		//TODO: change to get ID instead of concept title
		HashMap<String, ConceptNode> fullNodesMap = new HashMap<String, ConceptNode>();
		for( ConceptNode currNode : this.nodes){
			fullNodesMap.put(currNode.getConcept().getConceptTitle(), currNode);
		}
		
		for( ConceptLink currLink : this.links){
			ConceptNode currParent = fullNodesMap.get(currLink.getParent().getConcept().getConceptTitle());
			currParent.addChild(fullNodesMap.get(currLink.getChild().getConcept().getConceptTitle()));
		}
	}

	
 	private List<ConceptNode> findRoot() {
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
		return runningTotal;
	}

	
	public String toString(){
		
		return "Nodes:\n"+this.nodes+"\nLinks:\n"+this.links;
		
		//return root.toString();
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

	public ConceptGraph graphToTree(){
		List<ConceptNode> treeNodesList = new ArrayList<ConceptNode>();
		List<ConceptLink> treeLinksList = new ArrayList<ConceptLink>();
		HashMap<String, List<ConceptNode>> multCopies = new HashMap<String, List<ConceptNode>>();

		
		for(ConceptLink currLink : this.links){
			ConceptNode child = currLink.getChild();
			ConceptNode parent = currLink.getParent();
			ConceptNode replaceChild = null;
			ConceptNode replaceParent = null;
			
			List<ConceptNode> copiesOfChild = multCopies.get(child.getConcept().getConceptTitle());
			
			//If node has never been copied before
			if(copiesOfChild == null){
				replaceChild = new ConceptNode(child.getConcept(), makeName(child.getID()));
				copiesOfChild = new ArrayList<ConceptNode>();
				copiesOfChild.add(replaceChild);
				multCopies.put(child.getConcept().getConceptTitle(), copiesOfChild);
				treeNodesList.add(replaceChild);
			}else{
				replaceChild = new ConceptNode(child.getConcept(), makeName(copiesOfChild.get(copiesOfChild.size() - 1).getID()));
				copiesOfChild.add(replaceChild);
				multCopies.put(child.getConcept().getConceptTitle(), copiesOfChild);
				
				treeNodesList.add(replaceChild);
			}
			
			//If parent is not in treeNodesList
			//Find or create replaceParent
			List<ConceptNode> copiesOfParentsList = multCopies.get(parent.getConcept().getConceptTitle());
			if(copiesOfParentsList == null){
				copiesOfParentsList = new ArrayList<ConceptNode>();
				replaceParent = new ConceptNode(parent.getConcept(), makeName(parent.getID()));
				copiesOfParentsList.add(replaceParent);
				multCopies.put(parent.getConcept().getConceptTitle(), copiesOfParentsList);
				
				treeNodesList.add(replaceParent);
				treeLinksList.add(new ConceptLink(replaceParent, replaceChild));
			}else{
				treeLinksList.add((new ConceptLink(copiesOfParentsList.get(0), replaceChild)));
				for(int i = 1; i < copiesOfParentsList.size(); i++){
					List<ConceptNode> childCopiesList = multCopies.get(child.getConcept().getConceptTitle());
					ConceptNode replaceChildCopy = new ConceptNode(child.getConcept(),makeName(childCopiesList.get(childCopiesList.size() - 1).getID()));
					
					childCopiesList.add(replaceChildCopy);
					multCopies.put(child.getConcept().getConceptTitle(), childCopiesList);
					
					treeNodesList.add(replaceChildCopy);
					
					treeLinksList.add(new ConceptLink(copiesOfParentsList.get(i), replaceChildCopy));			
				}
			}
		}

		NodeAndLinkLists tempNodeAndLinkList = new NodeAndLinkLists(treeNodesList, treeLinksList);
		return new ConceptGraph(tempNodeAndLinkList);
	}
	
	public ConceptGraph graphToTreeNewLinks(){
		List<IDLink> treeLinksList = new ArrayList<IDLink>();
		HashMap<String,ConceptNode> treeNodesList = new HashMap<String,ConceptNode>();
		HashMap<String, List<ConceptNode>> multCopies = new HashMap<String, List<ConceptNode>>();

		
		for(IDLink currLink : this.idLinks){
			ConceptNode child = this.nodesMap.get(currLink.getChild());
			ConceptNode parent = this.nodesMap.get(currLink.getParent());
			ConceptNode replaceChild = null;
			ConceptNode replaceParent = null;
			
			List<ConceptNode> copiesOfChild = multCopies.get(child.getConcept().getConceptTitle());
			
			//If node has never been copied before
			if(copiesOfChild == null){
				replaceChild = new ConceptNode(child.getConcept(), makeName(child.getID()));
				copiesOfChild = new ArrayList<ConceptNode>();
				copiesOfChild.add(replaceChild);
				multCopies.put(child.getConcept().getConceptTitle(), copiesOfChild);
				treeNodesList.put(child.getConcept().getConceptTitle(),replaceChild);
			}else{
				replaceChild = new ConceptNode(child.getConcept(), makeName(copiesOfChild.get(copiesOfChild.size() - 1).getID()));
				copiesOfChild.add(replaceChild);
				multCopies.put(child.getConcept().getConceptTitle(), copiesOfChild);
				
				treeNodesList.put(child.getConcept().getConceptTitle(),replaceChild);
			}
			
			//If parent is not in treeNodesList
			//Find or create replaceParent
			List<ConceptNode> copiesOfParentsList = multCopies.get(parent.getConcept().getConceptTitle());
			if(copiesOfParentsList == null){
				copiesOfParentsList = new ArrayList<ConceptNode>();
				replaceParent = new ConceptNode(parent.getConcept(), makeName(parent.getID()));
				copiesOfParentsList.add(replaceParent);
				multCopies.put(parent.getConcept().getConceptTitle(), copiesOfParentsList);
				
				treeNodesList.put(parent.getConcept().getConceptTitle(),replaceParent);
				treeLinksList.add(new IDLink(replaceParent.getID(), replaceChild.getID()));
			}else{
				treeLinksList.add((new IDLink(copiesOfParentsList.get(0).getID(), replaceChild.getID())));
				for(int i = 1; i < copiesOfParentsList.size(); i++){
					List<ConceptNode> childCopiesList = multCopies.get(child.getConcept().getConceptTitle());
					ConceptNode replaceChildCopy = new ConceptNode(child.getConcept(),makeName(childCopiesList.get(childCopiesList.size() - 1).getID()));
					
					childCopiesList.add(replaceChildCopy);
					multCopies.put(child.getConcept().getConceptTitle(), childCopiesList);
					
					treeNodesList.put(child.getConcept().getConceptTitle(),replaceChildCopy);
					
					treeLinksList.add(new IDLink(copiesOfParentsList.get(i).getID(), replaceChildCopy.getID()));			
				}
			}
		}

		
		return new ConceptGraph(treeNodesList, treeLinksList);
	}
	
	public static String makeName(String prevName) {
        if(prevName != "" && prevName != null) {
            String[] nameList = prevName.split("");
            String name = "";
            String num = "";
            for (int i = 0; i < nameList.length; i++) {
                try {
                    Integer.parseInt(nameList[i]);
                    num += nameList[i];
                } catch (NumberFormatException e) {
                    name += nameList[i];
                }
            }
            if (num.equals("")) {
                return name + "1";
            } else {
                int number = Integer.parseInt(num);
                number += 1;
                name += number;
                return name;
            }
        }
        return "";
    }
	
}

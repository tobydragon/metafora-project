package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.PerUserPerProblemSummary;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Book;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Chapter;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Question;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.SubChapter;

public class ConceptGraph {
	
	static Logger logger = Logger.getLogger(ConceptGraph.class);
	public static final Integer DIVISION_FACTOR = 2;
	//ConceptNode root;
	List<ConceptNode> roots;
	String stringToReturn = "";
	//List<ConceptNode> nodes;
	//List<ConceptLink> links;
	
	//HashMap<String, List<ConceptNode>> nodesMap;
	//List<IDLink> idLinks;

	/*
	 *Takes in a book, starts at the root, then goes through each level (chapters, sub chapters, questions) and creates
	 *a node for each concept, and adds it as a child.
	 */
	public ConceptGraph(Book b){
		ConceptNode root = new ConceptNode(b);
		List<ConceptNode> nodes = new ArrayList<ConceptNode>();
		List<ConceptLink> links = new ArrayList<ConceptLink>();
		
		
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
		this.roots = new ArrayList<ConceptNode>();
		this.roots.add(root);
	}
	
	public ConceptGraph(NodesAndIDLinks lists) {
		List<ConceptNode> nodes = lists.getNodes();
		List<IDLink> links = lists.getLinks();
		this.roots = findRoot(nodes, links);
		
		addChildren(nodes, links);
	}
	
//	public ConceptGraph(List<ConceptNode> nodes, List<IDLink> links){
//		this.roots = findRoot(nodes, links);
//		
//		addChildren(nodes, links);
//	}
	
	public List<ConceptNode> getNodes(){
		return null;
	}
	
	public List<ConceptLink> getLinks(){
		return null;
	}
	
	public List<IDLink> getIDLinks(){
		return null;
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
			boolean hasAdded = false;
			for(ConceptNode currRoot : this.roots){
				if(hasAdded == false){
					hasAdded = currRoot.addSummaryNode(summaryNode);
				}
			}
			logger.warn("Summary not added, no matching parent found for summary node " + summaryNode);
		}
	}	

	private void addChildren(List<ConceptNode> nodes, List<IDLink> links){

		HashMap<String, ConceptNode> fullNodesMap = new HashMap<String, ConceptNode>();
		for( ConceptNode currNode : nodes){
			fullNodesMap.put(currNode.getID(), currNode);
		}
		
		for( IDLink currLink : links){
			ConceptNode currParent = fullNodesMap.get(currLink.getParent());
			currParent.addChild(fullNodesMap.get(currLink.getChild()));
		}
	}

	
 	private List<ConceptNode> findRoot(List<ConceptNode> nodes, List<IDLink> links) {
		List<ConceptNode> runningTotal = new ArrayList<ConceptNode>();
		for (ConceptNode node: nodes) {
			runningTotal.add(node);
		}
		for (IDLink link : links) {
			if (runningTotal.contains(link.getChild())) {
				runningTotal.remove(link.getChild());
			}
		}
		return runningTotal;
	}

	
	public String toString(){
		
		//return "Nodes:\n"+this.nodesMap+"\nLinks:\n"+this.idLinks;
		
		return roots.toString();
		
	}
	
//	//takes in a ConceptNode and creates an object to hold on to two lists - a list of nodes and a list of links
//		private NodesAndIDLinks buildNodeAndLinkLists(ConceptNode currNode, int level, List<ConceptNode> nodes, List<IDLink> links){
//			currNode.setLevel(level);
//			
//			//checks to see if the current node is already in the list, if not it adds it
//
//			if(nodes.contains(currNode) == false) {
//				nodes.add(currNode);
//			}
//			
//			//goes through each child of the current node and checks to see if there is a link already for that parent/child pair
//			//if not then it adds it
//			for(ConceptNode child : currNode.getChildren()){
//				IDLink linkToAdd = new IDLink (currNode.getID(), child.getID());
//				if(links.contains(linkToAdd) == false){
//					links.add(linkToAdd);
//					
//					//recursively calls the function again with the child as the current node
//					buildNodeAndLinkLists(child, level+1, nodes, links);
//				}
//				
//				
//			}
//			List<ConceptNode> finalNodes = new ArrayList<ConceptNode>();
//			List<IDLink> finalLinks = new ArrayList<IDLink>();
//			
//			for	(ConceptNode node : nodes) {
//				if (node.getLevel() != 0) {
//					finalNodes.add(node);
//				}
//			}
//			for (IDLink link : links) {
//				if (getNodeLevel(link.getParent(), nodes) != 0 && getNodeLevel(link.getChild(), nodes) != 0) {
//					finalLinks.add(link);
//				}
//			}
//			
//			//creates the LinksAndNodes object to hold on to both lists, then returns that object
//			NodesAndIDLinks finalLists = new NodesAndIDLinks(finalNodes, finalLinks);
//			
//			
//			return finalLists;
//		}
		
		public int getNodeLevel(String id, List<ConceptNode> nodes){
			for(ConceptNode currNode : nodes){
				if(currNode.getID().equals(id)){
					return currNode.getLevel();
				}
			}
			return -1;
		}
		
		public NodesAndIDLinks buildNodesAndLinks() {
			List<ConceptNode> tempNodes = new ArrayList<ConceptNode>();
			List<IDLink> tempLinks = new ArrayList<IDLink>();
			for(ConceptNode currRoot : this.roots){
				currRoot.addToNodesAndLinksLists(tempNodes,tempLinks);
			}
			NodesAndIDLinks outputLists = new NodesAndIDLinks(tempNodes, tempLinks);
			return outputLists;
		}
		

	public void calcActualComp(){
		for(ConceptNode root : this.roots){
			root.calcActualComp();
		}
	}

	public void calcPredictedScores() {
		for(ConceptNode root : this.roots){
			calcPredictedScores(root);
		}
		
	}
	
	//TODO: currentRoot.getActualComp used to be this.root.getActualComp, analyze how this changed things
	private void calcPredictedScores(ConceptNode currentRoot) {
		calcPredictedScores(currentRoot, currentRoot.getActualComp(), currentRoot);
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

//	public ConceptGraph graphToTree(){
//		List<ConceptNode> treeNodesList = new ArrayList<ConceptNode>();
//		List<ConceptLink> treeLinksList = new ArrayList<ConceptLink>();
//		HashMap<String, List<ConceptNode>> multCopies = new HashMap<String, List<ConceptNode>>();
//
//		
//		for(ConceptLink currLink : this.links){
//			ConceptNode child = currLink.getChild();
//			ConceptNode parent = currLink.getParent();
//			ConceptNode replaceChild = null;
//			ConceptNode replaceParent = null;
//			
//			List<ConceptNode> copiesOfChild = multCopies.get(child.getConcept().getConceptTitle());
//			
//			//If node has never been copied before
//			if(copiesOfChild == null){
//				replaceChild = new ConceptNode(child.getConcept(), makeName(child.getID()));
//				copiesOfChild = new ArrayList<ConceptNode>();
//				copiesOfChild.add(replaceChild);
//				multCopies.put(child.getConcept().getConceptTitle(), copiesOfChild);
//				treeNodesList.add(replaceChild);
//			}else{
//				replaceChild = new ConceptNode(child.getConcept(), makeName(copiesOfChild.get(copiesOfChild.size() - 1).getID()));
//				copiesOfChild.add(replaceChild);
//				multCopies.put(child.getConcept().getConceptTitle(), copiesOfChild);
//				
//				treeNodesList.add(replaceChild);
//			}
//			
//			//If parent is not in treeNodesList
//			//Find or create replaceParent
//			List<ConceptNode> copiesOfParentsList = multCopies.get(parent.getConcept().getConceptTitle());
//			if(copiesOfParentsList == null){
//				copiesOfParentsList = new ArrayList<ConceptNode>();
//				replaceParent = new ConceptNode(parent.getConcept(), makeName(parent.getID()));
//				copiesOfParentsList.add(replaceParent);
//				multCopies.put(parent.getConcept().getConceptTitle(), copiesOfParentsList);
//				
//				treeNodesList.add(replaceParent);
//				treeLinksList.add(new ConceptLink(replaceParent, replaceChild));
//			}else{
//				treeLinksList.add((new ConceptLink(copiesOfParentsList.get(0), replaceChild)));
//				for(int i = 1; i < copiesOfParentsList.size(); i++){
//					List<ConceptNode> childCopiesList = multCopies.get(child.getConcept().getConceptTitle());
//					ConceptNode replaceChildCopy = new ConceptNode(child.getConcept(),makeName(childCopiesList.get(childCopiesList.size() - 1).getID()));
//					
//					childCopiesList.add(replaceChildCopy);
//					multCopies.put(child.getConcept().getConceptTitle(), childCopiesList);
//					
//					treeNodesList.add(replaceChildCopy);
//					
//					treeLinksList.add(new ConceptLink(copiesOfParentsList.get(i), replaceChildCopy));			
//				}
//			}
//		}
//
//		NodeAndLinkLists tempNodeAndLinkList = new NodeAndLinkLists(treeNodesList, treeLinksList);
//		return new ConceptGraph(tempNodeAndLinkList);
//	}
	
	public ConceptGraph graphToTreeNewLinks(){
		NodesAndIDLinks currentGraphNodesAndLinksList = this.buildNodesAndLinks();
		
		HashMap<String, ArrayList<ConceptNode>> nodesMap = new HashMap<String,ArrayList<ConceptNode>>();
		for( ConceptNode currNode : currentGraphNodesAndLinksList.getNodes()){
			ArrayList<ConceptNode> temp = new ArrayList<ConceptNode>();
			temp.add(currNode);
			nodesMap.put(currNode.getConcept().getConceptTitle(), temp);
		}
		
		
		List<IDLink> treeLinksList = new ArrayList<IDLink>();
		List<ConceptNode> treeNodesList = new ArrayList<ConceptNode>();
		HashMap<String, List<ConceptNode>> multCopies = new HashMap<String, List<ConceptNode>>();

		//for every link in the idLinks
		for(IDLink currLink : currentGraphNodesAndLinksList.getLinks()){
			ConceptNode child = nodesMap.get(currLink.getChild()).get(0); // assign to first item mapped to that concept title
			ConceptNode parent = nodesMap.get(currLink.getParent()).get(0); // assign to first item mapped to that concept title
			ConceptNode replaceChild = null;
			ConceptNode replaceParent = null;
			
			List<ConceptNode> copiesOfChild = multCopies.get(child.getConcept().getConceptTitle());
			
			//If node has never been copied before
			if(copiesOfChild == null){
				//make new conceptNode that is a copy of child
				replaceChild = new ConceptNode(child.getConcept(), makeName(child.getID()));
				copiesOfChild = new ArrayList<ConceptNode>();
				copiesOfChild.add(replaceChild);
				//put new list with just the first copy in it as the value to the concept title key
				multCopies.put(child.getConcept().getConceptTitle(), copiesOfChild);
			}else{
				//node has multiple instances, make a new copy with a new id and add to multCopies
				replaceChild = new ConceptNode(child.getConcept(), makeName(copiesOfChild.get(copiesOfChild.size() - 1).getID()));
				copiesOfChild.add(replaceChild);
				multCopies.put(child.getConcept().getConceptTitle(), copiesOfChild);
			}
			
			//If parent is not in treeNodesList
			//Find or create replaceParent
			List<ConceptNode> copiesOfParentsList = multCopies.get(parent.getConcept().getConceptTitle());
			if(copiesOfParentsList == null){
				copiesOfParentsList = new ArrayList<ConceptNode>();
				replaceParent = new ConceptNode(parent.getConcept(), makeName(parent.getID()));
				copiesOfParentsList.add(replaceParent);
				multCopies.put(parent.getConcept().getConceptTitle(), copiesOfParentsList);
				
				//make link
				treeLinksList.add(new IDLink(replaceParent.getID(), replaceChild.getID()));
			}else{
				treeLinksList.add((new IDLink(copiesOfParentsList.get(0).getID(), replaceChild.getID())));
				for(int i = 1; i < copiesOfParentsList.size(); i++){
					List<ConceptNode> childCopiesList = multCopies.get(child.getConcept().getConceptTitle());
					ConceptNode replaceChildCopy = new ConceptNode(child.getConcept(),makeName(childCopiesList.get(childCopiesList.size() - 1).getID()));
					
					childCopiesList.add(replaceChildCopy);
					multCopies.put(child.getConcept().getConceptTitle(), childCopiesList);
					//make link
					treeLinksList.add(new IDLink(copiesOfParentsList.get(i).getID(), replaceChildCopy.getID()));			
				}
			}
		}

		Iterator it = multCopies.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        for(ConceptNode currNode : (ArrayList<ConceptNode>) pair.getValue()){
	        	treeNodesList.add(currNode);
	        }
	    }
	    
	    logger.debug(treeNodesList);
	    logger.debug(treeLinksList);
		return new ConceptGraph(new NodesAndIDLinks(treeNodesList, treeLinksList));
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
	
	public static void main(String args[]){
		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<IDLink> clList = new ArrayList<IDLink>();
		
		
		//Make simple tree
		Concept c = new ConceptImpl("A");
		ConceptNode cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("B");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("C");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		
		
		clList.add(new IDLink("A","B")); //A -> B
		clList.add(new IDLink("A","C")); //A -> C
		clList.add(new IDLink("B","C")); //B -> C
		
		
		ConceptGraph simpleGraph = new ConceptGraph(new NodesAndIDLinks(cnList,clList));
		//logger.debug(simpleGraph);
		ConceptGraph simpleTree = simpleGraph.graphToTreeNewLinks();
		NodesAndIDLinks treeLists = simpleTree.buildNodesAndLinks();
		//logger.debug(treeLists);
	}
	
}

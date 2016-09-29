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
	List<ConceptNode> roots;
	String stringToReturn = "";

	/*
	 *Takes in a book, starts at the root, then goes through each level (chapters, sub chapters, questions) and creates
	 *a node for each concept, and adds it as a child.
	 */
	
	public ConceptGraph(Book b){
		ConceptNode root = new ConceptNode(b);
		
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
	
	public ConceptGraph(Book b, NodesAndIDLinks lists){
		
	}
	
	public ConceptGraph(List<ConceptNode> rootsIn){
		this.roots = rootsIn;
	}
	
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
			//logger.warn("Summary not added, no matching parent found for summary node " + summaryNode);
		}
	}	

	private void addChildren(List<ConceptNode> nodes, List<IDLink> links){

		HashMap<String, ConceptNode> fullNodesMap = new HashMap<String, ConceptNode>();
		for( ConceptNode currNode : nodes){
			fullNodesMap.put(currNode.getID(), currNode);
		}
		
		for( IDLink currLink : links){
			ConceptNode currParent = fullNodesMap.get(currLink.getParent());
			if(currParent == null){
				logger.warn("In ConceptGraph.addChildren(): " + currLink.getParent()+" node not found in nodes list for link " + currLink);
			}
			else{
				ConceptNode currChild = fullNodesMap.get(currLink.getChild());
				if(currChild == null){
					logger.warn("In ConceptGraph.addChildren(): " + currLink.getChild()+" node not found in nodes list for link " + currLink);
				}
				else{
					currParent.addChild(currChild);
				}
			}
			
		}
	}

	
 	private List<ConceptNode> findRoot(List<ConceptNode> nodes, List<IDLink> links) {
		List<ConceptNode> runningTotal = new ArrayList<ConceptNode>();
		for (ConceptNode node : nodes) {
			runningTotal.add(node);
		}
		for (IDLink link : links) {
			for(ConceptNode node : nodes){
	 			if(node.getID().equals(link.getChild())){
	 				runningTotal.remove(node);
	 			}
	 		}
		}
		return runningTotal;
	}
	
	public String toString(){
		NodesAndIDLinks thisGraph = this.buildNodesAndLinks();
		return "Nodes:\n"+thisGraph.getNodes()+"\nLinks:\n"+thisGraph.getLinks();
		
		//return roots.toString();
		
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
		
		// simple check for if we"re dealing with the root, which has its own rule
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
		List<ConceptNode> newRoots = new ArrayList<ConceptNode>();
		HashMap<String, List<String>> initMultCopies = new HashMap<String, List<String>>();
		for(ConceptNode root : this.roots){
			newRoots.add(root.makeTree(initMultCopies));
		}
		
		return new ConceptGraph(newRoots);
		
	}
	
	public ConceptNode findNode(String ID, List<ConceptNode> NodesToSearch){
		for(ConceptNode currNode : NodesToSearch){
			if(currNode.getID().equals(ID)){
				return currNode;
			}
		}
		return null;
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
		
		clList.add(new IDLink("B","C")); //B -> C
		clList.add(new IDLink("A","C")); //A -> C
		clList.add(new IDLink("A","B")); //A -> B

		
		ConceptGraph simpleGraph = new ConceptGraph(new NodesAndIDLinks(cnList,clList));
		logger.debug(simpleGraph);
		ConceptGraph simpleTree = simpleGraph.graphToTree();
		NodesAndIDLinks treeLists = simpleTree.buildNodesAndLinks();
		logger.debug(simpleTree);
		System.out.println(simpleTree);
		
		
	}
	
}

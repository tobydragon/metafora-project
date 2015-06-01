package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Book;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Chapter;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Question;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.SubChapter;

import java.util.ArrayList;
import java.util.List;

public class ConceptGraph {
	
	List<Chapter> chapters; 
	List<SubChapter> subChaps;
	List<Question> questions;
	ConceptNode root;
	
	
	/*
	 *Takes in a book, starts at the root, then goes through each level (chapters, sub chapters, questions) and creates
	 *a node for each concept, and adds it as a child.
	 */
	public ConceptGraph(Book b){
	
		root = new ConceptNode(b);
	
		chapters = b.chaps;
		for (Chapter chap : chapters){
			ConceptNode chapNode = new ConceptNode(chap);
			root.addChild(chapNode);
			
			subChaps = chap.subChapters;
			for (SubChapter subChap : subChaps){
				ConceptNode subChapNode = new ConceptNode(subChap);
				chapNode.addChild(subChapNode);
				
				questions = subChap.Questions;
				for(Question ques : questions){
					ConceptNode quesNode = new ConceptNode(ques);
					subChapNode.addChild(quesNode);
				}
				
			}
		}
	
				
	}
	
	//First attempt at trying to print the graph
	//Currently being used in order to see that each node is added correctly 
	public String toString(){
		int rootChildrenSize;
		int firstLevelChildrenSize;
		int secondLevelChildrenSize;
		
		
		
		List<ConceptNode> rootChildren = root.getChildren();
		rootChildrenSize = rootChildren.size();
		//System.out.println(rootChildrenSize);
			
		for(ConceptNode child : rootChildren){
			System.out.println(child.getConcept().getConceptTitle());
			List<ConceptNode> tempList = child.getChildren();
			firstLevelChildrenSize = tempList.size();
			//System.out.println(firstLevelChildrenSize);
			
			for(ConceptNode child1 : tempList){
				System.out.println(child1.getConcept().getConceptTitle());
				List<ConceptNode> tempList1 = child1.getChildren();
				secondLevelChildrenSize = tempList1.size();
				//System.out.println(secondLevelChildrenSize);
				
				for (ConceptNode child2 : tempList1){
					System.out.println(child2.getConcept().getConceptTitle());
				}
			}
			
		}
		
		
		
		return "";
	}
	
}

package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Book;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Chapter;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Question;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.SubChapter;

import java.util.ArrayList;
import java.util.List;

public class ConceptGraph {
	
	ConceptNode root;
	
	/*
	 *Takes in a book, starts at the root, then goes through each level (chapters, sub chapters, questions) and creates
	 *a node for each concept, and adds it as a child.
	 */
	public ConceptGraph(Book b){
		root = new ConceptNode(b);
	
		List<Chapter> chapters = b.getChapters();
		for (Chapter chap : chapters){
			ConceptNode chapNode = new ConceptNode(chap);
			root.addChild(chapNode);
			
			List<SubChapter> subChaps = chap.getSubChapters();
			for (SubChapter subChap : subChaps){
				ConceptNode subChapNode = new ConceptNode(subChap);
				chapNode.addChild(subChapNode);
				
				List<Question> questions = subChap.getQuestions();
				for(Question ques : questions){
					ConceptNode quesNode = new ConceptNode(ques);
					subChapNode.addChild(quesNode);
				}
				
			}
		}		
	}
	
	public String toString(){
		return toString("");
	}
	
	//First attempt at trying to print the graph
	//Currently being used in order to see that each node is added correctly 
	public String toString(String indent){
		int rootChildrenSize;
		int firstLevelChildrenSize;
		int secondLevelChildrenSize;
		
		
		System.out.println(indent + root.getConcept().getConceptTitle());
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

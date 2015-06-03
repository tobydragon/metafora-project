package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Book;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Chapter;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Question;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.SubChapter;

import java.util.List;

public class ConceptGraph {
	
	ConceptNode root;
	String stringToReturn = "";
	
	/*
	 *Takes in a book, starts at the root, then goes through each level (chapters, sub chapters, questions) and creates
	 *a node for each concept, and adds it as a child.
	 */
	public ConceptGraph(Book b){
		root = new ConceptNode(b);
		
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
		
	
	public String toString(){
		return toString("\n", root);
	}
	
	
	//Prints the concept graph using recursion 
	public String toString(String indent, ConceptNode node){
		
		//gets the children of the current node
		List<ConceptNode> children = node.getChildren();
		//appends the conceptTitle to the string 
		stringToReturn = stringToReturn + indent + node.getConcept().getConceptTitle();

		//goes through the children of the current node
		for(ConceptNode child : children){
			//if the current node has no children, return the string
			if (child.getChildren() == null){
				return stringToReturn;
			}
			//if the current node does have children, make a recursive call with the child as the node
			else{
				toString(indent + "\t", child);	
			}
		}
		return stringToReturn;
	}	
}

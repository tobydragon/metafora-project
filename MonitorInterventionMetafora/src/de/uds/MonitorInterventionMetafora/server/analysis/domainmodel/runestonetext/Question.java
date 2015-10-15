package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.Concept;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.SummaryInfo;

public class Question implements Concept{
	private QuestionType type;
	private String conceptTitle;
	
	
	public Question() {
		super();
	}
	//constructor
	//takes in the line and the question type
	public Question(String line, QuestionType type) {
		this.type = type;
		this.conceptTitle = getQuestion(line).trim();
		
	}
	//strips the line of the extra symbols
	public String getQuestion(String line){
		 String question = line.trim();
		 //question = question.replaceAll("[^a-zA-Z0-9]+", "");
		 
		 //the colon and the period were the only symbols that shouldn't be in the question title. Eliminate and replace those. 
		 question = question.replace(":", "");
		 question = question.replace(".",  "");
		 return question;
	}
	public String toString(){
		String typeAndTitle = type +" "+ conceptTitle;
		return typeAndTitle;
	}
	
	public String getConceptTitle(){
		return conceptTitle;
	}
	
	public SummaryInfo getSummaryInfo(){
		return new SummaryInfo();
	}

}

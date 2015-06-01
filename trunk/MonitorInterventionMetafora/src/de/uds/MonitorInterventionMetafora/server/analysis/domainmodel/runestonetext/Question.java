package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.Concept;

public class Question implements Concept{
	private String type;
	private String title;
	
	//constructor
	//takes in the line and the question type
	public Question(String line, String type) {
		this.type = type;
		this.title = getQuestion(line);
		
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
		String typeAndTitle = type +" "+title;
		return typeAndTitle;
	}
	
	public String getConceptTitle(){
		return title;
	}

}

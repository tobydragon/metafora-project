package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

public class Question {
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
		 question = question.replaceAll("[^a-zA-Z0-9]+", "");
		 return question;
	}
	public String toString(){
		String typeAndTitle = type +" "+title;
		return typeAndTitle;
	}

}

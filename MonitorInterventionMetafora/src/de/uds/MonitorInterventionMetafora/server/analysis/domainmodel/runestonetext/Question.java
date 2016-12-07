package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

import java.util.ArrayList;
import java.util.List;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.Concept;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.IDLink;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.SummaryInfo;

public class Question implements Concept{
	private QuestionType type;
	private String conceptTitle;
	List<String> tags;
	String questionText;             
	
	
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
	
	public static List createTags(String tagsString){
		int indx = tagsString.indexOf(":");
		String[] temptags = tagsString.substring(indx+1, tagsString.length()).split(", ");
		ArrayList tempArray = new ArrayList();
		for(int i = 0; i < temptags.length; i++){
			tempArray.add(temptags[i]);
		}
		return tempArray;
	}
	
	public List<IDLink> buildTagLinks(){
		ArrayList<IDLink> listOfLinks = new ArrayList<IDLink>();
		for(String tag: tags){
			listOfLinks.add(new IDLink(tag,conceptTitle));
		}
		return listOfLinks;
	}
	
	public void setTags(List tagsIn){
		tags = tagsIn;
	}
	
	public void addTag(String tagIn){
		tags.add(tagIn);
	}
	public List getTags(){
		return tags;
	}
	
	public QuestionType getType(){
		return type;
	}
	
	public SummaryInfo getSummaryInfo(){
		return new SummaryInfo();
	}

}

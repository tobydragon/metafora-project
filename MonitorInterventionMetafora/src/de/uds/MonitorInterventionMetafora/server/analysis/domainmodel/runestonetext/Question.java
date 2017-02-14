package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

import java.util.ArrayList;
import java.util.List;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.Concept;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.IDLink;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.SummaryInfo;
import de.uds.MonitorInterventionMetafora.shared.utils.PeekableScanner;

public class Question implements Concept{
	private QuestionType type;
	private String questionId;
	List<String> tags;
	String questionText;             
	
	public Question(List<String> questionLines){
		String titleLine = questionLines.get(0);
		for (QuestionType questionType : QuestionType.values()){
			if (titleLine.contains(questionType.getSourceString())){
				this.type = questionType;
				this.questionId = titleLine.replaceAll(questionType.getSourceString(), "").replaceAll(":", "").replaceAll("\\.", "").trim();
			}
		}
		//TODO: this doesn't work for all of them, need to figure why for some and not for others
		//questionText = questionLines.get(questionLines.size()-1).trim();
		//Quick Fix: for now, just put all lines into text
		questionText = "";
		for (String line : questionLines){
			questionText += line+"|||";
		}
	}
	
	//takes in the line and the question type
	public Question(String line, QuestionType type) {
		this.type = type;
		this.questionId = getQuestion(line).trim();
		
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
		return  questionId  +" "+ type+ ": " + questionText;
		
	}
	
	public String getConceptTitle(){
		return questionId;
	}
	
	public static List<String> createTags(String tagsString){
		int indx = tagsString.indexOf(":");
		String[] temptags = tagsString.substring(indx+1, tagsString.length()).split(", ");
		ArrayList<String> tempArray = new ArrayList<String>();
		for(int i = 0; i < temptags.length; i++){
			tempArray.add(temptags[i]);
		}
		return tempArray;
	}
	
	public List<IDLink> buildTagLinks(){
		ArrayList<IDLink> listOfLinks = new ArrayList<IDLink>();
		for(String tag: tags){
			listOfLinks.add(new IDLink(tag,questionId));
		}
		return listOfLinks;
	}
	
	public void setTags(List<String> tagsIn){
		tags = tagsIn;
	}
	
	public void addTag(String tagIn){
		tags.add(tagIn);
	}
	public List<String> getTags(){
		return tags;
	}
	
	public QuestionType getType(){
		return type;
	}
	
	public SummaryInfo getSummaryInfo(){
		return new SummaryInfo();
	}

	public String getQuestionText() {
		
		return questionText;
	}
	
	//returns all the lines of a question, in a list
	//side effect: scanner will have all lines of current question removed
	public static List<String> buildQuestionTextList(PeekableScanner scanner){
		List<String> linesList = new ArrayList<String>();
		linesList.add(scanner.nextLine());
		//only take lines until there aren't 3 spaces at the beginning (or is blank)
		while (scanner.hasNextLine() && (scanner.peek().startsWith("   ") || "".equals(scanner.peek().trim()) ) ) {
			String line = scanner.nextLine();
			if (!"".equals(line.trim())){
				linesList.add(line);
			}
		}
		return linesList;
	}

}

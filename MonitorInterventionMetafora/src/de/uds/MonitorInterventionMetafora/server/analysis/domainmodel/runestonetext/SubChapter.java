package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.Concept;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.SummaryInfo;
import de.uds.MonitorInterventionMetafora.shared.utils.PeekableScanner;


public class SubChapter implements Concept{
	private String conceptTitle;
	private String fileName;
	private String filePath;
	private List<Question> questions;
	
	
	public SubChapter() {
		super();
	}
	//constructor
	//takes a title for the subchapter, and the chapter title
	public SubChapter(String t, String chap, String path)throws FileNotFoundException{
		//deletes the ".rst" from the SubChapter title
		t = t.replace(".rst",  "");
		this.conceptTitle = t;
		this.fileName = chap.trim();
		this.filePath = path;
		//getQs(conceptTitle,fileName, filePath);
		filePath = filePath+"_sources/"+chap.trim()+"/"+conceptTitle+".rst";
		questions = parseFileForQuestions(filePath);
		
	}

	public SubChapter(String t){
		this.conceptTitle = t;
		questions = new ArrayList<Question>();
	}
	
	public String toString(){
		String subAndQuestions = conceptTitle;
		for (int i=0;i<questions.size();i++){
			subAndQuestions+="\n"+"\t\t\t"+questions.get(i).toString();
		}
		return subAndQuestions;
	}

	//returns list of question objects from a given file
	public static List<Question> parseFileForQuestions (String path) throws FileNotFoundException{
		List<Question> questions = new ArrayList<Question>();
		
		File fIn = new File(path);
		PeekableScanner scanner = new PeekableScanner(fIn);
		while (scanner.hasNextLine()){
			//Either we find a question, and the builder takes all the question lines, or we throw the line away (not a question)
			String nextLineToCome = scanner.peek();
			boolean notQuestionLine = true;
			for (QuestionType questionType : QuestionType.values()){
				if (nextLineToCome.contains(questionType.getSourceString())){
					List<String> questionLines = Question.buildQuestionTextList(scanner);
					questions.add(new Question(questionLines));
					notQuestionLine = false;
				}
			}
			if (notQuestionLine){
				scanner.nextLine();
			}
		}
		scanner.close();
		return questions;
	}
	
	//TODO: old version that has tag stuff, which shoudl be functional in new version
	//takes in the subject and chapter
	public void getQs (String sub, String chap, String path)throws FileNotFoundException{
		chap = chap.trim();	
		//takes the filepath passed down from BookTest and appends the necessary add ons to it
		//need to add the ".rst" back in since it was deleted from the title
		//TODO: Fix this comment
		filePath = filePath+"_sources/"+chap+"/"+sub+".rst";
		Scanner findQs;
		File fIn = new File(filePath);
		findQs = new Scanner(fIn);
			while (findQs.hasNextLine()){
				String line = findQs.nextLine();
				for (QuestionType questionType : QuestionType.values()){
					if (line.contains(questionType.getSourceString())){
						questions.add(new Question(line.replaceAll(questionType.getSourceString(), ""),questionType));
					}
				}
				if(line.contains(".. tag")){
					questions.get(questions.size()-1).setTags(line);
				}
			}
		findQs.close();
	}
	
	public String getConceptTitle(){
		return conceptTitle;
	}

	@JsonIgnore
	public List<Question> getQuestions() {
		return questions;
	}
	
	public SummaryInfo getSummaryInfo(){
		return new SummaryInfo();
	}
	
	public Question findQuestion(String questionId) {
		Question theQuestion = null;
		for (Question question : questions){
			if (questionId.equals(question.getConceptTitle())){
				theQuestion = question;
			}
		}
		return theQuestion;
	}
}

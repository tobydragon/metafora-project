package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.Concept;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.SummaryInfo;


public class SubChapter implements Concept{
	private String conceptTitle;
	private String fileName;
	private String filePath;
	private List<Question> questions = new ArrayList<Question>();
	
	
	public SubChapter() {
		super();
	}
	//constructor
	//takes a title for the subchapter, and the chapter title
	public SubChapter(String t, String chap, String path)throws FileNotFoundException{
		//deletes the ".rst" from the SubChapter title
		t = t.replace(".rst",  "");
		this.conceptTitle = t;
		this.fileName = chap;
		this.filePath = path;
		getQs(conceptTitle,fileName, filePath);
	}

	public String toString(){
		String subAndQuestions = conceptTitle;
		for (int i=0;i<questions.size();i++){
			subAndQuestions+="\n"+"\t\t\t"+questions.get(i).toString();
		}
		return subAndQuestions;
	}

	//adds question objects to the list of questions
	//takes in the subject and chapter
	public void getQs (String sub, String chap, String path)throws FileNotFoundException{
		chap = chap.trim();	
		//takes the filepath passed down from BookTest and appends the necessary add ons to it
		//need to add the ".rst" back in since it was deleted from the title
		filePath = filePath+"source/"+chap+"/"+sub + ".rst";
		Scanner findQs;
		findQs = new Scanner(new File(filePath));
			while (findQs.hasNextLine()==true){
				String line = findQs.nextLine();
				for (QuestionType questionType : QuestionType.values()){
					if (line.contains(questionType.getSourceString())){
						questions.add(new Question(line.replaceAll(questionType.getSourceString(), ""),questionType));
					}
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
}

package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.Concept;


public class SubChapter implements Concept{
	private String title;
	private String fileName;
	private String filePath;
	private List<Question> questions = new ArrayList<Question>();
	
	//constructor
	//takes a title for the subchapter, and the chapter title
	public SubChapter(String t, String title2, String path)throws FileNotFoundException{
		//deletes the ".rst" from the SubChapter title
		t = t.replace(".rst",  "");
		this.title = t;
		this.fileName = title2;
		this.filePath = path;
		getQs(title,fileName, filePath);
	}

	public String toString(){
		String subAndQuestions = title;
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
		return title;
	}

	public List<Question> getQuestions() {
		return questions;
	}
	
	public long getSummaryInfo(){
		return 0;
	}
}

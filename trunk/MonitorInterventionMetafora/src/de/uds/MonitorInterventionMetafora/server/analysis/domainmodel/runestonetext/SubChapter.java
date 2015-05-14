package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

public class SubChapter {
	public String title;
	private String fileName;
	public List<String> questions = new ArrayList<String>();
	public List<Question> Questions = new ArrayList<Question>();
	public List<String> qTypes = new ArrayList<String>();
	
	//constructor
	//takes a title for the subchapter, and the chapter title
	public SubChapter(String t, String title2)throws FileNotFoundException{
		this.title = t;
		this.fileName = title2;
		this.qTypes.add("activecode");
		this.qTypes.add("parsonsprob");
		this.qTypes.add("actex");
		this.qTypes.add("mchoicemf");
		getQs(title,fileName);
	}

	public String toString(){
		String subAndQuestions = title;
		for (int i=0;i<Questions.size();i++){
			subAndQuestions+="\n"+"\t\t\t"+Questions.get(i).toString();
		}
		return subAndQuestions;
	}
	//adds more question types to the list of question types
	public void addQType(String Q){
		this.qTypes.add(Q);
	}
	//adds question objects to the list of questions
	//takes in the subject and chapter
	public void getQs (String sub, String chap)throws FileNotFoundException{
		String filePath = "war/conffiles/domainfiles/thinkcspy/source/";
		chap = chap.trim();
		filePath = filePath+chap+"/"+sub;
		
		Scanner findQs;
		findQs = new Scanner(new File(filePath));
			while (findQs.hasNextLine()==true){
				String line = findQs.nextLine();
				for (int i=0;i<qTypes.size();i++){
					if (line.contains(qTypes.get(i))){
						Questions.add(new Question(line.replaceAll(qTypes.get(i), ""),qTypes.get(i)));
					}
				}
			}
		findQs.close();
	}
}

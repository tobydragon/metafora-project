package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.Concept;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.SummaryInfo;

public class Book implements Concept {
	private String conceptTitle;
	private List<Chapter> chaps = new ArrayList<Chapter>();
	
	//constructor
	//takes a string that's the title and a file path as parameters
	public Book(String t, String filePath) {
		conceptTitle = t;
		List<String> chapsAndSubs = getFile(filePath);
		List<String> subjects = new ArrayList<String>();
		for (int i = 0; i < chapsAndSubs.size(); i++) {
			String parts[] = chapsAndSubs.get(i).split("/");
			subjects.add(parts[1]);
		}
		List<String> chapters = getChapters(chapsAndSubs);
		for(int i=0; i<chapters.size();i++){
			chaps.add(new Chapter(chapters.get(i), subjects, chapsAndSubs, filePath));
			//chaps.add(newChapter(chapters.get(i),chapsAndSubs,subjects, filePath));
		}
	}
	//creates a string list of the chapters
	//takes the list of allChapterFiles
	public List<String> getChapters(List<String> allLines){
		List<String> chapters = new ArrayList<String>();
		int currentChap = 0;
		for (int i = 0; i<allLines.size();i++){
			if(i == 0){
				String temp = allLines.get(i).split("/")[0];
				chapters.add(temp.replaceAll("\\s+",""));
			}
			if (!(allLines.get(i).contains(chapters.get(currentChap)))){
				String temp = allLines.get(i).split("/")[0];
				chapters.add(temp.replaceAll("\\s+",""));
				currentChap++;
			}
		}
		return chapters;
	}
	public String toString(){
		String book = conceptTitle;
		for (int i=0; i<chaps.size();i++){
			book += "\n"+"\t"+chaps.get(i).toString();
		}
		return book;
	}
	
	//gets a string list of all the lines of the allChapterFiles text file
	public List<String> getFile(String filePath){
		List<String> chapsAndSubs = new ArrayList<String>();
		try {
			Scanner sc = new Scanner(new File(filePath+"allChapterFiles.txt"));
			while (sc.hasNextLine() == true) {
				String line = sc.nextLine();
				if (!(line.isEmpty())) {
					chapsAndSubs.add(line);
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return chapsAndSubs;
	}
	
	public String getConceptTitle(){
		return conceptTitle;
	}
	
	@JsonIgnore
	public List<Chapter> getChapters(){
		return chaps;
	}
	
	public SummaryInfo getSummaryInfo(){
		return new SummaryInfo();
	}
	

}

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
	
	public Book() {
		super();
	}
	
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
			chaps.add(newChapter(chapters.get(i),chapsAndSubs,subjects, filePath));
		}
	}
	//creates a string list of the chapters
	//takes the list of allChapterFiles
	public List<String> getChapters(List<String> allLines){
		List<String> chapters = new ArrayList<String>();
		int currentChap = 0;
		for (int i = 0; i<allLines.size();i++){
			if(i == 0){
				chapters.add(allLines.get(i).split("/")[0]);
			}
			if (!(allLines.get(i).contains(chapters.get(currentChap)))){
				chapters.add(allLines.get(i).split("/")[0]);
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
	
	//creates a new chapter object
	//takes the chapter title, the allChapterFile list, and list of chapters and returns a chapter object
	public Chapter newChapter(String chap, List<String> fileList, List<String> allSubChap, String filePath){
		List<Integer> position = new ArrayList<Integer>();
		List<String> subChap = new ArrayList<String>();
		for (int i=0; i<fileList.size(); i++){
			if (fileList.get(i).contains(chap)){
				position.add(i);
			}
		}
		for (int i=0;i<position.size();i++){
			subChap.add(allSubChap.get(position.get(i)));
		}
		return new Chapter(chap,subChap, filePath);
		
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

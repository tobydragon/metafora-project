package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.Concept;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.SummaryInfo;

public class Chapter implements Concept{
	
	private String conceptTitle;
	private List<SubChapter> subChapters;

	
	public Chapter() {
		super();
	}
	//constructor
	//takes parameters of a title and list of subjects
	public Chapter(String t, List<String> subjectList, List<String> chapsAndSubs, String filePath) {
//		subChapters = new ArrayList<SubChapter>();
//		conceptTitle = t;
//		for (int i=0;i<subjectList.size();i++){
//			try {
//				String filePathTest = filePath + "_sources/" + this.conceptTitle+"/" + subjectList.get(i);
//				filePathTest = filePathTest.replaceAll("\\s+","");
//				
//				//change filePath
//				subChapters.add(new SubChapter(subjectList.get(i), conceptTitle, filePath));
//			} 
//			catch(FileNotFoundException e) {
//				System.out.println("WARN Subchapter missing: " + subjectList.get(i));
//			}
//		}
		subChapters = new ArrayList<SubChapter>();
		conceptTitle = t;	
		for(int i = 0; i < chapsAndSubs.size(); i++){
			String[] parts = chapsAndSubs.get(i).split("/");
			if(parts[0].contains(t)){
				try {
				subChapters.add(new SubChapter(parts[1], parts[0], filePath));
//					String filePathTest = filePath + "_sources/" + this.conceptTitle+"/" + parts[1];
//					filePathTest = filePathTest.replaceAll("\\s+","");
//					
//					//change filePath
//					subChapters.add(new SubChapter(subjectList.get(i), conceptTitle, filePath));
				} catch(FileNotFoundException e) {
					subChapters.add(new SubChapter(parts[1]));
					//System.out.println("WARN Subchapter missing: " + parts[1]);
				}
			}
		}
	}
	
	public String toString(){
		String subChaps = conceptTitle;
		for (int i=0;i<subChapters.size();i++){
			subChaps += "\n"+"\t\t"+subChapters.get(i).toString();
		}
		return subChaps;
	}
	
	public String getConceptTitle(){
		return conceptTitle;
	}

	@JsonIgnore
	public List<SubChapter> getSubChapters() {
		// TODO Auto-generated method stub
		return subChapters;
	}
	
	public SummaryInfo getSummaryInfo(){
		return new SummaryInfo();
	}
	
}

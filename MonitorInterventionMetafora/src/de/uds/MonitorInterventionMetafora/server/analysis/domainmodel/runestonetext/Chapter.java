package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.Concept;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.SummaryInfo;

public class Chapter implements Concept{
	
	private String title;
	private List<SubChapter> subChapters;

	//constructor
	//takes parameters of a title and list of subjects
	public Chapter(String t, List<String> subjectList, String filePath) {
		subChapters = new ArrayList<SubChapter>();
		title = t;
		for (int i=0;i<subjectList.size();i++){
			try {
				subChapters.add(new SubChapter(subjectList.get(i), title, filePath));
			} 
			catch(FileNotFoundException e) {
				System.out.println("WARN Subchapter missing: " + subjectList.get(i));
			}
		}
	}
	
	public String toString(){
		String subChaps = title;
		for (int i=0;i<subChapters.size();i++){
			subChaps += "\n"+"\t\t"+subChapters.get(i).toString();
		}
		return subChaps;
	}
	
	public String getConceptTitle(){
		return title;
	}

	public List<SubChapter> getSubChapters() {
		// TODO Auto-generated method stub
		return subChapters;
	}
	
	public SummaryInfo getSummaryInfo(){
		return new SummaryInfo();
	}
	
}

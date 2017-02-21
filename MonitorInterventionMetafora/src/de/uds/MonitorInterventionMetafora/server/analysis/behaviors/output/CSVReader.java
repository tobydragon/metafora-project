package de.uds.MonitorInterventionMetafora.server.analysis.behaviors.output;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.ManualGradedLearningObject;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.ManualGradedResponse;


public class CSVReader {
	String filename;
	BufferedReader csvBuffer = null;
	//HandGradedQuestionsList
	ArrayList<ManualGradedLearningObject> mgloList;
	ArrayList<ManualGradedResponse> mgrList;
	
	
	public CSVReader(String filename){
		this.filename = filename;
		mgrList = new ArrayList<ManualGradedResponse>();
		mgloList = new ArrayList<ManualGradedLearningObject>();
		try {
			String line;
			this.csvBuffer = new BufferedReader(new FileReader(filename));
			ArrayList<ArrayList<String>> lineList = new ArrayList<ArrayList<String>>();
			while((line = this.csvBuffer.readLine())!= null){
				lineList.add(lineToList(line));
			}
			boolean firstIteration = true;
			for(ArrayList<String> singleList: lineList){
				int i = 2;
				if(firstIteration == true){
					firstIteration = false;
					while(i<singleList.size()){
						String question = singleList.get(i);
						int begin = question.lastIndexOf('[');
						int end = question.lastIndexOf(']');
						String maxScoreStr = question.substring(begin+1,end);
						double maxScore = Double.parseDouble(maxScoreStr);
						question = question.substring(0, begin-1);
						ManualGradedLearningObject learningObject = new ManualGradedLearningObject(question,maxScore);
						this.mgloList.add(learningObject);
						i++;
					}
				} else {
					String stdID = singleList.get(0);
					while(i<singleList.size()){
						ManualGradedLearningObject curLO = this.mgloList.get(i-2);
						String qid = curLO.getID();
						double maxScore = curLO.getMaxPossibleScore();
						
						ManualGradedResponse response = new ManualGradedResponse(qid,maxScore,Double.parseDouble(singleList.get(i)),stdID);
						curLO.addStudentAnswer(response);
						this.mgrList.add(response);
						i++;
					}
					
				}
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<ManualGradedResponse> getManualGradedResponses(){
		return this.mgrList;
	}
	
	public ArrayList<ManualGradedLearningObject> getManualGradedLearingObjects(){
		return this.mgloList;
	}
	
	public static ArrayList<String> lineToList(String line) {
		ArrayList<String> returnlist = new ArrayList<String>();
		
		if (line != null) {
			String[] splitData = line.split("\\s*,\\s*");
			for (int i = 0; i < splitData.length; i++) {
				if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
					returnlist.add(splitData[i].trim());
				}
			}
		}
		
		return returnlist;
	}
	

}

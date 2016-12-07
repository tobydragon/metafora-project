package de.uds.MonitorInterventionMetafora.server.analysis.behaviors.output;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.PerUserPerProblemSummary;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.ConceptNode;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.SummaryInfo;

public class CSVOutputter {
	
	Map<String, Map<String, Integer>> studentsToQuestions;
	
	
	/**
	 * Constructor used for actually creating a CSV File, calls the second constructor
	 * @param filename  - name of CSV file to be output
	 * @param summaries - List of PerUserPerProblemSummaries
	 * @throws IOException
	 */
	public CSVOutputter(String filename, List<PerUserPerProblemSummary> summaries) throws IOException{
		this(summaries);
		toCSV(filename);
	}
	/**
	 * Takes the list of PUPPS and converts them all into a CSV file based on whether or not they were answered 
	 * correctly. Read functions to find out more about the formatting (makeCSV())
	 * @param summaries takes a list of Per User Per Problem Summaries
	 */
	public CSVOutputter(List<PerUserPerProblemSummary> summaries){
		studentsToQuestions = new HashMap<String, Map<String, Integer>>();
		
		//Go through all summaries, find the student, call get on the HashMap with the student ID, if returns null then make a new HashMap
		//and put it in for the student
		//add a new function in PUPPSum that returns right or wrong
		
		for(PerUserPerProblemSummary summary: summaries){
			
			Map<String, Integer> questionsToAnswer = studentsToQuestions.get(summary.getUser());
			//creates the student if it does not exist in the map yet
			if(questionsToAnswer == null){
				questionsToAnswer = new HashMap<String,Integer>();
				studentsToQuestions.put(summary.getUser(), questionsToAnswer);
			}
			
			//If there are any wrong attempts before correct answer, the question is considered wrong
			// we might want to look closer into wrong after right answers, but not written in SummaryInfo yet.
			int correct = 1;
			if(summary.getSummaryInfo().getTotalWrongAttemptsBeforeRight()>0){
				correct = 0;
			}
			//gets the right map for the current user of this summary and adds to it
			questionsToAnswer.put(summary.getObjectId(),correct);	
		}
		
	}
	/**
	 * This function just takes a students ID and then returns the question Map associated with that student
	 * @param key - a string that is a key from a studentToQuestions map
	 * @return Map<String, Integer> - the student's map of questions
	 */
	public Map<String, Integer> getValueinStudentsMap(String key){
		return studentsToQuestions.get(key);
	}
	/**
	 * The purpose of this function is to go through every student and add all the questions to a sorted set which gets rid of any 
	 * duplicates so that we know every question is in the set and there are no repeats.
	 * @param toSet - Map<String, Map<String, Integer>> which is structured as <Student ID, <Question ID, if correct or not>>
	 * @return SortedSet<String> of all the unique questions answered by all students (Question ID)
	 */
	public static SortedSet<String> questionsToSortedSet(Map<String, Map<String, Integer>> toSet){
		SortedSet<String> set = new TreeSet<>();
		//Goes through every student and adds each question each student did to a SortedSet
		for(String student: toSet.keySet()){
			for(String question: toSet.get(student).keySet()){
					set.add(question);
			}
			
		}
		//System.out.println(set);
		return set;
	}
	/**
	 * Creates the string that gets printed in the CSV
	 * questions on the top x
	 * students on the y
	 * and the content is whether or not they got that question right(1) or wrong(0)
	 * blank means they have no data for it
	 * @return csvString - string getting put in the CSV file
	 */
	public String makeCSV(){
		String csvString = ",";
		//puts the questions across the top in alphabetical order
		SortedSet<String> questionSet = questionsToSortedSet(studentsToQuestions);
		for(String question: questionSet){
			csvString+=question+",";
		}
		csvString+="\n";
		//Goes through all the students now and and then goes through the questions and adds whether
		//they got the question right or wrong and leaves a blank if they didn't answer it
		//TEMP: an int or sanity check of wrong answers
		int wrongAnswerCount=0;
		for(String student: studentsToQuestions.keySet()){
			csvString+=student+",";
			for(String question: questionSet){
				for(String stuQuestion: getValueinStudentsMap(student).keySet()){
					if(question == stuQuestion){
						//TEMP continued
						int answerBool = studentsToQuestions.get(student).get(question);
						if (answerBool == 0){
							wrongAnswerCount++;
						}
						csvString+=studentsToQuestions.get(student).get(question);
					}
				}
				csvString+=",";
			}
			csvString+="\n";
		}
		//TEMP final
		System.out.println("Total Wrong answers:" + wrongAnswerCount);
		return csvString;
	}
	/**
	 * writes the CSV file of students to questions 
	 * see makeCSV() for more information about the string
	 * @param filename - the name for the file, WITHOUT '.csv' at the end
	 * @throws IOException
	 */
	//filename should not include the extension
	public void toCSV(String filename) throws IOException{
		
		Path file = Paths.get(filename+".csv");

		String input = makeCSV();
		
		List<String> lines = Arrays.asList(input);
		Files.write(file, lines, Charset.forName("UTF-8"));
	}
}



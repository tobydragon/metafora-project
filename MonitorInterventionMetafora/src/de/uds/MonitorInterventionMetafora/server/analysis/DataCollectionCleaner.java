package de.uds.MonitorInterventionMetafora.server.analysis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.AssessablePerUserPerProblemSummary;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorFilters;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.ObjectSummaryIdentifier;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.PerUserPerProblemSummary;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.output.CSVOutputter;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Book;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfInteractionDataParser;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.analysis.AnalysisActions;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;

public class DataCollectionCleaner {
	static Logger logger = Logger.getLogger(DataCollectionCleaner.class);

	
	//mutator, replaces IDs, and returns the map of old to new IDs
	public static Map <String, String> replaceAllIds(List<CfAction> cfActions, List<String> oldIds, int startingId){
		Collections.shuffle(oldIds);
		Map <String, String> old2newIds = calcNewIds(oldIds, startingId);
		replaceAllIds(cfActions, old2newIds);
		return old2newIds;
	}
		
	//mutator, changes original object
	private static void replaceAllIds(List<CfAction> cfActions, Map<String, String> old2newId){
		for (CfAction action : cfActions){
			action.replaceUserIds(old2newId, true);
		}
	}
	
	private static Map <String, String> calcNewIds(List<String> oldIds, int startingId){
				String prefix = "student";
		Map <String, String> old2newId = new TreeMap<String, String>();
		for (String oldId : oldIds){
			old2newId.put(oldId, prefix+startingId);
			startingId++;
		}
		
		return old2newId;	
	}
	
	
	private static void removeIpNumberNames(List<String> names){
		Iterator<String> iterator = names.iterator();
		while (iterator.hasNext()) {
		    String name = iterator.next();
		    if (name.length() > 20 && name.substring(0,20).matches("\\d+")){
		    	iterator.remove();
			}
		}
	}
	
	private static List<String> getAllValidNames(List<CfAction> actions){
		List<String> names = AnalysisActions.getOriginatingUsernames(actions);
		removeIpNumberNames(names);
		return names;
	}
	
	//mutator
	private static void combineActionsForMultipleNames( List<CfAction> actions, List<String> allNames) {
		if (allNames.size() > 1){
			String nameToUse = allNames.get(0);
			//make name mapping
			Map<String, String> old2newIds = new TreeMap<String, String>();
			for (String name : allNames.subList(1, allNames.size())){
				old2newIds.put(name, nameToUse);
			}
			//change all the actions with 
			for (CfAction action : actions){
				action.replaceUserIds(old2newIds, false);
			}
		}
	}
	
	public static void writeCSV(List<CfAction> allActions, String filename){
		//Creates problem summaries from user actions
		ObjectSummaryIdentifier myIdentifier = new ObjectSummaryIdentifier();
		List<String> involvedUsers = AnalysisActions.getOriginatingUsernames(allActions);
		List<PerUserPerProblemSummary> summaries = myIdentifier.buildPerUserPerProblemSummaries(allActions, involvedUsers);
		
		//get just assessable ones
		List<PerUserPerProblemSummary> assessableSummaries = new ArrayList<PerUserPerProblemSummary>();
		for (PerUserPerProblemSummary summary : summaries){
			if (summary instanceof AssessablePerUserPerProblemSummary){
				assessableSummaries.add(summary);
			}
		}
		try {
			Book b =  new Book("Interacitve Python","war/conffiles/domainfiles/thinkcspy/"); 
			new CSVOutputter("war/realData/"+filename,assessableSummaries, b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void readAndWriteFiles(List<String> filenames, int startingId, int fileIdOffset){
		List<CfAction> allActions = new ArrayList<CfAction>();
		for (String filename: filenames){
			List<CfAction> actions = CfInteractionDataParser.getRunestoneActionListFromFile("war/realData/unfiltered/"+filename+".xml");
			
//			//print names so they can be checked against permission sheets, and permission file can be created
//			List<String> validNames = getAllValidNames(actions);
//			java.util.Collections.sort(validNames);
//			System.out.println(validNames);
			
			//this line is called for each person who has multiple usernames
			//171-20160SP-01-Toby
			combineActionsForMultipleNames(actions, Arrays.asList("Gabe", "Gshakou1"));
			//02-171-2014-FA-02-Toby
			combineActionsForMultipleNames(actions, Arrays.asList("destinroyer", "DestinRoyer"));
			
			try {
				//read valid name list from file
				String permittedNamesStr = GeneralUtil.readFileToString("war/realData/unfiltered/"+filename+"-permission.txt").trim();
				//get only actions associated with those names
				actions = BehaviorFilters.createUsersFilter(permittedNamesStr).getFilteredList(actions);
				
				List<String> permittedNames = Arrays.asList(permittedNamesStr.split(", "));
				
				//changes all action names, and returns the mapping
				Map<String, String> old2newIds = replaceAllIds(actions, permittedNames, startingId);
				startingId += permittedNames.size() + fileIdOffset;
				
				//print names to screen for record file
				for (Entry<String, String> entry : old2newIds.entrySet()) {
					System.out.println(entry);
				}

				filename = filename.substring(0,2);
				
				//write newly created data to file
				CfInteractionData dataOut = new CfInteractionData(actions);
				XmlFragment cfFrag = CfInteractionDataParser.toXml(dataOut);
				cfFrag.overwriteFile("war/realData/"+filename+"-anon"+".xml");
				
				allActions.addAll(actions);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		//also write to csv
		writeCSV(allActions, "AllData");
		//TODO: overwrite old valid ID file.
		
	}
	
	//use this to create permissions file and single line for combineActionsForMulitpleNames
	public static void printNamesForManualPermissionsFileCreation(String filename){
		List<CfAction> actions = CfInteractionDataParser.getRunestoneActionListFromFile("war/realData/unfiltered/"+filename+".xml");
		
		//print names so they can be checked against permission sheets, and permission file can be created
		List<String> validNames = getAllValidNames(actions);
		java.util.Collections.sort(validNames);
		System.out.println(validNames);
	}
	
	public static void main( String[] args){
		//for cleaning files, replace all '<invalid type>' with 'invalid type'
		//									<string> with string
		//									'/act> with ' </act>
		//					remove 'Indentation error' feedback
		//printNamesForManualPermissionsFileCreation("03-171-2014-FA-03-John");
		
		int startingId=0;
		try {
			startingId = Integer.valueOf(GeneralUtil.readFileToString("war/conffiles/xml/realDataNextValidId.txt").trim());
		} catch (NumberFormatException e) {
			logger.error("In calcNewIds: ID file not proper format", e);
		} catch (FileNotFoundException e) {
			logger.error("In calcNewIds: ID file not found", e);
		}
		final int FILE_ID_OFFSET = 100;
		readAndWriteFiles(Arrays.asList("05-171-2015-SP-02","06-171-2016-SP-01-Toby", "07-171-2016-SP-02-Sharon", "04-171-2015-SP-01-Nate", "01-171-2014-FA-01-Paul", "02-171-2014-FA-02-Toby"), startingId, FILE_ID_OFFSET);
		System.out.println("----------------Process Finished-------------");
	}

	
	

}

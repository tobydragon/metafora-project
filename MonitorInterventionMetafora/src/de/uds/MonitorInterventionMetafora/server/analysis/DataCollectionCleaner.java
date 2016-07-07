package de.uds.MonitorInterventionMetafora.server.analysis;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.BehaviorFilters;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfInteractionDataParser;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.analysis.AnalysisActions;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;

public class DataCollectionCleaner {
	static Logger logger = Logger.getLogger(DataCollectionCleaner.class);

	
	//mutator, replaces IDs, and returns the map of old to new IDs
	public static Map <String, String> replaceAllIds(List<CfAction> cfActions, List<String> oldIds){
		Collections.shuffle(oldIds);
		Map <String, String> old2newIds = calcNewIds(oldIds);
		replaceAllIds(cfActions, old2newIds);
		return old2newIds;
	}
		
	//mutator, changes original object
	private static void replaceAllIds(List<CfAction> cfActions, Map<String, String> old2newId){
		for (CfAction action : cfActions){
			action.replaceUserIds(old2newId, true);
		}
	}
	
	private static Map <String, String> calcNewIds(List<String> oldIds){
		
		int startingId=0;
		try {
			startingId = Integer.valueOf(GeneralUtil.readFileToString("war/conffiles/xml/realDataNextValidId.txt").trim());
		} catch (NumberFormatException e) {
			logger.error("In calcNewIds: ID file not proper format", e);
		} catch (FileNotFoundException e) {
			logger.error("In calcNewIds: ID file not found", e);
		}
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
		    if (name.startsWith("14")){
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
	
	
	public static void main(String[] args){
		XmlFragment runestoneFrag = XmlFragment.getFragmentFromLocalFile("war/realData/unfiltered/171-2015-SP-02.xml");
		CfInteractionData testCf = CfInteractionDataParser.fromRunestoneXml(runestoneFrag);
		List<CfAction> actions = testCf.getCfActions();
		
		//this line is called for each person who has multiple usernames
		combineActionsForMultipleNames(actions, Arrays.asList("name1", "name2"));

		//print names so they can be checked against permission sheets, and permission file can be created
		//System.out.println(getAllValidNames(actions));
		
		try {
			//read valid name list from file
			String permittedNamesStr = GeneralUtil.readFileToString("war/realData/unfiltered/171-2015-SP-02-permission.txt").trim();
			//get only actions associated with those names
			actions = BehaviorFilters.createUsersFilter(permittedNamesStr).getFilteredList(actions);
			
			List<String> permittedNames = Arrays.asList(permittedNamesStr.split(", "));
			
			//changes all action names, and returns the mapping
			Map<String, String> old2newIds = replaceAllIds(actions, permittedNames);
			
			//print to screen for saving a hard-copy of the mapping
			for(Entry<String, String> entry : old2newIds.entrySet()){
				System.out.println(entry);
			}
			
			CfInteractionData dataOut = new CfInteractionData(actions);
			XmlFragment cfFrag = CfInteractionDataParser.toXml(dataOut);
			//logger.info(cfFrag);
			cfFrag.overwriteFile("war/realData/DS1.xml");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//TODO: overwrite old valid ID file.
		
	}

	
	

}

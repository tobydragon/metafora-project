package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.ObjectSummaryIdentifier;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.PerUserPerProblemSummary;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfInteractionDataParser;
import de.uds.MonitorInterventionMetafora.server.json.JsonImportExport;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.analysis.AnalysisActions;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;

public class ConceptGraphTest {
	static Logger logger = Logger.getLogger(ConceptGraphTest.class);

	public static void main(String[] args) throws Exception {
		testBookGraphToJson();
	}
	
	public static void testBookGraphToJson(){
		
		// Needed to get behaviors (end result is we get the per user per problem summaries)
//		XmlFragment runestoneFrag = XmlFragment.getFragmentFromLocalFile("war/conffiles/xml/test/runestoneXml/DavidCaitlinExample.xml");
//		
//		// this line is simply making an empty testCf, and inturn we get no summaries
//		CfInteractionData testCf = CfInteractionDataParser.fromRunestoneXml(runestoneFrag);
		
		XmlFragment runestoneFrag = XmlFragment.getFragmentFromLocalFile("war/conffiles/xml/test/analysisChannelInput.xml");
		
		// this line is simply making an empty testCf, and inturn we get no summaries
		CfInteractionData testCf = CfInteractionDataParser.fromXml(runestoneFrag);
		
		
		logger.info(CfInteractionDataParser.toXml(testCf));
		List<CfAction> allActions = testCf.getCfActions();
		ObjectSummaryIdentifier myIdentifier = new ObjectSummaryIdentifier();
		List<String> involvedUsers = AnalysisActions.getOriginatingUsernames(allActions);
		List<PerUserPerProblemSummary> summaries = myIdentifier.getAllSummaries(allActions, involvedUsers, new ArrayList<CfProperty>());
		
		// filter down the number of summaries to one user
		List<String> users = new Vector<String>();
		users.add("student1");
		List<PerUserPerProblemSummary> filteredSummaries = myIdentifier.filterSummariesByUser(users, summaries);
		
		
		// test building Nodes and Edges lists from JSON "small json"
		String thisString = GeneralUtil.getRealPath("nodesAndEdgesBasicFull.json");
		NodeAndLinkLists fromJsonLists =  JsonImportExport.fromJson(thisString);		
		
		// Make the concept graph from the Json
		ConceptGraph graphFromJson = new ConceptGraph(fromJsonLists);
		
		// Add summary info to it
		myIdentifier.addSummariesToGraph(graphFromJson.getRoot(), filteredSummaries, new ArrayList<String>());

		// calculate "up" the graph the actual scores
		graphFromJson.calcActualComp();
		
		// calculate "down" the graph the predicted scores
		graphFromJson.calcPredictedScores();
		
		System.out.println(graphFromJson);
		
		NodeAndLinkLists toBeJsoned =  graphFromJson.buildNodesAndLinks();
		
		JsonImportExport.toJson("smallJsonWithSummaries", toBeJsoned);
	}
	
	public static void testGraphFromRunestoneData(){
		XmlFragment runestoneFrag = XmlFragment.getFragmentFromLocalFile("war/conffiles/xml/test/runestoneXml/DavidCaitlinExample.xml");
		CfInteractionData testCf = CfInteractionDataParser.fromRunestoneXml(runestoneFrag);
		logger.info(CfInteractionDataParser.toXml(testCf));
		List<CfAction> allActions = testCf.getCfActions();
		
		ObjectSummaryIdentifier myIdentifier = new ObjectSummaryIdentifier();
		
		//filter this list before getting usernames to limit who is analyzed
		List<String> involvedUsers = AnalysisActions.getOriginatingUsernames(allActions);
		myIdentifier.identifyBehaviors(allActions, involvedUsers, new ArrayList<CfProperty>());
	}

}
 

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

public class ConceptGraphMainTester {
	static Logger logger = Logger.getLogger(ConceptGraphMainTester.class);

	public static void main(String[] args) throws Exception {
		testBookGraphToJson();
	}
	
	public static void testBookGraphToJson(){
		//String inputXML = "war/conffiles/xml/test/analysisChannelInput.xml";
		//String inputStructure = "nodesAndEdgesBasicFull.json";
		
		String inputXML = "conffiles/xml/test/graphTests/simpleGraphTest/simpleGraphTestRunestone.xml";
		String inputStructure = "conffiles/xml/test/graphTests/simpleGraphTest/simpleGraph.json";
		
		//Get behaviors from runsetone xml
//		XmlFragment runestoneFrag = XmlFragment.getFragmentFromLocalFile("war/conffiles/xml/test/runestoneXml/DavidCaitlinExample.xml");
//		CfInteractionData testCf = CfInteractionDataParser.fromRunestoneXml(runestoneFrag);
		
		//Get behaviors from cf xml
		XmlFragment runestoneFrag = XmlFragment.getFragmentFromLocalFile(GeneralUtil.getRealPath(inputXML));
		CfInteractionData testCf = CfInteractionDataParser.fromRunestoneXml(runestoneFrag);
		
		
//		logger.debug(CfInteractionDataParser.toXml(testCf));
		List<CfAction> allActions = testCf.getCfActions();
		
		//Creates problem summaries from user actions
		ObjectSummaryIdentifier myIdentifier = new ObjectSummaryIdentifier();
		List<String> involvedUsers = AnalysisActions.getOriginatingUsernames(allActions);
		List<PerUserPerProblemSummary> summaries = myIdentifier.buildPerUserPerProblemSummaries(allActions, involvedUsers);
		// filter down the number of summaries to one user
//		List<String> users = new Vector<String>();
//		users.add("student24");
//		summaries = myIdentifier.filterSummariesByUser(users, summaries);
//		logger.debug(summaries);
		for (PerUserPerProblemSummary summary : summaries){
			if (summary.toString() != null && summary.toString().contains("5_1_1")){
				logger.debug(summary);
			}
		}
		// Make the concept graph from Json
		//TODO: files should be read from within war file...
		String thisString = GeneralUtil.getRealPath(inputStructure);
		NodesAndIDLinks fromJsonLists =  JsonImportExport.fromJson(thisString);		
		ConceptGraph graphFromJson = new ConceptGraph(fromJsonLists);
		
		//List<ConceptNode> summaryNodeList = new ArrayList<ConceptNode>();
		// Add summary info to it
		List<ConceptNode> graphSummaryNodeList = new ArrayList<ConceptNode>();
		for(PerUserPerProblemSummary summary : summaries){
			//System.out.println(summary.getObjectId());
			ConceptNode sumNode = new ConceptNode(summary);
			graphSummaryNodeList.add(sumNode);
		}
		
		//TODO: TD - Does this code belong in ObjectSummaryIdentifier? Doesn't seem like its job...
		//Also, should probably identify whether connection was made or not
		graphFromJson.addSummariesToGraph(summaries);

		// calculate "up" the graph the actual scores
		graphFromJson.calcActualComp();
		
		// calculate "down" the graph the predicted scores
		graphFromJson.calcPredictedScores();
		
		System.out.println(graphFromJson);
		
		NodesAndIDLinks toBeJsoned =  graphFromJson.buildNodesAndLinks();
		
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
 

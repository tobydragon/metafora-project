package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.ObjectSummaryIdentifier;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Book;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfInteractionDataParser;
import de.uds.MonitorInterventionMetafora.server.json.JsonImportExport;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.analysis.AnalysisActions;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;

public class ConceptGraphTest {
	static Logger logger = Logger.getLogger(ConceptGraphTest.class);

	public static void main(String[] args) throws Exception {
		testGraphFromRunestoneData();
	}
	
	public static void testBookGraphToJson(){
		// test building the graphs and nodes from book and send to JSON
		String bookPath = "/Users/David/git/metafora-project/MonitorInterventionMetafora/war/conffiles/domainfiles/thinkcspy/";
		Book b = new Book("Interacitve Python", bookPath);
		//create a ConceptGraph of the book and then call createConceptGraph in order to add the summaries to the graph
		ConceptGraph graph = new ConceptGraph(b);

		// the actual printing to JSON
		NodeAndLinkLists lists =  graph.buildNodesAndLinks();
		JsonImportExport.toJson("testing", lists);
		
		
		// test building Nodes and Edges lists from JSON "small json"
		NodeAndLinkLists fromJsonLists =  JsonImportExport.fromJson("/Users/David/Documents/2015/SeniorProject/nodesAndEdgesBasicFull.json");		
		
		// Need to test making concept graph from JSON
		ConceptGraph graphFromJson = new ConceptGraph(fromJsonLists);
		System.out.println(graphFromJson);
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
 

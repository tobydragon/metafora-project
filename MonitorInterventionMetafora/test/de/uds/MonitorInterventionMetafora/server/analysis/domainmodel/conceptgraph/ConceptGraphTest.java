package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

	ConceptGraph graphFromJson;
	List<PerUserPerProblemSummary> summaries;
	
	@Before
	public void setUp() throws Exception {
		
		String inputXML = "conffiles/xml/test/graphTests/simpleGraphTest/simpleGraphTestRunestone.xml";
		String inputStructure = "conffiles/xml/test/graphTests/simpleGraphTest/simpleGraph.json";
		
		XmlFragment runestoneFrag = XmlFragment.getFragmentFromLocalFile(GeneralUtil.getRealPath(inputXML));
		CfInteractionData testCf = CfInteractionDataParser.fromRunestoneXml(runestoneFrag);
		
//		logger.debug(CfInteractionDataParser.toXml(testCf));
		List<CfAction> allActions = testCf.getCfActions();
		
		//Creates problem summaries from user actions
		ObjectSummaryIdentifier myIdentifier = new ObjectSummaryIdentifier();
		List<String> involvedUsers = AnalysisActions.getOriginatingUsernames(allActions);
		this.summaries = myIdentifier.getAllSummaries(allActions, involvedUsers, new ArrayList<CfProperty>());
		
		// Make the concept graph from Json
		String thisString = GeneralUtil.getRealPath(inputStructure);
		NodeAndLinkLists fromJsonLists =  JsonImportExport.fromJson(thisString);		
		this.graphFromJson = new ConceptGraph(fromJsonLists);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void addSummariesTest() {		
		graphFromJson.addSummariesToGraph(summaries);
		
		graphFromJson.calcActualComp();
		
		graphFromJson.calcPredictedScores();
		
		System.out.println(graphFromJson);
	}

}

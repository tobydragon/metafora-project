package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
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
	ConceptGraph simpleGraph;
	ConceptGraph simpleTree;
	ConceptGraph madeTree;
	List<PerUserPerProblemSummary> summaries;
	
	@Before
	public void setUp() throws Exception {
		makeSummaries();
		makeGraph();
		
		
		
	}

	@After
	public void tearDown() throws Exception {
		//this.graphFromJson = null;
		//this.summaries = null;
	}
	
	@Test
	public void makingConceptGraphTest(){
		//System.out.println(this.graphFromJson);
	}
	
	@Test
	public void addSummariesTest() {		

	}
	
	@Test
	public void graphToTreeSimpleTest(){
		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<ConceptLink> clList = new ArrayList<ConceptLink>();
		
		Concept c = new ConceptImpl("A");
		cnList.add(new ConceptNode(c, c.getConceptTitle()));
		c = new ConceptImpl("B");
		cnList.add(new ConceptNode(c, c.getConceptTitle()));
		c = new ConceptImpl("C");
		cnList.add(new ConceptNode(c, c.getConceptTitle()));
		c = new ConceptImpl("D");
		cnList.add(new ConceptNode(c, c.getConceptTitle()));
		
		clList.add(new ConceptLink(cnList.get(0),cnList.get(1))); //A -> B
		clList.add(new ConceptLink(cnList.get(0),cnList.get(2))); //A -> C
		clList.add(new ConceptLink(cnList.get(1),cnList.get(2))); //B -> C
//		clList.add(new ConceptLink(cnList.get(1),cnList.get(3))); //B -> D
//		clList.add(new ConceptLink(cnList.get(2),cnList.get(3))); //C -> D
//		clList.add(new ConceptLink(cnList.get(0),cnList.get(3))); //A -> D
		
		
		NodeAndLinkLists bothLists = new NodeAndLinkLists(cnList,clList);
		
		this.simpleTree = new ConceptGraph(bothLists);

		this.madeTree = this.simpleTree.graphToTree();
		System.out.println("Made Tree__________________");
		System.out.println(this.madeTree.getNodes());
		System.out.println(this.madeTree.getLinks());
	}
	
	@Test
	public void conceptNodeIsNotEqualButConceptIsTest(){
		
	}
	
	@Test
	public void numUniqueConceptNodevsNumUniqueConceptsTest(){
		
	}
	public void makeSummaries(){
		String inputXML = "conffiles/xml/test/graphTests/simpleGraphTest/simpleGraphTestRunestone.xml";
		
		XmlFragment runestoneFrag = XmlFragment.getFragmentFromLocalFile(GeneralUtil.getRealPath(inputXML));
		CfInteractionData testCf = CfInteractionDataParser.fromRunestoneXml(runestoneFrag);
		
//		logger.debug(CfInteractionDataParser.toXml(testCf));
		List<CfAction> allActions = testCf.getCfActions();
		//Creates problem summaries from user actions
		ObjectSummaryIdentifier myIdentifier = new ObjectSummaryIdentifier();
		List<String> involvedUsers = AnalysisActions.getOriginatingUsernames(allActions);
		this.summaries = myIdentifier.getAllSummaries(allActions, involvedUsers, new ArrayList<CfProperty>());
	}
	
	public void makeGraph(){
		String inputStructure = "conffiles/xml/test/graphTests/simpleGraphTest/simpleGraph.json";
		// Make the concept graph from Json
		String thisString = GeneralUtil.getRealPath(inputStructure);
		NodeAndLinkLists fromJsonLists =  JsonImportExport.fromJson(thisString);
		this.graphFromJson = new ConceptGraph(fromJsonLists);
	}

}

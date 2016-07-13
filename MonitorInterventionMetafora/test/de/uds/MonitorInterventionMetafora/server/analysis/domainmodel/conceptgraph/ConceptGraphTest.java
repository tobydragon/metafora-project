package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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

	static Logger logger = Logger.getLogger(ConceptGraphTest.class);
	ConceptGraph graphFromJson;
	ConceptGraph simpleGraph;
	ConceptGraph mediumGraph;
	ConceptGraph complexGraph;
	ConceptGraph simpleTree;
	ConceptGraph mediumTree;
	ConceptGraph complexTree;
	
	List<PerUserPerProblemSummary> summaries;
	
	public void makeSimple(){
		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<ConceptLink> clList = new ArrayList<ConceptLink>();
		
		
		//Make simple tree
		Concept c = new ConceptImpl("A");
		cnList.add(new ConceptNode(c, c.getConceptTitle()));
		c = new ConceptImpl("B");
		cnList.add(new ConceptNode(c, c.getConceptTitle()));
		c = new ConceptImpl("C");
		cnList.add(new ConceptNode(c, c.getConceptTitle()));
		
		
		clList.add(new ConceptLink(cnList.get(0),cnList.get(1))); //A -> B
		clList.add(new ConceptLink(cnList.get(0),cnList.get(2))); //A -> C
		clList.add(new ConceptLink(cnList.get(1),cnList.get(2))); //B -> C
		
		NodeAndLinkLists bothLists = new NodeAndLinkLists(cnList,clList);
		
		this.simpleGraph = new ConceptGraph(bothLists);
		this.simpleTree = simpleGraph.graphToTree();
		
		logger.debug(simpleGraph);
		logger.debug(simpleTree);
	}
	
	public void makeMedium(){
		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<ConceptLink> clList = new ArrayList<ConceptLink>();
		
		
		//Make simple tree
		Concept c = new ConceptImpl("A");
		cnList.add(new ConceptNode(c, c.getConceptTitle()));
		c = new ConceptImpl("B");
		cnList.add(new ConceptNode(c, c.getConceptTitle()));
		c = new ConceptImpl("C");
		cnList.add(new ConceptNode(c, c.getConceptTitle()));
		
		
		clList.add(new ConceptLink(cnList.get(0),cnList.get(1))); //A -> B
		clList.add(new ConceptLink(cnList.get(0),cnList.get(2))); //A -> C
		clList.add(new ConceptLink(cnList.get(1),cnList.get(2))); //B -> C
		
		//make medium graph
		c = new ConceptImpl("D");
		cnList.add(new ConceptNode(c, c.getConceptTitle()));
		clList.add(new ConceptLink(cnList.get(1),cnList.get(3))); //B -> D
		clList.add(new ConceptLink(cnList.get(2),cnList.get(3))); //C -> D
		
		NodeAndLinkLists bothLists = new NodeAndLinkLists(cnList, clList);
		
		this.mediumGraph = new ConceptGraph(bothLists);
		
		this.mediumTree = this.mediumGraph.graphToTree();
		
		logger.debug(mediumGraph);
		logger.debug(mediumTree);
	}
	
	public void makeComplex(){
		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<ConceptLink> clList = new ArrayList<ConceptLink>();
		
		
		//Make simple tree
		Concept c = new ConceptImpl("A");
		cnList.add(new ConceptNode(c, c.getConceptTitle()));
		c = new ConceptImpl("B");
		cnList.add(new ConceptNode(c, c.getConceptTitle()));
		c = new ConceptImpl("C");
		cnList.add(new ConceptNode(c, c.getConceptTitle()));
		
		
		clList.add(new ConceptLink(cnList.get(0),cnList.get(1))); //A -> B
		clList.add(new ConceptLink(cnList.get(0),cnList.get(2))); //A -> C
		clList.add(new ConceptLink(cnList.get(1),cnList.get(2))); //B -> C
		
		//make medium graph
		c = new ConceptImpl("D");
		cnList.add(new ConceptNode(c, c.getConceptTitle()));
		clList.add(new ConceptLink(cnList.get(1),cnList.get(3))); //B -> D
		clList.add(new ConceptLink(cnList.get(2),cnList.get(3))); //C -> D
		
		c = new ConceptImpl("E");
		cnList.add(new ConceptNode(c, c.getConceptTitle()));
		clList.add(new ConceptLink(cnList.get(0), cnList.get(4))); //A -> E
		clList.add(new ConceptLink(cnList.get(2), cnList.get(4))); //C -> E
		clList.add(new ConceptLink(cnList.get(3), cnList.get(4))); //D -> E

		NodeAndLinkLists bothLists = new NodeAndLinkLists(cnList, clList);
		
		this.complexGraph = new ConceptGraph(bothLists);
		
		this.complexTree = complexGraph.graphToTree();
		
		logger.debug(complexGraph);
		logger.debug(complexTree);
	}
	
	@Before
	public void setUp() throws Exception {
		makeSimple();
		makeMedium();
		makeComplex();
		//makeSummaries();
		//makeGraph();
		
		//logger.debug("SimpleGraph in setup: " + this.simpleGraph.nodes);
		
	}

	@After
	public void tearDown() throws Exception {
		this.simpleGraph = null;
		this.simpleTree = null;
		this.mediumGraph = null;
		this.mediumTree = null;
		this.complexGraph = null;
		this.complexTree = null;
	}
	
	@Test
	public void simpleGraphCheckNodesNumbersTest(){
		int numNodesInput = simpleGraph.nodes.size();
		int numNodesOutput = simpleTree.nodes.size();
		
		Assert.assertEquals(3,numNodesInput);
		Assert.assertEquals(4, numNodesOutput);
	}
	
	@Test
	public void simpleGraphCheckLinksNumbersTest(){
		int numLinksInput = simpleGraph.links.size();
		int numLinksOutput = simpleTree.links.size();
		
		Assert.assertEquals(3, numLinksInput);
		Assert.assertEquals(3, numLinksOutput);
	}
	
	@Test
	public void mediumGraphCheckNodesNumbersTest(){
		int numNodesInput = mediumGraph.nodes.size();
		int numNodesOutput = mediumTree.nodes.size();
		
		Assert.assertEquals(4,numNodesInput);
		Assert.assertEquals(7, numNodesOutput);
	}
	
	@Test
	public void mediumGraphCheckLinksNumbersTest(){
		int numLinksInput = mediumGraph.links.size();
		int numLinksOutput = mediumTree.links.size();
		
		Assert.assertEquals(5, numLinksInput);
		Assert.assertEquals(6, numLinksOutput);
	}
	
	@Test
	public void complexGraphCheckNodesNumbersTest(){
		int numNodesInput = complexGraph.nodes.size();
		int numNodesOutput = complexTree.nodes.size();
		
		Assert.assertEquals(5,numNodesInput);
		Assert.assertEquals(13, numNodesOutput);
	}
	
	@Test
	public void complexGraphCheckLinksNumbersTest(){
		int numLinksInput = complexGraph.links.size();
		int numLinksOutput = complexTree.links.size();
		
		Assert.assertEquals(8, numLinksInput);
		Assert.assertEquals(12, numLinksOutput);
	}
	
	@Test
	public void simpleCheckNumEachIDTest(){
		int numA = 0;
		int numB = 0;
		int numC = 0;
		for( ConceptNode currNode : simpleTree.nodes){
			if(currNode.getConcept().getConceptTitle().equals("A")){
				numA++;
			}else if(currNode.getConcept().getConceptTitle().equals("B")){
				numB++;
			}else if(currNode.getConcept().getConceptTitle().equals("C")){
				numC++;
			}
		}
		
		Assert.assertEquals(1, numA);
		Assert.assertEquals(1, numB);
		Assert.assertEquals(2, numC);
	}
	
	@Test
	public void mediumCheckNumEachIDTest(){
		int numA = 0;
		int numB = 0;
		int numC = 0;
		int numD = 0;
		for( ConceptNode currNode : mediumTree.nodes){
			if(currNode.getConcept().getConceptTitle().equals("A")){
				numA++;
			}else if(currNode.getConcept().getConceptTitle().equals("B")){
				numB++;
			}else if(currNode.getConcept().getConceptTitle().equals("C")){
				numC++;
			}else if(currNode.getConcept().getConceptTitle().equals("D")){
				numD++;
			}
		}
		
		Assert.assertEquals(1, numA);
		Assert.assertEquals(1, numB);
		Assert.assertEquals(2, numC);
		Assert.assertEquals(3, numD);
	}
	
	@Test
	public void complexCheckNumEachIDTest(){
		int numA = 0;
		int numB = 0;
		int numC = 0;
		int numD = 0;
		int numE = 0;
		for( ConceptNode currNode : complexTree.nodes){
			if(currNode.getConcept().getConceptTitle().equals("A")){
				numA++;
			}else if(currNode.getConcept().getConceptTitle().equals("B")){
				numB++;
			}else if(currNode.getConcept().getConceptTitle().equals("C")){
				numC++;
			}else if(currNode.getConcept().getConceptTitle().equals("D")){
				numD++;
			}else if(currNode.getConcept().getConceptTitle().equals("E")){
				numE++;
			}
		}
		
		Assert.assertEquals(1, numA);
		Assert.assertEquals(1, numB);
		Assert.assertEquals(2, numC);
		Assert.assertEquals(3, numD);
		Assert.assertEquals(6, numE);
	}
	
	@Test
	public void addSummariesTest() {		

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

package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
	
	@Before
	public void setUp() throws Exception {
		makeSimple();
		makeMedium();
		makeComplex();
		//makeSummaries();
		//makeGraph();	
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
	
	
	public void makeSimple(){
		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<IDLink> clList = new ArrayList<IDLink>();
		
		
		//Make simple tree
		Concept c = new ConceptImpl("A");
		ConceptNode cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("B");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("C");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		
		
		
		clList.add(new IDLink("A","B")); //A -> B
		clList.add(new IDLink("A","C")); //A -> C
		clList.add(new IDLink("B","C")); //B -> C
		
		NodesAndIDLinks inputNodesAndLinks = new NodesAndIDLinks(cnList,clList);
		this.simpleGraph = new ConceptGraph(inputNodesAndLinks);
		this.simpleTree = simpleGraph.graphToTreeNewLinks();
	}
	
	public void makeMedium(){
		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<IDLink> clList = new ArrayList<IDLink>();
		
		
		//Make simple tree
		Concept c = new ConceptImpl("A");
		ConceptNode cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("B");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("C");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("D");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		
		
		clList.add(new IDLink("A","B")); //A -> B
		clList.add(new IDLink("A","C")); //A -> C
		clList.add(new IDLink("B","C")); //B -> C
		clList.add(new IDLink("B","D")); //B -> D
		clList.add(new IDLink("C","D")); //C -> D
		
		NodesAndIDLinks inputNodesAndLinks = new NodesAndIDLinks(cnList,clList);
		this.mediumGraph = new ConceptGraph(inputNodesAndLinks);
		this.mediumTree = mediumGraph.graphToTreeNewLinks();
	}
	public void makeComplex(){
		
		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<IDLink> clList = new ArrayList<IDLink>();
		
		
		//Make simple tree
		Concept c = new ConceptImpl("A");
		ConceptNode cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("B");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("C");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("D");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("E");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);

		clList.add(new IDLink("A","B")); //A -> B
		clList.add(new IDLink("A","C")); //A -> C
		clList.add(new IDLink("B","C")); //B -> C
		clList.add(new IDLink("B","D")); //B -> D
		clList.add(new IDLink("C","D")); //C -> D
		clList.add(new IDLink("A","E")); //A -> E
		clList.add(new IDLink("C","E")); //C -> E
		clList.add(new IDLink("D","E")); //D -> E
		
		NodesAndIDLinks inputNodesAndLinks = new NodesAndIDLinks(cnList,clList);
		this.complexGraph = new ConceptGraph(inputNodesAndLinks);
		this.complexTree = complexGraph.graphToTreeNewLinks();
		
		logger.debug(complexTree);
	}
	
	@Test
	public void simpleGraphCheckNodesNumbersTest(){

		NodesAndIDLinks graphLists = this.simpleGraph.buildNodesAndLinks();
		NodesAndIDLinks treeLists = this.simpleTree.buildNodesAndLinks();
		
		Assert.assertEquals(3, graphLists.getNodes().size());
		//Assert.assertEquals(4, treeLists.getNodes().size());
	}
	
	@Test
	public void simpleGraphCheckLinksNumbersTest(){
		NodesAndIDLinks graphLists = this.simpleGraph.buildNodesAndLinks();
		NodesAndIDLinks treeLists = this.simpleTree.buildNodesAndLinks();
		
		Assert.assertEquals(3, graphLists.getLinks().size());
		//Assert.assertEquals(3, treeLists.getLinks().size());
	}
	
	@Test
	public void mediumGraphCheckNodesNumbersTest(){
		NodesAndIDLinks graphLists = this.mediumGraph.buildNodesAndLinks();
		NodesAndIDLinks treeLists = this.mediumTree.buildNodesAndLinks();
		logger.debug(graphLists.getNodes());
		logger.debug(treeLists.getNodes());
		
		Assert.assertEquals(4, graphLists.getNodes().size());
		//Assert.assertEquals(7, treeLists.getNodes().size());
	}
	
	@Test
	public void mediumGraphCheckLinksNumbersTest(){
		NodesAndIDLinks graphLists = this.mediumGraph.buildNodesAndLinks();
		NodesAndIDLinks treeLists = this.mediumTree.buildNodesAndLinks();
		
		logger.debug(graphLists.getLinks());
		logger.debug(treeLists.getLinks());
		Assert.assertEquals(5, graphLists.getLinks().size());
		//Assert.assertEquals(6, treeLists.getLinks().size());
	}
	
	@Test
	public void complexGraphCheckNodesNumbersTest(){
		NodesAndIDLinks graphLists = this.complexGraph.buildNodesAndLinks();
		NodesAndIDLinks treeLists = this.complexTree.buildNodesAndLinks();

		Assert.assertEquals(5, graphLists.getNodes().size());
		//Assert.assertEquals(13, treeLists.getNodes().size());
	}
	
	@Test
	public void complexGraphCheckLinksNumbersTest(){
		NodesAndIDLinks graphLists = this.complexGraph.buildNodesAndLinks();
		NodesAndIDLinks treeLists = this.complexTree.buildNodesAndLinks();
		
		Assert.assertEquals(8, graphLists.getLinks().size());
		//Assert.assertEquals(12, treeLists.getLinks().size());
	}
	
	@Test
	public void simpleCheckNumEachIDTest(){
		//checks if the tree has the right number of copies per orig node
		int numA = 0;
		int numB = 0;
		int numC = 0;
		
		NodesAndIDLinks treeLists = this.simpleTree.buildNodesAndLinks();
		
		for(ConceptNode node : treeLists.getNodes()){
			if(node.getConcept().getConceptTitle().equals("A")){
				numA++;
			}else if(node.getConcept().getConceptTitle().equals("B")){
				numB++;
			}else if(node.getConcept().getConceptTitle().equals("C")){
				numC++;
			}
		}
		
		Assert.assertEquals(1, numA);
		Assert.assertEquals(1, numB);
		Assert.assertEquals(2, numC);
	}
//	
//	@Test
//	public void mediumCheckNumEachIDTest(){
//		int numA = 0;
//		int numB = 0;
//		int numC = 0;
//		int numD = 0;
//		
//		Iterator it = mediumTree.nodesMap.entrySet().iterator();
//	    while (it.hasNext()) {
//	        Map.Entry pair = (Map.Entry)it.next();
//	        if(pair.getKey().equals("A")){
//				numA += ((List<ConceptNode>) pair.getValue()).size();
//			}else if(pair.getKey().equals("B")){
//				numB += ((List<ConceptNode>) pair.getValue()).size();
//			}else if(pair.getKey().equals("C")){
//				numC += ((List<ConceptNode>) pair.getValue()).size();
//			}else if(pair.getKey().equals("D")){
//				numD += ((List<ConceptNode>) pair.getValue()).size();
//			}
//	        it.remove(); // avoids a ConcurrentModificationException
//	    }
//		
//		Assert.assertEquals(1, numA);
//		Assert.assertEquals(1, numB);
//		Assert.assertEquals(2, numC);
//		Assert.assertEquals(3, numD);
//	}
//	
//	@Test
//	public void complexCheckNumEachIDTest(){
//		int numA = 0;
//		int numB = 0;
//		int numC = 0;
//		int numD = 0;
//		int numE = 0;
//		
//		Iterator it = complexTree.nodesMap.entrySet().iterator();
//	    while (it.hasNext()) {
//	        Map.Entry pair = (Map.Entry)it.next();
//	        if(pair.getKey().equals("A")){
//				numA += ((List<ConceptNode>) pair.getValue()).size();
//			}else if(pair.getKey().equals("B")){
//				numB += ((List<ConceptNode>) pair.getValue()).size();
//			}else if(pair.getKey().equals("C")){
//				numC += ((List<ConceptNode>) pair.getValue()).size();
//			}else if(pair.getKey().equals("D")){
//				numD += ((List<ConceptNode>) pair.getValue()).size();
//			}else if(pair.getKey().equals("E")){
//				numE += ((List<ConceptNode>) pair.getValue()).size();
//			}
//	        it.remove(); // avoids a ConcurrentModificationException
//	    }
//	    
//		Assert.assertEquals(1, numA);
//		Assert.assertEquals(1, numB);
//		Assert.assertEquals(2, numC);
//		Assert.assertEquals(3, numD);
//		Assert.assertEquals(6, numE);
//	}
	
	@Test
	public void addSummariesTest() {		
//		makeSummaries();
//		makeGraph();
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
//		String inputStructure = "conffiles/xml/test/graphTests/simpleGraphTest/simpleGraph.json";
//		// Make the concept graph from Json
//		String thisString = GeneralUtil.getRealPath(inputStructure);
//		NodeAndLinkLists fromJsonLists =  JsonImportExport.fromJson(thisString);
//		this.graphFromJson = new ConceptGraph(fromJsonLists);
	}
	

}

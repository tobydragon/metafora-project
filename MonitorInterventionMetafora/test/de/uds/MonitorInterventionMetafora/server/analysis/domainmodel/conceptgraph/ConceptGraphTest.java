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
		HashMap<String, List<ConceptNode>> cnList = new HashMap<String, List<ConceptNode>>();
		List<IDLink> clList = new ArrayList<IDLink>();
		
		
		//Make simple tree
		List<ConceptNode> tempValue= new ArrayList<ConceptNode>();
		Concept c = new ConceptImpl("A");
		tempValue.add(new ConceptNode(c, c.getConceptTitle()));
		cnList.put(c.getConceptTitle(), tempValue);
		tempValue= new ArrayList<ConceptNode>();
		c = new ConceptImpl("B");
		tempValue.add(new ConceptNode(c, c.getConceptTitle()));
		cnList.put(c.getConceptTitle(), tempValue);
		tempValue= new ArrayList<ConceptNode>();
		c = new ConceptImpl("C");
		tempValue.add(new ConceptNode(c, c.getConceptTitle()));
		cnList.put(c.getConceptTitle(), tempValue);
		
		
		clList.add(new IDLink("A","B")); //A -> B
		clList.add(new IDLink("A","C")); //A -> C
		clList.add(new IDLink("B","C")); //B -> C
		
		this.simpleGraph = new ConceptGraph(cnList,clList);
		this.simpleTree = simpleGraph.graphToTreeNewLinks();
	}
	
	public void makeMedium(){
		HashMap<String, List<ConceptNode>> cnList = new HashMap<String, List<ConceptNode>>();
		List<IDLink> clList = new ArrayList<IDLink>();
		
		
		//Make simple tree
		List<ConceptNode> tempValue= new ArrayList<ConceptNode>();
		Concept c = new ConceptImpl("A");
		tempValue.add(new ConceptNode(c, c.getConceptTitle()));
		cnList.put(c.getConceptTitle(), tempValue);
		tempValue = null;
		tempValue= new ArrayList<ConceptNode>();
		c = new ConceptImpl("B");
		tempValue.add(new ConceptNode(c, c.getConceptTitle()));
		cnList.put(c.getConceptTitle(), tempValue);
		tempValue = null;
		tempValue= new ArrayList<ConceptNode>();
		c = new ConceptImpl("C");
		tempValue.add(new ConceptNode(c, c.getConceptTitle()));
		cnList.put(c.getConceptTitle(), tempValue);
		tempValue= new ArrayList<ConceptNode>();
		c = new ConceptImpl("D");
		tempValue.add(new ConceptNode(c, c.getConceptTitle()));
		cnList.put(c.getConceptTitle(), tempValue);
		
		
		clList.add(new IDLink("A","B")); //A -> B
		clList.add(new IDLink("A","C")); //A -> C
		clList.add(new IDLink("B","C")); //B -> C
		clList.add(new IDLink("B","D")); //B -> D
		clList.add(new IDLink("C","D")); //C -> D
		
		this.mediumGraph = new ConceptGraph(cnList,clList);
		this.mediumTree = mediumGraph.graphToTreeNewLinks();
		
	}
	public void makeComplex(){
		HashMap<String, List<ConceptNode>> cnList = new HashMap<String, List<ConceptNode>>();
		List<IDLink> clList = new ArrayList<IDLink>();
		
		
		//Make simple tree
		List<ConceptNode> tempValue= new ArrayList<ConceptNode>();
		Concept c = new ConceptImpl("A");
		tempValue.add(new ConceptNode(c, c.getConceptTitle()));
		cnList.put(c.getConceptTitle(), tempValue);
		tempValue = null;
		tempValue= new ArrayList<ConceptNode>();
		c = new ConceptImpl("B");
		tempValue.add(new ConceptNode(c, c.getConceptTitle()));
		cnList.put(c.getConceptTitle(), tempValue);
		tempValue = null;
		tempValue= new ArrayList<ConceptNode>();
		c = new ConceptImpl("C");
		tempValue.add(new ConceptNode(c, c.getConceptTitle()));
		cnList.put(c.getConceptTitle(), tempValue);
		tempValue= new ArrayList<ConceptNode>();
		c = new ConceptImpl("D");
		tempValue.add(new ConceptNode(c, c.getConceptTitle()));
		cnList.put(c.getConceptTitle(), tempValue);
		tempValue= new ArrayList<ConceptNode>();
		c = new ConceptImpl("E");
		tempValue.add(new ConceptNode(c, c.getConceptTitle()));
		cnList.put(c.getConceptTitle(), tempValue);
		
		
		clList.add(new IDLink("A","B")); //A -> B
		clList.add(new IDLink("A","C")); //A -> C
		clList.add(new IDLink("B","C")); //B -> C
		clList.add(new IDLink("B","D")); //B -> D
		clList.add(new IDLink("C","D")); //C -> D
		clList.add(new IDLink("A","E")); //A -> E
		clList.add(new IDLink("C","E")); //C -> E
		clList.add(new IDLink("D","E")); //D -> E
		
		this.complexGraph = new ConceptGraph(cnList,clList);
		this.complexTree = complexGraph.graphToTreeNewLinks();
		
		logger.debug(complexTree);
	}
	
	@Test
	public void simpleGraphCheckNodesNumbersTest(){
		int numNodesInput = 0;
		int numNodesOutput = 0;
		
		Iterator it = simpleGraph.nodesMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        logger.debug(pair.getValue());
	        numNodesInput += ((List<ConceptNode>) pair.getValue()).size();
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    
	    it = simpleTree.nodesMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        numNodesOutput += ((List<ConceptNode>) pair.getValue()).size();
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
		Assert.assertEquals(3,numNodesInput);
		Assert.assertEquals(4, numNodesOutput);
	}
	
	@Test
	public void simpleGraphCheckLinksNumbersTest(){
		int numLinksInput = simpleGraph.idLinks.size();
		int numLinksOutput = simpleTree.idLinks.size();
		
		Assert.assertEquals(3, numLinksInput);
		Assert.assertEquals(3, numLinksOutput);
	}
	
	@Test
	public void mediumGraphCheckNodesNumbersTest(){
		int numNodesInput = 0;
		int numNodesOutput = 0;
		
		Iterator it = mediumGraph.nodesMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        logger.debug(pair.getValue());
	        numNodesInput += ((List<ConceptNode>) pair.getValue()).size();
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    
	    it = mediumTree.nodesMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        numNodesOutput += ((List<ConceptNode>) pair.getValue()).size();
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
		
		Assert.assertEquals(4,numNodesInput);
		Assert.assertEquals(7, numNodesOutput);
	}
	
	@Test
	public void mediumGraphCheckLinksNumbersTest(){
		int numLinksInput = mediumGraph.idLinks.size();
		int numLinksOutput = mediumTree.idLinks.size();
		
		Assert.assertEquals(5, numLinksInput);
		Assert.assertEquals(6, numLinksOutput);
	}
	
	@Test
	public void complexGraphCheckNodesNumbersTest(){
		int numNodesInput = 0;
		int numNodesOutput = 0;
		
		Iterator it = complexGraph.nodesMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        logger.debug(pair.getValue());
	        numNodesInput += ((List<ConceptNode>) pair.getValue()).size();
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    
	    it = complexTree.nodesMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        numNodesOutput += ((List<ConceptNode>) pair.getValue()).size();
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
		Assert.assertEquals(5,numNodesInput);
		Assert.assertEquals(13, numNodesOutput);
	}
	
	@Test
	public void complexGraphCheckLinksNumbersTest(){
		int numLinksInput = complexGraph.idLinks.size();
		int numLinksOutput = complexTree.idLinks.size();
		
		Assert.assertEquals(8, numLinksInput);
		Assert.assertEquals(12, numLinksOutput);
	}
	
	@Test
	public void simpleCheckNumEachIDTest(){
		int numA = 0;
		int numB = 0;
		int numC = 0;
		
		Iterator it = simpleTree.nodesMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        if(pair.getKey().equals("A")){
				numA += ((List<ConceptNode>) pair.getValue()).size();
			}else if(pair.getKey().equals("B")){
				numB += ((List<ConceptNode>) pair.getValue()).size();
			}else if(pair.getKey().equals("C")){
				numC += ((List<ConceptNode>) pair.getValue()).size();
			}
	        it.remove(); // avoids a ConcurrentModificationException
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
		
		Iterator it = mediumTree.nodesMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        if(pair.getKey().equals("A")){
				numA += ((List<ConceptNode>) pair.getValue()).size();
			}else if(pair.getKey().equals("B")){
				numB += ((List<ConceptNode>) pair.getValue()).size();
			}else if(pair.getKey().equals("C")){
				numC += ((List<ConceptNode>) pair.getValue()).size();
			}else if(pair.getKey().equals("D")){
				numD += ((List<ConceptNode>) pair.getValue()).size();
			}
	        it.remove(); // avoids a ConcurrentModificationException
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
		
		Iterator it = complexTree.nodesMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        if(pair.getKey().equals("A")){
				numA += ((List<ConceptNode>) pair.getValue()).size();
			}else if(pair.getKey().equals("B")){
				numB += ((List<ConceptNode>) pair.getValue()).size();
			}else if(pair.getKey().equals("C")){
				numC += ((List<ConceptNode>) pair.getValue()).size();
			}else if(pair.getKey().equals("D")){
				numD += ((List<ConceptNode>) pair.getValue()).size();
			}else if(pair.getKey().equals("E")){
				numE += ((List<ConceptNode>) pair.getValue()).size();
			}
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    
		Assert.assertEquals(1, numA);
		Assert.assertEquals(1, numB);
		Assert.assertEquals(2, numC);
		Assert.assertEquals(3, numD);
		Assert.assertEquals(6, numE);
	}
	
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
	
//	public void makeSimpleOld(){
//		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
//		List<ConceptLink> clList = new ArrayList<ConceptLink>();
//		
//		
//		//Make simple tree
//		Concept c = new ConceptImpl("A");
//		cnList.add(new ConceptNode(c, c.getConceptTitle()));
//		c = new ConceptImpl("B");
//		cnList.add(new ConceptNode(c, c.getConceptTitle()));
//		c = new ConceptImpl("C");
//		cnList.add(new ConceptNode(c, c.getConceptTitle()));
//		
//		
//		clList.add(new ConceptLink(cnList.get(0),cnList.get(1))); //A -> B
//		clList.add(new ConceptLink(cnList.get(0),cnList.get(2))); //A -> C
//		clList.add(new ConceptLink(cnList.get(1),cnList.get(2))); //B -> C
//		
//		NodeAndLinkLists bothLists = new NodeAndLinkLists(cnList,clList);
//		
//		this.simpleGraph = new ConceptGraph(bothLists);
//		this.simpleTree = simpleGraph.graphToTree();
//		
//		logger.debug(simpleGraph);
//		logger.debug(simpleTree);
//	}
//	
//	public void makeMediumOld(){
//		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
//		List<ConceptLink> clList = new ArrayList<ConceptLink>();
//		
//		
//		//Make simple tree
//		Concept c = new ConceptImpl("A");
//		cnList.add(new ConceptNode(c, c.getConceptTitle()));
//		c = new ConceptImpl("B");
//		cnList.add(new ConceptNode(c, c.getConceptTitle()));
//		c = new ConceptImpl("C");
//		cnList.add(new ConceptNode(c, c.getConceptTitle()));
//		
//		
//		clList.add(new ConceptLink(cnList.get(0),cnList.get(1))); //A -> B
//		clList.add(new ConceptLink(cnList.get(0),cnList.get(2))); //A -> C
//		clList.add(new ConceptLink(cnList.get(1),cnList.get(2))); //B -> C
//		
//		//make medium graph
//		c = new ConceptImpl("D");
//		cnList.add(new ConceptNode(c, c.getConceptTitle()));
//		clList.add(new ConceptLink(cnList.get(1),cnList.get(3))); //B -> D
//		clList.add(new ConceptLink(cnList.get(2),cnList.get(3))); //C -> D
//		
//		NodeAndLinkLists bothLists = new NodeAndLinkLists(cnList, clList);
//		
//		this.mediumGraph = new ConceptGraph(bothLists);
//		
//		this.mediumTree = this.mediumGraph.graphToTree();
//		
//		logger.debug(mediumGraph);
//		logger.debug(mediumTree);
//	}
//	
//	public void makeComplexOld(){
//		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
//		List<ConceptLink> clList = new ArrayList<ConceptLink>();
//		
//		
//		//Make simple tree
//		Concept c = new ConceptImpl("A");
//		cnList.add(new ConceptNode(c, c.getConceptTitle()));
//		c = new ConceptImpl("B");
//		cnList.add(new ConceptNode(c, c.getConceptTitle()));
//		c = new ConceptImpl("C");
//		cnList.add(new ConceptNode(c, c.getConceptTitle()));
//		
//		
//		clList.add(new ConceptLink(cnList.get(0),cnList.get(1))); //A -> B
//		clList.add(new ConceptLink(cnList.get(0),cnList.get(2))); //A -> C
//		clList.add(new ConceptLink(cnList.get(1),cnList.get(2))); //B -> C
//		
//		//make medium graph
//		c = new ConceptImpl("D");
//		cnList.add(new ConceptNode(c, c.getConceptTitle()));
//		clList.add(new ConceptLink(cnList.get(1),cnList.get(3))); //B -> D
//		clList.add(new ConceptLink(cnList.get(2),cnList.get(3))); //C -> D
//		
//		c = new ConceptImpl("E");
//		cnList.add(new ConceptNode(c, c.getConceptTitle()));
//		clList.add(new ConceptLink(cnList.get(0), cnList.get(4))); //A -> E
//		clList.add(new ConceptLink(cnList.get(2), cnList.get(4))); //C -> E
//		clList.add(new ConceptLink(cnList.get(3), cnList.get(4))); //D -> E
//
//		NodeAndLinkLists bothLists = new NodeAndLinkLists(cnList, clList);
//		
//		this.complexGraph = new ConceptGraph(bothLists);
//		
//		this.complexTree = complexGraph.graphToTree();
//		
//		logger.debug(complexGraph);
//		logger.debug(complexTree);
//	}
}

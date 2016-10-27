package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.collections.transformation.SortedList;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.ObjectSummaryIdentifier;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.PerUserPerProblemSummary;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Book;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfInteractionDataParser;
import de.uds.MonitorInterventionMetafora.server.json.StructureCreationLibrary;
import de.uds.MonitorInterventionMetafora.server.json.JsonImportExport;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.analysis.AnalysisActions;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;

public class GroupConceptGraphsTest {

	static Logger logger = Logger.getLogger(ConceptGraphTest.class);
	
	ConceptGraph simpleGraph;
	ConceptGraph mediumGraph;
	ConceptGraph simpleTree;
	ConceptGraph mediumTree;
	ConceptGraph simpleInputTree;
	List<PerUserPerProblemSummary> test_summaries;
	
	@Before
	public void setUp() throws Exception {
		makeSimple();
		makeMedium();
		//Move to SetUp instead of in each file
		String inputXML = "test/testdata/GroupConceptGraphsTest.xml";
		
		//Get behaviors from runsetone xml
		XmlFragment runestoneFrag = XmlFragment.getFragmentFromLocalFile(inputXML);
		CfInteractionData testCf = CfInteractionDataParser.fromRunestoneXml(runestoneFrag);
				
		List<CfAction> allActions = testCf.getCfActions();
		
		//Creates problem summaries from user actions
		ObjectSummaryIdentifier myIdentifier = new ObjectSummaryIdentifier();
		List<String> involvedUsers = AnalysisActions.getOriginatingUsernames(allActions);
		test_summaries = myIdentifier.buildPerUserPerProblemSummaries(allActions, involvedUsers);
		
		
	}

	@After
	public void tearDown() throws Exception {
		this.simpleGraph = null;
		this.simpleTree = null;
		this.mediumGraph = null;
		this.mediumTree = null;
		this.simpleInputTree = null;
		this.test_summaries = null;
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
		this.simpleTree = simpleGraph.graphToTree();
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
		this.mediumTree = mediumGraph.graphToTree();
	}
	
	//@Test
	public void userCountTest(){
		GroupConceptGraphs group = new GroupConceptGraphs(simpleGraph,test_summaries);
		
		Assert.assertEquals(2,group.userCount());
	}
	
	@Test
	public void addSummariesTest(){
		GroupConceptGraphs group = new GroupConceptGraphs(simpleGraph,test_summaries);
		Map<String, ConceptGraph> userGraphMap = group.getUserToGraphMap();
		
		ConceptGraph user2Graph = userGraphMap.get("CLTestStudent1");
		NodesAndIDLinks user2Nodes = user2Graph.buildNodesAndLinks();
		
		Assert.assertEquals(6,user2Nodes.getNodes().size());
		Assert.assertEquals(6,user2Nodes.getLinks().size());
	}
	
	
	//@Test
	public void getAllGraphsTest(){
		GroupConceptGraphs group = new GroupConceptGraphs(simpleGraph,test_summaries);
		Map<String, ConceptGraph> userGraphMap = group.getUserToGraphMap();
		
		List<String> namesList = new ArrayList<String>();
		for (String name: userGraphMap.keySet()){
			namesList.add(name);
		}
		Assert.assertEquals("CLTestStudent2",namesList.get(0));
		Assert.assertEquals("CLTestStudent1",namesList.get(1));
		Assert.assertEquals(2,namesList.size());
		
		ConceptGraph user2Tree = userGraphMap.get("CLTestStudent2").graphToTree();
		NodesAndIDLinks user2Nodes = user2Tree.buildNodesAndLinks();
		
		
		//Assert.assertEquals(,.size());
	}
	
	//@Test
	public void jsonOutputTest(){
		GroupConceptGraphs group = new GroupConceptGraphs("war/TreeDisplay/input",simpleGraph,test_summaries);
		//Assert.assertEquals(1,1);
	}
	
	
}



	
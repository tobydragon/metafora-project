package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfInteractionDataParser;
import de.uds.MonitorInterventionMetafora.server.json.JsonCreationLibrary;
import de.uds.MonitorInterventionMetafora.server.json.JsonImportExport;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.analysis.AnalysisActions;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;

public class ConceptGraphTest {

	static Logger logger = Logger.getLogger(ConceptGraphTest.class);
	
	ConceptGraph simpleGraph;
	ConceptGraph mediumGraph;
	ConceptGraph complexGraph;
	ConceptGraph superComplexGraph;
	ConceptGraph simpleTree;
	ConceptGraph mediumTree;
	ConceptGraph complexTree;
	ConceptGraph superComplexTree;
	
	ConceptGraph graphFromJson;
	List<PerUserPerProblemSummary> summaries;
	
	@Before
	public void setUp() throws Exception {
		makeSimple();
		makeMedium();
		makeComplex();
		makeSuperComplex();
	}

	@After
	public void tearDown() throws Exception {
		this.simpleGraph = null;
		this.simpleTree = null;
		this.mediumGraph = null;
		this.mediumTree = null;
		this.complexGraph = null;
		this.complexTree = null;
		this.superComplexGraph = null;
		this.superComplexTree = null;
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
	public void makeComplex(){
		
		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<IDLink> clList = new ArrayList<IDLink>();
		
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
		this.complexTree = complexGraph.graphToTree();
	}
	
	public void makeSuperComplex(){
		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<IDLink> clList = new ArrayList<IDLink>();
		
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
		c = new ConceptImpl("F");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		
		clList.add(new IDLink("A","B"));
		clList.add(new IDLink("A","C"));
		clList.add(new IDLink("A","E"));
		clList.add(new IDLink("B","C"));
		clList.add(new IDLink("B","D"));
		clList.add(new IDLink("B","E"));
		clList.add(new IDLink("C","D"));
		clList.add(new IDLink("C","E"));
		clList.add(new IDLink("D","E"));
		clList.add(new IDLink("D","F"));
		clList.add(new IDLink("E","F"));
		
		NodesAndIDLinks inputNodesAndLinks = new NodesAndIDLinks(cnList,clList);
		this.superComplexGraph = new ConceptGraph(inputNodesAndLinks);
		this.superComplexTree = superComplexGraph.graphToTree();
	}
	
	@Test
	public void simpleGraphCheckNodesNumbersTest(){

		NodesAndIDLinks graphLists = this.simpleGraph.buildNodesAndLinks();
		NodesAndIDLinks treeLists = this.simpleTree.buildNodesAndLinks();
		
		Assert.assertEquals(3, graphLists.getNodes().size());
		Assert.assertEquals(4, treeLists.getNodes().size());
	}
	
	@Test
	public void simpleGraphCheckLinksNumbersTest(){
		NodesAndIDLinks graphLists = this.simpleGraph.buildNodesAndLinks();
		NodesAndIDLinks treeLists = this.simpleTree.buildNodesAndLinks();
		
		Assert.assertEquals(3, graphLists.getLinks().size());
		Assert.assertEquals(3, treeLists.getLinks().size());
	}
	
	@Test
	public void mediumGraphCheckNodesNumbersTest(){
		NodesAndIDLinks graphLists = this.mediumGraph.buildNodesAndLinks();
		NodesAndIDLinks treeLists = this.mediumTree.buildNodesAndLinks();
	
		Assert.assertEquals(4, graphLists.getNodes().size());
		Assert.assertEquals(7, treeLists.getNodes().size());
	}
	
	@Test
	public void mediumGraphCheckLinksNumbersTest(){
		NodesAndIDLinks graphLists = this.mediumGraph.buildNodesAndLinks();
		NodesAndIDLinks treeLists = this.mediumTree.buildNodesAndLinks();
		
		Assert.assertEquals(5, graphLists.getLinks().size());
		Assert.assertEquals(6, treeLists.getLinks().size());
	}
	
	@Test
	public void complexGraphCheckNodesNumbersTest(){
		NodesAndIDLinks graphLists = this.complexGraph.buildNodesAndLinks();
		NodesAndIDLinks treeLists = this.complexTree.buildNodesAndLinks();

		Assert.assertEquals(5, graphLists.getNodes().size());
		Assert.assertEquals(13, treeLists.getNodes().size());
	}
	
	@Test
	public void complexGraphCheckLinksNumbersTest(){
		NodesAndIDLinks graphLists = this.complexGraph.buildNodesAndLinks();
		NodesAndIDLinks treeLists = this.complexTree.buildNodesAndLinks();
		
		Assert.assertEquals(8, graphLists.getLinks().size());
		Assert.assertEquals(12, treeLists.getLinks().size());
	}
	
	@Test
	public void superComplexGraphCheckNodesNumbersTest(){
		NodesAndIDLinks graphLists = this.superComplexGraph.buildNodesAndLinks();
		NodesAndIDLinks treeLists = this.superComplexTree.buildNodesAndLinks();
		
		logger.debug(graphLists);
		logger.debug(treeLists);

		Assert.assertEquals(6, graphLists.getNodes().size());
		Assert.assertEquals(24, treeLists.getNodes().size());
	}
	
	@Test
	public void superComplexGraphCheckLinksNumbersTest(){
		NodesAndIDLinks graphLists = this.superComplexGraph.buildNodesAndLinks();
		NodesAndIDLinks treeLists = this.superComplexTree.buildNodesAndLinks();
		
		Assert.assertEquals(11, graphLists.getLinks().size());
		Assert.assertEquals(23, treeLists.getLinks().size());
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

	@Test
	public void makeConceptGraphFromFileTest(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
        try {
			NodesAndIDLinks lists = mapper.readValue(new File("test/testdata/ABCSimple.json"), NodesAndIDLinks.class);
			Assert.assertEquals(11, lists.getNodes().size());
			Assert.assertEquals(11, lists.getLinks().size());
			
			ConceptGraph myGraph = new ConceptGraph(lists);
			ConceptGraph myTree = myGraph.graphToTree();
			
			NodesAndIDLinks listsFromTree = myTree.buildNodesAndLinks();
			
			Assert.assertEquals(16, listsFromTree.getNodes().size());
			Assert.assertEquals(15, listsFromTree.getLinks().size());

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void makeJSONfromConceptGraphTest(){

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		String outputLocation = "CarrieJsonGraph.json";
		
        try {
			NodesAndIDLinks lists = mapper.readValue(new File("test/testdata/ABCSimple.json"), NodesAndIDLinks.class);
			try{
			mapper.writeValue(new File(outputLocation), lists);
			}catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (IOException e) {
			e.printStackTrace();
		}
				
		String jsonString = "";
		//reads json from file
		try {
			for (String line : Files.readAllLines(Paths.get(outputLocation))) {
			    jsonString += line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int numID = jsonString.split("id").length-1;
		jsonString = new String(jsonString);
		int numLink = jsonString.split("parent").length-1;
		
		Assert.assertEquals(11, numID);
		Assert.assertEquals(11, numLink);
	}
	
	@Test
	public void makeJSONfromConceptGraphTreeTest(){

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		String outputLocation = "CarrieJsonGraph.json";
		
        try {
			NodesAndIDLinks lists = mapper.readValue(new File("test/testdata/ABCSimple.json"), NodesAndIDLinks.class);
			ConceptGraph tree = new ConceptGraph(lists).graphToTree();
			
			try{
			mapper.writeValue(new File(outputLocation), tree.buildNodesAndLinks());
			}catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (IOException e) {
			e.printStackTrace();
		}
				
		String jsonString = "";
		//reads json from file
		try {
			for (String line : Files.readAllLines(Paths.get(outputLocation))) {
			    jsonString += line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int numID = jsonString.split("id").length-1;
		jsonString = new String(jsonString);
		int numLink = jsonString.split("parent").length-1;
		
		Assert.assertEquals(16, numID);
		Assert.assertEquals(15, numLink);
	}

}

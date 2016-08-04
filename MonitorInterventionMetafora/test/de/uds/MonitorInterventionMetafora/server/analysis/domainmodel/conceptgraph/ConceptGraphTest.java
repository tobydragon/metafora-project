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
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Book;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfInteractionDataParser;
import de.uds.MonitorInterventionMetafora.server.json.StructureCreationLibrary;
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
	ConceptGraph graphFromBook;
	ConceptGraph bookTree;
	
	@Before
	public void setUp() throws Exception {
		makeSimple();
		makeMedium();
		makeComplex();
		makeSuperComplex();
		makeBookGraph();
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
		this.graphFromBook = null;
		this.bookTree = null;
	}
	
	public void makeBookGraph(){
		Book b = new Book("Interacitve Python","war/conffiles/domainfiles/thinkcspy/");
		graphFromBook = new ConceptGraph(b);
		bookTree = graphFromBook.graphToTree();
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
	
	
	@Test
	public void makeSimpleSelectionGraphTest(){
				
		ConceptGraph selectionGraph = new ConceptGraph(StructureCreationLibrary.createSimpleSelection());		
		NodesAndIDLinks selectionLists =  selectionGraph.buildNodesAndLinks();
		
		Assert.assertEquals(14, selectionLists.getNodes().size());
		Assert.assertEquals(17, selectionLists.getLinks().size());
		
		
	}
	
	@Test
	public void makeSimpleSelectionTreeTest(){
				
		ConceptGraph selectionGraph = new ConceptGraph(StructureCreationLibrary.createSimpleSelection());
		ConceptGraph selectionTree = selectionGraph.graphToTree();
		NodesAndIDLinks selectionLists =  selectionTree.buildNodesAndLinks();
		
		Assert.assertEquals(22, selectionLists.getNodes().size());
		Assert.assertEquals(21, selectionLists.getLinks().size());		
	}
	@Test
	public void makeSummariesSimpleSelectionGraphTest(){
				
		String inputXML = "test/testdata/simple.xml";
		
		//Get behaviors from runsetone xml
		XmlFragment runestoneFrag = XmlFragment.getFragmentFromLocalFile(inputXML);
		CfInteractionData testCf = CfInteractionDataParser.fromRunestoneXml(runestoneFrag);
				
		List<CfAction> allActions = testCf.getCfActions();
		
		//Creates problem summaries from user actions
		ObjectSummaryIdentifier myIdentifier = new ObjectSummaryIdentifier();
		List<String> involvedUsers = AnalysisActions.getOriginatingUsernames(allActions);
		List<PerUserPerProblemSummary> summaries = myIdentifier.buildPerUserPerProblemSummaries(allActions, involvedUsers);
		
		for (PerUserPerProblemSummary summary : summaries){
			if (summary.toString() != null && summary.toString().contains("5_1_1")){
				logger.debug(summary);
			}
		}
		// Make the concept graph from Json
		//TODO: files should be read from within war file...
		NodesAndIDLinks selectionListsInput =  StructureCreationLibrary.createSimpleSelection();		
		ConceptGraph selectionGraph = new ConceptGraph(selectionListsInput);
		
		// Add summary info to it
		List<ConceptNode> graphSummaryNodeList = new ArrayList<ConceptNode>();
		for(PerUserPerProblemSummary summary : summaries){
			//System.out.println(summary.getObjectId());
			ConceptNode sumNode = new ConceptNode(summary);
			graphSummaryNodeList.add(sumNode);
		}
		
		//TODO: TD - Does this code belong in ObjectSummaryIdentifier? Doesn't seem like its job...
		//Also, should probably identify whether connection was made or not
		selectionGraph.addSummariesToGraph(summaries);

		// calculate "up" the graph the actual scores
		selectionGraph.calcActualComp();
		
		// calculate "down" the graph the predicted scores
		selectionGraph.calcPredictedScores();
		
		NodesAndIDLinks selectionLists =  selectionGraph.buildNodesAndLinks();
		
		Assert.assertEquals(16, selectionLists.getNodes().size());
		Assert.assertEquals(19, selectionLists.getLinks().size());
	}

	@Test
	public void makeSummariesSimpleSelectionGraphToTreeTest(){
				
		String inputXML = "test/testdata/simple.xml";
		
		//Get behaviors from runsetone xml
		XmlFragment runestoneFrag = XmlFragment.getFragmentFromLocalFile(inputXML);
		CfInteractionData testCf = CfInteractionDataParser.fromRunestoneXml(runestoneFrag);
				
		List<CfAction> allActions = testCf.getCfActions();
		
		//Creates problem summaries from user actions
		ObjectSummaryIdentifier myIdentifier = new ObjectSummaryIdentifier();
		List<String> involvedUsers = AnalysisActions.getOriginatingUsernames(allActions);
		List<PerUserPerProblemSummary> summaries = myIdentifier.buildPerUserPerProblemSummaries(allActions, involvedUsers);
		
		for (PerUserPerProblemSummary summary : summaries){
			if (summary.toString() != null && summary.toString().contains("5_1_1")){
				//logger.debug(summary);
			}
		}
		// Make the concept graph from Json
		//TODO: files should be read from within war file...
		NodesAndIDLinks selectionListsInput =  StructureCreationLibrary.createSimpleSelection();		
		ConceptGraph selectionGraph = new ConceptGraph(selectionListsInput);
		
		// Add summary info to it
		List<ConceptNode> graphSummaryNodeList = new ArrayList<ConceptNode>();
		for(PerUserPerProblemSummary summary : summaries){
			//System.out.println(summary.getObjectId());
			ConceptNode sumNode = new ConceptNode(summary);
			graphSummaryNodeList.add(sumNode);
		}
		
		//TODO: TD - Does this code belong in ObjectSummaryIdentifier? Doesn't seem like its job...
		//Also, should probably identify whether connection was made or not
		selectionGraph.addSummariesToGraph(summaries);

		// calculate "up" the graph the actual scores
		selectionGraph.calcActualComp();
		
		// calculate "down" the graph the predicted scores
		selectionGraph.calcPredictedScores();
		
		ConceptGraph selectionTree = selectionGraph.graphToTree();
		NodesAndIDLinks selectionLists =  selectionTree.buildNodesAndLinks();
		
		Assert.assertEquals(24, selectionLists.getNodes().size());
		Assert.assertEquals(23, selectionLists.getLinks().size());
	}
	
	@Test
	public void ConceptGraphFromBookNodesTest(){
		NodesAndIDLinks lists = graphFromBook.buildNodesAndLinks();
		Assert.assertEquals(217, lists.getNodes().size());
	}
	
	@Test
	public void ConceptGraphFromBookLinksTest(){
		NodesAndIDLinks lists = graphFromBook.buildNodesAndLinks();
		Assert.assertEquals(216, lists.getLinks().size());
	}
	
	@Test
	public void ConceptGraphFromBookSelectionTest(){
		NodesAndIDLinks lists = graphFromBook.buildNodesAndLinks();
		String[] selectionTitles = {"BooleanValuesandBooleanExpressions", "Logicaloperators", "PrecedenceofOperators", "ConditionalExecutionBinarySelection", "OmittingtheelseClauseUnarySelection", "Nestedconditionals", "Chainedconditionals", "BooleanFunctions"};
		List<ConceptNode> nodes = lists.getNodes();
		List<ConceptNode> selectionNodes = new ArrayList<ConceptNode>();
		
		ConceptNode selectionNode = null;
				
		for(int i = 0; i < nodes.size(); i++){
			if(nodes.get(i).getConcept().getConceptTitle().equals("Selection")){
				selectionNode = nodes.get(i);
			}
		}
		
		List<ConceptNode> selectionChildren = selectionNode.getChildren(); 
		
		for(int i = 0; i < nodes.size(); i++){
			for(int j = 0; j < selectionTitles.length; j++){
				if(nodes.get(i).getID().contains(selectionTitles[j])){
					selectionNodes.add(nodes.get(i));
				}
			}
		}
		
		System.out.println(selectionChildren);
		System.out.println(selectionNodes);
		
		Assert.assertEquals(8, selectionChildren.size()-2-1); //sub 2 for exercises and glossary, sub 1 for intro page
		Assert.assertEquals(8, selectionNodes.size()-1); //sub for duplicate BooleanFunctions	
	}
	
	@Test
	public void OutputFromBookTest(){
		//TODO: Json does not work with visualization because there are duplicate row names.
		NodesAndIDLinks lists = graphFromBook.buildNodesAndLinks();
		JsonImportExport.toJson("test", lists);
	}
}

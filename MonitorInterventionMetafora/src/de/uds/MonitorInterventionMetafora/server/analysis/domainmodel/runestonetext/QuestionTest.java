package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.Concept;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.ConceptGraph;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.ConceptImpl;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.ConceptNode;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.IDLink;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.NodesAndIDLinks;



public class QuestionTest {
	
	public String input = "";
	public Question q1 = null;
	public String testString;
	public SubChapter subChap;
	public ConceptGraph cg;

	@Before
	public void setUp() throws Exception {
		 input = "";
		 q1 = null;
		 subChap = new SubChapter("Test","sampleChapter","test/testdata/sampleChapter.rst");
		 testString = ".. tag test_questions6_1_2:Boolean, Boolean Expression, Data Types";
		 createSimpleCG();
	}

	@After
	public void tearDown() throws Exception {
		input = null;
		q1 = null;
		subChap = null;
		testString = null;
		cg = null;
		
	}

	public void createSimpleCG(){
		ArrayList<ConceptNode> cnList = new ArrayList<ConceptNode>();
		Concept c = new ConceptImpl("Boolean");
		ConceptNode cn = new ConceptNode(c);
		cnList.add(cn);
		
		c = new ConceptImpl("Boolean Expression");
		cn = new ConceptNode(c);
		cnList.add(cn);
		
		c = new ConceptImpl("Data Types");
		cn = new ConceptNode(c);
		cnList.add(cn);
		
		c = new ConceptImpl("Expression");
		cn = new ConceptNode(c);
		cnList.add(cn);
		
		c = new ConceptImpl("A");
		cn = new ConceptNode(c);
		cnList.add(cn);
		
		c = new ConceptImpl("B");
		cn = new ConceptNode(c);
		cnList.add(cn);
		
		ArrayList<IDLink> linkList = new ArrayList<IDLink>();
		linkList.add(new IDLink("B","Boolean"));
		linkList.add(new IDLink("B","Boolean Expression"));
		linkList.add(new IDLink("B","DataTypes"));
		linkList.add(new IDLink("B","Expression"));
		linkList.add(new IDLink("A","B"));
		
		NodesAndIDLinks simpleLists = new NodesAndIDLinks(cnList, linkList);
		
		this.cg = new ConceptGraph(simpleLists);
		
		
	}
	
	@Test
	public void testCreateTags(){
		List tags = Question.createTags(testString);
		List myTags = new ArrayList();
		myTags.add("Boolean");
		myTags.add("Boolean Expression");
		myTags.add("Data Types");
	
		
		boolean tagsMatch = true;
		for(int i = 0; i < tags.size(); i++){
			if(!tags.get(i).equals(myTags.get(i))){
				tagsMatch = false;
			}
		}
		Assert.assertEquals(tagsMatch, true);
	}
	
	@Test
	public void testAddingQs(){
		
	}
	
	@Test
	public void testGetTags(){
		List<Question> mcQs = new ArrayList<Question>();
		for(Question q : subChap.getQuestions()){
			if(q.getType().getLogString().equals("mchoice")){
				mcQs.add(q);
			}
		}
		
		HashMap<String, ArrayList<String>> testHash = new HashMap<String, ArrayList<String>>();
		ArrayList <String> tempList = new ArrayList();
		tempList.add("Boolean");
		testHash.put("test_question6_1_1", tempList);
		
		tempList = new ArrayList();
		tempList.add("Boolean");
		tempList.add("Boolean Expression");
		tempList.add("Data Types");
		testHash.put("test_question6_1_2", tempList);
		
		tempList = new ArrayList();
		tempList.add("Boolean");
		tempList.add("Boolean Expression");
		tempList.add("Expressions");
		testHash.put("test_question6_1_3", tempList);
		
		tempList = new ArrayList();
		tempList.add("Boolean Expression");
		testHash.put("test_question6_1_4", tempList);
		
	
		Assert.assertEquals(testHash.get("test_question6_1_1").get(0), mcQs.get(0).getTags().get(0));
		Assert.assertEquals(testHash.get("test_question6_1_2").get(0), mcQs.get(1).getTags().get(0));
		Assert.assertEquals(testHash.get("test_question6_1_2").get(1), mcQs.get(1).getTags().get(1));
		Assert.assertEquals(testHash.get("test_question6_1_2").get(2), mcQs.get(1).getTags().get(2));
		Assert.assertEquals(testHash.get("test_question6_1_3").get(0), mcQs.get(2).getTags().get(0));
		Assert.assertEquals(testHash.get("test_question6_1_3").get(1), mcQs.get(2).getTags().get(1));
		Assert.assertEquals(testHash.get("test_question6_1_3").get(2), mcQs.get(2).getTags().get(2));
		Assert.assertEquals(testHash.get("test_question6_1_4").get(0), mcQs.get(3).getTags().get(0));
		
	}
	
	@Test
	public void testAddToCG(){
		
		NodesAndIDLinks fullNLList = cg.buildNodesAndLinks();
		List<ConceptNode> cnList = fullNLList.getNodes();
		List<IDLink> linkList = fullNLList.getLinks();
		
		List<Question> mcQs = new ArrayList<Question>();
		for(Question q : subChap.getQuestions()){
			if(q.getType().getLogString().equals("mchoice")){
				mcQs.add(q);
			}
		}
		
		for(Question q : mcQs){
			List<IDLink> myLinks = q.tagsToAdd();
			for(IDLink link : myLinks){
				linkList.add(link);
			}
		}
		
		fullNLList = new NodesAndIDLinks(cnList,linkList);
		ConceptGraph fullCG = new ConceptGraph(fullNLList);
		
		
	}

}

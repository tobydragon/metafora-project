package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

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
import de.uds.MonitorInterventionMetafora.shared.utils.PeekableScanner;



public class QuestionTest {
	
	public String input = "";
	public Question q1 = null;
	public String testString;
	public SubChapter subChap;
//
//	@Before
//	public void setUp() throws Exception {
//		 input = "";
//		 q1 = null;
//		 subChap = new SubChapter("Test","sampleChapter","test/testdata/sampleChapter.rst");
//		 testString = ".. tag test_questions6_1_2:Boolean, Boolean Expression, Data Types";
//	}
//
//	@After
//	public void tearDown() throws Exception {
//		input = null;
//		q1 = null;
//		subChap = null;
//		testString = null;
//		
//	}

	@Test
	public void QuestionConstructorFromListTest(){
		//text taken from chapter MoreAboutIteration, used website to convert text to string literal:  http://snible.org/java2/uni2java.html
		String test_question7_8_2_1 = ".. mchoice:: test_question7_8_2_1\n   :answer_a: 149 132 122\n   :answer_b: 183 179 170\n   :answer_c: 165 161 158\n   :answer_d: 201 104 115\n   :correct: b\n   :feedback_a: These are the values for the pixel at row 30, column 100.  Get the values for row 100 and column 30 with p = img.getPixel(100, 30).\n   :feedback_b: Yes, the RGB values are 183 179 170 at row 100 and column 30.\n   :feedback_c: These are the values from the original example (row 45, column 55). Get the values for row 100 and column 30 with p = img.getPixel(30, 100).\n   :feedback_d: These are simply made-up values that may or may not appear in the image.  Get the values for row 100 and column 30 with p = img.getPixel(30, 100).\n\n   Using the previous ActiveCode example, select the answer that is closest to the RGB values of the pixel at row 100, column 30?  The values may be off by one or two due to differences in browsers.\n\n\n";
		List<String>  questionList = Question.buildQuestionTextList(new PeekableScanner(test_question7_8_2_1));
		Question question = new Question(questionList);
		
		Assert.assertEquals("test_question7_8_2_1", question.getConceptTitle());
		Assert.assertEquals(QuestionType.MULT_CHOICE, question.getType());
		Assert.assertEquals( "Using the previous ActiveCode example, select the answer that is closest to the RGB values of the pixel at row 100, column 30?  The values may be off by one or two due to differences in browsers.", question.getQuestionText());
	}
	
	@Test
	public void buildQuestionTextListTest(){
		//the kind of scanner that should be handed to Question.buildQuestionTextList
		String testQuestion7_8_2_1WithJunkAfter = ".. mchoice:: test_question7_8_2_1\n   :answer_a: 149 132 122\n   :answer_b: 183 179 170\n   :answer_c: 165 161 158\n   :answer_d: 201 104 115\n   :correct: b\n   :feedback_a: These are the values for the pixel at row 30, column 100.  Get the values for row 100 and column 30 with p = img.getPixel(100, 30).\n   :feedback_b: Yes, the RGB values are 183 179 170 at row 100 and column 30.\n   :feedback_c: These are the values from the original example (row 45, column 55). Get the values for row 100 and column 30 with p = img.getPixel(30, 100).\n   :feedback_d: These are simply made-up values that may or may not appear in the image.  Get the values for row 100 and column 30 with p = img.getPixel(30, 100).\n\n   Using the previous ActiveCode example, select the answer that is closest to the RGB values of the pixel at row 100, column 30?  The values may be off by one or two due to differences in browsers.\n\n\nImage Processing and Nested Iteration\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n\n**Image processing** refers to the ability to manipulate the individual pixels in a digital image.  In order to process\nall of the pixels, we need to be able to systematically visit all of the rows and columns in the image.  The best way\nto do this is to use **nested iteration**.";
		PeekableScanner midSubChapter = new PeekableScanner(testQuestion7_8_2_1WithJunkAfter);
		
		List<String>  questionList = Question.buildQuestionTextList(midSubChapter);
		Assert.assertEquals(11, questionList.size());
		Assert.assertEquals(".. mchoice:: test_question7_8_2_1", questionList.get(0));
		Assert.assertEquals("   Using the previous ActiveCode example, select the answer that is closest to the RGB values of the pixel at row 100, column 30?  The values may be off by one or two due to differences in browsers.", questionList.get(questionList.size()-1));
		int linesLeft=0;
		while (midSubChapter.hasNextLine()){
			midSubChapter.nextLine();
			linesLeft++;
		}
		Assert.assertEquals(6, linesLeft);
	}
	
	
	
//	public NodesAndIDLinks createSimpleCG(){
//		ArrayList<ConceptNode> cnList = new ArrayList<ConceptNode>();
//		Concept c = new ConceptImpl("Boolean");
//		ConceptNode cn = new ConceptNode(c);
//		cnList.add(cn);
//		
//		c = new ConceptImpl("Boolean Expression");
//		cn = new ConceptNode(c);
//		cnList.add(cn);
//		
//		c = new ConceptImpl("Data Types");
//		cn = new ConceptNode(c);
//		cnList.add(cn);
//		
//		c = new ConceptImpl("Expressions");
//		cn = new ConceptNode(c);
//		cnList.add(cn);
//		
//		c = new ConceptImpl("A");
//		cn = new ConceptNode(c);
//		cnList.add(cn);
//		
//		c = new ConceptImpl("B");
//		cn = new ConceptNode(c);
//		cnList.add(cn);
//		
//		c = new ConceptImpl("test_question6_1_1");
//		cn = new ConceptNode(c);
//		cnList.add(cn);
//		
//		c = new ConceptImpl("test_question6_1_2");
//		cn = new ConceptNode(c);
//		cnList.add(cn);
//		
//		c = new ConceptImpl("test_question6_1_3");
//		cn = new ConceptNode(c);
//		cnList.add(cn);
//		
//		c = new ConceptImpl("test_question6_1_4");
//		cn = new ConceptNode(c);
//		cnList.add(cn);
//		
//		ArrayList<IDLink> linkList = new ArrayList<IDLink>();
//		linkList.add(new IDLink("B","Boolean"));
//		linkList.add(new IDLink("B","Boolean Expression"));
//		linkList.add(new IDLink("B","Data Types"));
//		linkList.add(new IDLink("B","Expressions"));
//		linkList.add(new IDLink("A","B"));
//		
//		NodesAndIDLinks simpleLists = new NodesAndIDLinks(cnList, linkList);
//		
//		return simpleLists;
//	}
//	
//	@Test
//	public void testCreateTags(){
//		List tags = Question.createTags(testString);
//		List myTags = new ArrayList();
//		myTags.add("Boolean");
//		myTags.add("Boolean Expression");
//		myTags.add("Data Types");
//	
//		
//		boolean tagsMatch = true;
//		for(int i = 0; i < tags.size(); i++){
//			if(!tags.get(i).equals(myTags.get(i))){
//				tagsMatch = false;
//			}
//		}
//		Assert.assertEquals(tagsMatch, true);
//	}
//	
//	@Test
//	public void testAddingQs(){
//		
//	}
//	
//	@Test
//	public void testGetTags(){
//		List<Question> mcQs = new ArrayList<Question>();
//		for(Question q : subChap.getQuestions()){
//			if(q.getType().getLogString().equals("mchoice")){
//				mcQs.add(q);
//			}
//		}
//		
//		HashMap<String, ArrayList<String>> testHash = new HashMap<String, ArrayList<String>>();
//		ArrayList <String> tempList = new ArrayList();
//		tempList.add("Boolean");
//		testHash.put("test_question6_1_1", tempList);
//		
//		tempList = new ArrayList();
//		tempList.add("Boolean");
//		tempList.add("Boolean Expression");
//		tempList.add("Data Types");
//		testHash.put("test_question6_1_2", tempList);
//		
//		tempList = new ArrayList();
//		tempList.add("Boolean");
//		tempList.add("Boolean Expression");
//		tempList.add("Expressions");
//		testHash.put("test_question6_1_3", tempList);
//		
//		tempList = new ArrayList();
//		tempList.add("Boolean Expression");
//		testHash.put("test_question6_1_4", tempList);
//		
//	
//		Assert.assertEquals(testHash.get("test_question6_1_1").get(0), mcQs.get(0).getTags().get(0));
//		Assert.assertEquals(testHash.get("test_question6_1_2").get(0), mcQs.get(1).getTags().get(0));
//		Assert.assertEquals(testHash.get("test_question6_1_2").get(1), mcQs.get(1).getTags().get(1));
//		Assert.assertEquals(testHash.get("test_question6_1_2").get(2), mcQs.get(1).getTags().get(2));
//		Assert.assertEquals(testHash.get("test_question6_1_3").get(0), mcQs.get(2).getTags().get(0));
//		Assert.assertEquals(testHash.get("test_question6_1_3").get(1), mcQs.get(2).getTags().get(1));
//		Assert.assertEquals(testHash.get("test_question6_1_3").get(2), mcQs.get(2).getTags().get(2));
//		Assert.assertEquals(testHash.get("test_question6_1_4").get(0), mcQs.get(3).getTags().get(0));
//		
//	}
//	
//	@Test
//	public void testAddToCG(){
//		
////		ConceptGraph current = new ConceptGraph(createSimpleCG());
////		NodesAndIDLinks fullNLList = current.buildNodesAndLinks();
//		
//		NodesAndIDLinks fullNLList = createSimpleCG();
//		
//		List<ConceptNode> cnList = fullNLList.getNodes();
//		List<IDLink> linkList = fullNLList.getLinks();
//		
//		List<Question> mcQs = new ArrayList<Question>();
//		for(Question q : subChap.getQuestions()){
//			if(q.getType().getLogString().equals("mchoice")){
//				mcQs.add(q);
//			}
//		}
//		
//		for(Question q : mcQs){
//			List<IDLink> myLinks = q.buildTagLinks();
//			for(IDLink link : myLinks){
//				linkList.add(link);
//			}
//		}
//		
//		fullNLList = new NodesAndIDLinks(cnList,linkList);
//		ConceptGraph fullCG = new ConceptGraph(fullNLList);
//		NodesAndIDLinks test = fullCG.buildNodesAndLinks();
//		Assert.assertEquals(13, test.getLinks().size());
//		Assert.assertEquals(fullNLList.getNodes().size(),test.getNodes().size());
//		
//	}
//
}
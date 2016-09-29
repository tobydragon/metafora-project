package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Book;

public class CGandQTest {

	Book testBook;
	NodesAndIDLinks testGraphLinks;
	
	@Before
	public void setUp() throws Exception {
		this.testBook = new Book("My Test Book", "war/MyTestBook/");
		this.testGraphLinks = createSimpleCG();
	}

	@After
	public void tearDown() throws Exception {
		this.testBook =  null;
		this.testGraphLinks = null;
	}
	
	
	public NodesAndIDLinks createSimpleCG(){
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
		
		c = new ConceptImpl("Expressions");
		cn = new ConceptNode(c);
		cnList.add(cn);
		
		c = new ConceptImpl("A");
		cn = new ConceptNode(c);
		cnList.add(cn);
		
		c = new ConceptImpl("B");
		cn = new ConceptNode(c);
		cnList.add(cn);
		
		c = new ConceptImpl("test_question6_1_1");
		cn = new ConceptNode(c);
		cnList.add(cn);
		
		c = new ConceptImpl("test_question6_1_2");
		cn = new ConceptNode(c);
		cnList.add(cn);
		
		c = new ConceptImpl("test_question6_1_3");
		cn = new ConceptNode(c);
		cnList.add(cn);
		
		c = new ConceptImpl("test_question6_1_4");
		cn = new ConceptNode(c);
		cnList.add(cn);
		
		ArrayList<IDLink> linkList = new ArrayList<IDLink>();
		linkList.add(new IDLink("B","Boolean"));
		linkList.add(new IDLink("B","Boolean Expression"));
		linkList.add(new IDLink("B","Data Types"));
		linkList.add(new IDLink("B","Expressions"));
		linkList.add(new IDLink("A","B"));
		
		NodesAndIDLinks simpleLists = new NodesAndIDLinks(cnList, linkList);
		
		return simpleLists;
	}
	
	@Test
	public void newConstructorTest() {
		ConceptGraph simpleCG = new ConceptGraph(this.testBook, this.testGraphLinks);
		NodesAndIDLinks newNodesAndLinks = simpleCG.buildNodesAndLinks();
		
		Assert.assertEquals(this.testGraphLinks.getLinks().size()+48, newNodesAndLinks.getLinks().size());
		Assert.assertEquals(this.testGraphLinks.getNodes().size(), newNodesAndLinks.getNodes().size());
		//expect 48 extra links
	}
	
	@Test
	public void gettingFile(){
		@SuppressWarnings("unused")
		File myFile = new File("test/MyTestBook/_sources/Intro/pageThree.rst");
		
	}

}

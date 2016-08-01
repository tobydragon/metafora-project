package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConceptNodeTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void makeNameWithInitialInputTest() {
		Assert.assertEquals("Title-1", ConceptNode.makeName("Title"));
	}
	
	@Test 
	public void makeNameWithExpectedInputTest(){
		Assert.assertEquals("Title-4", ConceptNode.makeName("Title-3"));
	}
	
	@Test
	public void makeNameWithTwoDigitInputTest(){
		Assert.assertEquals("Title-35", ConceptNode.makeName("Title-34"));
	}
	
	@Test
	public void makeNameWithDoubleDashTest(){
		Assert.assertEquals("MyTitle-4", ConceptNode.makeName("My-Title-3"));
	}
	
	@Test
	public void makeNameWithDoubleDashNoNumTest(){
		Assert.assertEquals("MyTitleName-1", ConceptNode.makeName("My-Title-Name"));
	}

	
}

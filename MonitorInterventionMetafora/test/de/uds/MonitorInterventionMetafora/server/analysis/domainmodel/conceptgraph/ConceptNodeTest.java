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

}

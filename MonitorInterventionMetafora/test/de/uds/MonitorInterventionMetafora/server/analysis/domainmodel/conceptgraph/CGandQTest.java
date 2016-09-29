package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Book;

public class CGandQTest {

	Book testBook;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		this.testBook =  null;
	}

	@Test
	public void test() {
		this.testBook = new Book("My Test Book", "test/MyTestBook/");
				
	}

}

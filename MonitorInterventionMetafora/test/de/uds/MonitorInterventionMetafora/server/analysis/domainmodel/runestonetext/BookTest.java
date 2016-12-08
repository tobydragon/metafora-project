package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

import org.junit.Test;

public class BookTest {

	
	@Test
	public void BookConstructorTest(){
		Book b = new Book("Interacitve Python","war/conffiles/domainfiles/thinkcspy/");
		
		System.out.println(b);
	}
}

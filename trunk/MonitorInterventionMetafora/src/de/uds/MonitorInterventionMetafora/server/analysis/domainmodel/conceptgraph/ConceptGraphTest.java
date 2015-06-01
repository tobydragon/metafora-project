package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Book;

public class ConceptGraphTest {

	public static Book b;
	public static ConceptGraph graph;


	public static void main(String[] args) throws Exception {
		b = new Book("Interacitve Python","war/conffiles/domainfiles/thinkcspy/");

		graph = new ConceptGraph(b);
		System.out.println(graph);
	}

}
 
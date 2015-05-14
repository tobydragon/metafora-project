package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

public class BookTest {
	public static Book b;

	public static void main(String[] args) throws Exception {
		b = new Book("Interacitve Python","war/conffiles/domainfiles/thinkcspy/");
		System.out.println(b);
	}
}

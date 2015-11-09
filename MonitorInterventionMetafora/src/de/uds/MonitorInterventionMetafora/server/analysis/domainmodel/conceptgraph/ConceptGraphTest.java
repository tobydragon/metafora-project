package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.Book;
import de.uds.MonitorInterventionMetafora.server.json.JsonImportExport;

public class ConceptGraphTest {

	public static void main(String[] args) throws Exception {
		
		// test building the graphs and nodes from book and send to JSON
		String bookPath = "/Users/David/git/metafora-project/MonitorInterventionMetafora/war/conffiles/domainfiles/thinkcspy/";
		Book b = new Book("Interacitve Python", bookPath);
		//create a ConceptGraph of the book and then call createConceptGraph in order to add the summaries to the graph
		ConceptGraph graph = new ConceptGraph(b);

		// the actual printing to JSON
		NodeAndLinkLists lists =  graph.buildNodesAndLinks();
		JsonImportExport.toJson("testing", lists);
		
		
		// test building Nodes and Edges lists from JSON "small json"
		NodeAndLinkLists fromJsonLists =  JsonImportExport.fromJson("/Users/David/Documents/2015/SeniorProject/nodesAndEdgesBasicFull.json");		
		
		// Need to test making concept graph from JSON
		ConceptGraph graphFromJson = new ConceptGraph(fromJsonLists);
		System.out.println(graphFromJson);
		
	}
	
	

}
 
package de.uds.MonitorInterventionMetafora.server.analysis.behaviors.output;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.ConceptGraph;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.NodesAndIDLinks;

public class NamedGraph {
	public String name;
	public NodesAndIDLinks cg;
	
	public NamedGraph(String nameIn, ConceptGraph cgIn){
		name = nameIn;
		ConceptGraph tree = cgIn.graphToTree();
		cg = tree.buildNodesAndLinks();
		
	}
	
	public String toString(){
		return this.name;
	}
}
package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.ConceptGraph;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.NodesAndIDLinks;

public class Student {
	public String name;
	public NodesAndIDLinks cg;
	
	public Student(String nameIn, ConceptGraph cgIn){
		name = nameIn;
		ConceptGraph tree = cgIn.graphToTree();
		cg = tree.buildNodesAndLinks();
	}
	
	public String toString(){
		return this.name;
	}
}

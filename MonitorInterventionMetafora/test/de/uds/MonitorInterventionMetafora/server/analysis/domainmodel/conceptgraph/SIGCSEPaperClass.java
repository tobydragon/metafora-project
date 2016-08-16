package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import de.uds.MonitorInterventionMetafora.server.json.JsonImportExport;

public class SIGCSEPaperClass {
	
	public static void makeSimpleScenarioGraph(){
		NodesAndIDLinks lists = JsonImportExport.fromJson("war/conffiles/domainfiles/conceptgraph/exampleForPaper.json");
		ConceptGraph simpleScene = new ConceptGraph(lists);
		ConceptGraph simpleSceneTree = simpleScene.graphToTree();
		NodesAndIDLinks toBeJsoned = simpleSceneTree.buildNodesAndLinks();
		JsonImportExport.toJson("outputTest", toBeJsoned);
	}
	
	public static void main(String args[]){
		makeSimpleScenarioGraph();
	}

}

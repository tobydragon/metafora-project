package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.ConceptGraph;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.NodesAndIDLinks;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;

public class ConceptGraphAnalyzer {
	public static Logger logger = Logger.getLogger(ConceptGraphAnalyzer.class);


	public static List<BehaviorInstance> addAnalysisAndCreateVisualiztionHtml(List<CfAction> actionsToConsider, List<String> involvedUsers) {
		try{
			NodesAndIDLinks graphDef = NodesAndIDLinks.buildfromJson(GeneralUtil.getRealPath("conffiles/domainfiles/conceptgraph/selectionStructure.json"));
			ConceptGraph conceptGraph = new ConceptGraph(graphDef);
			
			ObjectSummaryIdentifier summarizer = new ObjectSummaryIdentifier();
			List<PerUserPerProblemSummary> summaries = summarizer.buildPerUserPerProblemSummaries(actionsToConsider, involvedUsers);
			
			conceptGraph.addSummariesToGraph(summaries);
			conceptGraph.calcActualComp();
			
			ConceptGraph treeVersion = conceptGraph.graphToTree();
			NodesAndIDLinks treeLists = treeVersion.buildNodesAndLinks();
			treeLists.writeToJson(GeneralUtil.getRealPath("TreeDisplay/input.json"));
			
			// TODO create a summary for each node in the concept graph
			
		}
		catch(IOException e){
			logger.error("Problem loading ConceptGraph from Json:",  e);
			return new ArrayList<BehaviorInstance>();
		}
		
		 
		
		return null;
	}

}

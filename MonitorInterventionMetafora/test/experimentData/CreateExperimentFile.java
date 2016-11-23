package experimentData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.ObjectSummaryIdentifier;
import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.PerUserPerProblemSummary;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.ConceptGraph;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.GroupConceptGraphs;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.NodesAndIDLinks;
import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfInteractionDataParser;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.analysis.AnalysisActions;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;

public class CreateExperimentFile {
	
	public static void main(String[] args){
		ObjectMapper mapper = new ObjectMapper();
		String inputXML = "test/experimentData/experiment.xml";
		
		//Get behaviors from runsetone xml
		XmlFragment runestoneFrag = XmlFragment.getFragmentFromLocalFile(inputXML);
		CfInteractionData testCf = CfInteractionDataParser.fromRunestoneXml(runestoneFrag);
				
		List<CfAction> allActions = testCf.getCfActions();
		
		//Creates problem summaries from user actions
		ObjectSummaryIdentifier myIdentifier = new ObjectSummaryIdentifier();
		List<String> involvedUsers = AnalysisActions.getOriginatingUsernames(allActions);
		List<PerUserPerProblemSummary> sums  = myIdentifier.buildPerUserPerProblemSummaries(allActions, involvedUsers);
		
		NodesAndIDLinks nodes = null;
		try {
			nodes = mapper.readValue(new File("test/experimentData/domainStructure.json"), NodesAndIDLinks.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ConceptGraph graph = new ConceptGraph(nodes);
		GroupConceptGraphs group = new GroupConceptGraphs(graph,sums);
		
		try {
			mapper.writeValue(new File("war/TreeDisplay/experimentInput/fullExperimentFile"+".json"), group.getAllNamedGraphs());
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

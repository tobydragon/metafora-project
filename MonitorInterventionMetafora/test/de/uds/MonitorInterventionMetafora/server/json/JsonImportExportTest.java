package de.uds.MonitorInterventionMetafora.server.json;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.Concept;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.ConceptGraph;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.ConceptImpl;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.ConceptNode;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.IDLink;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.NodesAndIDLinks;

public class JsonImportExportTest {
	 ObjectMapper mapper;
	 NodesAndIDLinks lists;

	@Before
	public void setUp() throws Exception {
		setMedium();
	}

	@After
	public void tearDown() throws Exception {
		this.mapper = null;
	}
	
	
	public void setSimple(){
		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<IDLink> clList = new ArrayList<IDLink>();
		
		
		//Make simple tree
		Concept c = new ConceptImpl("A");
		ConceptNode cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("B");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("C");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		
		clList.add(new IDLink("A","B")); //A -> B
		clList.add(new IDLink("A","C")); //A -> C
		clList.add(new IDLink("B","C")); //B -> C
		
		NodesAndIDLinks inputNodesAndLinks = new NodesAndIDLinks(cnList,clList);
		
		ConceptGraph inputGraph = new ConceptGraph(inputNodesAndLinks);
		ConceptGraph inputTree = inputGraph.graphToTree();
		this.lists = inputTree.buildNodesAndLinks();
		
		this.mapper = new ObjectMapper();
	}
	
	public void setMedium(){
		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<IDLink> clList = new ArrayList<IDLink>();
		
		
		//Make simple tree
		Concept c = new ConceptImpl("A");
		ConceptNode cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("B");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("C");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("D");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		
		
		clList.add(new IDLink("A","B")); //A -> B
		clList.add(new IDLink("A","C")); //A -> C
		clList.add(new IDLink("B","C")); //B -> C
		clList.add(new IDLink("B","D")); //B -> D
		clList.add(new IDLink("C","D")); //C -> D
		
		NodesAndIDLinks inputNodesAndLinks = new NodesAndIDLinks(cnList,clList);
		
		ConceptGraph inputGraph = new ConceptGraph(inputNodesAndLinks);
		ConceptGraph inputTree = inputGraph.graphToTree();
		this.lists = inputTree.buildNodesAndLinks();
		
		this.mapper = new ObjectMapper();
	}

	public void setComplex(){

		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<IDLink> clList = new ArrayList<IDLink>();
		
		Concept c = new ConceptImpl("A");
		ConceptNode cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("B");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("C");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("D");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("E");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);

		clList.add(new IDLink("A","B")); //A -> B
		clList.add(new IDLink("A","C")); //A -> C
		clList.add(new IDLink("B","C")); //B -> C
		clList.add(new IDLink("B","D")); //B -> D
		clList.add(new IDLink("C","D")); //C -> D
		clList.add(new IDLink("A","E")); //A -> E
		clList.add(new IDLink("C","E")); //C -> E
		clList.add(new IDLink("D","E")); //D -> E
		
		NodesAndIDLinks inputNodesAndLinks = new NodesAndIDLinks(cnList,clList);
		
		ConceptGraph inputGraph = new ConceptGraph(inputNodesAndLinks);
		ConceptGraph inputTree = inputGraph.graphToTree();
		this.lists = inputTree.buildNodesAndLinks();
		
		this.mapper = new ObjectMapper();
	}

	public void setSuperComplex(){

		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<IDLink> clList = new ArrayList<IDLink>();
		
		Concept c = new ConceptImpl("A");
		ConceptNode cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("B");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("C");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("D");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("E");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		c = new ConceptImpl("F");
		cn = (new ConceptNode(c, c.getConceptTitle()));
		cnList.add(cn);
		
		clList.add(new IDLink("A","B"));
		clList.add(new IDLink("A","C"));
		clList.add(new IDLink("A","E"));
		clList.add(new IDLink("B","C"));
		clList.add(new IDLink("B","D"));
		clList.add(new IDLink("B","E"));
		clList.add(new IDLink("C","D"));
		clList.add(new IDLink("C","E"));
		clList.add(new IDLink("D","E"));
		clList.add(new IDLink("D","F"));
		clList.add(new IDLink("E","F"));
		
		NodesAndIDLinks inputNodesAndLinks = new NodesAndIDLinks(cnList,clList);
		
		ConceptGraph inputGraph = new ConceptGraph(inputNodesAndLinks);
		ConceptGraph inputTree = inputGraph.graphToTree();
		this.lists = inputTree.buildNodesAndLinks();
		
		this.mapper = new ObjectMapper();
	}
	
	@Test
	public void JSONInputMatchOutputTest() {
		try {
			mapper.writeValue(new File("CarrieJsonGraph.json"), this.lists);
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

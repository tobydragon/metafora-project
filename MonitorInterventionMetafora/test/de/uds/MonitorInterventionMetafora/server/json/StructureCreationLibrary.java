package de.uds.MonitorInterventionMetafora.server.json;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.Concept;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.ConceptGraph;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.ConceptImpl;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.ConceptNode;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.IDLink;
import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.NodesAndIDLinks;

public class StructureCreationLibrary {
	
	public static NodesAndIDLinks createSimple(){
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
		return inputTree.buildNodesAndLinks();
	}
	
	public static NodesAndIDLinks  createMedium(){
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
		return inputTree.buildNodesAndLinks();
	}

	public static NodesAndIDLinks  createComplex(){

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
		return inputTree.buildNodesAndLinks();
	}

	public static NodesAndIDLinks  createSuperComplex(){

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
		return inputTree.buildNodesAndLinks();
	}

	public static NodesAndIDLinks createDomainModel(){
		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<IDLink> clList = new ArrayList<IDLink>();
		
		
		//Make Domain Model
		Concept c = new ConceptImpl("Intro CS");
		ConceptNode cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Structure");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Scope");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Classes");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Functions");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Defining Classes");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Defining Functions");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Returning Values");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Parameters");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Using Objects");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Calling Functions");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Arguments");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Catching Return Values");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Expressions");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Data");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Assignment");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Literals");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Math Expressions");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Int");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Float");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Variables");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Boolean Expressions");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Boolean");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Data Types");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Control");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("If Statement");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Loops");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("While Loop");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("For Loop");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Sequence Types");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("String");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("List");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
				
				
		clList.add(new IDLink("Intro CS","Structure"));
		clList.add(new IDLink("Intro CS","Data"));
		clList.add(new IDLink("Intro CS","Control"));
		clList.add(new IDLink("Structure","Scope"));
		clList.add(new IDLink("Structure","Classes"));
		clList.add(new IDLink("Structure","Functions"));
		clList.add(new IDLink("Structure","Expressions"));
		clList.add(new IDLink("Data","Literals"));
		clList.add(new IDLink("Data","Assignment"));
		clList.add(new IDLink("Data","Variables"));
		clList.add(new IDLink("Data","Data Types"));
		clList.add(new IDLink("Control","If Statement"));
		clList.add(new IDLink("Control","Loops"));
		clList.add(new IDLink("Scope","Functions"));
		clList.add(new IDLink("Scope","Classes"));
		clList.add(new IDLink("Classes","Defining Classes"));
		clList.add(new IDLink("Classes","Functions"));
		clList.add(new IDLink("Classes","Using Objects"));
		clList.add(new IDLink("Functions","Defining Functions"));
		clList.add(new IDLink("Functions","Calling Functions"));
		clList.add(new IDLink("Expressions","Boolean Expressions"));
		clList.add(new IDLink("Expressions","Math Expressions"));
		clList.add(new IDLink("Expressions","Variables"));
		clList.add(new IDLink("Expressions","Literals"));
		clList.add(new IDLink("Assignment","Expressions"));
		clList.add(new IDLink("Assignment","Literals"));
		clList.add(new IDLink("Assignment","Variables"));
		clList.add(new IDLink("Data Types","Boolean"));
		clList.add(new IDLink("Data Types","Int"));
		clList.add(new IDLink("Data Types","Float"));
		clList.add(new IDLink("Data Types","String"));
		clList.add(new IDLink("Data Types","Sequence Types"));
		clList.add(new IDLink("If Statement","Boolean Expressions"));
		clList.add(new IDLink("Loops","While Loop"));
		clList.add(new IDLink("Loops","For Loop"));
		clList.add(new IDLink("Defining Classes","Defining Functions"));
		clList.add(new IDLink("Defining Functions","Returning Values"));
		clList.add(new IDLink("Defining Functions","Parameters"));
		clList.add(new IDLink("Using Objects","Calling Functions"));
		clList.add(new IDLink("Calling Functions","Arguments"));
		clList.add(new IDLink("Calling Functions","Catching Return Values"));
		clList.add(new IDLink("Math Expressions","Int"));
		clList.add(new IDLink("Math Expressions","Float"));
		clList.add(new IDLink("Boolean Expressions","Boolean"));
		clList.add(new IDLink("Sequence Types","String"));
		clList.add(new IDLink("Sequence Types","List"));
		clList.add(new IDLink("While Loop","Boolean Expressions"));
		clList.add(new IDLink("For Loop","Sequence Types"));
		
		NodesAndIDLinks inputNodesAndLinks = new NodesAndIDLinks(cnList,clList);
		
		ConceptGraph inputGraph = new ConceptGraph(inputNodesAndLinks);
		ConceptGraph inputTree = inputGraph.graphToTree();
		return inputTree.buildNodesAndLinks();
	}

	public static NodesAndIDLinks createSimpleSelection(){
		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<IDLink> clList = new ArrayList<IDLink>();
		
		Concept c = new ConceptImpl("Intro CS");
		ConceptNode cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Structure");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Expressions");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Data");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Assignment");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Boolean Expressions");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Boolean");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Data Types");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Control");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("If Statement");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Loops");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("While Loop");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("test_question6_4_2");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);		
		c = new ConceptImpl("test_question6_4_1");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
				
		clList.add(new IDLink("Intro CS","Structure"));
		clList.add(new IDLink("Intro CS","Data"));
		clList.add(new IDLink("Intro CS","Control"));
		clList.add(new IDLink("Structure","Expressions"));
		clList.add(new IDLink("Data","Assignment"));
		clList.add(new IDLink("Data","Data Types"));
		clList.add(new IDLink("Control","If Statement"));
		clList.add(new IDLink("Control","Loops"));
		clList.add(new IDLink("Expressions","Boolean Expressions"));
		clList.add(new IDLink("Assignment","Expressions"));
		clList.add(new IDLink("Data Types","Boolean"));
		clList.add(new IDLink("If Statement","Boolean Expressions"));
		clList.add(new IDLink("Loops","While Loop"));
		clList.add(new IDLink("Boolean Expressions","Boolean"));
		clList.add(new IDLink("While Loop","Boolean Expressions"));
		
		clList.add(new IDLink("If Statement","test_question6_4_1"));
		clList.add(new IDLink("If Statement","test_question6_4_2"));
		
		NodesAndIDLinks inputNodesAndLinks= new NodesAndIDLinks(cnList,clList);
		return inputNodesAndLinks;
	}
	
	public static NodesAndIDLinks createSelection(){
		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<IDLink> clList = new ArrayList<IDLink>();
		
		Concept c = new ConceptImpl("Intro CS");
		ConceptNode cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Structure");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Expressions");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Data");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Assignment");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Boolean Expressions");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Boolean");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Data Types");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Control");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("If Statement");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("Loops");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("While Loop");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("test_question6_4_3");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("test_question6_4_2");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);		
		c = new ConceptImpl("test_question6_4_1");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("test_question6_3_1");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("test_question6_2_1");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
		c = new ConceptImpl("test_question6_1_1");
		cn = new ConceptNode(c, c.getConceptTitle());
		cnList.add(cn);
				
		clList.add(new IDLink("Intro CS","Structure"));
		clList.add(new IDLink("Intro CS","Data"));
		clList.add(new IDLink("Intro CS","Control"));
		clList.add(new IDLink("Structure","Expressions"));
		clList.add(new IDLink("Data","Assignment"));
		clList.add(new IDLink("Data","Data Types"));
		clList.add(new IDLink("Control","If Statement"));
		clList.add(new IDLink("Control","Loops"));
		clList.add(new IDLink("Expressions","Boolean Expressions"));
		clList.add(new IDLink("Assignment","Expressions"));
		clList.add(new IDLink("Data Types","Boolean"));
		clList.add(new IDLink("If Statement","Boolean Expressions"));
		clList.add(new IDLink("Loops","While Loop"));
		clList.add(new IDLink("Boolean Expressions","Boolean"));
		clList.add(new IDLink("While Loop","Boolean Expressions"));
		
		clList.add(new IDLink("Boolean Expressions","test_question6_1_1"));
		clList.add(new IDLink("Boolean Expressions","test_question6_2_1"));
		clList.add(new IDLink("Expressions","test_question6_3_1"));
		clList.add(new IDLink("If Statement","test_question6_4_1"));
		clList.add(new IDLink("If Statement","test_question6_4_2"));
		clList.add(new IDLink("If Statement","test_question6_4_3"));
		
		NodesAndIDLinks inputNodesAndLinks= new NodesAndIDLinks(cnList,clList);
		
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		NodesAndIDLinks inputNodesAndLinks = null;
//        try {
//			inputNodesAndLinks = mapper.readValue(new File("test/testdata/Selection.json"), NodesAndIDLinks.class);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		return inputNodesAndLinks;
	}
	
	
}

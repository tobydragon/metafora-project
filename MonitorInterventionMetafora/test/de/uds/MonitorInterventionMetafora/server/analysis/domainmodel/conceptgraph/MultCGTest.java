package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.output.NamedGraph;

public class MultCGTest {

	ConceptGraph cgA;
	ConceptGraph cgB;
	ConceptGraph cgC;
	NamedGraph studA;
	NamedGraph studB;
	NamedGraph studC;
	List students;
	
	ObjectMapper mapper = new ObjectMapper();
	 
	String outputLocation = "MultStudentsOutput.json";
	
	public MultCGTest(){
		setCG();
		studA = new NamedGraph("StudentA", cgA);
		studB = new NamedGraph("StudentB", cgB);
		studC = new NamedGraph("StudentC", cgC);
		students = new ArrayList();
		students.add(studA);
		students.add(studB);
		students.add(studC);
		
		try {
			//writes JSON to file
			mapper.writeValue(new File(outputLocation), this.students);
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
	
	public String toString(){
		return students+" ";
	}
	public void setCG(){
		cgA = makeSimple();
		System.out.println(cgA);
		cgB = makeSimple();
		cgC = makeSimple();
	}
	
	public ConceptGraph makeSimple(){
		List<ConceptNode> cnList = new ArrayList<ConceptNode>();
		List<IDLink> linkList = new ArrayList<IDLink>(); 
		
		Concept c = new ConceptImpl("A");
		ConceptNode cn = new ConceptNode(c);
		cn.setActualComp(Math.random());
		cnList.add(cn);
		c = new ConceptImpl("B");
		cn = new ConceptNode(c);
		cn.setActualComp(Math.random());
		cnList.add(cn);
		c = new ConceptImpl("C");
		cn = new ConceptNode(c);
		cn.setActualComp(Math.random());
		cnList.add(cn);
		c = new ConceptImpl("D");
		cn = new ConceptNode(c);
		cn.setActualComp(Math.random());
		cnList.add(cn);
		c = new ConceptImpl("E");
		cn = new ConceptNode(c);
		cn.setActualComp(Math.random());
		cnList.add(cn);
		
		IDLink link = new IDLink("A","B");
		linkList.add(link);
		link = new IDLink("A","C");
		linkList.add(link);
		link = new IDLink("B","D");
		linkList.add(link);
		link = new IDLink("D","E");
		linkList.add(link);
		link = new IDLink("C","E");
		linkList.add(link);
		
		NodesAndIDLinks lists = new NodesAndIDLinks(cnList, linkList);
		return new ConceptGraph(lists);
	}
	
	public static void main(String args[]){
		MultCGTest myTest = new MultCGTest();
		System.out.println(myTest);
	}
}

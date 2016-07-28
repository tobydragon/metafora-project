package de.uds.MonitorInterventionMetafora.server.json;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.NodesAndIDLinks;


public class JsonExportTest {
	 ObjectMapper mapper;
	 NodesAndIDLinks lists;
	 
	 String outputLocation = "CarrieJsonGraph.json";

	@Before
	public void setUp() throws Exception {
		lists = StructureCreationLibrary.createDomainModel();
		mapper = new ObjectMapper();
	}

	@After
	public void tearDown() throws Exception {
		lists = null;
		this.mapper = null;
	}
	
	@Test
	public void JSONInputMatchOutputSimpleTest() {
		//Populates lists with the simple tree
		lists = StructureCreationLibrary.createSimple();
		try {
			//writes JSON to file
			mapper.writeValue(new File(outputLocation), this.lists);
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
		
		String jsonString = "";
		//reads json from file
		try {
			for (String line : Files.readAllLines(Paths.get(outputLocation))) {
			    jsonString += line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int numID = jsonString.split("id").length-1;
		jsonString = new String(jsonString);
		int numLink = jsonString.split("parent").length-1;
		
		Assert.assertEquals(4, numID);
		Assert.assertEquals(3, numLink);
		
	}

	@Test
	public void JSONInputMatchOutputMediumTest() {
		//Populates lists with the simple tree
		lists = StructureCreationLibrary.createMedium();
		try {
			//writes JSON to file
			mapper.writeValue(new File(outputLocation), this.lists);
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
		
		String jsonString = "";
		//reads json from file
		try {
			for (String line : Files.readAllLines(Paths.get(outputLocation))) {
			    jsonString += line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int numID = jsonString.split("id").length-1;
		jsonString = new String(jsonString);
		int numLink = jsonString.split("parent").length-1;
		
		Assert.assertEquals(7, numID);
		Assert.assertEquals(6, numLink);
		
	}
	
	@Test
	public void JSONInputMatchOutputComplexTest() {
		//Populates lists with the simple tree
		lists = StructureCreationLibrary.createComplex();
		try {
			//writes JSON to file
			mapper.writeValue(new File(outputLocation), this.lists);
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
		
		String jsonString = "";
		//reads json from file
		try {
			for (String line : Files.readAllLines(Paths.get(outputLocation))) {
			    jsonString += line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int numID = jsonString.split("id").length-1;
		jsonString = new String(jsonString);
		int numLink = jsonString.split("parent").length-1;
		
		Assert.assertEquals(13, numID);
		Assert.assertEquals(12, numLink);
		
	}
	
	@Test
	public void JSONInputMatchOutputSuperComplexTest() {
		//Populates lists with the simple tree
		lists = StructureCreationLibrary.createSuperComplex();
		try {
			//writes JSON to file
			mapper.writeValue(new File(outputLocation), this.lists);
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
		
		String jsonString = "";
		//reads json from file
		try {
			for (String line : Files.readAllLines(Paths.get(outputLocation))) {
			    jsonString += line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int numID = jsonString.split("id").length-1;
		jsonString = new String(jsonString);
		int numLink = jsonString.split("parent").length-1;
		
		Assert.assertEquals(24, numID);
		Assert.assertEquals(23, numLink);
		
	}
	
	@Test
	public void JSONNodeNumSuperComplexTest() {
		//Populates lists with the simple tree
		lists = StructureCreationLibrary.createSuperComplex();
		try {
			//writes JSON to file
			mapper.writeValue(new File(outputLocation), this.lists);
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
		
		String jsonString = "";
		//reads json from file
		try {
			for (String line : Files.readAllLines(Paths.get(outputLocation))) {
			    jsonString += line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] temp = jsonString.split("\"id\":\"A");
		int numIDA = temp.length-1;
		temp = jsonString.split("\"id\":\"B");
		int numIDB = temp.length-1;
		temp = jsonString.split("\"id\":\"C");
		int numIDC = temp.length-1;
		temp = jsonString.split("\"id\":\"D");
		int numIDD = temp.length-1;
		temp = jsonString.split("\"id\":\"E");
		int numIDE = temp.length-1;
		temp = jsonString.split("\"id\":\"F");
		int numIDF = temp.length-1;
		
		Assert.assertEquals(1, numIDA);
		Assert.assertEquals(1, numIDB);
		Assert.assertEquals(2, numIDC);
		Assert.assertEquals(3, numIDD);
		Assert.assertEquals(7, numIDE);
		Assert.assertEquals(10, numIDF);
		
	}
	
	@Test
	public void exportSimpleSelectionTest(){
		//Populates lists with the simple tree
				lists = StructureCreationLibrary.createSimpleSelection();
				try {
					//writes JSON to file
					mapper.writeValue(new File(outputLocation), this.lists);
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
				
				String jsonString = "";
				//reads json from file
				try {
					for (String line : Files.readAllLines(Paths.get(outputLocation))) {
					    jsonString += line;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int numID = jsonString.split("id").length-1;
				jsonString = new String(jsonString);
				int numLink = jsonString.split("parent").length-1;
				
				Assert.assertEquals(14, numID);
				Assert.assertEquals(17, numLink);	
	}
	
	@Test
	public void exportSimpleSelectionIDTest(){
		//Populates lists with the simple tree
				lists = StructureCreationLibrary.createSimpleSelection();
				try {
					//writes JSON to file
					mapper.writeValue(new File(outputLocation), this.lists);
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
				
				String jsonString = "";
				//reads json from file
				try {
					for (String line : Files.readAllLines(Paths.get(outputLocation))) {
					    jsonString += line;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String[] temp = jsonString.split("\"id\":\"test");
				int numIDTest = temp.length-1;
				temp = jsonString.split("\"id\":\"Boolean\"");
				int numIDB = temp.length-1;
				temp = jsonString.split("\"id\":\"Boolean Expression");
				int numIDExp = temp.length-1;
				
				Assert.assertEquals(2, numIDTest);
				Assert.assertEquals(1, numIDB);
				Assert.assertEquals(1, numIDExp);				
	}
	
	public static void main(String[] args){
		NodesAndIDLinks lists = StructureCreationLibrary.createSimpleSelection();
		ObjectMapper mapper = new ObjectMapper();
		try {
			//writes JSON to file
			mapper.writeValue(new File("selectionOutput.json"), lists);
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

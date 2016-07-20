package de.uds.MonitorInterventionMetafora.server.json;

import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.NodesAndIDLinks;

public class JsonExportTest {
	 ObjectMapper mapper;
	 NodesAndIDLinks lists;

	@Before
	public void setUp() throws Exception {
		lists = JsonCreationLibrary.createMedium();
		mapper = new ObjectMapper();
	}

	@After
	public void tearDown() throws Exception {
		lists = null;
		this.mapper = null;
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

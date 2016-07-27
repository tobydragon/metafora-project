package de.uds.MonitorInterventionMetafora.server.json;


import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.NodesAndIDLinks;

public class JsonImportTest {
	Logger logger = Logger.getLogger(this.getClass());


	@Test
	public void ReadSimpleFromFileTest() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
        try {
			NodesAndIDLinks lists = mapper.readValue(new File("test/testdata/ABCSimple.json"), NodesAndIDLinks.class);
			
			Assert.assertEquals(11, lists.getNodes().size());
			Assert.assertEquals(11, lists.getLinks().size());
//			logger.debug("\n"+lists);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
       
	}
	
	@Test 
	public void ReadSelectionJsonFromFileTest(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
        try {
			NodesAndIDLinks lists = mapper.readValue(new File("selection.json"), NodesAndIDLinks.class);
			
			Assert.assertEquals(33, lists.getNodes().size());
			Assert.assertEquals(32, lists.getLinks().size());
//			logger.debug("\n"+lists);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

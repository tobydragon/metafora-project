package de.uds.MonitorInterventionMetafora.server.json;


import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph.NodesAndIDLinks;

public class JsonImportTest {
	Logger logger = Logger.getLogger(this.getClass());


	@Test
	public void ReadSimpleFromFileTest() {
		ObjectMapper mapper = new ObjectMapper();
		
        try {
        	NodesAndIDLinks lists = JsonCreationLibrary.createSimple();
        	logger.debug(mapper.writeValueAsString(lists));
//			NodesAndIDLinks lists = mapper.readValue(new File("CarrieJsonGraph.json"), NodesAndIDLinks.class);
//			
//			Assert.assertEquals(11, lists.getNodes().size());
//			Assert.assertEquals(12, lists.getLinks().size());
//			logger.debug(lists);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        	
       
	}

}

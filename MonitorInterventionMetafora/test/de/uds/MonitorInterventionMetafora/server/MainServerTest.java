package de.uds.MonitorInterventionMetafora.server;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

import de.uds.MonitorInterventionMetafora.shared.monitor.UpdateResponse;

public class MainServerTest {
	Logger logger = Logger.getLogger(this.getClass());

	@Test
	public void requestDataFromFileTest() {
		MainServer server = new MainServer();
		UpdateResponse response = server.requestDataFromFile("SimpleRunestone.xml");
		logger.debug("Actions returned:" +response.getActions().size());
		
	}

}

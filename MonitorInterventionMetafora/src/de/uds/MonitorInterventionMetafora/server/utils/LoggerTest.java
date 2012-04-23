package de.uds.MonitorInterventionMetafora.server.utils;

import junit.framework.TestCase;

public class LoggerTest extends TestCase {
	
	public void testLoggerTest(){
		Logger logger = Logger.getLogger(this.getClass());
		
		logger.debug("debug msg");
		logger.info("info msg");
		logger.warn("warn msg");

	}

}

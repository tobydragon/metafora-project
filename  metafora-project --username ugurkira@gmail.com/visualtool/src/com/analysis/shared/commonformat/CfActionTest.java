package com.analysis.shared.commonformat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.analysis.server.commonformatparser.CfActionParser;



import junit.framework.TestCase;

public class CfActionTest extends TestCase {
	Log logger = LogFactory.getLog(CfActionTest.class);
	
	public void testCfActionCreate(){
		CfAction cfAction = CfActionParser.getTestableInstance();
		logger.debug("\n" + CfActionParser.toXml(cfAction).toString());
	}

}

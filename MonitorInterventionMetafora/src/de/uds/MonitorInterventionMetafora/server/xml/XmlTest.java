package de.uds.MonitorInterventionMetafora.server.xml;

import de.uds.MonitorInterventionMetafora.shared.utils.GeneralUtil;
import junit.framework.TestCase;

public class XmlTest extends TestCase {
	
//	public void testXmlConfigParser(){
//		String connectionConfigFilepath = "conf/xmpp-connect-settings.xml";
//		XmlConfigParser connectionParser = new XmlConfigParser(connectionConfigFilepath);
//		String connectionName = connectionParser.getConfigValue("connection-name");
//		String userName = connectionParser.getConfigValue("username");
//		String password = connectionParser.getConfigValue("password");
//		String chatroom = connectionParser.getConfigValue("chatroom");
//		String alias = connectionParser.getConfigValue("alias");
//		String device = connectionParser.getConfigValue("device");
//		
//		assertEquals("LasadCommand", connectionName);
//		assertEquals("command@conference.metafora.ku-eichstaett.de", chatroom);
//	}
	
//	public void testXmlConfigParserFragment(){
//		String connectionConfigFilepath = GeneralUtil.getAplicationResourceDirectory()+"conffiles/metafora/details/agents/types/xmpp/xmpp-connect-settings.xml";
//		XmlConfigParser connectionParser = new XmlConfigParser(connectionConfigFilepath);
//		connectionParser = connectionParser.getfragmentById("xmpp-channel-setting", "channelid", "command");
//		String connectionName = connectionParser.getConfigValue("connection-name");
//		String chatroom = connectionParser.getConfigValue("chatroom");
//		assertEquals("LasadCommand", connectionName);
//		assertEquals("command@conference.metafora.ku-eichstaett.de", chatroom);
//	}
	
	public void testRemoteXmlFile(){
		XmlFragment xmlFragment = XmlFragment.getFragmentFromRemoteFile("http://metafora.ku-eichstaett.de/logs/analysisRequest1.xml");
		System.out.println(xmlFragment.toString());
	}

}

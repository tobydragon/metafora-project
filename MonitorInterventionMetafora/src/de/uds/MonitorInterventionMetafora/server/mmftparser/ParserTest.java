package de.uds.MonitorInterventionMetafora.server.mmftparser;

import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import junit.framework.TestCase;

public class ParserTest extends TestCase{

	
	public void testSuggestedMessageParse(){
		System.out.println(SuggestedMessagesModelParserForServer.toXml(SuggestedMessagesModelParserForServer.fromXml(XmlFragment.getFragmentFromLocalFile("war/resources/feedback/peer-messages_en.xml"))).toString());
	}
}

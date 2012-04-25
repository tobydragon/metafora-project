package de.uds.MonitorInterventionMetafora.server.monitor;

import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfActionParser;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import junit.framework.TestCase;

public class HistoryRequesterTest extends TestCase {
	
	public void testAnswerMessage(){
		XmlFragment xmlFragment = XmlFragment.getFragmentFromLocalFile("war/conffiles/xml/message/HistoryRequestAnswer.xml");
		CfAction cfAction = CfActionParser.fromXml(xmlFragment);
		System.out.println(cfAction);
	}

}

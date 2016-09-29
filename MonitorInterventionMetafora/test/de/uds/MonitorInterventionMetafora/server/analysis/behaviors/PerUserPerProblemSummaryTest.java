package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.uds.MonitorInterventionMetafora.server.commonformatparser.CfInteractionDataParser;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;


public class PerUserPerProblemSummaryTest {
	
	@Test
	public void calcTimeTest() {
		long time1 = 1000; //1 second
		long time2 = PerUserPerProblemSummary.TIME_INTERVAL*1000; //3 minutes (180 seconds)
		long time3 = (PerUserPerProblemSummary.TIME_INTERVAL+10)*1000; //3 minutes 10 seconds (190 seconds)
		
		//Tests to see if it took under 3 minutes per question that it will return 179 seconds because of our timeInterval constant
		Assert.assertEquals(179, PerUserPerProblemSummary.calcTime(time1, time2));
		//Same test just also testing that it will invert the times if it is negative
		Assert.assertEquals(179, PerUserPerProblemSummary.calcTime(time2, time1));
		//Tests to see if it takes over 3 minutes, should return our standardTime of 30 seconds
		Assert.assertEquals(30,PerUserPerProblemSummary.calcTime(time1, time3));
		
	}
	
	@Test
	public void calculateTimeTest() {
		
		String inputXML = "test/testdata/timeTest.xml";
		
		//Get behaviors from runsetone xml
		XmlFragment runestoneFrag = XmlFragment.getFragmentFromLocalFile(inputXML);
		CfInteractionData testCf = CfInteractionDataParser.fromRunestoneXml(runestoneFrag);
				
		List<CfAction> allActions = testCf.getCfActions();
		
		Assert.assertEquals(456, PerUserPerProblemSummary.calculateTime(allActions));
		
	}
}


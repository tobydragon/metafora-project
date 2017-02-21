package de.uds.MonitorInterventionMetafora.server.analysis.behaviors.output;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import de.uds.MonitorInterventionMetafora.server.analysis.behaviors.ManualGradedResponse;

public class CSVReaderTest {
	
	@Test
	public void createQuestionsTest(){
		String file = "test/testdata/DataCSVExample.csv";
		CSVReader readfile = new CSVReader(file);
		ArrayList<ManualGradedResponse> mgrList = readfile.getManualGradedResponses();
		Assert.assertEquals(25*9, mgrList.size());
		ManualGradedResponse testQ = new ManualGradedResponse("Week 8 Exercises",6,6,"stu1");
		Assert.assertEquals(testQ.getScore(), mgrList.get(0).getScore(),0);
		Assert.assertEquals(testQ.getRealScore(), mgrList.get(0).getRealScore(),0);
		Assert.assertEquals(testQ.getMaxScore(), mgrList.get(0).getMaxScore(),0);
		Assert.assertEquals(testQ.getStudentID(), mgrList.get(0).getStudentID());
		Assert.assertEquals(testQ.getConceptTitle(), mgrList.get(0).getConceptTitle());
	}
}

package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class SubChapterTest {
	
	@Test
	public void parseFileForQuestionsTest() throws FileNotFoundException{
		List<Question> questions = SubChapter.parseFileForQuestions("war/conffiles/domainfiles/thinkcspy/_sources/MoreAboutIteration/2DimensionalIterationImageProcessing.rst");
		Assert.assertEquals(4, questions.size());
	}

}

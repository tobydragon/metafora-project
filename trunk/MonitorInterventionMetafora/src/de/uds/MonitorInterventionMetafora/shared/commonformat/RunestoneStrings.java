package de.uds.MonitorInterventionMetafora.shared.commonformat;

import de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.runestonetext.QuestionType;

public class RunestoneStrings {

	public static final String ROWS_STRING = "rows";
	public static final String ROW_STRING = "row";
	public static final String TIMESTAMP_STRING = "timestamp";
	public static final String SID_STRING = "sid";
	public static final String ACT_STRING = "act";
	public static final String DIV_ID_STRING = "div_id";
	public static final String EVENT_STRING = "event";
	
	
	public static final String MCHOICE_STRING = QuestionType.MULT_CHOICE.getLogString();
	public static final String PARSONS_STRING = QuestionType.DRAG_AND_DROP.getLogString();
	public static final String CORRECT_STRING = "Correct";

	public static final String TIME_SPENT_STRING = "TIME_SPENT";
	public static final String IS_EVER_CORRECT_STRING = "IS_EVER_CORRECT";
	public static final String IS_ASSESSABLE_STRING = "IS_ASSESSABLE";
	public static final String TIMES_FALSE_STRING = "TIMES_FALSE";
	public static final String FALSE_ENTRIES_STRING = "FALSE_ENTRIES";
	public static final String OBJECT_ID_STRING = "OBJECT_ID";
	public static final String INDICATOR_STRING = "INDICATOR";
	public static final String ORIGINATOR_STRING = "ORIGINATOR";
	public static final String TYPE_STRING = "TYPE";
	
	public static final String TOTAL_ATTEMPTED_STRING = "TOTAL_ATTEMPTED";
	public static final String TOTAL_NUMBER_NOT_ASSESSABLE_STRING = "TOTAL_NUMBER_NOT_ASSESSABLE";
	public static final String TOTAL_NOT_ASSESSABLE_ANSWERS_STRING = "TOTAL_NOT_ASSESSABLE_ANSWERS";
	public static final String TOTAL_NUMBER_CORRECT_STRING = "TOTAL_NUMBER_CORRECT";
	public static final String TOTAL_CORRECT_ANSWERS_STRING = "TOTAL_CORRECT_ANSWERS";
	public static final String TOTAL_NUMBER_INCORRECT_STRING = "TOTAL_NUMBER_INCORRECT";
	public static final String TOTAL_INCORRECT_ANSWERS_STRING = "TOTAL_INCORRECT_ANSWERS";
	public static final String TOTAL_NUMBER_OTHERS_STRING = "TOTAL_NUMBER_OTHERS";
	public static final String TOTAL_TIME_SPENT_STRING = "TOTAL_TIME_SPENT";
	
	public static final String TOTAL_CORRECT_USERS_STRING = "TOTAL_CORRECT_USERS";
	public static final String TOTAL_NUMBER_BOTH_STRING = "TOTAL_NUMBER_BOTH";
	public static final String TOTAL_BOTH_USERS_STRING = "TOTAL_BOTH_USERS";
	public static final String TOTAL_INCORRECT_USERS_STRING = "TOTAL_INCORRECT_USERS";
}

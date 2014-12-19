package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;

public class PerUserPerProblemSummary {
	private String user;
	private long time;
	private boolean isCorrect;
	private boolean assessable;
	private int numberTimesFalse;
	private String falseEntries;
	private String objectId;
	private String type;
	
	public PerUserPerProblemSummary(String user, long time, boolean isCorrect, boolean assessable,
			int numberTimesFalse, String falseEntries, String objectId,
			String type) {
		super();
		this.user = user;
		this.time = time;
		this.isCorrect = isCorrect;
		this.assessable = assessable;
		this.numberTimesFalse = numberTimesFalse;
		this.falseEntries = falseEntries;
		this.objectId = objectId;
		this.type = type;
	}
	
	public BehaviorInstance buildBehaviorInstance(){
		List <CfProperty >instanceProperties = new Vector<CfProperty>();
		instanceProperties.add(new CfProperty(RunestoneStrings.TIME_SPENT_STRING, String.valueOf(time)));
		instanceProperties.add(new CfProperty(RunestoneStrings.IS_EVER_CORRECT_STRING,String.valueOf(isCorrect)));
		instanceProperties.add(new CfProperty(RunestoneStrings.IS_ASSESSABLE_STRING,String.valueOf(assessable)));
		instanceProperties.add(new CfProperty(RunestoneStrings.TIMES_FALSE_STRING, String.valueOf(numberTimesFalse)));
		instanceProperties.add(new CfProperty(RunestoneStrings.FALSE_ENTRIES_STRING, falseEntries));
		instanceProperties.add(new CfProperty(RunestoneStrings.OBJECT_ID_STRING, objectId));
		instanceProperties.add(new CfProperty(RunestoneStrings.TYPE_STRING, type));
		
		List <String> userList = new Vector<String>();
		userList.add(user);
		
		BehaviorInstance instance = new BehaviorInstance(BehaviorType.PER_USER_PER_OBJECT_SUMMARY, userList, instanceProperties);
		return instance;
	}

	
	
	public String getUser() {
		return user;
	}

	public long getTime() {
		return time;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public boolean getAssessable() {
		return assessable;
	}
	
	public int getNumberTimesFalse() {
		return numberTimesFalse;
	}

	public String getFalseEntries() {
		return falseEntries;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getType() {
		return type;
	}
	
}

package de.uds.MonitorInterventionMetafora.shared.datamodels.attributes;

import com.allen_sauer.gwt.log.client.Log;

public enum BehaviorType {
	
	//DL
	STRUGGLE(-1),
	
	NEW_IDEA_NOT_DISCUSSED(-1),
	NEW_IDEA_DISCUSSED(1),
	
	MEMBER_NOT_PLANNING (-1),
	ALL_MEMBERS_PLANNING (1),
	
	DISTINCT_IDEAS_NOT_SEPERATED (-1), 
	
	//ME
	WORK_DIVERGES_FROM_PLAN (-1),
	MEMBER_NOT_DISCUSSING (-1),
	
	MEMBERS_NOT_RESPONDING (-1),
	MEMBERS_RESPONDING (1),
	
	STRUGGLE_NO_DISCUSSION (-1),
	DISAGREEMENT (-1),
	
	DIVERGENCE_WITHOUT_CONVERGENCE_NOT_DISCUSSED(-1),
	DIVERGENCE_AND_CONVERGENCE(1),
	
	//PF
	SOLUTIONS_NOT_DISCUSSED(-1),
	MULTIPLE_SOLUTIONS_DSICUSSED(1),
	
	//GR
	PLAN_NOT_UPDATED_WITH_WORK(-1),
	NOT_USING_ATTITUDES_OR_ROLES(-1),
	DISCUSSIONS_HELD_IN_APPROPRIATE_SPACES(-1)

	
	;
	
	public final int valence;
	
	private BehaviorType(int valence){
		this.valence = valence;
	}
	
	//catch errors and return null, for use when null is acceptable
	public static BehaviorType getFromString(String str){
		try {
			return valueOf(str);
		}
		catch (Exception e){
			if (str != null){
				Log.warn ("[BehaviorType.getFromString] Bad input, return null for String: " + str);
			}
			return null;
		}
	}

}

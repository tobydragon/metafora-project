package de.uds.MonitorInterventionMetafora.shared.datamodels.attributes;

import com.allen_sauer.gwt.log.client.Log;

public enum BehaviorType {
	NOWORK, NEW_IDEA_NOT_DISCUSSED;
	
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

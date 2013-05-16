package de.uds.MonitorInterventionMetafora.shared.suggestedmessages;

import com.allen_sauer.gwt.log.client.Log;

public enum L2L2category {
	DISTRIBUTED_LEADERSHIP, MUTUAL_ENGAGEMENT, PEER_FEEDBACK, REFLECTION;

	//catch errors and return null, for use when null is acceptable
	public static L2L2category getFromString(String str){
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

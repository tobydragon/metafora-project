package de.uds.MonitorInterventionMetafora.shared.datamodels.attributes;

public enum BehaviorType {
	NOWORK, NEW_IDEA_NOT_DISCUSSED;
	
	public static BehaviorType getFromString(String str){
		try {
			return valueOf(str);
		}
		catch (Exception e){
			//System.out.println("[FilterElementType.getFromString] bad string input");
			return null;
		}
	}

}

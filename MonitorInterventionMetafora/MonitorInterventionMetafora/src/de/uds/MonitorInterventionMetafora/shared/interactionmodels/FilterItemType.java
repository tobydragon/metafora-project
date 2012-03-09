package de.uds.MonitorInterventionMetafora.shared.interactionmodels;

public enum FilterItemType {
	ACTION_TYPE,
	USER,
	OBJECT,
	CONTENT;
	
	public static FilterItemType getFromString(String str){
		try {
			return valueOf(str);
		}
		catch (Exception e){
			System.out.println("[FilterElementType.getFromString] bad string input");
			return null;
		}
	}

}

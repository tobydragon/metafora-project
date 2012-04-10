package de.uds.MonitorInterventionMetafora.shared.datamodels.attributes;

public enum FilterItemType {
	ACTION,
	ACTION_TYPE,
	USER,
	OBJECT,
	CONTENT;
	
	public static FilterItemType getFromString(String str){
		try {
			return valueOf(str);
		}
		catch (Exception e){
			//System.out.println("[FilterElementType.getFromString] bad string input");
			return null;
		}
	}

}

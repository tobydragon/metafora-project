package de.uds.MonitorInterventionMetafora.shared.datamodels.attributes;

public enum FilterAttributeName {
	ID,
	CLASSIFICATION,
	TYPE,
	ROLE,
	TIME,
	SUCCEED,
	;
	
	public  static FilterAttributeName getFromString(String str){
		try {
			return valueOf(str);
		}
		catch (Exception e){
			//System.out.println("[FilterElementType.getFromString] bad string input");
			return null;
		}
		
	}

}

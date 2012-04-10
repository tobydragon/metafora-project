package de.uds.MonitorInterventionMetafora.shared.datamodels.attributes;

public enum OperationType {
	EQUALS,
	CONTAINS,
	OCCUREDWITHIN
	;
	
	public  static OperationType getFromString(String str){
		try {
			return valueOf(str);
		}
		catch (Exception e){
			//System.out.println("[FilterElementType.getFromString] bad string input");
			return null;
		}
		
	}

}

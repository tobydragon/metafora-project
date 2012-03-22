package de.uds.MonitorInterventionMetafora.client.datamodels.attributes;

public enum OperationType {
	Equals,
	Contains
	;
	
	public  static OperationType getFromString(String str){
		try {
			return valueOf(str);
		}
		catch (Exception e){
			System.out.println("[FilterElementType.getFromString] bad string input");
			return null;
		}
		
	}

}

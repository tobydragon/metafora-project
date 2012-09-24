package de.uds.MonitorInterventionMetafora.shared.datamodels.attributes;

public enum OperationsCategory {
	STANDARD,
	TIME
	;
	
	public static OperationsCategory getCategoryFromPropertyName(String propertyName){
		if (propertyName.equalsIgnoreCase("TIME")){
			return TIME;
		}
		else {
			return STANDARD;
		}
	}

}

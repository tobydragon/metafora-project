package de.uds.MonitorInterventionMetafora.shared.datamodels.attributes;

public enum NotificationType {
	NOWORK;
	
	public static NotificationType getFromString(String str){
		try {
			return valueOf(str);
		}
		catch (Exception e){
			System.out.println("[FilterElementType.getFromString] bad string input");
			return null;
		}
	}

}

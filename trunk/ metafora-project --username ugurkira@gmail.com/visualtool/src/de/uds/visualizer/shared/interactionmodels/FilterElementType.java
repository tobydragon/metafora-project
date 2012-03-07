package de.uds.visualizer.shared.interactionmodels;

public enum FilterElementType {
	ACTION_TYPE,
	USER,
	OBJECT,
	CONTENT;
	
	public FilterElementType getFromString(String str){
		try {
			return valueOf(str);
		}
		catch (Exception e){
			System.out.println("[FilterElementType.getFromString] bad string input");
			return null;
		}
	}

}

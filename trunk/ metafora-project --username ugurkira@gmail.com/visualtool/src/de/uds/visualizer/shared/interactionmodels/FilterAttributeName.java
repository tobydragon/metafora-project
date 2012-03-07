package de.uds.visualizer.shared.interactionmodels;

public enum FilterAttributeName {
	ID,
	CLASSIFICATION,
	TYPE,
	CONTENT,
	TIME,
	SUCCEED,
	;
	
	public FilterAttributeName getFromString(String str){
		try {
			return valueOf(str);
		}
		catch (Exception e){
			System.out.println("[FilterElementType.getFromString] bad string input");
			return null;
		}
	}

}

package de.uds.MonitorInterventionMetafora.client.logger;

public enum ComponentType {
	ACTION_TABLE,
	FILTER_TABLE,
	MAIN_CONFIGURATION_TABLE,
	MAIN_TAB_PANEL,
	VIEW_TAB_PANEL,
	PIE_CHART,
	BAR_CHART,UPDATER_TOOLBAR,
	FILTER_MANAGEMENT_TOOLBAR,
	ACTION_FILTERER,
	FEEDBACK_OUTBOX,
	FEEDBACK_TEMPLATE_POOL
	
	;
	
	public static ComponentType getFromString(String str){
		try {
			return valueOf(str);
		}
		catch (Exception e){
			//System.out.println("[FilterElementType.getFromString] bad string input");
			return null;
		}
	}

}

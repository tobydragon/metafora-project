package de.uds.MonitorInterventionMetafora.server.analysis.notification;


import java.io.Serializable;
import java.util.List;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.NotificationType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorFilter;
import de.uds.MonitorInterventionMetafora.shared.utils.IndicatorFilterer;



public  interface Notification  {


	
	void sendNotification();
	String getDescriptionString();
	void setFilter(IndicatorFilter _filters);
	IndicatorFilter getFilter();
	void setType(NotificationType _type);
	NotificationType getType();
	boolean shouldFireNotification(List<CfAction> cfActions);
	CfAction createNotificationCfAction();
	
	
	
}

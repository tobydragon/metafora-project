package de.uds.MonitorInterventionMetafora.shared.notifications;

import java.util.List;

import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.NotificationType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorFilter;


public  interface Notification{


	void sendNotification();
	
	void setFilter(IndicatorFilter _filters);
	IndicatorFilter getFilter();
	void setType(NotificationType _type);
	NotificationType getType();
	boolean fireNotification();
	
	
	
}

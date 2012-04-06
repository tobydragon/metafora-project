package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.NotificationType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorFilter;
import de.uds.MonitorInterventionMetafora.shared.notifications.Notification;

public class NoWorkNotification extends TimerTask implements Notification {

	IndicatorFilter filter;
	
	public NoWorkNotification(){
		
		filter=new IndicatorFilter();
	}
	@Override
	public void sendNotification() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setFilter(IndicatorFilter _filter) {
		
	 filter=_filter;
		
	}
	@Override
	public IndicatorFilter getFilter() {
		
		return filter;
	}
	@Override
	public void setType(NotificationType _type) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public NotificationType getType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean fireNotification() {
		// TODO Auto-generated method stub
		return false;
	}
	

}

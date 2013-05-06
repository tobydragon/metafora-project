package de.uds.MonitorInterventionMetafora.server.analysis.notification;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.allen_sauer.gwt.log.client.Log;

import de.uds.MonitorInterventionMetafora.server.analysis.manager.AnalysisManager;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.NotificationType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;



public abstract class Notification  {

	private static final long serialVersionUID = -8109458748846339834L;
//	ActionFilter filter;
	NotificationType type;
		
	public abstract boolean shouldFireNotification(final List<CfAction> cfActions);
	
	//TODO: shouldn't be a static reference
	public void sendNotification() {
		AnalysisManager.getAnalysisManagerInstance().sendToAllAgents("Notification", createNotificationCfAction());
	}

	public String getDescriptionString() {
		String description= type.toString() + " notification for filterset:";
	
//		for(ActionPropertyRule rule :filter.getActionPropertyRules()){
//			description += " [" +rule.getDisplayText() + "] ";
//		}
		return description;
	}
	
	public CfAction createNotificationCfAction() {	
		CfContent content=new CfContent(getDescriptionString());

		content.addProperty(new CfProperty(CommonFormatStrings.INDICATOR_TYPE, CommonFormatStrings.ACTIVITY));
		content.addProperty(new CfProperty(CommonFormatStrings.TOOL, CommonFormatStrings.VISAULIZER_ANALYZER));
		content.addProperty(new CfProperty(CommonFormatStrings.ANALYSIS_TYPE, CommonFormatStrings.NOTIFICATION));
//		String color = filter.getColor();
//		if(color!=null && color!=""){
//			content.addProperty(new CfProperty(CommonFormatStrings.COLOR,color));
//		}

		final CfActionType cfActionType = new CfActionType(CommonFormatStrings.LANDMARK, 
				CommonFormatStrings.OTHER, CommonFormatStrings.TRUE);
		
		final CfUser _user=new CfUser("NotificationManager", "Manager");
		final List<CfUser> _users=new ArrayList<CfUser>();
		_users.add(_user);
		
		return new CfAction(System.currentTimeMillis(), cfActionType,_users, new ArrayList<CfObject>(),content);	
	}
	
	//takes a list of filters and creates a list of notifications, one for each filter
	public static List<Notification> createNotificationsFromFilters(List<ActionFilter> filters){
		List<Notification> notifications = new Vector<Notification>();
		
		for (ActionFilter filter : filters){
			String typeString = filter.getType();
			
			try {
				NotificationType type = NotificationType.valueOf(typeString);
				switch(type){
					case NOWORK:
						notifications.add(new NoWorkNotification(filter));
						break;
				}
			}
			catch (Exception e){
				Log.warn("[Notification.createNotificationsFromFilters] Missing or wrong type on filter: " + filter.toString());
			}
		}
		return notifications;
	}
	
	
	/////////// getters and setters  ///////////////////

//	public void setFilter(final ActionFilter _filter) {
//		filter=_filter;	
//	}
//	
//	public ActionFilter getFilter() {
//		return filter;
//	}
	
	public void setType(final NotificationType type) {
		this.type = type;
	}
	
	public NotificationType getType() {
		return type;
	}
	
	
	
	
}

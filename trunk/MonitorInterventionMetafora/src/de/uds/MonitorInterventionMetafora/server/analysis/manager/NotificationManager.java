package de.uds.MonitorInterventionMetafora.server.analysis.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//import com.google.gwt.user.client.Timer;


import de.uds.MonitorInterventionMetafora.server.analysis.notification.Notification;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorFilterer;

public class NotificationManager extends TimerTask{

	List<Notification> notifications;
	static NotificationManager instance; 
	Timer notificationManagerTimer;
	//List<NoWorkNotification_old> noWorkNotifications;
	List<CfAction> cfActions;
	IndicatorFilterer filterAgent;
	
	

	public NotificationManager(){
		
		cfActions=new ArrayList<CfAction>();
		filterAgent=new IndicatorFilterer();
		notificationManagerTimer=new Timer();
		notificationManagerTimer.schedule(this,0,120000);
		
		
	}
	
	
	public NotificationManager(List<Notification> _notifications){
		
		cfActions=new ArrayList<CfAction>();
		filterAgent=new IndicatorFilterer();
		notifications=new ArrayList<Notification>();
		notifications.addAll(_notifications);
		
		notificationManagerTimer=new Timer();
		notificationManagerTimer.schedule(this,0,120000);
		
		System.out.println("Notifcation M is initialized");
	}
	
	
	
public static NotificationManager getNotificationManagerInstance(List<Notification> _notifications){
		
		if(instance==null)
			instance= new NotificationManager(_notifications);
		return instance;
	}


public static NotificationManager getNotificationManagerInstance(){
	
	if(instance==null)
		instance= new NotificationManager();
	return instance;
}



@Override
public void run() {

	System.out.println("Notification manager runs and check notification");
	
	for(Notification _notification:notifications){
		
		
		if(_notification.shouldFireNotification(cfActions)){
			
		
			System.out.println("Notification found!!!! Sending message");
			_notification.sendNotification();
			
		}
		
		
		else{
			
			System.out.println("NoWorkNotification not found.Action is detected!!");
		}
		
		
	}				
}

public void setActions(List<CfAction> _actionList){

	cfActions=_actionList;
}







	
	
	
}

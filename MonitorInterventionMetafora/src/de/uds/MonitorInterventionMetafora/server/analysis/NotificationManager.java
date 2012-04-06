package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.utils.IndicatorFilterer;

public class NotificationManager implements Comparator<CfAction>  {

	static NotificationManager instance; 
	
	//List<NoWorkNotification_old> noWorkNotifications;
	List<CfAction> cfActions;
	IndicatorFilterer filterAgent;

	public NotificationManager(){
		
		cfActions=new ArrayList<CfAction>();
		//noWorkNotifications=new ArrayList<NoWorkNotification_old>();
		filterAgent=new IndicatorFilterer();
		
		
	}
	
	
	
	
public static NotificationManager getNotificationManagerInstance(){
		
		if(instance==null)
			instance= new NotificationManager();
		return instance;
	}


public void setActions(List<CfAction> _cfActions){
	
	cfActions.clear();
	cfActions.addAll(_cfActions);
	
	updateNotifications();
	
}



public void updateNotifications(){
	int index=0;
	
	
}

public void addNotification(NoWorkNotification_old _notification){
	
//	noWorkNotifications.add(_notification);	
}




@Override
public int compare(CfAction action0, CfAction action1) {
	int dif=(int) (action0.getTime()-action1.getTime());
	return dif;
}








	
	
	
}

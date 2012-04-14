package de.uds.MonitorInterventionMetafora.server.analysis.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


import de.uds.MonitorInterventionMetafora.server.analysis.notification.Notification;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfCommunicationListener;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.server.xml.XmlConfigParser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;




public class AnalysisManager {

	private Vector <CfCommunicationListener> allListeners;
	 List<CfAction> cfActions;
	NotificationManager notificationManager;
	static AnalysisManager instance; 
	String  notificationsSourceFile=GeneralUtil.getAplicationResourceDirectory()+"conffiles/toolconf/notifications.xml";

	
	
	public AnalysisManager(){
		
		
		allListeners=new Vector<CfCommunicationListener>();
		notificationManager=new NotificationManager(getNotifications());
	//	notificationManager=NotificationManager.getNotificationManagerInstance(_notifications);
		//notificationManager.run();
		cfActions=new ArrayList<CfAction>();
		System.out.println("NotificationManager added to AM");
		//cfActions.addAll(_cfActions);
	}
	
	
	public static AnalysisManager getAnalysisManagerInstance(){
		
		if(instance==null){
			instance= new AnalysisManager();

		}
		
		return instance;
	}
	


	public void setActions(List<CfAction> _actionList){
		
		
		cfActions=_actionList;
		notificationManager.setActions(cfActions);
			
	}
	
	public void register(CfCommunicationListener agent){
		allListeners.add(agent);
	}
	
	public void unregister(CfCommunicationListener agent){
		allListeners.remove(agent);
	}
	
	
	public void sendToAllAgents(String user, CfAction action){
		for (CfCommunicationListener agent : allListeners){
			agent.processCfAction(user, action);
		}
	}
	
	
	List<Notification>  getNotifications(){
		
		XmlConfigParser _configParser=new XmlConfigParser(notificationsSourceFile);
	
		return _configParser.getNotificationList();
	}

}

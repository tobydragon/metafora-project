package de.uds.MonitorInterventionMetafora.server.analysis.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


import de.uds.MonitorInterventionMetafora.server.analysis.notification.BehaviorIdentifier;
import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfCommunicationListener;
import de.uds.MonitorInterventionMetafora.server.mmftparser.ActionFilterParser;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;
import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.utils.ServerFormatStrings;




public class AnalysisManager {

	private Vector <CfCommunicationListener> allListeners;
	 List<CfAction> cfActions;
	NotificationManager notificationManager;

	static AnalysisManager instance; 
	String  notificationsSourceFile= GeneralUtil.getRealPath("conffiles/toolconf/notifications.xml");

	
	
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
	
	List<BehaviorIdentifier>  getNotifications(){
		return null;
//		XmlFragment configParser=new XmlFragment(notificationsSourceFile);
//		return BehaviorIdentifier.createNotificationsFromFilters(ActionFilterParser.listFromXml(configParser.accessChild(ServerFormatStrings.FILTERS)));
	}

}

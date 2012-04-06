package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


import de.uds.MonitorInterventionMetafora.server.cfcommunication.CfCommunicationListener;
import de.uds.MonitorInterventionMetafora.server.utils.ServerFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;




public class AnalysisManager {

	private Vector <CfCommunicationListener> allListeners;
	List<CfAction> cfActions;
	static AnalysisManager instance; 
	public AnalysisManager(){
		
		allListeners=new Vector<CfCommunicationListener>();
		
		cfActions=new ArrayList<CfAction>();
	}
	
	
	
	public static AnalysisManager getAnalysisManagerInstance(){
		
		if(instance==null)
			instance= new AnalysisManager();
		
		
		return instance;
	}
	
	
	public void setActions(List<CfAction> _actionList){
		
		
		cfActions=_actionList;
		
	//	if(_newAction.getCfContent().getProperty(ServerFormatStrings.IGNORED_LANDMARK)==null)
		//NotificationManager.getNotificationManagerInstance().setActions(cfActions);
		
	}
	
	public void register(CfCommunicationListener agent){
		allListeners.add(agent);
	}
	
	public void unregister(AnalysisListener agent){
		allListeners.remove(agent);
	}
	
	
	protected void sendToAllAgents(String user, CfAction action){
		for (CfCommunicationListener agent : allListeners){
			agent.processCfAction(user, action);
		}
	}
	

}

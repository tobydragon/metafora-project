package de.uds.MonitorInterventionMetafora.shared.interactionmodels;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uds.MonitorInterventionMetafora.server.analysis.notification.Notification;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
//import de.uds.MonitorInterventionMetafora.shared.utils.Logger;




public class Configuration implements Serializable{
	private static final long serialVersionUID = -4344917692123021453L;
	
//	Logger logger = Logger.getLogger(this.getClass());
	String name="";
	
	CfCommunicationMethodType communicationMethodType = CfCommunicationMethodType.file;
	String historyStartTime = MetaforaStrings.CURRENT_TIME;
	
	Map<String, ActionFilter> filters;
	
	public Configuration(){
		filters=new HashMap<String,ActionFilter>();
		//notifications=new ArrayList<Notification>();
	}
	
	public void setName(String _name){
		name=_name;
	}
	
	public String getName(){
		return name;
	}
	
	public void addActionFilter(ActionFilter af){
		filters.put(af.getName(), af);
	}
	
	public void addFilters(List<ActionFilter> actionfilters){
		filters.clear();
		for(ActionFilter filter:actionfilters){
			filters.put(filter.getName(), filter);
		}
	}
	
	public Map<String, ActionFilter>  getActionFilters(){	
		return filters;
	}
	
	public CfCommunicationMethodType getDataSouceType(){
		return communicationMethodType;
	}
	
	public void setDataSourceType(String typeString){
		try {
			communicationMethodType = CfCommunicationMethodType.valueOf(typeString);
		}
		catch (Exception e){
//			logger.error("Unable to set communication type, probably bad typeString=" + typeString + e.toString());
		}
	}
	
	public void setHistoryStartTime(String historyStartTime){
		this.historyStartTime=historyStartTime;
	}
	
	public String getHistoryStartTime(){
		return historyStartTime;
	}
	
	
	/*	
	public void addNotification(Notification _notification){
		notifications.add(_notification);
		
	}
	
	public void addNotifications(List<Notification> _notifications){
		notifications.clear();
		notifications.addAll(_notifications);
		
		
	}
	
	
	public List<Notification> getNotifications(){
		
		return notifications;
	}
	*/
	
}

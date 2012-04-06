package de.uds.MonitorInterventionMetafora.shared.interactionmodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uds.MonitorInterventionMetafora.shared.notifications.Notification;




public class Configuration implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4344917692123021453L;
	String name="";
	String _dataSourceType="";
	Map<String, IndicatorFilter> filters;
	List<Notification> notifications;
	
	public Configuration(){
		
		filters=new HashMap<String,IndicatorFilter>();
		notifications=new ArrayList<Notification>();
		
		
	}
	public void setName(String _name){

		name=_name;
	}
	
	public String getName(){
				
		return name;
	}
	
	public void addFilter(IndicatorFilter af){
		
		filters.put(af.name, af);
		//filters.add(af);
	}
	
	
public void addFilters(List<IndicatorFilter> _filters){
		
	filters.clear();
	for(IndicatorFilter _enty:_filters)
		filters.put(_enty.name, _enty);
	}
	
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
	
	public Map<String, IndicatorFilter>  getFilters(){
		
		return filters;
	}
	
	
	public String getActionSource(){
		
		return _dataSourceType;
	}
	
	public void setDataSource(String _source){
		
		_dataSourceType=_source;
	}
	
	
	
}

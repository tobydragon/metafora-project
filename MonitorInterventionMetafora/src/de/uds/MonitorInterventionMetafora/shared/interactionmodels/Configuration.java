package de.uds.MonitorInterventionMetafora.shared.interactionmodels;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.allen_sauer.gwt.log.client.Log;

import de.uds.MonitorInterventionMetafora.server.utils.ErrorUtil;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfCommunicationMethodType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;

public class Configuration implements Serializable{
	private static final long serialVersionUID = -4344917692123021453L;
	
	String name="";
	
	CfCommunicationMethodType communicationMethodType = CfCommunicationMethodType.file;
	XmppServerType defaultXmppServer = XmppServerType.METAFORA_TEST;
	
	boolean testServerMonitoring = true;
	boolean deployServerMonitoring = true;
	String historyStartTime = MetaforaStrings.CURRENT_TIME;
	
	Map<String, ActionFilter> filters;
	
	public Configuration(){
		this("No Name", "file", "CURRENT", "METAFORA_TEST", "false", "false", new Vector<ActionFilter>());
	}
	
	public Configuration(String name, String communicationMethodType, String historyStartTime, String defaultServer, String testServerMonitoring, String deployServerMonitoring, List<ActionFilter> filters){
		this.name = name;
		setDataSourceType(communicationMethodType);
		this.historyStartTime = historyStartTime;
		
		setTestServerMonitoring (testServerMonitoring);
		setDeployServerMonitoring(deployServerMonitoring);
		setDefaultXmppServer(defaultServer);
		
		this.filters=new HashMap<String,ActionFilter>();
		addFilters(filters);
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
			Log.warn("[Configuration.setDataSourceType] Unable to set communication type, probably bad typeString=" + typeString + " - configName - " + name);
		}
	}
	
	public void setTestServerMonitoring(String typeString){
		
		try {
			testServerMonitoring = Boolean.valueOf(typeString);
		}
		catch (Exception e){
			Log.error("[Configuration.setTestServerMonitoring] Unable to set, probably bad string=" + typeString + e.toString());
		}
	}
	
	public void setDeployServerMonitoring(String typeString){
		
		try {
			deployServerMonitoring = Boolean.valueOf(typeString);
		}
		catch (Exception e){
			Log.error("[setDeployServerMonitoring] Unable to set, probably bad string=" + typeString + e.toString());
		}
	}
	
	
	public void setDefaultXmppServer(String typeString){
		try {
			defaultXmppServer = XmppServerType.valueOf(typeString);
		}
		catch (Exception e){
			Log.error("[setDefaultXmppServer] Unable to set probably bad string=" + typeString + e.toString());
		}
	}
	
	public void setHistoryStartTime(String historyStartTime){
		this.historyStartTime=historyStartTime;
	}
	
	public String getHistoryStartTime(){
		return historyStartTime;
	}
	
	public void removeFilter(String filterName){
		
		if(filters.containsKey(filterName))
		filters.remove(filterName);
	}

	public XmppServerType getDefaultXmppServer() {
		return defaultXmppServer;
	}

	public boolean isTestServerMonitoring() {
		return testServerMonitoring;
	}

	public boolean isDeployServerMonitoring() {
		return deployServerMonitoring;
	}
	
	
	
}

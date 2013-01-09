package de.uds.MonitorInterventionMetafora.client.logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.extjs.gxt.ui.client.store.ListStore;


import de.uds.MonitorInterventionMetafora.client.User;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanelType;
import de.uds.MonitorInterventionMetafora.client.monitor.filter.FilterGridRow;
import de.uds.MonitorInterventionMetafora.client.urlparameter.UrlParameterConfig;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.monitor.MonitorConstants;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class UserLog {
	 private ComponentType component;
	 private UserActionType userActionType;
	 private String descriptionText;
	 private ComponentType triggeredBy;
//	 private User user;
	 private List<FilterGridRow> filterRules;
	 private Map<String, String> properties;
	 
	public UserLog(){
		
		properties=new HashMap<String,String>();
//		user=new User();
		filterRules=new Vector<FilterGridRow>();
	}
	
	public void setComponentType(ComponentType viewType){
		this.component=viewType;
		}
	
	
	public void setComponentType(DataViewPanelType viewType){
		
			this.component=getComponentType(viewType);
		
	
		}
	public ComponentType getComponentType(){	
		return component;
	}
	
//	public void setUser(User user){
//		this.user=user;
//	}
	
//	public User getUser(){
//		return user;
//	}
	public void setTriggeredBy(ComponentType triggeredBy){
		this.triggeredBy=triggeredBy;
		}
	
	
	public void setTriggeredBy(DataViewPanelType viewType){
	
		this.triggeredBy=getComponentType(viewType);
		
		}
	
	
	ComponentType getComponentType(DataViewPanelType viewType){
		ComponentType componentType = null;
		switch(viewType){
		case TABLE:
			componentType=ComponentType.ACTION_TABLE;
			break;
		case PIE_CHART:
			componentType=ComponentType.PIE_CHART;
			break;
		case BAR_CHART:
			componentType=ComponentType.BAR_CHART;
			break;
		}
		return componentType;
	}
	
	
	public ComponentType getTriggeredBy(){
		
		return triggeredBy;
	}
	
	public void setDescription(String descriptionText){
		
		this.descriptionText=descriptionText;
	}
	
	
	
	
public void setDescription(String descriptionText,ActionPropertyRule actionPropertyRule){
		
	String propertyString=",["+MonitorConstants.PROPERTY_NAME+":"+actionPropertyRule.getDisplayText()+"," +
						  MonitorConstants.OPERATION_TYPE+":"+actionPropertyRule.getOperationType()+","+
						  MonitorConstants.VALUE+":"+actionPropertyRule.getValue();

		propertyString=propertyString+"]";	
		this.descriptionText=descriptionText+propertyString;
	}
	


public void setDescription(String descriptionText,ListStore<FilterGridRow> filterRules){
	this.filterRules=filterRules.getRange(0, filterRules.getCount()-1);
	String propertyString=",[";
	
	for(FilterGridRow rule:filterRules.getRange(0, filterRules.getCount()-1)){
		
		propertyString=propertyString+rule.getDisplayText()+" "+rule.getOperation() +" "+rule.getValue()+","; 
		
	}
	
	propertyString=propertyString+"]";
	
	this.descriptionText=descriptionText+" "+propertyString;
		
}

public String getDescription(){
		return descriptionText;
	}
	

	public void setFilterList(List<FilterGridRow> filters){
		
		this.filterRules=filters;
	}
	
	public List<FilterGridRow> getFilterList(){
		return filterRules;
	}

	public void setUserActionType(UserActionType userActionType){
		this.userActionType=userActionType;
	}
	
	public UserActionType getUserActionType(){
		return userActionType;
	}
	
	public void addProperty(String key,String value){
		properties.put(key, value);
	}
	
	public Map<String, String> getLogProperties(){
		return properties;
	}
	
	public CfAction toCfAction(){
			
		CfActionType cfActionType = new CfActionType("LOG",userActionType.toString(), "true","true");
		CfAction logAction = new CfAction(System.currentTimeMillis(), cfActionType);
		String userID = UrlParameterConfig.getInstance().getUsername();
		logAction.addUser(new CfUser(userID, userID));
		
		CfObject	logObject = new CfObject(component.toString(),component.toString());
		String rules="[";
		for(FilterGridRow filterproperty: filterRules){
			rules=rules+filterproperty.getPropertyName()+","+filterproperty.getOperation().toString()+","+filterproperty.getValue();
		}
		rules=rules+"]";
		
		logObject.addProperty(new CfProperty("FILTER_RULES", rules));
		
		for(String key:properties.keySet()){
			
		logObject.addProperty(new CfProperty(key, properties.get(key)));
		}
		
		CfObject	triggeredByObject;
		if(triggeredBy==null){
		triggeredByObject= new CfObject(component.toString(),component.toString());		
		}
		else{
			
			triggeredByObject= new CfObject(triggeredBy.toString(),triggeredBy.toString());		
		}
		CfContent myContent = new CfContent(descriptionText);
		logAction.addObject(logObject);
		logAction.addObject(triggeredByObject);
		logAction.setCfContent(myContent);
		
		return logAction;
	}

}

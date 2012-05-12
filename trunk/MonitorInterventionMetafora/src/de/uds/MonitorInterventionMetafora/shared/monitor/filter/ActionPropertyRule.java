/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.uds.MonitorInterventionMetafora.shared.monitor.filter;




import java.io.Serializable;

import de.uds.MonitorInterventionMetafora.client.logger.ComponentType;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanelType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.ActionElementType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.monitor.MonitorConstants;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

//a class representing a rule about an aspect of an action (to filter or group by).
//This could be a standard attribute or a property of a CfAction...
public class ActionPropertyRule  implements Serializable{

	private static final long serialVersionUID = 3617519429426816927L;

	

	private String propertyName="";
	private String displayText="";

	private OperationType operationtype;
	private ActionElementType type;
	private String valueToFilterBy="";
	private DataViewPanelType  origin;

	public ActionPropertyRule() {
	}

	public ActionPropertyRule(ActionElementType actionElementType, String propertyName) {
		this(actionElementType, propertyName, propertyName);
	}
	
	public ActionPropertyRule(ActionElementType actionElementType, String propertyName, String displayName) {
		this.type = actionElementType;
		this.propertyName = propertyName;
		this.displayText = displayName;
	}
  
  public ActionPropertyRule(String _entityName, String _value,ActionElementType _type,OperationType _operationtype) {
   
	  propertyName=_entityName;
	  valueToFilterBy=_value;
	  type=_type;
	 operationtype=_operationtype;
	  
  }
  
  public String getPropertyName(){ 
	  return propertyName; 
  }
  
  public void setPropertyName(String propertyName){
	  this.propertyName=propertyName; 
  }

  public String getValue() {
    return valueToFilterBy;
  }
  
  public void setValue(String _value){
	  valueToFilterBy= _value;
  }

  public void setOrigin(DataViewPanelType  origin){
	  this.origin=origin;
  }
  
  public DataViewPanelType  getOrigin(){
	  return origin;
  } 
  public void setType(ActionElementType _type){
	  
	  type= _type;
	  }
  public ActionElementType getType() {
	    return type;
	  }
  
  public void setOperationType(OperationType _operationtype){
	  
	  operationtype=_operationtype;
  }
  
   
 public OperationType getOperationType(){
	  
	  return operationtype;
  }
  
  public void setDisplayText(String _text){
	  
	  displayText=_text;
  }
  
  public String getDisplayText(){
	  
	  return displayText;
  }
  
  public String getKey(){
	   String key= getType().toString()+"-"+ getPropertyName()+"-"+ getValue();
	   key = key.toLowerCase();
	   return key;
  }
  
  public String toString(){
	  return getKey();
  }

	public String getActionValue(CfAction action) {
		String actionValue = null;
		try {
		switch (getType()){
			case ACTION_TYPE:
				if (propertyName.equalsIgnoreCase("classification")){
					actionValue=action.getCfActionType().getClassification();
				} else if (propertyName.equalsIgnoreCase("type")){
					actionValue=action.getCfActionType().getType();
				}
				break;
			case USER:
				//TODO: deal with more than one user
				if (propertyName.equalsIgnoreCase("id")){
					if (action.getCfUsers().size() >0){
						actionValue=action.getCfUsers().get(0).getid();
					}
				}
				break;
			case CONTENT:
				if (propertyName.equalsIgnoreCase("TOOL")){
					actionValue=action.getCfContent().getPropertyValue("TOOL");
				} else if (propertyName.equalsIgnoreCase("INDICATOR_TYPE")){
					actionValue=action.getCfContent().getPropertyValue("INDICATOR_TYPE");
				} else if (propertyName.equalsIgnoreCase("CHALLENGE_NAME")){
					actionValue=action.getCfContent().getPropertyValue("CHALLENGE_NAME");
				} else if (propertyName.equalsIgnoreCase("GROUP_ID")){
					actionValue=action.getCfContent().getPropertyValue("GROUP_ID");
				} else if (propertyName.equalsIgnoreCase("description")){
					actionValue=action.getCfContent().getDescription();
				}
				break;
			case ACTION:
				if (propertyName.equalsIgnoreCase("time")){
					actionValue= Long.toString(action.getTime());
				}
				break;
			case OBJECT:
				//TODO: Deal with multiple objects
				if (action.getCfObjects().size() > 0){
					if (propertyName.equalsIgnoreCase("TOOL")){
						actionValue=action.getCfObjects().get(0).getPropertyValue("TOOL");
					} else if (propertyName.equalsIgnoreCase("INDICATOR_TYPE")){
						actionValue=action.getCfObjects().get(0).getPropertyValue("INDICATOR_TYPE");
					} else if (propertyName.equalsIgnoreCase("CHALLENGE_NAME")){
						actionValue=action.getCfObjects().get(0).getPropertyValue("CHALLENGE_NAME");
					} else if (propertyName.equalsIgnoreCase("GROUP_ID")){
						actionValue=action.getCfObjects().get(0).getPropertyValue("GROUP_ID");
					} else if (propertyName.equalsIgnoreCase("type")){
						actionValue=action.getCfObjects().get(0).getType();
					} else if (propertyName.equalsIgnoreCase("id")){
						actionValue=action.getCfObjects().get(0).getId();
					} 
				}
				break;
			
		}
		}
		catch(Exception e){
			System.out.println("[ActionPropertyRule.getActionValue] No value found in action=\n" + "\nRule:" + this);
		}
		return actionValue;
	}
	
	
	boolean isStatisfyConditionWithMultipleValues(String valueToFilterBy,String actionValue,OperationType operation){
		String [] filterValueList=valueToFilterBy.split(",");
		
		if(filterValueList!=null&&filterValueList.length<=0){
			return false;
		}
		if(operation==OperationType.ISONEOF){
			for(String filtervalue:filterValueList){
				if(actionValue.equalsIgnoreCase(filtervalue.trim())){
					return true;
				}
			}
		}
		else if(operation==OperationType.CONTAINSONEOF) {
			for(String filtervalue:filterValueList){		
				if((actionValue.toLowerCase().contains(filtervalue.trim().toLowerCase()))){
					return true;
				}
			}
		}
		return false;
	}
	
	
	public boolean ruleIncludesAction(CfAction action){
		try {
			if(valueToFilterBy != null){
				String actionValue = getActionValue(action);
				if (actionValue != null){
					if (operationtype == OperationType.EQUALS){
						return valueToFilterBy.equalsIgnoreCase(actionValue);
					}
					else if (operationtype == OperationType.CONTAINS){
						return actionValue.toLowerCase().contains(valueToFilterBy.toLowerCase());
					}
					else if (operationtype == OperationType.OCCUREDWITHIN){
						long timeStamp = Long.valueOf(actionValue);
						if(GWTUtils.getDifferenceInMinutes(timeStamp) <= Integer.valueOf(valueToFilterBy)){
							return true;
						}
						else {
							return false;
						}
					}
					
					else if (operationtype == OperationType.ISONEOF)
					{
						
						return isStatisfyConditionWithMultipleValues(valueToFilterBy,actionValue,OperationType.ISONEOF);
					}
					else if (operationtype == OperationType.CONTAINSONEOF)
					{
						
						return isStatisfyConditionWithMultipleValues(valueToFilterBy,actionValue,OperationType.CONTAINSONEOF);
					}
					
					else {
						System.err.println("ERROR:\t\t[ActionProperty.ruleIncludesAction] Unrecognized OperationType=" + operationtype);
					}
					
					
					
					
					
				}
				else {
					return MonitorConstants.BLANK_PROPERTY_LABEL.equalsIgnoreCase(valueToFilterBy);
	//				System.out.println("DEBUG:\t\t[ActionProperty.ruleIncludesAction] no value for action=\n" + action + "\nRule=" + this);
				}
			}
			else {
				System.err.println("ERROR:\t\t[ActionProperty.ruleIncludesAction] valueToFilterBy is null for Rule=" + this);
			}
		}
		catch(Exception e){
			System.err.println("ERROR\t\t[ActionPropertyRule.ruleIncludesAction] action \n"+ action + "\n rule: " + this +" \n" + e);
		}
		return false;
	}
	
}
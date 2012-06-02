/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.uds.MonitorInterventionMetafora.shared.monitor.filter;




import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.client.logger.ComponentType;
import de.uds.MonitorInterventionMetafora.client.monitor.dataview.DataViewPanelType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
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
	private ComponentType  origin;

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
	  this.origin=getComponentType(origin);
  }
  
  public void setOrigin(ComponentType  origin){
	  this.origin=origin;
  }
  public ComponentType  getOrigin(){
	  return origin;
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

  //TODO: return list of strings for user;tag;object  and return list of string
	public List<String> getActionValue(CfAction action) {
		
		List<String> actionValues=new Vector<String>();

		try {
		switch (getType()){
			case ACTION_TYPE:
				if (propertyName.equalsIgnoreCase("classification")){
					actionValues.add(action.getCfActionType().getClassification());
				} else if (propertyName.equalsIgnoreCase("type")){
					actionValues.add(action.getCfActionType().getType());
				}
				break;
			case USER:
				
				if (propertyName.equalsIgnoreCase("id")){
					if (action.getCfUsers().size() >0){
						for(CfUser user:action.getCfUsers()){
						actionValues.add(user.getid());
						}
					}
				}
				break;
			case CONTENT:
				if (propertyName.equalsIgnoreCase("TOOL")){
					actionValues.add(action.getCfContent().getPropertyValue("TOOL"));
				} else if (propertyName.equalsIgnoreCase("INDICATOR_TYPE")){
					actionValues.add(action.getCfContent().getPropertyValue("INDICATOR_TYPE"));
				} else if (propertyName.equalsIgnoreCase("CHALLENGE_NAME")){
					actionValues.add(action.getCfContent().getPropertyValue("CHALLENGE_NAME"));
				} else if (propertyName.equalsIgnoreCase("GROUP_ID")){
					actionValues.add(action.getCfContent().getPropertyValue("GROUP_ID"));
				} else if (propertyName.equalsIgnoreCase("description")){
					actionValues.add(action.getCfContent().getDescription());
				}
				break;
			case ACTION:
				if (propertyName.equalsIgnoreCase("time")){
					actionValues.add(Long.toString(action.getTime()));
				}
				break;
			case OBJECT:
			
				if (action.getCfObjects().size() > 0){
					
					for(CfObject object:action.getCfObjects()){
						
					if (propertyName.equalsIgnoreCase("TOOL")){
						actionValues.add(object.getPropertyValue("TOOL"));
					} else if (propertyName.equalsIgnoreCase("INDICATOR_TYPE")){
						actionValues.add(object.getPropertyValue("INDICATOR_TYPE"));
					} else if (propertyName.equalsIgnoreCase("CHALLENGE_NAME")){
						actionValues.add(object.getPropertyValue("CHALLENGE_NAME"));
					} else if (propertyName.equalsIgnoreCase("GROUP_ID")){
						actionValues.add(object.getPropertyValue("GROUP_ID"));
					} else if (propertyName.equalsIgnoreCase("type")){
						actionValues.add(object.getType());
					} else if (propertyName.equalsIgnoreCase("id")){
						actionValues.add(object.getId());
					}
					 else if (propertyName.equalsIgnoreCase(MonitorConstants.TAGS)){
						
						 String [] tags=object.getPropertyValue(MonitorConstants.TAGS).split(",");
						 for(String tag:tags){
						 actionValues.add(tag);
						 }
						} 
					}
					
					
				}
				break;
			
		}
		}
		catch(Exception e){
			System.out.println("[ActionPropertyRule.getActionValue] No value found in action=\n" + "\nRule:" + this);
		}
		return actionValues;
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
				List<String> actionValues = getActionValue(action);
				
				String actionValue=actionValues.get(0);
				
				if (actionValue != null){
					if (operationtype == OperationType.EQUALS){
						
						if (propertyName.equalsIgnoreCase(MonitorConstants.TAGS)){
							
							for(String value:actionValues){
								
								if(valueToFilterBy.equalsIgnoreCase(value))
								{
									return true;
								}
							}
							
						}
						else{
						
						return valueToFilterBy.equalsIgnoreCase(actionValue);
						}
					}
					else if (operationtype == OperationType.CONTAINS){
						
						if (propertyName.equalsIgnoreCase(MonitorConstants.TAGS)){
							
							for(String value:actionValues){
								
								if(value.toLowerCase().contains(valueToFilterBy.toLowerCase()))
								{
									return true;
								}
							}
							
						}
						
						else if (propertyName.equalsIgnoreCase(CommonFormatStrings.ID_STRING)){
							
							for(String value:actionValues){
								
								if(value.toLowerCase().contains(valueToFilterBy.toLowerCase()))
								{
									return true;
								}
							}
							
						}
						
						
						else{
						
						
						return actionValue.toLowerCase().contains(valueToFilterBy.toLowerCase());
						}
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
						if (propertyName.equalsIgnoreCase(MonitorConstants.TAGS)){
							
							for(String value:actionValues){
								
								if(isStatisfyConditionWithMultipleValues(valueToFilterBy,value,OperationType.ISONEOF))
								{
									return true;
								}
							}
							
						}
						
						
						else if (propertyName.equalsIgnoreCase(CommonFormatStrings.ID_STRING)){
							
							for(String value:actionValues){
								
								if(isStatisfyConditionWithMultipleValues(valueToFilterBy,value,OperationType.ISONEOF))
								{
									return true;
								}
							}
							
						}
						
						else{
						
						
						
						return isStatisfyConditionWithMultipleValues(valueToFilterBy,actionValue,OperationType.ISONEOF);
						}
					}
					else if (operationtype == OperationType.CONTAINSONEOF)
					{
						
						if (propertyName.equalsIgnoreCase(MonitorConstants.TAGS)){
							
							for(String value:actionValues){
								
								if(isStatisfyConditionWithMultipleValues(valueToFilterBy,value,OperationType.CONTAINSONEOF))
								{
									return true;
								}
							}
							
						}
						else{
						
						return isStatisfyConditionWithMultipleValues(valueToFilterBy,actionValue,OperationType.CONTAINSONEOF);
						}
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
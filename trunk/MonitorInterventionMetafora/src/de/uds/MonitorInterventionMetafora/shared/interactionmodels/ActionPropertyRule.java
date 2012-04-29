/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.uds.MonitorInterventionMetafora.shared.interactionmodels;




import java.io.Serializable;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.ActionElementType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;

//a class representing a rule about an aspect of an action (to filter or group by).
//This could be a standard attribute or a property of a CfAction...
public class ActionPropertyRule  implements Serializable{

	private static final long serialVersionUID = 3617519429426816927L;

	

	private String propertyName="";
	private String displayText="";

	private OperationType operationtype;
	private ActionElementType type;
	private String valueToFilterBy="";

	public ActionPropertyRule() {
	}

	public ActionPropertyRule(ActionElementType actionElementType, String propertyName) {
		this.type = actionElementType;
		this.propertyName = propertyName;
		this.displayText = propertyName;
	}
  
  public ActionPropertyRule(String _entityName, String _value,ActionElementType _type,OperationType _operationtype) {
   
	  propertyName=_entityName;
	  valueToFilterBy=_value;
	  type=_type;
	 operationtype=_operationtype;
	  
  }


  
  public String getEntityName(){
	  
	  return propertyName; 
  }
  
  public void setEntityName(String _entityName){
	  propertyName=_entityName; 
  }
  


  public String getValue() {
    return valueToFilterBy;
  }
  
  public void setValue(String _value){
	  
	  valueToFilterBy= _value;
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
	   String key= getType().toString()+"-"+ getEntityName()+"-"+ getValue();
	   key = key.toLowerCase();
	   return key;
  }
  
  public String toString(){
	  return getKey();
  }

	public String getActionValue(CfAction action) {
		String actionValue = "unknown";
		switch (getType()){
			case ACTION_TYPE:
				if (propertyName.equalsIgnoreCase("classification")){
					actionValue=action.getCfActionType().getClassification();
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
				}
				break;
			
		}
		return actionValue;
	}
	
}
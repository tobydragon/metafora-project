/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.uds.MonitorInterventionMetafora.shared.interactionmodels;




import java.io.Serializable;

import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.FilterItemType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;

//a class representing one aspect of an indicator (to filter by).
//This could be a standard attribute of an indicator or a property...
public class IndicatorEntity  implements Serializable{

	private static final long serialVersionUID = 3617519429426816927L;

	

	private String entityName="";
	private String displayText="";

	private OperationType operationtype;
	private FilterItemType type;
	private String value="";

	public IndicatorEntity() {
	}

  
  public IndicatorEntity(String _entityName, String _value,FilterItemType _type,OperationType _operationtype) {
   
	  entityName=_entityName;
	  value=_value;
	  type=_type;
	 operationtype=_operationtype;
	  
  }


  
  public String getEntityName(){
	  
	  return entityName; 
  }
  
  public void setEntityName(String _entityName){
	  entityName=_entityName; 
  }
  


  public String getValue() {
    return value;
  }
  
  public void setValue(String _value){
	  
	  value= _value;
  }

  
  public void setType(FilterItemType _type){
	  
	  type= _type;
	  }
  public FilterItemType getType() {
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
  
  //gets a unique key for this entity
  public String getKey(){
	   return getType().toString()+"-"+ getEntityName()+"-"+ getValue();
  }

}

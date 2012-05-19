/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.uds.MonitorInterventionMetafora.client.monitor.filter;




import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BaseModel;

import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.ActionElementType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

//TODO: remake this class, as IndicatorGridRowItem, where it holds the IndicatorEntity and also sets the necessary values for the table
public class FilterGridRow extends BaseModel implements Serializable {


	
  /**
	 * 
	 */
	private static final long serialVersionUID = -3416203885980295041L;

private ActionPropertyRule filterRule;
public FilterGridRow() {
  
	filterRule=new ActionPropertyRule();
}

  public FilterGridRow(ActionPropertyRule filterRule) {
	  this.filterRule=filterRule;
	  set("filtertype", this.filterRule.getType().toString());
	  set("displaytext",this.filterRule.getDisplayText());
	  set("property",this.filterRule.getPropertyName());
	  set("operation",this.filterRule.getOperationType().toString());
      set("value", this.filterRule.getValue());
   
   
  }


  
  public void setDisplayText(String  displayText){
	  
	  filterRule.setDisplayText(displayText);
	  set("displaytext", displayText);
	  
  }
  
  public String getDisplayText(){
	  
	  return filterRule.getDisplayText();  
  }
  
  public void setOperation(OperationType operation){
	  filterRule.setOperationType(operation);
	  set("operation",operation.toString());
	  
  }
  
  public OperationType getOperation(){
	  
	  return filterRule.getOperationType(); 
	  
  }
  
  public String getPropertyName(){
	  
	  return  filterRule.getPropertyName(); 
  }
  
  public void setPropertyName(String propertyName){
	  set("property",propertyName);
	  filterRule.setPropertyName(propertyName);
  }
  


  public String getValue() {
    return filterRule.getValue();
  }
  
  public void setValue(String _value){
	  
	  set("value", _value);
	  filterRule.setValue(_value);
  }

  
  public void setType(ActionElementType _type){
	  
	  set("filtertype", _type.toString());
	  filterRule.setType(_type);
  }
  public ActionElementType getType() {
	    return filterRule.getType();
	  }
   
  
  public ActionPropertyRule getActionPropertyRule(){
	  
	  return filterRule;
  }
  
  public String toString() {
    return getPropertyName();
  }

  public String getKey(){
	 return  getType().toString()+"-"+ getPropertyName()+"-"+ getValue();
  }

}

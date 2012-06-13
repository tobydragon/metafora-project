/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.uds.MonitorInterventionMetafora.client.monitor.datamodel;

import com.extjs.gxt.ui.client.data.BaseModelData;

import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.ActionElementType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class PropertyGridModel extends BaseModelData {

  /**
	 * 
	 */
	private static final long serialVersionUID = -8119756981959764189L;

public PropertyGridModel() {

  }


public PropertyGridModel(String propertyName,String value) {
	setPropertyName(propertyName);
	setValue(value);
	
	
}
    

 
public void setValue(String _value){
	set("Value",_value);
	
}

public String getValue(){
	
	return get("Value");
	
}

 
  
  public String getPropertyName() {
    return get("PropertyName");
  }

  public void setPropertyName(String propertyName) {
    set("PropertyName", propertyName);
  }
  
  
  

}

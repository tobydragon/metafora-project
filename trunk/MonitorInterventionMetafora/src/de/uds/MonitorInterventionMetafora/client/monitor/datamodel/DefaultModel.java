/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package de.uds.MonitorInterventionMetafora.client.monitor.datamodel;

import com.extjs.gxt.ui.client.data.BaseModelData;

import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.ActionSubsection;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class DefaultModel extends BaseModelData {

  /**
	 * 
	 */
	private static final long serialVersionUID = -8119756981959764189L;

public DefaultModel() {

  }


public DefaultModel(String _displayText,String _value) {
	setDisplayText(_displayText);
	setValue(_value);
	
	
}
    

 
public void setValue(String _value){
	set("value",_value);
	
}

public String getValue(){
	
	return get("value");
	
}

 
  
  public String getDisplayText() {
    return get("displaytext");
  }

  public void setDisplayText(String _displayText) {
    set("displaytext", _displayText);
  }
  
  
  

}

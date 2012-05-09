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

public class OperationsComboBoxModel extends BaseModelData {

  /**
	 * 
	 */
	private static final long serialVersionUID = -8119756981959764189L;

public OperationsComboBoxModel() {

  }

private OperationType operationType;

public OperationsComboBoxModel(String displayText,OperationType operationType) {
	setDisplayText(displayText);
	setOperationType(operationType);
	this.operationType=operationType;	
	
}
    

 
public void setOperationType(OperationType _operationType){
	set("operationtype",_operationType.toString());
	
}

public OperationType getOperationType(){
	
	return operationType;
	
}

 
  
  public String getDisplayText() {
    return get("displaytext");
  }

  public void setDisplayText(String _displayText) {
    set("displaytext", _displayText);
  }
  
  
  

}

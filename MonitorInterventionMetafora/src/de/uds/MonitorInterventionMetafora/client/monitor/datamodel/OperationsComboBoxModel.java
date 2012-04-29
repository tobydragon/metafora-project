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
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.ActionPropertyRule;

public class OperationsComboBoxModel extends BaseModelData {

  /**
	 * 
	 */
	private static final long serialVersionUID = -8119756981959764189L;

public OperationsComboBoxModel() {

  }


public OperationsComboBoxModel(String _displayText,OperationType _operationType) {
	setDisplayText(_displayText);
	setOperationType(_operationType);
	
	
}
    

 
public void setOperationType(OperationType _operationType){
	set("operationtype",_operationType.toString());
	
}

public String getOperationType(){
	
	return get("operationtype");
	
}

 
  
  public String getDisplayText() {
    return get("displaytext");
  }

  public void setDisplayText(String _displayText) {
    set("displaytext", _displayText);
  }
  
  
  

}

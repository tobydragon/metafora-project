package de.uds.MonitorInterventionMetafora.client.monitor.datamodel;

import com.extjs.gxt.ui.client.data.BaseModelData;

import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;

public class OperationsComboBoxModel extends BaseModelData {

	private static final long serialVersionUID = -8119756981959764189L;

	private OperationType operationType;
	
	public OperationsComboBoxModel(OperationType operationType) {
		setOperationType(operationType);
	}    
 
	public void setOperationType(OperationType operationType){
		this.operationType=operationType;
		set("operationtype",operationType.toString());
		
		setDisplayText(operationType.toString());
	}

	public OperationType getOperationType(){
		return operationType;
	}

	public String getDisplayText() {
		return get("displaytext");
	}

	public void setDisplayText(String displayText) {
		set("displaytext", displayText);
	}

}

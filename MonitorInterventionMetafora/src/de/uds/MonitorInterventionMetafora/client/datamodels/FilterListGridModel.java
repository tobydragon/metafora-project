package de.uds.MonitorInterventionMetafora.client.datamodels;

import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;

public class FilterListGridModel {


	ActionMaintenance maintenance;
	public FilterListGridModel(ActionMaintenance _maintenance){
		
		
		maintenance=_maintenance;
	}



public ActionMaintenance getActionMaintenance(){
	
	return maintenance;
}

}


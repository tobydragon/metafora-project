package de.uds.MonitorInterventionMetafora.client.datamodels;

import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.UpdatingDataModel;

public class FilterListGridModel {


	UpdatingDataModel maintenance;
	public FilterListGridModel(UpdatingDataModel _maintenance){
		
		
		maintenance=_maintenance;
	}



public UpdatingDataModel getActionMaintenance(){
	
	return maintenance;
}

}


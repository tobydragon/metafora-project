package de.uds.MonitorInterventionMetafora.client.communication.servercommunication;

import java.util.List;

import com.google.gwt.user.client.Timer;
import de.uds.MonitorInterventionMetafora.client.actionresponse.RequestUpdateCallBack;
import de.uds.MonitorInterventionMetafora.client.communication.ServerCommunication;
import de.uds.MonitorInterventionMetafora.client.datamodels.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.client.manager.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class ClientMonitorDataModelUpdater extends Timer implements RequestUpdateCallBack{

	private ClientMonitorDataModel clientDataModel;
	private ClientMonitorController controller;

	public ClientMonitorDataModelUpdater(ClientMonitorDataModel monitorModel, ClientMonitorController controller) {
		this.clientDataModel = monitorModel;
		this.controller = controller;
	}
	
	public void update(List<CfAction> actions){
		clientDataModel.addData(actions);
		controller.refreshViews();
	}

	public void setActiveActionList(List<CfAction> actions){
		update(actions);
	}
	
	public void startMaintenance(){
		this.scheduleRepeating(5000);
	}
	
	public void stopMaintenance(){
		this.cancel();	
	}
	
	
	
	@Override
	public void onFailure(Throwable caught) {
		System.out.println("ERROR [UpdatingDataModel.onFailure] call failed "+ caught);
	}

	@Override
	public void onSuccess(List<CfAction> actions) {
		if(actions!=null){	
			update(actions);
			System.out.println("DEBUG [UpdatingDataModel.onSuccess] Action recieved and list updated");
		}
		else{
			System.out.println("DEBUG [UpdatingDataModel.onSuccess] No Action Update Recieved");
		}
	}

	@Override
	public void run() {
		ServerCommunication.getInstance().processAction(clientDataModel.getLastAction(),this);
		
	}

	public ClientMonitorDataModel getClientDataModel() {
		return clientDataModel;
	}
	
}

package de.uds.MonitorInterventionMetafora.client.monitor;

import java.util.List;

import com.google.gwt.user.client.Timer;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationServiceAsync;
import de.uds.MonitorInterventionMetafora.client.communication.actionresponses.RequestUpdateCallBack;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ClientMonitorDataModel;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;

public class ClientMonitorDataModelUpdater extends Timer implements RequestUpdateCallBack{

	private ClientMonitorDataModel clientDataModel;
	private ClientMonitorController controller;
	

	public ClientMonitorDataModelUpdater(ClientMonitorDataModel monitorModel, ClientMonitorController controller) {
		this.clientDataModel = monitorModel;
		this.controller = controller;
		
	}
	
	public void receiveUpdate(List<CfAction> actions){
		clientDataModel.addData(actions);
		controller.refreshViews();
	}

	public void startUpdates(){
		this.scheduleRepeating(3000);
	}
	
	public void stopUpdates(){
		this.cancel();	
	}
	
	@Override
	public void onFailure(Throwable caught) {
		System.out.println("ERROR [UpdatingDataModel.onFailure] call failed "+ caught);
	}

	@Override
	public void onSuccess(List<CfAction> actions) {
		if(actions!=null && actions.size() > 0){	
			receiveUpdate(actions);
			System.out.println("DEBUG [UpdatingDataModel.onSuccess] Action recieved and list updated");
		}
		else{
			System.out.println("DEBUG [UpdatingDataModel.onSuccess] No Action Update Recieved");
		}
	}
	
	public void getUpdate(){
		
		clientDataModel.getServiceServlet().requestUpdate(clientDataModel.getLastAction(),this);
		
		
		//ServerCommunication.getInstance().processAction(clientDataModel.getLastAction(),this);
	}

	@Override
	public void run() {
		getUpdate();
	}

	public ClientMonitorDataModel getClientDataModel() {
		return clientDataModel;
	}
	
}

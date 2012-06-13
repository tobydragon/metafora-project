package de.uds.MonitorInterventionMetafora.client.monitor;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;

import de.uds.MonitorInterventionMetafora.client.communication.CommunicationService;
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
		Log.debug("Action 5: "+actions.size());
		clientDataModel.addData(actions);
		Log.debug("Action 6: "+actions.size());
		controller.refreshViews();
		Log.debug("Action 7: "+actions.size());
	}

	public void startUpdates(){
		this.scheduleRepeating(25000);
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
		
		Log.debug("3Update Response is recieved from the model.Action Size:"+actions.size());
		if(actions!=null && actions.size() > 0){
			Log.debug("4 updated model.Action Size:"+actions.size());
			receiveUpdate(actions);
			System.out.println("DEBUG [UpdatingDataModel.onSuccess] Action recieved and list updated");
		}
		else{
			System.out.println("DEBUG [UpdatingDataModel.onSuccess] No Action Update Recieved");
		}
	}
	
	public void getUpdate(){
		
		Log.debug("1Update Request is sent from model is started!");
		
		
		 CommunicationServiceAsync monitoringServ = GWT.create(CommunicationService.class);
		
		 monitoringServ.requestUpdate(clientDataModel.getLastAction(),this);
	//	clientDataModel.getServiceServlet()
		Log.debug("2Update Request is sent from model is started!");
		
		
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
